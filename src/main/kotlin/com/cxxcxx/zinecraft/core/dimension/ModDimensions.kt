package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.api.world.dimension.TerraBiomeSource
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator

/** 泰拉大陆及其国家群系所在的独立维度。 */
object ModDimensions {
  /** 数据包中的 `zinecraft:terra` 群系源类型。 */
  private val TERRA_BIOME_SOURCE = Zinecraft.REGISTRAR.biomeSource("terra", TerraBiomeSource.CODEC)

  val TERRA = Zinecraft.DIMENSIONS.register(
    path = "terra",
    biomes = NationBiomePlacements.ALL.filterNot { it.biome == NationBiomes.LATERANO_HOLY_FIELDS },
    createGenerator = { context ->
      val laterano = context.biomes.getOrThrow(NationBiomes.LATERANO_HOLY_FIELDS)
      NoiseBasedChunkGenerator(
        TerraBiomeSource(context.biomeParameters, laterano, LATERANO_CENTER_RADIUS),
        context.noiseSettings
      )
    }
  )

  init {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra")
  }

  const val LATERANO_CENTER_RADIUS = 1024
}
