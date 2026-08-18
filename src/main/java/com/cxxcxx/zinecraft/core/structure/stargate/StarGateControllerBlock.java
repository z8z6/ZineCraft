package com.cxxcxx.zinecraft.core.structure.stargate;

import com.cxxcxx.zinecraft.core.item.ModItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public final class StarGateControllerBlock extends Block {
  public static final String REQUIRES_KEY_MESSAGE = "message.zinecraft.stargate.requires_protocol_originium";
  public static final String ACTIVATED_MESSAGE = "message.zinecraft.stargate.activated";
  public static final String ALREADY_ACTIVE_MESSAGE = "message.zinecraft.stargate.already_active";
  public static final String DAMAGED_MESSAGE = "message.zinecraft.stargate.damaged";

  public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
  public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
  public static final MapCodec<StarGateControllerBlock> CODEC = Block.simpleCodec(StarGateControllerBlock::new);

  public StarGateControllerBlock(Properties properties) {
    super(properties);
    registerDefaultState(stateDefinition.any()
        .setValue(ACTIVE, false)
        .setValue(AXIS, Axis.X));
  }

  static Axis rotateAxis(Axis axis, Rotation rotation) {
    if (axis == Axis.Y) {
      return axis;
    }
    StarGateGeometry.HorizontalAxis horizontalAxis = axis == Axis.X
        ? StarGateGeometry.HorizontalAxis.X
        : StarGateGeometry.HorizontalAxis.Z;
    boolean quarterTurn = rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;
    return StarGateGeometry.rotateAxis(horizontalAxis, quarterTurn) == StarGateGeometry.HorizontalAxis.X
        ? Axis.X
        : Axis.Z;
  }

  private static void displayMessage(Level level, Player player, String translationKey) {
    if (!level.isClientSide) {
      player.displayClientMessage(Component.translatable(translationKey), true);
    }
  }

  private static void playActivationEffects(Level level, BlockPos controllerPos, BlockPos portalCenter) {
    level.playSound(null, portalCenter, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.8F, 0.75F);
    level.playSound(null, controllerPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.4F, 0.65F);
    if (level instanceof ServerLevel serverLevel) {
      double x = portalCenter.getX() + 0.5;
      double y = portalCenter.getY() + 0.5;
      double z = portalCenter.getZ() + 0.5;
      serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 360, 11.0, 10.0, 2.5, 0.16);
      serverLevel.sendParticles(ParticleTypes.END_ROD, x, y, z, 180, 10.0, 9.0, 2.0, 0.08);
    }
  }

  @Override
  protected MapCodec<? extends StarGateControllerBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(ACTIVE, AXIS);
  }

  @Override
  protected void onRemove(
      BlockState state,
      Level level,
      BlockPos pos,
      BlockState newState,
      boolean movedByPiston
  ) {
    if (!state.is(newState.getBlock()) && state.getValue(ACTIVE)) {
      StarGateStructure.INSTANCE.deactivate(level, pos, state.getValue(AXIS));
    }
    super.onRemove(state, level, pos, newState, movedByPiston);
  }

  @Override
  protected InteractionResult useWithoutItem(
      BlockState state,
      Level level,
      BlockPos pos,
      Player player,
      BlockHitResult hit
  ) {
    displayMessage(level, player, state.getValue(ACTIVE) ? ALREADY_ACTIVE_MESSAGE : REQUIRES_KEY_MESSAGE);
    return InteractionResult.SUCCESS;
  }

  @Override
  protected ItemInteractionResult useItemOn(
      ItemStack stack,
      BlockState state,
      Level level,
      BlockPos pos,
      Player player,
      InteractionHand hand,
      BlockHitResult hit
  ) {
    if (!stack.is(ModItem.PROTOCOL_ORIGINIUM)) {
      displayMessage(level, player, REQUIRES_KEY_MESSAGE);
      return ItemInteractionResult.FAIL;
    }
    if (state.getValue(ACTIVE)) {
      displayMessage(level, player, ALREADY_ACTIVE_MESSAGE);
      return ItemInteractionResult.SUCCESS;
    }
    if (level.isClientSide) {
      return ItemInteractionResult.SUCCESS;
    }

    Axis axis = state.getValue(AXIS);
    if (StarGateStructure.INSTANCE.activate(level, pos, axis)) {
      player.displayClientMessage(Component.translatable(ACTIVATED_MESSAGE), true);
      playActivationEffects(level, pos, StarGateStructure.INSTANCE.portalCenter(level, pos, axis));
    } else {
      player.displayClientMessage(Component.translatable(DAMAGED_MESSAGE), true);
      level.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 0.8F, 0.8F);
    }
    return ItemInteractionResult.SUCCESS;
  }

  @Override
  protected BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(AXIS, rotateAxis(state.getValue(AXIS), rotation));
  }

}
