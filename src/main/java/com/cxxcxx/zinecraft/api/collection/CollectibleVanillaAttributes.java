package com.cxxcxx.zinecraft.api.collection;

import com.cxxcxx.zinecraft.api.combat.CombatModifierPhase;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 将藏品的战斗属性声明转换为 Minecraft 原版属性。
 *
 * <p>明日方舟属性模型用于描述原作效果，最终运行值仍落在 Minecraft 属性系统中，
 * 从而让原版战斗、其他模组和 L2 原版能力页面读取到一致结果。</p>
 */
public final class CollectibleVanillaAttributes {
  /**
   * 工具类不允许实例化。
   */
  private CollectibleVanillaAttributes() {
  }

  /**
   * 查找战斗属性对应的 Minecraft 原版属性。
   *
   * @param stat 藏品声明使用的战斗属性
   * @return 对应的原版属性持有者
   */
  public static Holder<Attribute> attribute(CombatStat stat) {
    return switch (stat) {
      case MAX_HEALTH -> Attributes.MAX_HEALTH;
      case ATTACK -> Attributes.ATTACK_DAMAGE;
      case DEFENSE -> Attributes.ARMOR;
      case RESISTANCE -> Attributes.ARMOR_TOUGHNESS;
      case ATTACK_SPEED -> Attributes.ATTACK_SPEED;
    };
  }

  /**
   * 将藏品修饰阶段转换为 Minecraft 属性运算方式。
   * 攻击速度加算使用基础值倍率，以适配其百分比声明语义。
   *
   * @param modifier 藏品战斗属性修饰器
   * @return 对应的原版属性运算方式
   */
  public static AttributeModifier.Operation operation(CombatStatModifier modifier) {
    return switch (modifier.phase()) {
      case COLLECTIBLE_ADDITION -> modifier.stat() == CombatStat.ATTACK_SPEED
          ? AttributeModifier.Operation.ADD_MULTIPLIED_BASE
          : AttributeModifier.Operation.ADD_VALUE;
      case COLLECTIBLE_MULTIPLIER -> AttributeModifier.Operation.ADD_MULTIPLIED_BASE;
      default ->
          throw new IllegalArgumentException("Collectible attribute bridge does not support " + modifier.phase());
    };
  }

  /**
   * 将藏品声明值转换为 Minecraft 属性修饰值。
   * 攻击速度的加算百分比会由百分数转换为小数比例。
   *
   * @param modifier 藏品战斗属性修饰器
   * @return 可直接传给 {@link AttributeModifier} 的数值
   */
  public static double amount(CombatStatModifier modifier) {
    if (modifier.phase() == CombatModifierPhase.COLLECTIBLE_ADDITION
        && modifier.stat() == CombatStat.ATTACK_SPEED) {
      return modifier.amount() / 100.0;
    }
    return modifier.amount();
  }
}
