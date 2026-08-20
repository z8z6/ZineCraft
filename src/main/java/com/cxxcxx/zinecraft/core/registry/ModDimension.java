package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.DimensionBuilder;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBiome;
import com.cxxcxx.zinecraft.api.world.dimension.DimensionBootstrapContext;
import com.cxxcxx.zinecraft.api.world.dimension.TerraBiomeSource;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

public final class ModDimension {
  /**
   * 泰拉世界边界的完整边长，坐标范围为 [-50000, 50000)。
   */
  public static final int TERRA_MAP_SIZE = 100_000;
  /**
   * 世界边界内侧固定海洋环宽度。
   */
  public static final int OUTER_OCEAN_RING_WIDTH = 1_000;
  /**
   * 泰拉新玩家出生点，位于地图中心的拉特兰区域。
   */
  public static final int TERRA_SPAWN_X = 0;
  public static final int TERRA_SPAWN_Z = 0;
  /**
   * 萨米唯一星门的地图坐标；对齐区块边界，便于固定结构放置。
   */
  public static final int SAMI_STARGATE_X = -23_008;
  public static final int SAMI_STARGATE_Z = -40_000;
  public static final int SAMI_STARGATE_CHUNK_X = Math.floorDiv(SAMI_STARGATE_X, 16) - 1;
  public static final int SAMI_STARGATE_CHUNK_Z = Math.floorDiv(SAMI_STARGATE_Z, 16) - 1;

  public static final MapCodec<TerraBiomeSource> TERRA_BIOME_SOURCE = Zinecraft.DIMENSIONS
      .biomeSource("terra", TerraBiomeSource.ACCESS.getCODEC());

  /**
   * 参考泰拉地图相对位置绘制的唯一国家锚点。锚点决定国家范围，国家内部只生成该国群系池。
   */
  public static final List<MapSite> TERRA_MAP = List.of(
      nation(TerraNation.BOLIVAR, -45_000, -18_000),
      nation(TerraNation.COLUMBIA, -33_000, -19_000),
      nation(TerraNation.SAMI, -23_000, -40_000),
      nation(TerraNation.KAZIMIERZ, -18_000, -25_000),
      nation(TerraNation.KJERAG, -19_000, -10_000),
      nation(TerraNation.LEITHANIEN, -7_000, -10_000),
      nation(TerraNation.VICTORIA, -12_000, -1_000),
      nation(TerraNation.MINOS, -30_000, 3_000),
      nation(TerraNation.SARGON, -39_000, 18_000),
      nation(TerraNation.AEGIR, -10_000, 32_000),
      nation(TerraNation.IBERIA, -4_000, 16_000),
      nation(TerraNation.LATERANO, 0, 0),
      nation(TerraNation.SIRACUSA, 6_000, -5_000),
      nation(TerraNation.RIM_BILLITON, 16_000, 0),
      nation(TerraNation.KAZDEL, 14_000, -18_000),
      nation(TerraNation.URSUS, 9_000, -36_000),
      nation(TerraNation.HIGASHI, 26_000, -37_000),
      nation(TerraNation.YAN, 26_000, -23_000),
      nation(TerraNation.DURIN, 34_000, -31_000),
      special("catastrophe_zone", List.of(ModBiome.TERRA_CATASTROPHE_ZONE), 35_000, 14_000)
  );

  public static final DimensionBuilder TERRA = Zinecraft.DIMENSIONS.dimension("terra")
      .biomes(validateMap().stream()
          .map(builder -> new DimensionBiome(builder.key(), builder.climate()))
          .toList())
      .generator(ModDimension::createTerraGenerator)
      .build();

  static {
    Zinecraft.TRANSLATIONS.add("dimension.zinecraft.terra", "泰拉", "Terra");
    Zinecraft.TRANSLATIONS.add("journeymap.zinecraft.terra_nations", "泰拉国家边界", "Terra Nations");
    TerraNation.entries().forEach(nation -> Zinecraft.TRANSLATIONS.add(
        "journeymap.zinecraft.nation." + nation.getId(), nation.getZhCn(), nation.getEnUs()
    ));
  }

  private ModDimension() {
  }

  private static ChunkGenerator createTerraGenerator(DimensionBootstrapContext context) {
    List<TerraBiomeSource.Region> regions = TERRA_MAP.stream()
        .map(site -> new TerraBiomeSource.Region(
            site.id(),
            site.biomes().stream().map(builder -> {
              BiomeBuilder.ClimateCoordinates climate = builder.climateCoordinates();
              return new TerraBiomeSource.BiomeEntry(
                  context.biomes().getOrThrow(builder.key()),
                  climate.temperature(), climate.humidity(), climate.continentalness(),
                  climate.erosion(), climate.depth(), climate.weirdness()
              );
            }).toList(),
            site.x(),
            site.z()
        ))
        .toList();
    return new NoiseBasedChunkGenerator(
        new TerraBiomeSource(
            regions,
            context.biomes().getOrThrow(ModBiome.TERRA_OUTER_OCEAN.key()),
            TERRA_MAP_SIZE,
            OUTER_OCEAN_RING_WIDTH
        ),
        context.noiseSettings()
    );
  }

  private static List<BiomeBuilder> validateMap() {
    var registeredBiomes = new HashSet<>(ModBiome.ALL_TERRA_BIOMES);
    registeredBiomes.removeAll(ModBiome.MAP_SUPPORT_BIOMES);
    var mappedBiomes = new HashSet<net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>>();
    var mappedNations = EnumSet.noneOf(TerraNation.class);
    TERRA_MAP.forEach(site -> {
      if (site.nation() != null && !mappedNations.add(site.nation())) {
        throw new IllegalStateException("泰拉地图国家锚点重复：" + site.nation().getId());
      }
      for (BiomeBuilder biome : site.biomes()) {
        if (site.nation() != null && !biome.path.startsWith(site.nation().getId() + "_")) {
          throw new IllegalStateException("国家群系 ID 必须包含国家名称：" + biome.key().location());
        }
        if (!mappedBiomes.add(biome.key())) {
          throw new IllegalStateException("泰拉地图群系重复归属：" + biome.key().location());
        }
      }
    });
    if (!mappedNations.equals(EnumSet.allOf(TerraNation.class))) {
      var missingNations = EnumSet.allOf(TerraNation.class);
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

  private static MapSite nation(TerraNation nation, int x, int z) {
    List<BiomeBuilder> biomes = ModBiome.NATIONAL_BIOMES.get(nation);
    if (biomes == null || biomes.isEmpty()) {
      throw new IllegalStateException("国家没有专属群系：" + nation.getId());
    }
    return new MapSite(nation.getId(), nation, biomes, x, z);
  }

  private static MapSite special(String id, List<BiomeBuilder> biomes, int x, int z) {
    return new MapSite(id, null, List.copyOf(biomes), x, z);
  }

  public record MapSite(String id, TerraNation nation, List<BiomeBuilder> biomes, int x, int z) {
  }

  public static void bootstrap() {
  }
}
