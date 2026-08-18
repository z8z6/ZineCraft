package com.cxxcxx.zinecraft.core;

import com.cxxcxx.zinecraft.api.accessory.CollectibleCatalog;
import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.block.BlockEntityCatalog;
import com.cxxcxx.zinecraft.api.enchantment.EnchantmentCatalog;
import com.cxxcxx.zinecraft.api.entity.EntityCatalog;
import com.cxxcxx.zinecraft.api.item.CreativeTabCatalog;
import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.recipe.RecipeCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.skill.SkillCatalog;
import com.cxxcxx.zinecraft.api.skill.SkillService;
import com.cxxcxx.zinecraft.api.sound.SongCatalog;
import com.cxxcxx.zinecraft.api.sound.SoundCatalog;
import com.cxxcxx.zinecraft.api.weapon.WeaponRegistry;
import com.cxxcxx.zinecraft.api.weapon.WeaponServerController;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponPayloadTypes;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.api.world.WorldgenManager;
import com.cxxcxx.zinecraft.compat.jer.ZinecraftJerPlugin;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.biome.ModTerraBlender;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.datagen.ZinecraftDataGenerator;
import com.cxxcxx.zinecraft.core.dimension.ModDimension;
import com.cxxcxx.zinecraft.core.dimension.TerraMobSpawnPolicy;
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity;
import com.cxxcxx.zinecraft.core.entity.ModEntity;
import com.cxxcxx.zinecraft.core.item.ModCollectible;
import com.cxxcxx.zinecraft.core.item.ModCreativeTab;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.nation.TerraNationRelations;
import com.cxxcxx.zinecraft.core.quest.FtbQuestGuideInstaller;
import com.cxxcxx.zinecraft.core.skill.ModSkills;
import com.cxxcxx.zinecraft.core.sound.ModSound;
import com.cxxcxx.zinecraft.core.structure.LateranoHostStructure;
import com.cxxcxx.zinecraft.core.structure.ModLandmark;
import com.cxxcxx.zinecraft.core.structure.ModSettlement;
import com.cxxcxx.zinecraft.core.structure.ModStructure;
import com.cxxcxx.zinecraft.core.weapon.ModWeapons;
import com.cxxcxx.zinecraft.core.worldgen.ModWorldFeatures;
import com.cxxcxx.zinecraft.integration.tacz.TaczIntegration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Zinecraft.MOD_ID)
public final class Zinecraft {
  public static final String MOD_ID = "zinecraft";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
  public static final ModRegistrar REGISTRAR = new ModRegistrar(MOD_ID);
  public static final TranslationCatalog TRANSLATIONS = new TranslationCatalog();
  public static final ItemCatalog ITEMS = new ItemCatalog(REGISTRAR, TRANSLATIONS);
  public static final CollectibleCatalog COLLECTIBLES = new CollectibleCatalog(ITEMS, TRANSLATIONS, MOD_ID);
  public static final EntityCatalog ENTITIES = new EntityCatalog(REGISTRAR, ITEMS, TRANSLATIONS);
  public static final SkillCatalog SKILLS = new SkillCatalog(ITEMS, TRANSLATIONS);
  public static final BlockCatalog BLOCKS = new BlockCatalog(REGISTRAR, TRANSLATIONS);
  public static final CreativeTabCatalog CREATIVE_TABS = new CreativeTabCatalog(REGISTRAR, ITEMS, BLOCKS, TRANSLATIONS);
  public static final BlockEntityCatalog BLOCK_ENTITIES = new BlockEntityCatalog(REGISTRAR);
  public static final SoundCatalog SOUNDS = new SoundCatalog(REGISTRAR);
  public static final SongCatalog SONGS = new SongCatalog(REGISTRAR, SOUNDS, ITEMS, TRANSLATIONS);
  public static final EnchantmentCatalog ENCHANTMENTS = new EnchantmentCatalog(REGISTRAR, TRANSLATIONS);
  public static final WorldgenManager WORLDGEN = new WorldgenManager(REGISTRAR, TRANSLATIONS, BLOCKS);
  public static final RecipeCatalog RECIPES = new RecipeCatalog();
  public static final SkillService SKILL_SERVICE = new SkillService();
  public static final WeaponRegistry WEAPONS = new WeaponRegistry();
  public static Zinecraft INSTANCE;


  public Zinecraft(IEventBus modBus) {
    INSTANCE = this;
    bootstrapContent();
    REGISTRAR.register(modBus);
    modBus.addListener(WeaponPayloadTypes::register);
    modBus.addListener(this::commonSetup);
    modBus.addListener(ZinecraftDataGenerator::gatherData);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onServerTick);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onPlayerLogout);
    NeoForge.EVENT_BUS.addListener(TerraMobSpawnPolicy::onFinalizeSpawn);
    FtbQuestGuideInstaller.INSTANCE.install();
  }

  // 按依赖顺序显式触发静态内容注册，避免依赖单例数组和类加载副作用。
  public static void bootstrapContent() {
    WeaponStateComponents.bootstrap();
    ModSound.bootstrap();
    ModItem.bootstrap();
    ModCollectible.bootstrap();
    ModBlock.bootstrap();
    ModBiome.bootstrap();
    ModBlockEntity.bootstrap();
    ModSkills.bootstrap();
    ModWeapons.bootstrap();
    TaczIntegration.bootstrap();
    ModEntity.bootstrap();
    TerraNationRelations.bootstrap();
    ModDimension.bootstrap();
    LateranoHostStructure.bootstrap();
    ModLandmark.bootstrap();
    ModSettlement.bootstrap();
    ModWorldFeatures.bootstrap();
    ModStructure.bootstrap();
    ModCreativeTab.bootstrap();
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      ModWeapons.bindRegisteredItems();
      ModTerraBlender.initialize();
      if (ModList.get().isLoaded("jeresources")) ZinecraftJerPlugin.install();
    });
  }

}
