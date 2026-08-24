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

  /** Resolves an outgoing base attack through all equipped collectible functions. */
  public double attack(LivingEntity entity, double baseAttack) {
    CombatStat base = CombatStat.EMPTY.withAttack(requireBase(baseAttack));
    return CollectibleCombatStats.apply(entity, base).limited().attack();
  }

  /** Resolves attack speed around Arknights' neutral value of 100. */
  public double attackSpeed(LivingEntity entity, double baseAttackSpeed) {
    CombatStat base = CombatStat.EMPTY.withAttackSpeed(requireBase(baseAttackSpeed));
    return CollectibleCombatStats.apply(entity, base).limited().attackSpeed();
  }
  public double attackIntervalSeconds(LivingEntity entity, double theoreticalIntervalSeconds, double baseAttackSpeed) {
    return CombatFormulas.attackInterval(theoreticalIntervalSeconds, attackSpeed(entity, baseAttackSpeed));
  }

  public CombatActionTiming actionTiming(LivingEntity entity, int baseEffectTick, int baseDurationTicks) {
    return CombatActionTiming.scale(baseEffectTick, baseDurationTicks, attackSpeed(entity, 100.0));
  }

  /** 聚合施加者藏品后，计算作用于敌方的一次性异常状态持续时间。 */
  public int enemyStatusDurationTicks(LivingEntity source, int baseDurationTicks) {
    return CollectibleCombatStats.apply(source, CombatStat.EMPTY)
        .enemyStatusDurationTicks(baseDurationTicks);
  }

  /** 聚合承受者藏品后，计算我方承受的一次性异常状态持续时间。 */
  public int friendlyStatusDurationTicks(LivingEntity target, int baseDurationTicks) {
    return CollectibleCombatStats.apply(target, CombatStat.EMPTY)
        .friendlyStatusDurationTicks(baseDurationTicks);
  }

  public float calculateDamage(
      LivingEntity attacker,
      LivingEntity target,
      CombatDamageType type,
      double baseAttack,
      CombatRequest request
  ) {
    return calculateDamageFromResolvedAttack(
        target,
        type,
        attack(attacker, baseAttack),
        withCollectibleDamageModifiers(attacker, type, request)
    );
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
    float amount = calculateDamageFromResolvedAttack(
        target,
        type,
        resolvedAttack,
        withCollectibleDamageModifiers(attacker, type, request)
    );
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

  /** 将所有藏品的防御无视与对应伤害加成聚合后，一次性并入伤害请求。 */
  private static CombatRequest withCollectibleDamageModifiers(
      LivingEntity attacker,
      CombatDamageType type,
      CombatRequest request
  ) {
    boolean physical = type.mitigation() == CombatMitigationType.PHYSICAL;
    if (!physical && type != CombatDamageType.TRUE) return request;
    CombatStat stats = CollectibleCombatStats.apply(attacker, CombatStat.EMPTY).limited();
    double combined = physical
        ? Math.clamp(request.percentPenetration() + stats.defenseIgnore(), 0.0, 1.0)
        : request.percentPenetration();
    double finalMultiplier = type == CombatDamageType.TRUE
        ? request.finalMultiplier() * (1.0 + stats.trueDamageBonus())
        : request.finalMultiplier();
    return new CombatRequest(
        request.attackMultiplier(),
        request.additionalAttack(),
        combined,
        request.flatPenetration(),
        finalMultiplier
    );
  }

  private static double requireBase(double value) {
    if (!Double.isFinite(value) || value < 0.0) {
      throw new IllegalArgumentException("Base stat must be finite and non-negative");
    }
    return value;
  }
  private DamageSource damageSource(LivingEntity attacker, CombatDamageType type) {
    ResourceKey<DamageType> key = damageType(type.path());
    var registry = attacker.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
    return new DamageSource(registry.getHolderOrThrow(key), attacker);
  }
}
