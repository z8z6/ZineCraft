package com.cxxcxx.zinecraft.api.combat;

public final class CombatFormulas {
  private static final double MINIMUM_DAMAGE_RATIO = 0.05;

  private CombatFormulas() {
  }

  public static double damage(CombatDamageType type, double attack, double defenseOrResistance, CombatRequest request) {
    validateNonNegative("attack", attack);
    validateNonNegative("defenseOrResistance", defenseOrResistance);
    double basic = Math.max(0.0, attack * request.attackMultiplier() + request.additionalAttack());
    double effectiveDefense = (1.0 - request.percentPenetration()) * Math.max(0.0, defenseOrResistance - request.flatPenetration());
    double resolved = switch (type.mitigation()) {
      case PHYSICAL -> Math.max(MINIMUM_DAMAGE_RATIO * basic, basic - effectiveDefense);
      case MAGIC -> Math.max(MINIMUM_DAMAGE_RATIO * basic, basic * Math.max(0.0, 100.0 - effectiveDefense) / 100.0);
      case NONE -> basic;
    };
    return resolved * request.finalMultiplier();
  }

  public static double healing(double attack, double healingMultiplier, double additionalAttack) {
    validateNonNegative("attack", attack);
    validateNonNegative("healingMultiplier", healingMultiplier);
    if (!Double.isFinite(additionalAttack)) throw new IllegalArgumentException("additionalAttack must be finite");
    return Math.max(0.0, attack * healingMultiplier + additionalAttack);
  }

  public static double attackInterval(double theoreticalIntervalSeconds, double attackSpeed) {
    if (!Double.isFinite(theoreticalIntervalSeconds) || theoreticalIntervalSeconds <= 0.0)
      throw new IllegalArgumentException("Theoretical interval must be finite and positive");
    return theoreticalIntervalSeconds / (Math.clamp(attackSpeed, 20.0, 600.0) / 100.0);
  }

  private static void validateNonNegative(String name, double value) {
    if (!Double.isFinite(value) || value < 0.0)
      throw new IllegalArgumentException(name + " must be finite and non-negative");
  }
}
