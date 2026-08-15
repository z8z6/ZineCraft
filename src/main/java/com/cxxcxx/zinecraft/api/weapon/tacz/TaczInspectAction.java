package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import kotlin.ranges.IntRange;
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
    return TaczWeaponActionsKt.access$gun(context) != null && context.getPlayer().isAlive();
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull WeaponContext context) {
    IntRange intRange = new IntRange(0, 0);
    return new TimedWeaponActionRuntime(intRange, 40) {
      @Override
      protected void onTick(int tick) {
      }
    };
  }
}

