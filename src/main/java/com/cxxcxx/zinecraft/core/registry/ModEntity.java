package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.registry.builder.MobBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.FelineVictorianResidentEntity;
import com.cxxcxx.zinecraft.core.entity.SanktaFormalResidentEntity;
import com.cxxcxx.zinecraft.core.entity.TerraBeastEntity;
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

  public static final MobBuilder<TerraBeastEntity> SANDBEAST = beast(
      "sandbeast", "沙地兽", "Sandbeast", TerraBeastEntity::sandbeastAttributes,
      TerraBeastEntity::canSpawnInDesert, 1.45F, 1.25F, 0x4B382B, 0xC69B57);

  public static final MobBuilder<TerraBeastEntity> RIVENBEAST = beast(
      "rivenbeast", "裂兽", "Rivenbeast", TerraBeastEntity::rivenbeastAttributes,
      TerraBeastEntity::canSpawnInColdLand, 1.45F, 1.35F, 0x5D5551, 0xD6D0C0);

  public static final MobBuilder<TerraBeastEntity> CLAMPBEAST = beast(
      "clampbeast", "钳兽", "Clampbeast", TerraBeastEntity::clampbeastAttributes,
      TerraBeastEntity::canSpawnInWetLand, 1.35F, 0.85F, 0x564832, 0xBC672E);

  public static final MobBuilder<TerraBeastEntity> PACKBEAST = beast(
      "packbeast", "驮兽", "Packbeast", TerraBeastEntity::packbeastAttributes,
      TerraBeastEntity::canSpawnInDesert, 1.65F, 1.35F, 0x6B574B, 0xD0C0A4);

  private static MobBuilder<TerraBeastEntity> beast(
      String path,
      String zhCn,
      String enUs,
      java.util.function.Supplier<? extends net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder> attributes,
      net.minecraft.world.entity.SpawnPlacements.SpawnPredicate<TerraBeastEntity> predicate,
      float width,
      float height,
      int primaryEggColor,
      int secondaryEggColor
  ) {
    return new MobBuilder<>(
        Zinecraft.ENTITIES,
        path,
        zhCn,
        TerraBeastEntity::new,
        MobCategory.CREATURE,
        attributes,
        new MobSpawnRestriction<>(
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            predicate
        ),
        builder -> builder.sized(width, height).clientTrackingRange(8))
        .enUs(enUs)
        .spawnEgg(primaryEggColor, secondaryEggColor, zhCn + "刷怪蛋", enUs + " Spawn Egg")
        .noDrops()
        .build();
  }

  private ModEntity() {
  }

  public static void bootstrap() {
  }
}
