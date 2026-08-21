package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 一座城市完成 Region slot 分配和 Voronoi 切分后的纯数据计划。
 */
public record CityLayoutPlan(
    NationBuilder nation,
    TerraCityBuilder city,
    PlanarPoint center,
    List<PlanarPoint> boundary,
    List<CityRegionCell> regions
) {
  public CityLayoutPlan {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(center, "城市世界中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "城市边界不能为空"));
    regions = List.copyOf(Objects.requireNonNull(regions, "城市 Region 计划不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("城市边界至少需要三个点");
  }
}
