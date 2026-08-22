package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.Objects;

/** 一对相邻 Region Cell 在共享边界上的连通点。 */
public record CityRegionConnection(
    int neighboringSlotIndex,
    PlanarPoint point
) {
  public CityRegionConnection {
    if (neighboringSlotIndex < 0) throw new IllegalArgumentException("相邻 Region 槽位索引不能为负数");
    Objects.requireNonNull(point, "Region 连通点不能为空");
  }
}
