package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


object ModBlock {
  fun register(name: String, block: Block, shouldRegisterItem: Boolean = true): Block {
    // Register the block and its item.
    val id = ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)

    // Sometimes, you may not want to register an item for the block.
    // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
    if (shouldRegisterItem) {
      val blockItem = BlockItem(block, Item.Properties())
      Registry.register(BuiltInRegistries.ITEM, id, blockItem)
    }

    return Registry.register(BuiltInRegistries.BLOCK, id, block)
  }

  val EXAMPLE_ENTITY_BLOCK = register(
    "example_entity_block",
    ExampleEntityBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS))
  )

  fun init() {
    EXAMPLE_ENTITY_BLOCK
  }
}