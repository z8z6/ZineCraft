package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;

/**
 * 折线 Voronoi 单元及其单一多边形边界。
 */
public record PolylineVoronoiCell<T>(
    VoronoiSite<T> site,
    List<PlanarPoint> boundary
) {
  public PolylineVoronoiCell {
    Objects.requireNonNull(site, "折线 Voronoi 站点不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "折线 Voronoi 边界不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("折线 Voronoi 边界至少需要三个点");
  }
}
