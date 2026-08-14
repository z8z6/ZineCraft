package com.cxxcxx.zinecraft.core.dimension

import com.cxxcxx.zinecraft.core.item.ModItem
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.BlockHitResult

/** 星门基座控制器；协议源石是唯一能够建立事件视界的授权密钥。 */
class StarGateControllerBlock(properties: BlockBehaviour.Properties) : Block(properties) {
  init {
    registerDefaultState(
      stateDefinition.any()
        .setValue(ACTIVE, false)
        .setValue(AXIS, Direction.Axis.X)
    )
  }

  override fun codec(): MapCodec<out StarGateControllerBlock> = CODEC

  override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
    builder.add(ACTIVE, AXIS)
  }

  override fun useItemOn(
    stack: ItemStack,
    state: BlockState,
    level: Level,
    pos: BlockPos,
    player: Player,
    hand: InteractionHand,
    hit: BlockHitResult
  ): ItemInteractionResult {
    if (!stack.`is`(ModItem.PROTOCOL_ORIGINIUM.item)) {
      if (!level.isClientSide) {
        player.displayClientMessage(Component.translatable(REQUIRES_KEY_MESSAGE), true)
      }
      return ItemInteractionResult.FAIL
    }

    if (state.getValue(ACTIVE)) {
      if (!level.isClientSide) {
        player.displayClientMessage(Component.translatable(ALREADY_ACTIVE_MESSAGE), true)
      }
      return ItemInteractionResult.SUCCESS
    }

    if (!level.isClientSide) {
      val activated = StarGateStructure.activate(level, pos, state.getValue(AXIS))
      if (activated) {
        player.displayClientMessage(Component.translatable(ACTIVATED_MESSAGE), true)
        level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.4f, 0.75f)
        (level as? ServerLevel)?.sendParticles(
          net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK,
          pos.x + 0.5,
          pos.y + 1.25,
          pos.z + 0.5,
          80,
          3.5,
          5.0,
          3.5,
          0.08
        )
      } else {
        player.displayClientMessage(Component.translatable(DAMAGED_MESSAGE), true)
        level.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 0.8f, 0.8f)
      }
    }
    return ItemInteractionResult.SUCCESS
  }

  override fun useWithoutItem(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    player: Player,
    hit: BlockHitResult
  ): InteractionResult {
    if (!level.isClientSide) {
      val message = if (state.getValue(ACTIVE)) ALREADY_ACTIVE_MESSAGE else REQUIRES_KEY_MESSAGE
      player.displayClientMessage(Component.translatable(message), true)
    }
    return InteractionResult.SUCCESS
  }

  override fun onRemove(
    state: BlockState,
    level: Level,
    pos: BlockPos,
    newState: BlockState,
    movedByPiston: Boolean
  ) {
    if (!state.`is`(newState.block) && state.getValue(ACTIVE)) {
      StarGateStructure.deactivate(level, pos, state.getValue(AXIS))
    }
    super.onRemove(state, level, pos, newState, movedByPiston)
  }

  companion object {
    val ACTIVE: BooleanProperty = BooleanProperty.create("active")
    val AXIS: EnumProperty<Direction.Axis> = BlockStateProperties.HORIZONTAL_AXIS
    val CODEC: MapCodec<StarGateControllerBlock> = simpleCodec(::StarGateControllerBlock)

    const val REQUIRES_KEY_MESSAGE = "message.zinecraft.stargate.requires_protocol_originium"
    const val ACTIVATED_MESSAGE = "message.zinecraft.stargate.activated"
    const val ALREADY_ACTIVE_MESSAGE = "message.zinecraft.stargate.already_active"
    const val DAMAGED_MESSAGE = "message.zinecraft.stargate.damaged"
  }
}
