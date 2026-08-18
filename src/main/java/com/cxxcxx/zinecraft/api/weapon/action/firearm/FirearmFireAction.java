package com.cxxcxx.zinecraft.api.weapon.action.firearm;

import com.cxxcxx.zinecraft.api.combat.CombatDamageType;
import com.cxxcxx.zinecraft.api.combat.CombatRequest;
import com.cxxcxx.zinecraft.api.combat.CombatService;
import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public final class FirearmFireAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;
  private final int fireTick;
  private final int durationTicks;
  private final float damage;
  private final double range;

  public FirearmFireAction(@NotNull ResourceLocation id, int fireTick, int durationTicks, float damage, double range) {
    super();
    this.id = id;
    this.fireTick = fireTick;
    this.durationTicks = durationTicks;
    this.damage = damage;
    this.range = range;
    int i = this.durationTicks;
    int j = this.fireTick;
    if (0 <= j ? j >= i : true) {
      j = 0;
      String string2 = "开火 tick 必须位于动作时间线内";
      throw new IllegalArgumentException(string2.toString());
    }

    if (!(this.damage > 0.0F)) {
      j = 0;
      String string1 = "枪械伤害必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    if (!(this.range > 0.0)) {
      j = 0;
      String string = "枪械射程必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return context.getPlayer().isAlive()
        && !context.getPlayer().isSpectator()
        && ((Number) context.getStack().getOrDefault(WeaponStateComponents.AMMO, 0)).intValue() > 0;
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    var timing = CombatService.INSTANCE.actionTiming(context.getPlayer(), this.fireTick, this.durationTicks);
    TickRange intRange = new TickRange(timing.effectTick(), timing.effectTick());
    return new TimedWeaponActionRuntime(intRange, timing.durationTicks()) {
      @Override
      protected void onTick(int tick) {
        if (tick == timing.effectTick()) {
          Integer integer = (Integer) context.getStack().getOrDefault(WeaponStateComponents.AMMO, 0);
          if (integer > 0) {
            context.getStack().set(WeaponStateComponents.AMMO, integer - 1);
            Boolean boolean_ = (Boolean) context.getStack().getOrDefault(WeaponStateComponents.AIMING, false);
            HitscanService.Hit hit = HitscanService.INSTANCE.trace(context.getPlayer(), FirearmFireAction.this.range, boolean_ ? 0.65 : 0.15);
            if (hit != null) {
              LivingEntity livingEntity1 = hit.getTarget();
              if (livingEntity1 != null) {
                LivingEntity livingEntity = livingEntity1;
                CombatService.INSTANCE.damage(
                    context.getPlayer(), livingEntity, CombatDamageType.PHYSICAL, FirearmFireAction.this.damage, CombatRequest.DEFAULT
                );
                return;
              }
            }
          }
        }
      }
    };
  }
}
