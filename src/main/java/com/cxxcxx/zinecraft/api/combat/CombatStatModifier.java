package com.cxxcxx.zinecraft.api.combat;

import java.util.Objects;

public record CombatStatModifier(CombatStat stat, CombatModifierPhase phase, double amount) {
  public CombatStatModifier {
    Objects.requireNonNull(stat, "stat");
    Objects.requireNonNull(phase, "phase");
    if (!Double.isFinite(amount)) throw new IllegalArgumentException("Combat modifier must be finite");
    if (phase == CombatModifierPhase.FINAL_SCALER && amount < 0.0)
      throw new IllegalArgumentException("Final scaler must not be negative");
  }

  public static CombatStatModifier collectibleAddition(CombatStat stat, double amount) {
    return new CombatStatModifier(stat, CombatModifierPhase.COLLECTIBLE_ADDITION, amount);
  }

  public static CombatStatModifier collectibleMultiplier(CombatStat stat, double bonus) {
    return new CombatStatModifier(stat, CombatModifierPhase.COLLECTIBLE_MULTIPLIER, bonus);
  }

  public static CombatStatModifier directAddition(CombatStat stat, double amount) {
    return new CombatStatModifier(stat, CombatModifierPhase.DIRECT_ADDITION, amount);
  }

  public static CombatStatModifier directMultiplier(CombatStat stat, double bonus) {
    return new CombatStatModifier(stat, CombatModifierPhase.DIRECT_MULTIPLIER, bonus);
  }

  public static CombatStatModifier finalAddition(CombatStat stat, double amount) {
    return new CombatStatModifier(stat, CombatModifierPhase.FINAL_ADDITION, amount);
  }

  public static CombatStatModifier finalScaler(CombatStat stat, double factor) {
    return new CombatStatModifier(stat, CombatModifierPhase.FINAL_SCALER, factor);
  }
}
