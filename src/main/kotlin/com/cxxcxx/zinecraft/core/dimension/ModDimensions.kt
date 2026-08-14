package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomePlacements

/** 泰拉大陆及其国家群系所在的独立维度。 */
object ModDimensions {
  val TERRA = Zinecraft.DIMENSIONS.register(
    path = "terra",
    biomes = NationBiomePlacements.ALL
  )

  init {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra")
  }
}
