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

  // $VF: synthetic method
  public static TaczSoundAsset copy$default(TaczSoundAsset var0, String var1, ResourceLocation var2, int var3, Object var4) {
    if ((var3 & 1) != 0) {
      var1 = var0.sourcePath;
    }

    if ((var3 & 2) != 0) {
      var2 = var0.runtimeId;
    }

    return var0.copy(var1, var2);
  }

  @NotNull
  public final String getSourcePath() {
    return this.sourcePath;
  }

  @NotNull
  public final ResourceLocation getRuntimeId() {
    return this.runtimeId;
  }

  @NotNull
  public final String component1() {
    return this.sourcePath;
  }

  @NotNull
  public final ResourceLocation component2() {
    return this.runtimeId;
  }

  @NotNull
  public final TaczSoundAsset copy(@NotNull String sourcePath, @NotNull ResourceLocation runtimeId) {
    return new TaczSoundAsset(sourcePath, runtimeId);
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

