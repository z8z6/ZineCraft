package com.cxxcxx.zinecraft.api.nation;

/**
 * JourneyMap 地点层级；类型只描述地图表达，不推断官方行政级别。
 */
public enum TerraPlaceType {
  CITY("city", true),
  SETTLEMENT("settlement", true),
  REGION("region", false),
  NATURAL_FEATURE("natural_feature", false);

  private final String id;
  private final boolean urban;

  TerraPlaceType(String id, boolean urban) {
    this.id = id;
    this.urban = urban;
  }

  public String id() {
    return id;
  }

  public boolean isUrban() {
    return urban;
  }
}
