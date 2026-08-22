package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionConnection;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

/**
 * 在已计算的 City Voronoi 边界内分配 Region slot，并计算 Region Voronoi 边界。
 */
public final class CityLayoutCalculator {
  private static final double BUILDING_SLOT_INSET = 0.9;
  private final LayoutPlanner layoutPlanner;
  private final NormalizedVoronoiCalculator normalizedVoronoiCalculator;
  private final PolygonAdjacencyCalculator adjacencyCalculator;
  private final AxisAlignedRectangleCalculator rectangleCalculator;

  public CityLayoutCalculator() {
    this(
        new LayoutPlanner(),
        new NormalizedVoronoiCalculator(),
        new PolygonAdjacencyCalculator(),
        new AxisAlignedRectangleCalculator()
    );
  }

  public CityLayoutCalculator(
      LayoutPlanner layoutPlanner,
      NormalizedVoronoiCalculator normalizedVoronoiCalculator,
      PolygonAdjacencyCalculator adjacencyCalculator,
      AxisAlignedRectangleCalculator rectangleCalculator
  ) {
    this.layoutPlanner = Objects.requireNonNull(layoutPlanner, "布局规划器不能为空");
    this.normalizedVoronoiCalculator = Objects.requireNonNull(
        normalizedVoronoiCalculator,
        "归一化 Voronoi 计算器不能为空"
    );
    this.adjacencyCalculator = Objects.requireNonNull(adjacencyCalculator, "多边形邻接计算器不能为空");
    this.rectangleCalculator = Objects.requireNonNull(rectangleCalculator, "中心矩形计算器不能为空");
  }

  public CityLayoutPlan calculate(
      NationBuilder nation,
      TerraCityBuilder city,
      PlanarPoint cityCenter,
      List<PlanarPoint> cityBoundary,
      RandomGenerator random
  ) {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(cityCenter, "城市中心不能为空");
    List<PlanarPoint> boundary = List.copyOf(Objects.requireNonNull(cityBoundary, "城市边界不能为空"));
    Objects.requireNonNull(random, "城市布局随机源不能为空");
    if (!nation.cities().contains(city)) {
      throw new IllegalArgumentException("国家没有声明该城市：" + nation.id() + "/" + city.id());
    }

    List<LayoutAssignment<TerraCityRegionBuilder>> assignments = layoutPlanner.plan(
        city.regionLayout(),
        city.slotCount().count(),
        city.regions(),
        random
    );
    for (LayoutAssignment<TerraCityRegionBuilder> assignment : assignments) {
      if (assignment.element().nation() != nation) {
        throw new IllegalArgumentException("城市引用了其他国家的 Region：" + assignment.element().id());
      }
    }

    List<VoronoiCell<LayoutAssignment<TerraCityRegionBuilder>>> voronoiCells =
        normalizedVoronoiCalculator.calculate(
            boundary,
            cityCenter,
            assignments,
            assignment -> assignment.slot().x(),
            assignment -> assignment.slot().z(),
            city.rotationDegrees()
        );
    List<List<Integer>> adjacency = adjacencyCalculator.calculate(voronoiCells, VoronoiCell::boundary);
    ArrayList<ArrayList<CityRegionConnection>> connections = new ArrayList<>(voronoiCells.size());
    for (int index = 0; index < voronoiCells.size(); index++) connections.add(new ArrayList<>());
    for (int first = 0; first < voronoiCells.size(); first++) {
      for (int second : adjacency.get(first)) {
        if (second <= first) continue;
        java.util.Optional<PlanarPoint> connection = adjacencyCalculator.sharedBoundaryMidpoint(
            voronoiCells.get(first).boundary(),
            voronoiCells.get(second).boundary()
        );
        if (connection.isEmpty()) {
          throw new IllegalStateException(
              "相邻 Region 缺少共享边界：" + voronoiCells.get(first).site().element().slot().index()
                  + "/" + voronoiCells.get(second).site().element().slot().index()
          );
        }
        PlanarPoint connectionPoint = connection.get();
        connections.get(first).add(new CityRegionConnection(
            voronoiCells.get(second).site().element().slot().index(), connectionPoint
        ));
        connections.get(second).add(new CityRegionConnection(
            voronoiCells.get(first).site().element().slot().index(), connectionPoint
        ));
      }
    }
    List<CityRegionCell> connectedRegions = java.util.stream.IntStream.range(0, voronoiCells.size())
        .mapToObj(index -> {
          VoronoiCell<LayoutAssignment<TerraCityRegionBuilder>> cell = voronoiCells.get(index);
          PlanarRectangle mobilePlotBounds;
          try {
            mobilePlotBounds = rectangleCalculator.calculate(cell.boundary());
          } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                "无法计算 Region 移动地块：" + city.id() + "/" + cell.site().element().slot().index(),
                exception
            );
          }
          List<CityRegionBuildingSlot> buildingSlots = buildingSlots(
              cell.site().element().element(), mobilePlotBounds, random
          );
          return new CityRegionCell(
              cell.site().element().slot(),
              cell.site().element().element(),
              cell.site().point(),
              cell.boundary(),
              connections.get(index),
              mobilePlotBounds,
              buildingSlots
          );
        })
        .toList();
    return new CityLayoutPlan(nation, city, cityCenter, boundary, connectedRegions, List.of());
  }

  private List<CityRegionBuildingSlot> buildingSlots(
      TerraCityRegionBuilder region,
      PlanarRectangle bounds,
      RandomGenerator random
  ) {
    List<LayoutAssignment<TerraCityRegionBuilding>> assignments = layoutPlanner.plan(
        region.buildingLayout(), region.slotCount().count(), region.buildings(), random
    );
    return assignments.stream().map(assignment -> {
      LayoutSlot declaredSlot = assignment.slot();
      if (Math.abs(declaredSlot.x()) > 1.0 || Math.abs(declaredSlot.z()) > 1.0) {
        throw new IllegalStateException(
            "Region 建筑布局槽位超出归一化范围：" + region.id() + "/" + declaredSlot.index()
        );
      }
      LayoutSlot insetSlot = new LayoutSlot(
          declaredSlot.index(),
          declaredSlot.x() * BUILDING_SLOT_INSET,
          declaredSlot.z() * BUILDING_SLOT_INSET
      );
      return new CityRegionBuildingSlot(
          insetSlot,
          bounds.pointAt(insetSlot.x(), insetSlot.z()),
          assignment.element().building()
      );
    }).toList();
  }

}
