package com.cxxcxx.zinecraft.api.accessory;

import net.minecraft.world.item.Rarity;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Immutable runtime data for one collectible item.
 */
public record CollectibleSpec(
    String path,
    String orderId,
    String zhCn,
    String enUs,
    String originalEffectZhCn,
    String originalEffectEnUs,
    String descriptionZhCn,
    String descriptionEnUs,
    String minecraftEffectZhCn,
    String minecraftEffectEnUs,
    CollectiblePower power,
    Rarity rarity,
    int originalEffectLineCount,
    int descriptionLineCount
) {
  private static final Pattern PATH_PATTERN = Pattern.compile("[a-z0-9_]+");
  private static final Pattern ORDER_PATTERN = Pattern.compile("(?:[0-9]{3}|PCS[0-9]{2})");

  public CollectibleSpec {
    path = Objects.requireNonNull(path, "path");
    orderId = Objects.requireNonNull(orderId, "orderId");
    zhCn = requireText(zhCn, "藏品名称不能为空：" + path);
    enUs = requireText(enUs, "藏品名称不能为空：" + path);
    originalEffectZhCn = requireText(originalEffectZhCn, "藏品原效果不能为空：" + path);
    originalEffectEnUs = requireText(originalEffectEnUs, "藏品原效果不能为空：" + path);
    descriptionZhCn = requireText(descriptionZhCn, "藏品原描述不能为空：" + path);
    descriptionEnUs = requireText(descriptionEnUs, "藏品原描述不能为空：" + path);
    minecraftEffectZhCn = requireText(minecraftEffectZhCn, "藏品适配说明不能为空：" + path);
    minecraftEffectEnUs = requireText(minecraftEffectEnUs, "藏品适配说明不能为空：" + path);
    power = Objects.requireNonNull(power, "power");
    rarity = Objects.requireNonNull(rarity, "rarity");
    if (!PATH_PATTERN.matcher(path).matches()) {
      throw new IllegalArgumentException("藏品 ID 必须是 snake_case：" + path);
    }
    if (!ORDER_PATTERN.matcher(orderId).matches()) {
      throw new IllegalArgumentException("藏品编号格式无效：" + orderId);
    }
    if (originalEffectLineCount < 0 || descriptionLineCount < 0) {
      throw new IllegalArgumentException("藏品提示行数不能为负数：" + path);
    }
  }

  public CollectibleSpec withLineCounts(int originalEffectLines, int descriptionLines) {
    return new CollectibleSpec(
        path, orderId, zhCn, enUs,
        originalEffectZhCn, originalEffectEnUs,
        descriptionZhCn, descriptionEnUs,
        minecraftEffectZhCn, minecraftEffectEnUs,
        power, rarity, originalEffectLines, descriptionLines
    );
  }

  private static String requireText(String value, String message) {
    Objects.requireNonNull(value, "value");
    if (value.isBlank()) throw new IllegalArgumentException(message);
    return value;
  }
}
