package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


object ModBlock {

  val EXAMPLE_ENTITY_BLOCK = ZinecraftCore.CONTENT.block(
    "example_entity_block",
    "示例实体方块",
    "Example Entity Block"
  ) {
    ExampleEntityBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS))
  }.block

}
