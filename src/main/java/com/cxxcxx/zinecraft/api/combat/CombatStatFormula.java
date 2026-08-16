package com.cxxcxx.zinecraft.api.combat;

import java.util.Collection;

public final class CombatStatFormula {
  private CombatStatFormula() {
  }

  public static double resolve(CombatStat stat, double baseValue, Collection<CombatStatModifier> modifiers) {
    if (!Double.isFinite(baseValue) || baseValue < 0.0)
      throw new IllegalArgumentException("Base stat must be finite and non-negative");
    double collectibleAddition = sum(stat, modifiers, CombatModifierPhase.COLLECTIBLE_ADDITION);
    double collectibleMultiplier = 1.0 + sum(stat, modifiers, CombatModifierPhase.COLLECTIBLE_MULTIPLIER);
    double collectibleBase = Math.max(0.0, collectibleMultiplier) * (baseValue + collectibleAddition);
    double directAddition = sum(stat, modifiers, CombatModifierPhase.DIRECT_ADDITION);
    double directMultiplier = Math.max(0.0, 1.0 + sum(stat, modifiers, CombatModifierPhase.DIRECT_MULTIPLIER));
    double finalAddition = sum(stat, modifiers, CombatModifierPhase.FINAL_ADDITION);
    double finalScaler = modifiers.stream()
        .filter(modifier -> modifier.stat() == stat && modifier.phase() == CombatModifierPhase.FINAL_SCALER)
        .mapToDouble(CombatStatModifier::amount)
        .reduce(1.0, (left, right) -> left * right);
    return stat.clamp(finalScaler * ((collectibleBase + directAddition) * directMultiplier + finalAddition));
  }

  private static double sum(CombatStat stat, Collection<CombatStatModifier> modifiers, CombatModifierPhase phase) {
    return modifiers.stream()
        .filter(modifier -> modifier.stat() == stat && modifier.phase() == phase)
        .mapToDouble(CombatStatModifier::amount)
        .sum();
  }
}
