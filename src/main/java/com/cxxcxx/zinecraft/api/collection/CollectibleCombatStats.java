package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
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
    Objects.requireNonNull(entity, "entity");
    CombatStat[] result = {Objects.requireNonNull(base, "base")};
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
