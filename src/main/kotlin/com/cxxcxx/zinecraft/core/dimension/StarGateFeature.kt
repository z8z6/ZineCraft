package com.cxxcxx.zinecraft.core.dimension

import com.mojang.serialization.Codec
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration

/** 主世界雪原自然生成的星门入口。 */
class StarGateFeature : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
  override fun place(context: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
    val level = context.level()
    if (level.level.dimension() != Level.OVERWORLD) return false

    val base = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, context.origin())
    val axis = if (context.random().nextBoolean()) Direction.Axis.X else Direction.Axis.Z
    if (!StarGateStructure.canPlace(level, base, axis)) return false
    StarGateStructure.place(level, base, axis)
    return true
  }
}
