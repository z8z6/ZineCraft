package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class ModEntity {
  public static final ModEntity INSTANCE = new ModEntity();

  public static final Supplier<EntityType<LateranoCitizen>> LATERANO_CITIZEN = Zinecraft.ENTITIES
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
      .build();

  public static final Supplier<EntityType<NationResident>> AEGIR_RESIDENT = resident(
      TerraNation.AEGIR,
      ModBiome.AEGIR_ABYSSAL_SEA,
      Items.NAUTILUS_SHELL,
      SpawnPlacementTypes.IN_WATER,
      true
  );
  public static final Supplier<EntityType<NationResident>> BOLIVAR_RESIDENT = resident(
      TerraNation.BOLIVAR, ModBiome.BOLIVAR_PLAIN, Items.COOKIE
  );
  public static final Supplier<EntityType<NationResident>> HIGASHI_RESIDENT = resident(
      TerraNation.HIGASHI, ModBiome.HIGASHI_SHADOW_RIFT, Items.IRON_SWORD
  );
  public static final Supplier<EntityType<NationResident>> DURIN_RESIDENT = resident(
      TerraNation.DURIN, ModBiome.DURIN_UNDERGROUND_GARDEN, Items.REDSTONE
  );
  public static final Supplier<EntityType<NationResident>> COLUMBIA_RESIDENT = resident(
      TerraNation.COLUMBIA, ModBiome.COLUMBIA_SANDSTONE_WILDS, Items.COMPASS
  );
  public static final Supplier<EntityType<NationResident>> KAZIMIERZ_RESIDENT = resident(
      TerraNation.KAZIMIERZ, ModBiome.KAZIMIERZ_KNIGHTLAND, Items.IRON_SWORD
  );
  public static final Supplier<EntityType<NationResident>> KAZDEL_RESIDENT = resident(
      TerraNation.KAZDEL, ModBiome.KAZDEL_SCARRED_WASTES, Items.IRON_AXE
  );
  public static final Supplier<EntityType<NationResident>> LEITHANIEN_RESIDENT = resident(
      TerraNation.LEITHANIEN, ModBiome.LEITHANIEN_TWILIGHT_FOREST, Items.NOTE_BLOCK
  );
  public static final Supplier<EntityType<NationResident>> RIM_BILLITON_RESIDENT = resident(
      TerraNation.RIM_BILLITON, ModBiome.RIM_BILLITON_MINING_BADLANDS, Items.IRON_PICKAXE
  );
  public static final Supplier<EntityType<NationResident>> MINOS_RESIDENT = resident(
      TerraNation.MINOS, ModBiome.MINOS_SUNLIT_HILLS, Items.SHIELD
  );
  public static final Supplier<EntityType<NationResident>> SARGON_RESIDENT = resident(
      TerraNation.SARGON, ModBiome.SARGON_ROCKY_DESERT, Items.EMERALD
  );
  public static final Supplier<EntityType<NationResident>> SAMI_RESIDENT = resident(
      TerraNation.SAMI, ModBiome.SAMI_FROZEN_FOREST, Items.BONE
  );
  public static final Supplier<EntityType<NationResident>> VICTORIA_RESIDENT = resident(
      TerraNation.VICTORIA, ModBiome.VICTORIA_MISTY_HIGHLANDS, Items.IRON_INGOT
  );
  public static final Supplier<EntityType<NationResident>> URSUS_RESIDENT = resident(
      TerraNation.URSUS, ModBiome.URSUS_FROZEN_STEPPE, Items.IRON_AXE
  );
  public static final Supplier<EntityType<NationResident>> KJERAG_RESIDENT = resident(
      TerraNation.KJERAG, ModBiome.KJERAG_SNOWY_PEAKS, Items.EMERALD
  );
  public static final Supplier<EntityType<NationResident>> SIRACUSA_RESIDENT = resident(
      TerraNation.SIRACUSA, ModBiome.SIRACUSA_RAINY_WOODLAND, Items.SHEARS
  );
  public static final Supplier<EntityType<NationResident>> YAN_RESIDENT = resident(
      TerraNation.YAN, ModBiome.YAN_MOUNTAIN_GROVE, Items.PAPER
  );
  public static final Supplier<EntityType<NationResident>> IBERIA_RESIDENT = resident(
      TerraNation.IBERIA, ModBiome.IBERIA_SALT_DELTA, Items.COD
  );

  public static final List<Supplier<EntityType<NationResident>>> GENERIC_RESIDENTS = List.of(
      AEGIR_RESIDENT, BOLIVAR_RESIDENT, HIGASHI_RESIDENT, DURIN_RESIDENT,
      COLUMBIA_RESIDENT, KAZIMIERZ_RESIDENT, KAZDEL_RESIDENT, LEITHANIEN_RESIDENT,
      RIM_BILLITON_RESIDENT, MINOS_RESIDENT, SARGON_RESIDENT, SAMI_RESIDENT,
      VICTORIA_RESIDENT, URSUS_RESIDENT, KJERAG_RESIDENT, SIRACUSA_RESIDENT,
      YAN_RESIDENT, IBERIA_RESIDENT
  );
  public static final Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>> RESIDENTS_BY_NATION = residentsByNation();

  private static Supplier<EntityType<NationResident>> resident(
      TerraNation nation,
      ResourceKey<Biome> biome,
      Item heldItem
  ) {
    return resident(nation, biome, heldItem, SpawnPlacementTypes.ON_GROUND, false);
  }

  private static Supplier<EntityType<NationResident>> resident(
      TerraNation nation,
      ResourceKey<Biome> biome,
      Item heldItem,
      SpawnPlacementType placement,
      boolean aquatic
  ) {
    NationResidentProfile profile = new NationResidentProfile(nation, heldItem, aquatic);
    return Zinecraft.ENTITIES
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
  }

  private static int eggColor(TerraNation nation, int rotation) {
    int hash = Integer.rotateLeft(nation.getId().hashCode() * 0x45D9F3B, rotation);
    return 0x303030 | (hash & 0xCFCFCF);
  }

  private static Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>> residentsByNation() {
    Map<TerraNation, Supplier<? extends EntityType<? extends Mob>>> residents = new LinkedHashMap<>();
    residents.put(TerraNation.AEGIR, AEGIR_RESIDENT);
    residents.put(TerraNation.BOLIVAR, BOLIVAR_RESIDENT);
    residents.put(TerraNation.HIGASHI, HIGASHI_RESIDENT);
    residents.put(TerraNation.DURIN, DURIN_RESIDENT);
    residents.put(TerraNation.COLUMBIA, COLUMBIA_RESIDENT);
    residents.put(TerraNation.KAZIMIERZ, KAZIMIERZ_RESIDENT);
    residents.put(TerraNation.KAZDEL, KAZDEL_RESIDENT);
    residents.put(TerraNation.LATERANO, LATERANO_CITIZEN);
    residents.put(TerraNation.LEITHANIEN, LEITHANIEN_RESIDENT);
    residents.put(TerraNation.RIM_BILLITON, RIM_BILLITON_RESIDENT);
    residents.put(TerraNation.MINOS, MINOS_RESIDENT);
    residents.put(TerraNation.SARGON, SARGON_RESIDENT);
    residents.put(TerraNation.SAMI, SAMI_RESIDENT);
    residents.put(TerraNation.VICTORIA, VICTORIA_RESIDENT);
    residents.put(TerraNation.URSUS, URSUS_RESIDENT);
    residents.put(TerraNation.KJERAG, KJERAG_RESIDENT);
    residents.put(TerraNation.SIRACUSA, SIRACUSA_RESIDENT);
    residents.put(TerraNation.YAN, YAN_RESIDENT);
    residents.put(TerraNation.IBERIA, IBERIA_RESIDENT);

    if (!residents.keySet().equals(new HashSet<>(TerraNation.getEntries()))) {
      throw new IllegalStateException("必须为全部十九国注册居民");
    }
    return Map.copyOf(residents);
  }
}
