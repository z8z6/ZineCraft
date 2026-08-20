package com.cxxcxx.zinecraft.api.weapon.action.staff;

import com.cxxcxx.zinecraft.api.combat.CombatDamageProfile;
import com.cxxcxx.zinecraft.api.combat.CombatDamageProvider;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.registry.builder.SkillEffectBuilder;
import com.cxxcxx.zinecraft.api.skill.SkillCastContext;
import com.cxxcxx.zinecraft.api.weapon.action.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class CastSkillAction implements WeaponAction, CombatDamageProvider {
  @NotNull
  private final ResourceLocation id;
  @NotNull
  private final SkillEffectBuilder<?> effect;
  private final int castTick;
  private final int durationTicks;

  public CastSkillAction(
      @NotNull ResourceLocation id,
      @NotNull SkillEffectBuilder<?> effect,
      int castTick,
      int durationTicks
  ) {
    this.id = Objects.requireNonNull(id, "施法动作 ID 不能为空");
    this.effect = Objects.requireNonNull(effect, "施法技能效果不能为空：" + id);
    this.effect.getEffect();
    this.castTick = castTick;
    this.durationTicks = durationTicks;
    if (castTick < 0 || castTick >= durationTicks) {
      throw new IllegalArgumentException("施法 tick 必须位于动作时间线内");
    }
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public List<CombatDamageProfile> damageProfiles() {
    return effect.damageProfiles();
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return effect.canCast(toSkillContext(context));
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    var timing = CombatService.INSTANCE.actionTiming(context.getPlayer(), this.castTick, this.durationTicks);
    TickRange intRange = new TickRange(timing.effectTick(), timing.effectTick());
    return new TimedWeaponActionRuntime(intRange, timing.durationTicks()) {
      @Override
      protected void onTick(int tick) {
        if (tick == timing.effectTick()) {
          effect.cast(toSkillContext(context));
        }
      }
    };
  }

  private SkillCastContext toSkillContext(WeaponContext context) {
    return new SkillCastContext(context.getPlayer(), context.getStack(), context.getHand());
  }
}
