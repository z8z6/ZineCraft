package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 泰拉 Nation、City、Region 三级 Voronoi 边界计划。
 */
public record TerraLayoutPlan(
    List<PlanarPoint> boundary,
    List<NationLayoutPlan> nations
) {
  public TerraLayoutPlan {
    boundary = List.copyOf(Objects.requireNonNull(boundary, "泰拉地图边界不能为空"));
    nations = List.copyOf(Objects.requireNonNull(nations, "泰拉国家布局不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("泰拉地图边界至少需要三个点");
  }
}
