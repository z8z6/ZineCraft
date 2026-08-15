package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExampleEntityBlock extends BaseEntityBlock {
  private final Logger logger;

  public ExampleEntityBlock(@NotNull Properties prop) {
    super(prop);
    this.logger = LoggerFactory.getLogger(Zinecraft.MOD_ID);
  }

  @NotNull
  public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    return new ExampleBlockEntity(blockPos, blockState);
  }

  @Nullable
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return BaseEntityBlock.simpleCodec(ExampleEntityBlock::new);
  }

  @NotNull
  protected RenderShape getRenderShape(@NotNull BlockState state) {
    return RenderShape.MODEL;
  }

  @NotNull
  protected InteractionResult useWithoutItem(
      @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit
  ) {
    this.logger.debug("Using {}", pos);
    BlockEntity blockEntity1 = level.getBlockEntity(pos);
    ExampleBlockEntity exampleBlockEntity1 = blockEntity1 instanceof ExampleBlockEntity ? (ExampleBlockEntity) blockEntity1 : null;
    if ((blockEntity1 instanceof ExampleBlockEntity ? (ExampleBlockEntity) blockEntity1 : null) == null) {
      InteractionResult interactionResult = super.useWithoutItem(state, level, pos, player, hit);
      return interactionResult;
    } else {
      ExampleBlockEntity exampleBlockEntity = exampleBlockEntity1;
      exampleBlockEntity.incrementClicks();
      player.displayClientMessage((Component) Component.literal("You've clicked the block " + exampleBlockEntity.getClicks() + " times."), true);
      return InteractionResult.SUCCESS;
    }
  }
}

