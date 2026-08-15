package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record OreEntry(
    ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
    ResourceKey<PlacedFeature> placedKey,
    Block block,
    int veinSize,
    int veinsPerChunk,
    int maxY,
    float discardChanceOnAirExposure,
    BiomeSelection biomes
) {
  public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredKey() {
    return configuredKey;
  }

  public ResourceKey<PlacedFeature> getPlacedKey() {
    return placedKey;
  }

  public Block getBlock() {
    return block;
  }

  public int getVeinSize() {
    return veinSize;
  }

  public int getVeinsPerChunk() {
    return veinsPerChunk;
  }

  public int getMaxY() {
    return maxY;
  }

  public float getDiscardChanceOnAirExposure() {
    return discardChanceOnAirExposure;
  }

  public BiomeSelection getBiomes() {
    return biomes;
  }
}
