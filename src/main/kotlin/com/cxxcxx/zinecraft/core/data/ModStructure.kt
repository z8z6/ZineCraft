package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.structure.ExampleStructure
import com.cxxcxx.zinecraft.core.structure.ExampleStructurePieces.ExampleStructurePiece
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
import java.util.Map

// 获取结构方块
// give @s minecraft:structure_block
object ModStructure {

  val EXAMPLE_STRUCTURE_TYPE: StructureType<ExampleStructure> =
    ZinecraftCore.REGISTRAR.structureType("example_structure", ExampleStructure.CODEC)

  val EXAMPLE_STRUCTURE_PIECE_TYPE: StructurePieceType =
    ZinecraftCore.REGISTRAR.structurePiece(
      "example_structure_piece"
    ) { manager, tag -> ExampleStructurePiece(manager, tag) }


  val EXAMPLE_STRUCTURE: ResourceKey<Structure> =
    ZinecraftCore.REGISTRAR.key(Registries.STRUCTURE, "example_structure")

  fun configure(context: BootstrapContext<Structure>) {
    val biomeHolderGetter: HolderGetter<Biome> = context.lookup(Registries.BIOME)

    // 第一个参数是生物群系，我们指定了生物群系是主世界，
    // 第二个参数设置的是TerrainAdjustment
    ZinecraftCore.REGISTRAR.dynamic(
      context,
      EXAMPLE_STRUCTURE,
      ExampleStructure(
        structure(
          biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
          TerrainAdjustment.NONE
        )
      )
    )

  }

  init {
    ZinecraftCore.WORLDGEN.structures(::configure)
  }

  private fun structure(
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

  private fun structure(
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
