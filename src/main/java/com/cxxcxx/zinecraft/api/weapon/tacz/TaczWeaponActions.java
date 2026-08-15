package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;

/**
 * Shared lookups used by TaCZ weapon actions.
 */
public final class TaczWeaponActions {
  private TaczWeaponActions() {
  }

  public static TaczGunSpec gun(WeaponContext context) {
    var gunId = context.getStack().get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    return gunId == null ? null : TaczGunPacks.INSTANCE.gun(gunId);
  }

  public static String fireMode(WeaponContext context) {
    int selected = context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getFIRE_MODE(), 0);
    String requested = switch (selected) {
      case 0 -> "auto";
      case 1 -> "semi";
      case 2 -> "burst";
      default -> "unknown";
    };
    TaczGunSpec gun = gun(context);
    if (gun == null || gun.getFireModes().contains(requested)) return requested;
    return gun.getFireModes().isEmpty() ? requested : gun.getFireModes().getFirst();
  }

  public static int fireModeOrdinal(String mode) {
    return switch (mode) {
      case "auto" -> 0;
      case "semi" -> 1;
      case "burst" -> 2;
      default -> 3;
    };
  }
}
