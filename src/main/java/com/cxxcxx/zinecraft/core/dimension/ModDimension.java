package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionEntry;
import com.cxxcxx.zinecraft.api.world.dimension.TerraBiomeSource;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

public final class ModDimension {
  public static final int LATERANO_CENTER_RADIUS = 1024;

  public static final MapCodec<TerraBiomeSource> TERRA_BIOME_SOURCE = Zinecraft.REGISTRAR
      .biomeSource("terra", TerraBiomeSource.ACCESS.getCODEC());

  public static final DimensionEntry TERRA = Zinecraft.WORLDGEN.dimensions.register(
      "terra",
      NationBiomePlacements.ALL.stream()
          .filter(placement -> !placement.getBiome().equals(ModBiome.LATERANO_HOLY_FIELDS))
          .toList(),
      ModDimension::createTerraGenerator
  );

  static {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra");
  }

  private ModDimension() {
  }

  private static ChunkGenerator createTerraGenerator(DimensionBootstrapContext context) {
    return new NoiseBasedChunkGenerator(
        new TerraBiomeSource(
            context.getBiomeParameters(),
            context.getBiomes().getOrThrow(ModBiome.LATERANO_HOLY_FIELDS),
            LATERANO_CENTER_RADIUS
        ),
        context.getNoiseSettings()
    );
  }

  public static void bootstrap() {
  }
}
