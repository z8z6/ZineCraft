package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.item.ModItem;
import com.mojang.serialization.MapCodec;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
  public static final StarGateControllerBlock.Companion Companion = new StarGateControllerBlock.Companion(null);
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
    if (!stack.is(ModItem.INSTANCE.getPROTOCOL_ORIGINIUM().getItem())) {
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
          level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.4F, 0.75F);
          ServerLevel serverLevel = level instanceof ServerLevel ? (ServerLevel) level : null;
          if ((level instanceof ServerLevel ? (ServerLevel) level : null) != null) {
            serverLevel.sendParticles(
                (ParticleOptions) ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, 80, 3.5, 5.0, 3.5, 0.08
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

  public static final class Companion {
    private Companion() {
    }

    // $VF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
      this();
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

