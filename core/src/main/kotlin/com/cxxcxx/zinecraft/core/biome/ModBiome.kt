package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome

object ModBiome {
  val HOT_RED: ResourceKey<Biome?> = register("hot_red")
  val COLD_BLUE: ResourceKey<Biome?> = register("cold_blue")
  val EXAMPLE_BIOME: ResourceKey<Biome?> = register("example_biome")

  private fun register(name: String): ResourceKey<Biome?> {
    return ResourceKey.create(
      Registries.BIOME,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }
}