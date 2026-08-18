package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.block.BlockEntry;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ItemLike;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class FeatureCatalog {
  private final ModRegistrar registrar;
  private final BlockCatalog blocks;
  private final List<OreEntry> mutableOres = new ArrayList<>();
  public final List<OreEntry> ores = Collections.unmodifiableList(mutableOres);
  private final List<MaterialOre> mutableMaterialOres = new ArrayList<>();
  public final List<MaterialOre> materialOres = Collections.unmodifiableList(mutableMaterialOres);
  private final List<SimpleFeatureEntry> mutableSimpleFeatures = new ArrayList<>();
  public final List<SimpleFeatureEntry> simpleFeatures = Collections.unmodifiableList(mutableSimpleFeatures);

  public FeatureCatalog(ModRegistrar registrar, BlockCatalog blocks) {
    this.registrar = Objects.requireNonNull(registrar, "registrar");
    this.blocks = Objects.requireNonNull(blocks, "blocks");
  }

  /**
   * 通过地物目录的唯一入口注册材料矿石，并返回包含方块、地物、掉落物和烧炼信息的封装对象。
   *
   * @param blockPath                  方块及其方块物品的注册路径
   * @param featurePath                配置地物和放置地物的注册路径
   * @param zhCn                       矿石方块中文名
   * @param blockFactory               矿石方块构造工厂
   * @param drop                       挖掘矿石时掉落的物品
   * @param cookingGroup               熔炼与高炉配方共用的分组名
   * @param veinSize                   单条矿脉最多生成的方块数
   * @param veinsPerChunk              每区块尝试放置矿脉的次数
   * @param maxY                       偏向底部高度分布的最高端点
   * @param discardChanceOnAirExposure 矿石暴露于空气时被丢弃的概率
   * @param biomes                     允许生成矿石的维度或群系范围
   * @return 材料矿石的完整注册封装对象
   */
  public MaterialOre materialOre(
      String blockPath,
      String featurePath,
      String zhCn,
      Supplier<? extends Block> blockFactory,
      ItemLike drop,
      String cookingGroup,
      int veinSize,
      int veinsPerChunk,
      int maxY,
      float discardChanceOnAirExposure,
      BiomeSelection biomes
  ) {
    Objects.requireNonNull(drop, "drop");
    BlockEntry<? extends Block> block = blocks.builder(blockPath, zhCn, blockFactory)
        .drop(drop)
        .build();
    OreEntry feature = ore(
        featurePath,
        block,
        veinSize,
        veinsPerChunk,
        maxY,
        discardChanceOnAirExposure,
        biomes
    );
    MaterialOre entry = new MaterialOre(block, feature, drop, cookingGroup);
    mutableMaterialOres.add(entry);
    return entry;
  }

  /**
   * 声明矿石的配置地物、放置地物和群系修改器来源条目。
   *
   * @param path                       放置地物的基础注册路径；配置地物会追加 {@code _vein}
   * @param block                      矿脉实际放置的方块
   * @param veinSize                   单条矿脉最多生成的方块数
   * @param veinsPerChunk              每区块尝试放置矿脉的次数
   * @param maxY                       偏向底部高度分布的最高端点
   * @param discardChanceOnAirExposure 暴露矿石被丢弃的概率，范围为 0 到 1
   * @param biomes                     目标维度或群系范围
   * @return 矿石的不可变注册键和生成参数
   */
  public OreEntry ore(
      String path,
      Supplier<? extends Block> block,
      int veinSize,
      int veinsPerChunk,
      int maxY,
      float discardChanceOnAirExposure,
      BiomeSelection biomes
  ) {
    if (veinSize <= 0 || veinsPerChunk <= 0) throw new IllegalArgumentException("矿脉参数必须大于 0");
    if (discardChanceOnAirExposure < 0 || discardChanceOnAirExposure > 1)
      throw new IllegalArgumentException("暴露丢弃概率必须在 0 到 1 之间");
    var entry = new OreEntry(
        registrar.key(Registries.CONFIGURED_FEATURE, path + "_vein"),
        registrar.key(Registries.PLACED_FEATURE, path), block, veinSize, veinsPerChunk,
        maxY, discardChanceOnAirExposure, biomes
    );
    mutableOres.add(entry);
    return entry;
  }

  public SimpleFeatureEntry simple(String path, Feature<NoneFeatureConfiguration> feature,
                                   List<? extends PlacementModifier> placement, GenerationStep.Decoration step, BiomeSelection biomes) {
    if (path.isBlank() || placement.isEmpty()) throw new IllegalArgumentException("地物 ID 和放置规则不能为空");
    if (simpleFeatures.stream().anyMatch(entry -> entry.path().equals(path)))
      throw new IllegalArgumentException("地物 ID 重复: " + path);
    var entry = new SimpleFeatureEntry(path, registrar.key(Registries.CONFIGURED_FEATURE, path),
        registrar.key(Registries.PLACED_FEATURE, path), feature, List.copyOf(placement), step, biomes);
    mutableSimpleFeatures.add(entry);
    return entry;
  }

  public void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
    for (var ore : ores) {
      var targets = List.of(
          OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.block().get().defaultBlockState()),
          OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ore.block().get().defaultBlockState())
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
