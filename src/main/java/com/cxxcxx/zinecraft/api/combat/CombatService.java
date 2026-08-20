package com.cxxcxx.zinecraft.api.combat;

import com.cxxcxx.zinecraft.api.collection.CollectibleCombatStats;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

/**
 * Server-authoritative entry point for all Zinecraft combat stat and result calculations.
 */
public final class CombatService {
  public static final CombatService INSTANCE = new CombatService();

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
    double defensiveStat = switch (type.mitigation()) {
      case PHYSICAL -> target.getAttributeValue(Attributes.ARMOR);
      case MAGIC -> target.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
      case NONE -> 0.0;
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
   * 按统一伤害描述结算一段伤害。
   * 固定伤害进入基础攻击力流程；攻击力倍率伤害使用实体当前攻击属性作为已解析攻击力。
   */
  public boolean damage(LivingEntity attacker, LivingEntity target, CombatDamageProfile profile) {
    return switch (profile.basis()) {
      case FLAT -> damage(attacker, target, profile.type(), profile.amount(), CombatRequest.DEFAULT);
      case ATTACK_MULTIPLIER -> damageFromResolvedAttack(
          attacker,
          target,
          profile.type(),
          attacker.getAttributeValue(Attributes.ATTACK_DAMAGE),
          new CombatRequest(profile.amount(), 0.0, 0.0, 0.0, 1.0)
      );
    };
  }

  /**
   * 依次结算同一次命中的多段伤害，各段可以使用不同伤害类型。
   *
   * @return 任意伤害段成功命中时返回 {@code true}
   */
  public boolean damage(
      LivingEntity attacker,
      LivingEntity target,
      List<CombatDamageProfile> profiles
  ) {
    boolean damaged = false;
    for (CombatDamageProfile profile : List.copyOf(profiles)) {
      damaged = damage(attacker, target, profile) || damaged;
    }
    return damaged;
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
    ResourceKey<DamageType> key = damageType(type.path());
    var registry = attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
    return new DamageSource(registry.getHolderOrThrow(key), attacker);
  }
}
