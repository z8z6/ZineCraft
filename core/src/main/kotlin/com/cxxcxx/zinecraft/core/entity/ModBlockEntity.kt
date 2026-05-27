package com.cxxcxx.zinecraft.core.entity


import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
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
    val id = ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    return Registry.register(
      BuiltInRegistries.BLOCK_ENTITY_TYPE,
      id,
      BlockEntityType.Builder.of<T>(entityFactory, *blocks).build()
    )
  }

  fun init() {
    EXAMPLE_BLOCK_ENTITY
  }
}