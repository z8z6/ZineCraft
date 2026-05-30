package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.datagen.ModWorldPlacedFeatures
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.item.CompostableItemRegistry
import com.cxxcxx.zinecraft.core.item.FuelRegister
import com.cxxcxx.zinecraft.core.item.ModItem
import com.cxxcxx.zinecraft.core.structure.ESStructurePoolElementTypes
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.ModTemplatePool
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.world.level.levelgen.GenerationStep
import org.slf4j.LoggerFactory

object ZinecraftCore : ModInitializer {
  @JvmField
  var MOD_ID = "zinecraft-core"
  val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModItem.init()
    FuelRegister.init()
    CompostableItemRegistry.init()
    ModBlock.init()
    ModBlockEntity.init()
    initBiome()
    ModTemplatePool.init()
    ESStructurePoolElementTypes.init()
    ModStructure.init()
  }

  fun initBiome() {
    // 矿物生成
    BiomeModifications.addFeature(
      BiomeSelectors.foundInOverworld(),
      GenerationStep.Decoration.UNDERGROUND_ORES,
      ModWorldPlacedFeatures.EXAMPLE_BLOCK_ORE_PLACED_KEY
    )
  }
}