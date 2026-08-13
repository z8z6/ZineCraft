package com.cxxcxx.zinecraft.core.entity


import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier


object ModBlockEntity {

  val EXAMPLE_BLOCK_ENTITY: BlockEntityType<ExampleBlockEntity> =
    register(
      "example_block_entity", ::ExampleBlockEntity,
      ModBlock.EXAMPLE_ENTITY_BLOCK
    )


  private fun <T : BlockEntity> register(
    name: String,
    entityFactory: BlockEntitySupplier<out T>,
    vararg blocks: Block
  ): BlockEntityType<T> {
    return ZinecraftCore.REGISTRAR.blockEntity(name, entityFactory, *blocks)
  }

}
