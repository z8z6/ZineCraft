package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.api.datagen.CatalogLanguageProvider
import com.cxxcxx.zinecraft.api.datagen.CatalogLootTableProvider
import com.cxxcxx.zinecraft.api.datagen.CatalogModelProvider
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.dimension.ModDimensions
import com.cxxcxx.zinecraft.core.entity.ModEntities
import com.cxxcxx.zinecraft.core.item.ModItem
import com.cxxcxx.zinecraft.core.recipe.ModRecipeProvider
import com.cxxcxx.zinecraft.core.skill.ModSkills
import com.cxxcxx.zinecraft.core.sound.ModSound
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.NationLandmarks
import com.cxxcxx.zinecraft.core.structure.NationSettlements
import com.cxxcxx.zinecraft.core.worldgen.ModWorldFeatures
import com.cxxcxx.zinecraft.core.weapon.ModWeapons
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

// fabric 只能有一个数据生成器入口
object ZinecraftDataGenerator : DataGeneratorEntrypoint {
  override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
    ModSound
    ModItem
    ModBlock
    ModSkills
    ModWeapons
    ModTaczWeapons
    ModEntities
    NationBiomes
    ModDimensions
    NationLandmarks
    NationSettlements
    ModWorldFeatures
    ModStructure
    val pack = fabricDataGenerator.createPack()
    pack.addProvider { output, registries ->
      CatalogLanguageProvider(output, registries, Zinecraft.TRANSLATIONS, "en_us")
    }
    pack.addProvider { output, registries ->
      CatalogLanguageProvider(output, registries, Zinecraft.TRANSLATIONS, "zh_cn")
    }
    pack.addProvider(::ModCatalogModelProvider)
    pack.addProvider { output, registries ->
      CatalogLootTableProvider(output, registries, Zinecraft.BLOCKS)
    }
    pack.addProvider(::ModRecipeProvider)
    pack.addProvider(::ModDynamicRegistryProvider)
  }

  override fun buildRegistry(registryBuilder: RegistrySetBuilder?) {
    registryBuilder?.let(Zinecraft.WORLDGEN::addDataGeneration)
    registryBuilder?.add(Registries.ENCHANTMENT, Zinecraft.ENCHANTMENTS::bootstrap)
    registryBuilder?.add(Registries.JUKEBOX_SONG, ModSound::configure)
  }
}

private class ModCatalogModelProvider(output: net.fabricmc.fabric.api.datagen.v1.FabricDataOutput) :
  CatalogModelProvider(output, Zinecraft.ITEMS, Zinecraft.BLOCKS)
