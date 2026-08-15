package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.ranges.IntRange;
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
    return TaczWeaponActionsKt.access$gun(context) != null && context.getPlayer().isAlive();
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    TaczGunSpec taczGunSpec = TaczWeaponActionsKt.access$gun(context);
    int i = taczGunSpec != null ? taczGunSpec.getAimTicks() : 1;
    IntRange intRange = new IntRange(0, 0);
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

