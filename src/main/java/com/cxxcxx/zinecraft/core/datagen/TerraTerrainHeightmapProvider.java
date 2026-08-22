package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.registry.ModBiome;
import com.cxxcxx.zinecraft.core.registry.ModDimension;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;

import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

/** 使用固定种子在规则网格中心采样泰拉实体地表高度。 */
public final class TerraTerrainHeightmapProvider implements DataProvider {
  public static final String OUTPUT_PROPERTY = "zinecraft.terraTerrainHeightmap";
  public static final String SEED_PROPERTY = "zinecraft.terraTerrainSeed";
  public static final String SPACING_PROPERTY = "zinecraft.terraTerrainSpacing";
  public static final long DEFAULT_SEED = 0L;
  public static final int DEFAULT_SPACING = 128;

  private final CompletableFuture<HolderLookup.Provider> registries;
  private final Path outputPath;
  private final long seed;
  private final int spacing;

  public TerraTerrainHeightmapProvider(CompletableFuture<HolderLookup.Provider> registries) {
    this.registries = registries;
    this.outputPath = Path.of(System.getProperty(
        OUTPUT_PROPERTY,
        "build/reports/terra-terrain/heightmap.json"
    )).toAbsolutePath().normalize();
    this.seed = Long.parseLong(System.getProperty(SEED_PROPERTY, Long.toString(DEFAULT_SEED)));
    this.spacing = Integer.parseInt(System.getProperty(
        SPACING_PROPERTY,
        Integer.toString(DEFAULT_SPACING)
    ));
    if (spacing <= 0) throw new IllegalArgumentException("泰拉地形采样间距必须大于 0");
  }

  @Override
  public CompletableFuture<?> run(CachedOutput cache) {
    return registries.thenCompose(provider -> DataProvider.saveStable(cache, sample(provider), outputPath))
        .thenRun(() -> Zinecraft.LOGGER.info("泰拉高度采样 JSON 已写入：{}", outputPath));
  }

  private JsonObject sample(HolderLookup.Provider provider) {
    var settings = provider.lookupOrThrow(Registries.NOISE_SETTINGS)
        .getOrThrow(ModDimension.TERRA_NOISE_SETTINGS);
    var biome = provider.lookupOrThrow(Registries.BIOME)
        .getOrThrow(ModBiome.TERRA_OUTER_OCEAN.key());
    var generator = new NoiseBasedChunkGenerator(new FixedBiomeSource(biome), settings);
    var randomState = RandomState.create(provider.asGetterLookup(), ModDimension.TERRA_NOISE_SETTINGS, seed);
    LevelHeightAccessor heightAccessor = LevelHeightAccessor.create(
        ModDimension.TERRA_MIN_Y,
        ModDimension.TERRA_HEIGHT
    );

    int mapSize = ModDimension.TERRA_MAP_SIZE;
    int originX = -mapSize / 2;
    int originZ = -mapSize / 2;
    int width = Math.ceilDiv(mapSize, spacing);
    int depth = Math.ceilDiv(mapSize, spacing);
    long totalSamples = (long) width * depth;
    int progressInterval = Math.max(1, Math.ceilDiv(depth, 20));
    long startedAt = System.nanoTime();
    Zinecraft.LOGGER.info(
        "开始生成泰拉高度采样：种子={}，间距={}，网格={}x{}，共 {} 个采样点",
        seed, spacing, width, depth, totalSamples
    );
    JsonArray rows = new JsonArray(depth);
    int minimum = Integer.MAX_VALUE;
    int maximum = Integer.MIN_VALUE;
    long sum = 0L;

    for (int gridZ = 0; gridZ < depth; gridZ++) {
      JsonArray row = new JsonArray(width);
      int sampleZ = cellCenter(originZ, mapSize, gridZ);
      for (int gridX = 0; gridX < width; gridX++) {
        int sampleX = cellCenter(originX, mapSize, gridX);
        int surfaceY = generator.getBaseHeight(
            sampleX,
            sampleZ,
            Heightmap.Types.OCEAN_FLOOR_WG,
            heightAccessor,
            randomState
        ) - 1;
        row.add(surfaceY);
        minimum = Math.min(minimum, surfaceY);
        maximum = Math.max(maximum, surfaceY);
        sum += surfaceY;
      }
      rows.add(row);
      int completedRows = gridZ + 1;
      if (completedRows % progressInterval == 0 || completedRows == depth) {
        long completedSamples = (long) completedRows * width;
        long elapsedNanos = System.nanoTime() - startedAt;
        long remainingNanos = completedSamples == 0L
            ? 0L
            : Math.max(0L, elapsedNanos * (totalSamples - completedSamples) / completedSamples);
        double percentage = completedSamples * 100.0 / totalSamples;
        Zinecraft.LOGGER.info(
            "泰拉高度采样进度：{}/{} 行，{}/{} 点（{}%），已用 {}，预计剩余 {}",
            completedRows,
            depth,
            completedSamples,
            totalSamples,
            String.format(Locale.ROOT, "%.1f", percentage),
            formatDuration(elapsedNanos),
            formatDuration(remainingNanos)
        );
      }
    }

    JsonObject root = new JsonObject();
    root.addProperty("schema_version", 1);
    root.addProperty("dimension", ModDimension.TERRA.levelKey().location().toString());
    root.addProperty("seed", seed);
    root.addProperty("heightmap_type", "minecraft:ocean_floor_wg");
    root.addProperty("value_semantics", "top_solid_block_y");
    root.addProperty("origin_x", originX);
    root.addProperty("origin_z", originZ);
    root.addProperty("map_size", mapSize);
    root.addProperty("spacing", spacing);
    root.addProperty("sample_position", "cell_center_clamped_at_map_edge");
    root.addProperty("array_order", "heights[z][x]");
    root.addProperty("width", width);
    root.addProperty("depth", depth);
    JsonObject statistics = new JsonObject();
    statistics.addProperty("minimum_y", minimum);
    statistics.addProperty("maximum_y", maximum);
    statistics.addProperty("average_y", (double) sum / ((long) width * depth));
    root.add("statistics", statistics);
    root.add("heights", rows);
    return root;
  }

  private int cellCenter(int origin, int mapSize, int gridIndex) {
    int start = origin + gridIndex * spacing;
    int endExclusive = Math.min(origin + mapSize, start + spacing);
    return start + (endExclusive - start) / 2;
  }

  private static String formatDuration(long nanos) {
    long totalSeconds = Math.max(0L, nanos / 1_000_000_000L);
    long hours = totalSeconds / 3600L;
    long minutes = totalSeconds % 3600L / 60L;
    long seconds = totalSeconds % 60L;
    return hours > 0L
        ? String.format(Locale.ROOT, "%d:%02d:%02d", hours, minutes, seconds)
        : String.format(Locale.ROOT, "%02d:%02d", minutes, seconds);
  }

  @Override
  public String getName() {
    return "Terra fixed-seed terrain height samples";
  }
}
