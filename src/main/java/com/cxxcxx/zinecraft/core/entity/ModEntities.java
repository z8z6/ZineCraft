package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.entity.EntityCatalog;
import com.cxxcxx.zinecraft.api.entity.MobEntry;
import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public final class ModEntities {
  @NotNull
  public static final ModEntities INSTANCE = new ModEntities();
  @NotNull
  public static final MobEntry<LateranoCitizen> LATERANO_CITIZEN;
  @NotNull
  public static final ItemEntry<SpawnEggItem> LATERANO_CITIZEN_SPAWN_EGG;
  @NotNull
  public static final MobEntry<NationResident> AEGIR_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> BOLIVAR_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> HIGASHI_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> DURIN_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> COLUMBIA_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> KAZIMIERZ_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> KAZDEL_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> LEITHANIEN_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> RIM_BILLITON_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> MINOS_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> SARGON_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> SAMI_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> VICTORIA_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> URSUS_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> KJERAG_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> SIRACUSA_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> YAN_RESIDENT;
  @NotNull
  public static final MobEntry<NationResident> IBERIA_RESIDENT;
  @NotNull
  private static final LinkedHashMap<TerraNation, MobEntry<NationResident>> GENERIC_RESIDENTS_BY_NATION;

  static {
    EntityCatalog entityCatalog = Zinecraft.ENTITIES;
    EntityFactory entityFactory = LateranoCitizen::new;
    MobCategory mobCategory = MobCategory.CREATURE;
    Supplier function0 = LateranoCitizen.ACCESS::attributes;
    SpawnPlacementType spawnPlacementType1 = SpawnPlacementTypes.ON_GROUND;
    MobEntry mobEntry2 = entityCatalog.mob(
        "laterano_citizen",
        "拉特兰公民",
        "Laterano Citizen",
        entityFactory,
        mobCategory,
        function0,
        new MobSpawnRestriction(spawnPlacementType1, Types.MOTION_BLOCKING_NO_LEAVES, LateranoCitizen.ACCESS::canSpawn),
        ModEntities::LATERANO_CITIZENHelper0
    );
    LATERANO_CITIZEN = mobEntry2.naturalSpawn(10, 1, 2, BiomeSelection.of(NationBiomes.LATERANO_HOLY_FIELDS));
    LATERANO_CITIZEN_SPAWN_EGG = MobEntry.spawnEggWithDefaults(LATERANO_CITIZEN, 15853776, 14267980, "拉特兰公民生成蛋", "Laterano Citizen Spawn Egg", null, 16, null);
    ModEntities modEntities = INSTANCE;
    TerraNation terraNation1 = TerraNation.AEGIR;
    ResourceKey resourceKey = NationBiomes.AEGIR_ABYSSAL_SEA;
    Item item = Items.NAUTILUS_SHELL;
    SpawnPlacementType spawnPlacementType = SpawnPlacementTypes.IN_WATER;
    AEGIR_RESIDENT = modEntities.resident(terraNation1, resourceKey, item, spawnPlacementType, true);
    ModEntities modEntities1 = INSTANCE;
    terraNation1 = TerraNation.BOLIVAR;
    resourceKey = NationBiomes.BOLIVAR_PLAIN;
    item = Items.COOKIE;
    BOLIVAR_RESIDENT = residentWithDefaults(modEntities1, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities2 = INSTANCE;
    terraNation1 = TerraNation.HIGASHI;
    resourceKey = NationBiomes.HIGASHI_SHADOW_RIFT;
    item = Items.IRON_SWORD;
    HIGASHI_RESIDENT = residentWithDefaults(modEntities2, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities3 = INSTANCE;
    terraNation1 = TerraNation.DURIN;
    resourceKey = NationBiomes.DURIN_UNDERGROUND_GARDEN;
    item = Items.REDSTONE;
    DURIN_RESIDENT = residentWithDefaults(modEntities3, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities4 = INSTANCE;
    terraNation1 = TerraNation.COLUMBIA;
    resourceKey = NationBiomes.COLUMBIA_SANDSTONE_WILDS;
    item = Items.COMPASS;
    COLUMBIA_RESIDENT = residentWithDefaults(modEntities4, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities5 = INSTANCE;
    terraNation1 = TerraNation.KAZIMIERZ;
    resourceKey = NationBiomes.KAZIMIERZ_KNIGHTLAND;
    item = Items.IRON_SWORD;
    KAZIMIERZ_RESIDENT = residentWithDefaults(modEntities5, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities6 = INSTANCE;
    terraNation1 = TerraNation.KAZDEL;
    resourceKey = NationBiomes.KAZDEL_SCARRED_WASTES;
    item = Items.IRON_AXE;
    KAZDEL_RESIDENT = residentWithDefaults(modEntities6, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities7 = INSTANCE;
    terraNation1 = TerraNation.LEITHANIEN;
    resourceKey = NationBiomes.LEITHANIEN_TWILIGHT_FOREST;
    item = Items.NOTE_BLOCK;
    LEITHANIEN_RESIDENT = residentWithDefaults(modEntities7, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities8 = INSTANCE;
    terraNation1 = TerraNation.RIM_BILLITON;
    resourceKey = NationBiomes.RIM_BILLITON_MINING_BADLANDS;
    item = Items.IRON_PICKAXE;
    RIM_BILLITON_RESIDENT = residentWithDefaults(modEntities8, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities9 = INSTANCE;
    terraNation1 = TerraNation.MINOS;
    resourceKey = NationBiomes.MINOS_SUNLIT_HILLS;
    item = Items.SHIELD;
    MINOS_RESIDENT = residentWithDefaults(modEntities9, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities10 = INSTANCE;
    terraNation1 = TerraNation.SARGON;
    resourceKey = NationBiomes.SARGON_ROCKY_DESERT;
    item = Items.EMERALD;
    SARGON_RESIDENT = residentWithDefaults(modEntities10, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities11 = INSTANCE;
    terraNation1 = TerraNation.SAMI;
    resourceKey = NationBiomes.SAMI_FROZEN_FOREST;
    item = Items.BONE;
    SAMI_RESIDENT = residentWithDefaults(modEntities11, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities12 = INSTANCE;
    terraNation1 = TerraNation.VICTORIA;
    resourceKey = NationBiomes.VICTORIA_MISTY_HIGHLANDS;
    item = Items.IRON_INGOT;
    VICTORIA_RESIDENT = residentWithDefaults(modEntities12, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities13 = INSTANCE;
    terraNation1 = TerraNation.URSUS;
    resourceKey = NationBiomes.URSUS_FROZEN_STEPPE;
    item = Items.IRON_AXE;
    URSUS_RESIDENT = residentWithDefaults(modEntities13, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities14 = INSTANCE;
    terraNation1 = TerraNation.KJERAG;
    resourceKey = NationBiomes.KJERAG_SNOWY_PEAKS;
    item = Items.EMERALD;
    KJERAG_RESIDENT = residentWithDefaults(modEntities14, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities15 = INSTANCE;
    terraNation1 = TerraNation.SIRACUSA;
    resourceKey = NationBiomes.SIRACUSA_RAINY_WOODLAND;
    item = Items.SHEARS;
    SIRACUSA_RESIDENT = residentWithDefaults(modEntities15, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities16 = INSTANCE;
    terraNation1 = TerraNation.YAN;
    resourceKey = NationBiomes.YAN_MOUNTAIN_GROVE;
    item = Items.PAPER;
    YAN_RESIDENT = residentWithDefaults(modEntities16, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities17 = INSTANCE;
    terraNation1 = TerraNation.IBERIA;
    resourceKey = NationBiomes.IBERIA_SALT_DELTA;
    item = Items.COD;
    IBERIA_RESIDENT = residentWithDefaults(modEntities17, terraNation1, resourceKey, item, null, false, 24, null);
    Pair[] _this_map_iv = new Pair[]{
        Pair.of(TerraNation.AEGIR, AEGIR_RESIDENT),
        Pair.of(TerraNation.BOLIVAR, BOLIVAR_RESIDENT),
        Pair.of(TerraNation.HIGASHI, HIGASHI_RESIDENT),
        Pair.of(TerraNation.DURIN, DURIN_RESIDENT),
        Pair.of(TerraNation.COLUMBIA, COLUMBIA_RESIDENT),
        Pair.of(TerraNation.KAZIMIERZ, KAZIMIERZ_RESIDENT),
        Pair.of(TerraNation.KAZDEL, KAZDEL_RESIDENT),
        Pair.of(TerraNation.LEITHANIEN, LEITHANIEN_RESIDENT),
        Pair.of(TerraNation.RIM_BILLITON, RIM_BILLITON_RESIDENT),
        Pair.of(TerraNation.MINOS, MINOS_RESIDENT),
        Pair.of(TerraNation.SARGON, SARGON_RESIDENT),
        Pair.of(TerraNation.SAMI, SAMI_RESIDENT),
        Pair.of(TerraNation.VICTORIA, VICTORIA_RESIDENT),
        Pair.of(TerraNation.URSUS, URSUS_RESIDENT),
        Pair.of(TerraNation.KJERAG, KJERAG_RESIDENT),
        Pair.of(TerraNation.SIRACUSA, SIRACUSA_RESIDENT),
        Pair.of(TerraNation.YAN, YAN_RESIDENT),
        Pair.of(TerraNation.IBERIA, IBERIA_RESIDENT)
    };
    GENERIC_RESIDENTS_BY_NATION = com.cxxcxx.zinecraft.api.util.CollectionSupport.linkedMapOf(_this_map_iv);
    var registeredNations = new HashSet<>(GENERIC_RESIDENTS_BY_NATION.keySet());
    registeredNations.add(TerraNation.LATERANO);
    if (!registeredNations.equals(new HashSet<>(TerraNation.getEntries()))) {
      throw new IllegalArgumentException("必须为全部十九国注册居民");
    }
  }

  private ModEntities() {
  }

  static MobEntry residentWithDefaults(
      ModEntities var0, TerraNation var1, ResourceKey var2, Item var3, SpawnPlacementType var4, boolean var5, int var6, Object var7
  ) {
    if ((var6 & 8) != 0) {
      SpawnPlacementType spawnPlacementType = SpawnPlacementTypes.ON_GROUND;
      var4 = spawnPlacementType;
    }

    if ((var6 & 16) != 0) {
      var5 = false;
    }

    return var0.resident(var1, var2, var3, var4, var5);
  }

  private static void LATERANO_CITIZENHelper0(net.minecraft.world.entity.EntityType.Builder _this_mob) {
    _this_mob.sized(0.6F, 1.8F);
    _this_mob.clientTrackingRange(8);
    return;
  }

  private static final NationResident residentHelper0(NationResidentProfile _profile, EntityType type, Level level) {
    return new NationResident(type, level, _profile);
  }

  private static void residentHelper1(net.minecraft.world.entity.EntityType.Builder _this_mob) {
    _this_mob.sized(0.6F, 1.8F);
    _this_mob.clientTrackingRange(8);
    return;
  }

  @NotNull
  public final List<EntityType<NationResident>> getGENERIC_RESIDENT_TYPES() {
    return GENERIC_RESIDENTS_BY_NATION.values().stream().map(MobEntry::getType).toList();
  }

  @NotNull
  public final Map<TerraNation, EntityType<? extends Mob>> getRESIDENT_TYPES_BY_NATION() {
    Map<TerraNation, EntityType<? extends Mob>> result = new EnumMap<>(TerraNation.class);
    GENERIC_RESIDENTS_BY_NATION.forEach((nation, entry) -> result.put(nation, entry.getType()));
    result.put(TerraNation.LATERANO, LATERANO_CITIZEN.getType());
    return Map.copyOf(result);
  }

  private final MobEntry<NationResident> resident(TerraNation nation, ResourceKey<Biome> biome, Item heldItem, SpawnPlacementType placement, boolean aquatic) {
    NationResidentProfile nationResidentProfile = new NationResidentProfile(nation, heldItem, aquatic);
    MobEntry mobEntry = Zinecraft.ENTITIES
        .mob(
            nation.getId() + "_resident",
            nation.getZhCn() + "居民",
            nation.getEnUs() + " Resident",
            (type, level) -> residentHelper0(nationResidentProfile, type, level),
            MobCategory.CREATURE,
            NationResident.ACCESS::attributes,
            new MobSpawnRestriction(placement, Types.MOTION_BLOCKING_NO_LEAVES, NationResident.ACCESS::canSpawn),
            ModEntities::residentHelper1
        );
    return mobEntry.naturalSpawn(8, 1, 3, BiomeSelection.of(biome));
  }
}
