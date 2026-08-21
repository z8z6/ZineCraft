package com.cxxcxx.zinecraft.api.world.layout;

/**
 * 布局内的归一化二维槽位；坐标由后续城市生成步骤映射到实际尺寸。
 */
public record LayoutSlot(int index, double x, double z) {
  public LayoutSlot {
    if (index < 0) throw new IllegalArgumentException("布局槽位索引不能为负数：" + index);
    if (!Double.isFinite(x) || !Double.isFinite(z)) {
      throw new IllegalArgumentException("布局槽位坐标必须为有限数：" + index);
    }
  }
}
