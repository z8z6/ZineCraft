package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList


object ModTemplatePool {
  val PORTAL_RUINS_COMMON: ResourceKey<StructureTemplatePool?> = create("portal_ruins_common")
  val PORTAL_RUINS_VINES: ResourceKey<StructureProcessorList?> = createProcessor("portal_ruins_vines")

  fun create(name: String): ResourceKey<StructureTemplatePool?> {
    return ResourceKey.create(
      Registries.TEMPLATE_POOL,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }

  fun createProcessor(name: String): ResourceKey<StructureProcessorList?> {
    return ResourceKey.create(
      Registries.PROCESSOR_LIST,
      ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, name)
    )
  }

  fun init() {
    PORTAL_RUINS_VINES
    PORTAL_RUINS_COMMON
  }
}