package com.cxxcxx.zinecraft.api.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;

import java.util.function.Supplier;

public final class BlockEntityCatalog {
  private final ModRegistrar registrar;

  public BlockEntityCatalog(ModRegistrar registrar) {
    super();
    this.registrar = registrar;
  }
  public final <T extends BlockEntity> Supplier<BlockEntityType<T>> register(
      String path, BlockEntitySupplier<? extends T> factory, Supplier<? extends Block>... blocks
  ) {
    if (blocks.length == 0) {
      int i = 0;
      String string = "方块实体至少需要绑定一个方块";
      throw new IllegalArgumentException(string.toString());
    } else {
      return this.registrar.blockEntity(path, factory, blocks);
    }
  }
}
