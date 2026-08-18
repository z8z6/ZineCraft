package com.cxxcxx.zinecraft.core.worldgen;

import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.dimension.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * 由地面斜向拔起的源石尖刺群；尺寸参数决定晶簇数量、高度、底径与散布范围。
 */
public final class OriginiumSpireFeature extends Feature<NoneFeatureConfiguration> {
  private final int minSpikes;
  private final int maxSpikes;
  private final int minHeight;
  private final int maxHeight;
  private final int baseRadius;
  private final int spread;

  public OriginiumSpireFeature(int minSpikes, int maxSpikes, int minHeight, int maxHeight, int baseRadius, int spread) {
    super(NoneFeatureConfiguration.CODEC);
    if (minSpikes <= 0 || maxSpikes < minSpikes) throw new IllegalArgumentException("源石晶簇数量无效");
    if (minHeight < 3 || maxHeight < minHeight) throw new IllegalArgumentException("源石尖刺高度无效");
    if (baseRadius <= 0 || spread < baseRadius) throw new IllegalArgumentException("源石晶簇半径无效");
    this.minSpikes = minSpikes;
    this.maxSpikes = maxSpikes;
    this.minHeight = minHeight;
    this.maxHeight = maxHeight;
    this.baseRadius = baseRadius;
    this.spread = spread;
  }

  @Override
  public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
    WorldGenLevel level = context.level();
    if (!level.getLevel().dimension().equals(ModDimension.TERRA.getLevelKey())) return false;

    RandomSource random = context.random();
    int spikeCount = random.nextIntBetweenInclusive(minSpikes, maxSpikes);
    boolean placed = false;
    for (int spike = 0; spike < spikeCount; spike++) {
      int offsetX = random.nextIntBetweenInclusive(-spread, spread);
      int offsetZ = random.nextIntBetweenInclusive(-spread, spread);
      BlockPos sample = context.origin().offset(offsetX, 0, offsetZ);
      BlockPos base = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, sample);
      int height = random.nextIntBetweenInclusive(minHeight, maxHeight);
      double angle = random.nextDouble() * Math.PI * 2.0;
      double lean = 0.18 + random.nextDouble() * 0.24;
      placed |= placeSpire(level, base, height, angle, lean);
    }
    return placed;
  }

  private boolean placeSpire(WorldGenLevel level, BlockPos base, int height, double angle, double lean) {
    BlockState crystal = ModBlock.INSTANCE.ORIGINITE_ORE.block().get().defaultBlockState();
    boolean placed = false;
    for (int y = 0; y < height; y++) {
      double progress = y / (double) Math.max(1, height - 1);
      int centerX = base.getX() + (int) Math.round(Math.cos(angle) * y * lean);
      int centerZ = base.getZ() + (int) Math.round(Math.sin(angle) * y * lean);
      int radius = Math.max(0, (int) Math.ceil(baseRadius * Math.pow(1.0 - progress, 0.72)) - 1);
      for (int x = -radius; x <= radius; x++) {
        for (int z = -radius; z <= radius; z++) {
          if (x * x + z * z > radius * radius + 1) continue;
          BlockPos target = new BlockPos(centerX + x, base.getY() + y, centerZ + z);
          BlockState current = level.getBlockState(target);
          if (current.isAir() || !current.getFluidState().isEmpty() || current.canBeReplaced()) {
            level.setBlock(target, crystal, 2);
            placed = true;
          }
        }
      }
    }
    return placed;
  }
}
