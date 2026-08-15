package com.cxxcxx.zinecraft.api.weapon.tacz;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TaczSoundAsset {
  @NotNull
  private final String sourcePath;
  @NotNull
  private final ResourceLocation runtimeId;

  public TaczSoundAsset(@NotNull String sourcePath, @NotNull ResourceLocation runtimeId) {
    super();
    this.sourcePath = sourcePath;
    this.runtimeId = runtimeId;
  }

  @NotNull
  public final String getSourcePath() {
    return this.sourcePath;
  }

  @NotNull
  public final ResourceLocation getRuntimeId() {
    return this.runtimeId;
  }

  @Override
  public int hashCode() {
    int i = this.sourcePath.hashCode();
    return i * 31 + this.runtimeId.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TaczSoundAsset taczSoundAsset)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.sourcePath, taczSoundAsset.sourcePath) ? false : java.util.Objects.equals(this.runtimeId, taczSoundAsset.runtimeId);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TaczSoundAsset(sourcePath=" + this.sourcePath + ", runtimeId=" + this.runtimeId + ")";
  }
}

