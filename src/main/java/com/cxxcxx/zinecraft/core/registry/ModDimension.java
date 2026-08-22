package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.DimensionBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import com.cxxcxx.zinecraft.api.world.dimension.OverworldNoiseSettingsFactory;
import com.cxxcxx.zinecraft.api.world.dimension.TerraBiomeSource;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.HashSet;
import java.util.List;

public final class ModDimension {
  public static final MapCodec<TerraBiomeSource> TERRA_BIOME_SOURCE =
      Zinecraft.DIMENSIONS.biomeSource("terra", TerraBiomeSource.ACCESS.getCODEC());

  /**
   * 泰拉世界边界的完整边长，坐标范围为 [-50000, 50000)。
   */
  public static final int TERRA_MAP_SIZE = 100_000;
  /**
   * 泰拉的垂直建造与区块生成范围为 [-256, 767]。
   */
  public static final int TERRA_MIN_Y = -256;
  public static final int TERRA_HEIGHT = 1024;
  public static final int TERRA_MAX_Y = TERRA_MIN_Y + TERRA_HEIGHT - 1;
  public static final ResourceKey<NoiseGeneratorSettings> TERRA_NOISE_SETTINGS = ResourceKey.create(
      Registries.NOISE_SETTINGS,
      Zinecraft.id("terra")
  );
  /**
   * 国家 Voronoi 只在以原点为中心的八万格乘五万格泰拉核心矩形内计算。
   */
  public static final int TERRA_CORE_SIZE_X = 80_000;
  public static final int TERRA_CORE_SIZE_Z = 50_000;
  public static final int TERRA_CORE_HALF_SIZE_X = TERRA_CORE_SIZE_X / 2;
  public static final int TERRA_CORE_HALF_SIZE_Z = TERRA_CORE_SIZE_Z / 2;
  public static final int TERRA_SPAWN_Z = 0;
  /**
   * 泰拉新玩家出生点，位于地图中心
   */
  public static final int TERRA_SPAWN_X = 0;
  /**
   * 世界边界内侧固定海洋环宽度。
   */
  public static final int OUTER_OCEAN_RING_WIDTH = 1_000;

  public static final DimensionBuilder TERRA = Zinecraft.DIMENSIONS.dimension("terra")
      .heightRange(TERRA_MIN_Y, TERRA_HEIGHT)
      .noiseSettings(
          TERRA_NOISE_SETTINGS,
          context -> OverworldNoiseSettingsFactory.create(
              context,
              TERRA_MIN_Y,
              TERRA_HEIGHT,
              ModDensityFunction.TERRA_FINAL_DENSITY.key()
          )
      )
      .biomes(validateMap().stream()
          .map(builder -> new DimensionBiome(builder.key(), builder.climate()))
          .toList())
      .generator(ModDimension::createTerraGenerator)
      .build();

  static {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra");
    Zinecraft.TRANSLATIONS.add("journeymap.zinecraft.terra_nations", "泰拉国家边界", "Terra Nations");
  }

  private ModDimension() {
  }

  private static ChunkGenerator createTerraGenerator(DimensionBootstrapContext context) {
    List<TerraBiomeSource.Region> regions = ModNation.ALL.stream()
        .filter(nation -> !nation.isUnderground())
        .map(nation -> new TerraBiomeSource.Region(
            nation.id(),
            nationalBiomes(nation).stream().map(builder -> {
              BiomeBuilder.ClimateCoordinates climate = builder.climateCoordinates();
              return new TerraBiomeSource.BiomeEntry(
                  context.biomes().getOrThrow(builder.key()),
                  climate.temperature(), climate.humidity(), climate.continentalness(),
                  climate.erosion(), climate.depth(), climate.weirdness()
              );
            }).toList(),
            nation.relativePoints().stream()
                .map(point -> new TerraBiomeSource.MapPoint(
                    (int) Math.round(point.x() * TERRA_CORE_HALF_SIZE_X),
                    (int) Math.round(point.z() * TERRA_CORE_HALF_SIZE_Z)
                ))
                .toList()
        ))
        .toList();
    return new NoiseBasedChunkGenerator(
        new TerraBiomeSource(
            regions,
            context.biomes().getOrThrow(ModBiome.TERRA_OUTER_OCEAN.key()),
            TERRA_MAP_SIZE,
            OUTER_OCEAN_RING_WIDTH,
            TERRA_CORE_SIZE_X,
            TERRA_CORE_SIZE_Z
        ),
        context.noiseSettings()
    );
  }

  private static List<BiomeBuilder> validateMap() {
    var registeredBiomes = new HashSet<>(ModBiome.ALL_TERRA_BIOMES);
    registeredBiomes.removeAll(ModBiome.MAP_SUPPORT_BIOMES);
    var mappedBiomes = new HashSet<net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>>();
    var mappedNations = new HashSet<NationBuilder>();
    ModNation.ALL.forEach(nation -> {
      if (!mappedNations.add(nation)) {
        throw new IllegalStateException("泰拉地图国家重复：" + nation.getId());
      }
      for (BiomeBuilder biome : nationalBiomes(nation)) {
        if (!biome.path.startsWith(nation.getId() + "_")) {
          throw new IllegalStateException("国家群系 ID 必须包含国家名称：" + biome.key().location());
        }
        if (!mappedBiomes.add(biome.key())) {
          throw new IllegalStateException("泰拉地图群系重复归属：" + biome.key().location());
        }
      }
    });
    var registeredNations = new HashSet<>(Zinecraft.NATIONS.entries());
    if (!mappedNations.equals(registeredNations)) {
      var missingNations = new HashSet<>(registeredNations);
      missingNations.removeAll(mappedNations);
      throw new IllegalStateException("泰拉地图缺少国家：" + missingNations);
    }
    if (!registeredBiomes.equals(mappedBiomes)) {
      var missing = new HashSet<>(registeredBiomes);
      missing.removeAll(mappedBiomes);
      var unknown = new HashSet<>(mappedBiomes);
      unknown.removeAll(registeredBiomes);
      throw new IllegalStateException("泰拉地图群系不完整，缺少=" + missing + "，未知=" + unknown);
    }
    return ModBiome.ALL;
  }

  private static List<BiomeBuilder> nationalBiomes(NationBuilder nation) {
    List<BiomeBuilder> biomes = ModBiome.NATIONAL_BIOMES.get(nation);
    if (biomes == null || biomes.isEmpty()) {
      throw new IllegalStateException("国家没有专属群系：" + nation.getId());
    }
    return biomes;
  }

  public static void bootstrap() {
  }
}
