package com.cxxcxx.zinecraft.api.weapon.vfx;

import com.cxxcxx.zinecraft.api.weapon.backend.WeaponShotContext;
import org.jetbrains.annotations.NotNull;

/**
 * Receives high-level effect cues; implementations must not perform gameplay settlement.
 */
@FunctionalInterface
public interface WeaponVfxService {
  WeaponVfxService NONE = context -> {
  };

  void onShot(@NotNull WeaponShotContext context);
}
