package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.block.BlockEntityCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import org.jetbrains.annotations.NotNull;

public final class ModBlockEntity {
  @NotNull
  public static final ModBlockEntity INSTANCE = new ModBlockEntity();
  @NotNull
  private static final BlockEntityType<ExampleBlockEntity> EXAMPLE_BLOCK_ENTITY;

  static {
    BlockEntityCatalog blockEntityCatalog = Zinecraft.INSTANCE.getBLOCK_ENTITIES();
    BlockEntitySupplier blockEntitySupplier = ExampleBlockEntity::new;
    Block[] blocks = new Block[]{ModBlock.INSTANCE.getEXAMPLE_ENTITY_BLOCK()};
    EXAMPLE_BLOCK_ENTITY = blockEntityCatalog.register("example_block_entity", blockEntitySupplier, blocks);
  }

  private ModBlockEntity() {
  }

  @NotNull
  public final BlockEntityType<ExampleBlockEntity> getEXAMPLE_BLOCK_ENTITY() {
    return EXAMPLE_BLOCK_ENTITY;
  }
}
