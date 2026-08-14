package com.cxxcxx.zinecraft.core.worldgen

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.dimension.StarGateFeature
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.biome.Biomes
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.placement.BiomeFilter
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement
import net.minecraft.world.level.levelgen.placement.InSquarePlacement
import net.minecraft.world.level.levelgen.placement.RarityFilter

object ModWorldFeatures {
  private val STARGATE_FEATURE = Zinecraft.REGISTRAR.register(
    BuiltInRegistries.FEATURE,
    "stargate",
    StarGateFeature()
  )

  /** 选择器只把地物加入雪原；Feature.place 还会硬校验主世界，防止其他维度复用雪原时生成。 */
  val STARGATE = Zinecraft.FEATURES.simple(
    path = "stargate",
    feature = STARGATE_FEATURE,
    placement = listOf(
      RarityFilter.onAverageOnceEvery(64),
      InSquarePlacement.spread(),
      HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
      BiomeFilter.biome()
    ),
    generationStep = GenerationStep.Decoration.SURFACE_STRUCTURES,
    biomes = BiomeSelectors.includeByKey(Biomes.SNOWY_PLAINS)
      .and { context -> context.canGenerateIn(LevelStem.OVERWORLD) }
  )

  val EXAMPLE_BLOCK_ORE = Zinecraft.FEATURES.ore(
    path = "example_block_ore_placed",
    block = ModBlock.EXAMPLE_ENTITY_BLOCK,
    veinSize = 30,
    veinsPerChunk = 6,
    maxY = 0
  )
}
