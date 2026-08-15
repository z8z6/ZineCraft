package com.cxxcxx.zinecraft.api.world.structure;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public final class JigsawBuildingEntry {
  @NotNull
  private final ResourceKey<StructureProcessorList> processorKey;
  @NotNull
  private final Map<String, ResourceKey<StructureTemplatePool>> poolKeys;
  @NotNull
  private final String startPool;
  @NotNull
  private final ResourceKey<Structure> structureKey;
  @NotNull
  private final ResourceKey<StructureSet> setKey;
  @NotNull
  private final List<JigsawPoolDefinition> pools;
  private final int spacing;
  private final int separation;
  private final int salt;
  private final int size;
  private final int maxDistanceFromCenter;
  private final float removeVinesChance;
  @Nullable
  private final ResourceKey<Biome> biome;
  private final boolean unique;
  private final int ringDistance;
  @Nullable
  private final Types heightmap;
  private final int startHeight;
  private final boolean useExpansionHack;
  private final boolean fixedOrigin;
  @NotNull
  private final Decoration generationStep;
  @NotNull
  private final TerrainAdjustment terrainAdjustment;

  public JigsawBuildingEntry(
      @NotNull ResourceKey<StructureProcessorList> processorKey,
      @NotNull Map<String, ? extends ResourceKey<StructureTemplatePool>> poolKeys,
      @NotNull String startPool,
      @NotNull ResourceKey<Structure> structureKey,
      @NotNull ResourceKey<StructureSet> setKey,
      @NotNull List<JigsawPoolDefinition> pools,
      int spacing,
      int separation,
      int salt,
      int size,
      int maxDistanceFromCenter,
      float removeVinesChance,
      @Nullable ResourceKey<Biome> biome,
      boolean unique,
      int ringDistance,
      @Nullable Types heightmap,
      int startHeight,
      boolean useExpansionHack,
      boolean fixedOrigin,
      @NotNull Decoration generationStep,
      @NotNull TerrainAdjustment terrainAdjustment
  ) {
    super();
    this.processorKey = processorKey;
    this.poolKeys = new java.util.LinkedHashMap<>(poolKeys);
    this.startPool = startPool;
    this.structureKey = structureKey;
    this.setKey = setKey;
    this.pools = pools;
    this.spacing = spacing;
    this.separation = separation;
    this.salt = salt;
    this.size = size;
    this.maxDistanceFromCenter = maxDistanceFromCenter;
    this.removeVinesChance = removeVinesChance;
    this.biome = biome;
    this.unique = unique;
    this.ringDistance = ringDistance;
    this.heightmap = heightmap;
    this.startHeight = startHeight;
    this.useExpansionHack = useExpansionHack;
    this.fixedOrigin = fixedOrigin;
    this.generationStep = generationStep;
    this.terrainAdjustment = terrainAdjustment;
  }

  @NotNull
  public final ResourceKey<StructureProcessorList> getProcessorKey() {
    return this.processorKey;
  }

  @NotNull
  public final Map<String, ResourceKey<StructureTemplatePool>> getPoolKeys() {
    return this.poolKeys;
  }

  @NotNull
  public final String getStartPool() {
    return this.startPool;
  }

  @NotNull
  public final ResourceKey<Structure> getStructureKey() {
    return this.structureKey;
  }

  @NotNull
  public final ResourceKey<StructureSet> getSetKey() {
    return this.setKey;
  }

  @NotNull
  public final List<JigsawPoolDefinition> getPools() {
    return this.pools;
  }

  public final int getSpacing() {
    return this.spacing;
  }

  public final int getSeparation() {
    return this.separation;
  }

  public final int getSalt() {
    return this.salt;
  }

  public final int getSize() {
    return this.size;
  }

  public final int getMaxDistanceFromCenter() {
    return this.maxDistanceFromCenter;
  }

  public final float getRemoveVinesChance() {
    return this.removeVinesChance;
  }

  @Nullable
  public final ResourceKey<Biome> getBiome() {
    return this.biome;
  }

  public final boolean getUnique() {
    return this.unique;
  }

  public final int getRingDistance() {
    return this.ringDistance;
  }

  @Nullable
  public final Types getHeightmap() {
    return this.heightmap;
  }

  public final int getStartHeight() {
    return this.startHeight;
  }

  public final boolean getUseExpansionHack() {
    return this.useExpansionHack;
  }

  public final boolean getFixedOrigin() {
    return this.fixedOrigin;
  }

  @NotNull
  public final Decoration getGenerationStep() {
    return this.generationStep;
  }

  @NotNull
  public final TerrainAdjustment getTerrainAdjustment() {
    return this.terrainAdjustment;
  }
}

