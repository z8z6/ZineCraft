package com.cxxcxx.zinecraft.api.combat;

import java.util.Objects;

/**
 * 武器、技能等伤害来源共享的伤害描述。
 *
 * @param amount 固定基础攻击力或攻击力倍率，具体语义由 {@code basis} 决定
 * @param type   物理、法术或真实伤害类型
 * @param basis  数值的计算基准
 */
public record CombatDamageProfile(double amount, CombatDamageType type, CombatDamageBasis basis) {
  public CombatDamageProfile {
    if (!Double.isFinite(amount) || amount <= 0.0) {
      throw new IllegalArgumentException("伤害数值必须是有限正数：" + amount);
    }
    Objects.requireNonNull(type, "伤害类型不能为空");
    Objects.requireNonNull(basis, "伤害计算基准不能为空");
  }

  /**
   * 创建使用固定基础攻击力的伤害描述。
   */
  public static CombatDamageProfile flat(double damage, CombatDamageType type) {
    return new CombatDamageProfile(damage, type, CombatDamageBasis.FLAT);
  }

  /**
   * 创建使用伤害来源当前攻击力倍率的伤害描述。
   */
  public static CombatDamageProfile attackMultiplier(double multiplier, CombatDamageType type) {
    return new CombatDamageProfile(multiplier, type, CombatDamageBasis.ATTACK_MULTIPLIER);
  }
}
