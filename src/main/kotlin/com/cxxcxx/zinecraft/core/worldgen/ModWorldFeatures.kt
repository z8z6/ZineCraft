package com.cxxcxx.zinecraft.core.worldgen

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.block.MaterialOres
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.dimension.ModDimensions
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
  /** 基础材料既要支持主世界起步，也要在泰拉持续开采；其他维度不注入。 */
  private val MATERIAL_DIMENSIONS = BiomeSelectors.foundInOverworld()
    .or { context -> context.canGenerateIn(ModDimensions.TERRA.stemKey) }

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

  val ORIGINITE_ORE = Zinecraft.FEATURES.ore(
    path = "originite_ore",
    block = MaterialOres.ORIGINITE_ORE.block,
    veinSize = 3,
    veinsPerChunk = 2,
    maxY = -32,
    discardChanceOnAirExposure = 0.25f,
    biomes = MATERIAL_DIMENSIONS
  )

  val ORIROCK_ORE = Zinecraft.FEATURES.ore(
    path = "orirock_ore",
    block = MaterialOres.ORIROCK_ORE.block,
    veinSize = 10,
    veinsPerChunk = 12,
    maxY = 64,
    biomes = MATERIAL_DIMENSIONS
  )

  val ORIRON_ORE = Zinecraft.FEATURES.ore(
    path = "oriron_ore",
    block = MaterialOres.ORIRON_ORE.block,
    veinSize = 7,
    veinsPerChunk = 8,
    maxY = 32,
    biomes = MATERIAL_DIMENSIONS
  )

  val MANGANESE_ORE = Zinecraft.FEATURES.ore(
    path = "manganese_ore",
    block = MaterialOres.MANGANESE_ORE.block,
    veinSize = 5,
    veinsPerChunk = 6,
    maxY = 16,
    discardChanceOnAirExposure = 0.1f,
    biomes = MATERIAL_DIMENSIONS
  )

  val GRINDSTONE_ORE = Zinecraft.FEATURES.ore(
    path = "grindstone_ore",
    block = MaterialOres.GRINDSTONE_ORE.block,
    veinSize = 6,
    veinsPerChunk = 5,
    maxY = 0,
    discardChanceOnAirExposure = 0.15f,
    biomes = MATERIAL_DIMENSIONS
  )

  val RMA70_ORE = Zinecraft.FEATURES.ore(
    path = "rma70_ore",
    block = MaterialOres.RMA70_ORE.block,
    veinSize = 4,
    veinsPerChunk = 3,
    maxY = -32,
    discardChanceOnAirExposure = 0.25f,
    biomes = MATERIAL_DIMENSIONS
  )

  val CRYSTAL_ELEMENT_ORE = Zinecraft.FEATURES.ore(
    path = "crystal_element_ore",
    block = MaterialOres.CRYSTAL_ELEMENT_ORE.block,
    veinSize = 5,
    veinsPerChunk = 4,
    maxY = 16,
    discardChanceOnAirExposure = 0.15f,
    biomes = MATERIAL_DIMENSIONS
  )

  val LOXIC_KOHL_ORE = Zinecraft.FEATURES.ore(
    path = "loxic_kohl_ore",
    block = MaterialOres.LOXIC_KOHL_ORE.block,
    veinSize = 4,
    veinsPerChunk = 3,
    maxY = -16,
    discardChanceOnAirExposure = 0.2f,
    biomes = MATERIAL_DIMENSIONS
  )
}
