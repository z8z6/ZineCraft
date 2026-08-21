package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * 将子元素的归一化相对坐标映射进父级凸多边形，并计算子元素 Voronoi 边界。
 */
public final class NormalizedVoronoiCalculator {
  private final ConvexPolygonMapper polygonMapper;
  private final VoronoiDiagram voronoiDiagram;

  public NormalizedVoronoiCalculator() {
    this(new ConvexPolygonMapper(), new VoronoiDiagram());
  }

  public NormalizedVoronoiCalculator(
      ConvexPolygonMapper polygonMapper,
      VoronoiDiagram voronoiDiagram
  ) {
    this.polygonMapper = Objects.requireNonNull(polygonMapper, "凸多边形映射器不能为空");
    this.voronoiDiagram = Objects.requireNonNull(voronoiDiagram, "Voronoi 计算器不能为空");
  }

  public <T> List<VoronoiCell<T>> calculate(
      List<PlanarPoint> boundary,
      PlanarPoint center,
      List<T> elements,
      ToDoubleFunction<T> relativeX,
      ToDoubleFunction<T> relativeZ,
      double rotationDegrees
  ) {
    List<PlanarPoint> parentBoundary = List.copyOf(Objects.requireNonNull(boundary, "父级边界不能为空"));
    Objects.requireNonNull(center, "父级中心不能为空");
    List<T> children = List.copyOf(Objects.requireNonNull(elements, "子元素不能为空"));
    Objects.requireNonNull(relativeX, "子元素 X 坐标读取器不能为空");
    Objects.requireNonNull(relativeZ, "子元素 Z 坐标读取器不能为空");
    List<VoronoiSite<T>> sites = children.stream()
        .map(element -> new VoronoiSite<>(element, polygonMapper.map(
            parentBoundary,
            center,
            relativeX.applyAsDouble(element),
            relativeZ.applyAsDouble(element),
            rotationDegrees
        )))
        .toList();
    return voronoiDiagram.calculate(parentBoundary, sites);
  }
}
