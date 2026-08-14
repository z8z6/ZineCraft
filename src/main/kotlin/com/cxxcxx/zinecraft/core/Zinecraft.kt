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
import com.cxxcxx.zinecraft.api.skill.SkillCatalog
import com.cxxcxx.zinecraft.api.skill.SkillService
import com.cxxcxx.zinecraft.api.sound.SongCatalog
import com.cxxcxx.zinecraft.api.sound.SoundCatalog
import com.cxxcxx.zinecraft.api.weapon.WeaponRegistry
import com.cxxcxx.zinecraft.api.weapon.WeaponServerController
import com.cxxcxx.zinecraft.api.weapon.network.WeaponPayloadTypes
import com.cxxcxx.zinecraft.api.world.WorldgenManager
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import com.cxxcxx.zinecraft.core.block.MaterialOres
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.block.NationBlocks
import com.cxxcxx.zinecraft.core.dimension.ModDimensions
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity
import com.cxxcxx.zinecraft.core.entity.ModEntities
import com.cxxcxx.zinecraft.core.item.ModItem
import com.cxxcxx.zinecraft.core.item.NationFoods
import com.cxxcxx.zinecraft.core.nation.TerraNationRelations
import com.cxxcxx.zinecraft.core.quest.FtbQuestGuideInstaller
import com.cxxcxx.zinecraft.core.skill.ModSkills
import com.cxxcxx.zinecraft.core.sound.ModSound
import com.cxxcxx.zinecraft.core.structure.LateranoHostStructure
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.NationLandmarks
import com.cxxcxx.zinecraft.core.structure.NationSettlements
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import com.cxxcxx.zinecraft.core.weapon.ModWeapons
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
  val SKILLS = SkillCatalog(ITEMS, TRANSLATIONS)
  val SKILL_SERVICE = SkillService()
  val WEAPONS = WeaponRegistry()
  val WORLDGEN = WorldgenManager(REGISTRAR)
  val BIOMES = WORLDGEN.biomes
  val DIMENSIONS = WORLDGEN.dimensions
  val FEATURES = WORLDGEN.features
  val STRUCTURES = WORLDGEN.structures
  val logger = LoggerFactory.getLogger(MOD_ID)

  override fun onInitialize() {
    ModSound
    ModItem
    NationFoods
    ModBlock
    MaterialOres
    NationBlocks
    ModBlockEntity
    ModSkills
    ModWeapons
    ModTaczWeapons
    ModEntities
    TerraNationRelations
    NationBiomes
    ModDimensions
    LateranoHostStructure
    NationLandmarks
    NationSettlements
    ModWorldFeatures
    ModStructure
    FtbQuestGuideInstaller.install()
    WeaponPayloadTypes.register()
    WeaponServerController.initialize()
    WORLDGEN.initialize()
  }

}
