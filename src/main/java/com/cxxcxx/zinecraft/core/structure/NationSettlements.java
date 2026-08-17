package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public final class NationSettlements {
  /**
   * Dense national settlement placement. The 17-chunk minimum center gap remains
   * larger than twice the 112-block Jigsaw radius, so neighbouring settlements
   * cannot overlap even when both assemblies reach their configured boundary.
   */
  static final int DENSE_SETTLEMENT_SPACING = 36;
  static final int DENSE_SETTLEMENT_SEPARATION = 16;
  static final int DENSE_SETTLEMENT_JIGSAW_DEPTH = 9;
  static final int DENSE_SETTLEMENT_MAX_DISTANCE = 112;
  @NotNull
  public static final NationSettlements INSTANCE = new NationSettlements();
  @NotNull
  public static final JigsawBuildingEntry AEGIR_SUBSEA_ENCLAVE = settlementWithDefaults(
      INSTANCE,
      "aegir_subsea_enclave",
      NationBiomes.AEGIR_ABYSSAL_SEA,
      41000001,
      "pressure_residence",
      "hydroponics_lab",
      "bathysphere_dock",
      "current_archive",
      DENSE_SETTLEMENT_SPACING,
      Types.OCEAN_FLOOR_WG,
      0,
      0.0F,
      1536,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry BOLIVAR_DOSSOLES_DISTRICT = settlementWithDefaults(
      INSTANCE,
      "bolivar_dossoles_district",
      NationBiomes.BOLIVAR_PLAIN,
      41000002,
      "canal_house",
      "beach_market",
      "race_workshop",
      "festival_hall",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry HIGASHI_SOKOGAWA_TOWN = settlementWithDefaults(
      INSTANCE,
      "higashi_sokogawa_town",
      NationBiomes.HIGASHI_SHADOW_RIFT,
      41000003,
      "machiya",
      "swordsmith",
      "tea_house",
      "magistrate_house",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry DURIN_IDEAL_CITY_BLOCK = settlementWithDefaults(
      INSTANCE,
      "durin_ideal_city_block",
      NationBiomes.DURIN_UNDERGROUND_GARDEN,
      41000004,
      "dome_apartment",
      "machine_shop",
      "arcade",
      "transit_station",
      DENSE_SETTLEMENT_SPACING,
      null,
      24,
      0.0F,
      1024,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry COLUMBIA_FRONTIER_TOWN = settlementWithDefaults(
      INSTANCE,
      "columbia_frontier_town",
      NationBiomes.COLUMBIA_SANDSTONE_WILDS,
      41000005,
      "prefab_house",
      "pioneer_lab",
      "logistics_depot",
      "sheriff_office",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_BOROUGH = settlementWithDefaults(
      INSTANCE,
      "kazimierz_knight_borough",
      NationBiomes.KAZIMIERZ_KNIGHTLAND,
      41000006,
      "tenement",
      "armor_workshop",
      "sponsor_shop",
      "tournament_inn",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry KAZDEL_SARKAZ_SETTLEMENT = settlementWithDefaults(
      INSTANCE,
      "kazdel_sarkaz_settlement",
      NationBiomes.KAZDEL_SCARRED_WASTES,
      41000007,
      "canvas_house",
      "forge",
      "mercenary_lodge",
      "provision_store",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry LATERANO_MONASTERY_TOWN = settlementWithDefaults(
      INSTANCE,
      "laterano_monastery_town",
      NationBiomes.LATERANO_HOLY_FIELDS,
      41000008,
      "white_residence",
      "confectionery",
      "notary_office",
      "bell_chapel",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry LEITHANIEN_MUSIC_TOWN = settlementWithDefaults(
      INSTANCE,
      "leithanien_music_town",
      NationBiomes.LEITHANIEN_TWILIGHT_FOREST,
      41000009,
      "twilight_house",
      "instrument_workshop",
      "rehearsal_hall",
      "arts_academy",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry RIM_BILLITON_MINING_CAMP = settlementWithDefaults(
      INSTANCE,
      "rim_billiton_mining_camp",
      NationBiomes.RIM_BILLITON_MINING_BADLANDS,
      41000010,
      "miner_bunkhouse",
      "ore_workshop",
      "freight_depot",
      "canteen",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry MINOS_HEROIC_POLIS = settlementWithDefaults(
      INSTANCE,
      "minos_heroic_polis",
      NationBiomes.MINOS_SUNLIT_HILLS,
      41000011,
      "courtyard_house",
      "olive_market",
      "training_hall",
      "council_house",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry SARGON_OASIS_TOWN = settlementWithDefaults(
      INSTANCE,
      "sargon_oasis_town",
      NationBiomes.SARGON_ROCKY_DESERT,
      41000012,
      "adobe_house",
      "spice_market",
      "caravanserai",
      "well_house",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry SAMI_SNOWPRIEST_VILLAGE = settlementWithDefaults(
      INSTANCE,
      "sami_snowpriest_village",
      NationBiomes.SAMI_FROZEN_FOREST,
      41000013,
      "snow_lodge",
      "hunter_camp",
      "ritual_house",
      "supply_shed",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry VICTORIA_INDUSTRIAL_BOROUGH = settlementWithDefaults(
      INSTANCE,
      "victoria_industrial_borough",
      NationBiomes.VICTORIA_MISTY_HIGHLANDS,
      41000014,
      "brick_tenement",
      "steam_workshop",
      "rail_warehouse",
      "council_hall",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry URSUS_NORTHERN_TOWN = settlementWithDefaults(
      INSTANCE,
      "ursus_northern_town",
      NationBiomes.URSUS_FROZEN_STEPPE,
      41000015,
      "heated_house",
      "military_storehouse",
      "mine_office",
      "communal_hall",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry KJERAG_MOUNTAIN_VILLAGE = settlementWithDefaults(
      INSTANCE,
      "kjerag_mountain_village",
      NationBiomes.KJERAG_SNOWY_PEAKS,
      41000016,
      "stone_chalet",
      "tea_workshop",
      "caravan_post",
      "shrine_house",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry SIRACUSA_FAMILY_TOWN = settlementWithDefaults(
      INSTANCE,
      "siracusa_family_town",
      NationBiomes.SIRACUSA_RAINY_WOODLAND,
      41000017,
      "family_house",
      "trattoria",
      "tailor_shop",
      "meeting_hall",
      0,
      null,
      0,
      0.25F,
      896,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry YAN_SHANGSHU_TOWN = settlementWithDefaults(
      INSTANCE,
      "yan_shangshu_town",
      NationBiomes.YAN_MOUNTAIN_GROVE,
      41000018,
      "courtyard_residence",
      "tea_house",
      "artisan_workshop",
      "relay_office",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );
  @NotNull
  public static final JigsawBuildingEntry IBERIA_COASTAL_TOWN = settlementWithDefaults(
      INSTANCE,
      "iberia_coastal_town",
      NationBiomes.IBERIA_SALT_DELTA,
      41000019,
      "saltstone_house",
      "shipwright",
      "fish_market",
      "inquisitor_office",
      0,
      null,
      0,
      0.0F,
      1920,
      null
  );

  static {
    validateNationCoverage();
  }

  private NationSettlements() {
  }

  /**
   * Fails data generation early if a national biome loses its one-to-one settlement binding.
   */
  private static void validateNationCoverage() {
    List<JigsawBuildingEntry> settlements = List.of(
        AEGIR_SUBSEA_ENCLAVE, BOLIVAR_DOSSOLES_DISTRICT, HIGASHI_SOKOGAWA_TOWN,
        DURIN_IDEAL_CITY_BLOCK, COLUMBIA_FRONTIER_TOWN, KAZIMIERZ_KNIGHT_BOROUGH,
        KAZDEL_SARKAZ_SETTLEMENT, LATERANO_MONASTERY_TOWN, LEITHANIEN_MUSIC_TOWN,
        RIM_BILLITON_MINING_CAMP, MINOS_HEROIC_POLIS, SARGON_OASIS_TOWN,
        SAMI_SNOWPRIEST_VILLAGE, VICTORIA_INDUSTRIAL_BOROUGH, URSUS_NORTHERN_TOWN,
        KJERAG_MOUNTAIN_VILLAGE, SIRACUSA_FAMILY_TOWN, YAN_SHANGSHU_TOWN,
        IBERIA_COASTAL_TOWN
    );
    var biomes = new HashSet<ResourceKey<Biome>>();
    var structureIds = new HashSet<>();
    int fixedOriginCount = 0;
    for (JigsawBuildingEntry settlement : settlements) {
      if (settlement.getBiome() == null || !biomes.add(settlement.getBiome())) {
        throw new IllegalStateException("十九国群系必须各自唯一绑定一个本国聚落");
      }
      if (!structureIds.add(settlement.getStructureKey())) {
        throw new IllegalStateException("国家聚落结构 ID 不得重复");
      }
      if (settlement.getUnique()
          || settlement.getSize() != DENSE_SETTLEMENT_JIGSAW_DEPTH
          || settlement.getMaxDistanceFromCenter() != DENSE_SETTLEMENT_MAX_DISTANCE) {
        throw new IllegalStateException("国家普通聚落不得使用唯一地标放置，且必须遵守统一展开上限");
      }
      if (settlement.getFixedOrigin()) {
        fixedOriginCount++;
      } else if (settlement.getSpacing() != DENSE_SETTLEMENT_SPACING
          || settlement.getSeparation() != DENSE_SETTLEMENT_SEPARATION) {
        throw new IllegalStateException("外围国家聚落必须使用统一高密度随机散布参数");
      }
    }
    if (settlements.size() != 19 || biomes.size() != 19 || fixedOriginCount != 1) {
      throw new IllegalStateException("国家聚落覆盖必须恰好为 19 国");
    }
  }

  static JigsawBuildingEntry settlementWithDefaults(
      NationSettlements var0,
      String var1,
      ResourceKey var2,
      int var3,
      String var4,
      String var5,
      String var6,
      String var7,
      int var8,
      Types var9,
      int var10,
      float var11,
      int var12,
      Object var13
  ) {
    if ((var12 & 128) != 0) {
      var8 = DENSE_SETTLEMENT_SPACING;
    }

    if ((var12 & 256) != 0) {
      var9 = Types.WORLD_SURFACE_WG;
    }

    if ((var12 & 512) != 0) {
      var10 = 0;
    }

    if ((var12 & 1024) != 0) {
      var11 = 0.0F;
    }

    return var0.settlement(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
  }

  private final JigsawBuildingEntry settlement(
      String path,
      ResourceKey<Biome> biome,
      int salt,
      String first,
      String second,
      String third,
      String fourth,
      int spacing,
      Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    StructureCatalog structureCatalog = Zinecraft.WORLDGEN.getStructures();
    String string = "nation_settlements/" + path;
    Pair[] pairs = new Pair[]{Pair.of(first, 4), Pair.of(second, 3), Pair.of(third, 2), Pair.of(fourth, 2)};
    var templates = com.cxxcxx.zinecraft.api.util.CollectionSupport.<String, Integer>linkedMapOf(pairs);
    if (spacing != DENSE_SETTLEMENT_SPACING) {
      throw new IllegalStateException("十九国普通聚落必须使用统一高密度 spacing: " + path);
    }
    if ("laterano_monastery_town".equals(path)) {
      return structureCatalog.fixedOriginSettlement(
          path, string, biome, salt, templates,
          DENSE_SETTLEMENT_JIGSAW_DEPTH, DENSE_SETTLEMENT_MAX_DISTANCE,
          heightmap, startHeight, removeVinesChance
      );
    }
    return structureCatalog.settlement(
        path, string, biome, salt, templates,
        spacing, DENSE_SETTLEMENT_SEPARATION,
        DENSE_SETTLEMENT_JIGSAW_DEPTH, DENSE_SETTLEMENT_MAX_DISTANCE,
        heightmap, startHeight, removeVinesChance
    );
  }
}
