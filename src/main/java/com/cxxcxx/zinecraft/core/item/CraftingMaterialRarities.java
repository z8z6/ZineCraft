package com.cxxcxx.zinecraft.core.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps Arknights workshop recipe tiers to vanilla item rarity for Terra crafting materials.
 */
public final class CraftingMaterialRarities {
  private static final Map<String, Integer> RECIPE_TIERS = recipeTiers();

  private CraftingMaterialRarities() {
  }

  private static Map<String, Integer> recipeTiers() {
    Map<String, Integer> tiers = new HashMap<>();
    // Tier 1: basic grey materials.
    put(tiers, 1, "orirock", "ester_raw", "sugar_substitute", "damaged_device", "oriron_shard", "diketon");
    // Tier 2: first-stage green workshop materials and equivalent local intermediates.
    put(tiers, 2, "orirock_cube", "originium_powder", "polyester", "sugar", "device", "oriron", "aketone");
    // Tier 3: blue workshop materials, including materials obtained directly from ore processing.
    put(tiers, 3, "originite", "orirock_cluster", "grindstone", "manganese_ore", "rma70_12",
        "crystal_element", "polyester_group", "sugar_group", "compound_cutting_fluid",
        "semi_synthetic_solvent", "integrated_device", "oriron_group", "polyketon", "loxic_kohl", "gel",
        "twisted_alcohol");
    // Tier 4: purple advanced workshop outputs.
    put(tiers, 4, "orirock_concentration", "grindstone_pentahydrate", "manganese_trihydrate", "rma70_24",
        "crystal_group", "polyester_block",
        "sugar_polymer", "cutting_fluid_solution", "refined_solvent", "optimized_device",
        "oriron_cluster", "keton_colloid", "incandescent_alloy", "coagulating_gel", "white_horse_kohl");
    // Tier 5: gold workshop outputs and the local protocol-originium end product.
    put(tiers, 5, "protocol_originium", "crystalline_circuit", "bipolar_nanosheet", "d32_steel",
        "polymer_agent");
    return Map.copyOf(tiers);
  }

  public static Rarity rarity(String path) {
    int tier = RECIPE_TIERS.getOrDefault(path, 1);
    return switch (tier) {
      case 1 -> Rarity.COMMON;
      case 2 -> Rarity.UNCOMMON;
      case 3 -> Rarity.RARE;
      default -> Rarity.EPIC;
    };
  }

  static Item.Properties properties(String path) {
    return new Item.Properties().rarity(rarity(path));
  }

  public static int recipeTier(String path) {
    return RECIPE_TIERS.getOrDefault(path, 1);
  }

  private static void put(Map<String, Integer> tiers, int tier, String... paths) {
    for (String path : paths) {
      if (tiers.put(path, tier) != null) {
        throw new IllegalStateException("Duplicate crafting material rarity: " + path);
      }
    }
  }
}
