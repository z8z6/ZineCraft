package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public final class ModBlockEntity {
  private ModBlockEntity() {
  }  public static final Supplier<BlockEntityType<ExampleBlockEntity>> EXAMPLE_BLOCK_ENTITY = Zinecraft.BLOCK_ENTITIES.register(
      "example_block_entity", ExampleBlockEntity::new, ModBlock.EXAMPLE_ENTITY_BLOCK
  );

  public static void bootstrap() {
  }


}
