package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/** 与生成器解耦的一次不可变布局调试快照。 */
public record LayoutDebugEvent(
    String stage,
    PlanarPoint cityCore,
    List<ChunkRectangle> plots,
    List<UrbanRoad> roads,
    Map<CandidateRejectReason, Integer> rejectedCandidates,
    double coverage
) {
  public LayoutDebugEvent {
    Objects.requireNonNull(stage, "调试阶段不能为空");
    Objects.requireNonNull(cityCore, "城市核心不能为空");
    plots = List.copyOf(Objects.requireNonNull(plots, "调试地块不能为空"));
    roads = List.copyOf(Objects.requireNonNull(roads, "调试道路不能为空"));
    rejectedCandidates = Map.copyOf(Objects.requireNonNull(rejectedCandidates, "候选拒绝统计不能为空"));
  }
}
