package com.cxxcxx.zinecraft.api.world.structure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class JigsawBuildingDefinition {
  @NotNull
  private final String startPool;
  @NotNull
  private final List<JigsawPoolDefinition> pools;

  public JigsawBuildingDefinition(@NotNull String startPool, @NotNull List<JigsawPoolDefinition> pools) {
    super();
    this.startPool = startPool;
    this.pools = pools;
  }

  @NotNull
  public final String getStartPool() {
    return this.startPool;
  }

  @NotNull
  public final List<JigsawPoolDefinition> getPools() {
    return this.pools;
  }

  @Override
  public int hashCode() {
    int i = this.startPool.hashCode();
    return i * 31 + this.pools.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof JigsawBuildingDefinition jigsawBuildingDefinition)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.startPool, jigsawBuildingDefinition.startPool)
          ? false
          : java.util.Objects.equals(this.pools, jigsawBuildingDefinition.pools);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "JigsawBuildingDefinition(startPool=" + this.startPool + ", pools=" + this.pools + ")";
  }
}

