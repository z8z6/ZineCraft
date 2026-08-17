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
import com.cxxcxx.zinecraft.api.world.biome.BiomeCatalog;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionCatalog;
import com.cxxcxx.zinecraft.api.world.feature.FeatureCatalog;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.compat.jer.ZinecraftJerPlugin;
import com.cxxcxx.zinecraft.core.biome.ModTerraBlender;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.cxxcxx.zinecraft.core.block.AuthorHeadBlocks;
import com.cxxcxx.zinecraft.core.block.MaterialOres;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.block.NationBlocks;
import com.cxxcxx.zinecraft.core.datagen.ZinecraftDataGenerator;
import com.cxxcxx.zinecraft.core.dimension.ModDimensions;
import com.cxxcxx.zinecraft.core.dimension.TerraMobSpawnPolicy;
import com.cxxcxx.zinecraft.core.entity.ModBlockEntity;
import com.cxxcxx.zinecraft.core.entity.ModEntities;
import com.cxxcxx.zinecraft.core.item.ModCollectibles;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.item.NationFoods;
import com.cxxcxx.zinecraft.core.nation.TerraNationRelations;
import com.cxxcxx.zinecraft.core.quest.FtbQuestGuideInstaller;
import com.cxxcxx.zinecraft.core.skill.ModSkills;
import com.cxxcxx.zinecraft.core.sound.ModSound;
import com.cxxcxx.zinecraft.core.structure.*;
import com.cxxcxx.zinecraft.core.weapon.ModWeapons;
import com.cxxcxx.zinecraft.core.worldgen.ModWorldFeatures;
import com.cxxcxx.zinecraft.integration.tacz.TaczIntegration;
import net.minecraft.world.item.ItemStack;
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
  public static final TranslationCatalog TRANSLATIONS = new TranslationCatalog();
  public static Zinecraft INSTANCE;
  public static final ModRegistrar REGISTRAR = new ModRegistrar(MOD_ID);
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
  public static final WorldgenManager WORLDGEN = new WorldgenManager(REGISTRAR);
  public static final BiomeCatalog BIOMES = WORLDGEN.getBiomes();
  public static final DimensionCatalog DIMENSIONS = WORLDGEN.getDimensions();
  public static final FeatureCatalog FEATURES = WORLDGEN.getFeatures();
  public static final StructureCatalog STRUCTURES = WORLDGEN.getStructures();
  public static final RecipeCatalog RECIPES = new RecipeCatalog();
  public static final SkillService SKILL_SERVICE = new SkillService();
  public static final WeaponRegistry WEAPONS = new WeaponRegistry();
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  public Zinecraft(IEventBus modBus) {
    INSTANCE = this;
    bootstrapContent();
    registerSpecialCreativeTabs();
    REGISTRAR.register(modBus);
    modBus.addListener(WeaponPayloadTypes::register);
    modBus.addListener(this::commonSetup);
    modBus.addListener(ZinecraftDataGenerator::gatherData);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onServerTick);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onPlayerLogout);
    NeoForge.EVENT_BUS.addListener(TerraMobSpawnPolicy::onFinalizeSpawn);
    FtbQuestGuideInstaller.INSTANCE.install();
  }

  /**
   * Forces catalog declarations to run before their DeferredRegisters are attached.
   */
  public static void bootstrapContent() {
    Object[] content = {
        WeaponStateComponents.INSTANCE,
        ModSound.INSTANCE, ModItem.INSTANCE, ModCollectibles.INSTANCE, NationFoods.INSTANCE,
        ModBlock.INSTANCE, AuthorHeadBlocks.INSTANCE, MaterialOres.INSTANCE, NationBlocks.INSTANCE, ModBlockEntity.INSTANCE,
        ModSkills.INSTANCE, ModWeapons.INSTANCE, TaczIntegration.INSTANCE, ModEntities.INSTANCE,
        TerraNationRelations.INSTANCE, NationBiomes.INSTANCE, ModDimensions.INSTANCE,
        LateranoHostStructure.INSTANCE, NationLandmarks.INSTANCE, NationSettlements.INSTANCE,
        ModWorldFeatures.INSTANCE, ModStructure.INSTANCE, StructureTranslations.INSTANCE
    };
    if (content.length == 0) throw new IllegalStateException("Content bootstrap failed");
  }

  /**
   * 藏品与技能数量较多且语义独立，因此不混入普通物品页。
   */
  private void registerSpecialCreativeTabs() {
    var collectibles = ModCollectibles.ALL;
    if (collectibles.isEmpty()) throw new IllegalStateException("藏品创造模式页不能为空");
    CREATIVE_TABS.register(
        "collectibles",
        "Zinecraft 藏品",
        "Zinecraft Collectibles",
        () -> new ItemStack(collectibles.getFirst().getItem()),
        output -> collectibles.forEach(entry -> output.accept(entry.getItem()))
    );

    var skills = SKILLS.getEntries();
    if (skills.isEmpty()) throw new IllegalStateException("技能创造模式页不能为空");
    CREATIVE_TABS.register(
        "skills",
        "Zinecraft 技能",
        "Zinecraft Skills",
        () -> new ItemStack(skills.getFirst()),
        output -> skills.forEach(output::accept)
    );

  }

  private void commonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      ModWeapons.INSTANCE.bindRegisteredItems();
      ModTerraBlender.initialize();
      if (ModList.get().isLoaded("jeresources")) ZinecraftJerPlugin.install();
    });
  }

}
