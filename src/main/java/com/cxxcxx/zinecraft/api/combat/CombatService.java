package com.cxxcxx.zinecraft.api.combat;

import com.cxxcxx.zinecraft.api.accessory.CollectibleCombatStats;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * Server-authoritative entry point for all Zinecraft combat stat and result calculations.
 */
public final class CombatService {
  public static final CombatService INSTANCE = new CombatService();

  private static final ResourceKey<DamageType> PHYSICAL = damageType("physical");
  private static final ResourceKey<DamageType> ARTS = damageType("arts");
  private static final ResourceKey<DamageType> TRUE = damageType("true");

  private CombatService() {
  }

  private static ResourceKey<DamageType> damageType(String path) {
    return ResourceKey.create(Registries.DAMAGE_TYPE, Zinecraft.id(path));
  }

  /**
   * Resolves an outgoing base attack through equipped collectible-rune modifiers.
   */
  public double attack(LivingEntity entity, double baseAttack) {
    return CombatStatFormula.resolve(
        CombatStat.ATTACK,
        baseAttack,
        CollectibleCombatStats.modifiers(entity, CombatStat.ATTACK)
    );
  }

  /**
   * Resolves attack speed around Arknights' neutral value of 100.
   */
  public double attackSpeed(LivingEntity entity, double baseAttackSpeed) {
    return CombatStatFormula.resolve(
        CombatStat.ATTACK_SPEED,
        baseAttackSpeed,
        CollectibleCombatStats.modifiers(entity, CombatStat.ATTACK_SPEED)
    );
  }

  public double attackIntervalSeconds(LivingEntity entity, double theoreticalIntervalSeconds, double baseAttackSpeed) {
    return CombatFormulas.attackInterval(theoreticalIntervalSeconds, attackSpeed(entity, baseAttackSpeed));
  }

  public CombatActionTiming actionTiming(LivingEntity entity, int baseEffectTick, int baseDurationTicks) {
    return CombatActionTiming.scale(baseEffectTick, baseDurationTicks, attackSpeed(entity, 100.0));
  }

  public float calculateDamage(
      LivingEntity attacker,
      LivingEntity target,
      CombatDamageType type,
      double baseAttack,
      CombatRequest request
  ) {
    return calculateDamageFromResolvedAttack(target, type, attack(attacker, baseAttack), request);
  }

  /**
   * Calculates damage from an attack value already resolved by Minecraft attributes.
   */
  public float calculateDamageFromResolvedAttack(
      LivingEntity target,
      CombatDamageType type,
      double resolvedAttack,
      CombatRequest request
  ) {
    double defensiveStat = switch (type) {
      case PHYSICAL -> target.getAttributeValue(Attributes.ARMOR);
      case ARTS -> target.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
      case TRUE -> 0.0;
    };
    return (float) CombatFormulas.damage(type, resolvedAttack, defensiveStat, request);
  }

  public boolean damage(
      LivingEntity attacker,
      LivingEntity target,
      CombatDamageType type,
      double baseAttack,
      CombatRequest request
  ) {
    float amount = calculateDamage(attacker, target, type, baseAttack, request);
    return amount > 0.0F && target.hurt(damageSource(attacker, type), amount);
  }

  /**
   * Applies damage when the caller already read the final Minecraft attack attribute.
   */
  public boolean damageFromResolvedAttack(
      LivingEntity attacker,
      LivingEntity target,
      CombatDamageType type,
      double resolvedAttack,
      CombatRequest request
  ) {
    float amount = calculateDamageFromResolvedAttack(target, type, resolvedAttack, request);
    return amount > 0.0F && target.hurt(damageSource(attacker, type), amount);
  }

  public float heal(
      LivingEntity source,
      LivingEntity target,
      double baseAttack,
      double healingMultiplier,
      double additionalAttack
  ) {
    float amount = (float) CombatFormulas.healing(attack(source, baseAttack), healingMultiplier, additionalAttack);
    if (amount > 0.0F) target.heal(amount);
    return amount;
  }

  private DamageSource damageSource(LivingEntity attacker, CombatDamageType type) {
    ResourceKey<DamageType> key = switch (type) {
      case PHYSICAL -> PHYSICAL;
      case ARTS -> ARTS;
      case TRUE -> TRUE;
    };
    var registry = attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
    return new DamageSource(registry.getHolderOrThrow(key), attacker);
  }
}
