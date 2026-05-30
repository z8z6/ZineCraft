package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.structure.ESSinglePoolElement
import com.cxxcxx.zinecraft.core.structure.ModTemplatePool.PORTAL_RUINS_COMMON
import com.cxxcxx.zinecraft.core.structure.ModTemplatePool.PORTAL_RUINS_VINES
import com.google.common.collect.ImmutableList
import com.mojang.datafixers.util.Pair
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.Pools
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.*


object ModTemplatePoolProvider {
  fun configure(context: BootstrapContext<StructureTemplatePool?>) {
    val emptyPool: Holder<StructureTemplatePool?> = context.lookup(
      Registries.TEMPLATE_POOL
    ).getOrThrow(Pools.EMPTY)
    val processors = context.lookup(Registries.PROCESSOR_LIST)

    context.register(
      PORTAL_RUINS_COMMON,
      StructureTemplatePool(
        emptyPool,
        ImmutableList.of(
          Pair.of(
            ESSinglePoolElement.make(
              ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, "portal_ruins/common").toString(),
              processors.getOrThrow(PORTAL_RUINS_VINES),
              0
            ), 1
          )
        ),
        StructureTemplatePool.Projection.RIGID
      )
    )
  }

  fun bootstrapProcessors(context: BootstrapContext<StructureProcessorList?>) {
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
}
