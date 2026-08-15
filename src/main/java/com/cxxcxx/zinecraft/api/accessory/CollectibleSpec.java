package com.cxxcxx.zinecraft.api.accessory;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CollectibleSpec {
  @NotNull
  private final String path;
  @NotNull
  private final String orderId;
  @NotNull
  private final String zhCn;
  @NotNull
  private final String enUs;
  @NotNull
  private final String originalEffectZhCn;
  @NotNull
  private final String originalEffectEnUs;
  @NotNull
  private final String descriptionZhCn;
  @NotNull
  private final String descriptionEnUs;
  @NotNull
  private final String minecraftEffectZhCn;
  @NotNull
  private final String minecraftEffectEnUs;
  @NotNull
  private final CollectiblePower power;
  @NotNull
  private final Rarity rarity;
  private final int originalEffectLineCount;
  private final int descriptionLineCount;

  public CollectibleSpec(
      @NotNull String path,
      @NotNull String orderId,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull String originalEffectZhCn,
      @NotNull String originalEffectEnUs,
      @NotNull String descriptionZhCn,
      @NotNull String descriptionEnUs,
      @NotNull String minecraftEffectZhCn,
      @NotNull String minecraftEffectEnUs,
      @NotNull CollectiblePower power,
      @NotNull Rarity rarity,
      int originalEffectLineCount,
      int descriptionLineCount
  ) {
    super();
    this.path = path;
    this.orderId = orderId;
    this.zhCn = zhCn;
    this.enUs = enUs;
    this.originalEffectZhCn = originalEffectZhCn;
    this.originalEffectEnUs = originalEffectEnUs;
    this.descriptionZhCn = descriptionZhCn;
    this.descriptionEnUs = descriptionEnUs;
    this.minecraftEffectZhCn = minecraftEffectZhCn;
    this.minecraftEffectEnUs = minecraftEffectEnUs;
    this.power = power;
    this.rarity = rarity;
    this.originalEffectLineCount = originalEffectLineCount;
    this.descriptionLineCount = descriptionLineCount;
    CharSequence charSequence = this.path;
    if (!new Regex("[a-z0-9_]+").matches(charSequence)) {
      int n = 0;
      String string5 = "藏品 ID 必须是 snake_case：" + this.path;
      throw new IllegalArgumentException(string5.toString());
    }

    charSequence = this.orderId;
    if (!new Regex("(?:[0-9]{3}|PCS[0-9]{2})").matches(charSequence)) {
      int m = 0;
      String string4 = "藏品编号格式无效：" + this.orderId;
      throw new IllegalArgumentException(string4.toString());
    }

    if (StringsKt.isBlank(this.zhCn) || StringsKt.isBlank(this.enUs)) {
      int l = 0;
      String string3 = "藏品名称不能为空：" + this.path;
      throw new IllegalArgumentException(string3.toString());
    }

    if (StringsKt.isBlank(this.originalEffectZhCn) || StringsKt.isBlank(this.originalEffectEnUs)) {
      int k = 0;
      String string2 = "藏品原效果不能为空：" + this.path;
      throw new IllegalArgumentException(string2.toString());
    }

    if (StringsKt.isBlank(this.descriptionZhCn) || StringsKt.isBlank(this.descriptionEnUs)) {
      int j = 0;
      String string1 = "藏品原描述不能为空：" + this.path;
      throw new IllegalArgumentException(string1.toString());
    }

    if (StringsKt.isBlank(this.minecraftEffectZhCn) || StringsKt.isBlank(this.minecraftEffectEnUs)) {
      int i = 0;
      String string = "藏品适配说明不能为空：" + this.path;
      throw new IllegalArgumentException(string.toString());
    }
  }

  // $VF: synthetic method
  public CollectibleSpec(
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      CollectiblePower var11,
      Rarity var12,
      int var13,
      int var14,
      int var15,
      DefaultConstructorMarker var16
  ) {
    this(
        var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11,
        (var15 & 2048) != 0 ? Rarity.UNCOMMON : var12,
        (var15 & 4096) != 0 ? 0 : var13,
        (var15 & 8192) != 0 ? 0 : var14
    );
  }

  // $VF: synthetic method
  public static CollectibleSpec copy$default(
      CollectibleSpec var0,
      String var1,
      String var2,
      String var3,
      String var4,
      String var5,
      String var6,
      String var7,
      String var8,
      String var9,
      String var10,
      CollectiblePower var11,
      Rarity var12,
      int var13,
      int var14,
      int var15,
      Object var16
  ) {
    if ((var15 & 1) != 0) {
      var1 = var0.path;
    }

    if ((var15 & 2) != 0) {
      var2 = var0.orderId;
    }

    if ((var15 & 4) != 0) {
      var3 = var0.zhCn;
    }

    if ((var15 & 8) != 0) {
      var4 = var0.enUs;
    }

    if ((var15 & 16) != 0) {
      var5 = var0.originalEffectZhCn;
    }

    if ((var15 & 32) != 0) {
      var6 = var0.originalEffectEnUs;
    }

    if ((var15 & 64) != 0) {
      var7 = var0.descriptionZhCn;
    }

    if ((var15 & 128) != 0) {
      var8 = var0.descriptionEnUs;
    }

    if ((var15 & 256) != 0) {
      var9 = var0.minecraftEffectZhCn;
    }

    if ((var15 & 512) != 0) {
      var10 = var0.minecraftEffectEnUs;
    }

    if ((var15 & 1024) != 0) {
      var11 = var0.power;
    }

    if ((var15 & 2048) != 0) {
      var12 = var0.rarity;
    }

    if ((var15 & 4096) != 0) {
      var13 = var0.originalEffectLineCount;
    }

    if ((var15 & 8192) != 0) {
      var14 = var0.descriptionLineCount;
    }

    return var0.copy(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14);
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final String getOrderId() {
    return this.orderId;
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
  public final String getOriginalEffectZhCn() {
    return this.originalEffectZhCn;
  }

  @NotNull
  public final String getOriginalEffectEnUs() {
    return this.originalEffectEnUs;
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
  public final String getMinecraftEffectZhCn() {
    return this.minecraftEffectZhCn;
  }

  @NotNull
  public final String getMinecraftEffectEnUs() {
    return this.minecraftEffectEnUs;
  }

  @NotNull
  public final CollectiblePower getPower() {
    return this.power;
  }

  @NotNull
  public final Rarity getRarity() {
    return this.rarity;
  }

  public final int getOriginalEffectLineCount$zinecraft() {
    return this.originalEffectLineCount;
  }

  public final int getDescriptionLineCount$zinecraft() {
    return this.descriptionLineCount;
  }

  @NotNull
  public final String component1() {
    return this.path;
  }

  @NotNull
  public final String component2() {
    return this.orderId;
  }

  @NotNull
  public final String component3() {
    return this.zhCn;
  }

  @NotNull
  public final String component4() {
    return this.enUs;
  }

  @NotNull
  public final String component5() {
    return this.originalEffectZhCn;
  }

  @NotNull
  public final String component6() {
    return this.originalEffectEnUs;
  }

  @NotNull
  public final String component7() {
    return this.descriptionZhCn;
  }

  @NotNull
  public final String component8() {
    return this.descriptionEnUs;
  }

  @NotNull
  public final String component9() {
    return this.minecraftEffectZhCn;
  }

  @NotNull
  public final String component10() {
    return this.minecraftEffectEnUs;
  }

  @NotNull
  public final CollectiblePower component11() {
    return this.power;
  }

  @NotNull
  public final Rarity component12() {
    return this.rarity;
  }

  public final int component13$zinecraft() {
    return this.originalEffectLineCount;
  }

  public final int component14$zinecraft() {
    return this.descriptionLineCount;
  }

  @NotNull
  public final CollectibleSpec copy(
      @NotNull String path,
      @NotNull String orderId,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull String originalEffectZhCn,
      @NotNull String originalEffectEnUs,
      @NotNull String descriptionZhCn,
      @NotNull String descriptionEnUs,
      @NotNull String minecraftEffectZhCn,
      @NotNull String minecraftEffectEnUs,
      @NotNull CollectiblePower power,
      @NotNull Rarity rarity,
      int originalEffectLineCount,
      int descriptionLineCount
  ) {
    return new CollectibleSpec(
        path,
        orderId,
        zhCn,
        enUs,
        originalEffectZhCn,
        originalEffectEnUs,
        descriptionZhCn,
        descriptionEnUs,
        minecraftEffectZhCn,
        minecraftEffectEnUs,
        power,
        rarity,
        originalEffectLineCount,
        descriptionLineCount
    );
  }

  @Override
  public int hashCode() {
    int i = this.path.hashCode();
    i = i * 31 + this.orderId.hashCode();
    i = i * 31 + this.zhCn.hashCode();
    i = i * 31 + this.enUs.hashCode();
    i = i * 31 + this.originalEffectZhCn.hashCode();
    i = i * 31 + this.originalEffectEnUs.hashCode();
    i = i * 31 + this.descriptionZhCn.hashCode();
    i = i * 31 + this.descriptionEnUs.hashCode();
    i = i * 31 + this.minecraftEffectZhCn.hashCode();
    i = i * 31 + this.minecraftEffectEnUs.hashCode();
    i = i * 31 + this.power.hashCode();
    i = i * 31 + this.rarity.hashCode();
    i = i * 31 + Integer.hashCode(this.originalEffectLineCount);
    return i * 31 + Integer.hashCode(this.descriptionLineCount);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof CollectibleSpec collectibleSpec)) {
      return false;
    } else if (!java.util.Objects.equals(this.path, collectibleSpec.path)) {
      return false;
    } else if (!java.util.Objects.equals(this.orderId, collectibleSpec.orderId)) {
      return false;
    } else if (!java.util.Objects.equals(this.zhCn, collectibleSpec.zhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.enUs, collectibleSpec.enUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.originalEffectZhCn, collectibleSpec.originalEffectZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.originalEffectEnUs, collectibleSpec.originalEffectEnUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.descriptionZhCn, collectibleSpec.descriptionZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.descriptionEnUs, collectibleSpec.descriptionEnUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.minecraftEffectZhCn, collectibleSpec.minecraftEffectZhCn)) {
      return false;
    } else if (!java.util.Objects.equals(this.minecraftEffectEnUs, collectibleSpec.minecraftEffectEnUs)) {
      return false;
    } else if (!java.util.Objects.equals(this.power, collectibleSpec.power)) {
      return false;
    } else if (this.rarity != collectibleSpec.rarity) {
      return false;
    } else {
      return this.originalEffectLineCount != collectibleSpec.originalEffectLineCount
          ? false
          : this.descriptionLineCount == collectibleSpec.descriptionLineCount;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "CollectibleSpec(path="
        + this.path
        + ", orderId="
        + this.orderId
        + ", zhCn="
        + this.zhCn
        + ", enUs="
        + this.enUs
        + ", originalEffectZhCn="
        + this.originalEffectZhCn
        + ", originalEffectEnUs="
        + this.originalEffectEnUs
        + ", descriptionZhCn="
        + this.descriptionZhCn
        + ", descriptionEnUs="
        + this.descriptionEnUs
        + ", minecraftEffectZhCn="
        + this.minecraftEffectZhCn
        + ", minecraftEffectEnUs="
        + this.minecraftEffectEnUs
        + ", power="
        + this.power
        + ", rarity="
        + this.rarity
        + ", originalEffectLineCount="
        + this.originalEffectLineCount
        + ", descriptionLineCount="
        + this.descriptionLineCount
        + ")";
  }
}

