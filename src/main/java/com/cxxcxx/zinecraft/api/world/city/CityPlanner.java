package com.cxxcxx.zinecraft.api.world.city;

import java.util.Objects;

/**
 * 只产生二维/2.5D 数据的城市规划器；实现不得读取或强制加载远端区块。
 */
@FunctionalInterface
public interface CityPlanner {
  CityPlan plan(Context context);

  /**
   * 由噪声或预计算数据提供，不能包装会加载区块的 ServerLevel 查询。
   */
  @FunctionalInterface
  interface TerrainModel {
    TerrainCell sample(int cityRelativeX, int cityRelativeZ);
  }

  record Context(CityDefinition definition, long worldSeed, TerrainModel terrain) {
    public Context {
      Objects.requireNonNull(definition, "城市定义不能为空");
      Objects.requireNonNull(terrain, "城市地形模型不能为空");
    }
  }

  record TerrainCell(int surfaceY, int slopeCost, int waterCost, boolean buildable) {
    public TerrainCell {
      if (slopeCost < 0 || waterCost < 0) throw new IllegalArgumentException("地形代价不能为负数");
    }
  }
}
