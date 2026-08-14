package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.Heightmap
import kotlin.math.abs

/** 主世界入口和泰拉侧返回门共用的宏大拱形星门几何与激活逻辑。 */
object StarGateStructure {
  /** y=1..17 的拱体外缘半宽：下部直立、顶部逐层收束为高耸圆拱。 */
  private val outerHalfWidths = intArrayOf(8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 7, 7, 6, 5, 3, 1)
  private const val FRAME_THICKNESS = 2

  fun canPlace(level: LevelAccessor, base: BlockPos, axis: Direction.Axis): Boolean {
    val heights = listOf(-9, 0, 9).map { offset ->
      val sample = local(base, axis, offset, 0, 0)
      level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, sample.x, sample.z)
    }
    if (heights.max() - heights.min() > 2) return false

    // 下方两层由台基主动整平；只要求拱门上空无遮挡，避免一格积雪坡使生成永远失败。
    return (2..19).all { y ->
      (-10..10).all { horizontal ->
        (-3..3).all { depth ->
          val state = level.getBlockState(local(base, axis, horizontal, y, depth))
          state.canBeReplaced() || state.`is`(Blocks.SNOW) || state.`is`(Blocks.SNOW_BLOCK)
        }
      }
    }
  }

  /**
   * 放置 17 格高、21 格宽（含扶壁）的拱形星门。
   *
   * 主世界自然遗迹保持关闭；泰拉侧自动创建的返程门会直接激活，避免玩家被困。
   * @return 事件视界底部中央方块，用作安全抵达位置。
   */
  fun place(
    level: LevelAccessor,
    base: BlockPos,
    axis: Direction.Axis,
    active: Boolean
  ): BlockPos {
    val arch = ModBlock.STARGATE_ARCH.defaultBlockState()

    // 七格纵深的纪念性台基消化雪原缓坡，并给穿越实体提供稳定落脚点。
    for (horizontal in -10..10) {
      for (depth in -3..3) {
        level.setBlock(local(base, axis, horizontal, -1, depth), arch, Block.UPDATE_CLIENTS)
      }
    }

    // 两侧扶壁让高拱在远景中形成厚重的支撑轮廓。
    for (side in intArrayOf(-1, 1)) {
      for (horizontal in 8..10) {
        for (y in 0..(5 - (horizontal - 8))) {
          for (depth in -2..2) {
            level.setBlock(local(base, axis, side * horizontal, y, depth), arch, Block.UPDATE_CLIENTS)
          }
        }
      }
    }

    for (index in outerHalfWidths.indices) {
      val y = index + 1
      val outer = outerHalfWidths[index]
      val inner = outer - FRAME_THICKNESS
      for (horizontal in -outer..outer) {
        val isFrame = inner < 0 || abs(horizontal) > inner
        if (isFrame) {
          for (depth in -1..1) {
            level.setBlock(local(base, axis, horizontal, y, depth), arch, Block.UPDATE_CLIENTS)
          }
        } else {
          // 只在拱体中央平面生成事件视界，关闭状态下明确清空积雪和植被。
          val interior = if (active) ModBlock.STARGATE_PORTAL.defaultBlockState() else Blocks.AIR.defaultBlockState()
          level.setBlock(local(base, axis, horizontal, y, 0), interior, Block.UPDATE_CLIENTS)
        }
      }
    }

    level.setBlock(
      base,
      ModBlock.STARGATE_CONTROLLER.defaultBlockState()
        .setValue(StarGateControllerBlock.AXIS, axis)
        .setValue(StarGateControllerBlock.ACTIVE, active),
      Block.UPDATE_ALL
    )
    return local(base, axis, 0, 1, 0)
  }

  /** 验证拱体后建立事件视界。协议物品验证由控制方块负责。 */
  fun activate(level: LevelAccessor, controllerPos: BlockPos, axis: Direction.Axis): Boolean {
    if (!isFrameIntact(level, controllerPos, axis)) return false
    setPortalInterior(level, controllerPos, axis, active = true)

    val controller = level.getBlockState(controllerPos)
    if (controller.`is`(ModBlock.STARGATE_CONTROLLER)) {
      level.setBlock(
        controllerPos,
        controller.setValue(StarGateControllerBlock.ACTIVE, true),
        Block.UPDATE_ALL
      )
    }
    return true
  }

  /** 控制器被拆除时同步消除事件视界，防止绕过协议源石保留可用传送面。 */
  fun deactivate(level: LevelAccessor, controllerPos: BlockPos, axis: Direction.Axis) {
    setPortalInterior(level, controllerPos, axis, active = false)
  }

  private fun setPortalInterior(
    level: LevelAccessor,
    base: BlockPos,
    axis: Direction.Axis,
    active: Boolean
  ) {
    for (index in outerHalfWidths.indices) {
      val inner = outerHalfWidths[index] - FRAME_THICKNESS
      if (inner < 0) continue
      val y = index + 1
      for (horizontal in -inner..inner) {
        val pos = local(base, axis, horizontal, y, 0)
        if (active || level.getBlockState(pos).`is`(ModBlock.STARGATE_PORTAL)) {
          level.setBlock(
            pos,
            if (active) ModBlock.STARGATE_PORTAL.defaultBlockState() else Blocks.AIR.defaultBlockState(),
            Block.UPDATE_ALL
          )
        }
      }
    }
  }

  private fun isFrameIntact(level: LevelAccessor, base: BlockPos, axis: Direction.Axis): Boolean {
    val controller = level.getBlockState(base)
    if (!controller.`is`(ModBlock.STARGATE_CONTROLLER) || controller.getValue(StarGateControllerBlock.AXIS) != axis) {
      return false
    }

    for (index in outerHalfWidths.indices) {
      val y = index + 1
      val outer = outerHalfWidths[index]
      val inner = outer - FRAME_THICKNESS
      for (horizontal in -outer..outer) {
        if (inner >= 0 && abs(horizontal) <= inner) continue
        for (depth in -1..1) {
          if (!level.getBlockState(local(base, axis, horizontal, y, depth)).`is`(ModBlock.STARGATE_ARCH)) {
            return false
          }
        }
      }
    }
    return true
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
