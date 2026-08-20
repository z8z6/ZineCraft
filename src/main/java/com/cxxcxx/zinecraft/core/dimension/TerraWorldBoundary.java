package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.level.LevelEvent;

/**
 * 将泰拉维度限制为以原点为中心的 100000 × 100000 格地图。
 */
public final class TerraWorldBoundary {
  private TerraWorldBoundary() {
  }

  public static void onLevelLoad(LevelEvent.Load event) {
    if (!(event.getLevel() instanceof ServerLevel level)
        || !level.dimension().equals(ModDimension.TERRA.levelKey())) return;

    var border = level.getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(ModDimension.TERRA_MAP_SIZE);
  }
}
