package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.*;

import java.util.*;

/**
 * 计算泰拉 Nation、City、Region 三级 Voronoi 边界。
 */
public final class TerraLayoutCalculator {
  private final PolylineVoronoiDiagram nationVoronoiDiagram;
  private final CityLayoutCalculator cityLayoutCalculator;
  private final NormalizedVoronoiCalculator normalizedVoronoiCalculator;

  public TerraLayoutCalculator() {
    this(new PolylineVoronoiDiagram(), new CityLayoutCalculator(), new NormalizedVoronoiCalculator());
  }

  public TerraLayoutCalculator(
      PolylineVoronoiDiagram nationVoronoiDiagram,
      CityLayoutCalculator cityLayoutCalculator,
      NormalizedVoronoiCalculator normalizedVoronoiCalculator
  ) {
    this.nationVoronoiDiagram = Objects.requireNonNull(nationVoronoiDiagram, "国家 Voronoi 计算器不能为空");
    this.cityLayoutCalculator = Objects.requireNonNull(cityLayoutCalculator, "城市布局计算器不能为空");
    this.normalizedVoronoiCalculator = Objects.requireNonNull(
        normalizedVoronoiCalculator,
        "归一化 Voronoi 计算器不能为空"
    );
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
    return new TerraLayoutPlan(terraBoundary, plans);
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
    return new NationLayoutPlan(nation, nationCenter, nationBoundary, cities);
  }
}
