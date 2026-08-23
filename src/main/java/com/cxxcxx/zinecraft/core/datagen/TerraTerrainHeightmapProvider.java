package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import com.cxxcxx.zinecraft.core.registry.ModBiome;
import com.cxxcxx.zinecraft.core.registry.ModDensityFunction;
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
import net.minecraft.world.level.levelgen.DensityFunction;
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
    int emergencyCeilingSurfaceY = (ModDensityFunction.TERRAIN_CEILING_FADE_START_Y
        + ModDensityFunction.TERRAIN_CEILING_FADE_END_Y) / 2 - 1;
    long emergencyCeilingHitCount = 0L;

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
        if (surfaceY == emergencyCeilingSurfaceY) emergencyCeilingHitCount++;
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
    statistics.addProperty("configured_city_maximum_surface_y",
        ModDensityFunction.MAXIMUM_CITY_SURFACE_Y);
    statistics.addProperty("emergency_ceiling_surface_y", emergencyCeilingSurfaceY);
    statistics.addProperty("emergency_ceiling_hit_count", emergencyCeilingHitCount);
    root.add("statistics", statistics);
    root.add("region_validation", sampleRegions(generator, heightAccessor, randomState));
    root.add("heights", rows);
    return root;
  }

  private JsonObject sampleRegions(
      NoiseBasedChunkGenerator generator,
      LevelHeightAccessor heightAccessor,
      RandomState randomState
  ) {
    JsonArray mismatches = new JsonArray();
    int sampledCities = 0;
    int sampledColumns = 0;
    int mismatchCount = 0;
    DensityFunction finalDensity = randomState.router().finalDensity();
    for (var nation : TerraLayoutResource.load().nations()) {
      if (nation.nation().isUnderground()) continue;
      for (var city : nation.cities()) {
        if (city.regions().isEmpty()) continue;
        var region = city.regions().getFirst();
        var bounds = region.mobilePlotBounds();
        int minX = (int) Math.floor(bounds.center().x() - bounds.halfSizeX()) + 4;
        int maxX = (int) Math.ceil(bounds.center().x() + bounds.halfSizeX()) - 5;
        int minZ = (int) Math.floor(bounds.center().z() - bounds.halfSizeZ()) + 4;
        int maxZ = (int) Math.ceil(bounds.center().z() + bounds.halfSizeZ()) - 5;
        int centerX = (int) Math.floor(bounds.center().x());
        int centerZ = (int) Math.floor(bounds.center().z());
        int[][] positions = {
            {centerX, centerZ},
            {minX, minZ},
            {minX, maxZ},
            {maxX, minZ},
            {maxX, maxZ}
        };
        for (int[] position : positions) {
          int surfaceY = sampleSurfaceY(
              generator, position[0], position[1], heightAccessor, randomState
          );
          sampledColumns++;
          if (surfaceY == city.terrainProfile().groundY()) continue;
          mismatchCount++;
          if (mismatches.size() < 64) {
            JsonObject mismatch = new JsonObject();
            mismatch.addProperty("city", city.city().id());
            mismatch.addProperty("region", region.region().id());
            mismatch.addProperty("x", position[0]);
            mismatch.addProperty("z", position[1]);
            mismatch.addProperty("expected_y", city.terrainProfile().groundY());
            mismatch.addProperty("actual_y", surfaceY);
            mismatch.addProperty("density_at_expected_y", finalDensity.compute(new PointContext(
                position[0], city.terrainProfile().groundY(), position[1]
            )));
            mismatch.addProperty("density_at_expected_y_plus_one", finalDensity.compute(new PointContext(
                position[0], city.terrainProfile().groundY() + 1, position[1]
            )));
            mismatch.addProperty("density_at_actual_y", finalDensity.compute(new PointContext(
                position[0], surfaceY, position[1]
            )));
            mismatches.add(mismatch);
          }
        }
        sampledCities++;
      }
    }
    JsonObject validation = new JsonObject();
    validation.addProperty("sample_strategy", "first_region_center_and_four_inset_corners_per_surface_city");
    validation.addProperty("sampled_cities", sampledCities);
    validation.addProperty("sampled_columns", sampledColumns);
    validation.addProperty("mismatch_count", mismatchCount);
    validation.add("mismatches", mismatches);
    return validation;
  }

  private static int sampleSurfaceY(
      NoiseBasedChunkGenerator generator,
      int x,
      int z,
      LevelHeightAccessor heightAccessor,
      RandomState randomState
  ) {
    return generator.getBaseHeight(
        x,
        z,
        Heightmap.Types.OCEAN_FLOOR_WG,
        heightAccessor,
        randomState
    ) - 1;
  }

  private record PointContext(int blockX, int blockY, int blockZ)
      implements DensityFunction.FunctionContext {
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
