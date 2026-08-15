package com.cxxcxx.zinecraft.api.accessory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LocalizedTooltipLine {
  @NotNull
  private final String zhCn;
  @NotNull
  private final String enUs;

  public LocalizedTooltipLine(@NotNull String zhCn, @NotNull String enUs) {
    super();
    this.zhCn = zhCn;
    this.enUs = enUs;
  }

  // $VF: synthetic method
  public static LocalizedTooltipLine copy$default(LocalizedTooltipLine var0, String var1, String var2, int var3, Object var4) {
    if ((var3 & 1) != 0) {
      var1 = var0.zhCn;
    }

    if ((var3 & 2) != 0) {
      var2 = var0.enUs;
    }

    return var0.copy(var1, var2);
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
  public final String component1() {
    return this.zhCn;
  }

  @NotNull
  public final String component2() {
    return this.enUs;
  }

  @NotNull
  public final LocalizedTooltipLine copy(@NotNull String zhCn, @NotNull String enUs) {
    return new LocalizedTooltipLine(zhCn, enUs);
  }

  @Override
  public int hashCode() {
    int i = this.zhCn.hashCode();
    return i * 31 + this.enUs.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof LocalizedTooltipLine localizedTooltipLine)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.zhCn, localizedTooltipLine.zhCn) ? false : java.util.Objects.equals(this.enUs, localizedTooltipLine.enUs);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "LocalizedTooltipLine(zhCn=" + this.zhCn + ", enUs=" + this.enUs + ")";
  }
}

