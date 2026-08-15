package com.cxxcxx.zinecraft.api.world.dimension;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.NotNull;

public final class DimensionBootstrapContext {
  @NotNull
  private final DimensionEntry entry;
  @NotNull
  private final MultiNoiseBiomeSource biomeSource;
  @NotNull
  private final ParameterList<Holder<Biome>> biomeParameters;
  @NotNull
  private final HolderGetter<Biome> biomes;
  @NotNull
  private final Holder<NoiseGeneratorSettings> noiseSettings;

  public DimensionBootstrapContext(
      @NotNull DimensionEntry entry,
      @NotNull MultiNoiseBiomeSource biomeSource,
      @NotNull ParameterList<Holder<Biome>> biomeParameters,
      @NotNull HolderGetter<Biome> biomes,
      @NotNull Holder<NoiseGeneratorSettings> noiseSettings
  ) {
    super();
    this.entry = entry;
    this.biomeSource = biomeSource;
    this.biomeParameters = biomeParameters;
    this.biomes = biomes;
    this.noiseSettings = noiseSettings;
  }

  @NotNull
  public final DimensionEntry getEntry() {
    return this.entry;
  }

  @NotNull
  public final MultiNoiseBiomeSource getBiomeSource() {
    return this.biomeSource;
  }

  @NotNull
  public final ParameterList<Holder<Biome>> getBiomeParameters() {
    return this.biomeParameters;
  }

  @NotNull
  public final HolderGetter<Biome> getBiomes() {
    return this.biomes;
  }

  @NotNull
  public final Holder<NoiseGeneratorSettings> getNoiseSettings() {
    return this.noiseSettings;
  }
}

