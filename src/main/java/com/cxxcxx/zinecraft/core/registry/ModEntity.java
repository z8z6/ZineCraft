package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.registry.builder.MobBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.FelineVictorianResidentEntity;
import com.cxxcxx.zinecraft.core.entity.SanktaFormalResidentEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ModEntity {
  public static final MobBuilder<SanktaFormalResidentEntity> SANKTA_FORMAL_RESIDENT =
      new MobBuilder<>(
          Zinecraft.ENTITIES,
          "sankta_formal_resident",
          "萨科塔礼服居民",
          SanktaFormalResidentEntity::new,
          MobCategory.CREATURE,
          SanktaFormalResidentEntity::attributes,
          new MobSpawnRestriction<>(
              SpawnPlacementTypes.ON_GROUND,
              Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
              SanktaFormalResidentEntity::canSpawn
          ),
          builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8))
          .spawnEgg(0x8F2A34, 0xF5E7A3, "萨科塔礼服居民刷怪蛋", "Sankta Formal Resident Spawn Egg")
          .noDrops()
          .build();

  public static final MobBuilder<FelineVictorianResidentEntity> FELINE_VICTORIAN_RESIDENT =
      new MobBuilder<>(
          Zinecraft.ENTITIES,
          "feline_victorian_resident",
          "菲林维多利亚居民",
          FelineVictorianResidentEntity::new,
          MobCategory.CREATURE,
          FelineVictorianResidentEntity::attributes,
          new MobSpawnRestriction<>(
              SpawnPlacementTypes.ON_GROUND,
              Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
              FelineVictorianResidentEntity::canSpawn
          ),
          builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8))
          .spawnEgg(0x6E5143, 0xE8D9B5, "菲林维多利亚居民刷怪蛋", "Feline Victorian Resident Spawn Egg")
          .noDrops()
          .build();

  private ModEntity() {
  }

  public static void bootstrap() {
  }
}
