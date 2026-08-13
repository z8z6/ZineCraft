package com.cxxcxx.zinecraft.core.biome

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Noises
import net.minecraft.world.level.levelgen.SurfaceRules


object ModSurfaceRule {
  private fun singleBlock(block: Block): SurfaceRules.RuleSource {
    return SurfaceRules.state(block.defaultBlockState())
  }

  fun rules(): SurfaceRules.RuleSource {
    return SurfaceRules.sequence(
      // 十九个国家群系各使用一种不重复的表层方块，便于在世界中直接辨认。
      onFloorIn(singleBlock(Blocks.DARK_PRISMARINE), NationBiomes.AEGIR_ABYSSAL_SEA),
      onFloorIn(ecologicalSurface(Blocks.COARSE_DIRT), NationBiomes.BOLIVAR_PLAIN),
      onFloorIn(ecologicalSurface(Blocks.PODZOL), NationBiomes.HIGASHI_SHADOW_RIFT),
      onFloorIn(singleBlock(Blocks.MOSS_BLOCK), NationBiomes.DURIN_UNDERGROUND_GARDEN),
      onFloorIn(ecologicalSurface(Blocks.RED_SAND), NationBiomes.COLUMBIA_SANDSTONE_WILDS),
      onFloorIn(singleBlock(Blocks.GRASS_BLOCK), NationBiomes.KAZIMIERZ_KNIGHTLAND),
      onFloorIn(singleBlock(Blocks.BLACKSTONE), NationBiomes.KAZDEL_SCARRED_WASTES),
      onFloorIn(ecologicalSurface(Blocks.CALCITE), NationBiomes.LATERANO_HOLY_FIELDS),
      onFloorIn(ecologicalSurface(Blocks.ROOTED_DIRT), NationBiomes.LEITHANIEN_TWILIGHT_FOREST),
      onFloorIn(singleBlock(Blocks.TERRACOTTA), NationBiomes.RIM_BILLITON_MINING_BADLANDS),
      onFloorIn(ecologicalSurface(Blocks.PACKED_MUD), NationBiomes.MINOS_SUNLIT_HILLS),
      onFloorIn(singleBlock(Blocks.SAND), NationBiomes.SARGON_ROCKY_DESERT),
      onFloorIn(singleBlock(Blocks.SNOW_BLOCK), NationBiomes.SAMI_FROZEN_FOREST),
      onFloorIn(ecologicalSurface(Blocks.MUD), NationBiomes.VICTORIA_MISTY_HIGHLANDS),
      onFloorIn(singleBlock(Blocks.PACKED_ICE), NationBiomes.URSUS_FROZEN_STEPPE),
      onFloorIn(mixedSurface(Blocks.BLUE_ICE, Blocks.SNOW_BLOCK), NationBiomes.KJERAG_SNOWY_PEAKS),
      onFloorIn(ecologicalSurface(Blocks.MOSSY_COBBLESTONE), NationBiomes.SIRACUSA_RAINY_WOODLAND),
      onFloorIn(ecologicalSurface(Blocks.TUFF), NationBiomes.YAN_MOUNTAIN_GROVE),
      onFloorIn(singleBlock(Blocks.GRAVEL), NationBiomes.IBERIA_SALT_DELTA)
    )
  }

  /** 用原版表面噪声留下少量草方块斑块，确保树木和被动生物拥有合法生成地面。 */
  private fun ecologicalSurface(primary: Block): SurfaceRules.RuleSource =
    mixedSurface(primary, Blocks.GRASS_BLOCK)

  /** 在主表层中混入少量生态斑块；可用于满足特定生物的生成地面条件。 */
  private fun mixedSurface(primary: Block, patch: Block): SurfaceRules.RuleSource = SurfaceRules.sequence(
    SurfaceRules.ifTrue(
      SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, -0.12, 0.12),
      singleBlock(patch)
    ),
    singleBlock(primary)
  )

  private fun onFloorIn(
    surface: SurfaceRules.RuleSource,
    vararg biomes: net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>
  ): SurfaceRules.RuleSource = SurfaceRules.ifTrue(
    SurfaceRules.isBiome(*biomes),
    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)
  )
}
