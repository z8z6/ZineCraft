package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.structure.ExampleStructurePieces.ExampleStructurePiece
import com.mojang.serialization.MapCodec
import net.minecraft.core.HolderSet
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.structure.*
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType
import java.util.Map

// 获取结构方块
// give @s minecraft:structure_block
object ModStructure {

  val EXAMPLE_STRUCTURE_TYPE: StructureType<ExampleStructure?> =
    register("example_structure", ExampleStructure.CODEC)

  val EXAMPLE_STRUCTURE_PIECE_TYPE: StructurePieceType =
    register("example_structure_piece", ::ExampleStructurePiece)

  val EXAMPLE_STRUCTURE_SET: ResourceKey<StructureSet?> = register("example_structure")

  val EXAMPLE_STRUCTURE: ResourceKey<Structure?> = registerKey("example_structure")

  val PORTAL_RUINS_COMMON_SET: ResourceKey<StructureSet?> = register("portal_ruins_common")

  val PORTAL_RUINS_COMMON: ResourceKey<Structure?> = registerKey("portal_ruins_common")






  fun init() {
    EXAMPLE_STRUCTURE
    EXAMPLE_STRUCTURE_TYPE
    EXAMPLE_STRUCTURE_PIECE_TYPE
    EXAMPLE_STRUCTURE_SET
    PORTAL_RUINS_COMMON
    PORTAL_RUINS_COMMON_SET
  }

  private fun <S : Structure?> register(
    string: String,
    mapCodec: MapCodec<S?>?
  )
      : StructureType<S?> {
    return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, string, StructureType { mapCodec })
  }

  private fun register(
    string: String,
    structurePieceType: StructureTemplateType
  )
      : StructurePieceType {
    return Registry.register(BuiltInRegistries.STRUCTURE_PIECE, string, structurePieceType)
  }

  private fun register(name: String): ResourceKey<StructureSet?> {
    return ResourceKey.create(
      Registries.STRUCTURE_SET,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }

  fun registerKey(name: String): ResourceKey<Structure?> {
    return ResourceKey.create(
      Registries.STRUCTURE,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }

  fun structure(
    pBiomes: HolderSet<Biome?>,
    pSpawnOverrides: MutableMap<MobCategory?, StructureSpawnOverride?>,
    pStep: GenerationStep.Decoration,
    pTerrainAdaptation: TerrainAdjustment
  ): Structure.StructureSettings {
    return Structure.StructureSettings(pBiomes, pSpawnOverrides, pStep, pTerrainAdaptation)
  }

  private fun structure(
    pBiomes: HolderSet<Biome?>,
    pStep: GenerationStep.Decoration,
    pTerrainAdaptation: TerrainAdjustment
  ): Structure.StructureSettings {
    return structure(pBiomes, Map.of<MobCategory?, StructureSpawnOverride?>(), pStep, pTerrainAdaptation)
  }

  fun structure(
    pBiomes: HolderSet<Biome?>,
    pTerrainAdaptation: TerrainAdjustment
  ): Structure.StructureSettings {
    return structure(
      pBiomes,
      Map.of<MobCategory?, StructureSpawnOverride?>(),
      GenerationStep.Decoration.SURFACE_STRUCTURES,
      pTerrainAdaptation
    )
  }
}