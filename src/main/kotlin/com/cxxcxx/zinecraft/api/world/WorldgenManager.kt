package com.cxxcxx.zinecraft.api.world

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.cxxcxx.zinecraft.api.world.biome.BiomeCatalog
import com.cxxcxx.zinecraft.api.world.dimension.DimensionCatalog
import com.cxxcxx.zinecraft.api.world.feature.FeatureCatalog
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries

class WorldgenManager(registrar: ModRegistrar) {
  val biomes = BiomeCatalog(registrar)
  val dimensions = DimensionCatalog(registrar)
  val features = FeatureCatalog(registrar)
  val structures = StructureCatalog(registrar)

  fun initialize() {
    features.initialize()
  }

  fun addDataGeneration(builder: RegistrySetBuilder) {
    builder.add(Registries.CONFIGURED_FEATURE, features::bootstrapConfigured)
    builder.add(Registries.PLACED_FEATURE, features::bootstrapPlaced)
    builder.add(Registries.BIOME, biomes::bootstrap)
    builder.add(Registries.DIMENSION_TYPE, dimensions::bootstrapDimensionTypes)
    builder.add(Registries.LEVEL_STEM, dimensions::bootstrapLevelStems)
    builder.add(Registries.PROCESSOR_LIST, structures::bootstrapProcessors)
    builder.add(Registries.TEMPLATE_POOL, structures::bootstrapPools)
    builder.add(Registries.STRUCTURE, structures::bootstrapStructures)
    builder.add(Registries.STRUCTURE_SET, structures::bootstrapSets)
  }
}
