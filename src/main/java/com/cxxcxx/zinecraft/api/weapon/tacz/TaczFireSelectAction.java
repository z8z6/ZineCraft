package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
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
    TaczGunSpec taczGunSpec = TaczWeaponActions.gun(context);
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
    TickRange intRange = new TickRange(0, 0);
    return new TimedWeaponActionRuntime(intRange, 4) {
      @Override
      protected void onTick(int tick) {
        if (tick == 0) {
          TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
          if (taczGunSpec1 != null) {
            TaczGunSpec taczGunSpec = taczGunSpec1;
            String string = TaczWeaponActions.fireMode(context);
            List<String> list = taczGunSpec.getFireModes();
            int current = Math.max(list.indexOf(string), 0);
            String string1 = list.get((current + 1) % list.size());
            context.getStack().set(WeaponStateComponents.INSTANCE.getFIRE_MODE(), TaczWeaponActions.fireModeOrdinal(string1));
          }
        }
      }
    };
  }
}

