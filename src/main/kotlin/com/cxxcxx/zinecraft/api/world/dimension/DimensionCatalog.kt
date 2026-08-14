package com.cxxcxx.zinecraft.api.world.dimension

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.mojang.datafixers.util.Pair
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import net.minecraft.world.level.biome.MultiNoiseBiomeSource
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.dimension.LevelStem
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings

/**
 * 声明数据驱动维度，生成 dimension_type 并为维度数据包提供稳定资源键和 bootstrap 校验。
 *
 * 1.21.1 的 dimension 注册表由世界创建数据包层加载，发布时仍需携带对应的 `data/<mod>/dimension/<id>.json`。
 * 维度只保存资源键和构造参数，真正的 Holder 在 bootstrap 阶段解析，避免普通初始化期间访问尚未冻结的动态注册表。
 */
class DimensionCatalog(private val registrar: ModRegistrar) {
  private val entries = mutableListOf<DimensionEntry>()

  fun register(
    path: String,
    biomes: List<DimensionBiome>,
    createGenerator: ((DimensionBootstrapContext) -> ChunkGenerator)? = null
  ): DimensionEntry {
    require(path.isNotBlank()) { "维度 ID 不能为空" }
    require(entries.none { it.path == path }) { "维度 ID 重复: $path" }
    require(biomes.isNotEmpty()) { "维度至少需要一个群系: $path" }
    require(biomes.map { it.biome }.distinct().size == biomes.size) { "维度群系资源键重复: $path" }
    require(biomes.map { it.parameters }.distinct().size == biomes.size) { "维度气候点重复: $path" }

    return DimensionEntry(
      path = path,
      levelKey = ResourceKey.create(Registries.DIMENSION, registrar.id(path)),
      stemKey = registrar.key(Registries.LEVEL_STEM, path),
      typeKey = registrar.key(Registries.DIMENSION_TYPE, path),
      noiseSettingsKey = NoiseGeneratorSettings.OVERWORLD,
      biomes = biomes.toList(),
      createGenerator = createGenerator
    ).also(entries::add)
  }

  internal fun bootstrapDimensionTypes(context: BootstrapContext<DimensionType>) {
    entries.forEach { entry -> registrar.dynamic(context, entry.typeKey, DimensionHelper.overworldLikeType()) }
  }

  internal fun bootstrapLevelStems(context: BootstrapContext<LevelStem>) {
    val dimensions = context.lookup(Registries.DIMENSION_TYPE)
    val noiseSettings = context.lookup(Registries.NOISE_SETTINGS)
    val biomes = context.lookup(Registries.BIOME)

    entries.forEach { entry ->
      val parameters: Climate.ParameterList<net.minecraft.core.Holder<Biome>> = Climate.ParameterList(
        entry.biomes.map { biome ->
          Pair.of<Climate.ParameterPoint, net.minecraft.core.Holder<Biome>>(
            biome.parameters,
            biomes.getOrThrow(biome.biome)
          )
        }
      )
      val bootstrap = DimensionBootstrapContext(
        entry,
        MultiNoiseBiomeSource.createFromList(parameters),
        parameters,
        biomes,
        noiseSettings.getOrThrow(entry.noiseSettingsKey)
      )
      val generator = entry.createGenerator?.invoke(bootstrap)
        ?: NoiseBasedChunkGenerator(bootstrap.biomeSource, bootstrap.noiseSettings)
      registrar.dynamic(context, entry.stemKey, LevelStem(dimensions.getOrThrow(entry.typeKey), generator))
    }
  }
}

data class DimensionBiome(
  val biome: ResourceKey<Biome>,
  val parameters: Climate.ParameterPoint
)

class DimensionEntry internal constructor(
  val path: String,
  val levelKey: ResourceKey<Level>,
  val stemKey: ResourceKey<LevelStem>,
  val typeKey: ResourceKey<DimensionType>,
  val noiseSettingsKey: ResourceKey<NoiseGeneratorSettings>,
  internal val biomes: List<DimensionBiome>,
  internal val createGenerator: ((DimensionBootstrapContext) -> ChunkGenerator)?
)

class DimensionBootstrapContext internal constructor(
  val entry: DimensionEntry,
  val biomeSource: MultiNoiseBiomeSource,
  val biomeParameters: Climate.ParameterList<net.minecraft.core.Holder<Biome>>,
  val biomes: HolderGetter<Biome>,
  val noiseSettings: net.minecraft.core.Holder<NoiseGeneratorSettings>
)
