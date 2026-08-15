package com.cxxcxx.zinecraft.api.weapon;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public final class WeaponPresentation {
  @Nullable
  private final ResourceLocation playerAnimation;
  @Nullable
  private final ResourceLocation weaponAnimation;
  @NotNull
  private final List<TimedWeaponVfx> vfx;
  @NotNull
  private final List<TimedWeaponSound> sounds;
  private final int durationTicks;

  public WeaponPresentation(
      @Nullable ResourceLocation playerAnimation,
      @Nullable ResourceLocation weaponAnimation,
      @NotNull List<TimedWeaponVfx> vfx,
      @NotNull List<TimedWeaponSound> sounds,
      int durationTicks
  ) {
    super();
    this.playerAnimation = playerAnimation;
    this.weaponAnimation = weaponAnimation;
    this.vfx = vfx;
    this.sounds = sounds;
    this.durationTicks = durationTicks;
    if (this.durationTicks <= 0) {
      int m = 0;
      String string2 = "表现持续时间必须大于 0";
      throw new IllegalArgumentException(string2.toString());
    }

    Iterable<?> iterable = this.vfx;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      label90:
      {
        for (Object object : iterable) {
          TimedWeaponVfx timedWeaponVfx = (TimedWeaponVfx) object;
          int j = 0;
          int k = this.durationTicks;
          int l = timedWeaponVfx.getTick();
          if (0 <= l ? l >= k : true) {
            bl = false;
            break label90;
          }
        }

        bl = true;
      }
    }

    if (!bl) {
      i = 0;
      String string1 = "特效时间必须位于表现时间线内";
      throw new IllegalArgumentException(string1.toString());
    }

    iterable = this.sounds;
    i = 0;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      label92:
      {
        for (Object object1 : iterable) {
          TimedWeaponSound timedWeaponSound = (TimedWeaponSound) object1;
          int n = 0;
          int o = this.durationTicks;
          int p = timedWeaponSound.getTick();
          if (0 <= p ? p >= o : true) {
            bl = false;
            break label92;
          }
        }

        bl = true;
      }
    }

    if (!bl) {
      i = 0;
      String string = "声音时间必须位于表现时间线内";
      throw new IllegalArgumentException(string.toString());
    }
  }

  @Nullable
  public final ResourceLocation getPlayerAnimation() {
    return this.playerAnimation;
  }

  @Nullable
  public final ResourceLocation getWeaponAnimation() {
    return this.weaponAnimation;
  }

  @NotNull
  public final List<TimedWeaponVfx> getVfx() {
    return this.vfx;
  }

  @NotNull
  public final List<TimedWeaponSound> getSounds() {
    return this.sounds;
  }

  public final int getDurationTicks() {
    return this.durationTicks;
  }

  @Override
  public int hashCode() {
    int i = this.playerAnimation == null ? 0 : this.playerAnimation.hashCode();
    i = i * 31 + (this.weaponAnimation == null ? 0 : this.weaponAnimation.hashCode());
    i = i * 31 + this.vfx.hashCode();
    i = i * 31 + this.sounds.hashCode();
    return i * 31 + Integer.hashCode(this.durationTicks);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof WeaponPresentation weaponPresentation)) {
      return false;
    } else if (!java.util.Objects.equals(this.playerAnimation, weaponPresentation.playerAnimation)) {
      return false;
    } else if (!java.util.Objects.equals(this.weaponAnimation, weaponPresentation.weaponAnimation)) {
      return false;
    } else if (!java.util.Objects.equals(this.vfx, weaponPresentation.vfx)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.sounds, weaponPresentation.sounds) ? false : this.durationTicks == weaponPresentation.durationTicks;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponPresentation(playerAnimation="
        + this.playerAnimation
        + ", weaponAnimation="
        + this.weaponAnimation
        + ", vfx="
        + this.vfx
        + ", sounds="
        + this.sounds
        + ", durationTicks="
        + this.durationTicks
        + ")";
  }
}
