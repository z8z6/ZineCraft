package com.cxxcxx.zinecraft.api.world.dimension

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.Holder
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeSource
import net.minecraft.world.level.biome.Climate
import net.minecraft.world.level.biome.MultiNoiseBiomeSource
import java.util.stream.Stream

/**
 * 泰拉专用群系源。
 *
 * 原点周围固定为一个中心国家群系，保证不同种子的新世界都拥有相同的大陆政治中心；中心区之外继续使用原版多噪声最近点分区。
 * [getNoiseBiome] 接收四分之一方块坐标，因此半径比较必须先换算到噪声坐标。
 */
class TerraBiomeSource(
  private val parameters: Climate.ParameterList<Holder<Biome>>,
  private val centerBiome: Holder<Biome>,
  private val centerRadius: Int
) : BiomeSource() {

  init {
    require(centerRadius >= MIN_CENTER_RADIUS) { "泰拉中心群系半径不能小于 $MIN_CENTER_RADIUS 格" }
  }

  override fun collectPossibleBiomes(): Stream<Holder<Biome>> = Stream.concat(
    Stream.of(centerBiome),
    parameters.values().stream().map { it.second }
  ).distinct()

  override fun codec(): MapCodec<out BiomeSource> = CODEC

  override fun getNoiseBiome(
    quartX: Int,
    quartY: Int,
    quartZ: Int,
    sampler: Climate.Sampler
  ): Holder<Biome> {
    val radiusInQuartBlocks = centerRadius / 4L
    val distanceSquared = quartX.toLong() * quartX + quartZ.toLong() * quartZ
    return if (distanceSquared <= radiusInQuartBlocks * radiusInQuartBlocks) {
      centerBiome
    } else {
      parameters.findValue(sampler.sample(quartX, quartY, quartZ))
    }
  }

  companion object {
    const val MIN_CENTER_RADIUS = 64

    val CODEC: MapCodec<TerraBiomeSource> = RecordCodecBuilder.mapCodec { instance ->
      instance.group(
        MultiNoiseBiomeSource.DIRECT_CODEC.forGetter { source -> source.parameters },
        Biome.CODEC.fieldOf("center_biome").forGetter { source -> source.centerBiome },
        Codec.intRange(MIN_CENTER_RADIUS, 8192).fieldOf("center_radius").forGetter { source -> source.centerRadius }
      ).apply(instance, ::TerraBiomeSource)
    }
  }
}
