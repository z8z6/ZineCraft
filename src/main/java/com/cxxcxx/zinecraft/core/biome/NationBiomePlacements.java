package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NationBiomePlacements {
  @NotNull
  public static final NationBiomePlacements INSTANCE = new NationBiomePlacements();
  @NotNull
  private static final List<DimensionBiome> ALL;

  static {
    DimensionBiome[] dimensionBiomes = new DimensionBiome[]{
        INSTANCE.placement(NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA(), -0.35F, 0.65F, -1.05F, 0.35F, 0.0F, -0.75F),
        INSTANCE.placement(NationBiomes.INSTANCE.getBOLIVAR_PLAIN(), 0.55F, 0.0F, 0.1F, 0.55F, 0.0F, 0.0F),
        INSTANCE.placement(NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT(), -0.25F, 0.35F, 0.35F, -0.8F, 0.0F, -0.75F),
        INSTANCE.placement(NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN(), 0.35F, 0.65F, 0.55F, -0.4F, 1.0F, -0.75F),
        INSTANCE.placement(NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS(), 0.9F, -0.75F, 0.9F, -0.25F, 0.0F, 0.8F),
        INSTANCE.placement(NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND(), 0.0F, -0.35F, 0.35F, 0.55F, 0.0F, -0.2F),
        INSTANCE.placement(NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES(), 0.0F, -0.85F, 0.9F, -0.8F, 0.0F, -0.55F),
        INSTANCE.placement(NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(), 0.45F, 0.0F, 0.1F, 0.35F, 0.0F, 0.3F),
        INSTANCE.placement(NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST(), -0.3F, 0.45F, 0.35F, -0.4F, 0.0F, 0.55F),
        INSTANCE.placement(NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS(), 0.85F, -0.45F, 0.9F, -0.75F, 0.0F, 0.9F),
        INSTANCE.placement(NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS(), 0.45F, -0.25F, 0.1F, -0.2F, 0.0F, 0.55F),
        INSTANCE.placement(NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT(), 0.95F, -0.9F, 0.65F, 0.05F, 0.0F, -0.1F),
        INSTANCE.placement(NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST(), -0.85F, 0.4F, 0.85F, -0.45F, 0.0F, 0.0F),
        INSTANCE.placement(NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(), -0.2F, 0.7F, 0.4F, 0.0F, 0.0F, 0.65F),
        INSTANCE.placement(NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE(), -0.75F, -0.3F, 0.65F, 0.5F, 0.0F, -0.25F),
        INSTANCE.placement(NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS(), -0.95F, 0.35F, 0.95F, -0.85F, 0.0F, 0.95F),
        INSTANCE.placement(NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND(), 0.05F, 0.75F, 0.15F, 0.35F, 0.0F, -0.65F),
        INSTANCE.placement(NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE(), 0.05F, 0.4F, 0.45F, -0.2F, 0.0F, 0.8F),
        INSTANCE.placement(NationBiomes.INSTANCE.getIBERIA_SALT_DELTA(), 0.5F, 0.65F, -0.2F, 0.8F, 0.0F, -0.75F),
        INSTANCE.placement(NationBiomes.INSTANCE.getTERRA_CATASTROPHE_ZONE(), 0.8F, -0.8F, 0.5F, -0.5F, 0.0F, 0.2F)
    };
    ALL = java.util.List.of(dimensionBiomes);
  }

  private NationBiomePlacements() {
  }

  @NotNull
  public final List<DimensionBiome> getALL() {
    return ALL;
  }

  private final DimensionBiome placement(
      ResourceKey<Biome> biome, float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness
  ) {
    ParameterPoint parameterPoint = Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, 0.0F);
    return new DimensionBiome(biome, parameterPoint);
  }
}
