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

  @NotNull
  public final String getZhCn() {
    return this.zhCn;
  }

  @NotNull
  public final String getEnUs() {
    return this.enUs;
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

