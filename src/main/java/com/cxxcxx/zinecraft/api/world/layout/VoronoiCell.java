package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;

/**
 * 一个 Voronoi 站点在输入边界内的凸多边形单元。
 */
public record VoronoiCell<T>(VoronoiSite<T> site, List<PlanarPoint> boundary) {
  public VoronoiCell {
    Objects.requireNonNull(site, "Voronoi 站点不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "Voronoi 单元边界不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("Voronoi 单元边界至少需要三个点");
  }
}
