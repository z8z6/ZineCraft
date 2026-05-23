package com.cxxcxx.zinecraft.entity;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class ExampleEntityBlock extends BaseEntityBlock {

    public ExampleEntityBlock() {
        super(BlockBehaviour.Properties.of());
    }

    // 绑定方块实体
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExampleBlockEntity(pos, state);
    }

    // 右键打开GUI
    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ExampleBlockEntity bench) {
                player.openMenu(bench); // 打开GUI
            }
        }
        return InteractionResult.SUCCESS;
    }
    // 掉落内部物品
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}