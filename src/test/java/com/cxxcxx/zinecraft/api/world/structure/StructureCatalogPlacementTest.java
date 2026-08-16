package com.cxxcxx.zinecraft.api.world.structure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StructureCatalogPlacementTest {
  @Test
  void largestNationalRingRemainsInsideFiveThousandBlocks() {
    assertEquals(4944, ConcentricRingBounds.maximumRadiusBlocks(56));
    assertTrue(
        ConcentricRingBounds.maximumRadiusBlocks(56)
            <= ConcentricRingBounds.GUARANTEED_LANDMARK_RADIUS_BLOCKS
    );
  }

  @Test
  void invalidRingDistanceIsRejected() {
    assertThrows(IllegalArgumentException.class, () -> ConcentricRingBounds.maximumRadiusBlocks(0));
  }
}
