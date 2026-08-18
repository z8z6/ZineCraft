package com.cxxcxx.zinecraft.core.structure.stargate;

import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.dimension.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class StarGateTeleporter {
  public static final StarGateTeleporter INSTANCE = new StarGateTeleporter();

  private static final int HORIZONTAL_SEARCH_RADIUS = 20;
  private static final int VERTICAL_SEARCH_RADIUS = 10;
  private static final double ARRIVAL_Y_OFFSET = 0.15;

  private StarGateTeleporter() {
  }

  @Nullable
  private static ResourceKey<Level> targetDimension(ResourceKey<Level> sourceDimension) {
    if (sourceDimension.equals(Level.OVERWORLD)) {
      return ModDimension.TERRA.getLevelKey();
    }
    if (sourceDimension.equals(ModDimension.TERRA.getLevelKey())) {
      return Level.OVERWORLD;
    }
    return null;
  }

  private static BlockPos scaledPosition(ServerLevel source, ServerLevel target, BlockPos portalPos) {
    double scale = DimensionType.getTeleportationScale(source.dimensionType(), target.dimensionType());
    WorldBorder border = target.getWorldBorder();
    return border.clampToBounds(portalPos.getX() * scale, portalPos.getY(), portalPos.getZ() * scale);
  }

  @Nullable
  private static BlockPos findGate(ServerLevel level, BlockPos around) {
    for (int x = around.getX() - HORIZONTAL_SEARCH_RADIUS;
         x <= around.getX() + HORIZONTAL_SEARCH_RADIUS;
         x++) {
      for (int z = around.getZ() - HORIZONTAL_SEARCH_RADIUS;
           z <= around.getZ() + HORIZONTAL_SEARCH_RADIUS;
           z++) {
        int surfaceHeight = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        int minY = Math.max(surfaceHeight - VERTICAL_SEARCH_RADIUS, level.getMinBuildHeight());
        int maxY = Math.min(surfaceHeight + VERTICAL_SEARCH_RADIUS, level.getMaxBuildHeight() - 1);
        for (int y = minY; y <= maxY; y++) {
          BlockPos candidate = new BlockPos(x, y, z);
          if (level.getBlockState(candidate).is(ModBlock.STARGATE_PORTAL.get())) {
            return candidate;
          }
        }
      }
    }
    return null;
  }

  private static BlockPos createTerraGate(ServerLevel level, BlockPos around) {
    BlockPos base = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, around);
    return StarGateStructure.INSTANCE.place(level, base, Axis.X, true);
  }

  @Nullable
  public DimensionTransition destination(
      ServerLevel source,
      Entity entity,
      BlockPos portalPos
  ) {
    ResourceKey<Level> targetDimension = targetDimension(source.dimension());
    if (targetDimension == null) {
      return null;
    }

    ServerLevel target = source.getServer().getLevel(targetDimension);
    if (target == null) {
      return null;
    }

    BlockPos searchCenter = scaledPosition(source, target, portalPos);
    BlockPos destination = findGate(target, searchCenter);
    if (destination == null) {
      destination = target.dimension().equals(ModDimension.TERRA.getLevelKey())
          ? createTerraGate(target, searchCenter)
          : target.getSharedSpawnPos();
    }

    return new DimensionTransition(
        target,
        Vec3.atBottomCenterOf(destination).add(0.0, ARRIVAL_Y_OFFSET, 0.0),
        Vec3.ZERO,
        entity.getYRot(),
        entity.getXRot(),
        DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET)
    );
  }
}
