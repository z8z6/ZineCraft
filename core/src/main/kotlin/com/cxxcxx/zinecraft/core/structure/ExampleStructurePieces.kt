package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.google.common.collect.ImmutableMap
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.ServerLevelAccessor
import net.minecraft.world.level.StructureManager
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.Mirror
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.levelgen.structure.BoundingBox
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import java.util.function.Function


object ExampleStructurePieces {
  val STRUCTURE_LOCATION_MY_STRUCTURE: ResourceLocation =
    ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, "example_structure")

  val PIVOTS: MutableMap<ResourceLocation?, BlockPos?> = ImmutableMap.of(
    STRUCTURE_LOCATION_MY_STRUCTURE, BlockPos(4, 0, 4)
  )

  val OFFSETS: MutableMap<ResourceLocation?, BlockPos?> = ImmutableMap.of(
    STRUCTURE_LOCATION_MY_STRUCTURE, BlockPos.ZERO
  )

  fun addPieces(
    pStructureTemplateManager: StructureTemplateManager,
    pStartPos: BlockPos,
    pRotation: Rotation,
    pPieces: StructurePieceAccessor,
    pRandom: RandomSource
  ) {
    pPieces.addPiece(
      ExampleStructurePiece(
        pStructureTemplateManager,
        STRUCTURE_LOCATION_MY_STRUCTURE,
        pStartPos,
        pRotation,
        0
      )
    )
  }

  class ExampleStructurePiece : TemplateStructurePiece {
    constructor(
      pStructureTemplateManager: StructureTemplateManager,
      pLocation: ResourceLocation,
      pStartPos: BlockPos,
      pRotation: Rotation,
      pDown: Int
    ) : super(
      ModStructure.EXAMPLE_STRUCTURE_PIECE_TYPE, 0, pStructureTemplateManager, pLocation, pLocation.toString(),
      makeSettings(pRotation, pLocation),
      makePosition(pLocation, pStartPos, pDown)
    )


    constructor(
      pStructureTemplateManager: StructureTemplateManager,
      pTag: CompoundTag
    ) : super(
      ModStructure.EXAMPLE_STRUCTURE_PIECE_TYPE,
      pTag,
      pStructureTemplateManager,
      Function { resourceLocation: ResourceLocation? ->
        makeSettings(
          Rotation.valueOf(pTag.getString("Rot")),
          resourceLocation
        )
      })

    // 在序列化之后，从nbt读取数据之前，向nbt数据附加一些内容
    override fun addAdditionalSaveData(pContext: StructurePieceSerializationContext, pTag: CompoundTag) {
      super.addAdditionalSaveData(pContext, pTag)
      pTag.putString("Rot", this.placeSettings.getRotation().name)
    }

    // 例如给结构中的一些位置添加战利品箱子，可以利用这个方法实现
    override fun handleDataMarker(
      pName: String,
      pPos: BlockPos,
      pLevel: ServerLevelAccessor,
      pRandom: RandomSource,
      pBox: BoundingBox
    ) {
      if ("chest" == pName) {
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3)
        val blockentity = pLevel.getBlockEntity(pPos.below())
        if (blockentity is ChestBlockEntity) {
          blockentity.setLootTable(BuiltInLootTables.IGLOO_CHEST, pRandom.nextLong())
        }
      }
    }

    // 在结构生成之后，对结构进一步的处理和修饰
    override fun postProcess(
      pLevel: WorldGenLevel,
      pStructureManager: StructureManager,
      pGenerator: ChunkGenerator,
      pRandom: RandomSource,
      pBox: BoundingBox,
      pChunkPos: ChunkPos,
      pPos: BlockPos
    ) {
      super.postProcess(pLevel, pStructureManager, pGenerator, pRandom, pBox, pChunkPos, pPos)
    }

    companion object {
      private fun makeSettings(pRotation: Rotation, pLocation: ResourceLocation?): StructurePlaceSettings {
        return StructurePlaceSettings()
          .setRotation(pRotation)
          .setMirror(Mirror.NONE)
          .setRotationPivot(PIVOTS.get(pLocation))
          .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
      }

      private fun makePosition(pLocation: ResourceLocation?, pPos: BlockPos, pDown: Int): BlockPos {
        return pPos.offset(OFFSETS.get(pLocation)).below(pDown)
      }
    }
  }
}