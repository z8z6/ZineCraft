package com.cxxcxx.zinecraft.api.combat;

import com.cxxcxx.zinecraft.core.registry.ModMobEffect;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

/** 统一施加 Zinecraft 异常状态，并在服务端入口换算藏品提供的持续时间增减益。 */
public final class CombatStatusService {
  private CombatStatusService() {
  }

  /** 对敌方施加一次异常状态；持续时间读取施加者的敌方异常状态延长。 */
  public static boolean applyToEnemy(
      LivingEntity source,
      LivingEntity target,
      Holder<MobEffect> effect,
      int baseDurationTicks
  ) {
    Objects.requireNonNull(source, "source");
    return apply(target, effect,
        CombatService.INSTANCE.enemyStatusDurationTicks(source, baseDurationTicks));
  }

  /** 对我方施加一次异常状态；持续时间读取承受者的我方异常状态减免。 */
  public static boolean applyToFriendly(
      LivingEntity target,
      Holder<MobEffect> effect,
      int baseDurationTicks
  ) {
    return apply(target, effect,
        CombatService.INSTANCE.friendlyStatusDurationTicks(target, baseDurationTicks));
  }

  /** 使用已经结算完成的 tick 数施加状态；重复寒冷会转为冻结。 */
  public static boolean apply(
      LivingEntity target,
      Holder<MobEffect> effect,
      int durationTicks
  ) {
    Objects.requireNonNull(target, "target");
    Objects.requireNonNull(effect, "effect");
    if (durationTicks < 0) throw new IllegalArgumentException("状态持续时间不能为负数");
    if (durationTicks == 0 || target.level().isClientSide) return false;

    Holder<MobEffect> resolvedEffect = effect;
    if (effect.equals(ModMobEffect.COLD.holder()) && target.hasEffect(ModMobEffect.COLD.holder())) {
      target.removeEffect(ModMobEffect.COLD.holder());
      resolvedEffect = ModMobEffect.FROZEN.holder();
    }
    return target.addEffect(new MobEffectInstance(resolvedEffect, durationTicks));
  }

  /** 冻结、麻痹和晕眩期间禁止造成攻击伤害。 */
  public static boolean preventsAttacking(LivingEntity entity) {
    return entity.hasEffect(ModMobEffect.FROZEN.holder())
        || entity.hasEffect(ModMobEffect.PARALYSIS.holder())
        || entity.hasEffect(ModMobEffect.STUN.holder());
  }

  /** 冻结、麻痹、晕眩和束缚期间完全禁止移动。 */
  public static boolean preventsMovement(LivingEntity entity) {
    return preventsAttacking(entity) || entity.hasEffect(ModMobEffect.BIND.holder());
  }
}