package com.cxxcxx.zinecraft.api.nation;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.layout.WeightedLayoutElement;

import java.util.Objects;

/**
 * 一个城区内合法建筑的生成权重和唯一性声明。
 */
public record TerraCityRegionBuilding(JigsawBuilder building, int weight, boolean unique)
    implements WeightedLayoutElement {
  public TerraCityRegionBuilding {
    Objects.requireNonNull(building, "城区合法建筑不能为空");
    if (weight <= 0) throw new IllegalArgumentException("城区合法建筑权重必须为正数：" + building.path);
  }

  public static TerraCityRegionBuilding repeatable(JigsawBuilder building) {
    return new TerraCityRegionBuilding(building, 1, false);
  }

  @Override
  public boolean isUnique() {
    return unique;
  }
}
