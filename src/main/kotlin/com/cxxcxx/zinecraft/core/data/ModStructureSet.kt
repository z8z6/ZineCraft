package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSet
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType


object ModStructureSet {

  val EXAMPLE_STRUCTURE_SET: ResourceKey<StructureSet> =
    ZinecraftCore.REGISTRAR.key(Registries.STRUCTURE_SET, "example_structure")

  fun configure(ctx: BootstrapContext<StructureSet>) {
    val holdergetter: HolderGetter<Structure> = ctx.lookup(Registries.STRUCTURE)

    // RandomSpreadStructurePlacement 是随机放置
    // 第一个参数是当前位置生成失败时候寻找下一生成点的距离
    // 第二个参数建组之间的最小间隔
    // 第三个参数是随机放置的类型，这里填的线性的
    // 第四个参数是盐
    ZinecraftCore.REGISTRAR.dynamic(
      ctx,
      EXAMPLE_STRUCTURE_SET,
      StructureSet(
        holdergetter.getOrThrow(ModStructure.EXAMPLE_STRUCTURE),
        RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 14357619)
      )
    )


  }

  init {
    ZinecraftCore.WORLDGEN.structureSets(::configure)
  }


  fun init() {}

}
