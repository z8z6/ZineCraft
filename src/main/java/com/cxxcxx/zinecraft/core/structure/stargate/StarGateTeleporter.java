package com.cxxcxx.zinecraft.core.structure.stargate;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class StarGateTeleporter {
  public static final StarGateTeleporter INSTANCE = new StarGateTeleporter();

  private static final double ARRIVAL_Y_OFFSET = 0.15;

  private StarGateTeleporter() {
  }

  @Nullable
  public DimensionTransition destination(
      ServerLevel source,
      Entity entity,
      BlockPos portalPos
  ) {
    if (!source.dimension().equals(ModDimension.TERRA.levelKey())) {
      return null;
    }

    ServerLevel target = source.getServer().getLevel(Level.OVERWORLD);
    if (target == null) {
      return null;
    }

    BlockPos destination = target.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, target.getSharedSpawnPos());

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
