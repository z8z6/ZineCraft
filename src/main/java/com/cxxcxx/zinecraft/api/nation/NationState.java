package com.cxxcxx.zinecraft.api.nation;

public record NationState(TerraNation nation, int prosperity, int stability, int militaryStrength, int openness,
                          int aggression) {
  public NationState {
    for (int value : new int[]{prosperity, stability, militaryStrength, openness, aggression})
      if (value < 0 || value > 100) throw new IllegalArgumentException("国家状态指标必须在 0—100：" + nation.getId());
  }

  public TerraNation getNation() {
    return nation;
  }

  public int getProsperity() {
    return prosperity;
  }

  public int getStability() {
    return stability;
  }

  public int getMilitaryStrength() {
    return militaryStrength;
  }

  public int getOpenness() {
    return openness;
  }

  public int getAggression() {
    return aggression;
  }
}
