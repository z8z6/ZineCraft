package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.api.nation.NationAffiliated;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

/**
 * Keeps Terra populated by passive mobs and national residents only.
 */
public final class TerraMobSpawnPolicy {
  private TerraMobSpawnPolicy() {
  }

  public static void onFinalizeSpawn(FinalizeSpawnEvent event) {
    if (!event.getLevel().getLevel().dimension().equals(ModDimension.TERRA.getLevelKey())) {
      return;
    }

    if (!allowsSpawn(
        event.getEntity().getType().getCategory().isFriendly(),
        event.getEntity() instanceof NationAffiliated,
        event.getSpawnType() == MobSpawnType.NATURAL)) {
      event.setSpawnCancelled(true);
    }
  }

  public static boolean allowsSpawn(boolean friendlyCategory, boolean nationResident, boolean naturalSpawn) {
    return !naturalSpawn || nationResident || friendlyCategory;
  }
}
