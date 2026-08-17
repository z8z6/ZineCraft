package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

import java.util.List;

public final class NationBiomePlacements {
  public static final NationBiomePlacements INSTANCE = new NationBiomePlacements();
  public static final List<DimensionBiome> ALL;

  static {
    DimensionBiome[] dimensionBiomes = new DimensionBiome[]{
        INSTANCE.placement(ModBiome.AEGIR_ABYSSAL_SEA, -0.35F, 0.65F, -1.05F, 0.35F, 0.0F, -0.75F),
        INSTANCE.placement(ModBiome.BOLIVAR_PLAIN, 0.55F, 0.0F, 0.1F, 0.55F, 0.0F, 0.0F),
        INSTANCE.placement(ModBiome.HIGASHI_SHADOW_RIFT, -0.25F, 0.35F, 0.35F, -0.8F, 0.0F, -0.75F),
        INSTANCE.placement(ModBiome.DURIN_UNDERGROUND_GARDEN, 0.35F, 0.65F, 0.55F, -0.4F, 1.0F, -0.75F),
        INSTANCE.placement(ModBiome.COLUMBIA_SANDSTONE_WILDS, 0.9F, -0.75F, 0.9F, -0.25F, 0.0F, 0.8F),
        INSTANCE.placement(ModBiome.KAZIMIERZ_KNIGHTLAND, 0.0F, -0.35F, 0.35F, 0.55F, 0.0F, -0.2F),
        INSTANCE.placement(ModBiome.KAZDEL_SCARRED_WASTES, 0.0F, -0.85F, 0.9F, -0.8F, 0.0F, -0.55F),
        INSTANCE.placement(ModBiome.LATERANO_HOLY_FIELDS, 0.45F, 0.0F, 0.1F, 0.35F, 0.0F, 0.3F),
        INSTANCE.placement(ModBiome.LEITHANIEN_TWILIGHT_FOREST, -0.3F, 0.45F, 0.35F, -0.4F, 0.0F, 0.55F),
        INSTANCE.placement(ModBiome.RIM_BILLITON_MINING_BADLANDS, 0.85F, -0.45F, 0.9F, -0.75F, 0.0F, 0.9F),
        INSTANCE.placement(ModBiome.MINOS_SUNLIT_HILLS, 0.45F, -0.25F, 0.1F, -0.2F, 0.0F, 0.55F),
        INSTANCE.placement(ModBiome.SARGON_ROCKY_DESERT, 0.95F, -0.9F, 0.65F, 0.05F, 0.0F, -0.1F),
        INSTANCE.placement(ModBiome.SAMI_FROZEN_FOREST, -0.85F, 0.4F, 0.85F, -0.45F, 0.0F, 0.0F),
        INSTANCE.placement(ModBiome.VICTORIA_MISTY_HIGHLANDS, -0.2F, 0.7F, 0.4F, 0.0F, 0.0F, 0.65F),
        INSTANCE.placement(ModBiome.URSUS_FROZEN_STEPPE, -0.75F, -0.3F, 0.65F, 0.5F, 0.0F, -0.25F),
        INSTANCE.placement(ModBiome.KJERAG_SNOWY_PEAKS, -0.95F, 0.35F, 0.95F, -0.85F, 0.0F, 0.95F),
        INSTANCE.placement(ModBiome.SIRACUSA_RAINY_WOODLAND, 0.05F, 0.75F, 0.15F, 0.35F, 0.0F, -0.65F),
        INSTANCE.placement(ModBiome.YAN_MOUNTAIN_GROVE, 0.05F, 0.4F, 0.45F, -0.2F, 0.0F, 0.8F),
        INSTANCE.placement(ModBiome.IBERIA_SALT_DELTA, 0.5F, 0.65F, -0.2F, 0.8F, 0.0F, -0.75F),
        INSTANCE.placement(ModBiome.TERRA_CATASTROPHE_ZONE, 0.8F, -0.8F, 0.5F, -0.5F, 0.0F, 0.2F)
    };
    ALL = java.util.List.of(dimensionBiomes);
  }

  private DimensionBiome placement(
      ResourceKey<Biome> biome, float temperature, float humidity, float continentalness, float erosion, float depth, float weirdness
  ) {
    ParameterPoint parameterPoint = Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, 0.0F);
    return new DimensionBiome(biome, parameterPoint);
  }
}
