package com.cxxcxx.zinecraft.core.entity

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState


class ExampleBlockEntity(
  blockPos: BlockPos,
  blockState: BlockState,
) : BlockEntity(ModBlockEntity.EXAMPLE_BLOCK_ENTITY, blockPos, blockState) {

  private var clicks = 0

  fun getClicks(): Int {
    return clicks
  }

  fun incrementClicks() {
    clicks++
    setChanged()
  }

  override fun saveAdditional(nbt: CompoundTag, registryLookup: HolderLookup.Provider) {
    nbt.putInt("clicks", clicks)

    super.saveAdditional(nbt, registryLookup)
  }

  override fun loadAdditional(nbt: CompoundTag, registryLookup: HolderLookup.Provider) {
    super.loadAdditional(nbt, registryLookup)

    clicks = nbt.getInt("clicks")
  }
}