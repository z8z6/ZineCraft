package com.cxxcxx.zinecraft.api.registry.builder;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.BlockCatalog;
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

  public BlockBuilder(BlockCatalog catalog, String path, String zhCn, Supplier<? extends T> factory) {
    this.catalog = Objects.requireNonNull(catalog, "方块目录不能为空");
    this.path = path;
    this.zhCn = zhCn;
    this.enUs = TranslationNames.toDisplayName(path);
    Supplier<? extends T> checkedFactory = Objects.requireNonNull(factory, "方块 factory 不能为空：" + path);
    this.factory = checkedFactory::get;
  }

  public BlockBuilder<T> enUs(String enUs) {
    this.enUs = enUs;
    return this;
  }

  public BlockBuilder<T> noLoot() {
    dropSelf = false;
    dropItem = null;
    return this;
  }

  public BlockBuilder<T> drop(ItemLike item) {
    dropSelf = false;
    dropItem = Objects.requireNonNull(item, "掉落物品不能为空");
    return this;
  }

  public BlockBuilder<T> noCubeModel() {
    cubeModel = false;
    return this;
  }

  public BlockBuilder<T> noBlockItem() {
    registerItem = false;
    return this;
  }

  public BlockBuilder<T> itemProperties(Item.Properties itemProperties) {
    this.itemProperties = Objects.requireNonNull(itemProperties, "方块物品属性不能为空：" + path);
    return this;
  }

  public BlockBuilder<T> build() {
    if (block != null) {
      throw new IllegalStateException("方块 builder 不能重复 build：" + path);
    }
    return catalog.register(this);
  }

  public DeferredBlock<T> block() {
    return block;
  }

  public Optional<DeferredItem<BlockItem>> blockItem() {
    return blockItem;
  }

  @Override
  public T get() {
    return block.get();
  }

  @Override
  public Item asItem() {
    return block.asItem();
  }
}
