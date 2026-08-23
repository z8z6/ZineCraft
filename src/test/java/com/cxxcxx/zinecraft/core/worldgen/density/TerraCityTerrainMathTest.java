package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile;
import com.cxxcxx.zinecraft.core.registry.ModDensityFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerraCityTerrainMathTest {
  private static final CityTerrainProfile PROFILE = CityTerrainProfile.SURFACE;

  @Test
  void hardRegionSurfaceIsExactlyGroundY() {
    assertTrue(CityTerrainMath.evaluate(-1.0, 80, PROFILE, 80.0, 1.0).finalDensity() > 0.0);
    assertTrue(CityTerrainMath.evaluate(1.0, 81, PROFILE, 80.0, 1.0).finalDensity() < 0.0);
    assertEquals(
        CityTerrainMath.flatPlaneDensity(80, PROFILE),
        CityTerrainMath.evaluate(1.0, 80, PROFILE, 80.0, 1.0).finalDensity()
    );
    assertEquals(
        CityTerrainMath.HARD_AIR_DENSITY,
        CityTerrainMath.evaluate(-1.0, 81, PROFILE, 80.0, 1.0).finalDensity()
    );
  }

  @Test
  void regionShoulderAndTransitionAreContinuous() {
    assertEquals(1.0, TerraTerrainLookup.regionWeight(PROFILE.flatShoulder(), PROFILE));
    assertEquals(0.0, TerraTerrainLookup.regionWeight(PROFILE.influenceRadius(), PROFILE));
    double previousWeight = 1.0;
    double previousTarget = PROFILE.groundY();
    for (int distance = 0; distance <= PROFILE.influenceRadius(); distance += 4) {
      double weight = TerraTerrainLookup.regionWeight(distance, PROFILE);
      double target = CityTerrainMath.targetSurfaceY(
          PROFILE, 1.0, 0.0, 1.0, weight, 1.0,
          ModDensityFunction.DENSE_HILL_HEIGHT,
          ModDensityFunction.SPARSE_VALLEY_HEIGHT,
          ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
          ModDensityFunction.MOUNTAIN_MASK_START,
          ModDensityFunction.MOUNTAIN_MASK_FULL,
          ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
          ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
      );
      assertTrue(weight <= previousWeight + 1.0E-12);
      assertTrue(target >= previousTarget - 1.0E-12);
      assertTrue(target - previousTarget <= 8.0);
      previousWeight = weight;
      previousTarget = target;
    }
  }

  @Test
  void denseRegionsProduceHillsAndSparseRegionsPermitMountains() {
    double dense = CityTerrainMath.targetSurfaceY(
        PROFILE, 1.0, 0.0, 1.0, 0.0, 0.0,
        ModDensityFunction.DENSE_HILL_HEIGHT,
        ModDensityFunction.SPARSE_VALLEY_HEIGHT,
        ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
        ModDensityFunction.MOUNTAIN_MASK_START,
        ModDensityFunction.MOUNTAIN_MASK_FULL,
        ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
        ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
    );
    double sparse = CityTerrainMath.targetSurfaceY(
        PROFILE, 1.0, 0.0, 1.0, 0.0, 1.0,
        ModDensityFunction.DENSE_HILL_HEIGHT,
        ModDensityFunction.SPARSE_VALLEY_HEIGHT,
        ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
        ModDensityFunction.MOUNTAIN_MASK_START,
        ModDensityFunction.MOUNTAIN_MASK_FULL,
        ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
        ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
    );
    assertEquals(112.0, dense);
    assertEquals(224.0, sparse);
    assertTrue(sparse > dense);
    assertEquals(80.0, CityTerrainMath.targetSurfaceY(
        PROFILE, 1.0, 0.0, 1.0, 1.0, 1.0,
        ModDensityFunction.DENSE_HILL_HEIGHT,
        ModDensityFunction.SPARSE_VALLEY_HEIGHT,
        ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
        ModDensityFunction.MOUNTAIN_MASK_START,
        ModDensityFunction.MOUNTAIN_MASK_FULL,
        ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
        ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
    ));
  }

  @Test
  void sparseMountainsHaveRealValleysInsteadOfAContinuousPlateau() {
    double valley = targetSurface(-1.0, 1.0, -1.0, 1.0);
    double valleyWithDetail = targetSurface(-1.0, 1.0, 1.0, 1.0);
    double foothill = targetSurface(1.0, 1.0, -1.0, 1.0);
    double ridgePeak = targetSurface(1.0, 0.0, 1.0, 1.0);

    assertEquals(80.0, valley);
    assertEquals(96.0, valleyWithDetail);
    assertTrue(foothill > valley && foothill < ridgePeak);
    assertEquals(224.0, ridgePeak);
  }

  @Test
  void sparsityUsesFourthRegionDistanceSmoothly() {
    assertEquals(0.0, CityTerrainMath.sparsityWeight(
        ModDensityFunction.DENSE_REGION_DISTANCE,
        ModDensityFunction.DENSE_REGION_DISTANCE,
        ModDensityFunction.SPARSE_REGION_DISTANCE
    ));
    assertEquals(1.0, CityTerrainMath.sparsityWeight(
        ModDensityFunction.SPARSE_REGION_DISTANCE,
        ModDensityFunction.DENSE_REGION_DISTANCE,
        ModDensityFunction.SPARSE_REGION_DISTANCE
    ));
    double previous = 0.0;
    for (int distance = 256; distance <= 1024; distance += 16) {
      double weight = CityTerrainMath.sparsityWeight(
          distance,
          ModDensityFunction.DENSE_REGION_DISTANCE,
          ModDensityFunction.SPARSE_REGION_DISTANCE
      );
      assertTrue(weight >= previous - 1.0E-12);
      previous = weight;
    }
  }

  @Test
  void mountainIsRootedButDeepCavesReturn() {
    assertTrue(CityTerrainMath.evaluate(-1.0, 56, PROFILE, 224.0, 1.0).finalDensity() > 0.0);
    assertEquals(-1.0,
        CityTerrainMath.evaluate(-1.0, 43, PROFILE, 224.0, 1.0).finalDensity());
  }

  @Test
  void boundedMountainBecomesAirBeforeEmergencyCeiling() {
    assertTrue(CityTerrainMath.evaluate(1.0, 224, PROFILE, 224.0, 1.0).finalDensity() > 0.0);
    assertTrue(CityTerrainMath.evaluate(1.0, 225, PROFILE, 224.0, 1.0).finalDensity() < 0.0);
    assertTrue(CityTerrainMath.evaluate(1.0, 319, PROFILE, 224.0, 1.0).finalDensity() < 0.0);
    int ceilingZeroY = (ModDensityFunction.TERRAIN_CEILING_FADE_START_Y
        + ModDensityFunction.TERRAIN_CEILING_FADE_END_Y) / 2;
    assertTrue(ModDensityFunction.MAXIMUM_CITY_SURFACE_Y <= ceilingZeroY - 64);
  }

  private static double targetSurface(
      double mountainMaskNoise,
      double ridgeNoise,
      double detailNoise,
      double sparsity
  ) {
    return CityTerrainMath.targetSurfaceY(
        PROFILE, mountainMaskNoise, ridgeNoise, detailNoise, 0.0, sparsity,
        ModDensityFunction.DENSE_HILL_HEIGHT,
        ModDensityFunction.SPARSE_VALLEY_HEIGHT,
        ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
        ModDensityFunction.MOUNTAIN_MASK_START,
        ModDensityFunction.MOUNTAIN_MASK_FULL,
        ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
        ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
    );
  }
}
