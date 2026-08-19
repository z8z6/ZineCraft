package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.ItemCatalog;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 物品注册条目。物品的实际配置由 factory 完成，本类只保存注册和数据生成所需的元数据。
 */
public final class ItemBuilder<T extends Item> implements ItemLike {
  public final ItemCatalog catalog;
  public final String path;
  public final String zhCn;
  public final String enUs;
  public final Supplier<? extends T> factory;
  public final ModelTemplate model;
  public final boolean inCreativeTab;
  public DeferredItem<T> item;

  public ItemBuilder(
      ItemCatalog catalog,
      String path,
      String zhCn,
      String enUs,
      Supplier<? extends T> factory,
      ModelTemplate model,
      boolean inCreativeTab
  ) {
    this.catalog = Objects.requireNonNull(catalog, "物品目录不能为空");
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = enUs;
    this.factory = Objects.requireNonNull(factory, "物品 factory 不能为空：" + path);
    this.model = model;
    this.inCreativeTab = inCreativeTab;
  }

  public String transKey() {
    return "item." + catalog.registry.getNamespace() + "." + path;
  }

  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.registry.getNamespace(), path);
  }

  @Override
  public @NotNull Item asItem() {
    return item.get();
  }

  public DeferredItem<T> getItem() {
    return item;
  }
}
