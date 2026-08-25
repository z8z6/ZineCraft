package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * 一件藏品唯一的运行时效果函数。
 *
 * <p>函数接收效果应用前的不可变 {@link CombatStat}，返回修改后的新快照。</p>
 */
@FunctionalInterface
public interface CollectiblePower extends UnaryOperator<CombatStat> {
  CollectiblePower NONE = stats -> Objects.requireNonNull(stats, "stats");

  @Override
  CombatStat apply(CombatStat stats);

  /** 按声明顺序组合多个藏品效果。 */
  static CollectiblePower combine(CollectiblePower... effects) {
    CollectiblePower[] copied = effects.clone();
    return stats -> {
      CombatStat result = Objects.requireNonNull(stats, "stats");
      for (CollectiblePower effect : copied) {
        result = Objects.requireNonNull(effect, "effect").apply(result);
        result = Objects.requireNonNull(result, "藏品效果不能返回 null");
      }
      return result;
    };
  }

  /** 按 CombatStat 中的特殊条件档位选择一项效果；超出范围时使用最高档。 */
  static CollectiblePower tiered(CollectiblePower... effects) {
    CollectiblePower[] copied = effects.clone();
    if (copied.length == 0) throw new IllegalArgumentException("多档藏品至少需要一档效果");
    for (CollectiblePower effect : copied) Objects.requireNonNull(effect, "effect");
    return stats -> copied[Math.min(stats.collectibleEffectTier(), copied.length - 1)].apply(stats);
  }
}
