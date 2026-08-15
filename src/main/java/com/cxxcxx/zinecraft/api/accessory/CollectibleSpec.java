package com.cxxcxx.zinecraft.api.accessory;

import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Immutable declaration for one collectible and its localized source text.
 */
public final class CollectibleSpec {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");
  private static final Pattern ORDER_PATTERN = Pattern.compile("(?:[0-9]{3}|PCS[0-9]{2})");

  private final String path;
  private final String orderId;
  private final String zhCn;
  private final String enUs;
  private final String originalEffectZhCn;
  private final String originalEffectEnUs;
  private final String descriptionZhCn;
  private final String descriptionEnUs;
  private final String minecraftEffectZhCn;
  private final String minecraftEffectEnUs;
  private final CollectiblePower power;
  private final Rarity rarity;
  private final int originalEffectLineCount;
  private final int descriptionLineCount;

  public CollectibleSpec(
      String path, String orderId, String zhCn, String enUs,
      String originalEffectZhCn, String originalEffectEnUs,
      String descriptionZhCn, String descriptionEnUs,
      String minecraftEffectZhCn, String minecraftEffectEnUs,
      CollectiblePower power, Rarity rarity,
      int originalEffectLineCount, int descriptionLineCount
  ) {
    this.path = Objects.requireNonNull(path, "path");
    this.orderId = Objects.requireNonNull(orderId, "orderId");
    this.zhCn = requireText(zhCn, "藏品名称不能为空：" + path);
    this.enUs = requireText(enUs, "藏品名称不能为空：" + path);
    this.originalEffectZhCn = requireText(originalEffectZhCn, "藏品原效果不能为空：" + path);
    this.originalEffectEnUs = requireText(originalEffectEnUs, "藏品原效果不能为空：" + path);
    this.descriptionZhCn = requireText(descriptionZhCn, "藏品原描述不能为空：" + path);
    this.descriptionEnUs = requireText(descriptionEnUs, "藏品原描述不能为空：" + path);
    this.minecraftEffectZhCn = requireText(minecraftEffectZhCn, "藏品适配说明不能为空：" + path);
    this.minecraftEffectEnUs = requireText(minecraftEffectEnUs, "藏品适配说明不能为空：" + path);
    this.power = Objects.requireNonNull(power, "power");
    this.rarity = Objects.requireNonNull(rarity, "rarity");
    if (!PATH_PATTERN.matcher(path).matches()) {
      throw new IllegalArgumentException("藏品 ID 必须是 snake_case：" + path);
    }
    if (!ORDER_PATTERN.matcher(orderId).matches()) {
      throw new IllegalArgumentException("藏品编号格式无效：" + orderId);
    }
    if (originalEffectLineCount < 0 || descriptionLineCount < 0) {
      throw new IllegalArgumentException("藏品提示行数不能为负数：" + path);
    }
    this.originalEffectLineCount = originalEffectLineCount;
    this.descriptionLineCount = descriptionLineCount;
  }

  private static String requireText(String value, String message) {
    Objects.requireNonNull(value, "value");
    if (value.isBlank()) throw new IllegalArgumentException(message);
    return value;
  }

  public CollectibleSpec withLineCounts(int originalEffectLines, int descriptionLines) {
    return new CollectibleSpec(path, orderId, zhCn, enUs, originalEffectZhCn, originalEffectEnUs,
        descriptionZhCn, descriptionEnUs, minecraftEffectZhCn, minecraftEffectEnUs,
        power, rarity, originalEffectLines, descriptionLines);
  }

  @NotNull
  public String getPath() {
    return path;
  }

  @NotNull
  public String getOrderId() {
    return orderId;
  }

  @NotNull
  public String getZhCn() {
    return zhCn;
  }

  @NotNull
  public String getEnUs() {
    return enUs;
  }

  @NotNull
  public String getOriginalEffectZhCn() {
    return originalEffectZhCn;
  }

  @NotNull
  public String getOriginalEffectEnUs() {
    return originalEffectEnUs;
  }

  @NotNull
  public String getDescriptionZhCn() {
    return descriptionZhCn;
  }

  @NotNull
  public String getDescriptionEnUs() {
    return descriptionEnUs;
  }

  @NotNull
  public String getMinecraftEffectZhCn() {
    return minecraftEffectZhCn;
  }

  @NotNull
  public String getMinecraftEffectEnUs() {
    return minecraftEffectEnUs;
  }

  @NotNull
  public CollectiblePower getPower() {
    return power;
  }

  @NotNull
  public Rarity getRarity() {
    return rarity;
  }

  public int getOriginalEffectLineCount() {
    return originalEffectLineCount;
  }

  public int getDescriptionLineCount() {
    return descriptionLineCount;
  }

  @Override
  public int hashCode() {
    return Objects.hash(path, orderId, zhCn, enUs, originalEffectZhCn, originalEffectEnUs,
        descriptionZhCn, descriptionEnUs, minecraftEffectZhCn, minecraftEffectEnUs,
        power, rarity, originalEffectLineCount, descriptionLineCount);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof CollectibleSpec that)) return false;
    return originalEffectLineCount == that.originalEffectLineCount
        && descriptionLineCount == that.descriptionLineCount
        && path.equals(that.path) && orderId.equals(that.orderId)
        && zhCn.equals(that.zhCn) && enUs.equals(that.enUs)
        && originalEffectZhCn.equals(that.originalEffectZhCn)
        && originalEffectEnUs.equals(that.originalEffectEnUs)
        && descriptionZhCn.equals(that.descriptionZhCn)
        && descriptionEnUs.equals(that.descriptionEnUs)
        && minecraftEffectZhCn.equals(that.minecraftEffectZhCn)
        && minecraftEffectEnUs.equals(that.minecraftEffectEnUs)
        && power.equals(that.power) && rarity == that.rarity;
  }
}
