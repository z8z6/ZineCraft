package com.cxxcxx.zinecraft.api.entity;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record MobSpawnRestriction<T extends Mob>(@NotNull SpawnPlacementType placement, @NotNull Types heightmap,
                                                 @NotNull SpawnPredicate<T> predicate) {

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof MobSpawnRestriction(
        SpawnPlacementType placement1, Types heightmap1, SpawnPredicate predicate1
    ))) {
      return false;
    } else if (!Objects.equals(this.placement, placement1)) {
      return false;
    } else {
      return this.heightmap == heightmap1 && Objects.equals(this.predicate, predicate1);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "MobSpawnRestriction(placement=" + this.placement + ", heightmap=" + this.heightmap + ", predicate=" + this.predicate + ")";
  }
}

