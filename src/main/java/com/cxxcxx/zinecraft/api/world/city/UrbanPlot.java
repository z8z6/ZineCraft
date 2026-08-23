package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;

import java.util.Objects;

/** 一块已接受的、轴向且 Chunk 对齐的移动城市地块。 */
public record UrbanPlot(int id, TerraCityRegionBuilder type, ChunkRectangle chunkArea) {
  public UrbanPlot {
    if (id < 0) throw new IllegalArgumentException("移动地块 ID 不能为负数");
    Objects.requireNonNull(type, "移动地块类型不能为空");
    Objects.requireNonNull(chunkArea, "移动地块 Chunk 范围不能为空");
  }
}
