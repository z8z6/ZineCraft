package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.block.NationBlocks
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
      // 十九个国家群系使用专属地貌方块；少量草方块只作为植被生存斑块，不再承担国家视觉主题。
      onFloorIn(singleBlock(NationBlocks.AEGIR_ABYSSAL_SLATE), NationBiomes.AEGIR_ABYSSAL_SEA),
      onFloorIn(ecologicalSurface(NationBlocks.BOLIVAR_WAR_SCOURED_SOIL), NationBiomes.BOLIVAR_PLAIN),
      onFloorIn(ecologicalSurface(NationBlocks.HIGASHI_SHADOW_LOAM), NationBiomes.HIGASHI_SHADOW_RIFT),
      onFloorIn(singleBlock(NationBlocks.DURIN_GARDEN_MOSS), NationBiomes.DURIN_UNDERGROUND_GARDEN),
      onFloorIn(ecologicalSurface(NationBlocks.COLUMBIA_CANYON_SOIL), NationBiomes.COLUMBIA_SANDSTONE_WILDS),
      onFloorIn(singleBlock(NationBlocks.KAZIMIERZ_STEPPE_TURF), NationBiomes.KAZIMIERZ_KNIGHTLAND),
      onFloorIn(singleBlock(NationBlocks.KAZDEL_SCARRED_ASH), NationBiomes.KAZDEL_SCARRED_WASTES),
      onFloorIn(ecologicalSurface(NationBlocks.LATERANO_ALLUVIAL_CHALK), NationBiomes.LATERANO_HOLY_FIELDS),
      onFloorIn(ecologicalSurface(NationBlocks.LEITHANIEN_TWILIGHT_HUMUS), NationBiomes.LEITHANIEN_TWILIGHT_FOREST),
      onFloorIn(singleBlock(NationBlocks.RIM_BILLITON_MINE_TAILINGS), NationBiomes.RIM_BILLITON_MINING_BADLANDS),
      onFloorIn(ecologicalSurface(NationBlocks.MINOS_SUNBAKED_EARTH), NationBiomes.MINOS_SUNLIT_HILLS),
      onFloorIn(singleBlock(NationBlocks.SARGON_DESERT_CRUST), NationBiomes.SARGON_ROCKY_DESERT),
      onFloorIn(singleBlock(NationBlocks.SAMI_FROST_MOSS), NationBiomes.SAMI_FROZEN_FOREST),
      onFloorIn(ecologicalSurface(NationBlocks.VICTORIA_MOORLAND_SOIL), NationBiomes.VICTORIA_MISTY_HIGHLANDS),
      onFloorIn(singleBlock(NationBlocks.URSUS_PERMAFROST), NationBiomes.URSUS_FROZEN_STEPPE),
      onFloorIn(singleBlock(NationBlocks.KJERAG_SACRED_SNOWSTONE), NationBiomes.KJERAG_SNOWY_PEAKS),
      onFloorIn(ecologicalSurface(NationBlocks.SIRACUSA_RAIN_DARKENED_SOIL), NationBiomes.SIRACUSA_RAINY_WOODLAND),
      onFloorIn(ecologicalSurface(NationBlocks.YAN_MOUNTAIN_SOIL), NationBiomes.YAN_MOUNTAIN_GROVE),
      onFloorIn(singleBlock(NationBlocks.IBERIA_SALT_CRUSTED_GRAVEL), NationBiomes.IBERIA_SALT_DELTA)
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
