package com.cxxcxx.zinecraft.core.worldgen

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.block.ModBlock

object ModWorldFeatures {
  val EXAMPLE_BLOCK_ORE = Zinecraft.FEATURES.ore(
    path = "example_block_ore_placed",
    block = ModBlock.EXAMPLE_ENTITY_BLOCK,
    veinSize = 30,
    veinsPerChunk = 6,
    maxY = 0
  )
}
