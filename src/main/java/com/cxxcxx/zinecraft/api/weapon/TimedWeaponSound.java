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

  // $VF: synthetic method
  public static TimedWeaponSound copy$default(TimedWeaponSound var0, ResourceLocation var1, int var2, int var3, Object var4) {
    if ((var3 & 1) != 0) {
      var1 = var0.id;
    }

    if ((var3 & 2) != 0) {
      var2 = var0.tick;
    }

    return var0.copy(var1, var2);
  }

  @NotNull
  public final ResourceLocation getId() {
    return this.id;
  }

  public final int getTick() {
    return this.tick;
  }

  @NotNull
  public final ResourceLocation component1() {
    return this.id;
  }

  public final int component2() {
    return this.tick;
  }

  @NotNull
  public final TimedWeaponSound copy(@NotNull ResourceLocation id, int tick) {
    return new TimedWeaponSound(id, tick);
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

