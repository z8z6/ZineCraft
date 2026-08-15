package com.cxxcxx.zinecraft.api.world.biome;


import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

final class BiomeEntry {
  @NotNull
  private final ResourceKey<Biome> key;
  @NotNull
  private final Consumer<SimpleBiomeBuilder> build;

  public BiomeEntry(@NotNull ResourceKey<Biome> key, @NotNull Consumer<? super SimpleBiomeBuilder> build) {
    super();
    this.key = key;
    this.build = builder -> build.accept(builder);
  }

  @NotNull
  public final ResourceKey<Biome> getKey() {
    return this.key;
  }

  @NotNull
  public final Consumer<SimpleBiomeBuilder> getBuild() {
    return this.build;
  }

  @Override
  public int hashCode() {
    int i = this.key.hashCode();
    return i * 31 + this.build.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof BiomeEntry biomeEntry)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.key, biomeEntry.key) ? false : java.util.Objects.equals(this.build, biomeEntry.build);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "BiomeEntry(key=" + this.key + ", build=" + this.build + ")";
  }
}

