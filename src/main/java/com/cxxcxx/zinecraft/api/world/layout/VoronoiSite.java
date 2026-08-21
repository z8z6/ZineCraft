package com.cxxcxx.zinecraft.api.world.layout;

import java.util.Objects;

/**
 * Voronoi 图中的一个站点及其关联元素。
 */
public record VoronoiSite<T>(T element, PlanarPoint point) {
  public VoronoiSite {
    Objects.requireNonNull(element, "Voronoi 站点元素不能为空");
    Objects.requireNonNull(point, "Voronoi 站点坐标不能为空");
  }
}
