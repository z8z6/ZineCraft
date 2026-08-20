package com.cxxcxx.zinecraft.api.world.city;

public enum CityRoadClass {
  MAIN(15),
  SECONDARY(9),
  LOCAL(5),
  SERVICE(3);

  private final int defaultWidth;

  CityRoadClass(int defaultWidth) {
    this.defaultWidth = defaultWidth;
  }

  public int defaultWidth() {
    return defaultWidth;
  }
}
