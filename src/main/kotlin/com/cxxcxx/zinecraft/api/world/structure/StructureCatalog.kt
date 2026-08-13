package com.cxxcxx.zinecraft.api.world.structure

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.mojang.datafixers.util.Pair
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.Pools
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSet
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure
import net.minecraft.world.level.levelgen.structure.templatesystem.*
import java.util.*

class StructureCatalog(private val registrar: ModRegistrar) {
  private val buildings = mutableListOf<JigsawBuildingEntry>()
  private val structureGenerators = mutableListOf<(BootstrapContext<Structure>) -> Unit>()
  private val structureSetGenerators = mutableListOf<(BootstrapContext<StructureSet>) -> Unit>()

  fun simpleBuilding(
    path: String,
    template: String = path,
    spacing: Int = 32,
    separation: Int = 8,
    salt: Int,
    maxDistanceFromCenter: Int = 50,
    removeVinesChance: Float = 0f
  ): JigsawBuildingEntry = jigsawBuilding(
    path, spacing, separation, salt, 1, maxDistanceFromCenter, removeVinesChance
  ) {
    pool("start") { template(template) }
  }

  /**
   * 注册一个只在指定群系出现、每个世界仅有一个候选位置的地标。
   *
   * 唯一性由原版同心环结构放置器的 `count = 1` 保证；因此地标仍参与正常区块生成、定位和存档。
   */
  fun uniqueLandmark(
    path: String,
    template: String = path,
    biome: ResourceKey<Biome>,
    ringDistance: Int = 32,
    maxDistanceFromCenter: Int = 80,
    heightmap: Heightmap.Types? = Heightmap.Types.WORLD_SURFACE_WG,
    startHeight: Int = 0,
    removeVinesChance: Float = 0f
  ): JigsawBuildingEntry {
    require(ringDistance > 0) { "唯一地标与世界原点的环距离必须大于 0" }
    return jigsawBuilding(
      path = path,
      spacing = ringDistance + 1,
      separation = ringDistance,
      salt = path.hashCode(),
      size = 1,
      maxDistanceFromCenter = maxDistanceFromCenter,
      removeVinesChance = removeVinesChance,
      biome = biome,
      unique = true,
      ringDistance = ringDistance,
      heightmap = heightmap,
      startHeight = startHeight
    ) {
      pool("start") { template(template) }
    }
  }

  fun jigsawBuilding(
    path: String,
    spacing: Int = 32,
    separation: Int = 8,
    salt: Int,
    size: Int = 3,
    maxDistanceFromCenter: Int = 80,
    removeVinesChance: Float = 0f,
    biome: ResourceKey<Biome>? = null,
    unique: Boolean = false,
    ringDistance: Int = 32,
    heightmap: Heightmap.Types? = Heightmap.Types.WORLD_SURFACE_WG,
    startHeight: Int = 0,
    build: JigsawBuildingBuilder.() -> Unit
  ): JigsawBuildingEntry {
    require(spacing > separation) { "spacing 必须大于 separation" }
    require(size in 0..20) { "Jigsaw 展开深度必须在 0 到 20 之间" }
    require(maxDistanceFromCenter in 1..128) { "结构中心最大距离必须在 1 到 128 之间" }
    require(removeVinesChance in 0f..1f) { "藤蔓移除概率必须在 0 到 1 之间" }
    val definition = JigsawBuildingBuilder(path).apply(build).build()
    val poolKeys = definition.pools.associate { pool ->
      pool.name to registrar.key(Registries.TEMPLATE_POOL, "$path/${pool.name}")
    }
    return JigsawBuildingEntry(
      registrar.key(Registries.PROCESSOR_LIST, "${path}_processors"),
      poolKeys,
      definition.startPool,
      registrar.key(Registries.STRUCTURE, path),
      registrar.key(Registries.STRUCTURE_SET, path),
      definition.pools,
      spacing,
      separation,
      salt,
      size,
      maxDistanceFromCenter,
      removeVinesChance,
      biome,
      unique,
      ringDistance,
      heightmap,
      startHeight
    ).also(buildings::add)
  }

  fun structures(generate: (BootstrapContext<Structure>) -> Unit) {
    structureGenerators += generate
  }

  fun structureSets(generate: (BootstrapContext<StructureSet>) -> Unit) {
    structureSetGenerators += generate
  }

  internal fun bootstrapProcessors(context: BootstrapContext<StructureProcessorList>) {
    buildings.forEach { building ->
      val processors = if (building.removeVinesChance > 0f) {
        listOf(
          RuleProcessor(
            listOf(
              ProcessorRule(
                RandomBlockMatchTest(Blocks.VINE, building.removeVinesChance),
                AlwaysTrueTest.INSTANCE,
                Blocks.AIR.defaultBlockState()
              )
            )
          )
        )
      } else {
        emptyList()
      }
      registrar.dynamic(context, building.processorKey, StructureProcessorList(processors))
    }
  }

  internal fun bootstrapPools(context: BootstrapContext<StructureTemplatePool>) {
    val emptyPool = context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY)
    val processors = context.lookup(Registries.PROCESSOR_LIST)
    buildings.forEach { building ->
      building.pools.forEach { pool ->
        val elements = pool.templates.map { template ->
          Pair.of<java.util.function.Function<StructureTemplatePool.Projection, out StructurePoolElement>, Int>(
            StructurePoolElement.single(
              registrar.id(template.template).toString(),
              processors.getOrThrow(building.processorKey)
            ),
            template.weight
          )
        }
        registrar.dynamic(
          context,
          building.poolKeys.getValue(pool.name),
          StructureTemplatePool(emptyPool, elements, StructureTemplatePool.Projection.RIGID)
        )
      }
    }
  }

  internal fun bootstrapStructures(context: BootstrapContext<Structure>) {
    val biomes = context.lookup(Registries.BIOME)
    val pools = context.lookup(Registries.TEMPLATE_POOL)
    buildings.forEach { building ->
      val validBiomes = building.biome?.let { HolderSet.direct(biomes.getOrThrow(it)) }
        ?: biomes.getOrThrow(BiomeTags.IS_OVERWORLD)
      registrar.dynamic(
        context,
        building.structureKey,
        JigsawStructure(
          Structure.StructureSettings(
            validBiomes,
            emptyMap<MobCategory, StructureSpawnOverride>(),
            GenerationStep.Decoration.SURFACE_STRUCTURES,
            TerrainAdjustment.BEARD_THIN
          ),
          pools.getOrThrow(building.poolKeys.getValue(building.startPool)),
          Optional.empty(),
          building.size,
          ConstantHeight.of(VerticalAnchor.absolute(building.startHeight)),
          false,
          Optional.ofNullable(building.heightmap),
          building.maxDistanceFromCenter,
          emptyList<PoolAliasBinding>(),
          DimensionPadding.ZERO,
          LiquidSettings.IGNORE_WATERLOGGING
        )
      )
    }
    structureGenerators.forEach { it(context) }
  }

  internal fun bootstrapSets(context: BootstrapContext<StructureSet>) {
    val structures = context.lookup(Registries.STRUCTURE)
    val biomes = context.lookup(Registries.BIOME)
    buildings.forEach { building ->
      val placement = if (building.unique) {
        val biome = requireNotNull(building.biome) { "唯一地标必须绑定群系: ${building.structureKey.location()}" }
        ConcentricRingsStructurePlacement(
          building.ringDistance,
          1,
          1,
          HolderSet.direct(biomes.getOrThrow(biome))
        )
      } else {
        RandomSpreadStructurePlacement(
          building.spacing,
          building.separation,
          RandomSpreadType.LINEAR,
          building.salt
        )
      }
      registrar.dynamic(
        context,
        building.setKey,
        StructureSet(
          structures.getOrThrow(building.structureKey),
          placement
        )
      )
    }
    structureSetGenerators.forEach { it(context) }
  }
}

class JigsawBuildingEntry internal constructor(
  val processorKey: ResourceKey<StructureProcessorList>,
  val poolKeys: Map<String, ResourceKey<StructureTemplatePool>>,
  val startPool: String,
  val structureKey: ResourceKey<Structure>,
  val setKey: ResourceKey<StructureSet>,
  internal val pools: List<JigsawPoolDefinition>,
  internal val spacing: Int,
  internal val separation: Int,
  internal val salt: Int,
  internal val size: Int,
  internal val maxDistanceFromCenter: Int,
  internal val removeVinesChance: Float,
  internal val biome: ResourceKey<Biome>?,
  internal val unique: Boolean,
  internal val ringDistance: Int,
  internal val heightmap: Heightmap.Types?,
  internal val startHeight: Int
)

class JigsawBuildingBuilder internal constructor(private val path: String) {
  var startPool: String = "start"
  private val pools = mutableListOf<JigsawPoolDefinition>()

  fun pool(name: String, build: JigsawPoolBuilder.() -> Unit) {
    require(name.isNotBlank()) { "Jigsaw pool 名称不能为空" }
    require(pools.none { it.name == name }) { "Jigsaw pool 重复: $path/$name" }
    pools += JigsawPoolBuilder(name).apply(build).build()
  }

  internal fun build(): JigsawBuildingDefinition {
    require(pools.isNotEmpty()) { "Jigsaw 建筑至少需要一个模板池: $path" }
    require(pools.any { it.name == startPool }) { "找不到起始模板池: $path/$startPool" }
    return JigsawBuildingDefinition(startPool, pools.toList())
  }
}

class JigsawPoolBuilder internal constructor(private val name: String) {
  private val templates = mutableListOf<JigsawTemplateElement>()

  fun template(path: String, weight: Int = 1) {
    require(path.isNotBlank()) { "Jigsaw 模板路径不能为空" }
    require(weight > 0) { "Jigsaw 模板权重必须大于 0" }
    templates += JigsawTemplateElement(path, weight)
  }

  internal fun build(): JigsawPoolDefinition {
    require(templates.isNotEmpty()) { "Jigsaw 模板池不能为空: $name" }
    return JigsawPoolDefinition(name, templates.toList())
  }
}

internal data class JigsawBuildingDefinition(
  val startPool: String,
  val pools: List<JigsawPoolDefinition>
)

data class JigsawPoolDefinition(val name: String, val templates: List<JigsawTemplateElement>)

data class JigsawTemplateElement(val template: String, val weight: Int)
