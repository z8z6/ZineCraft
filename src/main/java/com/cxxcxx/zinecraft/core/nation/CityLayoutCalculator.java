package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.layout.*;

import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

/**
 * 在已计算的 City Voronoi 边界内分配 Region slot，并计算 Region Voronoi 边界。
 */
public final class CityLayoutCalculator {
  private final LayoutPlanner layoutPlanner;
  private final NormalizedVoronoiCalculator normalizedVoronoiCalculator;

  public CityLayoutCalculator() {
    this(new LayoutPlanner(), new NormalizedVoronoiCalculator());
  }

  public CityLayoutCalculator(
      LayoutPlanner layoutPlanner,
      NormalizedVoronoiCalculator normalizedVoronoiCalculator
  ) {
    this.layoutPlanner = Objects.requireNonNull(layoutPlanner, "布局规划器不能为空");
    this.normalizedVoronoiCalculator = Objects.requireNonNull(
        normalizedVoronoiCalculator,
        "归一化 Voronoi 计算器不能为空"
    );
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
    List<CityRegionCell> regionCells = voronoiCells.stream()
        .map(cell -> new CityRegionCell(
            cell.site().element().slot(),
            cell.site().element().element(),
            cell.site().point(),
            cell.boundary()
        ))
        .toList();
    return new CityLayoutPlan(nation, city, cityCenter, boundary, regionCells);
  }
}
