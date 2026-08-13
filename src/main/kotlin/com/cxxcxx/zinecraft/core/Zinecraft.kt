package com.cxxcxx.zinecraft.core

import com.cxxcxx.zinecraft.api.block.BlockCatalog
import com.cxxcxx.zinecraft.api.block.BlockEntityCatalog
import com.cxxcxx.zinecraft.api.enchantment.EnchantmentCatalog
import com.cxxcxx.zinecraft.api.entity.EntityCatalog
import com.cxxcxx.zinecraft.api.item.CreativeTabCatalog
import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.recipe.RecipeCatalog
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.cxxcxx.zinecraft.api.sound.SongCatalog
import com.cxxcxx.zinecraft.api.sound.SoundCatalog
import com.cxxcxx.zinecraft.api.world.WorldgenManager
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.item.ModItem
import com.cxxcxx.zinecraft.core.sound.ModSound
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.NationLandmarks
import com.cxxcxx.zinecraft.core.worldgen.ModWorldFeatures
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Zinecraft : ModInitializer {
  @JvmField
  val MOD_ID = "zinecraft"
  val REGISTRAR = ModRegistrar(MOD_ID)
  val TRANSLATIONS = TranslationCatalog()
  val ITEMS = ItemCatalog(REGISTRAR, TRANSLATIONS)
  val BLOCKS = BlockCatalog(REGISTRAR, TRANSLATIONS)
  val BLOCK_ENTITIES = BlockEntityCatalog(REGISTRAR)
  val SOUNDS = SoundCatalog(REGISTRAR)
  val RECIPES = RecipeCatalog()
  val SONGS = SongCatalog(REGISTRAR, SOUNDS, ITEMS, TRANSLATIONS)
  val CREATIVE_TABS = CreativeTabCatalog(REGISTRAR, ITEMS, BLOCKS, TRANSLATIONS)
  val ENTITIES = EntityCatalog(REGISTRAR, ITEMS, TRANSLATIONS)
  val ENCHANTMENTS = EnchantmentCatalog(REGISTRAR, TRANSLATIONS)
  val WORLDGEN = WorldgenManager(REGISTRAR)
  val BIOMES = WORLDGEN.biomes
  val FEATURES = WORLDGEN.features
  val STRUCTURES = WORLDGEN.structures
  val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModSound
    ModItem
    ModBlock
    ModBlockEntity
    NationBiomes
    NationLandmarks
    ModWorldFeatures
    ModStructure
    WORLDGEN.initialize()
  }

}
