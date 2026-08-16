package com.cxxcxx.zinecraft.api.weapon.event;

import com.cxxcxx.zinecraft.api.weapon.backend.WeaponShotContext;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Backend-neutral notification emitted after a ranged weapon has been allowed to fire.
 */
public final class WeaponShotEvent extends Event {
  @NotNull
  private final WeaponShotContext context;

  public WeaponShotEvent(@NotNull WeaponShotContext context) {
    this.context = Objects.requireNonNull(context, "context");
  }

  @NotNull
  public WeaponShotContext context() {
    return context;
  }
}
