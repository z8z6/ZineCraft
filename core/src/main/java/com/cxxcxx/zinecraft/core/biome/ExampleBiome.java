package com.cxxcxx.zinecraft.core.biome;


import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.Nullable;

public class ExampleBiome {
  // 一个辅助的函数用于根据温度判断段生物群系的天空的颜色
  protected static int calculateSkyColor(float pTemperature) {
    float $$1 = pTemperature / 3.0F;
    $$1 = Mth.clamp($$1, -1.0F, 1.0F);
    return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
  }

  // 一个构造方法 用于返回一个biome实例
  private static Biome ExampleOverworldBiomes(
      boolean pHasPercipitation,// 是否有降水
      float pTemperature,// 温度
      float pDownfall, // 降水量
      MobSpawnSettings.Builder pMobSpawnSettings,// 生物群系生物生成设置
      BiomeGenerationSettings.Builder pGenerationSettings,// 生物群系生成设置
      @Nullable Music pBackgroundMusic // 生物群系背景音乐
  ) {
    return ExampleOverworldBiomes(pHasPercipitation, pTemperature, pDownfall, 4159204, 329011, null, null, pMobSpawnSettings, pGenerationSettings, pBackgroundMusic);
  }

  // 另一个构造方法 同样返回bioome 能配置的更多
  private static Biome ExampleOverworldBiomes(
      boolean pHasPrecipitation,
      float pTemperature,// 温度
      float pDownfall,
      int pWaterColor, // 水的颜色
      int pWaterFogColor, // 水的雾颜色
      Integer pGrassColorOverride, // 草方块颜色
      Integer pFoliageColorOverride, // 树叶颜色
      MobSpawnSettings.Builder pMobSpawnSettings,
      BiomeGenerationSettings.Builder pGenerationSettings,
      Music pBackgroundMusic
  ) {
    BiomeSpecialEffects.Builder biomespecialeffects$builder = new BiomeSpecialEffects.Builder()
        .waterColor(pWaterColor)
        .waterFogColor(pWaterFogColor)
        .fogColor(12638463)
        .skyColor(calculateSkyColor(pTemperature))
        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
        .backgroundMusic(pBackgroundMusic);
    if (pGrassColorOverride != null) {
      biomespecialeffects$builder.grassColorOverride(pGrassColorOverride);
    }

    if (pFoliageColorOverride != null) {
      biomespecialeffects$builder.foliageColorOverride(pFoliageColorOverride);
    }

    return new Biome.BiomeBuilder()
        .hasPrecipitation(pHasPrecipitation)
        .temperature(pTemperature)
        .downfall(pDownfall)
        .specialEffects(biomespecialeffects$builder.build())
        .mobSpawnSettings(pMobSpawnSettings.build())
        .generationSettings(pGenerationSettings.build())
        .build();
  }

  // 添加一个自己的生物群系，使用了一些原版的方法。具体的内容自己点到方法里面看下把，不是很难，都是一些重复的配置的内容。
  public static Biome exampleBiome(
      HolderGetter<PlacedFeature> pPlacedFeatures,
      HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
    // 创建一个MobSpawnSettings.Builder对象，用于配置生物生成设置。
    MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
    // 配置沙漠生成的生物。
    BiomeDefaultFeatures.desertSpawns(mobspawnsettings$builder);
    // 用于配置生物群系生成设置。
    BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
    // 添加化石装饰物到生物群系生成设置中
    BiomeDefaultFeatures.addFossilDecoration(biomegenerationsettings$builder);
    // 一些通用的配置
    globalOverworldGeneration(biomegenerationsettings$builder);
    // 矿物
    BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDesertVegetation(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDesertExtraVegetation(biomegenerationsettings$builder);
    BiomeDefaultFeatures.addDesertExtraDecoration(biomegenerationsettings$builder);
    // 调用构造返回biome
    return ExampleOverworldBiomes(false, 2.0F, 0.0F, mobspawnsettings$builder, biomegenerationsettings$builder, Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT));
  }

  private static void globalOverworldGeneration(BiomeGenerationSettings.Builder pGenerationSettings) {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(pGenerationSettings);
    BiomeDefaultFeatures.addDefaultCrystalFormations(pGenerationSettings);
    BiomeDefaultFeatures.addDefaultMonsterRoom(pGenerationSettings);
    BiomeDefaultFeatures.addDefaultUndergroundVariety(pGenerationSettings);
    BiomeDefaultFeatures.addDefaultSprings(pGenerationSettings);
    BiomeDefaultFeatures.addSurfaceFreezing(pGenerationSettings);
  }

}
