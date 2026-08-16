package com.cxxcxx.zinecraft.api.weapon.action;

import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import org.jetbrains.annotations.NotNull;

public interface WeaponActionRuntime {
  int getCurrentTick();

  @NotNull
  ActionPhase getPhase();

  boolean getFinished();

  void tick();

  /**
   * Whether a new, server-validated input may cancel this runtime. Most actions are atomic; actions
   * such as inspection and shell-by-shell reload explicitly opt in.
   */
  default boolean canInterrupt(@NotNull WeaponInput input) {
    return false;
  }
}
