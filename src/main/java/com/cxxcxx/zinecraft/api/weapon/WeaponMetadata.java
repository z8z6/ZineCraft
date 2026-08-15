package com.cxxcxx.zinecraft.api.weapon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponMetadata {
  @NotNull
  private final String translationKey;

  public WeaponMetadata(@NotNull String translationKey) {
    super();
    this.translationKey = translationKey;
  }

  // $VF: synthetic method
  public static WeaponMetadata copy$default(WeaponMetadata var0, String var1, int var2, Object var3) {
    if ((var2 & 1) != 0) {
      var1 = var0.translationKey;
    }

    return var0.copy(var1);
  }

  @NotNull
  public final String getTranslationKey() {
    return this.translationKey;
  }

  @NotNull
  public final String component1() {
    return this.translationKey;
  }

  @NotNull
  public final WeaponMetadata copy(@NotNull String translationKey) {
    return new WeaponMetadata(translationKey);
  }

  @Override
  public int hashCode() {
    return this.translationKey.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else {
      return !(other instanceof WeaponMetadata weaponMetadata) ? false : java.util.Objects.equals(this.translationKey, weaponMetadata.translationKey);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponMetadata(translationKey=" + this.translationKey + ")";
  }
}

