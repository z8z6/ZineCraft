package com.cxxcxx.zinecraft.api.world;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.cxxcxx.zinecraft.api.world.biome.BiomeCatalog;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionCatalog;
import com.cxxcxx.zinecraft.api.world.feature.FeatureCatalog;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public final class WorldgenManager {
  private final BiomeCatalog biomes;
  private final DimensionCatalog dimensions;
  private final FeatureCatalog features;
  private final StructureCatalog structures;

  public WorldgenManager(ModRegistrar registrar, TranslationCatalog translations) {
    super();
    this.biomes = new BiomeCatalog(registrar, translations);
    this.dimensions = new DimensionCatalog(registrar);
    this.features = new FeatureCatalog(registrar);
    this.structures = new StructureCatalog(registrar, translations);
  }

  public final BiomeCatalog getBiomes() {
    return this.biomes;
  }

  public final DimensionCatalog getDimensions() {
    return this.dimensions;
  }

  public final FeatureCatalog getFeatures() {
    return this.features;
  }

  public final StructureCatalog getStructures() {
    return this.structures;
  }

  public final void addDataGeneration(RegistrySetBuilder builder) {
    builder.add(Registries.CONFIGURED_FEATURE, this.features::bootstrapConfigured);
    builder.add(Registries.PLACED_FEATURE, this.features::bootstrapPlaced);
    builder.add(Registries.BIOME, this.biomes::bootstrap);
    builder.add(Registries.DIMENSION_TYPE, this.dimensions::bootstrapDimensionTypes);
    builder.add(Registries.LEVEL_STEM, this.dimensions::bootstrapLevelStems);
    builder.add(Registries.PROCESSOR_LIST, this.structures::bootstrapProcessors);
    builder.add(Registries.TEMPLATE_POOL, this.structures::bootstrapPools);
    builder.add(Registries.STRUCTURE, this.structures::bootstrapStructures);
    builder.add(Registries.STRUCTURE_SET, this.structures::bootstrapSets);
  }
}
