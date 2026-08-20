package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.datagen.RegistryDataContributor;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Builder;
import net.minecraft.world.item.enchantment.Enchantment.EnchantmentDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 附魔动态注册目录，保存声明并在数据生成启动阶段构建附魔值。
 */
public final class EnchantmentCatalog implements RegistryDataContributor {
  private static final int MIN_WEIGHT = 1;
  private static final int MAX_WEIGHT = 1024;
  private static final int MIN_LEVEL = 1;
  private static final int MAX_LEVEL = 255;

  private final String namespace;
  private final TranslationCatalog translations;
  private final List<EnchantmentBuilder> entries = new ArrayList<>();

  /**
   * 创建附魔目录。
   *
   * @param namespace    模组命名空间
   * @param translations 用于登记附魔名称的翻译目录
   */
  public EnchantmentCatalog(String namespace, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 校验附魔声明、创建资源键并登记名称翻译。
   *
   * @param entry 附魔声明
   * @return 已分配资源键的声明
   */
  public EnchantmentBuilder register(EnchantmentBuilder entry) {
    validate(entry);
    entry.key = net.minecraft.resources.ResourceKey.create(
        Registries.ENCHANTMENT,
        ResourceLocation.fromNamespaceAndPath(namespace, entry.path)
    );
    entries.add(entry);
    translations.add("enchantment." + namespace + "." + entry.path, entry.zhCn, entry.enUs);
    return entry;
  }

  /**
   * 将所有声明构建并写入附魔动态注册表。
   *
   * @param context 附魔的启动注册上下文
   */
  public void bootstrap(BootstrapContext<Enchantment> context) {
    HolderGetter<Item> items = context.lookup(Registries.ITEM);
    HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

    for (EnchantmentBuilder entry : entries) {
      EnchantmentDefinition definition = createDefinition(entry, items);
      Builder builder = Enchantment.enchantment(definition);
      if (entry.exclusiveWith != null) {
        HolderSet<Enchantment> exclusiveEnchantments = enchantments.getOrThrow(entry.exclusiveWith);
        builder.exclusiveWith(exclusiveEnchantments);
      }

      entry.configure.accept(builder);
      context.register(entry.getKey(), builder.build(entry.getKey().location()));
    }
  }

  /**
   * @param registryBuilder 数据包动态注册表构建器
   */
  @Override
  public void contribute(RegistrySetBuilder registryBuilder) {
    registryBuilder.add(Registries.ENCHANTMENT, this::bootstrap);
  }

  /**
   * 根据声明和物品标签查询器创建原版附魔定义。
   *
   * @param entry 附魔声明
   * @param items 物品注册表查询器
   * @return 原版附魔定义
   */
  private EnchantmentDefinition createDefinition(EnchantmentBuilder entry, HolderGetter<Item> items) {
    HolderSet<Item> supportedItems = items.getOrThrow(entry.supportedItems);
    EquipmentSlotGroup[] slots = entry.slots.toArray(EquipmentSlotGroup[]::new);
    if (entry.primaryItems == null) {
      return Enchantment.definition(
          supportedItems,
          entry.weight,
          entry.maxLevel,
          entry.minCost,
          entry.maxCost,
          entry.anvilCost,
          slots
      );
    }

    HolderSet<Item> primaryItems = items.getOrThrow(entry.primaryItems);
    return Enchantment.definition(
        supportedItems,
        primaryItems,
        entry.weight,
        entry.maxLevel,
        entry.minCost,
        entry.maxCost,
        entry.anvilCost,
        slots
    );
  }

  /**
   * 校验声明归属、ID、名称、数值范围、装备槽和重复项。
   *
   * @param entry 待校验的附魔声明
   */
  private void validate(EnchantmentBuilder entry) {
    if (entry.catalog != this) {
      throw new IllegalArgumentException("附魔 builder 不属于当前目录：" + entry.path);
    }
    if (!ResourceLocation.isValidPath(entry.path)) {
      throw new IllegalArgumentException("附魔 ID 路径无效：" + entry.path);
    }
    if (entry.zhCn.isBlank()) {
      throw new IllegalArgumentException("附魔中文名不能为空：" + entry.path);
    }
    if (entry.enUs.isBlank()) {
      throw new IllegalArgumentException("附魔英文名不能为空：" + entry.path);
    }
    if (entries.stream().anyMatch(existing -> existing.path.equals(entry.path))) {
      throw new IllegalArgumentException("附魔 ID 重复：" + entry.path);
    }
    if (entry.weight < MIN_WEIGHT || entry.weight > MAX_WEIGHT) {
      throw new IllegalArgumentException("附魔权重必须在 1 到 1024 之间：" + entry.path);
    }
    if (entry.maxLevel < MIN_LEVEL || entry.maxLevel > MAX_LEVEL) {
      throw new IllegalArgumentException("附魔最高等级必须在 1 到 255 之间：" + entry.path);
    }
    if (entry.anvilCost < 0) {
      throw new IllegalArgumentException("附魔铁砧成本不能为负数：" + entry.path);
    }
    if (entry.slots.isEmpty()) {
      throw new IllegalArgumentException("附魔至少需要一个装备槽：" + entry.path);
    }
  }
}
