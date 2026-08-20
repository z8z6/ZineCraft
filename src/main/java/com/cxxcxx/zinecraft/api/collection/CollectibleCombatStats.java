package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

/**
 * 从实体已装备的所有 Curios 槽位读取藏品，并向战斗系统提供对应的属性修饰。
 */
public final class CollectibleCombatStats {
  /**
   * 工具类不允许实例化。
   */
  private CollectibleCombatStats() {
  }

  /**
   * 收集实体已装备藏品中作用于指定战斗属性的全部修饰器。
   *
   * @param entity 要检查 Curios 装备的实体
   * @param stat 要筛选的战斗属性
   * @return 不可变的属性修饰器列表；未装备相应藏品时为空列表
   */
  public static List<CombatStatModifier> modifiers(LivingEntity entity, CombatStat stat) {
    List<CombatStatModifier> result = new ArrayList<>();
    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> stack.getItem() instanceof CollectibleItem
    ).forEach(slot -> {
      if (slot.stack().getItem() instanceof CollectibleItem item) add(item.collectible().power, stat, result);
    }));
    return List.copyOf(result);
  }

  /**
   * 将一项藏品效果中匹配指定属性的修饰器追加到结果列表。
   *
   * @param power 藏品的运行时效果
   * @param stat 要筛选的战斗属性
   * @param result 接收匹配修饰器的可变列表
   */
  private static void add(CollectiblePower power, CombatStat stat, List<CombatStatModifier> result) {
    power.combatStats().stream()
        .filter(modifier -> modifier.stat() == stat)
        .forEach(result::add);
  }
}
