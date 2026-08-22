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
    List<CityRegionCell> regions,
    List<String> neighboringCityIds
) {
  public CityLayoutPlan {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(center, "城市世界中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "城市边界不能为空"));
    regions = List.copyOf(Objects.requireNonNull(regions, "城市 Region 计划不能为空"));
    neighboringCityIds = List.copyOf(Objects.requireNonNull(neighboringCityIds, "相邻城市 ID 不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("城市边界至少需要三个点");
    if (neighboringCityIds.contains(city.id())) {
      throw new IllegalArgumentException("城市不能与自身相邻：" + city.id());
    }
    if (neighboringCityIds.stream().distinct().count() != neighboringCityIds.size()) {
      throw new IllegalArgumentException("相邻城市 ID 不能重复：" + city.id());
    }
    validateRegionAdjacency(regions);
  }

  private static void validateRegionAdjacency(List<CityRegionCell> regions) {
    java.util.Map<Integer, CityRegionCell> bySlot = new java.util.LinkedHashMap<>();
    for (CityRegionCell region : regions) {
      if (bySlot.putIfAbsent(region.slot().index(), region) != null) {
        throw new IllegalArgumentException("城市存在重复 Region 槽位：" + region.slot().index());
      }
    }
    for (CityRegionCell region : regions) {
      for (CityRegionConnection connection : region.connections()) {
        int neighbor = connection.neighboringSlotIndex();
        CityRegionCell neighborCell = bySlot.get(neighbor);
        if (neighborCell == null) throw new IllegalArgumentException("Region 引用了未知相邻槽位：" + neighbor);
        CityRegionConnection reverse = neighborCell.connections().stream()
            .filter(candidate -> candidate.neighboringSlotIndex() == region.slot().index())
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Region 连通关系必须双向：" + region.slot().index() + "/" + neighbor
            ));
        if (!reverse.point().equals(connection.point())) {
          throw new IllegalArgumentException(
              "相邻 Region 必须共享同一个连通点：" + region.slot().index() + "/" + neighbor
          );
        }
      }
    }
  }

  /** 返回指定 Region Cell 的同级相邻节点。 */
  public List<CityRegionCell> neighboringRegions(CityRegionCell region) {
    Objects.requireNonNull(region, "Region Cell 不能为空");
    if (!regions.contains(region)) throw new IllegalArgumentException("Region Cell 不属于当前城市计划");
    java.util.Map<Integer, CityRegionCell> bySlot = regions.stream().collect(java.util.stream.Collectors.toMap(
        cell -> cell.slot().index(),
        java.util.function.Function.identity(),
        (first, second) -> first,
        java.util.LinkedHashMap::new
    ));
    return region.neighboringSlotIndexes().stream().map(bySlot::get).toList();
  }
}
