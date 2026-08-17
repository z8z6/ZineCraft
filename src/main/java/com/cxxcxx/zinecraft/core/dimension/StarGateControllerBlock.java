package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.item.ModItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public final class StarGateControllerBlock extends Block {
  @NotNull
  public static final StarGateControllerBlock.Access ACCESS = new StarGateControllerBlock.Access();
  @NotNull
  public static final String REQUIRES_KEY_MESSAGE = "message.zinecraft.stargate.requires_protocol_originium";
  @NotNull
  public static final String ACTIVATED_MESSAGE = "message.zinecraft.stargate.activated";
  @NotNull
  public static final String ALREADY_ACTIVE_MESSAGE = "message.zinecraft.stargate.already_active";
  @NotNull
  public static final String DAMAGED_MESSAGE = "message.zinecraft.stargate.damaged";
  @NotNull
  private static final BooleanProperty ACTIVE;
  @NotNull
  private static final EnumProperty<Axis> AXIS;
  @NotNull
  private static final MapCodec<StarGateControllerBlock> CODEC;

  static {
    BooleanProperty booleanProperty = BooleanProperty.create("active");
    ACTIVE = booleanProperty;
    EnumProperty enumProperty = BlockStateProperties.HORIZONTAL_AXIS;
    AXIS = enumProperty;
    MapCodec mapCodec = Block.simpleCodec(StarGateControllerBlock::new);
    CODEC = mapCodec;
  }

  public StarGateControllerBlock(@NotNull Properties properties) {
    super(properties);
    this.registerDefaultState(
        (BlockState) ((BlockState) ((BlockState) this.stateDefinition.any()).setValue((Property) ACTIVE, false)).setValue((Property) AXIS, (Comparable) Axis.X)
    );
  }

  @NotNull
  protected MapCodec<? extends StarGateControllerBlock> codec() {
    return CODEC;
  }

  protected void createBlockStateDefinition(@NotNull Builder<Block, BlockState> builder) {
    Property[] propertys = new Property[]{ACTIVE, AXIS};
    builder.add(propertys);
  }

  static Axis rotateAxis(@NotNull Axis axis, @NotNull Rotation rotation) {
    if (axis == Axis.Y) {
      return axis;
    }
    StarGateGeometry.HorizontalAxis horizontalAxis = axis == Axis.X
        ? StarGateGeometry.HorizontalAxis.X
        : StarGateGeometry.HorizontalAxis.Z;
    boolean quarterTurn = rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;
    return StarGateGeometry.rotateAxis(horizontalAxis, quarterTurn) == StarGateGeometry.HorizontalAxis.X ? Axis.X : Axis.Z;
  }

  @NotNull
  protected ItemInteractionResult useItemOn(
      @NotNull ItemStack stack,
      @NotNull BlockState state,
      @NotNull Level level,
      @NotNull BlockPos pos,
      @NotNull Player player,
      @NotNull InteractionHand hand,
      @NotNull BlockHitResult hit
  ) {
    if (!stack.is(ModItem.PROTOCOL_ORIGINIUM.getItem())) {
      if (!level.isClientSide) {
        player.displayClientMessage((Component) Component.translatable("message.zinecraft.stargate.requires_protocol_originium"), true);
      }

      return ItemInteractionResult.FAIL;
    } else if ((Boolean) state.getValue((Property) ACTIVE)) {
      if (!level.isClientSide) {
        player.displayClientMessage((Component) Component.translatable("message.zinecraft.stargate.already_active"), true);
      }

      return ItemInteractionResult.SUCCESS;
    } else {
      if (!level.isClientSide) {
        StarGateStructure starGateStructure = StarGateStructure.INSTANCE;
        LevelAccessor levelAccessor = (LevelAccessor) level;
        Comparable comparable = state.getValue((Property) AXIS);
        boolean bl = starGateStructure.activate(levelAccessor, pos, (Axis) comparable);
        if (bl) {
          player.displayClientMessage((Component) Component.translatable("message.zinecraft.stargate.activated"), true);
          BlockPos portalCenter = starGateStructure.portalCenter(levelAccessor, pos, (Axis) comparable);
          level.playSound(null, portalCenter, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.8F, 0.75F);
          level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.4F, 0.65F);
          if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                (ParticleOptions) ParticleTypes.ELECTRIC_SPARK,
                portalCenter.getX() + 0.5, portalCenter.getY() + 0.5, portalCenter.getZ() + 0.5,
                360, 11.0, 10.0, 2.5, 0.16
            );
            serverLevel.sendParticles(
                (ParticleOptions) ParticleTypes.END_ROD,
                portalCenter.getX() + 0.5, portalCenter.getY() + 0.5, portalCenter.getZ() + 0.5,
                180, 10.0, 9.0, 2.0, 0.08
            );
          }
        } else {
          player.displayClientMessage((Component) Component.translatable("message.zinecraft.stargate.damaged"), true);
          level.playSound(null, pos, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 0.8F, 0.8F);
        }
      }

      return ItemInteractionResult.SUCCESS;
    }
  }

  protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
    if (!state.is(newState.getBlock()) && (Boolean) state.getValue((Property) ACTIVE)) {
      StarGateStructure starGateStructure = StarGateStructure.INSTANCE;
      LevelAccessor levelAccessor = (LevelAccessor) level;
      Comparable comparable = state.getValue((Property) AXIS);
      starGateStructure.deactivate(levelAccessor, pos, (Axis) comparable);
    }

    super.onRemove(state, level, pos, newState, movedByPiston);
  }

  @NotNull
  protected InteractionResult useWithoutItem(
      @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit
  ) {
    if (!level.isClientSide) {
      String string = state.getValue(ACTIVE)
          ? "message.zinecraft.stargate.already_active"
          : "message.zinecraft.stargate.requires_protocol_originium";
      player.displayClientMessage((Component) Component.translatable(string), true);
    }

    return InteractionResult.SUCCESS;
  }

  @Override
  protected BlockState rotate(@NotNull BlockState state, @NotNull Rotation rotation) {
    return state.setValue(AXIS, rotateAxis(state.getValue(AXIS), rotation));
  }

  public static final class Access {
    private Access() {
    }

    @NotNull
    public final BooleanProperty getACTIVE() {
      return StarGateControllerBlock.ACTIVE;
    }

    @NotNull
    public final EnumProperty<Axis> getAXIS() {
      return StarGateControllerBlock.AXIS;
    }

    @NotNull
    public final MapCodec<StarGateControllerBlock> getCODEC() {
      return StarGateControllerBlock.CODEC;
    }
  }
}
