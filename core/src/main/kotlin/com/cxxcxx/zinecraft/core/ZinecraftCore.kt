package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.datagen.ModWorldPlacedFeatures
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.world.level.levelgen.GenerationStep
import org.slf4j.LoggerFactory

object ZinecraftCore : ModInitializer {
  @JvmField
  var MOD_ID = "zinecraft-core"
  private val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModItem.init()
    ModBlock.init()
    ModBlockEntity.init()
    initBiome()
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