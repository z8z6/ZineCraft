package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


object ModBlock {

  val EXAMPLE_ENTITY_BLOCK = register(
    "example_entity_block",
    ExampleEntityBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS))
  )

  private fun register(
    name: String,
    block: Block,
    shouldRegisterItem: Boolean = true
  )
      : Block {

    // 有些方块不应该有物品
    // minecraft:air 空气
    // minecraft:end_gateway 末地传送门
    if (shouldRegisterItem) {
      val blockItem = BlockItem(block, Item.Properties())
      ZinecraftCore.register(BuiltInRegistries.ITEM, name, blockItem)
    }

    return ZinecraftCore.register(BuiltInRegistries.BLOCK, name, block)
  }

}