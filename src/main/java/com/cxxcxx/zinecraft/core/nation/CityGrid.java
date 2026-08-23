package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.world.city.ChunkRectangle;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** 将城市多边形栅格化为 Chunk 单元，并维护 O(面积) 的占用查询。 */
public final class CityGrid {
  public enum CellState { OUTSIDE, EMPTY, PLOT, ROAD, RESERVED }

  private final List<PlanarPoint> boundary;
  private final int minChunkX;
  private final int minChunkZ;
  private final int width;
  private final int length;
  private final byte[] states;
  private final int usableChunkArea;

  public CityGrid(List<PlanarPoint> boundary) {
    this.boundary = List.copyOf(Objects.requireNonNull(boundary, "城市边界不能为空"));
    if (this.boundary.size() < 3) throw new IllegalArgumentException("城市边界至少需要三个点");
    double minX = this.boundary.stream().mapToDouble(PlanarPoint::x).min().orElseThrow();
    double maxX = this.boundary.stream().mapToDouble(PlanarPoint::x).max().orElseThrow();
    double minZ = this.boundary.stream().mapToDouble(PlanarPoint::z).min().orElseThrow();
    double maxZ = this.boundary.stream().mapToDouble(PlanarPoint::z).max().orElseThrow();
    minChunkX = (int) Math.floor(minX / 16.0);
    minChunkZ = (int) Math.floor(minZ / 16.0);
    int maxChunkX = (int) Math.ceil(maxX / 16.0) - 1;
    int maxChunkZ = (int) Math.ceil(maxZ / 16.0) - 1;
    width = Math.max(0, maxChunkX - minChunkX + 1);
    length = Math.max(0, maxChunkZ - minChunkZ + 1);
    states = new byte[Math.multiplyExact(width, length)];
    Arrays.fill(states, (byte) CellState.OUTSIDE.ordinal());
    int usable = 0;
    for (int z = minChunkZ; z <= maxChunkZ; z++) {
      for (int x = minChunkX; x <= maxChunkX; x++) {
        if (chunkFullyInside(x, z)) {
          states[index(x, z)] = (byte) CellState.EMPTY.ordinal();
          usable++;
        }
      }
    }
    usableChunkArea = usable;
  }

  public int usableChunkArea() {
    return usableChunkArea;
  }

  public int minChunkX() {
    return minChunkX;
  }

  public int minChunkZ() {
    return minChunkZ;
  }

  public int maxChunkXExclusive() {
    return minChunkX + width;
  }

  public int maxChunkZExclusive() {
    return minChunkZ + length;
  }

  public CellState state(int chunkX, int chunkZ) {
    if (!inBounds(chunkX, chunkZ)) return CellState.OUTSIDE;
    return CellState.values()[states[index(chunkX, chunkZ)]];
  }

  public boolean isEmpty(ChunkRectangle area) {
    for (int z = area.minChunkZ(); z < area.maxChunkZExclusive(); z++) {
      for (int x = area.minChunkX(); x < area.maxChunkXExclusive(); x++) {
        if (state(x, z) != CellState.EMPTY) return false;
      }
    }
    return true;
  }

  public boolean isUsable(ChunkRectangle area) {
    for (int z = area.minChunkZ(); z < area.maxChunkZExclusive(); z++) {
      for (int x = area.minChunkX(); x < area.maxChunkXExclusive(); x++) {
        if (state(x, z) == CellState.OUTSIDE || state(x, z) == CellState.RESERVED) return false;
      }
    }
    return true;
  }

  public void occupy(ChunkRectangle area, CellState state) {
    if (state == CellState.OUTSIDE || state == CellState.EMPTY) {
      throw new IllegalArgumentException("只能写入已占用的城市单元状态");
    }
    for (int z = area.minChunkZ(); z < area.maxChunkZExclusive(); z++) {
      for (int x = area.minChunkX(); x < area.maxChunkXExclusive(); x++) {
        if (!inBounds(x, z) || this.state(x, z) == CellState.OUTSIDE) {
          throw new IllegalArgumentException("占用范围超出城市可用区域");
        }
        states[index(x, z)] = (byte) state.ordinal();
      }
    }
  }

  public double distanceToBoundaryBlocks(double x, double z) {
    double best = Double.POSITIVE_INFINITY;
    for (int index = 0; index < boundary.size(); index++) {
      PlanarPoint start = boundary.get(index);
      PlanarPoint end = boundary.get((index + 1) % boundary.size());
      best = Math.min(best, pointSegmentDistance(x, z, start, end));
    }
    return best;
  }

  private boolean chunkFullyInside(int chunkX, int chunkZ) {
    double minX = chunkX * 16.0;
    double minZ = chunkZ * 16.0;
    double maxX = minX + 16.0;
    double maxZ = minZ + 16.0;
    return contains(minX, minZ) && contains(maxX, minZ)
        && contains(maxX, maxZ) && contains(minX, maxZ)
        && contains((minX + maxX) * 0.5, (minZ + maxZ) * 0.5);
  }

  private boolean contains(double x, double z) {
    boolean inside = false;
    for (int i = 0, j = boundary.size() - 1; i < boundary.size(); j = i++) {
      PlanarPoint first = boundary.get(i);
      PlanarPoint second = boundary.get(j);
      if (pointOnSegment(x, z, first, second)) return true;
      if ((first.z() > z) != (second.z() > z)
          && x < (second.x() - first.x()) * (z - first.z()) / (second.z() - first.z()) + first.x()) {
        inside = !inside;
      }
    }
    return inside;
  }

  private boolean inBounds(int x, int z) {
    return x >= minChunkX && x < minChunkX + width && z >= minChunkZ && z < minChunkZ + length;
  }

  private int index(int x, int z) {
    return (z - minChunkZ) * width + x - minChunkX;
  }

  private static boolean pointOnSegment(double x, double z, PlanarPoint start, PlanarPoint end) {
    double cross = (x - start.x()) * (end.z() - start.z()) - (z - start.z()) * (end.x() - start.x());
    double scale = Math.max(1.0, Math.hypot(end.x() - start.x(), end.z() - start.z()));
    if (Math.abs(cross) > 1.0E-9 * scale) return false;
    return x >= Math.min(start.x(), end.x()) - 1.0E-9 && x <= Math.max(start.x(), end.x()) + 1.0E-9
        && z >= Math.min(start.z(), end.z()) - 1.0E-9 && z <= Math.max(start.z(), end.z()) + 1.0E-9;
  }

  private static double pointSegmentDistance(double x, double z, PlanarPoint start, PlanarPoint end) {
    double dx = end.x() - start.x();
    double dz = end.z() - start.z();
    double lengthSquared = dx * dx + dz * dz;
    if (lengthSquared == 0.0) return Math.hypot(x - start.x(), z - start.z());
    double t = Math.max(0.0, Math.min(1.0, ((x - start.x()) * dx + (z - start.z()) * dz) / lengthSquared));
    return Math.hypot(x - (start.x() + t * dx), z - (start.z() + t * dz));
  }
}
