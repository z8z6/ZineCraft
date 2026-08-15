package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class TaczAimAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczAimAction(@NotNull ResourceLocation id) {
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
    TaczGunSpec taczGunSpec = TaczWeaponActions.gun(context);
    int i = taczGunSpec != null ? taczGunSpec.getAimTicks() : 1;
    TickRange intRange = new TickRange(0, 0);
    return new TimedWeaponActionRuntime(intRange, i) {
      @Override
      protected void onTick(int tick) {
        if (tick == 0) {
          Boolean boolean_ = (Boolean) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
          context.getStack().set(WeaponStateComponents.INSTANCE.getAIMING(), !boolean_);
        }
      }
    };
  }
}

