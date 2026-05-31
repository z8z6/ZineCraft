package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.structure.ExampleStructure
import com.cxxcxx.zinecraft.core.structure.ExampleStructurePieces.ExampleStructurePiece
import com.mojang.serialization.MapCodec
import net.minecraft.core.HolderGetter
import net.minecraft.core.HolderSet
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BiomeTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings
import java.util.*
import java.util.Map

// 获取结构方块
// give @s minecraft:structure_block
object ModStructure {

  val EXAMPLE_STRUCTURE_TYPE: StructureType<ExampleStructure> =
    register("example_structure", ExampleStructure.CODEC)

  val EXAMPLE_STRUCTURE_PIECE_TYPE: StructurePieceType =
    register("example_structure_piece", ::ExampleStructurePiece)


  val EXAMPLE_STRUCTURE: ResourceKey<Structure?> = key("example_structure")
  val PORTAL_RUINS_COMMON: ResourceKey<Structure?> = key("portal_ruins_common")

  fun configure(context: BootstrapContext<Structure?>) {
    val biomeHolderGetter: HolderGetter<Biome?> = context.lookup(Registries.BIOME)
    val poolGetter = context.lookup(Registries.TEMPLATE_POOL)

    // 第一个参数是生物群系，我们指定了生物群系是主世界，
    // 第二个参数设置的是TerrainAdjustment
    context.register(
      EXAMPLE_STRUCTURE,
      ExampleStructure(
        structure(
          biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
          TerrainAdjustment.NONE
        )
      )
    )

    context.register(
      PORTAL_RUINS_COMMON, JigsawStructure(
        Structure.StructureSettings(
          biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
          Map.of<MobCategory?, StructureSpawnOverride?>(),
          GenerationStep.Decoration.SURFACE_STRUCTURES,
          TerrainAdjustment.BEARD_THIN
        ),
        poolGetter.getOrThrow(ModTemplatePool.PORTAL_RUINS_COMMON),
        Optional.empty(),
        1,
        ConstantHeight.of(VerticalAnchor.absolute(0)),
        false,
        Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
        50,
        mutableListOf<PoolAliasBinding?>(),
        DimensionPadding.ZERO,
        LiquidSettings.IGNORE_WATERLOGGING
      )
    )

  }

  private fun <S : Structure> register(
    name: String,
    codec: MapCodec<S>
  ): StructureType<S> {
    return ZinecraftCore.register(BuiltInRegistries.STRUCTURE_TYPE, name, StructureType { codec })
  }

  private fun register(
    name: String,
    type: StructureTemplateType
  ): StructurePieceType {
    return ZinecraftCore.register(BuiltInRegistries.STRUCTURE_PIECE, name, type)
  }

  private fun key(name: String): ResourceKey<Structure?> {
    return ZinecraftCore.key(Registries.STRUCTURE, name)
  }

  fun init() {}

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