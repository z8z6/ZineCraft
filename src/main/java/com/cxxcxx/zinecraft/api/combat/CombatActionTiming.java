package com.cxxcxx.zinecraft.api.combat;

/**
 * Integer server-tick projection of an action timeline at a resolved Arknights attack speed.
 */
public record CombatActionTiming(int effectTick, int durationTicks) {
  public CombatActionTiming {
    if (effectTick < 0 || durationTicks <= effectTick)
      throw new IllegalArgumentException("Effect tick must be inside the action timeline");
  }

  public static CombatActionTiming scale(int baseEffectTick, int baseDurationTicks, double attackSpeed) {
    if (baseEffectTick < 0 || baseDurationTicks <= baseEffectTick)
      throw new IllegalArgumentException("Base effect tick must be inside the action timeline");
    double scale = 100.0 / Math.clamp(attackSpeed, 20.0, 600.0);
    int effectTick = Math.max(0, (int) Math.round(baseEffectTick * scale));
    int durationTicks = Math.max(effectTick + 1, (int) Math.round(baseDurationTicks * scale));
    return new CombatActionTiming(effectTick, durationTicks);
  }
}
