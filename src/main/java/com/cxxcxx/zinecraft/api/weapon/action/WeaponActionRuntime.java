package com.cxxcxx.zinecraft.api.weapon.action;

import org.jetbrains.annotations.NotNull;

public interface WeaponActionRuntime {
  int getCurrentTick();

  @NotNull
  ActionPhase getPhase();

  boolean getFinished();

  void tick();
}
