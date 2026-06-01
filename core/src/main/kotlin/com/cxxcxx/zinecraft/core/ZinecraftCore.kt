package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.data.*
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.item.CompostableItemRegistry
import com.cxxcxx.zinecraft.core.item.FuelRegister
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceKey.create
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.GenerationStep
import org.slf4j.LoggerFactory

object ZinecraftCore : ModInitializer {
  @JvmField
  var MOD_ID = "zinecraft-core"
  val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModSound.init()
    ModItem.init()
    FuelRegister.init()
    CompostableItemRegistry.init()
    ModBlock.init()
    ModBlockEntity.init()
    ModBiome.init()
    ModTemplatePool.init()
    ModStructure.init()
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

  fun id(name: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
  }

  fun <T> key(
    key: ResourceKey<Registry<T>>,
    name: String
  )
      : ResourceKey<T> {
    return create(key, id(name))
  }

  fun <V, T : V> register(registry: Registry<V>, name: String, o: T): T {
    return Registry.register(registry, id(name), o)
  }
}