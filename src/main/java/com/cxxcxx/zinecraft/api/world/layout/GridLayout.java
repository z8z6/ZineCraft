package com.cxxcxx.zinecraft.api.world.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * 将槽位排列为尽量接近正方形的棋盘格。
 *
 * <pre>
 * slotCount = 7
 *
 *   0   1   2
 *   3   4   5
 *       6
 * </pre>
 */
public final class GridLayout implements Layout {
  public static final GridLayout INSTANCE = new GridLayout();

  private GridLayout() {
  }

  private static double normalize(double position, int size) {
    return (position + 0.5) * 2.0 / size - 1.0;
  }

  @Override
  public String id() {
    return "grid";
  }

  @Override
  public List<LayoutSlot> createSlots(int slotCount) {
    if (slotCount < 0) throw new IllegalArgumentException("棋盘格槽位数不能为负数：" + slotCount);
    if (slotCount == 0) return List.of();

    int columns = (int) Math.ceil(Math.sqrt(slotCount));
    int rows = Math.ceilDiv(slotCount, columns);
    ArrayList<LayoutSlot> slots = new ArrayList<>(slotCount);
    for (int index = 0; index < slotCount; index++) {
      int row = index / columns;
      int column = index % columns;
      int entriesInRow = Math.min(columns, slotCount - row * columns);
      double rowOffset = (columns - entriesInRow) / 2.0;
      slots.add(new LayoutSlot(
          index,
          normalize(column + rowOffset, columns),
          normalize(row, rows)
      ));
    }
    return List.copyOf(slots);
  }
}
