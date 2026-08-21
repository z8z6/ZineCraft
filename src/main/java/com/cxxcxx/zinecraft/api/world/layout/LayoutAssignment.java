package com.cxxcxx.zinecraft.api.world.layout;

import java.util.Objects;

/**
 * 一个布局槽位及其获配的子元素。
 */
public record LayoutAssignment<T extends WeightedLayoutElement>(LayoutSlot slot, T element) {
  public LayoutAssignment {
    Objects.requireNonNull(slot, "布局槽位不能为空");
    Objects.requireNonNull(element, "布局槽位子元素不能为空");
  }
}
