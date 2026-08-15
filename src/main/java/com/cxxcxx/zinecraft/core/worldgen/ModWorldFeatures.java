package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.api.world.feature.SimpleFeatureEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.cxxcxx.zinecraft.core.block.MaterialOres;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.dimension.StarGateFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

public final class ModWorldFeatures {
  public static final ModWorldFeatures INSTANCE = new ModWorldFeatures();

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
          NationBiomes.INSTANCE.getIBERIA_SALT_DELTA()
      )
  );

  private static final StarGateFeature STARGATE_FEATURE = Zinecraft.INSTANCE.getREGISTRAR().register(
      BuiltInRegistries.FEATURE, "stargate", new StarGateFeature()
  );
  private static final SimpleFeatureEntry STARGATE = Zinecraft.INSTANCE.getFEATURES().simple(
      "stargate", STARGATE_FEATURE,
      List.of(RarityFilter.onAverageOnceEvery(64), InSquarePlacement.spread(),
          HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES), BiomeFilter.biome()),
      GenerationStep.Decoration.SURFACE_STRUCTURES, BiomeSelection.of(Biomes.SNOWY_PLAINS)
  );
  private static final OreEntry EXAMPLE_BLOCK_ORE = Zinecraft.INSTANCE.getFEATURES().ore(
      "example_block_ore_placed", ModBlock.INSTANCE.getEXAMPLE_ENTITY_BLOCK(), 30, 6, 0, 0, BiomeSelection.overworld());
  private static final OreEntry ORIGINITE_ORE = ore("originite_ore", MaterialOres.INSTANCE.getORIGINITE_ORE().getBlock(), 3, 2, -32, .25f);
  private static final OreEntry ORIROCK_ORE = ore("orirock_ore", MaterialOres.INSTANCE.getORIROCK_ORE().getBlock(), 10, 12, 64, 0);
  private static final OreEntry ORIRON_ORE = ore("oriron_ore", MaterialOres.INSTANCE.getORIRON_ORE().getBlock(), 7, 8, 32, 0);
  private static final OreEntry MANGANESE_ORE = ore("manganese_ore", MaterialOres.INSTANCE.getMANGANESE_ORE().getBlock(), 5, 6, 16, .1f);
  private static final OreEntry GRINDSTONE_ORE = ore("grindstone_ore", MaterialOres.INSTANCE.getGRINDSTONE_ORE().getBlock(), 6, 5, 0, .15f);
  private static final OreEntry RMA70_ORE = ore("rma70_ore", MaterialOres.INSTANCE.getRMA70_ORE().getBlock(), 4, 3, -32, .25f);
  private static final OreEntry CRYSTAL_ELEMENT_ORE = ore("crystal_element_ore", MaterialOres.INSTANCE.getCRYSTAL_ELEMENT_ORE().getBlock(), 5, 4, 16, .15f);
  private static final OreEntry LOXIC_KOHL_ORE = ore("loxic_kohl_ore", MaterialOres.INSTANCE.getLOXIC_KOHL_ORE().getBlock(), 4, 3, -16, .2f);

  private ModWorldFeatures() {
  }

  private static OreEntry ore(String path, net.minecraft.world.level.block.Block block, int size, int count, int maxY, float discard) {
    return Zinecraft.INSTANCE.getFEATURES().ore(path, block, size, count, maxY, discard, MATERIAL_DIMENSIONS);
  }

  public SimpleFeatureEntry getSTARGATE() {
    return STARGATE;
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
