package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class TaczInspectAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczInspectAction(@NotNull ResourceLocation id) {
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
    return TaczWeaponActions.gun(context) != null && context.getPlayer().isAlive()
        && !context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false);
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull WeaponContext context) {
    TickRange intRange = new TickRange(0, 0);
    TaczGunSpec gun = TaczWeaponActions.gun(context);
    int duration = gun == null ? 40 : gun.getAssets().animationDurationTicks(
        context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity()) <= 0
        ? "inspect_empty" : "inspect", 40);
    return new TimedWeaponActionRuntime(intRange, duration) {
      @Override
      protected void onTick(int tick) {
      }

      @Override
      public boolean canInterrupt(@NotNull WeaponInput input) {
        return input == WeaponInput.PRIMARY || input == WeaponInput.SECONDARY || input == WeaponInput.RELOAD
            || input == WeaponInput.MELEE;
      }
    };
  }
}
