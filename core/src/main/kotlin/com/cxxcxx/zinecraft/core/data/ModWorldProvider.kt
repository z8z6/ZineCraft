package com.cxxcxx.zinecraft.core.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import java.util.concurrent.CompletableFuture

class ModWorldProvider(
  output: FabricDataOutput?,
  registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricDynamicRegistryProvider(output, registriesFuture) {

  // generated/data/zinecraft-core/worldgen
  override fun configure(registries: HolderLookup.Provider, entries: Entries) {
    entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE))
    entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE))
    entries.addAll(registries.lookupOrThrow(Registries.BIOME))
    entries.addAll(registries.lookupOrThrow(Registries.PROCESSOR_LIST))
    entries.addAll(registries.lookupOrThrow(Registries.TEMPLATE_POOL))
    entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE))
    entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET))
  }

  override fun getName(): String {
    return "World Generation"
  }
}