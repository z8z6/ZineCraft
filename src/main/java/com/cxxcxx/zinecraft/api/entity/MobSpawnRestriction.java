package com.cxxcxx.zinecraft.api.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MobSpawnRestriction<T extends Mob> {
  @NotNull
  private final SpawnPlacementType placement;
  @NotNull
  private final Types heightmap;
  @NotNull
  private final SpawnPredicate<T> predicate;

  public MobSpawnRestriction(@NotNull SpawnPlacementType placement, @NotNull Types heightmap, @NotNull SpawnPredicate<T> predicate) {
    super();
    this.placement = placement;
    this.heightmap = heightmap;
    this.predicate = predicate;
  }

  @NotNull
  public final SpawnPlacementType getPlacement() {
    return this.placement;
  }

  @NotNull
  public final Types getHeightmap() {
    return this.heightmap;
  }

  @NotNull
  public final SpawnPredicate<T> getPredicate() {
    return this.predicate;
  }

  @Override
  public int hashCode() {
    int i = this.placement.hashCode();
    i = i * 31 + this.heightmap.hashCode();
    return i * 31 + this.predicate.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof MobSpawnRestriction mobSpawnRestriction)) {
      return false;
    } else if (!java.util.Objects.equals(this.placement, mobSpawnRestriction.placement)) {
      return false;
    } else {
      return this.heightmap != mobSpawnRestriction.heightmap ? false : java.util.Objects.equals(this.predicate, mobSpawnRestriction.predicate);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "MobSpawnRestriction(placement=" + this.placement + ", heightmap=" + this.heightmap + ", predicate=" + this.predicate + ")";
  }
}

