package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 一个国家的 Voronoi 边界及其城市划分结果。
 */
public record NationLayoutPlan(
    NationBuilder nation,
    PlanarPoint center,
    List<PlanarPoint> boundary,
    List<CityLayoutPlan> cities
) {
  public NationLayoutPlan {
    Objects.requireNonNull(nation, "国家不能为空");
    Objects.requireNonNull(center, "国家中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "国家边界不能为空"));
    cities = List.copyOf(Objects.requireNonNull(cities, "国家城市布局不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("国家边界至少需要三个点");
  }
}
