package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

public final class NationSettlements {
  @NotNull
  public static final NationSettlements INSTANCE = new NationSettlements();
  @NotNull
  private static final JigsawBuildingEntry AEGIR_SUBSEA_ENCLAVE = settlement$default(
      INSTANCE,
      "aegir_subsea_enclave",
      NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA(),
      41000001,
      "pressure_residence",
      "hydroponics_lab",
      "bathysphere_dock",
      "current_archive",
      64,
      Types.OCEAN_FLOOR_WG,
      0,
      0.0F,
      1536,
      null
  );
  @NotNull
  private static final JigsawBuildingEntry BOLIVAR_DOSSOLES_DISTRICT = settlement$default(
      INSTANCE,
      "bolivar_dossoles_district",
      NationBiomes.INSTANCE.getBOLIVAR_PLAIN(),
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
  private static final JigsawBuildingEntry HIGASHI_SOKOGAWA_TOWN = settlement$default(
      INSTANCE,
      "higashi_sokogawa_town",
      NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT(),
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
  private static final JigsawBuildingEntry DURIN_IDEAL_CITY_BLOCK = settlement$default(
      INSTANCE,
      "durin_ideal_city_block",
      NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN(),
      41000004,
      "dome_apartment",
      "machine_shop",
      "arcade",
      "transit_station",
      60,
      null,
      24,
      0.0F,
      1024,
      null
  );
  @NotNull
  private static final JigsawBuildingEntry COLUMBIA_FRONTIER_TOWN = settlement$default(
      INSTANCE,
      "columbia_frontier_town",
      NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS(),
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
  private static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_BOROUGH = settlement$default(
      INSTANCE,
      "kazimierz_knight_borough",
      NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND(),
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
  private static final JigsawBuildingEntry KAZDEL_SARKAZ_SETTLEMENT = settlement$default(
      INSTANCE,
      "kazdel_sarkaz_settlement",
      NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES(),
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
  private static final JigsawBuildingEntry LATERANO_MONASTERY_TOWN = settlement$default(
      INSTANCE,
      "laterano_monastery_town",
      NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(),
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
  private static final JigsawBuildingEntry LEITHANIEN_MUSIC_TOWN = settlement$default(
      INSTANCE,
      "leithanien_music_town",
      NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST(),
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
  private static final JigsawBuildingEntry RIM_BILLITON_MINING_CAMP = settlement$default(
      INSTANCE,
      "rim_billiton_mining_camp",
      NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS(),
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
  private static final JigsawBuildingEntry MINOS_HEROIC_POLIS = settlement$default(
      INSTANCE,
      "minos_heroic_polis",
      NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS(),
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
  private static final JigsawBuildingEntry SARGON_OASIS_TOWN = settlement$default(
      INSTANCE,
      "sargon_oasis_town",
      NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT(),
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
  private static final JigsawBuildingEntry SAMI_SNOWPRIEST_VILLAGE = settlement$default(
      INSTANCE,
      "sami_snowpriest_village",
      NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST(),
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
  private static final JigsawBuildingEntry VICTORIA_INDUSTRIAL_BOROUGH = settlement$default(
      INSTANCE,
      "victoria_industrial_borough",
      NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(),
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
  private static final JigsawBuildingEntry URSUS_NORTHERN_TOWN = settlement$default(
      INSTANCE,
      "ursus_northern_town",
      NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE(),
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
  private static final JigsawBuildingEntry KJERAG_MOUNTAIN_VILLAGE = settlement$default(
      INSTANCE,
      "kjerag_mountain_village",
      NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS(),
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
  private static final JigsawBuildingEntry SIRACUSA_FAMILY_TOWN = settlement$default(
      INSTANCE,
      "siracusa_family_town",
      NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND(),
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
  private static final JigsawBuildingEntry YAN_SHANGSHU_TOWN = settlement$default(
      INSTANCE,
      "yan_shangshu_town",
      NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE(),
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
  private static final JigsawBuildingEntry IBERIA_COASTAL_TOWN = settlement$default(
      INSTANCE,
      "iberia_coastal_town",
      NationBiomes.INSTANCE.getIBERIA_SALT_DELTA(),
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

  private NationSettlements() {
  }

  // $VF: synthetic method
  static JigsawBuildingEntry settlement$default(
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
      var8 = 52;
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

  @NotNull
  public final JigsawBuildingEntry getAEGIR_SUBSEA_ENCLAVE() {
    return AEGIR_SUBSEA_ENCLAVE;
  }

  @NotNull
  public final JigsawBuildingEntry getBOLIVAR_DOSSOLES_DISTRICT() {
    return BOLIVAR_DOSSOLES_DISTRICT;
  }

  @NotNull
  public final JigsawBuildingEntry getHIGASHI_SOKOGAWA_TOWN() {
    return HIGASHI_SOKOGAWA_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getDURIN_IDEAL_CITY_BLOCK() {
    return DURIN_IDEAL_CITY_BLOCK;
  }

  @NotNull
  public final JigsawBuildingEntry getCOLUMBIA_FRONTIER_TOWN() {
    return COLUMBIA_FRONTIER_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZIMIERZ_KNIGHT_BOROUGH() {
    return KAZIMIERZ_KNIGHT_BOROUGH;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZDEL_SARKAZ_SETTLEMENT() {
    return KAZDEL_SARKAZ_SETTLEMENT;
  }

  @NotNull
  public final JigsawBuildingEntry getLATERANO_MONASTERY_TOWN() {
    return LATERANO_MONASTERY_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getLEITHANIEN_MUSIC_TOWN() {
    return LEITHANIEN_MUSIC_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getRIM_BILLITON_MINING_CAMP() {
    return RIM_BILLITON_MINING_CAMP;
  }

  @NotNull
  public final JigsawBuildingEntry getMINOS_HEROIC_POLIS() {
    return MINOS_HEROIC_POLIS;
  }

  @NotNull
  public final JigsawBuildingEntry getSARGON_OASIS_TOWN() {
    return SARGON_OASIS_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getSAMI_SNOWPRIEST_VILLAGE() {
    return SAMI_SNOWPRIEST_VILLAGE;
  }

  @NotNull
  public final JigsawBuildingEntry getVICTORIA_INDUSTRIAL_BOROUGH() {
    return VICTORIA_INDUSTRIAL_BOROUGH;
  }

  @NotNull
  public final JigsawBuildingEntry getURSUS_NORTHERN_TOWN() {
    return URSUS_NORTHERN_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getKJERAG_MOUNTAIN_VILLAGE() {
    return KJERAG_MOUNTAIN_VILLAGE;
  }

  @NotNull
  public final JigsawBuildingEntry getSIRACUSA_FAMILY_TOWN() {
    return SIRACUSA_FAMILY_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getYAN_SHANGSHU_TOWN() {
    return YAN_SHANGSHU_TOWN;
  }

  @NotNull
  public final JigsawBuildingEntry getIBERIA_COASTAL_TOWN() {
    return IBERIA_COASTAL_TOWN;
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
    StructureCatalog structureCatalog = Zinecraft.INSTANCE.getSTRUCTURES();
    String string = "nation_settlements/" + path;
    Pair[] pairs = new Pair[]{TuplesKt.to(first, 4), TuplesKt.to(second, 3), TuplesKt.to(third, 2), TuplesKt.to(fourth, 2)};
    var templates = MapsKt.<String, Integer>linkedMapOf(pairs);
    if ("laterano_monastery_town".equals(path)) {
      return structureCatalog.fixedOriginSettlement(path, string, biome, salt, templates, 7, 112, heightmap, startHeight, removeVinesChance);
    }
    return structureCatalog.settlement(path, string, biome, salt, templates, spacing, 24, 7, 112, heightmap, startHeight, removeVinesChance);
  }
}
