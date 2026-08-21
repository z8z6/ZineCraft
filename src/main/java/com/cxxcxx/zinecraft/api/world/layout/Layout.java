package com.cxxcxx.zinecraft.api.world.layout;

import java.util.List;

/**
 * 生成一组有稳定顺序的二维布局槽位。
 */
public interface Layout {
  String id();

  List<LayoutSlot> createSlots(int slotCount);
}
