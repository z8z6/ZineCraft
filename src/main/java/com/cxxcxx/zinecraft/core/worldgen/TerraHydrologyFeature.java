package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * 在国家群系内部雕刻河网，并将世界边界内侧 1000 格塑造成连续外海。
 * 河道不替换群系，因此玩家始终能看到其所属国家的群系名称。
 */
public final class TerraHydrologyFeature extends Feature<NoneFeatureConfiguration> {
  private static final int OUTER_OCEAN_MAX_DEPTH = 28;
  private static final int RIVER_MIN_DEPTH = 3;

  public TerraHydrologyFeature() {
    super(NoneFeatureConfiguration.CODEC);
  }

  private static boolean isOuterOcean(int x, int z) {
    int oceanStart = ModDimension.TERRA_MAP_SIZE / 2 - ModDimension.OUTER_OCEAN_RING_WIDTH;
    return Math.abs(x) >= oceanStart || Math.abs(z) >= oceanStart;
  }

  private static boolean isRiverChannel(int x, int z) {
    int warpedX = x + (int) Math.round(Math.sin(z / 1800.0) * 720.0);
    int warpedZ = z + (int) Math.round(Math.sin(x / 2100.0) * 640.0);
    return distanceToGridLine(warpedX, 8_000) <= 5 || distanceToGridLine(warpedZ, 9_000) <= 5;
  }

  private static int distanceToGridLine(int coordinate, int spacing) {
    int remainder = Math.floorMod(coordinate, spacing);
    return Math.min(remainder, spacing - remainder);
  }

  private static boolean carveOuterOcean(WorldGenLevel level, int x, int z, int seaLevel) {
    int halfSize = ModDimension.TERRA_MAP_SIZE / 2;
    int distanceToBorder = halfSize - Math.max(Math.abs(x), Math.abs(z));
    double depthProgress = 1.0 - Math.max(0, distanceToBorder)
        / (double) ModDimension.OUTER_OCEAN_RING_WIDTH;
    int variation = coordinateVariation(x, z, 3);
    int floorY = seaLevel - 4 - (int) Math.round(depthProgress * (OUTER_OCEAN_MAX_DEPTH - 4)) - variation;
    int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;
    reshapeWaterColumn(level, x, z, floorY, seaLevel, Math.max(surfaceY, seaLevel));
    return true;
  }

  private static boolean carveRiver(WorldGenLevel level, int x, int z) {
    int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;
    if (surfaceY <= level.getMinBuildHeight() + RIVER_MIN_DEPTH) return false;
    BlockState surface = level.getBlockState(new BlockPos(x, surfaceY, z));
    if (!surface.getFluidState().isEmpty()) return false;

    int waterY = surfaceY - 1;
    int floorY = waterY - RIVER_MIN_DEPTH - coordinateVariation(x, z, 2);
    reshapeWaterColumn(level, x, z, floorY, waterY, surfaceY);
    return true;
  }

  private static void reshapeWaterColumn(
      WorldGenLevel level,
      int x,
      int z,
      int floorY,
      int waterY,
      int clearTopY
  ) {
    BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
    BlockState bed = ((x ^ z) & 3) == 0
        ? Blocks.SAND.defaultBlockState()
        : Blocks.GRAVEL.defaultBlockState();
    for (int y = floorY - 1; y <= floorY; y++) {
      level.setBlock(cursor.set(x, y, z), bed, 2);
    }
    for (int y = floorY + 1; y <= clearTopY; y++) {
      BlockState state = y <= waterY ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
      level.setBlock(cursor.set(x, y, z), state, 2);
    }
  }

  private static int coordinateVariation(int x, int z, int bound) {
    int mixed = x * 73_428_767 ^ z * 91_287_131;
    return Math.floorMod(mixed, bound);
  }

  @Override
  public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
    WorldGenLevel level = context.level();
    if (!level.getLevel().dimension().equals(ModDimension.TERRA.levelKey())) return false;

    ChunkPos chunk = new ChunkPos(context.origin());
    int seaLevel = level.getSeaLevel();
    boolean changed = false;
    for (int x = chunk.getMinBlockX(); x <= chunk.getMaxBlockX(); x++) {
      for (int z = chunk.getMinBlockZ(); z <= chunk.getMaxBlockZ(); z++) {
        if (isOuterOcean(x, z)) {
          changed |= carveOuterOcean(level, x, z, seaLevel);
        } else if (isRiverChannel(x, z)) {
          changed |= carveRiver(level, x, z);
        }
      }
    }
    return changed;
  }
}
