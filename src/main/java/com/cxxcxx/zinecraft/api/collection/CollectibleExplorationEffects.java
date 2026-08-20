package com.cxxcxx.zinecraft.api.collection;

import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Objects;

/**
 * 汇总实体当前装备藏品所提供的集成战略探索字段。
 */
public final class CollectibleExplorationEffects {
  /**
   * 工具类不允许实例化。
   */
  private CollectibleExplorationEffects() {
  }

  /**
   * 汇总实体当前装备的全部藏品所提供的探索效果。
   * 直接增量相加，倍率字段相乘。
   *
   * @param entity 要检查 Curios 装备的实体
   * @return 合并后的探索效果；没有藏品效果时返回 {@link CollectiblePower.Exploration#NONE}
   */
  public static CollectiblePower.Exploration equipped(LivingEntity entity) {
    Objects.requireNonNull(entity, "entity");
    CollectiblePower.Exploration[] result = {CollectiblePower.Exploration.NONE};
    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> stack.getItem() instanceof CollectibleItem
    ).forEach(slot -> {
      if (slot.stack().getItem() instanceof CollectibleItem item) {
        result[0] = result[0].plus(item.collectible().power.exploration());
      }
    }));
    return result[0];
  }
}
