package com.cxxcxx.zinecraft.core.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CraftingMaterialRaritiesTest {
  @Test
  void mapsWorkshopRecipeTiersToIncreasingRarityBands() {
    assertEquals(1, CraftingMaterialRarities.recipeTier("orirock"));
    assertEquals(2, CraftingMaterialRarities.recipeTier("orirock_cube"));
    assertEquals(3, CraftingMaterialRarities.recipeTier("orirock_cluster"));
    assertEquals(4, CraftingMaterialRarities.recipeTier("orirock_concentration"));
  }

  @Test
  void advancedWorkshopOutputsShareTheEpicBand() {
    assertEquals(4, CraftingMaterialRarities.recipeTier("optimized_device"));
    assertEquals(5, CraftingMaterialRarities.recipeTier("bipolar_nanosheet"));
    assertEquals(5, CraftingMaterialRarities.recipeTier("d32_steel"));
  }

  @Test
  void nonMaterialsKeepTheVanillaDefault() {
    assertEquals(1, CraftingMaterialRarities.recipeTier("example_item"));
  }
}
