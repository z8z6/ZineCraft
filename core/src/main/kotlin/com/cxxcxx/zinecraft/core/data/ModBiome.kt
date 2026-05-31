package com.cxxcxx.zinecraft.core.data


import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.biome.OverworldBiome
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature


object ModBiome {

  val EXAMPLE_BIOME: ResourceKey<Biome> = key("example_biome")

  fun configure(ctx: BootstrapContext<Biome>) {
    val place: HolderGetter<PlacedFeature> = ctx.lookup(Registries.PLACED_FEATURE)
    val worldCarver: HolderGetter<ConfiguredWorldCarver<*>> = ctx.lookup(Registries.CONFIGURED_CARVER)

    ctx.register(
      EXAMPLE_BIOME,
      OverworldBiome.exampleBiome(place, worldCarver)
    )
  }

  private fun key(name: String): ResourceKey<Biome> {
    return ZinecraftCore.key(Registries.BIOME, name)
  }

  fun init() {}
}