package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class TaczBoltAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczBoltAction(@NotNull ResourceLocation id) {
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
    TaczGunSpec taczGunSpec = TaczWeaponActionsKt.access$gun(context);
    return java.util.Objects.equals(taczGunSpec != null ? taczGunSpec.getBolt() : null, "manual_action")
        && (Boolean) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false);
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    TaczGunSpec taczGunSpec = TaczWeaponActionsKt.access$gun(context);
    int i = taczGunSpec != null ? RangesKt.coerceAtLeast(taczGunSpec.getBoltActionTicks(), 1) : 1;
    IntRange intRange = new IntRange(0, 0);
    return new TimedWeaponActionRuntime(intRange, i) {
      @Override
      protected void onTick(int tick) {
        if (tick == 0) {
          context.getStack().set(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false);
        }
      }
    };
  }
}

