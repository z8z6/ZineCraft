package com.cxxcxx.zinecraft.core.biome

import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import terrablender.api.ParameterUtils.*

internal data class NationBiomePlacement(
  val biome: ResourceKey<Biome>,
  val temperature: Temperature,
  val humidity: Humidity,
  val continentalness: Continentalness,
  val erosion: Erosion,
  val weirdness: Weirdness,
  val depth: Depth = Depth.SURFACE
)

/** Distinct climate points keep all nation biomes reachable without broad overlapping ranges. */
internal object NationBiomePlacements {
  val ALL = listOf(
    NationBiomePlacement(
      NationBiomes.AEGIR_ABYSSAL_SEA,
      Temperature.COOL,
      Humidity.HUMID,
      Continentalness.DEEP_OCEAN,
      Erosion.EROSION_4,
      Weirdness.VALLEY
    ),
    NationBiomePlacement(
      NationBiomes.BOLIVAR_PLAIN,
      Temperature.WARM,
      Humidity.NEUTRAL,
      Continentalness.NEAR_INLAND,
      Erosion.EROSION_5,
      Weirdness.MID_SLICE_NORMAL_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.HIGASHI_SHADOW_RIFT,
      Temperature.COOL,
      Humidity.WET,
      Continentalness.MID_INLAND,
      Erosion.EROSION_0,
      Weirdness.VALLEY
    ),
    NationBiomePlacement(
      NationBiomes.DURIN_UNDERGROUND_GARDEN,
      Temperature.WARM,
      Humidity.HUMID,
      Continentalness.INLAND,
      Erosion.EROSION_1,
      Weirdness.VALLEY,
      Depth.UNDERGROUND
    ),
    NationBiomePlacement(
      NationBiomes.COLUMBIA_SANDSTONE_WILDS,
      Temperature.HOT,
      Humidity.ARID,
      Continentalness.FAR_INLAND,
      Erosion.EROSION_2,
      Weirdness.PEAK_VARIANT
    ),
    NationBiomePlacement(
      NationBiomes.KAZIMIERZ_KNIGHTLAND,
      Temperature.NEUTRAL,
      Humidity.DRY,
      Continentalness.MID_INLAND,
      Erosion.EROSION_5,
      Weirdness.MID_SLICE_NORMAL_DESCENDING
    ),
    NationBiomePlacement(
      NationBiomes.KAZDEL_SCARRED_WASTES,
      Temperature.NEUTRAL,
      Humidity.ARID,
      Continentalness.FAR_INLAND,
      Erosion.EROSION_0,
      Weirdness.LOW_SLICE_NORMAL_DESCENDING
    ),
    NationBiomePlacement(
      NationBiomes.LATERANO_HOLY_FIELDS,
      Temperature.WARM,
      Humidity.NEUTRAL,
      Continentalness.NEAR_INLAND,
      Erosion.EROSION_4,
      Weirdness.MID_SLICE_VARIANT_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.LEITHANIEN_TWILIGHT_FOREST,
      Temperature.COOL,
      Humidity.WET,
      Continentalness.MID_INLAND,
      Erosion.EROSION_1,
      Weirdness.HIGH_SLICE_NORMAL_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.RIM_BILLITON_MINING_BADLANDS,
      Temperature.HOT,
      Humidity.DRY,
      Continentalness.FAR_INLAND,
      Erosion.EROSION_0,
      Weirdness.PEAK_NORMAL
    ),
    NationBiomePlacement(
      NationBiomes.MINOS_SUNLIT_HILLS,
      Temperature.WARM,
      Humidity.DRY,
      Continentalness.NEAR_INLAND,
      Erosion.EROSION_2,
      Weirdness.HIGH_SLICE_NORMAL_DESCENDING
    ),
    NationBiomePlacement(
      NationBiomes.SARGON_ROCKY_DESERT,
      Temperature.HOT,
      Humidity.ARID,
      Continentalness.INLAND,
      Erosion.EROSION_3,
      Weirdness.MID_SLICE_VARIANT_DESCENDING
    ),
    NationBiomePlacement(
      NationBiomes.SAMI_FROZEN_FOREST,
      Temperature.ICY,
      Humidity.WET,
      Continentalness.FAR_INLAND,
      Erosion.EROSION_1,
      Weirdness.MID_SLICE_NORMAL_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.VICTORIA_MISTY_HIGHLANDS,
      Temperature.COOL,
      Humidity.HUMID,
      Continentalness.MID_INLAND,
      Erosion.EROSION_3,
      Weirdness.HIGH_SLICE_VARIANT_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.URSUS_FROZEN_STEPPE,
      Temperature.ICY,
      Humidity.DRY,
      Continentalness.INLAND,
      Erosion.EROSION_5,
      Weirdness.MID_SLICE_NORMAL_DESCENDING
    ),
    NationBiomePlacement(
      NationBiomes.KJERAG_SNOWY_PEAKS,
      Temperature.ICY,
      Humidity.WET,
      Continentalness.FAR_INLAND,
      Erosion.EROSION_0,
      Weirdness.PEAK_NORMAL
    ),
    NationBiomePlacement(
      NationBiomes.SIRACUSA_RAINY_WOODLAND,
      Temperature.NEUTRAL,
      Humidity.HUMID,
      Continentalness.NEAR_INLAND,
      Erosion.EROSION_4,
      Weirdness.LOW_SLICE_VARIANT_ASCENDING
    ),
    NationBiomePlacement(
      NationBiomes.YAN_MOUNTAIN_GROVE,
      Temperature.NEUTRAL,
      Humidity.WET,
      Continentalness.MID_INLAND,
      Erosion.EROSION_2,
      Weirdness.PEAK_VARIANT
    ),
    NationBiomePlacement(
      NationBiomes.IBERIA_SALT_DELTA,
      Temperature.WARM,
      Humidity.HUMID,
      Continentalness.COAST,
      Erosion.EROSION_6,
      Weirdness.VALLEY
    )
  )
}
