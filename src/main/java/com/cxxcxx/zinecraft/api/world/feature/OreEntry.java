package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Supplier;

public record OreEntry(
    ResourceKey<ConfiguredFeature<?, ?>> configuredKey,
    ResourceKey<PlacedFeature> placedKey,
    Supplier<? extends Block> block,
    int veinSize,
    int veinsPerChunk,
    int maxY,
    float discardChanceOnAirExposure,
    BiomeSelection biomes
) {
}
