package com.cxxcxx.zinecraft.core.entity;

import com.cxxcxx.zinecraft.api.entity.EntityCatalog;
import com.cxxcxx.zinecraft.api.entity.MobEntry;
import com.cxxcxx.zinecraft.api.entity.MobSpawnRestriction;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
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
import java.util.Map.Entry;

public final class ModEntities {
  @NotNull
  public static final ModEntities INSTANCE = new ModEntities();
  @NotNull
  private static final MobEntry<LateranoCitizen> LATERANO_CITIZEN;
  @NotNull
  private static final ItemEntry<SpawnEggItem> LATERANO_CITIZEN_SPAWN_EGG;
  @NotNull
  private static final MobEntry<NationResident> AEGIR_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> BOLIVAR_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> HIGASHI_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> DURIN_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> COLUMBIA_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> KAZIMIERZ_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> KAZDEL_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> LEITHANIEN_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> RIM_BILLITON_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> MINOS_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> SARGON_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> SAMI_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> VICTORIA_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> URSUS_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> KJERAG_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> SIRACUSA_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> YAN_RESIDENT;
  @NotNull
  private static final MobEntry<NationResident> IBERIA_RESIDENT;
  @NotNull
  private static final LinkedHashMap<TerraNation, MobEntry<NationResident>> GENERIC_RESIDENTS_BY_NATION;
  @NotNull
  private static final List<EntityType<NationResident>> GENERIC_RESIDENT_TYPES;
  @NotNull
  private static final Map<TerraNation, EntityType<? extends Mob>> RESIDENT_TYPES_BY_NATION;

  static {
    EntityCatalog entityCatalog = Zinecraft.INSTANCE.getENTITIES();
    EntityFactory entityFactory = LateranoCitizen::new;
    MobCategory mobCategory = MobCategory.CREATURE;
    Function0 function0 = LateranoCitizen.Companion::attributes;
    SpawnPlacementType spawnPlacementType1 = SpawnPlacementTypes.ON_GROUND;
    MobEntry mobEntry2 = entityCatalog.mob(
        "laterano_citizen",
        "拉特兰公民",
        "Laterano Citizen",
        entityFactory,
        mobCategory,
        function0,
        new MobSpawnRestriction(spawnPlacementType1, Types.MOTION_BLOCKING_NO_LEAVES, LateranoCitizen.Companion::canSpawn),
        ModEntities::LATERANO_CITIZEN$lambda$0
    );
    LATERANO_CITIZEN = mobEntry2.naturalSpawn(10, 1, 2, BiomeSelection.of(NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS()));
    LATERANO_CITIZEN_SPAWN_EGG = MobEntry.spawnEgg$default(LATERANO_CITIZEN, 15853776, 14267980, "拉特兰公民生成蛋", "Laterano Citizen Spawn Egg", null, 16, null);
    ModEntities modEntities = INSTANCE;
    TerraNation terraNation1 = TerraNation.AEGIR;
    ResourceKey resourceKey = NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA();
    Item item = Items.NAUTILUS_SHELL;
    SpawnPlacementType spawnPlacementType = SpawnPlacementTypes.IN_WATER;
    AEGIR_RESIDENT = modEntities.resident(terraNation1, resourceKey, item, spawnPlacementType, true);
    ModEntities modEntities1 = INSTANCE;
    terraNation1 = TerraNation.BOLIVAR;
    resourceKey = NationBiomes.INSTANCE.getBOLIVAR_PLAIN();
    item = Items.COOKIE;
    BOLIVAR_RESIDENT = resident$default(modEntities1, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities2 = INSTANCE;
    terraNation1 = TerraNation.HIGASHI;
    resourceKey = NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT();
    item = Items.IRON_SWORD;
    HIGASHI_RESIDENT = resident$default(modEntities2, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities3 = INSTANCE;
    terraNation1 = TerraNation.DURIN;
    resourceKey = NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN();
    item = Items.REDSTONE;
    DURIN_RESIDENT = resident$default(modEntities3, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities4 = INSTANCE;
    terraNation1 = TerraNation.COLUMBIA;
    resourceKey = NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS();
    item = Items.COMPASS;
    COLUMBIA_RESIDENT = resident$default(modEntities4, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities5 = INSTANCE;
    terraNation1 = TerraNation.KAZIMIERZ;
    resourceKey = NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND();
    item = Items.IRON_SWORD;
    KAZIMIERZ_RESIDENT = resident$default(modEntities5, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities6 = INSTANCE;
    terraNation1 = TerraNation.KAZDEL;
    resourceKey = NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES();
    item = Items.IRON_AXE;
    KAZDEL_RESIDENT = resident$default(modEntities6, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities7 = INSTANCE;
    terraNation1 = TerraNation.LEITHANIEN;
    resourceKey = NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST();
    item = Items.NOTE_BLOCK;
    LEITHANIEN_RESIDENT = resident$default(modEntities7, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities8 = INSTANCE;
    terraNation1 = TerraNation.RIM_BILLITON;
    resourceKey = NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS();
    item = Items.IRON_PICKAXE;
    RIM_BILLITON_RESIDENT = resident$default(modEntities8, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities9 = INSTANCE;
    terraNation1 = TerraNation.MINOS;
    resourceKey = NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS();
    item = Items.SHIELD;
    MINOS_RESIDENT = resident$default(modEntities9, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities10 = INSTANCE;
    terraNation1 = TerraNation.SARGON;
    resourceKey = NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT();
    item = Items.EMERALD;
    SARGON_RESIDENT = resident$default(modEntities10, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities11 = INSTANCE;
    terraNation1 = TerraNation.SAMI;
    resourceKey = NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST();
    item = Items.BONE;
    SAMI_RESIDENT = resident$default(modEntities11, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities12 = INSTANCE;
    terraNation1 = TerraNation.VICTORIA;
    resourceKey = NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS();
    item = Items.IRON_INGOT;
    VICTORIA_RESIDENT = resident$default(modEntities12, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities13 = INSTANCE;
    terraNation1 = TerraNation.URSUS;
    resourceKey = NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE();
    item = Items.IRON_AXE;
    URSUS_RESIDENT = resident$default(modEntities13, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities14 = INSTANCE;
    terraNation1 = TerraNation.KJERAG;
    resourceKey = NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS();
    item = Items.EMERALD;
    KJERAG_RESIDENT = resident$default(modEntities14, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities15 = INSTANCE;
    terraNation1 = TerraNation.SIRACUSA;
    resourceKey = NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND();
    item = Items.SHEARS;
    SIRACUSA_RESIDENT = resident$default(modEntities15, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities16 = INSTANCE;
    terraNation1 = TerraNation.YAN;
    resourceKey = NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE();
    item = Items.PAPER;
    YAN_RESIDENT = resident$default(modEntities16, terraNation1, resourceKey, item, null, false, 24, null);
    ModEntities modEntities17 = INSTANCE;
    terraNation1 = TerraNation.IBERIA;
    resourceKey = NationBiomes.INSTANCE.getIBERIA_SALT_DELTA();
    item = Items.COD;
    IBERIA_RESIDENT = resident$default(modEntities17, terraNation1, resourceKey, item, null, false, 24, null);
    Pair[] $this$map$iv = new Pair[]{
        TuplesKt.to(TerraNation.AEGIR, AEGIR_RESIDENT),
        TuplesKt.to(TerraNation.BOLIVAR, BOLIVAR_RESIDENT),
        TuplesKt.to(TerraNation.HIGASHI, HIGASHI_RESIDENT),
        TuplesKt.to(TerraNation.DURIN, DURIN_RESIDENT),
        TuplesKt.to(TerraNation.COLUMBIA, COLUMBIA_RESIDENT),
        TuplesKt.to(TerraNation.KAZIMIERZ, KAZIMIERZ_RESIDENT),
        TuplesKt.to(TerraNation.KAZDEL, KAZDEL_RESIDENT),
        TuplesKt.to(TerraNation.LEITHANIEN, LEITHANIEN_RESIDENT),
        TuplesKt.to(TerraNation.RIM_BILLITON, RIM_BILLITON_RESIDENT),
        TuplesKt.to(TerraNation.MINOS, MINOS_RESIDENT),
        TuplesKt.to(TerraNation.SARGON, SARGON_RESIDENT),
        TuplesKt.to(TerraNation.SAMI, SAMI_RESIDENT),
        TuplesKt.to(TerraNation.VICTORIA, VICTORIA_RESIDENT),
        TuplesKt.to(TerraNation.URSUS, URSUS_RESIDENT),
        TuplesKt.to(TerraNation.KJERAG, KJERAG_RESIDENT),
        TuplesKt.to(TerraNation.SIRACUSA, SIRACUSA_RESIDENT),
        TuplesKt.to(TerraNation.YAN, YAN_RESIDENT),
        TuplesKt.to(TerraNation.IBERIA, IBERIA_RESIDENT)
    };
    GENERIC_RESIDENTS_BY_NATION = MapsKt.linkedMapOf($this$map$iv);
    Collection collection2 = GENERIC_RESIDENTS_BY_NATION.values();
    Iterable iterable1 = collection2;
    int k = 0;
    Iterable iterable = iterable1;
    var collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable1, 10));
    int i = 0;

    for (Object object : iterable) {
      MobEntry p0 = (MobEntry) object;
      Collection collection1 = collection;
      int j = 0;
      collection1.add(p0.getType());
    }

    GENERIC_RESIDENT_TYPES = (List<EntityType<NationResident>>) collection;
    Map map = MapsKt.createMapBuilder();
    Map map2 = map;
    int l = 0;
    Map map4 = GENERIC_RESIDENTS_BY_NATION;
    i = 0;

    for (Object rawEntry : map4.entrySet()) {
      Entry entry1 = (Entry) rawEntry;
      int o = 0;
      TerraNation terraNation = (TerraNation) entry1.getKey();
      MobEntry mobEntry1 = (MobEntry) entry1.getValue();
      map2.put(terraNation, mobEntry1.getType());
    }

    map2.put(TerraNation.LATERANO, LATERANO_CITIZEN.getType());
    Map map1 = MapsKt.build(map);
    Map map3 = map1;
    int m = 0;
    if (!java.util.Objects.equals(map3.keySet(), CollectionsKt.toSet((Iterable) TerraNation.getEntries()))) {
      int n = 0;
      String string = "必须为全部十九国注册居民";
      throw new IllegalArgumentException(string.toString());
    }

    RESIDENT_TYPES_BY_NATION = map1;
  }

  private ModEntities() {
  }

  // $VF: synthetic method
  static MobEntry resident$default(
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

  private static final Unit LATERANO_CITIZEN$lambda$0(net.minecraft.world.entity.EntityType.Builder $this$mob) {
    $this$mob.sized(0.6F, 1.8F);
    $this$mob.clientTrackingRange(8);
    return Unit.INSTANCE;
  }

  private static final NationResident resident$lambda$0(NationResidentProfile $profile, EntityType type, Level level) {
    return new NationResident(type, level, $profile);
  }

  private static final Unit resident$lambda$1(net.minecraft.world.entity.EntityType.Builder $this$mob) {
    $this$mob.sized(0.6F, 1.8F);
    $this$mob.clientTrackingRange(8);
    return Unit.INSTANCE;
  }

  @NotNull
  public final MobEntry<LateranoCitizen> getLATERANO_CITIZEN() {
    return LATERANO_CITIZEN;
  }

  @NotNull
  public final ItemEntry<SpawnEggItem> getLATERANO_CITIZEN_SPAWN_EGG() {
    return LATERANO_CITIZEN_SPAWN_EGG;
  }

  @NotNull
  public final MobEntry<NationResident> getAEGIR_RESIDENT() {
    return AEGIR_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getBOLIVAR_RESIDENT() {
    return BOLIVAR_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getHIGASHI_RESIDENT() {
    return HIGASHI_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getDURIN_RESIDENT() {
    return DURIN_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getCOLUMBIA_RESIDENT() {
    return COLUMBIA_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getKAZIMIERZ_RESIDENT() {
    return KAZIMIERZ_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getKAZDEL_RESIDENT() {
    return KAZDEL_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getLEITHANIEN_RESIDENT() {
    return LEITHANIEN_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getRIM_BILLITON_RESIDENT() {
    return RIM_BILLITON_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getMINOS_RESIDENT() {
    return MINOS_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getSARGON_RESIDENT() {
    return SARGON_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getSAMI_RESIDENT() {
    return SAMI_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getVICTORIA_RESIDENT() {
    return VICTORIA_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getURSUS_RESIDENT() {
    return URSUS_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getKJERAG_RESIDENT() {
    return KJERAG_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getSIRACUSA_RESIDENT() {
    return SIRACUSA_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getYAN_RESIDENT() {
    return YAN_RESIDENT;
  }

  @NotNull
  public final MobEntry<NationResident> getIBERIA_RESIDENT() {
    return IBERIA_RESIDENT;
  }

  @NotNull
  public final List<EntityType<NationResident>> getGENERIC_RESIDENT_TYPES() {
    return GENERIC_RESIDENT_TYPES;
  }

  @NotNull
  public final Map<TerraNation, EntityType<? extends Mob>> getRESIDENT_TYPES_BY_NATION() {
    return RESIDENT_TYPES_BY_NATION;
  }

  private final MobEntry<NationResident> resident(TerraNation nation, ResourceKey<Biome> biome, Item heldItem, SpawnPlacementType placement, boolean aquatic) {
    NationResidentProfile nationResidentProfile = new NationResidentProfile(nation, heldItem, aquatic);
    MobEntry mobEntry = Zinecraft.INSTANCE
        .getENTITIES()
        .mob(
            nation.getId() + "_resident",
            nation.getZhCn() + "居民",
            nation.getEnUs() + " Resident",
            (type, level) -> resident$lambda$0(nationResidentProfile, type, level),
            MobCategory.CREATURE,
            NationResident.Companion::attributes,
            new MobSpawnRestriction(placement, Types.MOTION_BLOCKING_NO_LEAVES, NationResident.Companion::canSpawn),
            ModEntities::resident$lambda$1
        );
    return mobEntry.naturalSpawn(8, 1, 3, BiomeSelection.of(biome));
  }
}

