package com.cxxcxx.zinecraft.api.world.layout;

/**
 * 城市或城区规划时请求的槽位数量。
 */
public enum LayoutSlotCount {
  SLOTS_5(5),
  SLOTS_50(50),
  SLOTS_100(100),
  SLOTS_150(150);

  private final int count;

  LayoutSlotCount(int count) {
    this.count = count;
  }

  public int count() {
    return count;
  }
}
