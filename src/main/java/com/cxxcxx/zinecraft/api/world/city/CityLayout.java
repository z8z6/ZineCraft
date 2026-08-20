package com.cxxcxx.zinecraft.api.world.city;

import java.util.List;
import java.util.Objects;

/**
 * 城市内部布局策略；只规划道路切分后的合法普通建筑地块，不生成建筑内容。
 */
@FunctionalInterface
public interface CityLayout {
  List<CityBuildingLot> createBuildingLots(Context context);

  record Context(
      CityRect bounds,
      List<CityRect> reservedAreas,
      List<CityDefinition.DistrictDefinition> districts,
      CityRoadClass roadClass,
      CityPlanner.TerrainModel terrain
  ) {
    public Context {
      Objects.requireNonNull(bounds, "布局范围不能为空");
      reservedAreas = List.copyOf(Objects.requireNonNull(reservedAreas, "布局保留区不能为空"));
      districts = List.copyOf(Objects.requireNonNull(districts, "布局城区不能为空"));
      Objects.requireNonNull(roadClass, "布局道路等级不能为空");
      Objects.requireNonNull(terrain, "布局地形模型不能为空");
      if (districts.isEmpty()) throw new IllegalArgumentException("布局至少需要一个城区");
      for (CityRect reservedArea : reservedAreas) {
        if (!bounds.contains(reservedArea)) throw new IllegalArgumentException("布局保留区越出城市范围");
      }
      for (CityDefinition.DistrictDefinition district : districts) {
        if (!bounds.contains(district.bounds())) throw new IllegalArgumentException("布局城区越出城市范围");
      }
    }
  }
}
