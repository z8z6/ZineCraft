package com.cxxcxx.zinecraft.api.world.city;

/** 一座城市冻结后唯一使用的地形平整参数。 */
public record CityTerrainProfile(
    int groundY,
    int foundationDepth,
    int foundationBlendDepth,
    int surfaceLockDepth,
    int flatShoulder,
    int transitionWidth,
    double planeSlope,
    double planeAmplitude
) {
  public static final double MAX_PLANE_AMPLITUDE = 0.30;
  public static final CityTerrainProfile SURFACE = new CityTerrainProfile(
      80, 24, 12, 8, 32, 384, 0.04, 0.20
  );
  public static final CityTerrainProfile UNDERGROUND = new CityTerrainProfile(
      -64, 24, 12, 8, 32, 384, 0.04, 0.20
  );

  public CityTerrainProfile {
    if (foundationDepth <= 0 || foundationBlendDepth <= 0) {
      throw new IllegalArgumentException("城市承重层深度与混合深度必须为正数");
    }
    if (surfaceLockDepth <= 0 || surfaceLockDepth > foundationDepth) {
      throw new IllegalArgumentException("城市地表硬锁深度必须位于 (0, foundationDepth]");
    }
    if (flatShoulder < 0 || transitionWidth <= 0) {
      throw new IllegalArgumentException("城市平地肩部必须非负且水平过渡宽度必须为正数");
    }
    if (!Double.isFinite(planeSlope) || planeSlope <= 0.0
        || !Double.isFinite(planeAmplitude) || planeAmplitude <= 0.0
        || planeAmplitude > MAX_PLANE_AMPLITUDE) {
      throw new IllegalArgumentException("城市平面密度斜率与幅度必须是有效的小幅正数");
    }
  }

  public int influenceRadius() {
    return Math.addExact(flatShoulder, transitionWidth);
  }
}
