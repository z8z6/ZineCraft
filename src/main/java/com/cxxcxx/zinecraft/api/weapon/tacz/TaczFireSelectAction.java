package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.ranges.IntRange;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class TaczFireSelectAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczFireSelectAction(@NotNull ResourceLocation id) {
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
    if (taczGunSpec != null) {
      List list = taczGunSpec.getFireModes();
      if (list != null) {
        return list.size() > 1;
      }
    }

    return 0 > 1;
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    IntRange intRange = new IntRange(0, 0);
    return new TimedWeaponActionRuntime(intRange, 4) {
      @Override
      protected void onTick(int tick) {
        if (tick == 0) {
          TaczGunSpec taczGunSpec1 = TaczWeaponActionsKt.access$gun(context);
          if (taczGunSpec1 != null) {
            TaczGunSpec taczGunSpec = taczGunSpec1;
            String string = TaczWeaponActionsKt.access$fireMode(context);
            List<String> list = taczGunSpec.getFireModes();
            int current = Math.max(list.indexOf(string), 0);
            String string1 = list.get((current + 1) % list.size());
            context.getStack().set(WeaponStateComponents.INSTANCE.getFIRE_MODE(), TaczWeaponActionsKt.access$fireModeOrdinal(string1));
          }
        }
      }
    };
  }
}

