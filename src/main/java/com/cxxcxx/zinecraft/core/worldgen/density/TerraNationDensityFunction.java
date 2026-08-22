package com.cxxcxx.zinecraft.core.worldgen.density;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/** 根据泰拉国家边界选择对应子地形的密度函数。 */
public record TerraNationDensityFunction(
    Map<String, DensityFunction> nationTerrains,
    DensityFunction fallback,
    double blendWidth
) implements DensityFunction {
  public static final KeyDispatchDataCodec<TerraNationDensityFunction> CODEC = KeyDispatchDataCodec.of(
      RecordCodecBuilder.mapCodec(instance -> instance.group(
          Codec.unboundedMap(Codec.STRING, DensityFunction.HOLDER_HELPER_CODEC)
              .fieldOf("nations").forGetter(TerraNationDensityFunction::nationTerrains),
          DensityFunction.HOLDER_HELPER_CODEC
              .fieldOf("fallback").forGetter(TerraNationDensityFunction::fallback),
          Codec.doubleRange(0.0, 8192.0)
              .fieldOf("blend_width").forGetter(TerraNationDensityFunction::blendWidth)
      ).apply(instance, TerraNationDensityFunction::new))
  );

  public TerraNationDensityFunction {
    nationTerrains = Collections.unmodifiableMap(new LinkedHashMap<>(Objects.requireNonNull(
        nationTerrains, "国家地形函数不能为空"
    )));
    Objects.requireNonNull(fallback, "国家范围外的后备地形函数不能为空");
    if (!Double.isFinite(blendWidth) || blendWidth < 0.0) {
      throw new IllegalArgumentException("国家边界混合宽度必须是有限非负数");
    }
  }

  @Override
  public double compute(FunctionContext context) {
    TerraTerrainLookup.NationBlend blend = TerraTerrainLookup.nationBlendAt(
        context.blockX(), context.blockY(), context.blockZ(), blendWidth
    );
    DensityFunction primary = nationTerrains.getOrDefault(blend.primary(), fallback);
    if (blend.secondary() == null || blend.secondaryWeight() == 0.0) return primary.compute(context);
    DensityFunction secondary = nationTerrains.getOrDefault(blend.secondary(), fallback);
    double secondaryWeight = blend.secondaryWeight();
    return primary.compute(context) * (1.0 - secondaryWeight)
        + secondary.compute(context) * secondaryWeight;
  }

  @Override
  public void fillArray(double[] array, ContextProvider contextProvider) {
    contextProvider.fillAllDirectly(array, this);
  }

  @Override
  public DensityFunction mapAll(Visitor visitor) {
    LinkedHashMap<String, DensityFunction> mapped = new LinkedHashMap<>();
    nationTerrains.forEach((id, terrain) -> mapped.put(id, terrain.mapAll(visitor)));
    return visitor.apply(new TerraNationDensityFunction(mapped, fallback.mapAll(visitor), blendWidth));
  }

  @Override
  public double minValue() {
    return Math.min(
        fallback.minValue(),
        nationTerrains.values().stream().mapToDouble(DensityFunction::minValue)
            .min().orElse(fallback.minValue())
    );
  }

  @Override
  public double maxValue() {
    return Math.max(
        fallback.maxValue(),
        nationTerrains.values().stream().mapToDouble(DensityFunction::maxValue)
            .max().orElse(fallback.maxValue())
    );
  }

  @Override
  public KeyDispatchDataCodec<? extends DensityFunction> codec() {
    return CODEC;
  }
}
