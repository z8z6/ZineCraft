package com.cxxcxx.zinecraft.api.combat;

public record CombatRequest(
    double attackMultiplier,
    double additionalAttack,
    double percentPenetration,
    double flatPenetration,
    double finalMultiplier
) {
  public static final CombatRequest DEFAULT = new CombatRequest(1.0, 0.0, 0.0, 0.0, 1.0);

  public CombatRequest {
    if (!Double.isFinite(attackMultiplier) || attackMultiplier < 0.0)
      throw new IllegalArgumentException("Attack multiplier must be non-negative");
    if (!Double.isFinite(additionalAttack)) throw new IllegalArgumentException("Additional attack must be finite");
    if (!Double.isFinite(percentPenetration) || percentPenetration < 0.0 || percentPenetration > 1.0)
      throw new IllegalArgumentException("Percent penetration must be in [0, 1]");
    if (!Double.isFinite(flatPenetration) || flatPenetration < 0.0)
      throw new IllegalArgumentException("Flat penetration must be non-negative");
    if (!Double.isFinite(finalMultiplier) || finalMultiplier < 0.0)
      throw new IllegalArgumentException("Final multiplier must be non-negative");
  }
}
