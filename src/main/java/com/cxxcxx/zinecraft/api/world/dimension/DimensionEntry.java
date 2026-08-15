package com.cxxcxx.zinecraft.api.world.dimension;

import java.util.List;

import kotlin.jvm.functions.Function1;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
  private final Function1<DimensionBootstrapContext, ChunkGenerator> createGenerator;

  public DimensionEntry(
      @NotNull String path,
      @NotNull ResourceKey<Level> levelKey,
      @NotNull ResourceKey<LevelStem> stemKey,
      @NotNull ResourceKey<DimensionType> typeKey,
      @NotNull ResourceKey<NoiseGeneratorSettings> noiseSettingsKey,
      @NotNull List<DimensionBiome> biomes,
      @Nullable Function1<? super DimensionBootstrapContext, ? extends ChunkGenerator> createGenerator
  ) {
    super();
    this.path = path;
    this.levelKey = levelKey;
    this.stemKey = stemKey;
    this.typeKey = typeKey;
    this.noiseSettingsKey = noiseSettingsKey;
    this.biomes = new java.util.ArrayList<>(biomes);
    this.createGenerator = createGenerator == null ? null : context -> createGenerator.invoke(context);
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final ResourceKey<Level> getLevelKey() {
    return this.levelKey;
  }

  @NotNull
  public final ResourceKey<LevelStem> getStemKey() {
    return this.stemKey;
  }

  @NotNull
  public final ResourceKey<DimensionType> getTypeKey() {
    return this.typeKey;
  }

  @NotNull
  public final ResourceKey<NoiseGeneratorSettings> getNoiseSettingsKey() {
    return this.noiseSettingsKey;
  }

  @NotNull
  public final List<DimensionBiome> getBiomes$zinecraft() {
    return this.biomes;
  }

  @Nullable
  public final Function1<DimensionBootstrapContext, ChunkGenerator> getCreateGenerator$zinecraft() {
    return this.createGenerator;
  }
}

