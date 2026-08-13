package com.cxxcxx.zinecraft.core.biome

import com.mojang.datafixers.util.Pair
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.Climate
import terrablender.api.ParameterUtils.ParameterPointListBuilder
import terrablender.api.Region
import terrablender.api.RegionType
import terrablender.api.VanillaParameterOverlayBuilder
import java.util.function.Consumer

class TerraNationRegion(name: ResourceLocation, weight: Int) : Region(name, RegionType.OVERWORLD, weight) {
  override fun addBiomes(
    registry: Registry<Biome>,
    mapper: Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>>
  ) {
    val overlay = VanillaParameterOverlayBuilder()
    NationBiomePlacements.ALL.forEach { placement ->
      ParameterPointListBuilder()
        .temperature(placement.temperature)
        .humidity(placement.humidity)
        .continentalness(placement.continentalness)
        .erosion(placement.erosion)
        .depth(placement.depth)
        .weirdness(placement.weirdness)
        .build()
        .forEach { point -> overlay.add(point, placement.biome) }
    }
    overlay.build().forEach(mapper)
  }
}
