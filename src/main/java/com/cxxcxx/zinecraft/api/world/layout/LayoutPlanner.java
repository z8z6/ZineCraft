package com.cxxcxx.zinecraft.api.world.layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

/**
 * 按权重为布局生成的每个槽位分配一个子元素。
 */
public final class LayoutPlanner {
  private static <T extends WeightedLayoutElement> T select(
      List<T> candidates,
      HashSet<T> usedUniqueElements,
      RandomGenerator random,
      LayoutSlot slot
  ) {
    long totalWeight = 0;
    for (T candidate : candidates) {
      if (!candidate.isUnique() || !usedUniqueElements.contains(candidate)) {
        totalWeight = Math.addExact(totalWeight, candidate.weight());
      }
    }
    if (totalWeight == 0) {
      throw new IllegalStateException("没有可分配到槽位的子元素：" + slot.index());
    }

    long cursor = random.nextLong(totalWeight);
    for (T candidate : candidates) {
      if (candidate.isUnique() && usedUniqueElements.contains(candidate)) continue;
      if (cursor < candidate.weight()) return candidate;
      cursor -= candidate.weight();
    }
    throw new IllegalStateException("布局权重选择未命中：" + slot.index());
  }

  public <T extends WeightedLayoutElement> List<LayoutAssignment<T>> plan(
      Layout layout,
      int slotCount,
      List<T> elements,
      RandomGenerator random
  ) {
    Objects.requireNonNull(layout, "布局不能为空");
    Objects.requireNonNull(elements, "布局子元素清单不能为空");
    Objects.requireNonNull(random, "布局随机源不能为空");
    if (slotCount < 0) throw new IllegalArgumentException("布局槽位数不能为负数：" + slotCount);
    List<LayoutSlot> slots = List.copyOf(layout.createSlots(slotCount));
    if (slots.size() != slotCount) {
      throw new IllegalStateException("布局生成的槽位数与请求不一致：" + layout.id());
    }
    if (!slots.isEmpty() && elements.isEmpty()) {
      throw new IllegalArgumentException("存在布局槽位时必须提供子元素");
    }

    List<T> candidates = List.copyOf(elements);
    HashSet<T> declaredElements = new HashSet<>();
    for (T candidate : candidates) {
      if (!declaredElements.add(candidate)) {
        throw new IllegalArgumentException("布局子元素不能重复声明：" + candidate);
      }
    }
    HashSet<Integer> slotIndexes = new HashSet<>();
    for (LayoutSlot slot : slots) {
      if (!slotIndexes.add(slot.index())) {
        throw new IllegalStateException("布局生成了重复槽位索引：" + slot.index());
      }
    }
    HashSet<T> usedUniqueElements = new HashSet<>();
    ArrayList<LayoutAssignment<T>> assignments = new ArrayList<>(slots.size());
    for (LayoutSlot slot : slots) {
      T selected = select(candidates, usedUniqueElements, random, slot);
      assignments.add(new LayoutAssignment<>(slot, selected));
      if (selected.isUnique()) usedUniqueElements.add(selected);
    }
    return List.copyOf(assignments);
  }
}
