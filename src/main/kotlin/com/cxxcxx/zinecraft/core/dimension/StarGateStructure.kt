package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Heightmap
import kotlin.math.abs

/** 生成可工作的圆环星门；入口地物和泰拉侧返回门共用同一份几何定义。 */
object StarGateStructure {
  private val halfWidths = intArrayOf(1, 2, 3, 3, 2, 1)

  fun canPlace(level: LevelAccessor, base: BlockPos, axis: Direction.Axis): Boolean {
    val heights = listOf(-4, 0, 4).map { offset ->
      val sample = local(base, axis, offset, 0, 0)
      level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, sample.x, sample.z)
    }
    if (heights.max() - heights.min() > 2) return false

    return (0..6).all { y ->
      (-4..4).all { horizontal ->
        (-1..1).all { depth ->
          val state = level.getBlockState(local(base, axis, horizontal, y, depth))
          state.canBeReplaced() || state.`is`(Blocks.SNOW) || state.`is`(Blocks.SNOW_BLOCK)
        }
      }
    }
  }

  /** @return 事件视界底部中央方块，用作安全抵达位置。 */
  fun place(level: LevelAccessor, base: BlockPos, axis: Direction.Axis): BlockPos {
    // 小型基座消化两格以内的雪原坡度，并为到达实体提供稳定落脚点。
    for (horizontal in -4..4) {
      for (depth in -1..1) {
        level.setBlock(local(base, axis, horizontal, -1, depth), Blocks.POLISHED_DEEPSLATE.defaultBlockState(), 2)
      }
    }

    for (y in halfWidths.indices) {
      val halfWidth = halfWidths[y]
      for (horizontal in -halfWidth..halfWidth) {
        val pos = local(base, axis, horizontal, y, 0)
        val frame = y == 0 || y == halfWidths.lastIndex || abs(horizontal) == halfWidth
        level.setBlock(
          pos,
          if (frame) frameState(y, horizontal, halfWidth) else ModBlock.STARGATE_PORTAL.defaultBlockState(),
          2
        )
      }
    }
    return local(base, axis, 0, 1, 0)
  }

  private fun frameState(y: Int, horizontal: Int, halfWidth: Int) =
    if ((y == 2 || y == 3) && abs(horizontal) == halfWidth) {
      Blocks.SEA_LANTERN.defaultBlockState()
    } else {
      Blocks.CHISELED_DEEPSLATE.defaultBlockState()
    }

  private fun local(
    base: BlockPos,
    axis: Direction.Axis,
    horizontal: Int,
    vertical: Int,
    depth: Int
  ): BlockPos = if (axis == Direction.Axis.X) {
    base.offset(horizontal, vertical, depth)
  } else {
    base.offset(depth, vertical, horizontal)
  }
}
