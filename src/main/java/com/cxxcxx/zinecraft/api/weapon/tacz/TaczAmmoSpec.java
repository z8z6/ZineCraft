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

