package com.cxxcxx.zinecraft.core.biome

import net.minecraft.core.HolderGetter
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.data.worldgen.placement.EndPlacements
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

object EndBiome {
  // 从原版 EndBiomes 复制
  // 末地群系构造函数，不要修改，参数是固定的
  private fun baseEndBiome(builder: BiomeGenerationSettings.Builder): Biome {
    val builder2 = MobSpawnSettings.Builder()
    BiomeDefaultFeatures.endSpawns(builder2)
    return Biome.BiomeBuilder()
      .hasPrecipitation(false)
      .temperature(0.5f)
      .downfall(0.5f)
      .specialEffects(
        BiomeSpecialEffects.Builder()
          .waterColor(4159204)
          .waterFogColor(329011)
          .fogColor(10518688)
          .skyColor(0)
          .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
          .build()
      )
      .mobSpawnSettings(builder2.build())
      .generationSettings(builder.build())
      .build()
  }

  // 从原版 EndBiomes 复制
  // 示例群系，末地主岛
  fun theEnd(
    holderGetter: HolderGetter<PlacedFeature?>,
    holderGetter2: HolderGetter<ConfiguredWorldCarver<*>?>
  ): Biome {
    val builder = BiomeGenerationSettings.Builder(holderGetter, holderGetter2)
      .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_SPIKE)
      .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, EndPlacements.END_PLATFORM)
    return baseEndBiome(builder)
  }

}