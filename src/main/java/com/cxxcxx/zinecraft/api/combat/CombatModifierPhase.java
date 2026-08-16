package com.cxxcxx.zinecraft.api.combat;

/**
 * Evaluation order follows Arknights' attribute pipeline.
 */
public enum CombatModifierPhase {
  COLLECTIBLE_ADDITION,
  COLLECTIBLE_MULTIPLIER,
  DIRECT_ADDITION,
  DIRECT_MULTIPLIER,
  FINAL_ADDITION,
  FINAL_SCALER
}
