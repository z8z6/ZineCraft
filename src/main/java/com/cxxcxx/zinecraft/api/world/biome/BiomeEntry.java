package com.cxxcxx.zinecraft.api.world.biome;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BiomeEntry {
  @NotNull
  private final ResourceKey<Biome> key;
  @NotNull
  private final Function1<SimpleBiomeBuilder, Unit> build;

  public BiomeEntry(@NotNull ResourceKey<Biome> key, @NotNull Function1<? super SimpleBiomeBuilder, Unit> build) {
    super();
    this.key = key;
    this.build = builder -> build.invoke(builder);
  }

  // $VF: synthetic method
  public static BiomeEntry copy$default(BiomeEntry var0, ResourceKey var1, Function1 var2, int var3, Object var4) {
    if ((var3 & 1) != 0) {
      var1 = var0.key;
    }

    if ((var3 & 2) != 0) {
      var2 = var0.build;
    }

    return var0.copy(var1, var2);
  }

  @NotNull
  public final ResourceKey<Biome> getKey() {
    return this.key;
  }

  @NotNull
  public final Function1<SimpleBiomeBuilder, Unit> getBuild() {
    return this.build;
  }

  @NotNull
  public final ResourceKey<Biome> component1() {
    return this.key;
  }

  @NotNull
  public final Function1<SimpleBiomeBuilder, Unit> component2() {
    return this.build;
  }

  @NotNull
  public final BiomeEntry copy(@NotNull ResourceKey<Biome> key, @NotNull Function1<? super SimpleBiomeBuilder, Unit> build) {
    return new BiomeEntry(key, build);
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

