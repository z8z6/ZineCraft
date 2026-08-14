package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.dimension.StarGateControllerBlock
import com.cxxcxx.zinecraft.core.dimension.StarGatePortalBlock
import com.cxxcxx.zinecraft.core.entity.ExampleEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor


object ModBlock {

  val STARGATE_ARCH = Zinecraft.BLOCKS.register(
    "stargate_arch",
    "星门拱石",
    "Stargate Archstone"
  ) {
    Block(
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_BLACK)
        .requiresCorrectToolForDrops()
        .strength(18.0f, 1_200.0f)
        .sound(SoundType.DEEPSLATE)
    )
  }.block

  val STARGATE_CONTROLLER = Zinecraft.BLOCKS.register(
    "stargate_controller",
    "星门协议控制器",
    "Stargate Protocol Controller"
  ) {
    StarGateControllerBlock(
      BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_CYAN)
        .requiresCorrectToolForDrops()
        .strength(18.0f, 1_200.0f)
        .lightLevel { state -> if (state.getValue(StarGateControllerBlock.ACTIVE)) 12 else 3 }
        .sound(SoundType.METAL)
    )
  }.block

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

  init {
    Zinecraft.TRANSLATIONS.add(
      StarGateControllerBlock.REQUIRES_KEY_MESSAGE,
      "控制器拒绝访问：请手持协议源石右键激活",
      "Access denied: right-click while holding Protocol Originium"
    )
    Zinecraft.TRANSLATIONS.add(
      StarGateControllerBlock.ACTIVATED_MESSAGE,
      "协议认证完成，星门事件视界已建立",
      "Protocol accepted. Stargate event horizon established"
    )
    Zinecraft.TRANSLATIONS.add(
      StarGateControllerBlock.ALREADY_ACTIVE_MESSAGE,
      "星门已处于激活状态",
      "The stargate is already active"
    )
    Zinecraft.TRANSLATIONS.add(
      StarGateControllerBlock.DAMAGED_MESSAGE,
      "星门拱体不完整，无法建立事件视界",
      "The stargate arch is incomplete and cannot form an event horizon"
    )
  }

}
