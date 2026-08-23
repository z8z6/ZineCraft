package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 一座城市完成 Chunk 移动地块与道路生长后的纯数据计划。
 */
public record CityLayoutPlan(
    NationBuilder nation,
    TerraCityBuilder city,
    PlanarPoint center,
    List<PlanarPoint> boundary,
    PlanarPoint cityCore,
    int usableChunkArea,
    CityTerrainProfile terrainProfile,
    List<CityRegionCell> regions,
    List<UrbanRoad> roads,
    double plotCoverage,
    List<String> neighboringCityIds
) {
  public CityLayoutPlan {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(center, "城市世界中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "城市边界不能为空"));
    Objects.requireNonNull(cityCore, "城市核心不能为空");
    if (usableChunkArea <= 0) throw new IllegalArgumentException("城市可用 Chunk 面积必须为正数");
    Objects.requireNonNull(terrainProfile, "城市地形 Profile 不能为空");
    regions = List.copyOf(Objects.requireNonNull(regions, "城市 Region 计划不能为空"));
    roads = List.copyOf(Objects.requireNonNull(roads, "城市道路计划不能为空"));
    if (!Double.isFinite(plotCoverage) || plotCoverage < 0.0 || plotCoverage > city.maxPlotCoverage() + 1.0E-12) {
      throw new IllegalArgumentException("城市移动地块覆盖率无效：" + city.id());
    }
    neighboringCityIds = List.copyOf(Objects.requireNonNull(neighboringCityIds, "相邻城市 ID 不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("城市边界至少需要三个点");
    if (neighboringCityIds.contains(city.id())) {
      throw new IllegalArgumentException("城市不能与自身相邻：" + city.id());
    }
    if (neighboringCityIds.stream().distinct().count() != neighboringCityIds.size()) {
      throw new IllegalArgumentException("相邻城市 ID 不能重复：" + city.id());
    }
    validateRegionAdjacency(regions);
    validateUrbanLayout(city, usableChunkArea, regions, roads, plotCoverage);
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

  private static void validateUrbanLayout(
      TerraCityBuilder city,
      int usableChunkArea,
      List<CityRegionCell> regions,
      List<UrbanRoad> roads,
      double plotCoverage
  ) {
    if (regions.size() < city.minPlotCount() || regions.size() > city.maxPlotCount()) {
      throw new IllegalArgumentException("城市移动地块数量越界：" + city.id());
    }
    double calculatedCoverage = regions.stream()
        .mapToDouble(region -> region.mobilePlotBounds().area() / 256.0)
        .sum() / usableChunkArea;
    if (Math.abs(calculatedCoverage - plotCoverage) > 1.0E-9) {
      throw new IllegalArgumentException("城市移动地块覆盖率与地块面积不一致：" + city.id());
    }
    java.util.Map<Integer, CityRegionCell> bySlot = regions.stream().collect(
        java.util.stream.Collectors.toMap(
            region -> region.slot().index(),
            java.util.function.Function.identity()
        )
    );
    java.util.Map<TerraCityRegionBuilder, Long> typeCounts = regions.stream().collect(
        java.util.stream.Collectors.groupingBy(
            CityRegionCell::region,
            java.util.stream.Collectors.counting()
        )
    );
    for (TerraCityRegionBuilder type : city.regions()) {
      long count = typeCounts.getOrDefault(type, 0L);
      if (count < type.minCount() || count > type.maxCount()) {
        throw new IllegalArgumentException("城市 Region 类型数量越界：" + type.id());
      }
    }
    for (UrbanRoad road : roads) {
      CityRegionCell from = bySlot.get(road.fromPlotId());
      CityRegionCell to = bySlot.get(road.toPlotId());
      if (from == null || to == null) {
        throw new IllegalArgumentException("城市道路引用未知 Region：" + city.id());
      }
      if (road.chunkArea().widthChunks() != city.roadWidthChunks()
          && road.chunkArea().lengthChunks() != city.roadWidthChunks()) {
        throw new IllegalArgumentException("城市道路宽度与声明不一致：" + city.id());
      }
      PlanarPoint point = road.chunkArea().centerBlocks();
      boolean forward = from.connections().stream().anyMatch(connection ->
          connection.neighboringSlotIndex() == road.toPlotId() && connection.point().equals(point)
      );
      boolean reverse = to.connections().stream().anyMatch(connection ->
          connection.neighboringSlotIndex() == road.fromPlotId() && connection.point().equals(point)
      );
      if (!forward || !reverse) {
        throw new IllegalArgumentException("城市道路与 Region connection 不一致：" + city.id());
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
