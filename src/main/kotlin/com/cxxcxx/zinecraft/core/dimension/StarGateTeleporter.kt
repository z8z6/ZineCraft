package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.core.block.ModBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level
import net.minecraft.world.level.border.WorldBorder
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.portal.DimensionTransition
import net.minecraft.world.phys.Vec3

/** 星门的双向寻址、出口创建和安全落点辅助。 */
object StarGateTeleporter {
  fun destination(source: ServerLevel, entity: Entity, portalPos: BlockPos): DimensionTransition? {
    val targetKey = when (source.dimension()) {
      Level.OVERWORLD -> ModDimensions.TERRA.levelKey
      ModDimensions.TERRA.levelKey -> Level.OVERWORLD
      else -> return null
    }
    val target = source.server.getLevel(targetKey) ?: return null
    val scale = DimensionType.getTeleportationScale(source.dimensionType(), target.dimensionType())
    val border: WorldBorder = target.worldBorder
    val scaled = border.clampToBounds(portalPos.x * scale, portalPos.y.toDouble(), portalPos.z * scale)

    val existing = findGate(target, scaled)
    val arrival = when {
      existing != null -> existing
      target.dimension() == ModDimensions.TERRA.levelKey -> createTerraGate(target, scaled)
      else -> target.sharedSpawnPos
    }

    return DimensionTransition(
      target,
      Vec3.atBottomCenterOf(arrival).add(0.0, 0.15, 0.0),
      Vec3.ZERO,
      entity.yRot,
      entity.xRot,
      DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
    )
  }

  private fun findGate(level: ServerLevel, around: BlockPos): BlockPos? {
    for (x in around.x - 12..around.x + 12) {
      for (z in around.z - 12..around.z + 12) {
        val surface = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z)
        for (y in (surface - 10).coerceAtLeast(level.minBuildHeight)..(surface + 10).coerceAtMost(level.maxBuildHeight - 1)) {
          val pos = BlockPos(x, y, z)
          if (level.getBlockState(pos).`is`(ModBlock.STARGATE_PORTAL)) return pos
        }
      }
    }
    return null
  }

  private fun createTerraGate(level: ServerLevel, around: BlockPos): BlockPos {
    val base = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, around)
    return StarGateStructure.place(level, base, Direction.Axis.X)
  }
}
