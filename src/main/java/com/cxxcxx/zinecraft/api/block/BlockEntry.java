package com.cxxcxx.zinecraft.api.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public final class BlockEntry<T extends Block> {
  @NotNull
  private final String path;
  @NotNull
  private final Supplier<? extends T> block;
  private final boolean dropSelf;
  @Nullable
  private final ItemLike dropItem;
  private final boolean cubeModel;
  private final boolean registerItem;

  public BlockEntry(@NotNull String path, @NotNull Supplier<? extends T> block, boolean dropSelf, @Nullable ItemLike dropItem, boolean cubeModel, boolean registerItem) {
    super();
    this.path = path;
    this.block = block;
    this.dropSelf = dropSelf;
    this.dropItem = dropItem;
    this.cubeModel = cubeModel;
    this.registerItem = registerItem;
  }

  @NotNull
  public final String getPath() {
    return this.path;
  }

  @NotNull
  public final T getBlock() {
    return this.block.get();
  }

  public final boolean getDropSelf() {
    return this.dropSelf;
  }

  @Nullable
  public final ItemLike getDropItem() {
    return this.dropItem;
  }

  public final boolean getCubeModel() {
    return this.cubeModel;
  }

  public final boolean getRegisterItem() {
    return this.registerItem;
  }
}
