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

  @NotNull
  public final String getTranslationKey() {
    return this.translationKey;
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

