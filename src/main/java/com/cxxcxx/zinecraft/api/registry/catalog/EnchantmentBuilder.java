package com.cxxcxx.zinecraft.api.registry.catalog;


import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Builder;
import net.minecraft.world.item.enchantment.Enchantment.Cost;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 附魔声明构建器，用于配置适用物品、等级、成本、权重、槽位和互斥关系。
 */
public final class EnchantmentBuilder {
  final EnchantmentCatalog catalog;
  final String path;
  final String zhCn;
  final TagKey<Item> supportedItems;
  String enUs;
  @Nullable TagKey<Item> primaryItems;
  @Nullable TagKey<Enchantment> exclusiveWith;
  int weight = 10;
  int maxLevel = 1;
  Cost minCost = Enchantment.constantCost(1);
  Cost maxCost = Enchantment.constantCost(1);
  int anvilCost = 1;
  List<EquipmentSlotGroup> slots = List.of(EquipmentSlotGroup.ANY);
  Consumer<? super Builder> configure = builder -> {
  };
  @Nullable ResourceKey<Enchantment> key;

  /**
   * 创建使用默认英文名和默认数值的附魔声明。
   *
   * @param catalog        接收附魔的目录
   * @param path           附魔的命名空间内路径
   * @param zhCn           附魔的简体中文名称
   * @param supportedItems 可接受该附魔的物品标签
   */
  public EnchantmentBuilder(
      EnchantmentCatalog catalog,
      String path,
      String zhCn,
      TagKey<Item> supportedItems
  ) {
    this.catalog = Objects.requireNonNull(catalog, "附魔目录不能为空");
    this.path = Objects.requireNonNull(path, "附魔 ID 不能为空");
    this.zhCn = Objects.requireNonNull(zhCn, "附魔中文名不能为空：" + path);
    this.supportedItems = Objects.requireNonNull(supportedItems, "附魔支持物品标签不能为空：" + path);
    this.enUs = TranslationCatalog.toDisplayName(path);
  }

  /**
   * 设置附魔的英文名称。
   *
   * @param enUs 英文名称
   * @return 当前构建器
   */
  public EnchantmentBuilder enUs(String enUs) {
    this.enUs = Objects.requireNonNull(enUs, "附魔英文名不能为空：" + path);
    return this;
  }

  /**
   * 设置附魔时优先选用的物品标签。
   *
   * @param primaryItems 主要物品标签；为 {@code null} 时不作区分
   * @return 当前构建器
   */
  public EnchantmentBuilder primaryItems(@Nullable TagKey<Item> primaryItems) {
    this.primaryItems = primaryItems;
    return this;
  }

  /**
   * 设置与当前附魔互斥的附魔标签。
   *
   * @param exclusiveWith 互斥附魔标签；为 {@code null} 时不声明互斥集合
   * @return 当前构建器
   */
  public EnchantmentBuilder exclusiveWith(@Nullable TagKey<Enchantment> exclusiveWith) {
    this.exclusiveWith = exclusiveWith;
    return this;
  }

  /**
   * 设置附魔随机选择权重。
   *
   * @param weight 1 到 1024 的权重
   * @return 当前构建器
   */
  public EnchantmentBuilder weight(int weight) {
    this.weight = weight;
    return this;
  }

  /**
   * 设置附魔最高等级。
   *
   * @param maxLevel 1 到 255 的最高等级
   * @return 当前构建器
   */
  public EnchantmentBuilder maxLevel(int maxLevel) {
    this.maxLevel = maxLevel;
    return this;
  }

  /**
   * 设置各等级的最低和最高附魔成本函数。
   *
   * @param minCost 最低成本函数
   * @param maxCost 最高成本函数
   * @return 当前构建器
   */
  public EnchantmentBuilder costs(Cost minCost, Cost maxCost) {
    this.minCost = Objects.requireNonNull(minCost, "附魔最低成本不能为空：" + path);
    this.maxCost = Objects.requireNonNull(maxCost, "附魔最高成本不能为空：" + path);
    return this;
  }

  /**
   * 设置铁砧合并时的附魔成本。
   *
   * @param anvilCost 非负铁砧成本
   * @return 当前构建器
   */
  public EnchantmentBuilder anvilCost(int anvilCost) {
    this.anvilCost = anvilCost;
    return this;
  }

  /**
   * 设置附魔生效的装备槽组。
   *
   * @param slots 一个或多个装备槽组
   * @return 当前构建器
   */
  public EnchantmentBuilder slots(EquipmentSlotGroup... slots) {
    this.slots = List.of(slots);
    return this;
  }

  /**
   * 设置对原版附魔构建器的附加配置。
   *
   * @param configure 接收原版附魔构建器的回调
   * @return 当前构建器
   */
  public EnchantmentBuilder configure(Consumer<? super Builder> configure) {
    this.configure = Objects.requireNonNull(configure, "附魔配置不能为空：" + path);
    return this;
  }

  /**
   * 校验并将附魔登记到所属目录。
   *
   * @return 已登记的当前构建器
   */
  public EnchantmentBuilder build() {
    return catalog.register(this);
  }

  /** @return 动态注册表中的附魔资源键 */
  public ResourceKey<Enchantment> getKey() {
    return Objects.requireNonNull(key, "附魔尚未登记：" + path);
  }
}
