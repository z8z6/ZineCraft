package com.cxxcxx.zinecraft.api.weapon.tacz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaczReloadTimingsTest {
  @Test
  void shellReloadKeepsIntroLoopAndEndingStages() {
    TaczReloadTimings timings = new TaczReloadTimings(true, 27, 12, 14, 4, 43, 26);

    assertEquals(27, timings.firstFeedTicks(true));
    assertEquals(12, timings.firstFeedTicks(false));
    assertEquals(60, timings.durationTicks(true, 3));
    assertEquals(45, timings.durationTicks(false, 3));
  }

  @Test
  void magazineReloadDurationDoesNotScaleWithMissingRounds() {
    TaczReloadTimings timings = new TaczReloadTimings(false, 24, 18, 1, 0, 36, 28);

    assertEquals(36, timings.durationTicks(true, 30));
    assertEquals(28, timings.durationTicks(false, 29));
  }
}
