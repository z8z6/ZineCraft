package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;

/** 采用左闭右开范围的 Chunk 对齐轴向矩形。 */
public record ChunkRectangle(int minChunkX, int minChunkZ, int widthChunks, int lengthChunks) {
  public ChunkRectangle {
    if (widthChunks <= 0 || lengthChunks <= 0) {
      throw new IllegalArgumentException("Chunk 矩形的长宽必须为正数");
    }
  }

  public int maxChunkXExclusive() {
    return Math.addExact(minChunkX, widthChunks);
  }

  public int maxChunkZExclusive() {
    return Math.addExact(minChunkZ, lengthChunks);
  }

  public int areaChunks() {
    return Math.multiplyExact(widthChunks, lengthChunks);
  }

  public boolean contains(int chunkX, int chunkZ) {
    return chunkX >= minChunkX && chunkX < maxChunkXExclusive()
        && chunkZ >= minChunkZ && chunkZ < maxChunkZExclusive();
  }

  public boolean intersects(ChunkRectangle other) {
    return minChunkX < other.maxChunkXExclusive() && maxChunkXExclusive() > other.minChunkX
        && minChunkZ < other.maxChunkZExclusive() && maxChunkZExclusive() > other.minChunkZ;
  }

  public ChunkRectangle expand(int chunks) {
    if (chunks < 0) throw new IllegalArgumentException("扩展 Chunk 数不能为负数");
    return new ChunkRectangle(
        Math.subtractExact(minChunkX, chunks),
        Math.subtractExact(minChunkZ, chunks),
        Math.addExact(widthChunks, chunks * 2),
        Math.addExact(lengthChunks, chunks * 2)
    );
  }

  public PlanarPoint centerBlocks() {
    return new PlanarPoint(
        (minChunkX + widthChunks / 2.0) * 16.0,
        (minChunkZ + lengthChunks / 2.0) * 16.0
    );
  }

  public PlanarRectangle toBlockRectangle() {
    return new PlanarRectangle(centerBlocks(), widthChunks * 8.0, lengthChunks * 8.0, 0.0);
  }
}
