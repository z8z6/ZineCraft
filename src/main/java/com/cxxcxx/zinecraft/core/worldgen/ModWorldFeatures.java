package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.api.world.feature.SimpleFeatureEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public final class ModWorldFeatures {

  public static final OreEntry EXAMPLE_BLOCK_ORE = Zinecraft.WORLDGEN.features.ore(
      "example_block_ore_placed", ModBlock.EXAMPLE_ENTITY_BLOCK, 30, 6, 0, 0, BiomeSelection.overworld());
  private static final BiomeSelection TERRA_BIOMES = BiomeSelection.of(
      NationBiomePlacements.ALL.stream().map(placement -> placement.getBiome()).toArray(ResourceKey[]::new)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_SMALL_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_small",
      new OriginiumSpireFeature(2, 4, 4, 8, 1, 4)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_MEDIUM_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_medium",
      new OriginiumSpireFeature(4, 7, 8, 15, 2, 7)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_LARGE_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_large",
      new OriginiumSpireFeature(7, 11, 14, 28, 3, 12)
  );
  private static final SimpleFeatureEntry ORIGINIUM_SPIRE_SMALL = originiumSpire(
      "originium_spire_small", ORIGINIUM_SPIRE_SMALL_FEATURE, 28, TERRA_BIOMES
  );
  private static final SimpleFeatureEntry ORIGINIUM_SPIRE_MEDIUM = originiumSpire(
      "originium_spire_medium", ORIGINIUM_SPIRE_MEDIUM_FEATURE, 84, TERRA_BIOMES
  );
  private static final SimpleFeatureEntry ORIGINIUM_SPIRE_LARGE = originiumSpire(
      "originium_spire_large", ORIGINIUM_SPIRE_LARGE_FEATURE, 220, TERRA_BIOMES
  );
  private static final SimpleFeatureEntry CATASTROPHE_ORIGINIUM_SPIRE_SMALL = denseOriginiumSpire(
      "catastrophe_originium_spire_small", ORIGINIUM_SPIRE_SMALL_FEATURE, 5
  );
  private static final SimpleFeatureEntry CATASTROPHE_ORIGINIUM_SPIRE_MEDIUM = denseOriginiumSpire(
      "catastrophe_originium_spire_medium", ORIGINIUM_SPIRE_MEDIUM_FEATURE, 2
  );
  private static final LateranoDryLandFeature LATERANO_DRY_LAND_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "laterano_dry_land", new LateranoDryLandFeature()
  );

  private static final SimpleFeatureEntry LATERANO_DRY_LAND = Zinecraft.WORLDGEN.features.simple(
      "laterano_dry_land", LATERANO_DRY_LAND_FEATURE,
      List.of(CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome()),
      GenerationStep.Decoration.TOP_LAYER_MODIFICATION, BiomeSelection.of(ModBiome.LATERANO_HOLY_FIELDS)
  );
  private static final SimpleFeatureEntry CATASTROPHE_ORIGINIUM_SPIRE_LARGE = originiumSpire(
      "catastrophe_originium_spire_large", ORIGINIUM_SPIRE_LARGE_FEATURE, 6,
      BiomeSelection.of(ModBiome.TERRA_CATASTROPHE_ZONE)
  );

  private ModWorldFeatures() {
  }

  private static SimpleFeatureEntry originiumSpire(
      String path, OriginiumSpireFeature feature, int rarity, BiomeSelection biomes
  ) {
    return Zinecraft.WORLDGEN.features.simple(
        path, feature,
        List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, biomes
    );
  }

  private static SimpleFeatureEntry denseOriginiumSpire(String path, OriginiumSpireFeature feature, int count) {
    return Zinecraft.WORLDGEN.features.simple(
        path, feature,
        List.of(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, BiomeSelection.of(ModBiome.TERRA_CATASTROPHE_ZONE)
    );
  }

  public static void bootstrap() {
  }

}
