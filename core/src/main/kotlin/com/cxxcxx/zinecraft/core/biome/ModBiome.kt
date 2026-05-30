package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome

object ModBiome {
  val EXAMPLE_BIOME: ResourceKey<Biome?> = register("example_biome")

  private fun register(name: String): ResourceKey<Biome?> {
    return ResourceKey.create(
      Registries.BIOME,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }
}