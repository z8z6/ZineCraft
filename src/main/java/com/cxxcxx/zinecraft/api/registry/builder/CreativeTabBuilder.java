package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.registry.catalog.CreativeTabCatalog;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 创造模式物品栏页声明构建器，用于配置标题、图标和展示内容。
 */
public final class CreativeTabBuilder {
  public final String path;
  public final String zhCn;
  private final CreativeTabCatalog catalog;
  public String enUs;
  public Supplier<ItemStack> icon;
  public boolean includeItems;
  public boolean includeBlocks;
  public Consumer<CreativeModeTab.Output> displayItems = output -> {
  };
  public ResourceKey<CreativeModeTab> key;
  public CreativeModeTab tab;

  /**
   * 创建创造模式页声明。
   *
   * @param catalog 接收该页面的创造模式页目录
   * @param path    页面的命名空间内路径
   * @param zhCn    页面的简体中文标题
   */
  public CreativeTabBuilder(CreativeTabCatalog catalog, String path, String zhCn) {
    this.catalog = catalog;
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = zhCn;
  }

  /**
   * 设置页面的英文标题。
   *
   * @param enUs 英文标题
   * @return 当前构建器
   */
  public CreativeTabBuilder enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  /**
   * 使用物品设置页面图标。
   *
   * @param icon 用作图标的物品
   * @return 当前构建器
   */
  public CreativeTabBuilder icon(ItemLike icon) {
    this.icon = () -> new ItemStack(icon);
    return this;
  }

  /**
   * 使用物品栈供应器设置页面图标。
   *
   * @param icon 延迟创建图标物品栈的供应器
   * @return 当前构建器
   */
  public CreativeTabBuilder icon(Supplier<ItemStack> icon) {
    this.icon = icon;
    return this;
  }

  /** @return 当前构建器，并自动展示物品目录中允许加入页面的物品 */
  public CreativeTabBuilder includeCatalogItems() {
    includeItems = true;
    return this;
  }

  /** @return 当前构建器，并自动展示方块目录中有方块物品的方块 */
  public CreativeTabBuilder includeCatalogBlocks() {
    includeBlocks = true;
    return this;
  }

  /**
   * 设置附加展示项回调。
   *
   * @param displayItems 向页面输出中添加物品的回调
   * @return 当前构建器
   */
  public CreativeTabBuilder displayItems(Consumer<CreativeModeTab.Output> displayItems) {
    this.displayItems = Objects.requireNonNull(displayItems, "displayItems");
    return this;
  }

  /**
   * 校验并将页面登记到所属目录。
   *
   * @return 当前构建器
   */
  public CreativeTabBuilder build() {
    if (tab != null) throw new IllegalStateException("创造模式页 builder 不能重复 build：" + path);
    catalog.register(this);
    return this;
  }
}
