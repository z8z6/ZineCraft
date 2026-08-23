package com.cxxcxx.zinecraft.core.worldgen.density;

import com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Objects;

/**
 * 以 Region Union 为硬平面，并在城市自然区域生成有界目标地表。
 * 类名为兼容现有 DensityFunction 类型 ID 暂时保留。
 */
public record TerraCityRegionDensityFunction(
    DensityFunction normalTerrain,
    DensityFunction mountainMaskNoise,
    DensityFunction ridgeNoise,
    DensityFunction detailNoise,
    int proximityNeighborRank,
    double denseNeighborDistance,
    double sparseNeighborDistance,
    double denseHillHeight,
    double sparseValleyHeight,
    double sparseMountainHeight,
    double mountainMaskStart,
    double mountainMaskFull,
    double ridgeThreshold,
    int maximumSurfaceY
) implements DensityFunction {
  private static final Codec<Double> NON_NEGATIVE_DISTANCE = Codec.doubleRange(0.0, 8192.0);
  private static final Codec<Double> RELIEF_HEIGHT = Codec.doubleRange(0.0, 512.0);

  public static final KeyDispatchDataCodec<TerraCityRegionDensityFunction> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(instance -> instance.group(
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("normal_terrain")
              .forGetter(TerraCityRegionDensityFunction::normalTerrain),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("mountain_mask_noise")
              .forGetter(TerraCityRegionDensityFunction::mountainMaskNoise),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("ridge_noise")
              .forGetter(TerraCityRegionDensityFunction::ridgeNoise),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("detail_noise")
              .forGetter(TerraCityRegionDensityFunction::detailNoise),
          Codec.intRange(1, 16).fieldOf("proximity_neighbor_rank")
              .forGetter(TerraCityRegionDensityFunction::proximityNeighborRank),
          NON_NEGATIVE_DISTANCE.fieldOf("dense_neighbor_distance")
              .forGetter(TerraCityRegionDensityFunction::denseNeighborDistance),
          NON_NEGATIVE_DISTANCE.fieldOf("sparse_neighbor_distance")
              .forGetter(TerraCityRegionDensityFunction::sparseNeighborDistance),
          RELIEF_HEIGHT.fieldOf("dense_hill_height")
              .forGetter(TerraCityRegionDensityFunction::denseHillHeight),
          RELIEF_HEIGHT.fieldOf("sparse_valley_height")
              .forGetter(TerraCityRegionDensityFunction::sparseValleyHeight),
          RELIEF_HEIGHT.fieldOf("sparse_mountain_height")
              .forGetter(TerraCityRegionDensityFunction::sparseMountainHeight),
          Codec.doubleRange(-1.0, 1.0).fieldOf("mountain_mask_start")
              .forGetter(TerraCityRegionDensityFunction::mountainMaskStart),
          Codec.doubleRange(-1.0, 1.0).fieldOf("mountain_mask_full")
              .forGetter(TerraCityRegionDensityFunction::mountainMaskFull),
          Codec.doubleRange(0.0, 0.99).fieldOf("ridge_threshold")
              .forGetter(TerraCityRegionDensityFunction::ridgeThreshold),
          Codec.intRange(0, 767).fieldOf("maximum_surface_y")
              .forGetter(TerraCityRegionDensityFunction::maximumSurfaceY)
      ).apply(instance, TerraCityRegionDensityFunction::new))
  );

  public TerraCityRegionDensityFunction {
    Objects.requireNonNull(normalTerrain, "城市正常地形函数不能为空");
    Objects.requireNonNull(mountainMaskNoise, "城市山体遮罩噪声函数不能为空");
    Objects.requireNonNull(ridgeNoise, "城市山脊噪声函数不能为空");
    Objects.requireNonNull(detailNoise, "城市细节噪声函数不能为空");
    if (proximityNeighborRank <= 0) throw new IllegalArgumentException("Region 邻近排名必须为正数");
    if (!Double.isFinite(denseNeighborDistance) || !Double.isFinite(sparseNeighborDistance)
        || denseNeighborDistance < 0.0 || sparseNeighborDistance <= denseNeighborDistance) {
      throw new IllegalArgumentException("Region 密集/稀疏距离范围无效");
    }
    if (!Double.isFinite(denseHillHeight) || denseHillHeight < 0.0
        || !orderedRelief(sparseValleyHeight, sparseMountainHeight)
        || sparseMountainHeight < denseHillHeight) {
      throw new IllegalArgumentException("城市丘陵/高山高度范围无效");
    }
    if (!Double.isFinite(mountainMaskStart) || !Double.isFinite(mountainMaskFull)
        || mountainMaskFull <= mountainMaskStart
        || !Double.isFinite(ridgeThreshold) || ridgeThreshold < 0.0 || ridgeThreshold >= 1.0) {
      throw new IllegalArgumentException("城市山体遮罩/山脊阈值无效");
    }
  }

  @Override
  public double compute(FunctionContext context) {
    double normal = normalTerrain.compute(context);
    TerraTerrainLookup.CityTerrainSample sample = TerraTerrainLookup.cityTerrainAt(
        context.blockX(), context.blockY(), context.blockZ(),
        proximityNeighborRank, sparseNeighborDistance
    );
    if (!sample.hasTerrainInfluence()) return normal;

    CityTerrainProfile profile = sample.profile();
    double overlayWeight;
    double targetSurfaceY;
    if (profile.groundY() < 0) {
      overlayWeight = sample.regionWeight();
      targetSurfaceY = profile.groundY();
    } else {
      overlayWeight = Math.max(sample.regionWeight(), sample.cityWeight());
      if (sample.regionWeight() == 1.0) {
        targetSurfaceY = profile.groundY();
      } else {
        double sparsity = CityTerrainMath.sparsityWeight(
            sample.neighborRegionDistance(), denseNeighborDistance, sparseNeighborDistance
        );
        targetSurfaceY = CityTerrainMath.targetSurfaceY(
            profile,
            mountainMaskNoise.compute(context),
            ridgeNoise.compute(context),
            detailNoise.compute(context),
            sample.regionWeight(),
            sparsity,
            denseHillHeight,
            sparseValleyHeight,
            sparseMountainHeight,
            mountainMaskStart,
            mountainMaskFull,
            ridgeThreshold,
            maximumSurfaceY
        );
      }
    }
    if (overlayWeight == 0.0) return normal;
    return CityTerrainMath.evaluate(
        normal, context.blockY(), profile, targetSurfaceY, overlayWeight
    ).finalDensity();
  }

  @Override
  public void fillArray(double[] array, ContextProvider contextProvider) {
    contextProvider.fillAllDirectly(array, this);
  }

  @Override
  public DensityFunction mapAll(Visitor visitor) {
    return visitor.apply(new TerraCityRegionDensityFunction(
        normalTerrain.mapAll(visitor),
        mountainMaskNoise.mapAll(visitor),
        ridgeNoise.mapAll(visitor),
        detailNoise.mapAll(visitor),
        proximityNeighborRank,
        denseNeighborDistance,
        sparseNeighborDistance,
        denseHillHeight,
        sparseValleyHeight,
        sparseMountainHeight,
        mountainMaskStart,
        mountainMaskFull,
        ridgeThreshold,
        maximumSurfaceY
    ));
  }

  @Override
  public double minValue() {
    return Math.min(normalTerrain.minValue(), CityTerrainMath.HARD_AIR_DENSITY);
  }

  @Override
  public double maxValue() {
    return Math.max(normalTerrain.maxValue(), CityTerrainProfile.MAX_PLANE_AMPLITUDE);
  }

  @Override
  public KeyDispatchDataCodec<? extends DensityFunction> codec() {
    return CODEC;
  }

  private static boolean orderedRelief(double minimum, double maximum) {
    return Double.isFinite(minimum) && Double.isFinite(maximum)
        && minimum >= 0.0 && maximum >= minimum;
  }
}
