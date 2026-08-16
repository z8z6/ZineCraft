package com.cxxcxx.zinecraft.core.dimension;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerraMobSpawnPolicyTest {
  @Test
  void allowsFriendlyMobCategories() {
    assertTrue(TerraMobSpawnPolicy.allowsSpawn(true, false, true));
  }

  @Test
  void rejectsMonstersUnlessTheyAreNationalResidents() {
    assertFalse(TerraMobSpawnPolicy.allowsSpawn(false, false, true));
    assertTrue(TerraMobSpawnPolicy.allowsSpawn(false, true, true));
  }

  @Test
  void nonNaturalSpawnSourcesBypassTheRule() {
    assertTrue(TerraMobSpawnPolicy.allowsSpawn(false, false, false));
  }
}
