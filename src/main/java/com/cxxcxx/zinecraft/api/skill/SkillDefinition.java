package com.cxxcxx.zinecraft.api.skill;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkillDefinition {
  @NotNull
  private final String path;
  @NotNull
  private final String zhCn;
  @NotNull
  private final String enUs;
  @NotNull
  private final String operatorZhCn;
  @NotNull
  private final String operatorEnUs;
  @NotNull
  private final SkillProfession profession;
  @NotNull
  private final String recoveryZhCn;
  @NotNull
  private final String recoveryEnUs;
  @NotNull
  private final String triggerZhCn;
  @NotNull
  private final String triggerEnUs;
  private final int initialSp;
  private final int spCost;
  @Nullable
  private final Integer durationSeconds;
  @NotNull
  private final String descriptionZhCn;
  @NotNull
  private final String descriptionEnUs;
  @NotNull
  private final SkillDemoTheme theme;

  public SkillDefinition(
      @NotNull String path,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull String operatorZhCn,
      @NotNull String operatorEnUs,
      @NotNull SkillProfession profession,
      @NotNull String recoveryZhCn,
      @NotNull String recoveryEnUs,
      @NotNull String triggerZhCn,
      @NotNull String triggerEnUs,
      int initialSp,
      int spCost,
      @Nullable Integer durationSeconds,
      @NotNull String descriptionZhCn,
      @NotNull String descriptionEnUs,
      @NotNull SkillDemoTheme theme
  ) {
    super();
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = enUs;
    this.operatorZhCn = operatorZhCn;
    this.operatorEnUs = operatorEnUs;
    this.profession = profession;
    this.recoveryZhCn = recoveryZhCn;
    this.recoveryEnUs = recoveryEnUs;
    this.triggerZhCn = triggerZhCn;
    this.triggerEnUs = triggerEnUs;
    this.initialSp = initialSp;
    this.spCost = spCost;
    this.durationSeconds = durationSeconds;
    this.descriptionZhCn = descriptionZhCn;
    this.descriptionEnUs = descriptionEnUs;
    this.theme = theme;
  }

  // $VF: synthetic method
  public static SkillDefinition copy$default(
      SkillDefinition var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      SkillProfession var6,
      String var7,
      String var8,
      String var9,
      String var10,
      int var11,
      int var12,
      Integer var13,
      String var14,
      String var15,
      SkillDemoTheme var16,
      int var17,
      Object var18
  ) {
    if ((var17 & 1) != 0) {
      var1 = var0.path;
    }

    if ((var17 & 2) != 0) {
      var2 = var0.zhCn;
    }

    if ((var17 & 4) != 0) {
      var3 = var0.enUs;
    }

    if ((var17 & 8) != 0) {
      var4 = var0.operatorZhCn;
    }

    if ((var17 & 16) != 0) {
      var5 = var0.operatorEnUs;
    }

    if ((var17 & 32) != 0) {
      var6 = var0.profession;
    }

    if ((var17 & 64) != 0) {
      var7 = var0.recoveryZhCn;
    }

    if ((var17 & 128) != 0) {
      var8 = var0.recoveryEnUs;
    }

    if ((var17 & 256) != 0) {
      var9 = var0.triggerZhCn;
    }

    if ((var17 & 512) != 0) {
      var10 = var0.triggerEnUs;
    }

    if ((var17 & 1024) != 0) {
      var11 = var0.initialSp;
    }

    if ((var17 & 2048) != 0) {
      var12 = var0.spCost;
    }

    if ((var17 & 4096) != 0) {
      var13 = var0.durationSeconds;
    }

    if ((var17 & 8192) != 0) {
      var14 = var0.descriptionZhCn;
    }

    if ((var17 & 16384) != 0) {
      var15 = var0.descriptionEnUs;
    }

    if ((var17 & 32768) != 0) {
      var16 = var0.theme;
    }

    return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16);
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final String getZhCn() {
    return this.zhCn;
  }

  @NotNull
  public final String getEnUs() {
    return this.enUs;
  }

  @NotNull
  public final String getOperatorZhCn() {
    return this.operatorZhCn;
  }

  @NotNull
  public final String getOperatorEnUs() {
    return this.operatorEnUs;
  }

  @NotNull
  public final SkillProfession getProfession() {
    return this.profession;
  }

  @NotNull
  public final String getRecoveryZhCn() {
    return this.recoveryZhCn;
  }

  @NotNull
  public final String getRecoveryEnUs() {
    return this.recoveryEnUs;
  }

  @NotNull
  public final String getTriggerZhCn() {
    return this.triggerZhCn;
  }

  @NotNull
  public final String getTriggerEnUs() {
    return this.triggerEnUs;
  }

  public final int getInitialSp() {
    return this.initialSp;
  }

  public final int getSpCost() {
    return this.spCost;
  }

  @Nullable
  public final Integer getDurationSeconds() {
    return this.durationSeconds;
  }

  @NotNull
  public final String getDescriptionZhCn() {
    return this.descriptionZhCn;
  }

  @NotNull
  public final String getDescriptionEnUs() {
    return this.descriptionEnUs;
  }

  @NotNull
  public final SkillDemoTheme getTheme() {
    return this.theme;
  }

  @NotNull
  public final String component1() {
    return this.path;
  }

  @NotNull
  public final String component2() {
    return this.zhCn;
  }

  @NotNull
  public final String component3() {
    return this.enUs;
  }

  @NotNull
  public final String component4() {
    return this.operatorZhCn;
  }

  @NotNull
  public final String component5() {
    return this.operatorEnUs;
  }

  @NotNull
  public final SkillProfession component6() {
    return this.profession;
  }

  @NotNull
  public final String component7() {
    return this.recoveryZhCn;
  }

  @NotNull
  public final String component8() {
    return this.recoveryEnUs;
  }

  @NotNull
  public final String component9() {
    return this.triggerZhCn;
  }

  @NotNull
  public final String component10() {
    return this.triggerEnUs;
  }

  public final int component11() {
    return this.initialSp;
  }

  public final int component12() {
    return this.spCost;
  }

  @Nullable
  public final Integer component13() {
    return this.durationSeconds;
  }

  @NotNull
  public final String component14() {
    return this.descriptionZhCn;
  }

  @NotNull
  public final String component15() {
    return this.descriptionEnUs;
  }

  @NotNull
  public final SkillDemoTheme component16() {
    return this.theme;
  }

  @NotNull
  public final SkillDefinition copy(
      @NotNull String path,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull String operatorZhCn,
      @NotNull String operatorEnUs,
      @NotNull SkillProfession profession,
      @NotNull String recoveryZhCn,
      @NotNull String recoveryEnUs,
      @NotNull String triggerZhCn,
      @NotNull String triggerEnUs,
      int initialSp,
      int spCost,
      @Nullable Integer durationSeconds,
      @NotNull String descriptionZhCn,
      @NotNull String descriptionEnUs,
      @NotNull SkillDemoTheme theme
  ) {
    return new SkillDefinition(
        path,
        zhCn,
        enUs,
        operatorZhCn,
        operatorEnUs,
        profession,
        recoveryZhCn,
        recoveryEnUs,
        triggerZhCn,
        triggerEnUs,
        initialSp,
        spCost,
        durationSeconds,
        descriptionZhCn,
        descriptionEnUs,
        theme
    );
  }

  @Override
  public int hashCode() {
    int i = this.path.hashCode();
    i = i * 31 + this.zhCn.hashCode();
    i = i * 31 + this.enUs.hashCode();
    i = i * 31 + this.operatorZhCn.hashCode();
    i = i * 31 + this.operatorEnUs.hashCode();
    i = i * 31 + this.profession.hashCode();
    i = i * 31 + this.recoveryZhCn.hashCode();
    i = i * 31 + this.recoveryEnUs.hashCode();
    i = i * 31 + this.triggerZhCn.hashCode();
    i = i * 31 + this.triggerEnUs.hashCode();
    i = i * 31 + Integer.hashCode(this.initialSp);
    i = i * 31 + Integer.hashCode(this.spCost);
    i = i * 31 + (this.durationSeconds == null ? 0 : this.durationSeconds.hashCode());
    i = i * 31 + this.descriptionZhCn.hashCode();
    i = i * 31 + this.descriptionEnUs.hashCode();
    return i * 31 + this.theme.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof SkillDefinition skillDefinition)) {
      return false;
    } else if (!java.util.Objects.equals(this.path, skillDefinition.path)) {
      return false;
    } else if (!java.util.Objects.equals(this.zhCn, skillDefinition.zhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.enUs, skillDefinition.enUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.operatorZhCn, skillDefinition.operatorZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.operatorEnUs, skillDefinition.operatorEnUs)) {
      return false;
    } else if (this.profession != skillDefinition.profession) {
      return false;
    } else if (!java.util.Objects.equals(this.recoveryZhCn, skillDefinition.recoveryZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.recoveryEnUs, skillDefinition.recoveryEnUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.triggerZhCn, skillDefinition.triggerZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.triggerEnUs, skillDefinition.triggerEnUs)) {
      return false;
    } else if (this.initialSp != skillDefinition.initialSp) {
      return false;
    } else if (this.spCost != skillDefinition.spCost) {
      return false;
    } else if (!java.util.Objects.equals(this.durationSeconds, skillDefinition.durationSeconds)) {
      return false;
    } else if (!java.util.Objects.equals(this.descriptionZhCn, skillDefinition.descriptionZhCn)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.descriptionEnUs, skillDefinition.descriptionEnUs) ? false : this.theme == skillDefinition.theme;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "SkillDefinition(path="
        + this.path
        + ", zhCn="
        + this.zhCn
        + ", enUs="
        + this.enUs
        + ", operatorZhCn="
        + this.operatorZhCn
        + ", operatorEnUs="
        + this.operatorEnUs
        + ", profession="
        + this.profession
        + ", recoveryZhCn="
        + this.recoveryZhCn
        + ", recoveryEnUs="
        + this.recoveryEnUs
        + ", triggerZhCn="
        + this.triggerZhCn
        + ", triggerEnUs="
        + this.triggerEnUs
        + ", initialSp="
        + this.initialSp
        + ", spCost="
        + this.spCost
        + ", durationSeconds="
        + this.durationSeconds
        + ", descriptionZhCn="
        + this.descriptionZhCn
        + ", descriptionEnUs="
        + this.descriptionEnUs
        + ", theme="
        + this.theme
        + ")";
  }
}

