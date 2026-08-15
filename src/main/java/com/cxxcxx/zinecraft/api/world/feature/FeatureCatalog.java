package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class FeatureCatalog {
  private final ModRegistrar registrar;
  private final List<OreEntry> ores = new ArrayList<>();
  private final List<SimpleFeatureEntry> simpleFeatures = new ArrayList<>();

  public FeatureCatalog(ModRegistrar registrar) {
    this.registrar = registrar;
  }

  public static OreEntry oreWithDefaults(FeatureCatalog self, String path, Supplier<? extends Block> block, int veinSize,
                                     int veinsPerChunk, int maxY, float discard, BiomeSelection biomes, int mask, Object marker) {
    return self.ore(path, block, veinSize, veinsPerChunk,
        (mask & 16) != 0 ? 0 : maxY, (mask & 32) != 0 ? 0 : discard,
        (mask & 64) != 0 ? BiomeSelection.overworld() : biomes);
  }

  public OreEntry ore(String path, Supplier<? extends Block> block, int veinSize, int veinsPerChunk, int maxY,
                      float discardChanceOnAirExposure, BiomeSelection biomes) {
    if (veinSize <= 0 || veinsPerChunk <= 0) throw new IllegalArgumentException("矿脉参数必须大于 0");
    if (discardChanceOnAirExposure < 0 || discardChanceOnAirExposure > 1)
      throw new IllegalArgumentException("暴露丢弃概率必须在 0 到 1 之间");
    var entry = new OreEntry(
        registrar.key(Registries.CONFIGURED_FEATURE, path + "_vein"),
        registrar.key(Registries.PLACED_FEATURE, path), block, veinSize, veinsPerChunk,
        maxY, discardChanceOnAirExposure, biomes
    );
    ores.add(entry);
    return entry;
  }

  public SimpleFeatureEntry simple(String path, Feature<NoneFeatureConfiguration> feature,
                                   List<? extends PlacementModifier> placement, GenerationStep.Decoration step, BiomeSelection biomes) {
    if (path.isBlank() || placement.isEmpty()) throw new IllegalArgumentException("地物 ID 和放置规则不能为空");
    if (simpleFeatures.stream().anyMatch(entry -> entry.path().equals(path)))
      throw new IllegalArgumentException("地物 ID 重复: " + path);
    var entry = new SimpleFeatureEntry(path, registrar.key(Registries.CONFIGURED_FEATURE, path),
        registrar.key(Registries.PLACED_FEATURE, path), feature, List.copyOf(placement), step, biomes);
    simpleFeatures.add(entry);
    return entry;
  }

  public void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    for (var ore : ores) {
      var targets = List.of(
          OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.getBlock().defaultBlockState()),
          OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ore.getBlock().defaultBlockState())
      );
      context.register(ore.configuredKey(), new ConfiguredFeature<>(Feature.ORE,
          new OreConfiguration(targets, ore.veinSize(), ore.discardChanceOnAirExposure())));
    }
    for (var entry : simpleFeatures) {
      context.register(entry.configuredKey(), new ConfiguredFeature<>(entry.feature(), NoneFeatureConfiguration.INSTANCE));
    }
  }

  public void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
    var configured = context.lookup(Registries.CONFIGURED_FEATURE);
    for (var ore : ores) {
      context.register(ore.placedKey(), new PlacedFeature(configured.getOrThrow(ore.configuredKey()), List.of(
          CountPlacement.of(ore.veinsPerChunk()), InSquarePlacement.spread(),
          HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(ore.maxY()), 3)),
          BiomeFilter.biome()
      )));
    }
    for (var entry : simpleFeatures) {
      context.register(entry.placedKey(), new PlacedFeature(configured.getOrThrow(entry.configuredKey()), entry.placement()));
    }
  }

  public void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
    var biomes = context.lookup(Registries.BIOME);
    var placed = context.lookup(Registries.PLACED_FEATURE);
    for (var ore : ores) {
      var parts = ore.biomes().resolveParts(biomes);
      for (int i = 0; i < parts.size(); i++) {
        String path = ore.placedKey().location().getPath() + (parts.size() == 1 ? "" : "_" + i);
        context.register(registrar.key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, path),
            new BiomeModifiers.AddFeaturesBiomeModifier(parts.get(i),
                HolderSet.direct(placed.getOrThrow(ore.placedKey())), GenerationStep.Decoration.UNDERGROUND_ORES));
      }
    }
    for (var entry : simpleFeatures) {
      var parts = entry.biomes().resolveParts(biomes);
      for (int i = 0; i < parts.size(); i++) {
        String path = entry.path() + (parts.size() == 1 ? "" : "_" + i);
        context.register(registrar.key(NeoForgeRegistries.Keys.BIOME_MODIFIERS, path),
            new BiomeModifiers.AddFeaturesBiomeModifier(parts.get(i),
                HolderSet.direct(placed.getOrThrow(entry.placedKey())), entry.generationStep()));
      }
    }
  }
}
