package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.api.world.biome.SimpleBiomeBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import kotlin.Unit;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class NationBiomes {
  @NotNull
  public static final NationBiomes INSTANCE = new NationBiomes();
  @NotNull
  private static final ResourceKey<Biome> AEGIR_ABYSSAL_SEA = Zinecraft.INSTANCE
      .getBIOMES()
      .register("aegir_abyssal_sea", NationBiomes::AEGIR_ABYSSAL_SEA$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> BOLIVAR_PLAIN = Zinecraft.INSTANCE.getBIOMES().register("bolivar_plain", NationBiomes::BOLIVAR_PLAIN$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> HIGASHI_SHADOW_RIFT = Zinecraft.INSTANCE
      .getBIOMES()
      .register("higashi_shadow_rift", NationBiomes::HIGASHI_SHADOW_RIFT$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> DURIN_UNDERGROUND_GARDEN = Zinecraft.INSTANCE
      .getBIOMES()
      .register("durin_underground_garden", NationBiomes::DURIN_UNDERGROUND_GARDEN$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> COLUMBIA_SANDSTONE_WILDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("columbia_sandstone_wilds", NationBiomes::COLUMBIA_SANDSTONE_WILDS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> KAZIMIERZ_KNIGHTLAND = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kazimierz_knightland", NationBiomes::KAZIMIERZ_KNIGHTLAND$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> KAZDEL_SCARRED_WASTES = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kazdel_scarred_wastes", NationBiomes::KAZDEL_SCARRED_WASTES$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> LATERANO_HOLY_FIELDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("laterano_holy_fields", NationBiomes::LATERANO_HOLY_FIELDS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> LEITHANIEN_TWILIGHT_FOREST = Zinecraft.INSTANCE
      .getBIOMES()
      .register("leithanien_twilight_forest", NationBiomes::LEITHANIEN_TWILIGHT_FOREST$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> RIM_BILLITON_MINING_BADLANDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("rim_billiton_mining_badlands", NationBiomes::RIM_BILLITON_MINING_BADLANDS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> MINOS_SUNLIT_HILLS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("minos_sunlit_hills", NationBiomes::MINOS_SUNLIT_HILLS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> SARGON_ROCKY_DESERT = Zinecraft.INSTANCE
      .getBIOMES()
      .register("sargon_rocky_desert", NationBiomes::SARGON_ROCKY_DESERT$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> SAMI_FROZEN_FOREST = Zinecraft.INSTANCE
      .getBIOMES()
      .register("sami_frozen_forest", NationBiomes::SAMI_FROZEN_FOREST$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> VICTORIA_MISTY_HIGHLANDS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("victoria_misty_highlands", NationBiomes::VICTORIA_MISTY_HIGHLANDS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> URSUS_FROZEN_STEPPE = Zinecraft.INSTANCE
      .getBIOMES()
      .register("ursus_frozen_steppe", NationBiomes::URSUS_FROZEN_STEPPE$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> KJERAG_SNOWY_PEAKS = Zinecraft.INSTANCE
      .getBIOMES()
      .register("kjerag_snowy_peaks", NationBiomes::KJERAG_SNOWY_PEAKS$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> SIRACUSA_RAINY_WOODLAND = Zinecraft.INSTANCE
      .getBIOMES()
      .register("siracusa_rainy_woodland", NationBiomes::SIRACUSA_RAINY_WOODLAND$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> YAN_MOUNTAIN_GROVE = Zinecraft.INSTANCE
      .getBIOMES()
      .register("yan_mountain_grove", NationBiomes::YAN_MOUNTAIN_GROVE$lambda$0);
  @NotNull
  private static final ResourceKey<Biome> IBERIA_SALT_DELTA = Zinecraft.INSTANCE
      .getBIOMES()
      .register("iberia_salt_delta", NationBiomes::IBERIA_SALT_DELTA$lambda$0);

  private NationBiomes() {
  }

  private static final Unit AEGIR_ABYSSAL_SEA$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(0.4F);
    $this$register.setDownfall(1.0F);
    $this$register.setWaterColor(1523551);
    $this$register.setWaterFogColor(465964);
    $this$register.setFogColor(5401986);
    NationBiomePresets.INSTANCE.ocean($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.WATER_CREATURE;
    EntityType entityType = EntityType.DOLPHIN;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 8, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit BOLIVAR_PLAIN$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.9F);
    $this$register.setDownfall(0.35F);
    $this$register.setGrassColor(10201685);
    $this$register.setFoliageColor(8885319);
    NationBiomePresets.INSTANCE.plains($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.PARROT;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 12, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit HIGASHI_SHADOW_RIFT$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.6F);
    $this$register.setDownfall(0.75F);
    $this$register.setFogColor(6910074);
    $this$register.setGrassColor(5338204);
    $this$register.setFoliageColor(4088909);
    NationBiomePresets.INSTANCE.mountain($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.FOX;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 10, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit DURIN_UNDERGROUND_GARDEN$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(1.0F);
    $this$register.setDownfall(0.8F);
    $this$register.setWaterColor(3516320);
    $this$register.setFogColor(9072552);
    $this$register.setGrassColor(5613672);
    NationBiomePresets.INSTANCE.cavern($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.UNDERGROUND_WATER_CREATURE;
    EntityType entityType = EntityType.GLOW_SQUID;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 16, 2, 4);
    return Unit.INSTANCE;
  }

  private static final Unit COLUMBIA_SANDSTONE_WILDS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(1.4F);
    $this$register.setDownfall(0.1F);
    $this$register.setGrassColor(10189641);
    $this$register.setFoliageColor(8743742);
    NationBiomePresets.INSTANCE.badlands($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.ARMADILLO;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 14, 1, 3);
    return Unit.INSTANCE;
  }

  private static final Unit KAZIMIERZ_KNIGHTLAND$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.75F);
    $this$register.setDownfall(0.35F);
    $this$register.setGrassColor(9416530);
    $this$register.setFoliageColor(7312197);
    NationBiomePresets.INSTANCE.plains($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.HORSE;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 18, 2, 5);
    return Unit.INSTANCE;
  }

  private static final Unit KAZDEL_SCARRED_WASTES$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(0.8F);
    $this$register.setDownfall(0.05F);
    $this$register.setFogColor(6445666);
    $this$register.setGrassColor(6642512);
    $this$register.setFoliageColor(5590341);
    NationBiomePresets.INSTANCE.badlands($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.MONSTER;
    EntityType entityType = EntityType.CAVE_SPIDER;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 30, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit LATERANO_HOLY_FIELDS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.9F);
    $this$register.setDownfall(0.5F);
    $this$register.setGrassColor(11058793);
    $this$register.setFoliageColor(9546332);
    $this$register.setFogColor(14276295);
    NationBiomePresets.INSTANCE.plains($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.BEE;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 16, 2, 4);
    return Unit.INSTANCE;
  }

  private static final Unit LEITHANIEN_TWILIGHT_FOREST$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.55F);
    $this$register.setDownfall(0.8F);
    $this$register.setFogColor(9077147);
    $this$register.setGrassColor(5796684);
    $this$register.setFoliageColor(4218175);
    NationBiomePresets.INSTANCE.forest($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.WOLF;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 12, 2, 4);
    return Unit.INSTANCE;
  }

  private static final Unit RIM_BILLITON_MINING_BADLANDS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(1.5F);
    $this$register.setDownfall(0.1F);
    $this$register.setGrassColor(10122311);
    $this$register.setFoliageColor(8741438);
    NationBiomePresets.INSTANCE.badlands($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.MONSTER;
    EntityType entityType = EntityType.HUSK;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 35, 1, 3);
    return Unit.INSTANCE;
  }

  private static final Unit MINOS_SUNLIT_HILLS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(1.1F);
    $this$register.setDownfall(0.35F);
    $this$register.setGrassColor(10926170);
    $this$register.setFoliageColor(8559437);
    NationBiomePresets.INSTANCE.mountain($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.GOAT;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 14, 2, 4);
    return Unit.INSTANCE;
  }

  private static final Unit SARGON_ROCKY_DESERT$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setPrecipitation(false);
    $this$register.setTemperature(2.0F);
    $this$register.setDownfall(0.0F);
    $this$register.setFogColor(14070909);
    NationBiomePresets.INSTANCE.desert($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.CAMEL;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 12, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit SAMI_FROZEN_FOREST$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(-0.5F);
    $this$register.setDownfall(0.8F);
    $this$register.setFogColor(12176339);
    $this$register.setGrassColor(7176824);
    $this$register.setFoliageColor(5796198);
    NationBiomePresets.INSTANCE.snowyForest($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.POLAR_BEAR;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 10, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit VICTORIA_MISTY_HIGHLANDS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.65F);
    $this$register.setDownfall(0.9F);
    $this$register.setFogColor(11054512);
    $this$register.setGrassColor(6718554);
    $this$register.setFoliageColor(5600076);
    NationBiomePresets.INSTANCE.mountain($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.SHEEP;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 18, 2, 4);
    return Unit.INSTANCE;
  }

  private static final Unit URSUS_FROZEN_STEPPE$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(-0.2F);
    $this$register.setDownfall(0.3F);
    $this$register.setFogColor(12700624);
    $this$register.setGrassColor(8687754);
    $this$register.setFoliageColor(7438456);
    NationBiomePresets.INSTANCE.snowyForest($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.MONSTER;
    EntityType entityType = EntityType.STRAY;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 40, 1, 3);
    return Unit.INSTANCE;
  }

  private static final Unit KJERAG_SNOWY_PEAKS$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(-0.7F);
    $this$register.setDownfall(0.7F);
    $this$register.setFogColor(14016999);
    $this$register.setGrassColor(7901322);
    $this$register.setFoliageColor(6454645);
    NationBiomePresets.INSTANCE.snowyForest($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.RABBIT;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 14, 2, 3);
    return Unit.INSTANCE;
  }

  private static final Unit SIRACUSA_RAINY_WOODLAND$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.8F);
    $this$register.setDownfall(0.95F);
    $this$register.setFogColor(9213584);
    $this$register.setGrassColor(4683086);
    $this$register.setFoliageColor(3761215);
    NationBiomePresets.INSTANCE.rainyForest($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.MONSTER;
    EntityType entityType = EntityType.SPIDER;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 45, 1, 3);
    return Unit.INSTANCE;
  }

  private static final Unit YAN_MOUNTAIN_GROVE$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(0.7F);
    $this$register.setDownfall(0.8F);
    $this$register.setWaterColor(4159141);
    $this$register.setGrassColor(6329947);
    $this$register.setFoliageColor(4882764);
    NationBiomePresets.INSTANCE.mountain($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.CREATURE;
    EntityType entityType = EntityType.PANDA;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 12, 1, 2);
    return Unit.INSTANCE;
  }

  private static final Unit IBERIA_SALT_DELTA$lambda$0(SimpleBiomeBuilder $this$register) {
    $this$register.setTemperature(1.0F);
    $this$register.setDownfall(0.8F);
    $this$register.setWaterColor(3043719);
    $this$register.setWaterFogColor(1457482);
    $this$register.setFogColor(11450548);
    $this$register.setGrassColor(7638130);
    $this$register.setFoliageColor(6519653);
    NationBiomePresets.INSTANCE.wetland($this$register);
    NationBiomePresets nationBiomePresets = NationBiomePresets.INSTANCE;
    MobCategory mobCategory = MobCategory.MONSTER;
    EntityType entityType = EntityType.DROWNED;
    nationBiomePresets.featuredSpawn($this$register, mobCategory, entityType, 35, 1, 2);
    return Unit.INSTANCE;
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
}

