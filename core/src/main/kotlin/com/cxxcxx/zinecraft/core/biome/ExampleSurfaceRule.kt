package com.cxxcxx.zinecraft.core.biome

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.SurfaceRules


object ExampleSurfaceRule {
  val DIRT: SurfaceRules.RuleSource = makeStateRule(Blocks.DIRT)
  val GRASS_BLOCK: SurfaceRules.RuleSource = makeStateRule(Blocks.GRASS_BLOCK)
  val RED_TERRACOTTA: SurfaceRules.RuleSource = makeStateRule(Blocks.RED_TERRACOTTA)
  val BLUE_TERRACOTTA: SurfaceRules.RuleSource = makeStateRule(Blocks.BLUE_TERRACOTTA)

  fun makeRules(): SurfaceRules.RuleSource {
    val isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0)
    val grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT)

    return SurfaceRules.sequence(
      SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiome.EXAMPLE_BIOME), RED_TERRACOTTA),
      SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
    )
  }

  private fun makeStateRule(block: Block): SurfaceRules.RuleSource {
    return SurfaceRules.state(block.defaultBlockState())
  }
}