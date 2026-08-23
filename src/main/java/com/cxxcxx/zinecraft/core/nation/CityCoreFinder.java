package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.Objects;

/** 以 Chunk 中心距边界最大值近似城市不可达极点。 */
public final class CityCoreFinder {
  public PlanarPoint find(CityGrid grid) {
    Objects.requireNonNull(grid, "城市栅格不能为空");
    PlanarPoint best = null;
    double bestDistance = -1.0;
    for (int z = grid.minChunkZ(); z < grid.maxChunkZExclusive(); z++) {
      for (int x = grid.minChunkX(); x < grid.maxChunkXExclusive(); x++) {
        if (grid.state(x, z) != CityGrid.CellState.EMPTY) continue;
        double blockX = x * 16.0 + 8.0;
        double blockZ = z * 16.0 + 8.0;
        double distance = grid.distanceToBoundaryBlocks(blockX, blockZ);
        if (distance > bestDistance) {
          bestDistance = distance;
          best = new PlanarPoint(blockX, blockZ);
        }
      }
    }
    if (best == null) throw new IllegalArgumentException("城市多边形内没有完整 Chunk");
    return best;
  }
}
