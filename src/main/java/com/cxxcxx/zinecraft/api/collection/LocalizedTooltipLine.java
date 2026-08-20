package com.cxxcxx.zinecraft.api.collection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 表示数据生成阶段中同一提示行的简体中文与英文文本。
 */
public final class LocalizedTooltipLine {
  @NotNull
  private final String zhCn;
  @NotNull
  private final String enUs;

  /**
   * 创建一组位置对应的双语提示文本。
   *
   * @param zhCn 简体中文文本
   * @param enUs 英文文本
   */
  public LocalizedTooltipLine(@NotNull String zhCn, @NotNull String enUs) {
    super();
    this.zhCn = zhCn;
    this.enUs = enUs;
  }

  /**
   * @return 简体中文提示文本
   */
  @NotNull
  public final String getZhCn() {
    return this.zhCn;
  }

  /** @return 英文提示文本 */
  @NotNull
  public final String getEnUs() {
    return this.enUs;
  }

  /** @return 由中英文文本共同计算的哈希值 */
  @Override
  public int hashCode() {
    int i = this.zhCn.hashCode();
    return i * 31 + this.enUs.hashCode();
  }

  /**
   * 按中英文文本内容判断两行本地化提示是否相等。
   *
   * @param other 要比较的对象
   * @return 两种语言文本均相等时返回 {@code true}
   */
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

  /** @return 包含中英文内容的调试字符串 */
  @NotNull
  @Override
  public String toString() {
    return "LocalizedTooltipLine(zhCn=" + this.zhCn + ", enUs=" + this.enUs + ")";
  }
}

