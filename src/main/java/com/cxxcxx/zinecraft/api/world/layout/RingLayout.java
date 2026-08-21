package com.cxxcxx.zinecraft.api.world.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * 将槽位从北侧开始沿单位圆顺时针等角度排列。
 *
 * <pre>
 * slotCount = 8
 *
 *           0
 *       7       1
 *     6           2
 *       5       3
 *           4
 * </pre>
 */
public final class RingLayout implements Layout {
  public static final RingLayout INSTANCE = new RingLayout();

  private RingLayout() {
  }

  @Override
  public String id() {
    return "ring";
  }

  @Override
  public List<LayoutSlot> createSlots(int slotCount) {
    if (slotCount < 0) throw new IllegalArgumentException("环状布局槽位数不能为负数：" + slotCount);
    if (slotCount == 0) return List.of();

    ArrayList<LayoutSlot> slots = new ArrayList<>(slotCount);
    for (int index = 0; index < slotCount; index++) {
      double angle = -Math.PI / 2.0 + Math.PI * 2.0 * index / slotCount;
      slots.add(new LayoutSlot(index, Math.cos(angle), Math.sin(angle)));
    }
    return List.copyOf(slots);
  }
}
