package com.cxxcxx.zinecraft.core.biome

import net.minecraft.core.HolderGetter
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.data.worldgen.biome.OverworldBiomes
import net.minecraft.sounds.Music
import net.minecraft.sounds.Musics
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object OverworldBiome {
  // 从原版 OverworldBiomes 复制
  // 根据温度判断段生物群系的天空的颜色
  fun calculateSkyColor(temperature: Float): Int {
    return OverworldBiomes.calculateSkyColor(temperature)
  }

  // 从原版 OverworldBiomes 复制
  // 主世界群系构造函数，不要修改，参数是固定的
  private fun biome(
    HasPercipitation: Boolean,                           // 是否有降水
    Temperature: Float,                                  // 温度
    Downfall: Float,                                     // 降水量
    MobSpawnSettings: MobSpawnSettings.Builder,          // 生物群系生物生成设置
    GenerationSettings: BiomeGenerationSettings.Builder, // 生物群系生成设置
    BackgroundMusic: Music?                              // 生物群系背景音乐
  ): Biome {
    return biome(
      HasPercipitation,
      Temperature,
      Downfall,
      4159204,
      329011,
      null,
      null,
      MobSpawnSettings,
      GenerationSettings,
      BackgroundMusic
    )
  }

  // 从原版 OverworldBiomes 复制
  // 主世界群系构造函数
  private fun biome(
    HasPrecipitation: Boolean,    // 是否降水
    Temperature: Float,           // 温度
    Downfall: Float,              // 降雨量
    WaterColor: Int,              // 水的颜色
    WaterFogColor: Int,           // 水的雾颜色
    GrassColorOverride: Int?,     // 草方块颜色
    FoliageColorOverride: Int?,   // 树叶颜色
    MobSpawnSettings: MobSpawnSettings.Builder,
    GenerationSettings: BiomeGenerationSettings.Builder,
    BackgroundMusic: Music?
  ): Biome {
    val specialEffect = BiomeSpecialEffects.Builder()
      .waterColor(WaterColor)
      .waterFogColor(WaterFogColor)
      .fogColor(12638463)
      .skyColor(calculateSkyColor(Temperature))
      .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
      .backgroundMusic(BackgroundMusic)

    GrassColorOverride?.let { specialEffect.grassColorOverride(it) }
    FoliageColorOverride?.let { specialEffect.foliageColorOverride(it) }

    return Biome.BiomeBuilder()
      .hasPrecipitation(HasPrecipitation)
      .temperature(Temperature)
      .downfall(Downfall)
      .specialEffects(specialEffect.build())
      .mobSpawnSettings(MobSpawnSettings.build())
      .generationSettings(GenerationSettings.build())
      .build()
  }

  // 从原版 OverworldBiomes 复制
  // 默认的生成属性
  private fun globalOverworldGeneration(generationSettings: BiomeGenerationSettings.Builder) {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings)
    BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings)
    BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings)
    BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings)
    BiomeDefaultFeatures.addDefaultSprings(generationSettings)
    BiomeDefaultFeatures.addSurfaceFreezing(generationSettings)
  }

  // 主世界示例群系
  fun exampleBiome(
    pPlacedFeatures: HolderGetter<PlacedFeature>,
    pWorldCarvers: HolderGetter<ConfiguredWorldCarver<*>>
  ): Biome {

    val mobspawnsetting = MobSpawnSettings.Builder()
    // 配置沙漠生成的生物
    BiomeDefaultFeatures.desertSpawns(mobspawnsetting)

    val generationsetting = BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers)
    // 添加化石装饰物到生物群系生成设置中
    BiomeDefaultFeatures.addFossilDecoration(generationsetting)
    // 一些通用的配置
    globalOverworldGeneration(generationsetting)
    // 矿物
    BiomeDefaultFeatures.addDefaultOres(generationsetting)
    BiomeDefaultFeatures.addDefaultSoftDisks(generationsetting)
    BiomeDefaultFeatures.addDefaultFlowers(generationsetting)
    BiomeDefaultFeatures.addDefaultGrass(generationsetting)
    BiomeDefaultFeatures.addDesertVegetation(generationsetting)
    BiomeDefaultFeatures.addDefaultMushrooms(generationsetting)
    BiomeDefaultFeatures.addDesertExtraVegetation(generationsetting)
    BiomeDefaultFeatures.addDesertExtraDecoration(generationsetting)

    return biome(
      false,
      2.0f,
      0.0f,
      mobspawnsetting,
      generationsetting,
      Musics.createGameMusic(
        SoundEvents.MUSIC_BIOME_DESERT
      )
    )
  }
}