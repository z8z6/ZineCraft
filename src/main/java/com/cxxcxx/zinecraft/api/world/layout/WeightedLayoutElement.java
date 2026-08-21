package com.cxxcxx.zinecraft.api.world.layout;

/**
 * 可分配到布局槽位的带权子元素。
 */
public interface WeightedLayoutElement {
  int weight();

  boolean isUnique();
}
