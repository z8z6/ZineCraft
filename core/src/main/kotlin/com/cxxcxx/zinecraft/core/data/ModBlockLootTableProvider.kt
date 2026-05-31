package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.block.ModBlock
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ModBlockLootTableProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricBlockLootTableProvider(dataOutput, registryLookup) {
  override fun generate() {
    dropSelf(ModBlock.EXAMPLE_ENTITY_BLOCK)
  }
}