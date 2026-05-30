package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.structure.ExampleStructure
import com.cxxcxx.zinecraft.core.structure.ModStructure
import com.cxxcxx.zinecraft.core.structure.ModStructure.PORTAL_RUINS_COMMON
import com.cxxcxx.zinecraft.core.structure.ModStructure.structure
import com.cxxcxx.zinecraft.core.structure.ModTemplatePool
import net.minecraft.core.HolderGetter
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.tags.BiomeTags
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.VerticalAnchor
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings
import java.util.*
import java.util.Map


object ModStructureProvider {

  fun configure(context: BootstrapContext<Structure?>) {
    val biomeHolderGetter: HolderGetter<Biome?> = context.lookup(Registries.BIOME)
    val poolGetter = context.lookup(Registries.TEMPLATE_POOL)

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

    context.register(
      PORTAL_RUINS_COMMON, JigsawStructure(
        Structure.StructureSettings(
          biomeHolderGetter.getOrThrow(BiomeTags.IS_OVERWORLD),
          Map.of<MobCategory?, StructureSpawnOverride?>(),
          GenerationStep.Decoration.SURFACE_STRUCTURES,
          TerrainAdjustment.BEARD_THIN
        ),
        poolGetter.getOrThrow(ModTemplatePool.PORTAL_RUINS_COMMON),
        Optional.empty(),
        1,
        ConstantHeight.of(VerticalAnchor.absolute(0)),
        false,
        Optional.of(Heightmap.Types.WORLD_SURFACE_WG),
        50,
        mutableListOf<PoolAliasBinding?>(),
        DimensionPadding.ZERO,
        LiquidSettings.IGNORE_WATERLOGGING
      )
    )

  }
}