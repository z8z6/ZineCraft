package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/** Chunk 栅格上完成连接生长后的城市移动地块计划。 */
public record MobileCityLayout(
    PlanarPoint cityCore,
    int usableChunkArea,
    List<UrbanPlot> plots,
    List<UrbanRoad> roads,
    double coverage
) {
  public MobileCityLayout {
    Objects.requireNonNull(cityCore, "城市核心不能为空");
    if (usableChunkArea <= 0) throw new IllegalArgumentException("城市可用 Chunk 面积必须为正数");
    plots = List.copyOf(Objects.requireNonNull(plots, "移动地块不能为空"));
    roads = List.copyOf(Objects.requireNonNull(roads, "城市道路不能为空"));
    if (!Double.isFinite(coverage) || coverage < 0.0 || coverage > 1.0) {
      throw new IllegalArgumentException("移动地块覆盖率必须位于 [0, 1]");
    }
  }
}
