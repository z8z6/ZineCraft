package com.cxxcxx.zinecraft.api.world.biome

import net.minecraft.core.HolderGetter
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.data.worldgen.biome.OverworldBiomes
import net.minecraft.sounds.Music
import net.minecraft.world.level.biome.*
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver
import net.minecraft.world.level.levelgen.placement.PlacedFeature

class SimpleBiomeBuilder(
  placedFeatures: HolderGetter<PlacedFeature>,
  carvers: HolderGetter<ConfiguredWorldCarver<*>>
) {
  var precipitation = true
  var temperature = 0.8f
  var downfall = 0.4f
  var waterColor = 4_159_204
  var waterFogColor = 329_011
  var fogColor = 12_638_463
  var skyColor: Int? = null
  var grassColor: Int? = null
  var foliageColor: Int? = null
  var music: Music? = null

  val spawns = MobSpawnSettings.Builder()
  val generation = BiomeGenerationSettings.Builder(placedFeatures, carvers)

  fun defaultOverworldGeneration() {
    BiomeDefaultFeatures.addDefaultCarversAndLakes(generation)
    BiomeDefaultFeatures.addDefaultCrystalFormations(generation)
    BiomeDefaultFeatures.addDefaultMonsterRoom(generation)
    BiomeDefaultFeatures.addDefaultUndergroundVariety(generation)
    BiomeDefaultFeatures.addDefaultSprings(generation)
    BiomeDefaultFeatures.addSurfaceFreezing(generation)
  }

  fun build(): Biome {
    val effects = BiomeSpecialEffects.Builder()
      .waterColor(waterColor)
      .waterFogColor(waterFogColor)
      .fogColor(fogColor)
      .skyColor(skyColor ?: OverworldBiomes.calculateSkyColor(temperature))
      .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
      .backgroundMusic(music)
    grassColor?.let(effects::grassColorOverride)
    foliageColor?.let(effects::foliageColorOverride)
    return Biome.BiomeBuilder()
      .hasPrecipitation(precipitation)
      .temperature(temperature)
      .downfall(downfall)
      .specialEffects(effects.build())
      .mobSpawnSettings(spawns.build())
      .generationSettings(generation.build())
      .build()
  }
}
