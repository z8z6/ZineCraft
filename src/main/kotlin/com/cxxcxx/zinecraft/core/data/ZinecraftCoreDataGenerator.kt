package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.api.content.ContentLanguageProvider
import com.cxxcxx.zinecraft.api.content.ContentLootTableProvider
import com.cxxcxx.zinecraft.api.content.ContentModelProvider
import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

// fabric 只能有一个数据生成器入口
object ZinecraftCoreDataGenerator : DataGeneratorEntrypoint {
  override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
    ModSound
    ModItem
    ModBlock
    ModBiome
    ModWorldFeatures
    ModBuildings
    ModStructure
    ModStructureSet
    val pack = fabricDataGenerator.createPack()
    pack.addProvider { output, registries ->
      ContentLanguageProvider(output, registries, ZinecraftCore.CONTENT, "en_us")
    }
    pack.addProvider { output, registries ->
      ContentLanguageProvider(output, registries, ZinecraftCore.CONTENT, "zh_cn")
    }
    pack.addProvider(::ModContentModelProvider)
    pack.addProvider { output, registries ->
      ContentLootTableProvider(output, registries, ZinecraftCore.CONTENT)
    }
    pack.addProvider(::ModRecipeProvider)
    pack.addProvider(::ModWorldProvider)
  }

  override fun buildRegistry(registryBuilder: RegistrySetBuilder?) {
    registryBuilder?.let(ZinecraftCore.WORLDGEN::addDataGeneration)
    registryBuilder?.add(Registries.JUKEBOX_SONG, ModSound::configure)
  }
}

private class ModContentModelProvider(output: net.fabricmc.fabric.api.datagen.v1.FabricDataOutput) :
  ContentModelProvider(output, ZinecraftCore.CONTENT)
