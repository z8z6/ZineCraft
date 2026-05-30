package com.cxxcxx.zinecraft.core.biome

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules


object ModSurfaceRule {
  val DIRT: SurfaceRules.RuleSource = singleBlock(Blocks.DIRT)
  val GRASS_BLOCK: SurfaceRules.RuleSource = singleBlock(Blocks.GRASS_BLOCK)
  val RED_TERRACOTTA: SurfaceRules.RuleSource = singleBlock(Blocks.RED_TERRACOTTA)

  private fun singleBlock(block: Block): SurfaceRules.RuleSource {
    return SurfaceRules.state(block.defaultBlockState())
  }

  fun Rules(): SurfaceRules.RuleSource {
    val isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0)
    val grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT)

    return SurfaceRules.sequence(
      SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiome.EXAMPLE_BIOME), RED_TERRACOTTA),
      SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
    )
  }
}