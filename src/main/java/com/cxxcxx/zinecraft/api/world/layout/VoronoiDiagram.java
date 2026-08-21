package com.cxxcxx.zinecraft.api.world.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 在任意凸多边形边界内计算欧氏距离 Voronoi 图。
 */
public final class VoronoiDiagram {
  private static final double EPSILON = 1.0E-9;

  public static List<PlanarPoint> rectangle(double minX, double minZ, double maxX, double maxZ) {
    if (!(minX < maxX) || !(minZ < maxZ)) throw new IllegalArgumentException("Voronoi 矩形边界无效");
    return List.of(
        new PlanarPoint(minX, minZ),
        new PlanarPoint(maxX, minZ),
        new PlanarPoint(maxX, maxZ),
        new PlanarPoint(minX, maxZ)
    );
  }

  /**
   * 返回以指定坐标为圆心的凸正多边形，用作圆形 Voronoi 裁剪边界。
   */
  public static List<PlanarPoint> circle(double centerX, double centerZ, double radius, int segments) {
    if (!Double.isFinite(radius) || radius <= 0.0) {
      throw new IllegalArgumentException("Voronoi 圆形边界半径必须为正数");
    }
    if (segments < 3) throw new IllegalArgumentException("Voronoi 圆形边界至少需要三个分段");
    ArrayList<PlanarPoint> boundary = new ArrayList<>(segments);
    for (int index = 0; index < segments; index++) {
      double angle = Math.PI * 2.0 * index / segments;
      boundary.add(new PlanarPoint(
          centerX + Math.cos(angle) * radius,
          centerZ + Math.sin(angle) * radius
      ));
    }
    return List.copyOf(boundary);
  }

  private static <T> void requireDistinctSites(List<VoronoiSite<T>> sites) {
    for (int first = 0; first < sites.size(); first++) {
      for (int second = first + 1; second < sites.size(); second++) {
        if (squaredDistance(sites.get(first).point(), sites.get(second).point()) <= EPSILON * EPSILON) {
          throw new IllegalArgumentException("Voronoi 站点坐标重复：" + first + "/" + second);
        }
      }
    }
  }

  private static List<PlanarPoint> clip(
      List<PlanarPoint> polygon,
      double normalX,
      double normalZ,
      double limit
  ) {
    if (polygon.isEmpty()) return List.of();
    ArrayList<PlanarPoint> result = new ArrayList<>();
    PlanarPoint start = polygon.getLast();
    double startDistance = signedDistance(start, normalX, normalZ, limit);
    for (PlanarPoint end : polygon) {
      double endDistance = signedDistance(end, normalX, normalZ, limit);
      boolean startInside = startDistance <= EPSILON;
      boolean endInside = endDistance <= EPSILON;
      if (startInside != endInside) {
        double denominator = startDistance - endDistance;
        double ratio = Math.abs(denominator) <= EPSILON ? 0.0 : startDistance / denominator;
        result.add(new PlanarPoint(
            start.x() + (end.x() - start.x()) * ratio,
            start.z() + (end.z() - start.z()) * ratio
        ));
      }
      if (endInside) result.add(end);
      start = end;
      startDistance = endDistance;
    }
    return List.copyOf(result);
  }

  private static double signedDistance(PlanarPoint point, double normalX, double normalZ, double limit) {
    return normalX * point.x() + normalZ * point.z() - limit;
  }

  private static List<PlanarPoint> compact(List<PlanarPoint> polygon) {
    ArrayList<PlanarPoint> result = new ArrayList<>(polygon.size());
    for (PlanarPoint point : polygon) {
      Objects.requireNonNull(point, "Voronoi 边界不能包含 null");
      if (result.isEmpty() || squaredDistance(result.getLast(), point) > EPSILON * EPSILON) {
        result.add(point);
      }
    }
    if (result.size() > 1 && squaredDistance(result.getFirst(), result.getLast()) <= EPSILON * EPSILON) {
      result.removeLast();
    }
    return List.copyOf(result);
  }

  private static double squaredDistance(PlanarPoint first, PlanarPoint second) {
    double deltaX = first.x() - second.x();
    double deltaZ = first.z() - second.z();
    return deltaX * deltaX + deltaZ * deltaZ;
  }

  public <T> List<VoronoiCell<T>> calculate(
      List<PlanarPoint> boundary,
      List<VoronoiSite<T>> sites
  ) {
    List<PlanarPoint> clippingBoundary = compact(Objects.requireNonNull(boundary, "Voronoi 外边界不能为空"));
    List<VoronoiSite<T>> declaredSites = List.copyOf(Objects.requireNonNull(sites, "Voronoi 站点不能为空"));
    if (clippingBoundary.size() < 3) throw new IllegalArgumentException("Voronoi 外边界至少需要三个点");
    if (declaredSites.isEmpty()) throw new IllegalArgumentException("Voronoi 至少需要一个站点");
    requireDistinctSites(declaredSites);

    ArrayList<VoronoiCell<T>> cells = new ArrayList<>(declaredSites.size());
    for (int siteIndex = 0; siteIndex < declaredSites.size(); siteIndex++) {
      List<PlanarPoint> polygon = clippingBoundary;
      PlanarPoint site = declaredSites.get(siteIndex).point();
      for (int otherIndex = 0; otherIndex < declaredSites.size(); otherIndex++) {
        if (otherIndex == siteIndex) continue;
        PlanarPoint other = declaredSites.get(otherIndex).point();
        double normalX = other.x() - site.x();
        double normalZ = other.z() - site.z();
        double limit = (
            other.x() * other.x() + other.z() * other.z()
                - site.x() * site.x() - site.z() * site.z()
        ) / 2.0;
        polygon = clip(polygon, normalX, normalZ, limit);
        if (polygon.isEmpty()) break;
      }
      polygon = compact(polygon);
      if (polygon.size() < 3) {
        throw new IllegalStateException("Voronoi 单元边界退化：" + siteIndex);
      }
      cells.add(new VoronoiCell<>(declaredSites.get(siteIndex), polygon));
    }
    return List.copyOf(cells);
  }
}
