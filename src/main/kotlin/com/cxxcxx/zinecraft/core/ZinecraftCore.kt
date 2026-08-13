package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.api.content.ContentCatalog
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.cxxcxx.zinecraft.api.world.WorldgenCatalog
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.data.*
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object ZinecraftCore : ModInitializer {
  @JvmField
  val MOD_ID = "zinecraft-core"
  val REGISTRAR = ModRegistrar(MOD_ID)
  val CONTENT = ContentCatalog(REGISTRAR)
  val WORLDGEN = WorldgenCatalog(REGISTRAR)
  val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModSound
    ModItem
    ModBlock
    ModBlockEntity
    ModBiome
    ModWorldFeatures
    ModBuildings
    ModStructure
    WORLDGEN.initialize()
  }

}
