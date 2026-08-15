package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public record SimpleFeatureEntry(
    String path,
    ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
    ResourceKey<PlacedFeature> placedKey,
    Feature<NoneFeatureConfiguration> feature,
    List<PlacementModifier> placement,
    GenerationStep.Decoration generationStep,
    BiomeSelection biomes
) {
  public String getPath() {
    return path;
  }

  public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredKey() {
    return configuredKey;
  }

  public ResourceKey<PlacedFeature> getPlacedKey() {
    return placedKey;
  }

  public Feature<NoneFeatureConfiguration> getFeature() {
    return feature;
  }

  public List<PlacementModifier> getPlacement() {
    return placement;
  }

  public GenerationStep.Decoration getGenerationStep() {
    return generationStep;
  }

  public BiomeSelection getBiomes() {
    return biomes;
  }
}
