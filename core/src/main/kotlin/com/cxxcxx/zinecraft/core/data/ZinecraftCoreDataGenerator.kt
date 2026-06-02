package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.client.datagen.ModZhLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

// fabric 只能有一个数据生成器入口
object ZinecraftCoreDataGenerator : DataGeneratorEntrypoint {
  override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
    val pack = fabricDataGenerator.createPack()
    pack.addProvider(::ModEnLanguageProvider)
    pack.addProvider(::ModZhLanguageProvider)
    pack.addProvider(::ModModelProvider)
    pack.addProvider(::ModBlockLootTableProvider)
    pack.addProvider(::ModRecipeProvider)
    pack.addProvider(::ModWorldProvider)
  }

  override fun buildRegistry(registryBuilder: RegistrySetBuilder?) {
    registryBuilder?.add(Registries.CONFIGURED_FEATURE, ModWorldConfiguredFeatures::configure)
    registryBuilder?.add(Registries.PLACED_FEATURE, ModWorldPlacedFeatures::configure)
    registryBuilder?.add(Registries.BIOME, ModBiome::configure)
    registryBuilder?.add(Registries.PROCESSOR_LIST, ModTemplatePool::configureProcessors)
    registryBuilder?.add(Registries.TEMPLATE_POOL, ModTemplatePool::configureTemplate)
    registryBuilder?.add(Registries.STRUCTURE, ModStructure::configure)
    registryBuilder?.add(Registries.STRUCTURE_SET, ModStructureSet::configure)
    registryBuilder?.add(Registries.JUKEBOX_SONG, ModSound::configure)
  }
}