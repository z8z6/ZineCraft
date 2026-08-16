package com.cxxcxx.zinecraft.api.combat;

/**
 * Core combat attributes shared by weapons, skills, entities and collectibles.
 */
public enum CombatStat {
  MAX_HEALTH(0.0, Double.MAX_VALUE),
  ATTACK(0.0, Double.MAX_VALUE),
  DEFENSE(0.0, Double.MAX_VALUE),
  RESISTANCE(0.0, 100.0),
  ATTACK_SPEED(20.0, 600.0);

  private final double minimum;
  private final double maximum;

  CombatStat(double minimum, double maximum) {
    this.minimum = minimum;
    this.maximum = maximum;
  }

  public double clamp(double value) {
    return Math.clamp(value, minimum, maximum);
  }
}
