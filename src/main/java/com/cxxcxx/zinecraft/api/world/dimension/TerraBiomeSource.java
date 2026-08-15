package com.cxxcxx.zinecraft.api.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

import java.util.stream.Stream;

public final class TerraBiomeSource extends BiomeSource {
  public static final int MIN_CENTER_RADIUS = 64;
  public static final MapCodec<TerraBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      MultiNoiseBiomeSource.DIRECT_CODEC.forGetter(source -> source.parameters),
      Biome.CODEC.fieldOf("center_biome").forGetter(source -> source.centerBiome),
      Codec.intRange(MIN_CENTER_RADIUS, 8192).fieldOf("center_radius").forGetter(source -> source.centerRadius)
  ).apply(instance, TerraBiomeSource::new));
  public static final Companion Companion = new Companion();

  private final Climate.ParameterList<Holder<Biome>> parameters;
  private final Holder<Biome> centerBiome;
  private final int centerRadius;

  public TerraBiomeSource(Climate.ParameterList<Holder<Biome>> parameters, Holder<Biome> centerBiome, int centerRadius) {
    if (centerRadius < MIN_CENTER_RADIUS) {
      throw new IllegalArgumentException("泰拉中心群系半径不能小于 " + MIN_CENTER_RADIUS + " 格");
    }
    this.parameters = parameters;
    this.centerBiome = centerBiome;
    this.centerRadius = centerRadius;
  }

  @Override
  protected MapCodec<? extends BiomeSource> codec() {
    return CODEC;
  }

  @Override
  protected Stream<Holder<Biome>> collectPossibleBiomes() {
    return Stream.concat(Stream.of(centerBiome), parameters.values().stream().map(pair -> pair.getSecond())).distinct();
  }

  @Override
  public Holder<Biome> getNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
    long radius = centerRadius / 4L;
    long distanceSquared = (long) quartX * quartX + (long) quartZ * quartZ;
    return distanceSquared <= radius * radius ? centerBiome : parameters.findValue(sampler.sample(quartX, quartY, quartZ));
  }

  public static final class Companion {
    private Companion() {
    }

    public MapCodec<TerraBiomeSource> getCODEC() {
      return CODEC;
    }
  }
}
