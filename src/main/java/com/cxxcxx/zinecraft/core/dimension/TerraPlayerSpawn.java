package com.cxxcxx.zinecraft.core.dimension;

import com.cxxcxx.zinecraft.core.registry.ModDimension;
import com.cxxcxx.zinecraft.core.registry.ModCityRegion;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutResource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * 将首次进入服务器的玩家送到泰拉，并把首次重生点设在泰拉。
 */
public final class TerraPlayerSpawn {
  private static final String INITIAL_SPAWN_TAG = "zinecraft_terra_initial_spawn";

  private TerraPlayerSpawn() {
  }

  public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
    if (!(event.getEntity() instanceof ServerPlayer player)
        || player.getPersistentData().getBoolean(INITIAL_SPAWN_TAG)) {
      return;
    }
    // 升级旧存档时不强制迁移已经游玩过的角色，只让真正的新玩家采用泰拉初始出生。
    if (player.getStats().getValue(Stats.CUSTOM, Stats.LEAVE_GAME) > 0) {
      player.getPersistentData().putBoolean(INITIAL_SPAWN_TAG, true);
      return;
    }

    ServerLevel terra = player.getServer().getLevel(ModDimension.TERRA.levelKey());
    if (terra == null) {
      return;
    }

    var coreRegion = TerraLayoutResource.findRegion(ModCityRegion.ZWILLINGSTURME_CORE);
    if (coreRegion.isEmpty()) return;
    var localCenter = coreRegion.get().regionLayout().localCenter();
    BlockPos column = new BlockPos(
        localCenter.chunkX() * 16 + 8,
        0,
        localCenter.chunkZ() * 16 + 8
    );
    terra.getChunkAt(column);
    BlockPos spawn = terra.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column);
    player.setRespawnPosition(terra.dimension(), spawn, 0.0F, true, false);
    player.teleportTo(terra, spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5, 0.0F, 0.0F);
    player.getPersistentData().putBoolean(INITIAL_SPAWN_TAG, true);
  }
}
