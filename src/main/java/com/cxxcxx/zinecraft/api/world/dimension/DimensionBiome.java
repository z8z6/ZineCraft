package com.cxxcxx.zinecraft.api.world.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DimensionBiome {
  @NotNull
  private final ResourceKey<Biome> biome;
  @NotNull
  private final ParameterPoint parameters;

  public DimensionBiome(@NotNull ResourceKey<Biome> biome, @NotNull ParameterPoint parameters) {
    super();
    this.biome = biome;
    this.parameters = parameters;
  }

  @NotNull
  public final ResourceKey<Biome> getBiome() {
    return this.biome;
  }

  @NotNull
  public final ParameterPoint getParameters() {
    return this.parameters;
  }

  @Override
  public int hashCode() {
    int i = this.biome.hashCode();
    return i * 31 + this.parameters.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof DimensionBiome dimensionBiome)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.biome, dimensionBiome.biome) ? false : java.util.Objects.equals(this.parameters, dimensionBiome.parameters);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "DimensionBiome(biome=" + this.biome + ", parameters=" + this.parameters + ")";
  }
}

