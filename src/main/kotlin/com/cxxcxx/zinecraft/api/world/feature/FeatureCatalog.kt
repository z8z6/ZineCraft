package com.cxxcxx.zinecraft.api.world.feature

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest
import java.util.function.Predicate

class FeatureCatalog(private val registrar: ModRegistrar) {
  private val ores = mutableListOf<OreEntry>()

  fun ore(
    path: String,
    block: Block,
    veinSize: Int,
    veinsPerChunk: Int,
    maxY: Int = 0,
    discardChanceOnAirExposure: Float = 0f,
    biomes: Predicate<BiomeSelectionContext> = BiomeSelectors.foundInOverworld()
  ): OreEntry {
    require(veinSize > 0) { "矿脉大小必须大于 0" }
    require(veinsPerChunk > 0) { "每区块矿脉数量必须大于 0" }
    require(discardChanceOnAirExposure in 0f..1f) { "暴露丢弃概率必须在 0 到 1 之间" }
    return OreEntry(
      registrar.key(Registries.CONFIGURED_FEATURE, "${path}_vein"),
      registrar.key(Registries.PLACED_FEATURE, path),
      block,
      veinSize,
      veinsPerChunk,
      maxY,
      discardChanceOnAirExposure,
      biomes
    ).also(ores::add)
  }

  fun initialize() {
    ores.forEach { ore ->
      BiomeModifications.addFeature(
        ore.biomes,
        GenerationStep.Decoration.UNDERGROUND_ORES,
        ore.placedKey
      )
    }
  }

  internal fun bootstrapConfigured(context: BootstrapContext<ConfiguredFeature<*, *>>) {
    ores.forEach { ore ->
      val targets = listOf(
        OreConfiguration.target(TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), ore.block.defaultBlockState()),
        OreConfiguration.target(TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), ore.block.defaultBlockState())
      )
      registrar.dynamic(
        context,
        ore.configuredKey,
        ConfiguredFeature(Feature.ORE, OreConfiguration(targets, ore.veinSize, ore.discardChanceOnAirExposure))
      )
    }
  }

  internal fun bootstrapPlaced(context: BootstrapContext<PlacedFeature>) {
    val configured = context.lookup(Registries.CONFIGURED_FEATURE)
    ores.forEach { ore ->
      registrar.dynamic(
        context,
        ore.placedKey,
        PlacedFeature(
          configured.getOrThrow(ore.configuredKey),
          listOf(
            CountPlacement.of(ore.veinsPerChunk),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(
              BiasedToBottomHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(ore.maxY), 3)
            ),
            BiomeFilter.biome()
          )
        )
      )
    }
  }
}

class OreEntry internal constructor(
  val configuredKey: ResourceKey<ConfiguredFeature<*, *>>,
  val placedKey: ResourceKey<PlacedFeature>,
  val block: Block,
  val veinSize: Int,
  val veinsPerChunk: Int,
  val maxY: Int,
  val discardChanceOnAirExposure: Float,
  val biomes: Predicate<BiomeSelectionContext>
)
