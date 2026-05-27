package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object ZinecraftCore : ModInitializer {
  val MOD_ID = "zinecraft-core"
  private val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModItem.init()
  }
}