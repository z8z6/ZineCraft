package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.dimension.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * 将拉特兰范围内低于海平面的水域填成冲积白垩，保证原点固定群系同时也是连续陆地。
 */
public final class LateranoDryLandFeature extends Feature<NoneFeatureConfiguration> {
  public LateranoDryLandFeature() {
    super(NoneFeatureConfiguration.CODEC);
  }

  @Override
  public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
    WorldGenLevel level = context.level();
    if (!level.getLevel().dimension().equals(ModDimension.TERRA.getLevelKey())) return false;

    ChunkPos chunk = new ChunkPos(context.origin());
    int seaLevel = level.getSeaLevel();
    BlockState chalk = ModBlock.INSTANCE.LATERANO_ALLUVIAL_CHALK.get().defaultBlockState();
    boolean placed = false;
    for (int x = chunk.getMinBlockX(); x <= chunk.getMaxBlockX(); x++) {
      for (int z = chunk.getMinBlockZ(); z <= chunk.getMaxBlockZ(); z++) {
        BlockPos biomePos = new BlockPos(x, seaLevel, z);
        if (!level.getBiome(biomePos).is(ModBiome.LATERANO_HOLY_FIELDS)) continue;
        int fillStart = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
        if (fillStart > seaLevel) continue;
        for (int y = fillStart; y <= seaLevel; y++) {
          level.setBlock(new BlockPos(x, y, z), chalk, 2);
          placed = true;
        }
      }
    }
    return placed;
  }
}
