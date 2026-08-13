package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.Zinecraft
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.sounds.Musics
import net.minecraft.sounds.SoundEvents

object ModBiome {
  val EXAMPLE_BIOME = Zinecraft.BIOMES.register("example_biome") {
    precipitation = false
    temperature = 2.0f
    downfall = 0.0f
    music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT)

    BiomeDefaultFeatures.desertSpawns(spawns)
    defaultOverworldGeneration()
    BiomeDefaultFeatures.addFossilDecoration(generation)
    BiomeDefaultFeatures.addDefaultOres(generation)
    BiomeDefaultFeatures.addDefaultSoftDisks(generation)
    BiomeDefaultFeatures.addDefaultFlowers(generation)
    BiomeDefaultFeatures.addDefaultGrass(generation)
    BiomeDefaultFeatures.addDesertVegetation(generation)
    BiomeDefaultFeatures.addDefaultMushrooms(generation)
    BiomeDefaultFeatures.addDesertExtraVegetation(generation)
    BiomeDefaultFeatures.addDesertExtraDecoration(generation)
  }
}
