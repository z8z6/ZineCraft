package com.cxxcxx.zinecraft.core.structure

import com.mojang.serialization.MapCodec
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType

object ESStructurePoolElementTypes {

  val SINGLE_POOL
      : StructurePoolElementType<ESSinglePoolElement?> =
    register("single_pool", ESSinglePoolElement.CODEC)

  private fun <S : StructurePoolElement?> register(
    string: String,
    mapCodec: MapCodec<S?>?
  ): StructurePoolElementType<S?> {
    return Registry.register(
      BuiltInRegistries.STRUCTURE_POOL_ELEMENT, string,
      StructurePoolElementType { mapCodec })
  }

  fun init() {}
}