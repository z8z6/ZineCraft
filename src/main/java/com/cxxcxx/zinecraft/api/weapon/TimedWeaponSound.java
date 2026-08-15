package com.cxxcxx.zinecraft.api.weapon;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TimedWeaponSound {
  @NotNull
  private final ResourceLocation id;
  private final int tick;

  public TimedWeaponSound(@NotNull ResourceLocation id, int tick) {
    super();
    this.id = id;
    this.tick = tick;
  }

  @NotNull
  public final ResourceLocation getId() {
    return this.id;
  }

  public final int getTick() {
    return this.tick;
  }

  @Override
  public int hashCode() {
    int i = this.id.hashCode();
    return i * 31 + Integer.hashCode(this.tick);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TimedWeaponSound timedWeaponSound)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.id, timedWeaponSound.id) ? false : this.tick == timedWeaponSound.tick;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TimedWeaponSound(id=" + this.id + ", tick=" + this.tick + ")";
  }
}

