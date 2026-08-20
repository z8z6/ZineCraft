package com.cxxcxx.zinecraft.api.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * 泰拉国家地图群系源。
 *
 * <p>国家锚点先划定唯一国界；随后只在该国的专属群系池中按气候选择自然环境。
 * 河流由地物雕刻，不会用通用河流群系覆盖国家身份。世界边界内侧保留固定宽度的非国家外海环。</p>
 */
public final class TerraBiomeSource extends BiomeSource {
  public static final int MIN_MAP_SIZE = 1024;
  public static final int MAX_MAP_SIZE = 60_000_000;
  public static final MapCodec<TerraBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
      Region.CODEC.listOf().fieldOf("regions").forGetter(source -> source.regions),
      Biome.CODEC.fieldOf("outer_ocean_biome").forGetter(source -> source.outerOceanBiome),
      Codec.intRange(MIN_MAP_SIZE, MAX_MAP_SIZE).fieldOf("map_size").forGetter(source -> source.mapSize),
      Codec.intRange(1, MAX_MAP_SIZE / 2).fieldOf("ocean_ring_width").forGetter(source -> source.oceanRingWidth)
  ).apply(instance, TerraBiomeSource::new));
  public static final Access ACCESS = new Access();

  private final List<Region> regions;
  private final Holder<Biome> outerOceanBiome;
  private final int mapSize;
  private final int oceanRingWidth;

  public TerraBiomeSource(List<Region> regions, Holder<Biome> outerOceanBiome, int mapSize, int oceanRingWidth) {
    if (mapSize < MIN_MAP_SIZE || mapSize > MAX_MAP_SIZE || (mapSize & 1) != 0) {
      throw new IllegalArgumentException(
          "泰拉地图边长必须是 " + MIN_MAP_SIZE + " 到 " + MAX_MAP_SIZE + " 之间的偶数格"
      );
    }
    if (oceanRingWidth <= 0 || oceanRingWidth >= mapSize / 2) {
      throw new IllegalArgumentException("泰拉外海环宽度必须大于 0 且小于地图半径");
    }
    if (regions.isEmpty()) throw new IllegalArgumentException("泰拉地图至少需要一个国家区域");

    requireTerraBiome(outerOceanBiome);
    int landHalfSize = mapSize / 2 - oceanRingWidth;
    var regionIds = new HashSet<String>();
    var biomeKeys = new HashSet<>();
    var anchors = new HashSet<Long>();
    for (Region region : regions) {
      if (region.id().isBlank() || !regionIds.add(region.id())) {
        throw new IllegalArgumentException("泰拉地图区域 ID 为空或重复：" + region.id());
      }
      if (region.biomes().isEmpty()) {
        throw new IllegalArgumentException("泰拉地图区域没有群系：" + region.id());
      }
      for (BiomeEntry entry : region.biomes()) {
        requireTerraBiome(entry.biome());
        var biomeKey = entry.biome().unwrapKey().orElseThrow();
        if (!biomeKeys.add(biomeKey)) {
          throw new IllegalArgumentException("泰拉国家群系被多个区域使用：" + biomeKey.location());
        }
        if (entry.biome().is(outerOceanBiome.unwrapKey().orElseThrow())) {
          throw new IllegalArgumentException("外海辅助群系不能放入国家群系池：" + region.id());
        }
      }
      if (Math.abs(region.x()) >= landHalfSize || Math.abs(region.z()) >= landHalfSize) {
        throw new IllegalArgumentException("泰拉国家锚点必须位于外海环以内：" + region.id());
      }
      long packedAnchor = ((long) region.x() << 32) ^ (region.z() & 0xFFFFFFFFL);
      if (!anchors.add(packedAnchor)) {
        throw new IllegalArgumentException("泰拉国家锚点坐标重复：(" + region.x() + ", " + region.z() + ")");
      }
    }
    this.regions = List.copyOf(regions);
    this.outerOceanBiome = outerOceanBiome;
    this.mapSize = mapSize;
    this.oceanRingWidth = oceanRingWidth;
  }

  private static void requireTerraBiome(Holder<Biome> biome) {
    var key = biome.unwrapKey().orElseThrow(() -> new IllegalArgumentException("泰拉群系必须是已注册群系"));
    if (!"zinecraft".equals(key.location().getNamespace())) {
      throw new IllegalArgumentException("泰拉维度不允许非 Zinecraft 群系: " + key.location());
    }
  }

  @Override
  protected MapCodec<? extends BiomeSource> codec() {
    return CODEC;
  }

  private static Holder<Biome> selectClimateBiome(Region region, Climate.TargetPoint target) {
    float temperature = Climate.unquantizeCoord(target.temperature());
    float humidity = Climate.unquantizeCoord(target.humidity());
    float continentalness = Climate.unquantizeCoord(target.continentalness());
    float erosion = Climate.unquantizeCoord(target.erosion());
    float depth = Climate.unquantizeCoord(target.depth());
    float weirdness = Climate.unquantizeCoord(target.weirdness());
    BiomeEntry nearest = region.biomes().getFirst();
    double nearestDistance = nearest.climateDistance(
        temperature, humidity, continentalness, erosion, depth, weirdness
    );
    for (int index = 1; index < region.biomes().size(); index++) {
      BiomeEntry candidate = region.biomes().get(index);
      double distance = candidate.climateDistance(
          temperature, humidity, continentalness, erosion, depth, weirdness
      );
      if (distance < nearestDistance) {
        nearest = candidate;
        nearestDistance = distance;
      }
    }
    return nearest.biome();
  }

  private static long distanceSquared(Region region, long x, long z) {
    long deltaX = x - region.x();
    long deltaZ = z - region.z();
    return deltaX * deltaX + deltaZ * deltaZ;
  }

  @Override
  protected Stream<Holder<Biome>> collectPossibleBiomes() {
    return Stream.concat(
        regions.stream().flatMap(region -> region.biomes().stream()).map(BiomeEntry::biome),
        Stream.of(outerOceanBiome)
    );
  }

  @Override
  public Holder<Biome> getNoiseBiome(int quartX, int quartY, int quartZ, Climate.Sampler sampler) {
    long blockX = (long) quartX * 4L;
    long blockZ = (long) quartZ * 4L;
    if (isOuterOcean(blockX, blockZ)) return outerOceanBiome;
    Climate.TargetPoint target = sampler.sample(quartX, quartY, quartZ);
    return selectClimateBiome(regionAtBlock(blockX, blockZ), target);
  }

  /**
   * 返回固定地图坐标所属国家/特殊区域；外海覆盖不参与该查询。
   */
  public Region regionAtBlock(long blockX, long blockZ) {
    long halfSize = mapSize / 2L;
    long x = Math.max(-halfSize, Math.min(halfSize - 1L, blockX));
    long z = Math.max(-halfSize, Math.min(halfSize - 1L, blockZ));
    Region nearest = regions.getFirst();
    long nearestDistance = distanceSquared(nearest, x, z);
    for (int index = 1; index < regions.size(); index++) {
      Region candidate = regions.get(index);
      long distance = distanceSquared(candidate, x, z);
      if (distance < nearestDistance) {
        nearest = candidate;
        nearestDistance = distance;
      }
    }
    return nearest;
  }

  private boolean isOuterOcean(long blockX, long blockZ) {
    long oceanStart = mapSize / 2L - oceanRingWidth;
    return Math.abs(blockX) >= oceanStart || Math.abs(blockZ) >= oceanStart;
  }

  public record BiomeEntry(
      Holder<Biome> biome,
      float temperature,
      float humidity,
      float continentalness,
      float erosion,
      float depth,
      float weirdness
  ) {
    public static final Codec<BiomeEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Biome.CODEC.fieldOf("biome").forGetter(BiomeEntry::biome),
        Codec.FLOAT.fieldOf("temperature").forGetter(BiomeEntry::temperature),
        Codec.FLOAT.fieldOf("humidity").forGetter(BiomeEntry::humidity),
        Codec.FLOAT.fieldOf("continentalness").forGetter(BiomeEntry::continentalness),
        Codec.FLOAT.fieldOf("erosion").forGetter(BiomeEntry::erosion),
        Codec.FLOAT.fieldOf("depth").forGetter(BiomeEntry::depth),
        Codec.FLOAT.fieldOf("weirdness").forGetter(BiomeEntry::weirdness)
    ).apply(instance, BiomeEntry::new));

    private static double square(float value) {
      return (double) value * value;
    }

    private double climateDistance(
        float sampledTemperature,
        float sampledHumidity,
        float sampledContinentalness,
        float sampledErosion,
        float sampledDepth,
        float sampledWeirdness
    ) {
      return square(sampledTemperature - temperature)
          + square(sampledHumidity - humidity)
          + square(sampledContinentalness - continentalness)
          + square(sampledErosion - erosion)
          + square(sampledDepth - depth)
          + square(sampledWeirdness - weirdness);
    }
  }

  public record Region(String id, List<BiomeEntry> biomes, int x, int z) {
    public static final Codec<Region> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("id").forGetter(Region::id),
        BiomeEntry.CODEC.listOf().fieldOf("biomes").forGetter(Region::biomes),
        Codec.intRange(-MAX_MAP_SIZE / 2, MAX_MAP_SIZE / 2).fieldOf("x").forGetter(Region::x),
        Codec.intRange(-MAX_MAP_SIZE / 2, MAX_MAP_SIZE / 2).fieldOf("z").forGetter(Region::z)
    ).apply(instance, Region::new));
  }

  public static final class Access {
    private Access() {
    }

    public MapCodec<TerraBiomeSource> getCODEC() {
      return CODEC;
    }
  }
}
