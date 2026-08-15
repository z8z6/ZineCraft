package com.cxxcxx.zinecraft.api.weapon.tacz;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TaczAmmoSpec {
  @NotNull
  private final ResourceLocation id;
  @NotNull
  private final String translationKey;
  private final int stackSize;
  private final int sort;
  @Nullable
  private final String modelPath;
  @Nullable
  private final String texturePath;
  @Nullable
  private final String slotTexturePath;

  public TaczAmmoSpec(
      @NotNull ResourceLocation id,
      @NotNull String translationKey,
      int stackSize,
      int sort,
      @Nullable String modelPath,
      @Nullable String texturePath,
      @Nullable String slotTexturePath
  ) {
    super();
    this.id = id;
    this.translationKey = translationKey;
    this.stackSize = stackSize;
    this.sort = sort;
    this.modelPath = modelPath;
    this.texturePath = texturePath;
    this.slotTexturePath = slotTexturePath;
  }

  // $VF: synthetic method
  public static TaczAmmoSpec copy$default(
      TaczAmmoSpec var0, ResourceLocation var1, String var2, int var3, int var4, String var5, String var6, String var7, int var8, Object var9
  ) {
    if ((var8 & 1) != 0) {
      var1 = var0.id;
    }

    if ((var8 & 2) != 0) {
      var2 = var0.translationKey;
    }

    if ((var8 & 4) != 0) {
      var3 = var0.stackSize;
    }

    if ((var8 & 8) != 0) {
      var4 = var0.sort;
    }

    if ((var8 & 16) != 0) {
      var5 = var0.modelPath;
    }

    if ((var8 & 32) != 0) {
      var6 = var0.texturePath;
    }

    if ((var8 & 64) != 0) {
      var7 = var0.slotTexturePath;
    }

    return var0.copy(var1, var2, var3, var4, var5, var6, var7);
  }

  @NotNull
  public final ResourceLocation getId() {
    return this.id;
  }

  @NotNull
  public final String getTranslationKey() {
    return this.translationKey;
  }

  public final int getStackSize() {
    return this.stackSize;
  }

  public final int getSort() {
    return this.sort;
  }

  @Nullable
  public final String getModelPath() {
    return this.modelPath;
  }

  @Nullable
  public final String getTexturePath() {
    return this.texturePath;
  }

  @Nullable
  public final String getSlotTexturePath() {
    return this.slotTexturePath;
  }

  @NotNull
  public final ResourceLocation component1() {
    return this.id;
  }

  @NotNull
  public final String component2() {
    return this.translationKey;
  }

  public final int component3() {
    return this.stackSize;
  }

  public final int component4() {
    return this.sort;
  }

  @Nullable
  public final String component5() {
    return this.modelPath;
  }

  @Nullable
  public final String component6() {
    return this.texturePath;
  }

  @Nullable
  public final String component7() {
    return this.slotTexturePath;
  }

  @NotNull
  public final TaczAmmoSpec copy(
      @NotNull ResourceLocation id,
      @NotNull String translationKey,
      int stackSize,
      int sort,
      @Nullable String modelPath,
      @Nullable String texturePath,
      @Nullable String slotTexturePath
  ) {
    return new TaczAmmoSpec(id, translationKey, stackSize, sort, modelPath, texturePath, slotTexturePath);
  }

  @Override
  public int hashCode() {
    int i = this.id.hashCode();
    i = i * 31 + this.translationKey.hashCode();
    i = i * 31 + Integer.hashCode(this.stackSize);
    i = i * 31 + Integer.hashCode(this.sort);
    i = i * 31 + (this.modelPath == null ? 0 : this.modelPath.hashCode());
    i = i * 31 + (this.texturePath == null ? 0 : this.texturePath.hashCode());
    return i * 31 + (this.slotTexturePath == null ? 0 : this.slotTexturePath.hashCode());
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TaczAmmoSpec taczAmmoSpec)) {
      return false;
    } else if (!java.util.Objects.equals(this.id, taczAmmoSpec.id)) {
      return false;
    } else if (!java.util.Objects.equals(this.translationKey, taczAmmoSpec.translationKey)) {
      return false;
    } else if (this.stackSize != taczAmmoSpec.stackSize) {
      return false;
    } else if (this.sort != taczAmmoSpec.sort) {
      return false;
    } else if (!java.util.Objects.equals(this.modelPath, taczAmmoSpec.modelPath)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.texturePath, taczAmmoSpec.texturePath)
          ? false
          : java.util.Objects.equals(this.slotTexturePath, taczAmmoSpec.slotTexturePath);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TaczAmmoSpec(id="
        + this.id
        + ", translationKey="
        + this.translationKey
        + ", stackSize="
        + this.stackSize
        + ", sort="
        + this.sort
        + ", modelPath="
        + this.modelPath
        + ", texturePath="
        + this.texturePath
        + ", slotTexturePath="
        + this.slotTexturePath
        + ")";
  }
}

