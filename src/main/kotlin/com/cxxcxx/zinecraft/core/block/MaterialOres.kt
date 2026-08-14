package com.cxxcxx.zinecraft.core.block

import com.cxxcxx.zinecraft.api.block.BlockEntry
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.item.ModItem
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour

/**
 * 明日方舟加工链的自然矿物入口。
 *
 * 这里只注册在资料描述中能够合理作为矿藏开采的原料；装置、糖、酯原料等工业或有机物不会被伪装成矿石。
 */
object MaterialOres {
  val ORIGINITE_ORE = ore("originite_ore", "源石矿", "Originite Ore", ModItem.ORIGINITE)
  val ORIROCK_ORE = ore("orirock_ore", "源岩矿", "Orirock Ore", ModItem.ORIROCK)
  val ORIRON_ORE = ore("oriron_ore", "异铁矿", "Oriron Ore", ModItem.ORIRON_SHARD)
  val MANGANESE_ORE = ore("manganese_ore_block", "轻锰矿脉", "Manganese Vein", ModItem.MANGANESE_ORE)
  val GRINDSTONE_ORE = ore("grindstone_ore", "研磨石矿", "Grindstone Ore", ModItem.GRINDSTONE)
  val RMA70_ORE = ore("rma70_ore", "RMA70 矿", "RMA70 Ore", ModItem.RMA70_12)
  val CRYSTAL_ELEMENT_ORE = ore(
    "crystal_element_ore",
    "晶体元件矿",
    "Crystalline Component Ore",
    ModItem.CRYSTAL_ELEMENT
  )
  val LOXIC_KOHL_ORE = ore("loxic_kohl_ore", "炽合金矿", "Incandescent Alloy Ore", ModItem.LOXIC_KOHL)

  val ALL: List<BlockEntry<Block>> = listOf(
    ORIGINITE_ORE,
    ORIROCK_ORE,
    ORIRON_ORE,
    MANGANESE_ORE,
    GRINDSTONE_ORE,
    RMA70_ORE,
    CRYSTAL_ELEMENT_ORE,
    LOXIC_KOHL_ORE
  )

  private fun ore(
    path: String,
    zhCn: String,
    enUs: String,
    drop: ItemLike
  ): BlockEntry<Block> = Zinecraft.BLOCKS.register(
    path = path,
    zhCn = zhCn,
    enUs = enUs,
    dropSelf = false,
    dropItem = drop
  ) {
    Block(
      BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
        .requiresCorrectToolForDrops()
        .strength(4.0f, 6.0f)
        .sound(SoundType.DEEPSLATE)
    )
  }
}
