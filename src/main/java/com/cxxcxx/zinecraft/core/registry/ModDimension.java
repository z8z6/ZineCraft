package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.DimensionBuilder;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import com.cxxcxx.zinecraft.api.world.dimension.TerraBiomeSource;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

public final class ModDimension {
  public static final int LATERANO_CENTER_RADIUS = 1024;

  public static final MapCodec<TerraBiomeSource> TERRA_BIOME_SOURCE = Zinecraft.DIMENSIONS
      .biomeSource("terra", TerraBiomeSource.ACCESS.getCODEC());

  public static final DimensionBuilder TERRA = Zinecraft.DIMENSIONS.dimension("terra")
      .biomes(ModBiome.ALL.stream()
          .filter(builder -> builder != ModBiome.LATERANO_HOLY_FIELDS)
          .map(builder -> new DimensionBiome(builder.key(), builder.climate()))
          .toList())
      .generator(ModDimension::createTerraGenerator)
      .build();

  static {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra");
  }

  private ModDimension() {
  }

  private static ChunkGenerator createTerraGenerator(DimensionBootstrapContext context) {
    return new NoiseBasedChunkGenerator(
        new TerraBiomeSource(
            context.biomeParameters(),
            context.biomes().getOrThrow(ModBiome.LATERANO_HOLY_FIELDS.key()),
            LATERANO_CENTER_RADIUS
        ),
        context.noiseSettings()
    );
  }

  public static void bootstrap() {
  }
}
