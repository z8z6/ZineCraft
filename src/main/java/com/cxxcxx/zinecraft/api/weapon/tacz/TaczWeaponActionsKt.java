package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.collections.CollectionsKt;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class TaczWeaponActionsKt {
  private static final TaczGunSpec gun(WeaponContext $this$gun) {
    ResourceLocation resourceLocation2 = (ResourceLocation) $this$gun.getStack().get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    TaczGunSpec taczGunSpec;
    if (resourceLocation2 != null) {
      ResourceLocation resourceLocation = resourceLocation2;
      TaczGunPacks taczGunPacks = TaczGunPacks.INSTANCE;
      ResourceLocation resourceLocation1 = resourceLocation;
      int i = 0;
      taczGunSpec = taczGunPacks.gun(resourceLocation1);
    } else {
      taczGunSpec = null;
    }

    return taczGunSpec;
  }

  private static final String fireMode(WeaponContext context) {
    Integer integer = (Integer) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getFIRE_MODE(), 0);
    String string1;
    if (integer != null && integer == 0) {
      string1 = "auto";
    } else {
      byte selected = 1;
      if (integer != null && integer == selected) {
        string1 = "semi";
      } else {
        selected = 2;
        string1 = integer != null && integer == selected ? "burst" : "unknown";
      }
    }

    String string = string1;
    int i = 0;
    TaczGunSpec taczGunSpec = gun(context);
    if (taczGunSpec != null) {
      List list1 = taczGunSpec.getFireModes();
      if (list1 != null) {
        List list = list1;
        int j = 0;
        string1 = list.contains(string) ? string : (String) CollectionsKt.firstOrNull(list);
        if (string1 != null) {
          return string1;
        }
      }
    }

    return string;
  }

  private static final int fireModeOrdinal(String mode) {
    switch (mode) {
      case "auto":
        return 0;
      case "semi":
        return 1;
      case "burst":
        return 2;
    }

    return 3;
  }

  // $VF: synthetic method
  public static final TaczGunSpec access$gun(WeaponContext $receiver) {
    return gun($receiver);
  }

  // $VF: synthetic method
  public static final String access$fireMode(WeaponContext context) {
    return fireMode(context);
  }

  // $VF: synthetic method
  public static final int access$fireModeOrdinal(String mode) {
    return fireModeOrdinal(mode);
  }
}
