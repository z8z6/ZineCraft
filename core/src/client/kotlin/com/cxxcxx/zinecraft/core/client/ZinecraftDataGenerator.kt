package com.cxxcxx.zinecraft.core.client

import com.cxxcxx.zinecraft.core.client.datagen.*
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator


object ZinecraftCoreDataGenerator : DataGeneratorEntrypoint {
  override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
	  val pack = fabricDataGenerator.createPack()
    pack.addProvider(::EnLanguageProvider)
    pack.addProvider(::ZhLanguageProvider)
    pack.addProvider(::ModModelProvider)
    pack.addProvider(::ModBlockLootTableProvider)
    pack.addProvider(::ModRecipeProvider)
  }
}