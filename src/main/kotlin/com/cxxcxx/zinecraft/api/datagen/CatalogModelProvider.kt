package com.cxxcxx.zinecraft.api.datagen

import com.cxxcxx.zinecraft.api.block.BlockCatalog
import com.cxxcxx.zinecraft.api.item.ItemCatalog
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators

open class CatalogModelProvider(
  output: FabricDataOutput,
  private val items: ItemCatalog,
  private val blocks: BlockCatalog
) : FabricModelProvider(output) {
  override fun generateBlockStateModels(generator: BlockModelGenerators?) {
    blocks.entries.filter { it.cubeModel }.forEach { generator?.createTrivialCube(it.block) }
  }

  override fun generateItemModels(generator: ItemModelGenerators?) {
    items.entries.forEach { generator?.generateFlatItem(it.item, it.model) }
  }
}
