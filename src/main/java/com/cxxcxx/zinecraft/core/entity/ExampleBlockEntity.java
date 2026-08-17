package com.cxxcxx.zinecraft.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public final class ExampleBlockEntity extends BlockEntity {
  private int clicks;

  public ExampleBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
    super(ModBlockEntity.EXAMPLE_BLOCK_ENTITY.get(), blockPos, blockState);
  }

  public final int getClicks() {
    return this.clicks;
  }

  public final void incrementClicks() {
    int i = this.clicks++;
    this.setChanged();
  }

  protected void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider registryLookup) {
    super.loadAdditional(nbt, registryLookup);
    this.clicks = nbt.getInt("clicks");
  }

  protected void saveAdditional(@NotNull CompoundTag nbt, @NotNull Provider registryLookup) {
    nbt.putInt("clicks", this.clicks);
    super.saveAdditional(nbt, registryLookup);
  }
}

