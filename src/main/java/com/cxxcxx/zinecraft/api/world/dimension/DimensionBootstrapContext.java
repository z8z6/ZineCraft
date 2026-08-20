package com.cxxcxx.zinecraft.api.world.dimension;

import com.cxxcxx.zinecraft.api.registry.builder.DimensionBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.Objects;

/**
 * 自定义维度区块生成器工厂可用的动态注册表上下文。
 *
 * @param builder         当前维度声明
 * @param biomeSource     根据维度群系气候点创建的默认群系源
 * @param biomeParameters 群系 Holder 多噪声参数列表
 * @param biomes          群系动态注册表查询器
 * @param noiseSettings   当前噪声设置 Holder
 */
public record DimensionBootstrapContext(
    DimensionBuilder builder,
    MultiNoiseBiomeSource biomeSource,
    ParameterList<Holder<Biome>> biomeParameters,
    HolderGetter<Biome> biomes,
    Holder<NoiseGeneratorSettings> noiseSettings
) {
  public DimensionBootstrapContext {
    Objects.requireNonNull(builder, "维度 builder 不能为空");
    Objects.requireNonNull(biomeSource, "群系源不能为空");
    Objects.requireNonNull(biomeParameters, "群系气候参数不能为空");
    Objects.requireNonNull(biomes, "群系查询器不能为空");
    Objects.requireNonNull(noiseSettings, "噪声设置不能为空");
  }
}
