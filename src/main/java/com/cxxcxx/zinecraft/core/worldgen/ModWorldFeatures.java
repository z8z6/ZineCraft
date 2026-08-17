package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.api.world.feature.SimpleFeatureEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.cxxcxx.zinecraft.core.block.MaterialOres;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.function.Supplier;

public final class ModWorldFeatures {
  public static final ModWorldFeatures INSTANCE = new ModWorldFeatures();

  public static final OreEntry EXAMPLE_BLOCK_ORE = Zinecraft.WORLDGEN.getFeatures().ore(
      "example_block_ore_placed", ModBlock.INSTANCE.EXAMPLE_ENTITY_BLOCK::getBlock, 30, 6, 0, 0, BiomeSelection.overworld());
  private static final BiomeSelection TERRA_BIOMES = BiomeSelection.of(
      NationBiomePlacements.ALL.stream().map(placement -> placement.getBiome()).toArray(ResourceKey[]::new)
  );
  private static final BiomeSelection MATERIAL_DIMENSIONS = BiomeSelection.union(
      BiomeSelection.overworld(),
      BiomeSelection.of(
          NationBiomes.AEGIR_ABYSSAL_SEA, NationBiomes.BOLIVAR_PLAIN,
          NationBiomes.HIGASHI_SHADOW_RIFT, NationBiomes.DURIN_UNDERGROUND_GARDEN,
          NationBiomes.COLUMBIA_SANDSTONE_WILDS, NationBiomes.KAZIMIERZ_KNIGHTLAND,
          NationBiomes.KAZDEL_SCARRED_WASTES, NationBiomes.LATERANO_HOLY_FIELDS,
          NationBiomes.LEITHANIEN_TWILIGHT_FOREST, NationBiomes.RIM_BILLITON_MINING_BADLANDS,
          NationBiomes.MINOS_SUNLIT_HILLS, NationBiomes.SARGON_ROCKY_DESERT,
          NationBiomes.SAMI_FROZEN_FOREST, NationBiomes.VICTORIA_MISTY_HIGHLANDS,
          NationBiomes.URSUS_FROZEN_STEPPE, NationBiomes.KJERAG_SNOWY_PEAKS,
          NationBiomes.SIRACUSA_RAINY_WOODLAND, NationBiomes.YAN_MOUNTAIN_GROVE,
          NationBiomes.IBERIA_SALT_DELTA, NationBiomes.TERRA_CATASTROPHE_ZONE
      )
  );
  public static final OreEntry ORIGINITE_ORE = ore("originite_ore", MaterialOres.ORIGINITE_ORE::getBlock, 3, 2, -32, .25f);
  public static final OreEntry ORIROCK_ORE = ore("orirock_ore", MaterialOres.ORIROCK_ORE::getBlock, 10, 12, 64, 0);
  public static final OreEntry ORIRON_ORE = ore("oriron_ore", MaterialOres.ORIRON_ORE::getBlock, 7, 8, 32, 0);
  public static final OreEntry MANGANESE_ORE = ore("manganese_ore", MaterialOres.MANGANESE_ORE::getBlock, 5, 6, 16, .1f);
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
  public static final OreEntry GRINDSTONE_ORE = ore("grindstone_ore", MaterialOres.GRINDSTONE_ORE::getBlock, 6, 5, 0, .15f);
  public static final OreEntry RMA70_ORE = ore("rma70_ore", MaterialOres.RMA70_ORE::getBlock, 4, 3, -32, .25f);
  public static final OreEntry CRYSTAL_ELEMENT_ORE = ore("crystal_element_ore", MaterialOres.CRYSTAL_ELEMENT_ORE::getBlock, 5, 4, 16, .15f);
  public static final OreEntry LOXIC_KOHL_ORE = ore("loxic_kohl_ore", MaterialOres.LOXIC_KOHL_ORE::getBlock, 4, 3, -16, .2f);
  private static final LateranoDryLandFeature LATERANO_DRY_LAND_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "laterano_dry_land", new LateranoDryLandFeature()
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_SMALL_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_small", new OriginiumSpireFeature(2, 4, 4, 8, 1, 4)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_MEDIUM_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_medium", new OriginiumSpireFeature(4, 7, 8, 15, 2, 7)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_LARGE_FEATURE = Zinecraft.REGISTRAR.register(
      BuiltInRegistries.FEATURE, "originium_spire_large", new OriginiumSpireFeature(7, 11, 14, 28, 3, 12)
  );
  private static final SimpleFeatureEntry LATERANO_DRY_LAND = Zinecraft.WORLDGEN.getFeatures().simple(
      "laterano_dry_land", LATERANO_DRY_LAND_FEATURE,
      List.of(CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome()),
      GenerationStep.Decoration.TOP_LAYER_MODIFICATION, BiomeSelection.of(NationBiomes.LATERANO_HOLY_FIELDS)
  );
  private static final SimpleFeatureEntry CATASTROPHE_ORIGINIUM_SPIRE_LARGE = originiumSpire(
      "catastrophe_originium_spire_large", ORIGINIUM_SPIRE_LARGE_FEATURE, 6,
      BiomeSelection.of(NationBiomes.TERRA_CATASTROPHE_ZONE)
  );

  private ModWorldFeatures() {
  }

  private static OreEntry ore(String path, Supplier<? extends Block> block, int size, int count, int maxY, float discard) {
    return Zinecraft.WORLDGEN.getFeatures().ore(path, block, size, count, maxY, discard, MATERIAL_DIMENSIONS);
  }

  private static SimpleFeatureEntry originiumSpire(
      String path, OriginiumSpireFeature feature, int rarity, BiomeSelection biomes
  ) {
    return Zinecraft.WORLDGEN.getFeatures().simple(
        path, feature,
        List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, biomes
    );
  }

  private static SimpleFeatureEntry denseOriginiumSpire(String path, OriginiumSpireFeature feature, int count) {
    return Zinecraft.WORLDGEN.getFeatures().simple(
        path, feature,
        List.of(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, BiomeSelection.of(NationBiomes.TERRA_CATASTROPHE_ZONE)
    );
  }

}
