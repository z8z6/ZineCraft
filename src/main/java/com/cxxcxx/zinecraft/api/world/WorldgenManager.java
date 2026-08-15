package com.cxxcxx.zinecraft.api.world;

import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.world.biome.BiomeCatalog;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionCatalog;
import com.cxxcxx.zinecraft.api.world.feature.FeatureCatalog;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public final class WorldgenManager {
  @NotNull
  private final BiomeCatalog biomes;
  @NotNull
  private final DimensionCatalog dimensions;
  @NotNull
  private final FeatureCatalog features;
  @NotNull
  private final StructureCatalog structures;

  public WorldgenManager(@NotNull ModRegistrar registrar) {
    super();
    this.biomes = new BiomeCatalog(registrar);
    this.dimensions = new DimensionCatalog(registrar);
    this.features = new FeatureCatalog(registrar);
    this.structures = new StructureCatalog(registrar);
  }

  @NotNull
  public final BiomeCatalog getBiomes() {
    return this.biomes;
  }

  @NotNull
  public final DimensionCatalog getDimensions() {
    return this.dimensions;
  }

  @NotNull
  public final FeatureCatalog getFeatures() {
    return this.features;
  }

  @NotNull
  public final StructureCatalog getStructures() {
    return this.structures;
  }

  public final void addDataGeneration(@NotNull RegistrySetBuilder builder) {
    builder.add(Registries.CONFIGURED_FEATURE, this.features::bootstrapConfigured$zinecraft);
    builder.add(Registries.PLACED_FEATURE, this.features::bootstrapPlaced$zinecraft);
    builder.add(Registries.BIOME, this.biomes::bootstrap$zinecraft);
    builder.add(Registries.DIMENSION_TYPE, this.dimensions::bootstrapDimensionTypes$zinecraft);
    builder.add(Registries.LEVEL_STEM, this.dimensions::bootstrapLevelStems$zinecraft);
    builder.add(Registries.PROCESSOR_LIST, this.structures::bootstrapProcessors$zinecraft);
    builder.add(Registries.TEMPLATE_POOL, this.structures::bootstrapPools$zinecraft);
    builder.add(Registries.STRUCTURE, this.structures::bootstrapStructures$zinecraft);
    builder.add(Registries.STRUCTURE_SET, this.structures::bootstrapSets$zinecraft);
  }
}

