package com.cxxcxx.zinecraft.api.world.dimension;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate.ParameterPoint;

import java.util.Objects;

/**
 * 维度群系资源键及其多噪声气候点。
 *
 * @param biome      群系资源键
 * @param parameters 多噪声群系源气候点
 */
public record DimensionBiome(ResourceKey<Biome> biome, ParameterPoint parameters) {
  public DimensionBiome {
    Objects.requireNonNull(biome, "维度群系资源键不能为空");
    Objects.requireNonNull(parameters, "维度群系气候点不能为空");
  }
}
