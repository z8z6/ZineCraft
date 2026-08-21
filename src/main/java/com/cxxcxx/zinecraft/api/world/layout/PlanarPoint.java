package com.cxxcxx.zinecraft.api.world.layout;

/**
 * X/Z 平面上的双精度坐标点。
 */
public record PlanarPoint(double x, double z) {
  public PlanarPoint {
    if (!Double.isFinite(x) || !Double.isFinite(z)) {
      throw new IllegalArgumentException("平面坐标必须为有限数");
    }
  }
}
