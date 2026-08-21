package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 一个 Region slot 的世界坐标及其 Voronoi 边界。
 */
public record CityRegionCell(
    LayoutSlot slot,
    TerraCityRegionBuilder region,
    PlanarPoint center,
    List<PlanarPoint> boundary
) {
  public CityRegionCell {
    Objects.requireNonNull(slot, "Region slot 不能为空");
    Objects.requireNonNull(region, "Region 不能为空");
    Objects.requireNonNull(center, "Region 世界中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "Region 边界不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("Region 边界至少需要三个点");
  }
}
