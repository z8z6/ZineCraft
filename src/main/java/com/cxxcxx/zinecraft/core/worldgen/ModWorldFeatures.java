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

  private static final BiomeSelection TERRA_BIOMES = BiomeSelection.of(
      NationBiomePlacements.INSTANCE.getALL().stream().map(placement -> placement.getBiome()).toArray(ResourceKey[]::new)
  );

  private static final BiomeSelection MATERIAL_DIMENSIONS = BiomeSelection.union(
      BiomeSelection.overworld(),
      BiomeSelection.of(
          NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA(), NationBiomes.INSTANCE.getBOLIVAR_PLAIN(),
          NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT(), NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN(),
          NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS(), NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND(),
          NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES(), NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(),
          NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST(), NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS(),
          NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS(), NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT(),
          NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST(), NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(),
          NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE(), NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS(),
          NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND(), NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE(),
          NationBiomes.INSTANCE.getIBERIA_SALT_DELTA(), NationBiomes.INSTANCE.getTERRA_CATASTROPHE_ZONE()
      )
  );

  private static final LateranoDryLandFeature LATERANO_DRY_LAND_FEATURE = Zinecraft.INSTANCE.getREGISTRAR().register(
      BuiltInRegistries.FEATURE, "laterano_dry_land", new LateranoDryLandFeature()
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_SMALL_FEATURE = Zinecraft.INSTANCE.getREGISTRAR().register(
      BuiltInRegistries.FEATURE, "originium_spire_small", new OriginiumSpireFeature(2, 4, 4, 8, 1, 4)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_MEDIUM_FEATURE = Zinecraft.INSTANCE.getREGISTRAR().register(
      BuiltInRegistries.FEATURE, "originium_spire_medium", new OriginiumSpireFeature(4, 7, 8, 15, 2, 7)
  );
  private static final OriginiumSpireFeature ORIGINIUM_SPIRE_LARGE_FEATURE = Zinecraft.INSTANCE.getREGISTRAR().register(
      BuiltInRegistries.FEATURE, "originium_spire_large", new OriginiumSpireFeature(7, 11, 14, 28, 3, 12)
  );
  private static final SimpleFeatureEntry LATERANO_DRY_LAND = Zinecraft.INSTANCE.getFEATURES().simple(
      "laterano_dry_land", LATERANO_DRY_LAND_FEATURE,
      List.of(CountPlacement.of(1), InSquarePlacement.spread(), BiomeFilter.biome()),
      GenerationStep.Decoration.TOP_LAYER_MODIFICATION, BiomeSelection.of(NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS())
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
  private static final SimpleFeatureEntry CATASTROPHE_ORIGINIUM_SPIRE_LARGE = originiumSpire(
      "catastrophe_originium_spire_large", ORIGINIUM_SPIRE_LARGE_FEATURE, 6,
      BiomeSelection.of(NationBiomes.INSTANCE.getTERRA_CATASTROPHE_ZONE())
  );
  private static final OreEntry EXAMPLE_BLOCK_ORE = Zinecraft.INSTANCE.getFEATURES().ore(
      "example_block_ore_placed", ModBlock.INSTANCE::getEXAMPLE_ENTITY_BLOCK, 30, 6, 0, 0, BiomeSelection.overworld());
  private static final OreEntry ORIGINITE_ORE = ore("originite_ore", MaterialOres.INSTANCE.getORIGINITE_ORE()::getBlock, 3, 2, -32, .25f);
  private static final OreEntry ORIROCK_ORE = ore("orirock_ore", MaterialOres.INSTANCE.getORIROCK_ORE()::getBlock, 10, 12, 64, 0);
  private static final OreEntry ORIRON_ORE = ore("oriron_ore", MaterialOres.INSTANCE.getORIRON_ORE()::getBlock, 7, 8, 32, 0);
  private static final OreEntry MANGANESE_ORE = ore("manganese_ore", MaterialOres.INSTANCE.getMANGANESE_ORE()::getBlock, 5, 6, 16, .1f);
  private static final OreEntry GRINDSTONE_ORE = ore("grindstone_ore", MaterialOres.INSTANCE.getGRINDSTONE_ORE()::getBlock, 6, 5, 0, .15f);
  private static final OreEntry RMA70_ORE = ore("rma70_ore", MaterialOres.INSTANCE.getRMA70_ORE()::getBlock, 4, 3, -32, .25f);
  private static final OreEntry CRYSTAL_ELEMENT_ORE = ore("crystal_element_ore", MaterialOres.INSTANCE.getCRYSTAL_ELEMENT_ORE()::getBlock, 5, 4, 16, .15f);
  private static final OreEntry LOXIC_KOHL_ORE = ore("loxic_kohl_ore", MaterialOres.INSTANCE.getLOXIC_KOHL_ORE()::getBlock, 4, 3, -16, .2f);

  private ModWorldFeatures() {
  }

  private static OreEntry ore(String path, Supplier<? extends Block> block, int size, int count, int maxY, float discard) {
    return Zinecraft.INSTANCE.getFEATURES().ore(path, block, size, count, maxY, discard, MATERIAL_DIMENSIONS);
  }

  private static SimpleFeatureEntry originiumSpire(
      String path, OriginiumSpireFeature feature, int rarity, BiomeSelection biomes
  ) {
    return Zinecraft.INSTANCE.getFEATURES().simple(
        path, feature,
        List.of(RarityFilter.onAverageOnceEvery(rarity), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, biomes
    );
  }

  private static SimpleFeatureEntry denseOriginiumSpire(String path, OriginiumSpireFeature feature, int count) {
    return Zinecraft.INSTANCE.getFEATURES().simple(
        path, feature,
        List.of(CountPlacement.of(count), InSquarePlacement.spread(), BiomeFilter.biome()),
        GenerationStep.Decoration.LOCAL_MODIFICATIONS, BiomeSelection.of(NationBiomes.INSTANCE.getTERRA_CATASTROPHE_ZONE())
    );
  }

  public OreEntry getEXAMPLE_BLOCK_ORE() {
    return EXAMPLE_BLOCK_ORE;
  }

  public OreEntry getORIGINITE_ORE() {
    return ORIGINITE_ORE;
  }

  public OreEntry getORIROCK_ORE() {
    return ORIROCK_ORE;
  }

  public OreEntry getORIRON_ORE() {
    return ORIRON_ORE;
  }

  public OreEntry getMANGANESE_ORE() {
    return MANGANESE_ORE;
  }

  public OreEntry getGRINDSTONE_ORE() {
    return GRINDSTONE_ORE;
  }

  public OreEntry getRMA70_ORE() {
    return RMA70_ORE;
  }

  public OreEntry getCRYSTAL_ELEMENT_ORE() {
    return CRYSTAL_ELEMENT_ORE;
  }

  public OreEntry getLOXIC_KOHL_ORE() {
    return LOXIC_KOHL_ORE;
  }
}
