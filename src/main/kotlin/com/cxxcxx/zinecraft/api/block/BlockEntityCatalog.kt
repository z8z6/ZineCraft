package com.cxxcxx.zinecraft.api.block

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

class BlockEntityCatalog(private val registrar: ModRegistrar) {
  fun <T : BlockEntity> register(
    path: String,
    factory: BlockEntityType.BlockEntitySupplier<out T>,
    vararg blocks: Block
  ): BlockEntityType<T> {
    require(blocks.isNotEmpty()) { "方块实体至少需要绑定一个方块" }
    return registrar.blockEntity(path, factory, *blocks)
  }
}
