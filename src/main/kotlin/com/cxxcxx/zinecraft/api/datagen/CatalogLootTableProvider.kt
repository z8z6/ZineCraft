package com.cxxcxx.zinecraft.api.datagen

import com.cxxcxx.zinecraft.api.block.BlockCatalog
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class CatalogLootTableProvider(
  output: FabricDataOutput,
  registries: CompletableFuture<HolderLookup.Provider?>?,
  private val blocks: BlockCatalog
) : FabricBlockLootTableProvider(output, registries) {
  override fun generate() {
    blocks.entries.filter { it.dropSelf }.forEach { dropSelf(it.block) }
    blocks.entries.mapNotNull { entry -> entry.dropItem?.let { entry.block to it } }
      .forEach { (block, item) -> dropOther(block, item) }
  }
}
