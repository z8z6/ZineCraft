package com.cxxcxx.zinecraft.api.world.city;

import java.util.Objects;

/** 两个移动地块之间同步规划的轴向 Chunk 道路。 */
public record UrbanRoad(int fromPlotId, int toPlotId, ChunkRectangle chunkArea) {
  public UrbanRoad {
    if (fromPlotId < 0 || toPlotId < 0 || fromPlotId == toPlotId) {
      throw new IllegalArgumentException("道路必须连接两个不同的非负地块 ID");
    }
    Objects.requireNonNull(chunkArea, "道路 Chunk 范围不能为空");
  }
}
