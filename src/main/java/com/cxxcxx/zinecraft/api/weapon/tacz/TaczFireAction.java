package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class TaczFireAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczFireAction(@NotNull ResourceLocation id) {
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
    TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
    if (taczGunSpec1 == null) {
      return false;
    }

    TaczGunSpec taczGunSpec = taczGunSpec1;
    return context.getPlayer().isAlive()
        && !context.getPlayer().isSpectator()
        && ((Number) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity())).intValue() > 0
        && !(Boolean) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false);
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
    if (taczGunSpec1 == null) {
      String string1 = "Required value was null.";
      throw new IllegalArgumentException(string1.toString());
    } else {
      final TaczGunSpec taczGunSpec = taczGunSpec1;
      String string = TaczWeaponActions.fireMode(context);
      int i = java.util.Objects.equals(string, "burst") ? taczGunSpec.getBurstCount() : 1;
      final int j = Math.max(
          (int) Math.ceil(1200.0 / (java.util.Objects.equals(string, "burst") ? taczGunSpec.getBurstRpm() : taczGunSpec.getRpm())), 1
      );
      final int k = (i - 1) * j;
      TickRange intRange = new TickRange(0, k);
      int l = k + j;
      return new TimedWeaponActionRuntime(intRange, l) {
        @Override
        protected void onTick(int tick) {
          if (tick % j == 0 && tick <= k) {
            Integer integer = (Integer) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity());
            if (integer > 0) {
              context.getStack().set(WeaponStateComponents.INSTANCE.getAMMO(), integer - 1);
              if (java.util.Objects.equals(taczGunSpec.getBolt(), "manual_action")) {
                context.getStack().set(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), true);
              }

              Boolean boolean_ = (Boolean) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
              HitscanService.Hit hit1 = HitscanService.INSTANCE.trace(context.getPlayer(), taczGunSpec.getRange(), boolean_ ? 0.08 : 0.3);
              if (hit1 != null) {
                HitscanService.Hit hit = hit1;
                int m = taczGunSpec.getProjectileCount();
                WeaponContext weaponContext = context;
                TaczGunSpec taczGunSpec2 = taczGunSpec;

                for (int n = 0; n < m; n++) {
                  int o = 0;
                  hit.getTarget()
                      .hurt(weaponContext.getPlayer().damageSources().playerAttack((Player) weaponContext.getPlayer()), taczGunSpec2.getDamage());
                }
              }
            }
          }
        }
      };
    }
  }
}

