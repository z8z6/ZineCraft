package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.block.BlockEntityCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class ModBlockEntity {
  @NotNull
  public static final ModBlockEntity INSTANCE = new ModBlockEntity();
  @NotNull
  public static final Supplier<BlockEntityType<ExampleBlockEntity>> EXAMPLE_BLOCK_ENTITY;

  static {
    BlockEntityCatalog blockEntityCatalog = Zinecraft.BLOCK_ENTITIES;
    BlockEntitySupplier blockEntitySupplier = ExampleBlockEntity::new;
    EXAMPLE_BLOCK_ENTITY = blockEntityCatalog.register("example_block_entity", blockEntitySupplier, ModBlock.EXAMPLE_ENTITY_BLOCK::getBlock);
  }

  private ModBlockEntity() {
  }

}
