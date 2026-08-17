package com.cxxcxx.zinecraft.api.world.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public final class DimensionEntry {
  @NotNull
  private final String path;
  @NotNull
  private final ResourceKey<Level> levelKey;
  @NotNull
  private final ResourceKey<LevelStem> stemKey;
  @NotNull
  private final ResourceKey<DimensionType> typeKey;
  @NotNull
  private final ResourceKey<NoiseGeneratorSettings> noiseSettingsKey;
  @NotNull
  private final List<DimensionBiome> biomes;
  @Nullable
  private final Function<DimensionBootstrapContext, ChunkGenerator> createGenerator;

  public DimensionEntry(
      String path,
      ResourceKey<Level> levelKey,
      ResourceKey<LevelStem> stemKey,
      ResourceKey<DimensionType> typeKey,
      ResourceKey<NoiseGeneratorSettings> noiseSettingsKey,
      List<DimensionBiome> biomes,
      @Nullable Function<? super DimensionBootstrapContext, ? extends ChunkGenerator> createGenerator
  ) {
    super();
    this.path = path;
    this.levelKey = levelKey;
    this.stemKey = stemKey;
    this.typeKey = typeKey;
    this.noiseSettingsKey = noiseSettingsKey;
    this.biomes = new java.util.ArrayList<>(biomes);
    this.createGenerator = createGenerator == null ? null : context -> createGenerator.apply(context);
  }

  @NotNull
  public String getPath() {
    return this.path;
  }

  @NotNull
  public ResourceKey<Level> getLevelKey() {
    return this.levelKey;
  }

  @NotNull
  public ResourceKey<LevelStem> getStemKey() {
    return this.stemKey;
  }

  @NotNull
  public ResourceKey<DimensionType> getTypeKey() {
    return this.typeKey;
  }

  @NotNull
  public ResourceKey<NoiseGeneratorSettings> getNoiseSettingsKey() {
    return this.noiseSettingsKey;
  }

  @NotNull
  public List<DimensionBiome> getBiomes() {
    return this.biomes;
  }

  @Nullable
  public Function<DimensionBootstrapContext, ChunkGenerator> getCreateGenerator() {
    return this.createGenerator;
  }
}

