package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.structure.ExampleStructure
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.ModStructure.structure
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.tags.BiomeTags
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment


object ModStructureProvider {

  fun configure(context: BootstrapContext<Structure?>) {
    val biomeHolderGetter: HolderGetter<Biome?> = context.lookup(Registries.BIOME)

    // 第一个参数是生物群系，我们指定了生物群系是主世界，
    // 第二个参数设置的是TerrainAdjustment
    context.register(
      ModStructure.EXAMPLE_STRUCTURE,
      ExampleStructure(
        structure(
          biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
          TerrainAdjustment.NONE
        )
      )
    )
  }
}