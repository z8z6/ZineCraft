package com.cxxcxx.zinecraft.core.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import java.util.concurrent.CompletableFuture

class ModWorldProvider(
  output: FabricDataOutput?,
  registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricDynamicRegistryProvider(output, registriesFuture) {

  override fun configure(registries: HolderLookup.Provider, entries: Entries) {
    // #region worldgen-add-entries
    entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE))
    entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE))
    // #endregion worldgen-add-entries
  }

  override fun getName(): String {
    return "World Generation"
  }
}