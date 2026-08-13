package com.cxxcxx.zinecraft.api.world

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.google.common.collect.ImmutableList
import com.mojang.datafixers.util.Pair
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.HolderGetter
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.Pools
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.tags.BlockTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight
import net.minecraft.world.level.levelgen.placement.*
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSet
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure
import net.minecraft.world.level.levelgen.structure.templatesystem.*
import java.util.*
import java.util.function.Predicate

class WorldgenCatalog(val registrar: ModRegistrar) {
  private val biomes = mutableListOf<BiomeEntry>()
  private val ores = mutableListOf<OreEntry>()
  private val buildings = mutableListOf<SimpleBuildingEntry>()
  private val structureGenerators = mutableListOf<(BootstrapContext<Structure>) -> Unit>()
  private val structureSetGenerators = mutableListOf<(BootstrapContext<StructureSet>) -> Unit>()

  fun biome(
    path: String,
    build: SimpleBiomeBuilder.() -> Unit
  ): ResourceKey<Biome> {
    val key = registrar.key(Registries.BIOME, path)
    biomes += BiomeEntry(key, build)
    return key
  }

  fun ore(
    path: String,
    block: Block,
    veinSize: Int,
    veinsPerChunk: Int,
    maxY: Int = 0,
    discardChanceOnAirExposure: Float = 0f,
    biomes: Predicate<BiomeSelectionContext> = BiomeSelectors.foundInOverworld()
  ): OreEntry {
    val entry = OreEntry(
      registrar.key(Registries.CONFIGURED_FEATURE, "${path}_vein"),
      registrar.key(Registries.PLACED_FEATURE, path),
      block,
      veinSize,
      veinsPerChunk,
      maxY,
      discardChanceOnAirExposure,
      biomes
    )
    ores += entry
    return entry
  }

  fun simpleBuilding(
    path: String,
    template: String = path,
    spacing: Int = 32,
    separation: Int = 8,
    salt: Int,
    size: Int = 1,
    maxDistanceFromCenter: Int = 50,
    removeVinesChance: Float = 0f
  ): SimpleBuildingEntry {
    require(spacing > separation) { "spacing 必须大于 separation" }
    val entry = SimpleBuildingEntry(
      registrar.key(Registries.PROCESSOR_LIST, "${path}_processors"),
      registrar.key(Registries.TEMPLATE_POOL, path),
      registrar.key(Registries.STRUCTURE, path),
      registrar.key(Registries.STRUCTURE_SET, path),
      registrar.id(template).toString(),
      spacing,
      separation,
      salt,
      size,
      maxDistanceFromCenter,
      removeVinesChance
    )
    buildings += entry
    return entry
  }

  fun structures(generate: (BootstrapContext<Structure>) -> Unit) {
    structureGenerators += generate
  }

  fun structureSets(generate: (BootstrapContext<StructureSet>) -> Unit) {
    structureSetGenerators += generate
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

  fun addDataGeneration(builder: RegistrySetBuilder) {
    builder.add(Registries.CONFIGURED_FEATURE, ::bootstrapConfiguredFeatures)
    builder.add(Registries.PLACED_FEATURE, ::bootstrapPlacedFeatures)
    builder.add(Registries.BIOME, ::bootstrapBiomes)
    builder.add(Registries.PROCESSOR_LIST, ::bootstrapProcessors)
    builder.add(Registries.TEMPLATE_POOL, ::bootstrapTemplatePools)
    builder.add(Registries.STRUCTURE, ::bootstrapStructures)
    builder.add(Registries.STRUCTURE_SET, ::bootstrapStructureSets)
  }

  private fun bootstrapConfiguredFeatures(context: BootstrapContext<ConfiguredFeature<*, *>>) {
    ores.forEach { ore ->
      val targets = listOf(
        OreConfiguration.target(
          TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
          ore.block.defaultBlockState()
        ),
        OreConfiguration.target(
          TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
          ore.block.defaultBlockState()
        )
      )
      registrar.dynamic(
        context,
        ore.configuredKey,
        ConfiguredFeature(
          Feature.ORE,
          OreConfiguration(targets, ore.veinSize, ore.discardChanceOnAirExposure)
        )
      )
    }
  }

  private fun bootstrapPlacedFeatures(context: BootstrapContext<PlacedFeature>) {
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
              BiasedToBottomHeight.of(
                VerticalAnchor.BOTTOM,
                VerticalAnchor.absolute(ore.maxY),
                3
              )
            ),
            BiomeFilter.biome()
          )
        )
      )
    }
  }

  private fun bootstrapBiomes(context: BootstrapContext<Biome>) {
    val features: HolderGetter<PlacedFeature> = context.lookup(Registries.PLACED_FEATURE)
    val carvers: HolderGetter<ConfiguredWorldCarver<*>> = context.lookup(Registries.CONFIGURED_CARVER)
    biomes.forEach { entry ->
      registrar.dynamic(context, entry.key, SimpleBiomeBuilder(features, carvers).apply(entry.build).build())
    }
  }

  private fun bootstrapProcessors(context: BootstrapContext<StructureProcessorList>) {
    buildings.forEach { building ->
      val processors = mutableListOf<StructureProcessor>()
      if (building.removeVinesChance > 0f) {
        processors += RuleProcessor(
          listOf(
            ProcessorRule(
              RandomBlockMatchTest(Blocks.VINE, building.removeVinesChance),
              AlwaysTrueTest.INSTANCE,
              Blocks.AIR.defaultBlockState()
            )
          )
        )
      }
      registrar.dynamic(context, building.processorKey, StructureProcessorList(processors))
    }
  }

  private fun bootstrapTemplatePools(context: BootstrapContext<StructureTemplatePool>) {
    val emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY)
    val processors = context.lookup(Registries.PROCESSOR_LIST)
    buildings.forEach { building ->
      registrar.dynamic(
        context,
        building.poolKey,
        StructureTemplatePool(
          emptyPool,
          ImmutableList.of(
            Pair.of(
              StructurePoolElement.single(
                building.template,
                processors.getOrThrow(building.processorKey)
              ),
              1
            )
          ),
          StructureTemplatePool.Projection.RIGID
        )
      )
    }
  }

  private fun bootstrapStructures(context: BootstrapContext<Structure>) {
    val biomes = context.lookup(Registries.BIOME)
    val pools = context.lookup(Registries.TEMPLATE_POOL)
    buildings.forEach { building ->
      registrar.dynamic(
        context,
        building.structureKey,
        JigsawStructure(
          Structure.StructureSettings(
            biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
            emptyMap<MobCategory, StructureSpawnOverride>(),
            GenerationStep.Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.BEARD_THIN
          ),
          pools.getOrThrow(building.poolKey),
          Optional.empty(),
          building.size,
          ConstantHeight.of(VerticalAnchor.absolute(0)),
          false,
          Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
          building.maxDistanceFromCenter,
          emptyList<PoolAliasBinding>(),
          DimensionPadding.ZERO,
          LiquidSettings.IGNORE_WATERLOGGING
        )
      )
    }
    structureGenerators.forEach { it(context) }
  }

  private fun bootstrapStructureSets(context: BootstrapContext<StructureSet>) {
    val structures = context.lookup(Registries.STRUCTURE)
    buildings.forEach { building ->
      registrar.dynamic(
        context,
        building.setKey,
        StructureSet(
          structures.getOrThrow(building.structureKey),
          RandomSpreadStructurePlacement(
            building.spacing,
            building.separation,
            RandomSpreadType.LINEAR,
            building.salt
          )
        )
      )
    }
    structureSetGenerators.forEach { it(context) }
  }
}

private data class BiomeEntry(
  val key: ResourceKey<Biome>,
  val build: SimpleBiomeBuilder.() -> Unit
)

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

class SimpleBuildingEntry internal constructor(
  val processorKey: ResourceKey<StructureProcessorList>,
  val poolKey: ResourceKey<StructureTemplatePool>,
  val structureKey: ResourceKey<Structure>,
  val setKey: ResourceKey<StructureSet>,
  val template: String,
  val spacing: Int,
  val separation: Int,
  val salt: Int,
  val size: Int,
  val maxDistanceFromCenter: Int,
  val removeVinesChance: Float
)
