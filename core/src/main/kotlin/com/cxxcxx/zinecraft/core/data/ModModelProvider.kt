package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates


class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

  // generated/assets/zinecraft-core/blockstates
  // generated/assets/zinecraft-core/models/block
  override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators?) {
    blockStateModelGenerator?.createTrivialCube(ModBlock.EXAMPLE_ENTITY_BLOCK)
  }

  override fun generateItemModels(G: ItemModelGenerators?) {
    ModItem.ItemWrap.List.forEach { G?.generateFlatItem(it.item, it.model) }
    G?.generateFlatItem(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), ModelTemplates.FLAT_ITEM)
  }

  override fun getName(): String {
    return "ModModelProvider"
  }
}