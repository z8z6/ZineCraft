package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Reads collectibles from every equipped Curios slot and exposes their modifiers to combat systems.
 */
public final class CollectibleCombatStats {
  private CollectibleCombatStats() {
  }

  public static List<CombatStatModifier> modifiers(LivingEntity entity, CombatStat stat) {
    List<CombatStatModifier> result = new ArrayList<>();
    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> stack.getItem() instanceof CollectibleItem
    ).forEach(slot -> {
      if (slot.stack().getItem() instanceof CollectibleItem item) add(item.getSpec().getPower(), stat, result);
    }));
    return List.copyOf(result);
  }

  private static void add(CollectiblePower power, CombatStat stat, List<CombatStatModifier> result) {
    if (power instanceof CollectiblePower.CombatStatBoost boost) {
      if (boost.modifier().stat() == stat) result.add(boost.modifier());
    } else if (power instanceof CollectiblePower.CombatStatSet set) {
      set.boosts().stream().map(CollectiblePower.CombatStatBoost::modifier)
          .filter(modifier -> modifier.stat() == stat).forEach(result::add);
    }
  }
}
