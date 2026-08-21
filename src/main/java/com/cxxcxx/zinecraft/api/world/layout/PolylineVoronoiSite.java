package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;

/**
 * 以一个点或由多个相邻顶点组成的折线作为距离源。
 */
public record PolylineVoronoiSite<T>(T element, List<PlanarPoint> points) {
  public PolylineVoronoiSite {
    Objects.requireNonNull(element, "折线 Voronoi 元素不能为空");
    points = List.copyOf(Objects.requireNonNull(points, "折线 Voronoi 顶点不能为空"));
    if (points.isEmpty()) throw new IllegalArgumentException("折线 Voronoi 至少需要一个顶点");
  }
}
