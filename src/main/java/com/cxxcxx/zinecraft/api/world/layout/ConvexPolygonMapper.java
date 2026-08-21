package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;

/**
 * 将 [-1, 1] 范围的归一化坐标沿中心射线映射到凸多边形内部。
 */
public final class ConvexPolygonMapper {
  private static final double EPSILON = 1.0E-9;

  private static double rayBoundaryDistance(
      List<PlanarPoint> polygon,
      PlanarPoint center,
      double directionX,
      double directionZ
  ) {
    double result = Double.POSITIVE_INFINITY;
    PlanarPoint start = polygon.getLast();
    for (PlanarPoint end : polygon) {
      double edgeX = end.x() - start.x();
      double edgeZ = end.z() - start.z();
      double denominator = cross(directionX, directionZ, edgeX, edgeZ);
      if (Math.abs(denominator) > EPSILON) {
        double offsetX = start.x() - center.x();
        double offsetZ = start.z() - center.z();
        double distance = cross(offsetX, offsetZ, edgeX, edgeZ) / denominator;
        double edgePosition = cross(offsetX, offsetZ, directionX, directionZ) / denominator;
        if (distance >= -EPSILON && edgePosition >= -EPSILON && edgePosition <= 1.0 + EPSILON) {
          result = Math.clamp(distance, 0.0, result);
        }
      }
      start = end;
    }
    if (!Double.isFinite(result)) throw new IllegalStateException("归一化坐标射线未命中多边形边界");
    return result;
  }

  private static double cross(double firstX, double firstZ, double secondX, double secondZ) {
    return firstX * secondZ - firstZ * secondX;
  }

  public PlanarPoint map(
      List<PlanarPoint> boundary,
      PlanarPoint center,
      double normalizedX,
      double normalizedZ,
      double rotationDegrees
  ) {
    List<PlanarPoint> polygon = List.copyOf(Objects.requireNonNull(boundary, "映射边界不能为空"));
    Objects.requireNonNull(center, "映射中心不能为空");
    if (polygon.size() < 3) throw new IllegalArgumentException("映射边界至少需要三个点");
    if (Math.abs(normalizedX) > 1.0 + EPSILON || Math.abs(normalizedZ) > 1.0 + EPSILON) {
      throw new IllegalArgumentException("待映射坐标必须位于 [-1, 1]");
    }
    double fraction = Math.max(Math.abs(normalizedX), Math.abs(normalizedZ));
    if (fraction <= EPSILON) return center;

    double localX = normalizedX / fraction;
    double localZ = normalizedZ / fraction;
    double rotation = Math.toRadians(rotationDegrees);
    double directionX = localX * Math.cos(rotation) - localZ * Math.sin(rotation);
    double directionZ = localX * Math.sin(rotation) + localZ * Math.cos(rotation);
    double boundaryDistance = rayBoundaryDistance(polygon, center, directionX, directionZ);
    return new PlanarPoint(
        center.x() + directionX * boundaryDistance * fraction,
        center.z() + directionZ * boundaryDistance * fraction
    );
  }
}
