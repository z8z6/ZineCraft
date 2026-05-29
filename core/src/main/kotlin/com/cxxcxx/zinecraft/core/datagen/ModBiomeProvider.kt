package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.biome.ExampleBiome
import com.cxxcxx.zinecraft.core.biome.ModBiome
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature


object ModBiomeProvider {

  fun configure(context: BootstrapContext<Biome>) {
    val holdergetter: HolderGetter<PlacedFeature?>? = context.lookup(Registries.PLACED_FEATURE)
    val holdergetter1: HolderGetter<ConfiguredWorldCarver<*>?>? = context.lookup(Registries.CONFIGURED_CARVER)

    context.register(
      ModBiome.EXAMPLE_BIOME,
      ExampleBiome.exampleBiome(holdergetter, holdergetter1)
    )
  }
}