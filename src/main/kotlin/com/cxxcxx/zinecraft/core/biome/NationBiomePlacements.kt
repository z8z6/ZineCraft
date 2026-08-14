package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome
import net.minecraft.world.level.biome.Climate

/**
 * 泰拉维度的多噪声气候点。
 *
 * 点位覆盖海洋、海岸、内陆、山峰与地下深度；最近点分区会填满整个泰拉维度，同时不会向主世界注入任何国家群系。
 */
internal object NationBiomePlacements {
  val ALL = listOf(
    placement(NationBiomes.AEGIR_ABYSSAL_SEA, -0.35f, 0.65f, -1.05f, 0.35f, 0.0f, -0.75f),
    placement(NationBiomes.BOLIVAR_PLAIN, 0.55f, 0.0f, 0.10f, 0.55f, 0.0f, 0.0f),
    placement(NationBiomes.HIGASHI_SHADOW_RIFT, -0.25f, 0.35f, 0.35f, -0.80f, 0.0f, -0.75f),
    placement(NationBiomes.DURIN_UNDERGROUND_GARDEN, 0.35f, 0.65f, 0.55f, -0.40f, 1.0f, -0.75f),
    placement(NationBiomes.COLUMBIA_SANDSTONE_WILDS, 0.90f, -0.75f, 0.90f, -0.25f, 0.0f, 0.80f),
    placement(NationBiomes.KAZIMIERZ_KNIGHTLAND, 0.0f, -0.35f, 0.35f, 0.55f, 0.0f, -0.20f),
    placement(NationBiomes.KAZDEL_SCARRED_WASTES, 0.0f, -0.85f, 0.90f, -0.80f, 0.0f, -0.55f),
    placement(NationBiomes.LATERANO_HOLY_FIELDS, 0.45f, 0.0f, 0.10f, 0.35f, 0.0f, 0.30f),
    placement(NationBiomes.LEITHANIEN_TWILIGHT_FOREST, -0.30f, 0.45f, 0.35f, -0.40f, 0.0f, 0.55f),
    placement(NationBiomes.RIM_BILLITON_MINING_BADLANDS, 0.85f, -0.45f, 0.90f, -0.75f, 0.0f, 0.90f),
    placement(NationBiomes.MINOS_SUNLIT_HILLS, 0.45f, -0.25f, 0.10f, -0.20f, 0.0f, 0.55f),
    placement(NationBiomes.SARGON_ROCKY_DESERT, 0.95f, -0.90f, 0.65f, 0.05f, 0.0f, -0.10f),
    placement(NationBiomes.SAMI_FROZEN_FOREST, -0.85f, 0.40f, 0.85f, -0.45f, 0.0f, 0.0f),
    placement(NationBiomes.VICTORIA_MISTY_HIGHLANDS, -0.20f, 0.70f, 0.40f, 0.0f, 0.0f, 0.65f),
    placement(NationBiomes.URSUS_FROZEN_STEPPE, -0.75f, -0.30f, 0.65f, 0.50f, 0.0f, -0.25f),
    placement(NationBiomes.KJERAG_SNOWY_PEAKS, -0.95f, 0.35f, 0.95f, -0.85f, 0.0f, 0.95f),
    placement(NationBiomes.SIRACUSA_RAINY_WOODLAND, 0.05f, 0.75f, 0.15f, 0.35f, 0.0f, -0.65f),
    placement(NationBiomes.YAN_MOUNTAIN_GROVE, 0.05f, 0.40f, 0.45f, -0.20f, 0.0f, 0.80f),
    placement(NationBiomes.IBERIA_SALT_DELTA, 0.50f, 0.65f, -0.20f, 0.80f, 0.0f, -0.75f)
  )

  private fun placement(
    biome: net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>,
    temperature: Float,
    humidity: Float,
    continentalness: Float,
    erosion: Float,
    depth: Float,
    weirdness: Float
  ) = DimensionBiome(
    biome,
    Climate.parameters(temperature, humidity, continentalness, erosion, depth, weirdness, 0.0f)
  )
}
