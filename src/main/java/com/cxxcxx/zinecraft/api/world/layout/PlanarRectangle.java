package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;
import java.util.Objects;

/** X/Z 平面中带旋转的矩形，以中心和两个局部半边长表示。 */
public record PlanarRectangle(
    PlanarPoint center,
    double halfSizeX,
    double halfSizeZ,
    double rotationDegrees
) {
  public PlanarRectangle {
    Objects.requireNonNull(center, "矩形中心不能为空");
    if (!Double.isFinite(halfSizeX) || halfSizeX < 0.0
        || !Double.isFinite(halfSizeZ) || halfSizeZ < 0.0) {
      throw new IllegalArgumentException("矩形半边长必须是有限非负数");
    }
    if (!Double.isFinite(rotationDegrees)) throw new IllegalArgumentException("矩形旋转角必须为有限数");
    rotationDegrees %= 360.0;
    if (rotationDegrees < 0.0) rotationDegrees += 360.0;
  }

  public double area() {
    return halfSizeX * halfSizeZ * 4.0;
  }

  public boolean isDegenerate() {
    return halfSizeX == 0.0 || halfSizeZ == 0.0;
  }

  public List<PlanarPoint> corners() {
    double rotation = Math.toRadians(rotationDegrees);
    double axisXX = Math.cos(rotation);
    double axisXZ = Math.sin(rotation);
    double axisZX = -axisXZ;
    double axisZZ = axisXX;
    return List.of(
        corner(-halfSizeX, -halfSizeZ, axisXX, axisXZ, axisZX, axisZZ),
        corner(halfSizeX, -halfSizeZ, axisXX, axisXZ, axisZX, axisZZ),
        corner(halfSizeX, halfSizeZ, axisXX, axisXZ, axisZX, axisZZ),
        corner(-halfSizeX, halfSizeZ, axisXX, axisXZ, axisZX, axisZZ)
    );
  }

  /** 将 [-1, 1] 范围内的局部归一化坐标映射到矩形。 */
  public PlanarPoint pointAt(double normalizedX, double normalizedZ) {
    if (!Double.isFinite(normalizedX) || !Double.isFinite(normalizedZ)) {
      throw new IllegalArgumentException("矩形局部坐标必须是有限数");
    }
    double rotation = Math.toRadians(rotationDegrees);
    double axisXX = Math.cos(rotation);
    double axisXZ = Math.sin(rotation);
    double axisZX = -axisXZ;
    double axisZZ = axisXX;
    return corner(
        normalizedX * halfSizeX, normalizedZ * halfSizeZ,
        axisXX, axisXZ, axisZX, axisZZ
    );
  }

  public boolean contains(PlanarPoint point) {
    Objects.requireNonNull(point, "待检查点不能为空");
    double rotation = Math.toRadians(rotationDegrees);
    double dx = point.x() - center.x();
    double dz = point.z() - center.z();
    double localX = dx * Math.cos(rotation) + dz * Math.sin(rotation);
    double localZ = -dx * Math.sin(rotation) + dz * Math.cos(rotation);
    double epsilon = 1.0E-7 * Math.max(1.0, Math.max(halfSizeX, halfSizeZ));
    return Math.abs(localX) <= halfSizeX + epsilon && Math.abs(localZ) <= halfSizeZ + epsilon;
  }

  private PlanarPoint corner(
      double localX,
      double localZ,
      double axisXX,
      double axisXZ,
      double axisZX,
      double axisZZ
  ) {
    return new PlanarPoint(
        center.x() + localX * axisXX + localZ * axisZX,
        center.z() + localX * axisXZ + localZ * axisZZ
    );
  }
}
