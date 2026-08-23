package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile;

/** 与 Minecraft 注册生命周期无关的城市 Density 数学。 */
final class CityTerrainMath {
  static final double HARD_AIR_DENSITY = -64.0;

  private CityTerrainMath() {
  }

  static Evaluation evaluate(
      double baseDensity,
      int blockY,
      CityTerrainProfile profile,
      double targetSurfaceY,
      double overlayWeight
  ) {
    double plane = surfacePlaneDensity(blockY, targetSurfaceY, profile);
    double forced;
    double surfaceLockBottom = targetSurfaceY - profile.surfaceLockDepth();
    double surfaceLockTop = targetSurfaceY + profile.surfaceLockDepth();
    if (blockY > targetSurfaceY) {
      double airConstraint = overlayWeight == 1.0 ? HARD_AIR_DENSITY : plane;
      forced = Math.min(baseDensity, airConstraint);
    } else if (blockY >= surfaceLockBottom && blockY <= surfaceLockTop) {
      forced = plane;
    } else {
      int foundationBottom = profile.groundY() - profile.foundationDepth();
      int blendBottom = foundationBottom - profile.foundationBlendDepth();
      double foundationWeight;
      if (blockY >= foundationBottom) {
        foundationWeight = 1.0;
      } else if (blockY <= blendBottom) {
        foundationWeight = 0.0;
      } else {
        foundationWeight = smootherstep(
            (blockY - blendBottom) / (double) profile.foundationBlendDepth()
        );
      }
      double supported = Math.max(baseDensity, plane);
      forced = lerp(baseDensity, supported, foundationWeight);
    }
    double result = overlayWeight == 1.0
        ? forced
        : overlayWeight == 0.0 ? baseDensity : lerp(baseDensity, forced, overlayWeight);
    return new Evaluation(baseDensity, targetSurfaceY, plane, forced, result);
  }

  static double flatPlaneDensity(int blockY, CityTerrainProfile profile) {
    return surfacePlaneDensity(blockY, profile.groundY(), profile);
  }

  static double surfacePlaneDensity(
      int blockY,
      double targetSurfaceY,
      CityTerrainProfile profile
  ) {
    return Math.clamp(
        (targetSurfaceY + 0.5 - blockY) * profile.planeSlope(),
        -profile.planeAmplitude(),
        profile.planeAmplitude()
    );
  }

  static double sparsityWeight(
      double neighborRegionDistance,
      double denseNeighborDistance,
      double sparseNeighborDistance
  ) {
    if (neighborRegionDistance <= denseNeighborDistance) return 0.0;
    if (neighborRegionDistance >= sparseNeighborDistance) return 1.0;
    return smootherstep(
        (neighborRegionDistance - denseNeighborDistance)
            / (sparseNeighborDistance - denseNeighborDistance)
    );
  }

  static double targetSurfaceY(
      CityTerrainProfile profile,
      double mountainMaskNoise,
      double ridgeNoise,
      double detailNoise,
      double regionWeight,
      double sparsityWeight,
      double denseHillHeight,
      double sparseValleyHeight,
      double sparseMountainHeight,
      double mountainMaskStart,
      double mountainMaskFull,
      double ridgeThreshold,
      int maximumSurfaceY
  ) {
    double detailShape = smootherstep((Math.clamp(detailNoise, -1.0, 1.0) + 1.0) * 0.5);
    double mountainMask = smootherstep(
        (Math.clamp(mountainMaskNoise, -1.0, 1.0) - mountainMaskStart)
            / (mountainMaskFull - mountainMaskStart)
    );
    double ridgeRaw = 1.0 - Math.abs(Math.clamp(ridgeNoise, -1.0, 1.0));
    double ridgeShape = smootherstep(
        (ridgeRaw - ridgeThreshold) / (1.0 - ridgeThreshold)
    );

    // 山体遮罩为 0 时必须能够回到低谷；否则稀疏区会被固定最低抬升连成高原。
    double mountainShape = Math.clamp(
        mountainMask * (0.20 + 0.65 * ridgeShape + 0.15 * detailShape),
        0.0,
        1.0
    );
    double denseRelief = denseHillHeight * detailShape;
    double sparseValleyRelief = sparseValleyHeight * detailShape;
    double sparseRelief = lerp(sparseValleyRelief, sparseMountainHeight, mountainShape);
    double naturalRelief = lerp(denseRelief, sparseRelief, sparsityWeight);
    double allowedRelief = Math.max(0.0, maximumSurfaceY - profile.groundY());
    naturalRelief = Math.clamp(naturalRelief, 0.0, allowedRelief);
    return profile.groundY() + naturalRelief * (1.0 - regionWeight);
  }

  static double smootherstep(double value) {
    double t = Math.clamp(value, 0.0, 1.0);
    return t * t * t * (t * (t * 6.0 - 15.0) + 10.0);
  }

  static double lerp(double first, double second, double weight) {
    return first + (second - first) * weight;
  }

  record Evaluation(
      double baseDensity,
      double targetSurfaceY,
      double planeDensity,
      double forcedDensity,
      double finalDensity
  ) {
  }
}
