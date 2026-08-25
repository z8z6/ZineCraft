package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.skill.SkillProfession;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;

/** 从实体已装备的全部 Curios 藏品依次应用函数式属性效果。 */
public final class CollectibleCombatStats {
  private CollectibleCombatStats() {
  }

  /**
   * 将所有已装备藏品的 {@code CombatStat -> CombatStat} 效果应用到基础快照。
   */
  public static CombatStat apply(LivingEntity entity, CombatStat base) {
    return apply(entity, base, CollectibleSpecialCondition.tier(entity));
  }

  /** 以显式特殊条件档位聚合藏品，供集成战略结算与测试调用。 */
  public static CombatStat apply(LivingEntity entity, CombatStat base, int collectibleEffectTier) {
    Objects.requireNonNull(entity, "entity");
    CombatStat[] result = {Objects.requireNonNull(base, "base")
        .withCollectibleEffectTier(collectibleEffectTier)};
    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> stack.getItem() instanceof CollectibleItem
    ).forEach(slot -> {
      if (slot.stack().getItem() instanceof CollectibleItem item) {
        result[0] = Objects.requireNonNull(
            item.collectible().power.apply(result[0]),
            "藏品效果不能返回 null：" + item.collectible().path
        );
      }
    }));
    return result[0];
  }

  /** 聚合全部藏品，并只解析指定技能职业对应的职业字段。 */
  public static CombatStat apply(LivingEntity entity, CombatStat base, SkillProfession profession) {
    return apply(entity, base).resolveProfession(Objects.requireNonNull(profession, "profession"));
  }

  /** 仅将实体藏品登记的指定职业字段应用到给定基础快照，不重复应用全局数值。 */
  public static CombatStat applyProfession(
      LivingEntity entity,
      CombatStat base,
      SkillProfession profession
  ) {
    return apply(entity, CombatStat.EMPTY).resolveProfession(
        Objects.requireNonNull(profession, "profession"),
        Objects.requireNonNull(base, "base")
    );
  }

  /** 按迭代顺序汇总多个实体装备的藏品效果。 */
  public static CombatStat applyAll(Iterable<? extends LivingEntity> entities, CombatStat base) {
    CombatStat result = Objects.requireNonNull(base, "base");
    for (LivingEntity entity : entities) result = apply(entity, result);
    return result;
  }

  /** 执行击杀者当前装备藏品注册的全部击杀能力。 */
  public static void triggerKillEffects(LivingEntity killer, LivingEntity killed) {
    Objects.requireNonNull(killed, "killed");
    apply(killer, CombatStat.EMPTY).triggerKillEffects(killer, killed);
  }
}
