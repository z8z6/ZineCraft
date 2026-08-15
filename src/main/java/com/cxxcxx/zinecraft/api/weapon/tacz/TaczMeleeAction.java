package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class TaczMeleeAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczMeleeAction(@NotNull ResourceLocation id) {
    super();
    this.id = id;
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return TaczWeaponActions.gun(context) != null && context.getPlayer().isAlive();
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
    if (taczGunSpec1 == null) {
      String string = "Required value was null.";
      throw new IllegalArgumentException(string.toString());
    } else {
      final TaczGunSpec taczGunSpec = taczGunSpec1;
      TickRange intRange = new TickRange(2, 2);
      int i = Math.max(taczGunSpec.getMeleeCooldownTicks(), 3);
      return new TimedWeaponActionRuntime(intRange, i) {
        @Override
        protected void onTick(int tick) {
          if (tick == 2) {
            HitscanService.Hit hit = HitscanService.INSTANCE.trace(context.getPlayer(), taczGunSpec.getMeleeDistance(), 0.6);
            if (hit != null) {
              LivingEntity livingEntity = hit.getTarget();
              if (livingEntity != null) {
                livingEntity.hurt(context.getPlayer().damageSources().playerAttack((Player) context.getPlayer()), taczGunSpec.getMeleeDamage());
              }
            }
          }
        }
      };
    }
  }
}

