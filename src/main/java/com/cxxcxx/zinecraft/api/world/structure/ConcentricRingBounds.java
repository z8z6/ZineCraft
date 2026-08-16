package com.cxxcxx.zinecraft.api.world.structure;

/**
 * Pure-Java bounds for Minecraft 1.21.1 concentric-ring placement.
 */
public final class ConcentricRingBounds {
  public static final int GUARANTEED_LANDMARK_RADIUS_BLOCKS = 5000;

  private ConcentricRingBounds() {
  }

  /**
   * Conservative upper bound derived from vanilla's one-ring formula:
   * {@code (4d + random[-1.25d, 1.25d]) chunks}. Vanilla searches a 112-block
   * square (not a circle) for the preferred biome; the final 240 blocks
   * conservatively cover that diagonal plus both chunk-coordinate roundings.
   */
  public static int maximumRadiusBlocks(int ringDistance) {
    if (ringDistance <= 0) {
      throw new IllegalArgumentException("环距离必须大于 0");
    }
    return (int) Math.ceil(5.25D * ringDistance * 16.0D) + 240;
  }
}
