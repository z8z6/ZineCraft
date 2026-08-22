package com.cxxcxx.zinecraft.core.worldgen.density;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Objects;

/** 在各 City Region 的移动地块范围内切换为平地密度，其余位置保留正常地形。 */
public record TerraCityRegionDensityFunction(
    DensityFunction normalTerrain,
    DensityFunction hillTerrain,
    DensityFunction surfaceFlatTerrain,
    DensityFunction undergroundFlatTerrain,
    double plotTransitionWidth
) implements DensityFunction {
  public static final KeyDispatchDataCodec<TerraCityRegionDensityFunction> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(instance -> instance.group(
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("normal_terrain")
              .forGetter(TerraCityRegionDensityFunction::normalTerrain),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("hill_terrain")
              .forGetter(TerraCityRegionDensityFunction::hillTerrain),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("surface_flat_terrain")
              .forGetter(TerraCityRegionDensityFunction::surfaceFlatTerrain),
          DensityFunction.HOLDER_HELPER_CODEC.fieldOf("underground_flat_terrain")
              .forGetter(TerraCityRegionDensityFunction::undergroundFlatTerrain),
          com.mojang.serialization.Codec.doubleRange(0.0, 512.0).fieldOf("plot_transition_width")
              .forGetter(TerraCityRegionDensityFunction::plotTransitionWidth)
      ).apply(instance, TerraCityRegionDensityFunction::new))
  );

  public TerraCityRegionDensityFunction {
    Objects.requireNonNull(normalTerrain, "Region 正常地形函数不能为空");
    Objects.requireNonNull(hillTerrain, "Region 山丘地形函数不能为空");
    Objects.requireNonNull(surfaceFlatTerrain, "Region 地表平地函数不能为空");
    Objects.requireNonNull(undergroundFlatTerrain, "Region 地下平地函数不能为空");
    if (!Double.isFinite(plotTransitionWidth) || plotTransitionWidth < 0.0) {
      throw new IllegalArgumentException("移动地块过渡宽度必须是有限非负数");
    }
  }

  @Override
  public double compute(FunctionContext context) {
    TerraTerrainLookup.RegionTerrainBlend blend = TerraTerrainLookup.regionTerrainAt(
        context.blockX(), context.blockY(), context.blockZ(), plotTransitionWidth
    );
    DensityFunction baseTerrain = blend.baseTerrain() == TerraTerrainLookup.BaseTerrain.HILLS
        ? hillTerrain
        : normalTerrain;
    if (blend.plotKind() == TerraTerrainLookup.PlotKind.NONE) return baseTerrain.compute(context);
    DensityFunction flatTerrain = blend.plotKind() == TerraTerrainLookup.PlotKind.UNDERGROUND
        ? undergroundFlatTerrain
        : surfaceFlatTerrain;
    if (blend.flatWeight() == 1.0) return flatTerrain.compute(context);
    double flatWeight = blend.flatWeight();
    return baseTerrain.compute(context) * (1.0 - flatWeight)
        + flatTerrain.compute(context) * flatWeight;
  }

  @Override
  public void fillArray(double[] array, ContextProvider contextProvider) {
    contextProvider.fillAllDirectly(array, this);
  }

  @Override
  public DensityFunction mapAll(Visitor visitor) {
    return visitor.apply(new TerraCityRegionDensityFunction(
        normalTerrain.mapAll(visitor),
        hillTerrain.mapAll(visitor),
        surfaceFlatTerrain.mapAll(visitor),
        undergroundFlatTerrain.mapAll(visitor),
        plotTransitionWidth
    ));
  }

  @Override
  public double minValue() {
    return Math.min(Math.min(normalTerrain.minValue(), hillTerrain.minValue()),
        Math.min(surfaceFlatTerrain.minValue(), undergroundFlatTerrain.minValue()));
  }

  @Override
  public double maxValue() {
    return Math.max(Math.max(normalTerrain.maxValue(), hillTerrain.maxValue()),
        Math.max(surfaceFlatTerrain.maxValue(), undergroundFlatTerrain.maxValue()));
  }

  @Override
  public KeyDispatchDataCodec<? extends DensityFunction> codec() {
    return CODEC;
  }
}
