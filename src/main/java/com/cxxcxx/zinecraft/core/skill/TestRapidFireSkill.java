package com.cxxcxx.zinecraft.core.skill;

import com.cxxcxx.zinecraft.api.weapon.backend.WeaponShotContext;
import com.cxxcxx.zinecraft.api.weapon.vfx.WeaponVfxService;
import org.jetbrains.annotations.NotNull;

/**
 * MVP proof skill: a backend-neutral shot is forwarded to the high-level VFX boundary.
 */
public final class TestRapidFireSkill {
  @NotNull
  private final WeaponVfxService vfx;

  public TestRapidFireSkill(@NotNull WeaponVfxService vfx) {
    this.vfx = vfx;
  }

  public void onShot(@NotNull WeaponShotContext context) {
    vfx.onShot(context);
  }
}
