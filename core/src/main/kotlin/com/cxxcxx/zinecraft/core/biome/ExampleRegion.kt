package com.cxxcxx.zinecraft.core.biome

import com.mojang.datafixers.util.Pair
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import terrablender.api.ParameterUtils.*
import terrablender.api.Region
import terrablender.api.RegionType
import terrablender.api.VanillaParameterOverlayBuilder
import java.util.function.Consumer


class ExampleRegion(
  name: ResourceLocation,
  weight: Int
) : Region(name, RegionType.OVERWORLD, weight) {

  override fun addBiomes(
    registry: Registry<Biome>,
    mapper: Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>
  ) {
    val builder = VanillaParameterOverlayBuilder()
    // Overlap Vanilla's parameters with our own for our COLD_BLUE biome.
    // The parameters for this biome are chosen arbitrarily.
    ParameterPointListBuilder()
      .temperature(Temperature.span(Temperature.COOL, Temperature.FROZEN))
      .humidity(Humidity.span(Humidity.ARID, Humidity.DRY))
      .continentalness(Continentalness.INLAND)
      .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
      .depth(Depth.SURFACE, Depth.FLOOR)
      .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
      .build().forEach(Consumer { point: Climate.ParameterPoint? ->
        builder.add(point, ModBiome.EXAMPLE_BIOME)
      })

    // Add our points to the mapper
    builder.build().forEach(mapper)
  }
}