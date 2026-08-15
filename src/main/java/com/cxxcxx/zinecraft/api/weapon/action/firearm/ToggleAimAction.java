package com.cxxcxx.zinecraft.api.weapon.action.firearm;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class ToggleAimAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;
  private final int durationTicks;

  public ToggleAimAction(@NotNull ResourceLocation id, int durationTicks) {
    super();
    this.id = id;
    this.durationTicks = durationTicks;
    if (this.durationTicks <= 0) {
      int i = 0;
      String string = "瞄准切换时间必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }
  }

  // $VF: synthetic method
  public ToggleAimAction(ResourceLocation var1, int var2, int var3, DefaultConstructorMarker var4) {
    this(var1, (var3 & 2) != 0 ? 6 : var2);
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return context.getPlayer().isAlive() && !context.getPlayer().isSpectator();
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    IntRange intRange = new IntRange(0, 0);
    int i = this.durationTicks;
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

