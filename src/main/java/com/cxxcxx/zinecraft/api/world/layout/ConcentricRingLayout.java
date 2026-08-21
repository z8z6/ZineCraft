package com.cxxcxx.zinecraft.api.world.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * 从内到外生成多个同心圆，每一环都从北侧开始顺时针排列。
 *
 * <pre>
 * slotCount = 16, slotsPerRing = 8
 *
 *                 8
 *          15     0     9
 *      14      7     1      10
 *              6     2
 *      13      5     3      11
 *          12     4
 *
 * 内环：0—7，外环：8—15。
 * </pre>
 */
public final class ConcentricRingLayout implements Layout {
  public static final int DEFAULT_SLOTS_PER_RING = 8;
  public static final ConcentricRingLayout INSTANCE = new ConcentricRingLayout(DEFAULT_SLOTS_PER_RING);

  private final int slotsPerRing;

  public ConcentricRingLayout(int slotsPerRing) {
    if (slotsPerRing <= 0) throw new IllegalArgumentException("同心圆每环槽位数必须为正数：" + slotsPerRing);
    this.slotsPerRing = slotsPerRing;
  }

  @Override
  public String id() {
    return "concentric_ring";
  }

  @Override
  public List<LayoutSlot> createSlots(int slotCount) {
    if (slotCount < 0) throw new IllegalArgumentException("同心圆布局槽位数不能为负数：" + slotCount);
    if (slotCount == 0) return List.of();

    int ringCount = Math.ceilDiv(slotCount, slotsPerRing);
    ArrayList<LayoutSlot> slots = new ArrayList<>(slotCount);
    for (int ring = 0; ring < ringCount; ring++) {
      int ringStart = ring * slotsPerRing;
      int entriesInRing = Math.min(slotsPerRing, slotCount - ringStart);
      double radius = (ring + 1.0) / ringCount;
      for (int position = 0; position < entriesInRing; position++) {
        int index = ringStart + position;
        double angle = -Math.PI / 2.0 + Math.PI * 2.0 * position / entriesInRing;
        slots.add(new LayoutSlot(index, Math.cos(angle) * radius, Math.sin(angle) * radius));
      }
    }
    return List.copyOf(slots);
  }

  public int slotsPerRing() {
    return slotsPerRing;
  }
}
