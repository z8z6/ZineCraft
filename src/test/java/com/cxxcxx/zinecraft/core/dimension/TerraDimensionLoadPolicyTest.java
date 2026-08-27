package com.cxxcxx.zinecraft.core.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.LevelStem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerraDimensionLoadPolicyTest {
  private static final ResourceKey<LevelStem> TERRA = levelStem("zinecraft", "terra");
  private static final ResourceKey<LevelStem> OTHER = levelStem("example", "other");

  @Test
  void flatWorldOnlyExcludesTerra() {
    assertFalse(TerraDimensionLoadPolicy.shouldLoadLevelStem(TERRA, true));
    assertTrue(TerraDimensionLoadPolicy.shouldLoadLevelStem(OTHER, true));
  }

  @Test
  void normalWorldKeepsTerra() {
    assertTrue(TerraDimensionLoadPolicy.shouldLoadLevelStem(TERRA, false));
  }

  @Test
  void flatWorldExcludesOnlyZinecraftNaturalStructures() {
    assertFalse(TerraDimensionLoadPolicy.shouldGenerateStructure(
        true, ResourceLocation.fromNamespaceAndPath("zinecraft", "stargate")
    ));
    assertTrue(TerraDimensionLoadPolicy.shouldGenerateStructure(
        true, ResourceLocation.fromNamespaceAndPath("minecraft", "village_plains")
    ));
    assertTrue(TerraDimensionLoadPolicy.shouldGenerateStructure(
        false, ResourceLocation.fromNamespaceAndPath("zinecraft", "stargate")
    ));
  }

  private static ResourceKey<LevelStem> levelStem(String namespace, String path) {
    return ResourceKey.create(
        Registries.LEVEL_STEM,
        ResourceLocation.fromNamespaceAndPath(namespace, path)
    );
  }
}