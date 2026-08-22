package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.Objects;

/** Region 移动地块内一个已分配建筑类型的规划槽位。 */
public record CityRegionBuildingSlot(
    LayoutSlot slot,
    PlanarPoint center,
    JigsawBuilder building
) {
  public CityRegionBuildingSlot {
    Objects.requireNonNull(slot, "建筑 slot 不能为空");
    Objects.requireNonNull(center, "建筑 slot 世界坐标不能为空");
    Objects.requireNonNull(building, "建筑 slot 建筑不能为空");
  }
}
