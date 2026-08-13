package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.api.world.biome.SimpleBiomeBuilder
import net.minecraft.data.worldgen.BiomeDefaultFeatures
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.MobSpawnSettings

/** 泰拉国家群系共用的原版地物与刷怪预设。 */
internal object NationBiomePresets {
  /** 为国家群系追加一种具有辨识度的原版生物。 */
  fun featuredSpawn(
    biome: SimpleBiomeBuilder,
    category: MobCategory,
    type: EntityType<*>,
    weight: Int,
    minCount: Int = 1,
    maxCount: Int = minCount
  ) {
    require(weight > 0) { "特色生物生成权重必须大于 0" }
    require(minCount > 0 && maxCount >= minCount) { "特色生物群体数量无效" }
    biome.spawns.addSpawn(category, MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount))
  }

  fun plains(biome: SimpleBiomeBuilder) = with(biome) {
    commonBase()
    BiomeDefaultFeatures.addPlainVegetation(generation)
    BiomeDefaultFeatures.addPlainGrass(generation)
  }

  fun forest(biome: SimpleBiomeBuilder) = with(biome) {
    commonBase()
    BiomeDefaultFeatures.addBirchTrees(generation)
    BiomeDefaultFeatures.addForestFlowers(generation)
    BiomeDefaultFeatures.addForestGrass(generation)
  }

  fun rainyForest(biome: SimpleBiomeBuilder) = with(biome) {
    commonBase()
    BiomeDefaultFeatures.addTallBirchTrees(generation)
    BiomeDefaultFeatures.addForestFlowers(generation)
    BiomeDefaultFeatures.addFerns(generation)
    BiomeDefaultFeatures.addForestGrass(generation)
  }

  fun mountain(biome: SimpleBiomeBuilder) = with(biome) {
    commonBase()
    BiomeDefaultFeatures.addMountainTrees(generation)
    BiomeDefaultFeatures.addMeadowVegetation(generation)
    BiomeDefaultFeatures.addExtraEmeralds(generation)
  }

  fun snowyForest(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.snowySpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addSnowyTrees(generation)
    BiomeDefaultFeatures.addTaigaGrass(generation)
    BiomeDefaultFeatures.addCommonBerryBushes(generation)
  }

  fun desert(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.desertSpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addDesertVegetation(generation)
    BiomeDefaultFeatures.addDesertExtraVegetation(generation)
    BiomeDefaultFeatures.addDesertExtraDecoration(generation)
    BiomeDefaultFeatures.addFossilDecoration(generation)
  }

  fun badlands(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.desertSpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addBadlandsTrees(generation)
    BiomeDefaultFeatures.addBadlandGrass(generation)
    BiomeDefaultFeatures.addBadlandExtraVegetation(generation)
    BiomeDefaultFeatures.addExtraGold(generation)
  }

  fun jungle(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.baseJungleSpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addJungleTrees(generation)
    BiomeDefaultFeatures.addJungleGrass(generation)
    BiomeDefaultFeatures.addJungleVines(generation)
    BiomeDefaultFeatures.addJungleMelons(generation)
  }

  fun wetland(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.commonSpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addSwampClayDisk(generation)
    BiomeDefaultFeatures.addSwampVegetation(generation)
    BiomeDefaultFeatures.addSwampExtraVegetation(generation)
  }

  fun ocean(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.oceanSpawns(spawns, 3, 4, 15)
    generationBase()
    BiomeDefaultFeatures.addDefaultSeagrass(generation)
    BiomeDefaultFeatures.addColdOceanExtraVegetation(generation)
  }

  fun cavern(biome: SimpleBiomeBuilder) = with(biome) {
    BiomeDefaultFeatures.caveSpawns(spawns)
    generationBase()
    BiomeDefaultFeatures.addDripstone(generation)
    BiomeDefaultFeatures.addLushCavesVegetationFeatures(generation)
    BiomeDefaultFeatures.addLushCavesSpecialOres(generation)
  }

  private fun SimpleBiomeBuilder.commonBase() {
    BiomeDefaultFeatures.plainsSpawns(spawns)
    generationBase()
  }

  private fun SimpleBiomeBuilder.generationBase() {
    defaultOverworldGeneration()
    BiomeDefaultFeatures.addDefaultOres(generation)
    BiomeDefaultFeatures.addDefaultSoftDisks(generation)
    BiomeDefaultFeatures.addDefaultMushrooms(generation)
    BiomeDefaultFeatures.addDefaultExtraVegetation(generation)
  }
}
