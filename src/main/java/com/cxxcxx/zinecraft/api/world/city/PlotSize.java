package com.cxxcxx.zinecraft.api.world.city;

/** 移动地块的离散 Chunk 尺寸。 */
public record PlotSize(int widthChunks, int lengthChunks) {
  public PlotSize {
    if (widthChunks <= 0 || lengthChunks <= 0) {
      throw new IllegalArgumentException("移动地块的 Chunk 长宽必须为正数");
    }
  }

  public int areaChunks() {
    return Math.multiplyExact(widthChunks, lengthChunks);
  }

  public PlotSize rotated() {
    return new PlotSize(lengthChunks, widthChunks);
  }
}
