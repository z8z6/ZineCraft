package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock
import com.cxxcxx.zinecraft.core.dimension.StarGatePortalBlock
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


object ModBlock {

  val STARGATE_PORTAL = Zinecraft.BLOCKS.register(
    path = "stargate_portal",
    zhCn = "星门事件视界",
    enUs = "Stargate Event Horizon",
    dropSelf = false,
    cubeModel = false,
    registerItem = false
  ) {
    StarGatePortalBlock(
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .noCollission()
        .noOcclusion()
        .strength(-1.0f, 3_600_000.0f)
        .lightLevel { 12 }
        .sound(SoundType.GLASS)
    )
  }.block

  val EXAMPLE_ENTITY_BLOCK = Zinecraft.BLOCKS.register(
    "example_entity_block",
    "示例实体方块",
    "Example Entity Block"
  ) {
    ExampleEntityBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS))
  }.block

}
