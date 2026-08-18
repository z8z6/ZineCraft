package com.cxxcxx.zinecraft.api.block;

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
 * A block registration together with the items associated with that declaration.
 */
public final class BlockEntry<T extends Block> implements Supplier<T>, ItemLike {
  private final DeferredBlock<T> block;
  private final Optional<DeferredItem<BlockItem>> blockItem;
  private final boolean dropSelf;
  private final ItemLike configuredDrop;

  BlockEntry(DeferredBlock<T> block, Optional<DeferredItem<BlockItem>> blockItem,
             boolean dropSelf, ItemLike configuredDrop) {
    this.block = Objects.requireNonNull(block, "block");
    this.blockItem = Objects.requireNonNull(blockItem, "blockItem");
    this.dropSelf = dropSelf;
    this.configuredDrop = configuredDrop;
  }

  public DeferredBlock<T> block() {
    return block;
  }

  public Optional<DeferredItem<BlockItem>> blockItem() {
    return blockItem;
  }

  /**
   * Returns the declared loot item, or empty when this block has no loot.
   */
  public Optional<ItemLike> dropItem() {
    if (dropSelf) return Optional.of(block);
    return Optional.ofNullable(configuredDrop);
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
