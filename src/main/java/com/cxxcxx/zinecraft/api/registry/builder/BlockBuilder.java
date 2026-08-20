package com.cxxcxx.zinecraft.api.registry.builder;


import com.cxxcxx.zinecraft.api.registry.catalog.BlockCatalog;
import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 方块声明构建器，保存方块、方块物品、模型和掉落配置。
 */
public final class BlockBuilder<T extends Block> implements Supplier<T>, ItemLike {
  private final BlockCatalog catalog;
  public final Supplier<T> factory;
  public final String path;
  public final String zhCn;
  public String enUs;

  public boolean dropSelf = true;
  public ItemLike dropItem;
  public boolean cubeModel = true;
  public boolean registerItem = true;
  public Item.Properties itemProperties = new Item.Properties();
  public DeferredBlock<T> block;
  public Optional<DeferredItem<BlockItem>> blockItem = Optional.empty();

  /**
   * 创建使用默认立方体模型、自身掉落和方块物品的方块声明。
   *
   * @param catalog 接收方块的目录
   * @param path    方块的命名空间内路径
   * @param zhCn    方块的简体中文名称
   * @param factory 创建方块实例的工厂
   */
  public BlockBuilder(BlockCatalog catalog, String path, String zhCn, Supplier<? extends T> factory) {
    this.catalog = Objects.requireNonNull(catalog, "方块目录不能为空");
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = TranslationCatalog.toDisplayName(path);
    Supplier<? extends T> checkedFactory = Objects.requireNonNull(factory, "方块 factory 不能为空：" + path);
    this.factory = checkedFactory::get;
  }

  /**
   * 设置方块的英文名称。
   *
   * @param enUs 英文名称
   * @return 当前构建器
   */
  public BlockBuilder<T> enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  /** @return 当前构建器，并禁用该方块的自动战利品表 */
  public BlockBuilder<T> noLoot() {
    dropSelf = false;
    dropItem = null;
    return this;
  }

  /**
   * 将方块掉落物改为指定物品。
   *
   * @param item 方块被正常采集时掉落的物品
   * @return 当前构建器
   */
  public BlockBuilder<T> drop(ItemLike item) {
    dropSelf = false;
    dropItem = Objects.requireNonNull(item, "掉落物品不能为空");
    return this;
  }

  /** @return 当前构建器，并禁用默认立方体方块状态和模型生成 */
  public BlockBuilder<T> noCubeModel() {
    cubeModel = false;
    return this;
  }

  /** @return 当前构建器，并禁止为方块登记对应的方块物品 */
  public BlockBuilder<T> noBlockItem() {
    registerItem = false;
    return this;
  }

  /**
   * 设置自动创建的方块物品属性。
   *
   * @param itemProperties 方块物品属性
   * @return 当前构建器
   */
  public BlockBuilder<T> itemProperties(Item.Properties itemProperties) {
    this.itemProperties = Objects.requireNonNull(itemProperties, "方块物品属性不能为空：" + path);
    return this;
  }

  /**
   * 校验并将方块及可选方块物品登记到所属目录。
   *
   * @return 当前构建器
   */
  public BlockBuilder<T> build() {
    if (block != null) {
      throw new IllegalStateException("方块 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  /** @return NeoForge 延迟方块句柄 */
  public DeferredBlock<T> block() {
    return block;
  }

  /** @return 方块物品句柄；未登记方块物品时为空 */
  public Optional<DeferredItem<BlockItem>> blockItem() {
    return blockItem;
  }

  /** @return 已登记的方块实例 */
  @Override
  public T get() {
    return block.get();
  }

  /** @return 方块对应的物品实例 */
  @Override
  public Item asItem() {
    return block.asItem();
  }
}
