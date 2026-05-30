package com.cxxcxx.zinecraft.core.structure

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.WorldgenRandom
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureType
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder
import java.util.*


class ExampleStructure(
  pSettings: StructureSettings?
) : Structure(pSettings) {

  protected override fun findGenerationPoint(ctx: GenerationContext): Optional<GenerationStub?> {
    return onTopOfChunkCenter(
      ctx,
      Heightmap.Types.WORLD_SURFACE_WG,
      { builder -> this.generatePieces(builder, ctx) })
  }

  private fun generatePieces(pBuilder: StructurePiecesBuilder, pContext: GenerationContext) {
    val chunkpos: ChunkPos = pContext.chunkPos()
    val worldgenrandom: WorldgenRandom = pContext.random()
    val blockpos = BlockPos(chunkpos.minBlockX, 64, chunkpos.getMinBlockZ())
    val rotation: Rotation = Rotation.getRandom(worldgenrandom)
    ExampleStructurePieces.addPieces(
      pContext.structureTemplateManager(),
      blockpos, rotation,
      pBuilder, worldgenrandom
    )
  }

  override fun type(): StructureType<*> {
    return ModStructure.EXAMPLE_STRUCTURE_TYPE
  }


  companion object {
    val CODEC: MapCodec<ExampleStructure?>? = simpleCodec(::ExampleStructure)
  }
}