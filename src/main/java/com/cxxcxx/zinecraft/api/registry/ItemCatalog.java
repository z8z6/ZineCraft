package com.cxxcxx.zinecraft.api.registry;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

// 通用的物品注册器
public final class ItemCatalog {
  public final DeferredRegister.Items registry;
  private final TranslationCatalog translations;
  private final List<ItemBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<ItemBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  public ItemCatalog(String mod, TranslationCatalog translations) {
    this.registry = DeferredRegister.createItems(mod);
    this.translations = translations;
  }

  public <T extends Item> ItemBuilder<T> builder(String path, String zhCn, Supplier<? extends T> factory) {
    return builder(path, zhCn, TranslationNames.toDisplayName(path), factory);
  }

  public <T extends Item> ItemBuilder<T> builder(
      String path, String zhCn, String enUs, Supplier<? extends T> factory
  ) {
    return builder(path, zhCn, enUs, factory, ModelTemplates.FLAT_ITEM, true);
  }

  public <T extends Item> ItemBuilder<T> builder(
      String path,
      String zhCn,
      Supplier<? extends T> factory,
      ModelTemplate model,
      boolean inCreativeTab
  ) {
    return builder(path, zhCn, TranslationNames.toDisplayName(path), factory, model, inCreativeTab);
  }

  public <T extends Item> ItemBuilder<T> builder(
      String path,
      String zhCn,
      String enUs,
      Supplier<? extends T> factory,
      ModelTemplate model,
      boolean inCreativeTab
  ) {
    ItemBuilder<T> builder = new ItemBuilder<T>(this, path, zhCn, enUs, factory, model, inCreativeTab);
    register(builder);
    return builder;
  }

  private <T extends Item> void register(ItemBuilder<T> builder) {
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("物品 ID 路径无效：" + builder.path);
    }
    if (builder.zhCn == null || builder.zhCn.isBlank()) {
      throw new IllegalArgumentException("物品中文名不能为空：" + builder.path);
    }
    if (builder.enUs == null || builder.enUs.isBlank()) {
      throw new IllegalArgumentException("物品英文名不能为空：" + builder.path);
    }
    Objects.requireNonNull(builder.factory, "物品 factory 不能为空：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("物品 ID 重复：" + builder.path);
    }
    builder.item = registry.register(builder.path, builder.factory);
    mutableEntries.add(builder);
    translations.add(builder.transKey(), builder.zhCn, builder.enUs);
  }
}
