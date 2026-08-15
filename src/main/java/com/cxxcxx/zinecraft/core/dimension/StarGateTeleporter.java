package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.block.ModBlock;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StarGateTeleporter {
  @NotNull
  public static final StarGateTeleporter INSTANCE = new StarGateTeleporter();

  private StarGateTeleporter() {
  }

  @Nullable
  public final DimensionTransition destination(@NotNull ServerLevel source, @NotNull Entity entity, @NotNull BlockPos portalPos) {
    ResourceKey target = source.dimension();
    ResourceKey resourceKey2;
    if (java.util.Objects.equals(target, Level.OVERWORLD)) {
      resourceKey2 = ModDimensions.INSTANCE.getTERRA().getLevelKey();
    } else {
      if (!java.util.Objects.equals(target, ModDimensions.INSTANCE.getTERRA().getLevelKey())) {
        return null;
      }

      resourceKey2 = Level.OVERWORLD;
    }

    ResourceKey resourceKey = resourceKey2;
    ServerLevel serverLevel1 = source.getServer().getLevel(resourceKey);
    if (serverLevel1 == null) {
      return null;
    }

    ServerLevel serverLevel = serverLevel1;
    double d = DimensionType.getTeleportationScale(source.dimensionType(), serverLevel.dimensionType());
    WorldBorder worldBorder1 = serverLevel.getWorldBorder();
    WorldBorder worldBorder = worldBorder1;
    BlockPos blockPos = worldBorder.clampToBounds(portalPos.getX() * d, portalPos.getY(), portalPos.getZ() * d);
    BlockPos blockPos1 = this.findGate(serverLevel, blockPos);
    BlockPos blockPos3 = blockPos1;
    if (blockPos1 == null) {
      blockPos3 = java.util.Objects.equals(serverLevel.dimension(), ModDimensions.INSTANCE.getTERRA().getLevelKey())
          ? this.createTerraGate(serverLevel, blockPos)
          : serverLevel.getSharedSpawnPos();
    }

    BlockPos blockPos2 = blockPos3;
    return new DimensionTransition(
        serverLevel,
        Vec3.atBottomCenterOf((Vec3i) blockPos2).add(0.0, 0.15, 0.0),
        Vec3.ZERO,
        entity.getYRot(),
        entity.getXRot(),
        DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
    );
  }

  private final BlockPos findGate(ServerLevel level, BlockPos around) {
    int i = around.getX() - 12;
    int j = around.getX() + 12;
    if (i <= j) {
      while (true) {
        int k = around.getZ() - 12;
        int l = around.getZ() + 12;
        if (k <= l) {
          while (true) {
            int m = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, i, k);
            int n = RangesKt.coerceAtLeast(m - 10, level.getMinBuildHeight());
            int o = RangesKt.coerceAtMost(m + 10, level.getMaxBuildHeight() - 1);
            if (n <= o) {
              while (true) {
                BlockPos blockPos = new BlockPos(i, n, k);
                if (level.getBlockState(blockPos).is(ModBlock.INSTANCE.getSTARGATE_PORTAL())) {
                  return blockPos;
                }

                if (n == o) {
                  break;
                }

                n++;
              }
            }

            if (k == l) {
              break;
            }

            k++;
          }
        }

        if (i == j) {
          break;
        }

        i++;
      }
    }

    return null;
  }

  private final BlockPos createTerraGate(ServerLevel level, BlockPos around) {
    BlockPos blockPos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, around);
    StarGateStructure starGateStructure = StarGateStructure.INSTANCE;
    LevelAccessor levelAccessor = (LevelAccessor) level;
    return starGateStructure.place(levelAccessor, blockPos, Axis.X, true);
  }
}

