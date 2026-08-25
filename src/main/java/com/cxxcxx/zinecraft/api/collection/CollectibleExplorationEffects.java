package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import net.minecraft.world.entity.LivingEntity;

/** 汇总实体当前装备藏品所提供的探索属性。 */
public final class CollectibleExplorationEffects {
  private CollectibleExplorationEffects() {
  }

  /**
   * 从空属性快照开始应用全部藏品效果；直接增量相加，倍率由效果函数依次乘入。
   */
  public static CombatStat equipped(LivingEntity entity) {
    return CollectibleCombatStats.apply(entity, CombatStat.EMPTY);
  }

  /** 按集成战略运行时给出的特殊条件档位汇总探索效果。 */
  public static CombatStat equipped(LivingEntity entity, int collectibleEffectTier) {
    return CollectibleCombatStats.apply(entity, CombatStat.EMPTY, collectibleEffectTier);
  }
}
