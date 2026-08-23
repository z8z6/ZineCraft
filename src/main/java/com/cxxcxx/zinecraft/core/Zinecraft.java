package com.cxxcxx.zinecraft.core;

import com.cxxcxx.zinecraft.api.registry.catalog.*;
import com.cxxcxx.zinecraft.api.weapon.WeaponServerController;
import com.cxxcxx.zinecraft.api.weapon.network.WeaponPayloadTypes;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.compat.jer.ZinecraftJerPlugin;
import com.cxxcxx.zinecraft.core.datagen.ZinecraftDataGenerator;
import com.cxxcxx.zinecraft.core.dimension.TerraMobSpawnPolicy;
import com.cxxcxx.zinecraft.core.dimension.TerraPlayerSpawn;
import com.cxxcxx.zinecraft.core.dimension.TerraWorldBoundary;
import com.cxxcxx.zinecraft.core.nation.TerraNationRelations;
import com.cxxcxx.zinecraft.core.nation.TerraBuildingLocateCommand;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import com.cxxcxx.zinecraft.core.quest.FtbQuestGuideInstaller;
import com.cxxcxx.zinecraft.core.registry.*;
import com.cxxcxx.zinecraft.core.skill.ModSkill;
import com.cxxcxx.zinecraft.core.worldgen.density.TerraTerrainLookup;
import com.cxxcxx.zinecraft.integration.tacz.TaczIntegration;
import net.minecraft.resources.ResourceLocation;
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
  public static final TranslationCatalog TRANSLATIONS = new TranslationCatalog();
  public static final NationCatalog NATIONS = new NationCatalog(TRANSLATIONS);
  public static final StructureCatalog STRUCTURES = new StructureCatalog(MOD_ID, TRANSLATIONS);
  public static final TerraCityRegionCatalog CITY_REGIONS = new TerraCityRegionCatalog(NATIONS, STRUCTURES, TRANSLATIONS);
  public static final TerraCityCatalog CITIES = new TerraCityCatalog(NATIONS, TRANSLATIONS);
  public static final AnimationCatalog ANIMATIONS = new AnimationCatalog(MOD_ID);
  public static final VfxCatalog VFX = new VfxCatalog(MOD_ID);
  public static final ItemCatalog ITEMS = new ItemCatalog(MOD_ID, TRANSLATIONS);
  public static final CollectibleCatalog COLLECTIBLES = new CollectibleCatalog(ITEMS, TRANSLATIONS, MOD_ID);
  public static final EntityCatalog ENTITIES = new EntityCatalog(MOD_ID, ITEMS, TRANSLATIONS);
  public static final SkillCatalog SKILLS = new SkillCatalog(ITEMS, TRANSLATIONS);
  public static final SkillEffectCatalog SKILL_EFFECTS = new SkillEffectCatalog(MOD_ID);
  public static final BlockCatalog BLOCKS = new BlockCatalog(MOD_ID, ITEMS, TRANSLATIONS);
  public static final CreativeTabCatalog CREATIVE_TABS = new CreativeTabCatalog(MOD_ID, ITEMS, BLOCKS, TRANSLATIONS);
  public static final BlockEntityCatalog BLOCK_ENTITIES = new BlockEntityCatalog(MOD_ID);
  public static final SoundCatalog SOUNDS = new SoundCatalog(MOD_ID, TRANSLATIONS);
  public static final EnchantmentCatalog ENCHANTMENTS = new EnchantmentCatalog(MOD_ID, TRANSLATIONS);
  public static final BiomeCatalog BIOMES = new BiomeCatalog(MOD_ID, TRANSLATIONS);
  public static final DensityFunctionCatalog DENSITY_FUNCTIONS = new DensityFunctionCatalog(MOD_ID);
  public static final DimensionCatalog DIMENSIONS = new DimensionCatalog(MOD_ID);
  public static final FeatureCatalog FEATURES = new FeatureCatalog(MOD_ID);
  public static final RecipeCatalog RECIPES = new RecipeCatalog();
  public static final WeaponCatalog WEAPONS = new WeaponCatalog(MOD_ID);
  public static Zinecraft INSTANCE;


  public Zinecraft(IEventBus modBus) {
    INSTANCE = this;
    bootstrapContent();
    ITEMS.register(modBus);
    BLOCKS.register(modBus);
    SOUNDS.register(modBus);
    ENTITIES.register(modBus);
    CREATIVE_TABS.register(modBus);
    BLOCK_ENTITIES.register(modBus);
    DENSITY_FUNCTIONS.register(modBus);
    DIMENSIONS.register(modBus);
    FEATURES.register(modBus);
    STRUCTURES.register(modBus);
    WeaponStateComponents.register(modBus);
    modBus.addListener(WeaponPayloadTypes::register);
    modBus.addListener(this::commonSetup);
    modBus.addListener(ZinecraftDataGenerator::gatherData);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onServerTick);
    NeoForge.EVENT_BUS.addListener(WeaponServerController.INSTANCE::onPlayerLogout);
    NeoForge.EVENT_BUS.addListener(TerraMobSpawnPolicy::onFinalizeSpawn);
    NeoForge.EVENT_BUS.addListener(TerraPlayerSpawn::onPlayerLoggedIn);
    NeoForge.EVENT_BUS.addListener(TerraWorldBoundary::onLevelLoad);
    NeoForge.EVENT_BUS.addListener(TerraBuildingLocateCommand::register);
    FtbQuestGuideInstaller.INSTANCE.install();
  }

  public static ResourceLocation id(String path) {
    return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
  }

  // 按依赖顺序显式触发静态内容注册，避免依赖单例数组和类加载副作用。
  public static void bootstrapContent() {
    ModNation.bootstrap();
    WeaponStateComponents.bootstrap();
    ModSound.bootstrap();
    ModWeaponPresentation.bootstrap();
    ModItem.bootstrap();
    ModCollectible.bootstrap();
    ModBlock.bootstrap();
    ModEntity.bootstrap();
    ModBiome.bootstrap();
    ModBlockEntity.bootstrap();
    ModSkill.bootstrap();
    ModWeaponSkillEffects.bootstrap();
    ModWeapon.bootstrap();
    TaczIntegration.bootstrap();
    TerraNationRelations.bootstrap();
    ModDensityFunction.bootstrap();
    ModDimension.bootstrap();
    ModStructure.bootstrap();
    ModCityRegion.bootstrap();
    ModCity.bootstrap();
    ModWorldFeature.bootstrap();
    ModCreativeTab.bootstrap();
  }

  private void commonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      // 布局包含全部国家、城市、Region、道路和建筑槽位，首次解析开销较大。
      // 在进入主菜单前完成读取，避免创建/进入世界时阻塞服务器线程和渲染线程。
      TerraLayoutResource.preload();
      TerraTerrainLookup.preload();
      ModTerraBlender.initialize();
      if (ModList.get().isLoaded("jeresources")) ZinecraftJerPlugin.install();
    });
  }

}
