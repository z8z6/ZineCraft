package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.structure.ExampleSinglePoolElement
import com.google.common.collect.ImmutableList
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.MapCodec
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.Pools
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.*


object ModTemplatePool {

  val PORTAL_RUINS_COMMON: ResourceKey<StructureTemplatePool> = keyTemplate("portal_ruins_common")
  val PORTAL_RUINS_VINES: ResourceKey<StructureProcessorList> = keyProcessor("portal_ruins_vines")

  val SINGLE_POOL: StructurePoolElementType<ExampleSinglePoolElement> =
    register("single_pool", ExampleSinglePoolElement.CODEC)

  fun configureTemplate(context: BootstrapContext<StructureTemplatePool>) {
    val emptyPool: Holder<StructureTemplatePool> = context.lookup(
      Registries.TEMPLATE_POOL
    ).getOrThrow(Pools.EMPTY)
    val processors = context.lookup(Registries.PROCESSOR_LIST)

    context.register(
      PORTAL_RUINS_COMMON,
      StructureTemplatePool(
        emptyPool,
        ImmutableList.of(
          Pair.of(
            ExampleSinglePoolElement.make(
              ZinecraftCore.id("portal_ruins/common").toString(),
              processors.getOrThrow(PORTAL_RUINS_VINES),
              0
            ), 1
          )
        ),
        StructureTemplatePool.Projection.RIGID
      )
    )
  }

  fun configureProcessors(context: BootstrapContext<StructureProcessorList>) {
    context.register(
      PORTAL_RUINS_VINES, StructureProcessorList(
        listOf<StructureProcessor?>(
          RuleProcessor(
            listOf<ProcessorRule?>(
              ProcessorRule(
                RandomBlockMatchTest(Blocks.VINE, 0.6f),
                AlwaysTrueTest.INSTANCE,
                Blocks.AIR.defaultBlockState()
              )
            )
          )
        )
      )
    )
  }


  private fun keyTemplate(name: String): ResourceKey<StructureTemplatePool> {
    return ZinecraftCore.key(Registries.TEMPLATE_POOL, name)
  }

  private fun keyProcessor(name: String): ResourceKey<StructureProcessorList> {
    return ZinecraftCore.key(Registries.PROCESSOR_LIST, name)
  }

  private fun <S : StructurePoolElement> register(
    string: String,
    mapCodec: MapCodec<S>
  ): StructurePoolElementType<S> {
    return ZinecraftCore.register(
      BuiltInRegistries.STRUCTURE_POOL_ELEMENT, string,
      StructurePoolElementType { mapCodec })
  }

  fun init() {}
}
