package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.ItemCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
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

  /**
   * 使用自动生成的英文名、默认平面模型并加入创造模式页来声明物品。
   *
   * @param catalog 接收物品的目录
   * @param path    物品的命名空间内路径
   * @param zhCn    物品的简体中文名称
   * @param factory 创建物品实例的工厂
   */
  public ItemBuilder(
      ItemCatalog catalog,
      String path,
      String zhCn,
      Supplier<? extends T> factory
  ) {
    this(catalog, path, zhCn, TranslationCatalog.toDisplayName(path), factory);
  }

  /**
   * 使用指定英文名、默认平面模型并加入创造模式页来声明物品。
   *
   * @param catalog 接收物品的目录
   * @param path 物品的命名空间内路径
   * @param zhCn 物品的简体中文名称
   * @param enUs 物品的英文名称
   * @param factory 创建物品实例的工厂
   */
  public ItemBuilder(
      ItemCatalog catalog,
      String path,
      String zhCn,
      String enUs,
      Supplier<? extends T> factory
  ) {
    this(catalog, path, zhCn, enUs, factory, ModelTemplates.FLAT_ITEM, true);
  }

  /**
   * 使用自动生成的英文名和指定模型、创造模式页选项来声明物品。
   *
   * @param catalog 接收物品的目录
   * @param path 物品的命名空间内路径
   * @param zhCn 物品的简体中文名称
   * @param factory 创建物品实例的工厂
   * @param model 数据生成使用的物品模型模板
   * @param inCreativeTab 是否自动加入目录的创造模式页
   */
  public ItemBuilder(
      ItemCatalog catalog,
      String path,
      String zhCn,
      Supplier<? extends T> factory,
      ModelTemplate model,
      boolean inCreativeTab
  ) {
    this(catalog, path, zhCn, TranslationCatalog.toDisplayName(path), factory, model, inCreativeTab);
  }

  /**
   * 使用完整元数据声明物品。
   *
   * @param catalog 接收物品的目录
   * @param path 物品的命名空间内路径
   * @param zhCn 物品的简体中文名称
   * @param enUs 物品的英文名称
   * @param factory 创建物品实例的工厂
   * @param model 数据生成使用的物品模型模板
   * @param inCreativeTab 是否自动加入目录的创造模式页
   */
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

  /** @return 物品名称的完整翻译键 */
  public String transKey() {
    return "item." + catalog.registry.getNamespace() + "." + path;
  }

  /** @return 物品的完整资源位置 */
  public ResourceLocation resourceKey() {
    return ResourceLocation.fromNamespaceAndPath(catalog.registry.getNamespace(), path);
  }

  /** @return 已登记物品实例 */
  @Override
  public @NotNull Item asItem() {
    return item.get();
  }

  /** @return NeoForge 延迟物品句柄；调用前应先完成 {@link #build()} */
  public DeferredItem<T> getItem() {
    return item;
  }

  /**
   * 校验并将物品登记到所属目录。
   *
   * @return 当前构建器
   */
  public ItemBuilder<T> build() {
    if (item != null) {
      throw new IllegalStateException("物品 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }
}
