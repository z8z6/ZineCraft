package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.client.datagen.EnLanguageProvider
import com.cxxcxx.zinecraft.core.client.datagen.ModModelProvider
import com.cxxcxx.zinecraft.core.client.datagen.ZhLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

// fabric 只能有一个数据生成器入口
object ZinecraftCoreDataGenerator : DataGeneratorEntrypoint {
  override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
    val pack = fabricDataGenerator.createPack()
    pack.addProvider(::EnLanguageProvider)
    pack.addProvider(::ZhLanguageProvider)
    pack.addProvider(::ModModelProvider)
    pack.addProvider(::ModBlockLootTableProvider)
    pack.addProvider(::ModRecipeProvider)
    pack.addProvider(::ModWorldProvider)
  }

  override fun buildRegistry(registryBuilder: RegistrySetBuilder?) {
    registryBuilder?.add(Registries.CONFIGURED_FEATURE, ModWorldConfiguredFeatures::configure)
    registryBuilder?.add(Registries.PLACED_FEATURE, ModWorldPlacedFeatures::configure)
    registryBuilder?.add(Registries.BIOME, ModBiomeProvider::configure)
  }
}