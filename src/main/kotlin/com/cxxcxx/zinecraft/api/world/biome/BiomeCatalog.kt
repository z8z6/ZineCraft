package com.cxxcxx.zinecraft.api.world.biome

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

class BiomeCatalog(private val registrar: ModRegistrar) {
  private val entries = mutableListOf<BiomeEntry>()

  fun register(path: String, build: SimpleBiomeBuilder.() -> Unit): ResourceKey<Biome> {
    val key = registrar.key(Registries.BIOME, path)
    entries += BiomeEntry(key, build)
    return key
  }

  internal fun bootstrap(context: BootstrapContext<Biome>) {
    val features: HolderGetter<PlacedFeature> = context.lookup(Registries.PLACED_FEATURE)
    val carvers: HolderGetter<ConfiguredWorldCarver<*>> = context.lookup(Registries.CONFIGURED_CARVER)
    entries.forEach { entry ->
      registrar.dynamic(context, entry.key, SimpleBiomeBuilder(features, carvers).apply(entry.build).build())
    }
  }
}

private data class BiomeEntry(
  val key: ResourceKey<Biome>,
  val build: SimpleBiomeBuilder.() -> Unit
)
