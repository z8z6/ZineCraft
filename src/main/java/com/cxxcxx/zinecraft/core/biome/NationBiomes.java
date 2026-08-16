package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.biome.SimpleBiomeBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class NationBiomes {
  @NotNull
  public static final NationBiomes INSTANCE = new NationBiomes();
  @NotNull
  private static final ResourceKey<Biome> AEGIR_ABYSSAL_SEA = Zinecraft.INSTANCE
      .getBIOMES()
      .register("aegir_abyssal_sea", NationBiomes::AEGIR_ABYSSAL_SEAHelper0);
  @NotNull
  private static final ResourceKey<Biome> BOLIVAR_PLAIN = Zinecraft.INSTANCE.getBIOMES().register("bolivar_plain", NationBiomes::BOLIVAR_PLAINHelper0);
  @NotNull
  private static final ResourceKey<Biome> HIGASHI_SHADOW_RIFT = Zinecraft.INSTANCE
      .getBIOMES()
      .register("higashi_shadow_rift", NationBiomes::HIGASHI_SHADOW_RIFTHelper0);
  @NotNull
  private static final ResourceKey<Biome> DURIN_UNDERGROUND_GARDEN = Zinecraft.INSTANCE
      .getBIOMES()
      .register("durin_underground_garden", NationBiomes::DURIN_UNDERGROUND_GARDENHelper0);
  @NotNull
  private static final ResourceKey<Biome> COLUMBIA_SANDSTONE_WILDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("columbia_sandstone_wilds", NationBiomes::COLUMBIA_SANDSTONE_WILDSHelper0);
  @NotNull
  private static final ResourceKey<Biome> KAZIMIERZ_KNIGHTLAND = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kazimierz_knightland", NationBiomes::KAZIMIERZ_KNIGHTLANDHelper0);
  @NotNull
  private static final ResourceKey<Biome> KAZDEL_SCARRED_WASTES = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kazdel_scarred_wastes", NationBiomes::KAZDEL_SCARRED_WASTESHelper0);
  @NotNull
  private static final ResourceKey<Biome> LATERANO_HOLY_FIELDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("laterano_holy_fields", NationBiomes::LATERANO_HOLY_FIELDSHelper0);
  @NotNull
  private static final ResourceKey<Biome> LEITHANIEN_TWILIGHT_FOREST = Zinecraft.INSTANCE
      .getBIOMES()
      .register("leithanien_twilight_forest", NationBiomes::LEITHANIEN_TWILIGHT_FORESTHelper0);
  @NotNull
  private static final ResourceKey<Biome> RIM_BILLITON_MINING_BADLANDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("rim_billiton_mining_badlands", NationBiomes::RIM_BILLITON_MINING_BADLANDSHelper0);
  @NotNull
  private static final ResourceKey<Biome> MINOS_SUNLIT_HILLS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("minos_sunlit_hills", NationBiomes::MINOS_SUNLIT_HILLSHelper0);
  @NotNull
  private static final ResourceKey<Biome> SARGON_ROCKY_DESERT = Zinecraft.INSTANCE
      .getBIOMES()
      .register("sargon_rocky_desert", NationBiomes::SARGON_ROCKY_DESERTHelper0);
  @NotNull
  private static final ResourceKey<Biome> SAMI_FROZEN_FOREST = Zinecraft.INSTANCE
      .getBIOMES()
      .register("sami_frozen_forest", NationBiomes::SAMI_FROZEN_FORESTHelper0);
  @NotNull
  private static final ResourceKey<Biome> VICTORIA_MISTY_HIGHLANDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("victoria_misty_highlands", NationBiomes::VICTORIA_MISTY_HIGHLANDSHelper0);
  @NotNull
  private static final ResourceKey<Biome> URSUS_FROZEN_STEPPE = Zinecraft.INSTANCE
      .getBIOMES()
      .register("ursus_frozen_steppe", NationBiomes::URSUS_FROZEN_STEPPEHelper0);
  @NotNull
  private static final ResourceKey<Biome> KJERAG_SNOWY_PEAKS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kjerag_snowy_peaks", NationBiomes::KJERAG_SNOWY_PEAKSHelper0);
  @NotNull
  private static final ResourceKey<Biome> SIRACUSA_RAINY_WOODLAND = Zinecraft.INSTANCE
      .getBIOMES()
      .register("siracusa_rainy_woodland", NationBiomes::SIRACUSA_RAINY_WOODLANDHelper0);
  @NotNull
  private static final ResourceKey<Biome> YAN_MOUNTAIN_GROVE = Zinecraft.INSTANCE
      .getBIOMES()
      .register("yan_mountain_grove", NationBiomes::YAN_MOUNTAIN_GROVEHelper0);
  @NotNull
  private static final ResourceKey<Biome> IBERIA_SALT_DELTA = Zinecraft.INSTANCE
      .getBIOMES()
      .register("iberia_salt_delta", NationBiomes::IBERIA_SALT_DELTAHelper0);
  @NotNull
  private static final ResourceKey<Biome> TERRA_CATASTROPHE_ZONE = Zinecraft.INSTANCE
      .getBIOMES()
      .register("terra_catastrophe_zone", NationBiomes::TERRA_CATASTROPHE_ZONEHelper0);

  static {
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.aegir_abyssal_sea", "阿戈尔深海", "Aegir Abyssal Sea");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.bolivar_plain", "玻利瓦尔平原", "Bolivar Plain");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.higashi_shadow_rift", "东国常暗裂谷", "Higashi Shadow Rift");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.durin_underground_garden", "杜林地下花园", "Durin Underground Garden");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.columbia_sandstone_wilds", "哥伦比亚砂岩荒野", "Columbia Sandstone Wilds");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.kazimierz_knightland", "卡西米尔骑士领", "Kazimierz Knightland");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.kazdel_scarred_wastes", "卡兹戴尔伤痕荒地", "Kazdel Scarred Wastes");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.laterano_holy_fields", "拉特兰圣田", "Laterano Holy Fields");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.leithanien_twilight_forest", "莱塔尼亚暮色林", "Leithanien Twilight Forest");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.rim_billiton_mining_badlands", "雷姆必拓矿业荒地", "Rim Billiton Mining Badlands");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.minos_sunlit_hills", "米诺斯日照丘陵", "Minos Sunlit Hills");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.sargon_rocky_desert", "萨尔贡岩漠", "Sargon Rocky Desert");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.sami_frozen_forest", "萨米冻林", "Sami Frozen Forest");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.victoria_misty_highlands", "维多利亚雾岭", "Victoria Misty Highlands");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.ursus_frozen_steppe", "乌萨斯冻原", "Ursus Frozen Steppe");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.kjerag_snowy_peaks", "谢拉格雪峰", "Kjerag Snowy Peaks");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.siracusa_rainy_woodland", "叙拉古雨林", "Siracusa Rainy Woodland");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.yan_mountain_grove", "炎国山林", "Yan Mountain Grove");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.iberia_salt_delta", "伊比利亚盐风三角洲", "Iberia Salt Delta");
    Zinecraft.INSTANCE.getTRANSLATIONS().add("biome.zinecraft.terra_catastrophe_zone", "泰拉天灾区", "Terra Catastrophe Zone");
  }

  private NationBiomes() {
  }

  private static void AEGIR_ABYSSAL_SEAHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(0.4F);
    _this_register.setDownfall(1.0F);
    _this_register.setWaterColor(1523551);
    _this_register.setWaterFogColor(465964);
    _this_register.setFogColor(5401986);
    NationBiomePresets.INSTANCE.ocean(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.WATER_CREATURE;
    EntityType entityType = EntityType.DOLPHIN;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 8, 1, 2);
    return;
  }

  private static void BOLIVAR_PLAINHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.9F);
    _this_register.setDownfall(0.35F);
    _this_register.setGrassColor(10201685);
    _this_register.setFoliageColor(8885319);
    NationBiomePresets.INSTANCE.plains(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.PARROT;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 12, 1, 2);
    return;
  }

  private static void HIGASHI_SHADOW_RIFTHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.6F);
    _this_register.setDownfall(0.75F);
    _this_register.setFogColor(6910074);
    _this_register.setGrassColor(5338204);
    _this_register.setFoliageColor(4088909);
    NationBiomePresets.INSTANCE.mountain(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.FOX;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 10, 1, 2);
    return;
  }

  private static void DURIN_UNDERGROUND_GARDENHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(1.0F);
    _this_register.setDownfall(0.8F);
    _this_register.setWaterColor(3516320);
    _this_register.setFogColor(9072552);
    _this_register.setGrassColor(5613672);
    NationBiomePresets.INSTANCE.cavern(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.UNDERGROUND_WATER_CREATURE;
    EntityType entityType = EntityType.GLOW_SQUID;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 16, 2, 4);
    return;
  }

  private static void COLUMBIA_SANDSTONE_WILDSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(1.4F);
    _this_register.setDownfall(0.1F);
    _this_register.setGrassColor(10189641);
    _this_register.setFoliageColor(8743742);
    NationBiomePresets.INSTANCE.badlands(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.ARMADILLO;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 14, 1, 3);
    return;
  }

  private static void KAZIMIERZ_KNIGHTLANDHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.75F);
    _this_register.setDownfall(0.35F);
    _this_register.setGrassColor(9416530);
    _this_register.setFoliageColor(7312197);
    NationBiomePresets.INSTANCE.plains(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.HORSE;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 18, 2, 5);
    return;
  }

  private static void KAZDEL_SCARRED_WASTESHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(0.8F);
    _this_register.setDownfall(0.05F);
    _this_register.setFogColor(6445666);
    _this_register.setGrassColor(6642512);
    _this_register.setFoliageColor(5590341);
    NationBiomePresets.INSTANCE.badlands(_this_register);
    return;
  }

  private static void LATERANO_HOLY_FIELDSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.9F);
    _this_register.setDownfall(0.5F);
    _this_register.setGrassColor(11058793);
    _this_register.setFoliageColor(9546332);
    _this_register.setFogColor(14276295);
    NationBiomePresets.INSTANCE.plains(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.BEE;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 16, 2, 4);
    return;
  }

  private static void LEITHANIEN_TWILIGHT_FORESTHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.55F);
    _this_register.setDownfall(0.8F);
    _this_register.setFogColor(9077147);
    _this_register.setGrassColor(5796684);
    _this_register.setFoliageColor(4218175);
    NationBiomePresets.INSTANCE.forest(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.WOLF;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 12, 2, 4);
    return;
  }

  private static void RIM_BILLITON_MINING_BADLANDSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(1.5F);
    _this_register.setDownfall(0.1F);
    _this_register.setGrassColor(10122311);
    _this_register.setFoliageColor(8741438);
    NationBiomePresets.INSTANCE.badlands(_this_register);
    return;
  }

  private static void MINOS_SUNLIT_HILLSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(1.1F);
    _this_register.setDownfall(0.35F);
    _this_register.setGrassColor(10926170);
    _this_register.setFoliageColor(8559437);
    NationBiomePresets.INSTANCE.mountain(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.GOAT;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 14, 2, 4);
    return;
  }

  private static void SARGON_ROCKY_DESERTHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(2.0F);
    _this_register.setDownfall(0.0F);
    _this_register.setFogColor(14070909);
    NationBiomePresets.INSTANCE.desert(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.CAMEL;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 12, 1, 2);
    return;
  }

  private static void SAMI_FROZEN_FORESTHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(-0.5F);
    _this_register.setDownfall(0.8F);
    _this_register.setFogColor(12176339);
    _this_register.setGrassColor(7176824);
    _this_register.setFoliageColor(5796198);
    NationBiomePresets.INSTANCE.snowyForest(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.POLAR_BEAR;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 10, 1, 2);
    return;
  }

  private static void VICTORIA_MISTY_HIGHLANDSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.65F);
    _this_register.setDownfall(0.9F);
    _this_register.setFogColor(11054512);
    _this_register.setGrassColor(6718554);
    _this_register.setFoliageColor(5600076);
    NationBiomePresets.INSTANCE.mountain(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.SHEEP;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 18, 2, 4);
    return;
  }

  private static void URSUS_FROZEN_STEPPEHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(-0.2F);
    _this_register.setDownfall(0.3F);
    _this_register.setFogColor(12700624);
    _this_register.setGrassColor(8687754);
    _this_register.setFoliageColor(7438456);
    NationBiomePresets.INSTANCE.snowyForest(_this_register);
    return;
  }

  private static void KJERAG_SNOWY_PEAKSHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(-0.7F);
    _this_register.setDownfall(0.7F);
    _this_register.setFogColor(14016999);
    _this_register.setGrassColor(7901322);
    _this_register.setFoliageColor(6454645);
    NationBiomePresets.INSTANCE.snowyForest(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.RABBIT;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 14, 2, 3);
    return;
  }

  private static void SIRACUSA_RAINY_WOODLANDHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.8F);
    _this_register.setDownfall(0.95F);
    _this_register.setFogColor(9213584);
    _this_register.setGrassColor(4683086);
    _this_register.setFoliageColor(3761215);
    NationBiomePresets.INSTANCE.rainyForest(_this_register);
    return;
  }

  private static void YAN_MOUNTAIN_GROVEHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(0.7F);
    _this_register.setDownfall(0.8F);
    _this_register.setWaterColor(4159141);
    _this_register.setGrassColor(6329947);
    _this_register.setFoliageColor(4882764);
    NationBiomePresets.INSTANCE.mountain(_this_register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.PANDA;
    nationBiomePresets.featuredSpawn(_this_register, mobCategory, entityType, 12, 1, 2);
    return;
  }

  private static void IBERIA_SALT_DELTAHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setTemperature(1.0F);
    _this_register.setDownfall(0.8F);
    _this_register.setWaterColor(3043719);
    _this_register.setWaterFogColor(1457482);
    _this_register.setFogColor(11450548);
    _this_register.setGrassColor(7638130);
    _this_register.setFoliageColor(6519653);
    NationBiomePresets.INSTANCE.wetland(_this_register);
    return;
  }

  /**
   * 天灾区：干燥、低植被的源石污染地带，地表晶簇由泰拉限定地物密集覆盖。
   */
  private static void TERRA_CATASTROPHE_ZONEHelper0(SimpleBiomeBuilder _this_register) {
    _this_register.setPrecipitation(false);
    _this_register.setTemperature(1.2F);
    _this_register.setDownfall(0.0F);
    _this_register.setFogColor(0x51405F);
    _this_register.setGrassColor(0x5B514B);
    _this_register.setFoliageColor(0x51453F);
    NationBiomePresets.INSTANCE.badlands(_this_register);
    return;
  }

  @NotNull
  public final ResourceKey<Biome> getAEGIR_ABYSSAL_SEA() {
    return AEGIR_ABYSSAL_SEA;
  }

  @NotNull
  public final ResourceKey<Biome> getBOLIVAR_PLAIN() {
    return BOLIVAR_PLAIN;
  }

  @NotNull
  public final ResourceKey<Biome> getHIGASHI_SHADOW_RIFT() {
    return HIGASHI_SHADOW_RIFT;
  }

  @NotNull
  public final ResourceKey<Biome> getDURIN_UNDERGROUND_GARDEN() {
    return DURIN_UNDERGROUND_GARDEN;
  }

  @NotNull
  public final ResourceKey<Biome> getCOLUMBIA_SANDSTONE_WILDS() {
    return COLUMBIA_SANDSTONE_WILDS;
  }

  @NotNull
  public final ResourceKey<Biome> getKAZIMIERZ_KNIGHTLAND() {
    return KAZIMIERZ_KNIGHTLAND;
  }

  @NotNull
  public final ResourceKey<Biome> getKAZDEL_SCARRED_WASTES() {
    return KAZDEL_SCARRED_WASTES;
  }

  @NotNull
  public final ResourceKey<Biome> getLATERANO_HOLY_FIELDS() {
    return LATERANO_HOLY_FIELDS;
  }

  @NotNull
  public final ResourceKey<Biome> getLEITHANIEN_TWILIGHT_FOREST() {
    return LEITHANIEN_TWILIGHT_FOREST;
  }

  @NotNull
  public final ResourceKey<Biome> getRIM_BILLITON_MINING_BADLANDS() {
    return RIM_BILLITON_MINING_BADLANDS;
  }

  @NotNull
  public final ResourceKey<Biome> getMINOS_SUNLIT_HILLS() {
    return MINOS_SUNLIT_HILLS;
  }

  @NotNull
  public final ResourceKey<Biome> getSARGON_ROCKY_DESERT() {
    return SARGON_ROCKY_DESERT;
  }

  @NotNull
  public final ResourceKey<Biome> getSAMI_FROZEN_FOREST() {
    return SAMI_FROZEN_FOREST;
  }

  @NotNull
  public final ResourceKey<Biome> getVICTORIA_MISTY_HIGHLANDS() {
    return VICTORIA_MISTY_HIGHLANDS;
  }

  @NotNull
  public final ResourceKey<Biome> getURSUS_FROZEN_STEPPE() {
    return URSUS_FROZEN_STEPPE;
  }

  @NotNull
  public final ResourceKey<Biome> getKJERAG_SNOWY_PEAKS() {
    return KJERAG_SNOWY_PEAKS;
  }

  @NotNull
  public final ResourceKey<Biome> getSIRACUSA_RAINY_WOODLAND() {
    return SIRACUSA_RAINY_WOODLAND;
  }

  @NotNull
  public final ResourceKey<Biome> getYAN_MOUNTAIN_GROVE() {
    return YAN_MOUNTAIN_GROVE;
  }

  @NotNull
  public final ResourceKey<Biome> getIBERIA_SALT_DELTA() {
    return IBERIA_SALT_DELTA;
  }

  @NotNull
  public final ResourceKey<Biome> getTERRA_CATASTROPHE_ZONE() {
    return TERRA_CATASTROPHE_ZONE;
  }

  /**
   * Every biome emitted by TerraBiomeSource, including the non-national catastrophe zone.
   */
  @NotNull
  public final List<ResourceKey<Biome>> getALL_TERRA_BIOMES() {
    return List.of(
        AEGIR_ABYSSAL_SEA, BOLIVAR_PLAIN, HIGASHI_SHADOW_RIFT, DURIN_UNDERGROUND_GARDEN,
        COLUMBIA_SANDSTONE_WILDS, KAZIMIERZ_KNIGHTLAND, KAZDEL_SCARRED_WASTES,
        LATERANO_HOLY_FIELDS, LEITHANIEN_TWILIGHT_FOREST, RIM_BILLITON_MINING_BADLANDS,
        MINOS_SUNLIT_HILLS, SARGON_ROCKY_DESERT, SAMI_FROZEN_FOREST,
        VICTORIA_MISTY_HIGHLANDS, URSUS_FROZEN_STEPPE, KJERAG_SNOWY_PEAKS,
        SIRACUSA_RAINY_WOODLAND, YAN_MOUNTAIN_GROVE, IBERIA_SALT_DELTA,
        TERRA_CATASTROPHE_ZONE
    );
  }
}
