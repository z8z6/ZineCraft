package com.cxxcxx.zinecraft.core.entity


import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.world.level.block.entity.BlockEntityType


object ModBlockEntity {

  val EXAMPLE_BLOCK_ENTITY: BlockEntityType<ExampleBlockEntity> =
    Zinecraft.BLOCK_ENTITIES.register(
      "example_block_entity", ::ExampleBlockEntity,
      ModBlock.EXAMPLE_ENTITY_BLOCK
    )
}
