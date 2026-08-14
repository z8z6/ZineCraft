package com.cxxcxx.zinecraft.core.dimension

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Portal
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.portal.DimensionTransition

/** 星门事件视界；传送冷却和跨维度实体复制仍由原版 Portal 流程处理。 */
class StarGatePortalBlock(properties: BlockBehaviour.Properties) : Block(properties), Portal {
  override fun codec(): MapCodec<out StarGatePortalBlock> = CODEC

  override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
    if (entity.canUsePortal(false)) entity.setAsInsidePortal(this, pos)
  }

  override fun getPortalTransitionTime(level: ServerLevel, entity: Entity): Int =
    if (entity is Player) 20 else 0

  override fun getPortalDestination(
    level: ServerLevel,
    entity: Entity,
    pos: BlockPos
  ): DimensionTransition? = StarGateTeleporter.destination(level, entity, pos)

  override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
    if (random.nextInt(80) == 0) {
      level.playLocalSound(pos, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 0.35f, 1.45f, false)
    }
    repeat(2) {
      level.addParticle(
        ParticleTypes.ELECTRIC_SPARK,
        pos.x + random.nextDouble(),
        pos.y + random.nextDouble(),
        pos.z + random.nextDouble(),
        (random.nextDouble() - 0.5) * 0.04,
        (random.nextDouble() - 0.5) * 0.04,
        (random.nextDouble() - 0.5) * 0.04
      )
    }
  }

  override fun getCloneItemStack(level: LevelReader, pos: BlockPos, state: BlockState): ItemStack = ItemStack.EMPTY

  companion object {
    val CODEC: MapCodec<StarGatePortalBlock> = simpleCodec(::StarGatePortalBlock)
  }
}
