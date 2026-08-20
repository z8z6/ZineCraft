package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

/**
 * Keeps Terra's natural population limited to friendly mobs declared by its biomes.
 */
public final class TerraMobSpawnPolicy {
  private TerraMobSpawnPolicy() {
  }

  public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
    if (!event.getLevel().getLevel().dimension().equals(ModDimension.TERRA.levelKey())) {
      return;
    }

    if (!allowsSpawn(
        event.getEntity().getType().getCategory().isFriendly(),
        event.getSpawnType() == MobSpawnType.NATURAL)) {
      event.setSpawnCancelled(true);
    }
  }

  public static boolean allowsSpawn(boolean friendlyCategory, boolean naturalSpawn) {
    return !naturalSpawn || friendlyCategory;
  }
}
