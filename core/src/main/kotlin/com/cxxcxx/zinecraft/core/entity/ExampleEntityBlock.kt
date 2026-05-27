package com.cxxcxx.zinecraft.core.entity

import com.cxxcxx.zinecraft.core.ZinecraftCore.MOD_ID
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import org.slf4j.LoggerFactory


class ExampleEntityBlock(prop: Properties) : BaseEntityBlock(prop) {

  private val logger = LoggerFactory.getLogger(MOD_ID)

  override fun newBlockEntity(
    blockPos: BlockPos,
    blockState: BlockState
  ): BlockEntity {
    return ExampleBlockEntity(blockPos, blockState)
  }

  override fun codec(): MapCodec<out BaseEntityBlock?>? {
    return simpleCodec(::ExampleEntityBlock)
  }

  override fun getRenderShape(state: BlockState): RenderShape {
    return RenderShape.MODEL
  }

  protected override fun useWithoutItem(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    player: Player,
    hit: BlockHitResult
  ): InteractionResult {

    logger.debug("Using {}", pos)

    // 获取并安全检查 BlockEntity
    val blockEntity = level.getBlockEntity(pos) as? ExampleBlockEntity
      ?: return super.useWithoutItem(state, level, pos, player, hit)

    // 这里已经是 ExampleBlockEntity 类型
    blockEntity.incrementClicks()

    player.displayClientMessage(
      Component.literal("You've clicked the block ${blockEntity.getClicks()} times."),
      true
    )

    return InteractionResult.SUCCESS
  }
}