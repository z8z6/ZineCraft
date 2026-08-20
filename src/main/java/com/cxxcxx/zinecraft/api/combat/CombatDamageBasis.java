package com.cxxcxx.zinecraft.api.combat;

/**
 * 伤害数值的计算基准。
 */
public enum CombatDamageBasis {
  /**
   * 数值直接作为提交给战斗公式的基础攻击力。
   */
  FLAT,
  /**
   * 数值表示相对于伤害来源当前攻击力的倍率。
   */
  ATTACK_MULTIPLIER
}
