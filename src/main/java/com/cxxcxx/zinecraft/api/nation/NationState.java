package com.cxxcxx.zinecraft.api.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;

import java.util.Objects;

/**
 * 一国的玩法初始状态；所有指标均使用 0—100 的闭区间。
 */
public record NationState(
    NationBuilder nation,
    int prosperity,
    int stability,
    int militaryStrength,
    int openness,
    int aggression
) {
  public NationState {
    Objects.requireNonNull(nation, "国家不能为空");
    requirePercentage(prosperity, "繁荣度", nation);
    requirePercentage(stability, "稳定度", nation);
    requirePercentage(militaryStrength, "军事力量", nation);
    requirePercentage(openness, "开放度", nation);
    requirePercentage(aggression, "进攻倾向", nation);
  }

  private static void requirePercentage(int value, String field, NationBuilder nation) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException(field + "必须在 0—100：" + nation.getId() + "=" + value);
    }
  }

  public NationBuilder getNation() {
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
