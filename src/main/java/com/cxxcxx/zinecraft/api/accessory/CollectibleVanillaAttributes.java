package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.combat.CombatModifierPhase;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Converts collectible combat declarations into Minecraft attributes.
 *
 * <p>The Arknights stat model describes the source effect. Minecraft attributes remain the final
 * runtime value so vanilla combat, other mods and L2's original ability page see the same result.
 */
public final class CollectibleVanillaAttributes {
  private CollectibleVanillaAttributes() {
  }

  public static Holder<Attribute> attribute(CombatStat stat) {
    return switch (stat) {
      case MAX_HEALTH -> Attributes.MAX_HEALTH;
      case ATTACK -> Attributes.ATTACK_DAMAGE;
      case DEFENSE -> Attributes.ARMOR;
      case RESISTANCE -> Attributes.ARMOR_TOUGHNESS;
      case ATTACK_SPEED -> Attributes.ATTACK_SPEED;
    };
  }

  public static AttributeModifier.Operation operation(CombatStatModifier modifier) {
    return switch (modifier.phase()) {
      case COLLECTIBLE_ADDITION -> modifier.stat() == CombatStat.ATTACK_SPEED
          ? AttributeModifier.Operation.ADD_MULTIPLIED_BASE
          : AttributeModifier.Operation.ADD_VALUE;
      case COLLECTIBLE_MULTIPLIER -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
      default ->
          throw new IllegalArgumentException("Collectible attribute bridge does not support " + modifier.phase());
    };
  }

  public static double amount(CombatStatModifier modifier) {
    if (modifier.phase() == CombatModifierPhase.COLLECTIBLE_ADDITION
        && modifier.stat() == CombatStat.ATTACK_SPEED) {
      return modifier.amount() / 100.0;
    }
    return modifier.amount();
  }
}
