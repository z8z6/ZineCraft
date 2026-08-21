package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.OreBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.SimpleFeatureBuilder;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock;
import com.cxxcxx.zinecraft.core.worldgen.LateranoDryLandFeature;
import com.cxxcxx.zinecraft.core.worldgen.OriginiumSpireFeature;
import com.cxxcxx.zinecraft.core.worldgen.TerraHydrologyFeature;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public final class ModWorldFeature {

  public static final OreBuilder<ExampleEntityBlock> EXAMPLE_BLOCK_ORE = new OreBuilder<>(
      Zinecraft.FEATURES,
      "example_block_ore_placed",
      ModBlockEntity.EXAMPLE_BLOCK_ENTITY.entityBlock()
  ).vein(30, 6)
      .maxY(0)
      .discardChanceOnAirExposure(0)
      .biomes(BiomeSelection.overworld())
      .build();
  private static final BiomeSelection TERRA_BIOMES = BiomeSelection.of(
      ModBiome.ALL_TERRA_BIOMES
  );
  private static final TerraHydrologyFeature TERRA_HYDROLOGY_FEATURE = Zinecraft.FEATURES.register(
      "terra_hydrology", new TerraHydrologyFeature()
  );
  private static final SimpleFeatureBuilder TERRA_HYDROLOGY = new SimpleFeatureBuilder(
      Zinecraft.FEATURES, "terra_hydrology", TERRA_HYDROLOGY_FEATURE
  ).placement(List.of(CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome()))
      .generationStep(GenerationStep.Decoration.TOP_LAYER_MODIFICATION)
      .biomes(TERRA_BIOMES)
      .build();
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_SMALL_FEATURE = Zinecraft.FEATURES.register(
      "originium_spire_small",
      new OriginiumSpireFeature(2, 4, 4, 8, 1, 4)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_MEDIUM_FEATURE = Zinecraft.FEATURES.register(
      "originium_spire_medium",
      new OriginiumSpireFeature(4, 7, 8, 15, 2, 7)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_LARGE_FEATURE = Zinecraft.FEATURES.register(
      "originium_spire_large",
      new OriginiumSpireFeature(7, 11, 14, 28, 3, 12)
  );
  private static final SimpleFeatureBuilder ORIGINIUM_SPIRE_SMALL = originiumSpire(
      "originium_spire_small", ORIGINIUM_SPIRE_SMALL_FEATURE, 28, TERRA_BIOMES
  );
  private static final SimpleFeatureBuilder ORIGINIUM_SPIRE_MEDIUM = originiumSpire(
      "originium_spire_medium", ORIGINIUM_SPIRE_MEDIUM_FEATURE, 84, TERRA_BIOMES
  );
  private static final SimpleFeatureBuilder ORIGINIUM_SPIRE_LARGE = originiumSpire(
      "originium_spire_large", ORIGINIUM_SPIRE_LARGE_FEATURE, 220, TERRA_BIOMES
  );
  private static final LateranoDryLandFeature LATERANO_DRY_LAND_FEATURE = Zinecraft.FEATURES.register(
      "laterano_dry_land", new LateranoDryLandFeature()
  );

  private static final SimpleFeatureBuilder LATERANO_DRY_LAND = new SimpleFeatureBuilder(
      Zinecraft.FEATURES, "laterano_dry_land", LATERANO_DRY_LAND_FEATURE
  ).placement(List.of(CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome()))
      .generationStep(GenerationStep.Decoration.TOP_LAYER_MODIFICATION)
      .biomes(BiomeSelection.of(ModBiome.LATERANO_HOLY_FIELDS.key()))
      .build();

  private ModWorldFeature() {
  }

  private static SimpleFeatureBuilder originiumSpire(
      String path, OriginiumSpireFeature feature, int rarity, BiomeSelection biomes
  ) {
    return new SimpleFeatureBuilder(Zinecraft.FEATURES, path, feature)
        .placement(List.of(
            RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), BiomeFilter.biome()
        ))
        .generationStep(GenerationStep.Decoration.LOCAL_MODIFICATIONS)
        .biomes(biomes)
        .build();
  }

  public static void bootstrap() {
  }

}
