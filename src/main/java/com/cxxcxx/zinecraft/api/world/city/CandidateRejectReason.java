package com.cxxcxx.zinecraft.api.world.city;

/** 调试器可聚合的地块候选拒绝原因。 */
public enum CandidateRejectReason {
  OUTSIDE_CITY,
  OVERLAPS_PLOT,
  OVERLAPS_ROAD,
  INVALID_ROAD_GAP,
  COVERAGE_LIMIT,
  TYPE_MAX_COUNT,
  INVALID_SIZE,
  NO_CONNECTION,
  RESERVED_TERRAIN
}
