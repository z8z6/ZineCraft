package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.core.registry.ModDensityFunction;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

/** 可选的城市边界 Density 断面 CSV 导出器；不会参与正常 Chunk 采样。 */
public final class TerraTerrainTransectExporter {
  private TerraTerrainTransectExporter() {
  }

  public static void export(
      Path output,
      DensityFunction normalTerrain,
      DensityFunction mountainMaskTerrain,
      DensityFunction ridgeTerrain,
      DensityFunction detailTerrain,
      DensityFunction finalTerrain,
      int startX,
      int startZ,
      int endX,
      int endZ,
      int stepBlocks,
      int minY,
      int maxY
  ) throws IOException {
    Objects.requireNonNull(output, "断面 CSV 路径不能为空");
    Objects.requireNonNull(normalTerrain, "自然地形 Density 不能为空");
    Objects.requireNonNull(mountainMaskTerrain, "山体遮罩 Density 不能为空");
    Objects.requireNonNull(ridgeTerrain, "山脊 Density 不能为空");
    Objects.requireNonNull(detailTerrain, "细节 Density 不能为空");
    Objects.requireNonNull(finalTerrain, "最终 Density 不能为空");
    if (stepBlocks <= 0 || minY >= maxY) throw new IllegalArgumentException("断面采样范围无效");

    Path parent = output.toAbsolutePath().getParent();
    if (parent != null) Files.createDirectories(parent);
    double length = Math.hypot(endX - startX, endZ - startZ);
    int samples = Math.max(1, (int) Math.ceil(length / stepBlocks));
    try (BufferedWriter writer = Files.newBufferedWriter(
        output, StandardCharsets.UTF_8
    )) {
      writer.write("x,z,regionWeight,distanceToRegion,neighborRegionDistance,sparsityWeight,"
          + "mountainMaskNoise,ridgeNoise,detailNoise,"
          + "targetSurfaceY,Dbase,Dforced,Dfinal,estimatedSurfaceY");
      writer.newLine();
      for (int index = 0; index <= samples; index++) {
        double t = index / (double) samples;
        int x = (int) Math.round(startX + (endX - startX) * t);
        int z = (int) Math.round(startZ + (endZ - startZ) * t);
        TerraTerrainLookup.CityTerrainSample terrain = TerraTerrainLookup.cityTerrainAt(
            x, maxY, z,
            ModDensityFunction.REGION_PROXIMITY_NEIGHBOR_RANK,
            ModDensityFunction.SPARSE_REGION_DISTANCE
        );
        int sampleY = terrain.profile() == null ? maxY : terrain.profile().groundY();
        DensityFunction.FunctionContext context = new PointContext(x, sampleY, z);
        double normal = normalTerrain.compute(context);
        double mountainMaskNoise = mountainMaskTerrain.compute(context);
        double ridgeNoise = ridgeTerrain.compute(context);
        double detailNoise = detailTerrain.compute(context);
        double sparsity = CityTerrainMath.sparsityWeight(
            terrain.neighborRegionDistance(),
            ModDensityFunction.DENSE_REGION_DISTANCE,
            ModDensityFunction.SPARSE_REGION_DISTANCE
        );
        double targetSurfaceY = terrain.profile() == null
            ? Double.NaN
            : terrain.profile().groundY() < 0
                ? terrain.profile().groundY()
                : CityTerrainMath.targetSurfaceY(
                    terrain.profile(), mountainMaskNoise, ridgeNoise, detailNoise,
                    terrain.regionWeight(), sparsity,
                    ModDensityFunction.DENSE_HILL_HEIGHT,
                    ModDensityFunction.SPARSE_VALLEY_HEIGHT,
                    ModDensityFunction.SPARSE_MOUNTAIN_HEIGHT,
                    ModDensityFunction.MOUNTAIN_MASK_START,
                    ModDensityFunction.MOUNTAIN_MASK_FULL,
                    ModDensityFunction.MOUNTAIN_RIDGE_THRESHOLD,
                    ModDensityFunction.MAXIMUM_CITY_SURFACE_Y
                );
        double overlayWeight = terrain.profile() == null
            ? 0.0
            : terrain.profile().groundY() < 0
                ? terrain.regionWeight()
                : Math.max(terrain.regionWeight(), terrain.cityWeight());
        CityTerrainMath.Evaluation evaluation = terrain.profile() == null
            ? new CityTerrainMath.Evaluation(normal, Double.NaN, Double.NaN, normal, normal)
            : CityTerrainMath.evaluate(
                normal, sampleY, terrain.profile(), targetSurfaceY, overlayWeight
            );
        double finalDensity = finalTerrain.compute(context);
        int surfaceY = estimatedSurfaceY(finalTerrain, x, z, minY, maxY);
        writer.write(String.format(Locale.ROOT,
            "%d,%d,%.9f,%.3f,%.3f,%.9f,%.9f,%.9f,%.9f,%.3f,%.9f,%.9f,%.9f,%d",
            x, z, terrain.regionWeight(), terrain.distanceToRegion(),
            terrain.neighborRegionDistance(), sparsity,
            mountainMaskNoise, ridgeNoise, detailNoise, targetSurfaceY,
            normal, evaluation.forcedDensity(), finalDensity, surfaceY));
        writer.newLine();
      }
    }
  }

  private static int estimatedSurfaceY(
      DensityFunction density, int x, int z, int minY, int maxY
  ) {
    for (int y = maxY; y >= minY; y--) {
      if (density.compute(new PointContext(x, y, z)) > 0.0) return y;
    }
    return minY - 1;
  }

  private record PointContext(int blockX, int blockY, int blockZ)
      implements DensityFunction.FunctionContext {
  }
}
