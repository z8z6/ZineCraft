package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.MobEffectBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Zinecraft 的服务端战斗异常状态声明。 */
public final class ModMobEffect {
  /** 攻击速度降低 30%；重复施加时由 CombatStatusService 转为冻结。 */
  public static final MobEffectBuilder COLD = effect("cold", "寒冷", "Cold", 0x6DD5FA)
      .attributeModifier(Attributes.ATTACK_SPEED, "attack_speed", -0.30,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .build();
  /** 无法移动或攻击，并使法术抗性降低 15。 */
  public static final MobEffectBuilder FROZEN = effect("frozen", "冻结", "Frozen", 0xB9F2FF)
      .attributeModifier(Attributes.MOVEMENT_SPEED, "movement_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .attributeModifier(Attributes.ATTACK_SPEED, "attack_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .attributeModifier(Attributes.ARMOR_TOUGHNESS, "resistance", -15.0,
          AttributeModifier.Operation.ADD_VALUE)
      .build();
  /** 无法移动或攻击。 */
  public static final MobEffectBuilder PARALYSIS = effect("paralysis", "麻痹", "Paralysis", 0xF2D45C)
      .attributeModifier(Attributes.MOVEMENT_SPEED, "movement_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .attributeModifier(Attributes.ATTACK_SPEED, "attack_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .build();
  /** 无法移动或攻击。 */
  public static final MobEffectBuilder STUN = effect("stun", "晕眩", "Stun", 0xE7B83B)
      .attributeModifier(Attributes.MOVEMENT_SPEED, "movement_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .attributeModifier(Attributes.ATTACK_SPEED, "attack_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .build();
  /** 移动速度降低 80%，仍可攻击。 */
  public static final MobEffectBuilder SLOW = effect("slow", "停顿", "Slow", 0x8F6BC4)
      .attributeModifier(Attributes.MOVEMENT_SPEED, "movement_speed", -0.80,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .build();
  /** 无法移动，仍可攻击。 */
  public static final MobEffectBuilder BIND = effect("bind", "束缚", "Bind", 0x7A5A44)
      .attributeModifier(Attributes.MOVEMENT_SPEED, "movement_speed", -1.0,
          AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
      .build();

  private ModMobEffect() {
  }

  private static MobEffectBuilder effect(String path, String zhCn, String enUs, int color) {
    return new MobEffectBuilder(
        Zinecraft.MOB_EFFECTS, path, zhCn, enUs, MobEffectCategory.HARMFUL, color
    );
  }

  public static void bootstrap() {
  }
}