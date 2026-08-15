package com.cxxcxx.zinecraft.api.weapon.action.melee;

import org.jetbrains.annotations.Nullable;

public final class MeleeAttackActionKt {
  public static final float resolveActionMeleeDamage(float baseDamage, @Nullable Double attackDamage) {
    if (!(Math.abs(baseDamage) <= Float.MAX_VALUE) || !(baseDamage > 0.0F)) {
      int j = 0;
      String string = "动作近战基础伤害必须是有限正数";
      throw new IllegalArgumentException(string.toString());
    }

    if (attackDamage != null) {
      Double double_ = attackDamage;
      double d = double_.doubleValue();
      int i = 0;
      Double double1 = Math.abs(d) <= Double.MAX_VALUE && d > 0.0 ? double_ : null;
      if (double1 != null) {
        return (float) double1.doubleValue();
      }
    }

    return baseDamage;
  }
}
