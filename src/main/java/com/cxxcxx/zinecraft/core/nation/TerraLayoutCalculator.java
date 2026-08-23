package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.*;

import java.util.*;

/**
 * 计算泰拉 Nation/City Voronoi 边界及 City 内部正交 Region 地块。
 */
public final class TerraLayoutCalculator {
  private final PolylineVoronoiDiagram nationVoronoiDiagram;
  private final CityLayoutCalculator cityLayoutCalculator;
  private final NormalizedVoronoiCalculator normalizedVoronoiCalculator;
  private final PolygonAdjacencyCalculator adjacencyCalculator;

  public TerraLayoutCalculator() {
    this(
        new PolylineVoronoiDiagram(),
        new CityLayoutCalculator(),
        new NormalizedVoronoiCalculator(),
        new PolygonAdjacencyCalculator()
    );
  }

  public TerraLayoutCalculator(
      PolylineVoronoiDiagram nationVoronoiDiagram,
      CityLayoutCalculator cityLayoutCalculator,
      NormalizedVoronoiCalculator normalizedVoronoiCalculator,
      PolygonAdjacencyCalculator adjacencyCalculator
  ) {
    this.nationVoronoiDiagram = Objects.requireNonNull(nationVoronoiDiagram, "国家 Voronoi 计算器不能为空");
    this.cityLayoutCalculator = Objects.requireNonNull(cityLayoutCalculator, "城市布局计算器不能为空");
    this.normalizedVoronoiCalculator = Objects.requireNonNull(
        normalizedVoronoiCalculator,
        "归一化 Voronoi 计算器不能为空"
    );
    this.adjacencyCalculator = Objects.requireNonNull(adjacencyCalculator, "多边形邻接计算器不能为空");
  }

  private static List<PlanarPoint> absolutePoints(
      NationBuilder nation,
      int coreHalfSizeX,
      int coreHalfSizeZ
  ) {
    return nation.relativePoints().stream()
        .map(point -> new PlanarPoint(point.x() * coreHalfSizeX, point.z() * coreHalfSizeZ))
        .toList();
  }

  public TerraLayoutPlan calculate(
      List<NationBuilder> registeredNations,
      int coreHalfSizeX,
      int coreHalfSizeZ
  ) {
    List<NationBuilder> nations = List.copyOf(Objects.requireNonNull(registeredNations, "泰拉国家清单不能为空"));
    if (coreHalfSizeX <= 0 || coreHalfSizeZ <= 0) {
      throw new IllegalArgumentException("泰拉核心矩形半边长必须为正数");
    }
    List<PlanarPoint> terraBoundary = VoronoiDiagram.rectangle(
        -coreHalfSizeX, -coreHalfSizeZ, coreHalfSizeX, coreHalfSizeZ
    );
    List<PolylineVoronoiSite<NationBuilder>> nationSites = nations.stream()
        .filter(nation -> !nation.isUnderground())
        .map(nation -> new PolylineVoronoiSite<>(
            nation,
            absolutePoints(nation, coreHalfSizeX, coreHalfSizeZ)
        ))
        .toList();
    List<PolylineVoronoiCell<NationBuilder>> nationCells = nationVoronoiDiagram.calculate(
        terraBoundary,
        nationSites
    );
    Map<NationBuilder, PolylineVoronoiCell<NationBuilder>> surfaceCells = new HashMap<>();
    for (PolylineVoronoiCell<NationBuilder> cell : nationCells) {
      surfaceCells.put(cell.site().element(), cell);
    }

    ArrayList<NationLayoutPlan> plans = new ArrayList<>();
    for (NationBuilder nation : nations) {
      if (nation.isUnderground()) {
        PlanarPoint center = PolylineVoronoiDiagram.midpoint(
            absolutePoints(nation, coreHalfSizeX, coreHalfSizeZ)
        );
        double halfSize = nation.size() / 2.0;
        if (Math.abs(center.x()) + halfSize > coreHalfSizeX
            || Math.abs(center.z()) + halfSize > coreHalfSizeZ) {
          throw new IllegalArgumentException("地下国家固定边界超出泰拉核心矩形：" + nation.id());
        }
        plans.add(calculateNation(
            nation,
            center,
            VoronoiDiagram.rectangle(
                center.x() - halfSize,
                center.z() - halfSize,
                center.x() + halfSize,
                center.z() + halfSize
            )
        ));
        continue;
      }
      PolylineVoronoiCell<NationBuilder> nationCell = surfaceCells.get(nation);
      if (nationCell == null) throw new IllegalStateException("地表国家缺少 Voronoi 单元：" + nation.id());
      plans.add(calculateNation(nation, nationCell.site().point(), nationCell.boundary()));
    }
    List<List<Integer>> adjacency = adjacencyCalculator.calculate(plans, NationLayoutPlan::boundary);
    List<NationLayoutPlan> connectedPlans = java.util.stream.IntStream.range(0, plans.size())
        .mapToObj(index -> {
          NationLayoutPlan plan = plans.get(index);
          List<String> neighboringNationIds = adjacency.get(index).stream()
              .map(neighborIndex -> plans.get(neighborIndex).nation().id())
              .toList();
          return new NationLayoutPlan(
              plan.nation(), plan.center(), plan.boundary(), plan.cities(), neighboringNationIds
          );
        })
        .toList();
    return new TerraLayoutPlan(terraBoundary, connectedPlans);
  }

  private NationLayoutPlan calculateNation(
      NationBuilder nation,
      PlanarPoint nationCenter,
      List<PlanarPoint> nationBoundary
  ) {
    List<VoronoiCell<TerraCityBuilder>> cityCells = normalizedVoronoiCalculator.calculate(
        nationBoundary,
        nationCenter,
        nation.cities(),
        TerraCityBuilder::relativeX,
        TerraCityBuilder::relativeZ,
        0.0
    );
    List<CityLayoutPlan> cities = cityCells.stream()
        .map(cell -> cityLayoutCalculator.calculate(
            nation,
            cell.site().element(),
            cell.site().point(),
            cell.boundary(),
            new Random(Integer.toUnsignedLong(cell.site().element().id().hashCode()))
        ))
        .toList();
    List<List<Integer>> adjacency = adjacencyCalculator.calculateVoronoi(
        cities,
        CityLayoutPlan::boundary,
        CityLayoutPlan::center
    );
    List<CityLayoutPlan> connectedCities = java.util.stream.IntStream.range(0, cities.size())
        .mapToObj(index -> {
          CityLayoutPlan city = cities.get(index);
          List<String> neighboringCityIds = adjacency.get(index).stream()
              .map(neighborIndex -> cities.get(neighborIndex).city().id())
              .toList();
          return new CityLayoutPlan(
              city.nation(), city.city(), city.center(), city.boundary(),
              city.cityCore(), city.usableChunkArea(), city.terrainProfile(), city.regions(), city.roads(),
              city.plotCoverage(), neighboringCityIds
          );
        })
        .toList();
    return new NationLayoutPlan(nation, nationCenter, nationBoundary, connectedCities, List.of());
  }
}
