package com.cxxcxx.zinecraft.core.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import java.util.concurrent.CompletableFuture

class ModDynamicRegistryProvider(
  output: FabricDataOutput?,
  registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricDynamicRegistryProvider(output, registriesFuture) {

  override fun configure(registries: HolderLookup.Provider, entries: Entries) {
    entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE))
    entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE))
    entries.addAll(registries.lookupOrThrow(Registries.BIOME))
    entries.addAll(registries.lookupOrThrow(Registries.PROCESSOR_LIST))
    entries.addAll(registries.lookupOrThrow(Registries.TEMPLATE_POOL))
    entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE))
    entries.addAll(registries.lookupOrThrow(Registries.STRUCTURE_SET))
    entries.addAll(registries.lookupOrThrow(Registries.ENCHANTMENT))
    entries.addAll(registries.lookupOrThrow(Registries.JUKEBOX_SONG))
  }

  override fun getName(): String = "Dynamic Registry Data"
}
