package com.cxxcxx.zinecraft.api.world.structure

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import com.mojang.serialization.MapCodec
import net.minecraft.core.Vec3i
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType
import java.util.*

/**
 * 将结构唯一候选区块固定在 `(-1, -1)`。
 *
 * 33×33 模板由该区块的最小坐标 `(-16, -16)` 展开后，几何中心恰好落在世界方块坐标 `(0, 0)`；定位偏移也指向原点。
 */
class FixedOriginStructurePlacement private constructor() : StructurePlacement(
  Vec3i(16, 0, 16),
  FrequencyReductionMethod.DEFAULT,
  1.0f,
  0,
  Optional.empty()
) {
  override fun isPlacementChunk(
    structureState: ChunkGeneratorStructureState,
    chunkX: Int,
    chunkZ: Int
  ): Boolean = chunkX == ORIGIN_CHUNK && chunkZ == ORIGIN_CHUNK

  override fun type(): StructurePlacementType<*> = TYPE

  companion object {
    private const val ORIGIN_CHUNK = -1

    val CODEC: MapCodec<FixedOriginStructurePlacement> = MapCodec.unit(::FixedOriginStructurePlacement)

    private lateinit var TYPE: StructurePlacementType<FixedOriginStructurePlacement>

    internal fun register(registrar: ModRegistrar) {
      if (!::TYPE.isInitialized) {
        TYPE = registrar.structurePlacement("fixed_origin", CODEC)
      }
    }

    internal fun create(): FixedOriginStructurePlacement {
      check(::TYPE.isInitialized) { "固定原点结构放置器尚未注册" }
      return FixedOriginStructurePlacement()
    }
  }
}
