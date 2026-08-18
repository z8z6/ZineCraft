package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.entity.MobEntry;
import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.entity.resident.LateranoCitizen;
import com.cxxcxx.zinecraft.core.entity.resident.NationResident;
import com.cxxcxx.zinecraft.core.entity.resident.NationResidentProfile;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModEntity {
  private static final List<Supplier<EntityType<NationResident>>> MUTABLE_GENERIC_RESIDENTS =
      new ArrayList<>();
  public static final List<Supplier<EntityType<NationResident>>> GENERIC_RESIDENTS =
      List.copyOf(MUTABLE_GENERIC_RESIDENTS);
  private static final Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>>
      MUTABLE_RESIDENTS_BY_NATION = new EnumMap<>(TerraNation.class);

  public static final MobEntry<NationResident> AEGIR_RESIDENT = resident(
      TerraNation.AEGIR,
      ModBiome.AEGIR_ABYSSAL_SEA,
      Items.NAUTILUS_SHELL,
      SpawnPlacementTypes.IN_WATER,
      true
  );
  public static final MobEntry<NationResident> BOLIVAR_RESIDENT = resident(
      TerraNation.BOLIVAR, ModBiome.BOLIVAR_PLAIN, Items.COOKIE
  );
  public static final MobEntry<NationResident> HIGASHI_RESIDENT = resident(
      TerraNation.HIGASHI, ModBiome.HIGASHI_SHADOW_RIFT, Items.IRON_SWORD
  );
  public static final MobEntry<NationResident> DURIN_RESIDENT = resident(
      TerraNation.DURIN, ModBiome.DURIN_UNDERGROUND_GARDEN, Items.REDSTONE
  );
  public static final MobEntry<NationResident> COLUMBIA_RESIDENT = resident(
      TerraNation.COLUMBIA, ModBiome.COLUMBIA_SANDSTONE_WILDS, Items.COMPASS
  );
  public static final MobEntry<NationResident> KAZIMIERZ_RESIDENT = resident(
      TerraNation.KAZIMIERZ, ModBiome.KAZIMIERZ_KNIGHTLAND, Items.IRON_SWORD
  );
  public static final MobEntry<NationResident> KAZDEL_RESIDENT = resident(
      TerraNation.KAZDEL, ModBiome.KAZDEL_SCARRED_WASTES, Items.IRON_AXE
  );
  public static final MobEntry<NationResident> LEITHANIEN_RESIDENT = resident(
      TerraNation.LEITHANIEN, ModBiome.LEITHANIEN_TWILIGHT_FOREST, Items.NOTE_BLOCK
  );
  public static final MobEntry<NationResident> RIM_BILLITON_RESIDENT = resident(
      TerraNation.RIM_BILLITON, ModBiome.RIM_BILLITON_MINING_BADLANDS, Items.IRON_PICKAXE
  );
  public static final MobEntry<NationResident> MINOS_RESIDENT = resident(
      TerraNation.MINOS, ModBiome.MINOS_SUNLIT_HILLS, Items.SHIELD
  );
  public static final MobEntry<NationResident> SARGON_RESIDENT = resident(
      TerraNation.SARGON, ModBiome.SARGON_ROCKY_DESERT, Items.EMERALD
  );
  public static final MobEntry<NationResident> SAMI_RESIDENT = resident(
      TerraNation.SAMI, ModBiome.SAMI_FROZEN_FOREST, Items.BONE
  );
  public static final MobEntry<NationResident> VICTORIA_RESIDENT = resident(
      TerraNation.VICTORIA, ModBiome.VICTORIA_MISTY_HIGHLANDS, Items.IRON_INGOT
  );
  public static final MobEntry<NationResident> URSUS_RESIDENT = resident(
      TerraNation.URSUS, ModBiome.URSUS_FROZEN_STEPPE, Items.IRON_AXE
  );
  public static final MobEntry<NationResident> KJERAG_RESIDENT = resident(
      TerraNation.KJERAG, ModBiome.KJERAG_SNOWY_PEAKS, Items.EMERALD
  );
  public static final MobEntry<NationResident> SIRACUSA_RESIDENT = resident(
      TerraNation.SIRACUSA, ModBiome.SIRACUSA_RAINY_WOODLAND, Items.SHEARS
  );
  public static final MobEntry<NationResident> YAN_RESIDENT = resident(
      TerraNation.YAN, ModBiome.YAN_MOUNTAIN_GROVE, Items.PAPER
  );
  public static final MobEntry<NationResident> IBERIA_RESIDENT = resident(
      TerraNation.IBERIA, ModBiome.IBERIA_SALT_DELTA, Items.COD
  );
  public static final MobEntry<LateranoCitizen> LATERANO_CITIZEN = registerResident(
      TerraNation.LATERANO,
      Zinecraft.ENTITIES
      .mob(
          "laterano_citizen",
          "拉特兰公民",
          "Laterano Citizen",
          LateranoCitizen::new,
          MobCategory.CREATURE,
          LateranoCitizen.ACCESS::attributes,
          new MobSpawnRestriction<>(
              SpawnPlacementTypes.ON_GROUND,
              Types.MOTION_BLOCKING_NO_LEAVES,
              LateranoCitizen.ACCESS::canSpawn
          ),
          builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8)
      )
      .naturalSpawn(10, 1, 2, BiomeSelection.of(ModBiome.LATERANO_HOLY_FIELDS))
      .spawnEgg(15853776, 14267980, "拉特兰公民刷怪蛋", "Laterano Citizen Spawn Egg")
      .drop(Items.COOKIE)
          .build()
  );
  public static final Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>>
      RESIDENTS_BY_NATION = validatedResidents();

  private static MobEntry<NationResident> resident(
      TerraNation nation,
      ResourceKey<Biome> biome,
      Item heldItem
  ) {
    return resident(nation, biome, heldItem, SpawnPlacementTypes.ON_GROUND, false);
  }

  private ModEntity() {
  }

  private static int eggColor(TerraNation nation, int rotation) {
    int hash = Integer.rotateLeft(nation.getId().hashCode() * 0x45D9F3B, rotation);
    return 0x303030 | (hash & 0xCFCFCF);
  }

  private static MobEntry<NationResident> resident(
      TerraNation nation,
      ResourceKey<Biome> biome,
      Item heldItem,
      SpawnPlacementType placement,
      boolean aquatic
  ) {
    NationResidentProfile profile = new NationResidentProfile(nation, heldItem, aquatic);
    MobEntry<NationResident> entry = Zinecraft.ENTITIES
        .mob(
            nation.getId() + "_resident",
            nation.getZhCn() + "居民",
            nation.getEnUs() + " Resident",
            (type, level) -> new NationResident(type, level, profile),
            MobCategory.CREATURE,
            NationResident.ACCESS::attributes,
            new MobSpawnRestriction<>(
                placement,
                Types.MOTION_BLOCKING_NO_LEAVES,
                NationResident.ACCESS::canSpawn
            ),
            builder -> builder.sized(0.6F, 1.8F).clientTrackingRange(8)
        )
        .naturalSpawn(8, 1, 3, BiomeSelection.of(biome))
        .spawnEgg(
            eggColor(nation, 0),
            eggColor(nation, 11),
            nation.getZhCn() + "居民刷怪蛋",
            nation.getEnUs() + " Resident Spawn Egg"
        )
        .drop(heldItem)
        .build();
    MUTABLE_GENERIC_RESIDENTS.add(entry);
    return registerResident(nation, entry);
  }

  private static <T extends Mob> MobEntry<T> registerResident(TerraNation nation, MobEntry<T> entry) {
    if (MUTABLE_RESIDENTS_BY_NATION.putIfAbsent(nation, entry) != null) {
      throw new IllegalArgumentException("国家居民重复注册: " + nation.getId());
    }
    return entry;
  }

  private static Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>> validatedResidents() {
    if (MUTABLE_RESIDENTS_BY_NATION.size() != TerraNation.getEntries().size()) {
      throw new IllegalStateException("必须为全部十九国注册居民");
    }
    return Map.copyOf(MUTABLE_RESIDENTS_BY_NATION);
  }

  public static void bootstrap() {
  }
}
