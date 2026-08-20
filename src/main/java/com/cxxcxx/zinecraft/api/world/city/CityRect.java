package com.cxxcxx.zinecraft.api.world.city;

/**
 * 城市中心相对坐标中的半开二维矩形。
 */
public record CityRect(int minX, int minZ, int maxXExclusive, int maxZExclusive) {
  public CityRect {
    if (maxXExclusive <= minX || maxZExclusive <= minZ) {
      throw new IllegalArgumentException("城市矩形必须具有正面积：" + minX + "," + minZ + " -> "
          + maxXExclusive + "," + maxZExclusive);
    }
  }

  public static CityRect sized(int minX, int minZ, int width, int depth) {
    if (width <= 0 || depth <= 0) throw new IllegalArgumentException("城市矩形尺寸必须为正数");
    return new CityRect(minX, minZ, Math.addExact(minX, width), Math.addExact(minZ, depth));
  }

  public int width() {
    return maxXExclusive - minX;
  }

  public int depth() {
    return maxZExclusive - minZ;
  }

  public boolean contains(CityRect other) {
    return minX <= other.minX && minZ <= other.minZ
        && maxXExclusive >= other.maxXExclusive && maxZExclusive >= other.maxZExclusive;
  }

  public boolean contains(int x, int z) {
    return x >= minX && x < maxXExclusive && z >= minZ && z < maxZExclusive;
  }

  public boolean intersects(CityRect other) {
    return minX < other.maxXExclusive && maxXExclusive > other.minX
        && minZ < other.maxZExclusive && maxZExclusive > other.minZ;
  }
}
