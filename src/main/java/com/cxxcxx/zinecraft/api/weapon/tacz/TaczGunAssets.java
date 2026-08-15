package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class TaczGunAssets {
  @Nullable
  private final String modelPath;
  @Nullable
  private final String texturePath;
  @Nullable
  private final String slotTexturePath;
  @Nullable
  private final String animationPath;
  @Nullable
  private final String defaultAnimationPath;
  @Nullable
  private final String stateMachinePath;
  @NotNull
  private final JsonObject stateMachineParameters;
  @Nullable
  private final String playerAnimationPath;
  @Nullable
  private final ResourceLocation playerAnimationId;
  @NotNull
  private final String thirdPersonAnimation;
  private final boolean fixedThirdPersonHand;
  @NotNull
  private final Map<String, TaczSoundAsset> sounds;

  public TaczGunAssets(
      @Nullable String modelPath,
      @Nullable String texturePath,
      @Nullable String slotTexturePath,
      @Nullable String animationPath,
      @Nullable String defaultAnimationPath,
      @Nullable String stateMachinePath,
      @NotNull JsonObject stateMachineParameters,
      @Nullable String playerAnimationPath,
      @Nullable ResourceLocation playerAnimationId,
      @NotNull String thirdPersonAnimation,
      boolean fixedThirdPersonHand,
      @NotNull Map<String, TaczSoundAsset> sounds
  ) {
    super();
    this.modelPath = modelPath;
    this.texturePath = texturePath;
    this.slotTexturePath = slotTexturePath;
    this.animationPath = animationPath;
    this.defaultAnimationPath = defaultAnimationPath;
    this.stateMachinePath = stateMachinePath;
    this.stateMachineParameters = stateMachineParameters;
    this.playerAnimationPath = playerAnimationPath;
    this.playerAnimationId = playerAnimationId;
    this.thirdPersonAnimation = thirdPersonAnimation;
    this.fixedThirdPersonHand = fixedThirdPersonHand;
    this.sounds = sounds;
  }

  // $VF: synthetic method
  public static TaczGunAssets copy$default(
      TaczGunAssets var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      JsonObject var7,
      String var8,
      ResourceLocation var9,
      String var10,
      boolean var11,
      Map var12,
      int var13,
      Object var14
  ) {
    if ((var13 & 1) != 0) {
      var1 = var0.modelPath;
    }

    if ((var13 & 2) != 0) {
      var2 = var0.texturePath;
    }

    if ((var13 & 4) != 0) {
      var3 = var0.slotTexturePath;
    }

    if ((var13 & 8) != 0) {
      var4 = var0.animationPath;
    }

    if ((var13 & 16) != 0) {
      var5 = var0.defaultAnimationPath;
    }

    if ((var13 & 32) != 0) {
      var6 = var0.stateMachinePath;
    }

    if ((var13 & 64) != 0) {
      var7 = var0.stateMachineParameters;
    }

    if ((var13 & 128) != 0) {
      var8 = var0.playerAnimationPath;
    }

    if ((var13 & 256) != 0) {
      var9 = var0.playerAnimationId;
    }

    if ((var13 & 512) != 0) {
      var10 = var0.thirdPersonAnimation;
    }

    if ((var13 & 1024) != 0) {
      var11 = var0.fixedThirdPersonHand;
    }

    if ((var13 & 2048) != 0) {
      var12 = var0.sounds;
    }

    return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
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

  @Nullable
  public final String getAnimationPath() {
    return this.animationPath;
  }

  @Nullable
  public final String getDefaultAnimationPath() {
    return this.defaultAnimationPath;
  }

  @Nullable
  public final String getStateMachinePath() {
    return this.stateMachinePath;
  }

  @NotNull
  public final JsonObject getStateMachineParameters() {
    return this.stateMachineParameters;
  }

  @Nullable
  public final String getPlayerAnimationPath() {
    return this.playerAnimationPath;
  }

  @Nullable
  public final ResourceLocation getPlayerAnimationId() {
    return this.playerAnimationId;
  }

  @NotNull
  public final String getThirdPersonAnimation() {
    return this.thirdPersonAnimation;
  }

  public final boolean getFixedThirdPersonHand() {
    return this.fixedThirdPersonHand;
  }

  @NotNull
  public final Map<String, TaczSoundAsset> getSounds() {
    return this.sounds;
  }

  @Nullable
  public final String component1() {
    return this.modelPath;
  }

  @Nullable
  public final String component2() {
    return this.texturePath;
  }

  @Nullable
  public final String component3() {
    return this.slotTexturePath;
  }

  @Nullable
  public final String component4() {
    return this.animationPath;
  }

  @Nullable
  public final String component5() {
    return this.defaultAnimationPath;
  }

  @Nullable
  public final String component6() {
    return this.stateMachinePath;
  }

  @NotNull
  public final JsonObject component7() {
    return this.stateMachineParameters;
  }

  @Nullable
  public final String component8() {
    return this.playerAnimationPath;
  }

  @Nullable
  public final ResourceLocation component9() {
    return this.playerAnimationId;
  }

  @NotNull
  public final String component10() {
    return this.thirdPersonAnimation;
  }

  public final boolean component11() {
    return this.fixedThirdPersonHand;
  }

  @NotNull
  public final Map<String, TaczSoundAsset> component12() {
    return this.sounds;
  }

  @NotNull
  public final TaczGunAssets copy(
      @Nullable String modelPath,
      @Nullable String texturePath,
      @Nullable String slotTexturePath,
      @Nullable String animationPath,
      @Nullable String defaultAnimationPath,
      @Nullable String stateMachinePath,
      @NotNull JsonObject stateMachineParameters,
      @Nullable String playerAnimationPath,
      @Nullable ResourceLocation playerAnimationId,
      @NotNull String thirdPersonAnimation,
      boolean fixedThirdPersonHand,
      @NotNull Map<String, TaczSoundAsset> sounds
  ) {
    return new TaczGunAssets(
        modelPath,
        texturePath,
        slotTexturePath,
        animationPath,
        defaultAnimationPath,
        stateMachinePath,
        stateMachineParameters,
        playerAnimationPath,
        playerAnimationId,
        thirdPersonAnimation,
        fixedThirdPersonHand,
        sounds
    );
  }

  @Override
  public int hashCode() {
    int i = this.modelPath == null ? 0 : this.modelPath.hashCode();
    i = i * 31 + (this.texturePath == null ? 0 : this.texturePath.hashCode());
    i = i * 31 + (this.slotTexturePath == null ? 0 : this.slotTexturePath.hashCode());
    i = i * 31 + (this.animationPath == null ? 0 : this.animationPath.hashCode());
    i = i * 31 + (this.defaultAnimationPath == null ? 0 : this.defaultAnimationPath.hashCode());
    i = i * 31 + (this.stateMachinePath == null ? 0 : this.stateMachinePath.hashCode());
    i = i * 31 + this.stateMachineParameters.hashCode();
    i = i * 31 + (this.playerAnimationPath == null ? 0 : this.playerAnimationPath.hashCode());
    i = i * 31 + (this.playerAnimationId == null ? 0 : this.playerAnimationId.hashCode());
    i = i * 31 + this.thirdPersonAnimation.hashCode();
    i = i * 31 + Boolean.hashCode(this.fixedThirdPersonHand);
    return i * 31 + this.sounds.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof TaczGunAssets taczGunAssets)) {
      return false;
    } else if (!java.util.Objects.equals(this.modelPath, taczGunAssets.modelPath)) {
      return false;
    } else if (!java.util.Objects.equals(this.texturePath, taczGunAssets.texturePath)) {
      return false;
    } else if (!java.util.Objects.equals(this.slotTexturePath, taczGunAssets.slotTexturePath)) {
      return false;
    } else if (!java.util.Objects.equals(this.animationPath, taczGunAssets.animationPath)) {
      return false;
    } else if (!java.util.Objects.equals(this.defaultAnimationPath, taczGunAssets.defaultAnimationPath)) {
      return false;
    } else if (!java.util.Objects.equals(this.stateMachinePath, taczGunAssets.stateMachinePath)) {
      return false;
    } else if (!java.util.Objects.equals(this.stateMachineParameters, taczGunAssets.stateMachineParameters)) {
      return false;
    } else if (!java.util.Objects.equals(this.playerAnimationPath, taczGunAssets.playerAnimationPath)) {
      return false;
    } else if (!java.util.Objects.equals(this.playerAnimationId, taczGunAssets.playerAnimationId)) {
      return false;
    } else if (!java.util.Objects.equals(this.thirdPersonAnimation, taczGunAssets.thirdPersonAnimation)) {
      return false;
    } else {
      return this.fixedThirdPersonHand != taczGunAssets.fixedThirdPersonHand ? false : java.util.Objects.equals(this.sounds, taczGunAssets.sounds);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "TaczGunAssets(modelPath="
        + this.modelPath
        + ", texturePath="
        + this.texturePath
        + ", slotTexturePath="
        + this.slotTexturePath
        + ", animationPath="
        + this.animationPath
        + ", defaultAnimationPath="
        + this.defaultAnimationPath
        + ", stateMachinePath="
        + this.stateMachinePath
        + ", stateMachineParameters="
        + this.stateMachineParameters
        + ", playerAnimationPath="
        + this.playerAnimationPath
        + ", playerAnimationId="
        + this.playerAnimationId
        + ", thirdPersonAnimation="
        + this.thirdPersonAnimation
        + ", fixedThirdPersonHand="
        + this.fixedThirdPersonHand
        + ", sounds="
        + this.sounds
        + ")";
  }
}

