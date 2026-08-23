package com.cxxcxx.zinecraft.api.world.city;

/** 城市移动地块生成失败的稳定分类。 */
public enum LayoutFailureReason {
  CITY_TOO_SMALL,
  MANDATORY_PLOTS_CANNOT_FIT,
  MINIMUM_PLOT_COUNT_CANNOT_FIT,
  INVALID_CONFIGURATION
}
