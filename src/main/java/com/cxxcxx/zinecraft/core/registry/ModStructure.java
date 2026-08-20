package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.catalog.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

import java.util.*;

public final class ModStructure {
  public static final JigsawBuilder STARGATE = Zinecraft.STRUCTURES.jigsaw("stargate", "萨米星门")
      .enUs("Sami Stargate")
      .fixedAt(ModDimension.SAMI_STARGATE_CHUNK_X, ModDimension.SAMI_STARGATE_CHUNK_Z)
      .allowedBiomes(ModBiome.NATIONAL_BIOMES.get(TerraNation.SAMI).stream().map(BiomeBuilder::key).toList())
      .layout(1, 32)
      .height(Types.WORLD_SURFACE_WG, 0)
      .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN)
      .pool("start", Projection.RIGID, pool -> pool.template("stargate", 1))
      .build();

  public static final JigsawBuilder AEGIR_VOLCANIC_BEACON = landmark("aegir_volcanic_beacon", "阿戈尔火山信标", ModBiome.AEGIR_ABYSSAL_SEA.key(), 40, Types.OCEAN_FLOOR_WG, 0);
  public static final JigsawBuilder AEGIR_ABYSSAL_OBSERVATORY = landmark("aegir_abyssal_observatory", "阿戈尔深渊观测站", ModBiome.AEGIR_ABYSSAL_SEA.key(), 52, Types.OCEAN_FLOOR_WG, 0);
  public static final JigsawBuilder BOLIVAR_DOSSOLES_YACHT = landmark("bolivar_dossoles_yacht", "玻利瓦尔多索雷斯游艇", ModBiome.BOLIVAR_PLAIN.key(), 30, null, 0);
  public static final JigsawBuilder BOLIVAR_RACE_CHECKPOINT = landmark("bolivar_race_checkpoint", "玻利瓦尔竞速检查站", ModBiome.BOLIVAR_PLAIN.key(), 46, null, 0);
  public static final JigsawBuilder HIGASHI_RIFT_SHRINE = landmark("higashi_rift_shrine", "东国裂谷神社", ModBiome.HIGASHI_SHADOW_RIFT.key(), 34, null, 0);
  public static final JigsawBuilder HIGASHI_SOKOGAWA_WATCHTOWER = landmark("higashi_sokogawa_watchtower", "东国索谷川瞭望塔", ModBiome.HIGASHI_SHADOW_RIFT.key(), 50, null, 0);
  public static final JigsawBuilder DURIN_DOME_STATION = landmark("durin_dome_station", "杜林穹顶车站", ModBiome.DURIN_UNDERGROUND_GARDEN.key(), 24, null, 24);
  public static final JigsawBuilder DURIN_WATER_PARK = landmark("durin_water_park", "杜林水上乐园", ModBiome.DURIN_UNDERGROUND_GARDEN.key(), 40, null, 24);
  public static final JigsawBuilder COLUMBIA_FRONTIER_LAB = landmark("columbia_frontier_lab", "哥伦比亚边疆实验室", ModBiome.COLUMBIA_SANDSTONE_WILDS.key(), 32, null, 0);
  public static final JigsawBuilder COLUMBIA_PRISON_OUTPOST = landmark("columbia_prison_outpost", "哥伦比亚监狱哨站", ModBiome.COLUMBIA_SANDSTONE_WILDS.key(), 48, null, 0);
  public static final JigsawBuilder KAZIMIERZ_ARENA_GATE = landmark("kazimierz_arena_gate", "卡西米尔竞技场大门", ModBiome.KAZIMIERZ_KNIGHTLAND.key(), 30, null, 0);
  public static final JigsawBuilder KAZIMIERZ_KNIGHT_MONUMENT = landmark("kazimierz_knight_monument", "卡西米尔骑士纪念碑", ModBiome.KAZIMIERZ_KNIGHTLAND.key(), 46, null, 0);
  public static final JigsawBuilder KAZDEL_BABEL_RUINS = landmark("kazdel_babel_ruins", "卡兹戴尔巴别塔遗迹", ModBiome.KAZDEL_SCARRED_WASTES.key(), 36, null, 0);
  public static final JigsawBuilder KAZDEL_SARKAZ_CAMP = landmark("kazdel_sarkaz_camp", "卡兹戴尔萨卡兹营地", ModBiome.KAZDEL_SCARRED_WASTES.key(), 52, null, 0);
  public static final JigsawBuilder LATERANO_REVELATION_TOWER = landmark("laterano_revelation_tower", "拉特兰启示石塔", ModBiome.LATERANO_HOLY_FIELDS.key(), 28, null, 0);
  public static final JigsawBuilder LATERANO_AMBROSIUS_CHAPEL = landmark("laterano_ambrosius_chapel", "拉特兰安布罗修礼拜堂", ModBiome.LATERANO_HOLY_FIELDS.key(), 44, null, 0);
  public static final JigsawBuilder LEITHANIEN_TWIN_SPIRES = landmark("leithanien_twin_spires", "莱塔尼亚双塔", ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(), 34, null, 0);
  public static final JigsawBuilder LEITHANIEN_CONCERT_HALL = landmark("leithanien_concert_hall", "莱塔尼亚音乐厅", ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(), 50, null, 0);
  public static final JigsawBuilder RIM_BILLITON_MINING_DERRICK = landmark("rim_billiton_mining_derrick", "雷姆必拓采矿井架", ModBiome.RIM_BILLITON_MINING_BADLANDS.key(), 32, null, 0);
  public static final JigsawBuilder RIM_BILLITON_RAIL_DEPOT = landmark("rim_billiton_rail_depot", "雷姆必拓铁路货站", ModBiome.RIM_BILLITON_MINING_BADLANDS.key(), 48, null, 0);
  public static final JigsawBuilder MINOS_HEROES_TEMPLE = landmark("minos_heroes_temple", "米诺斯英雄神殿", ModBiome.MINOS_SUNLIT_HILLS.key(), 28, null, 0);
  public static final JigsawBuilder MINOS_HEROES_PLAZA = landmark("minos_heroes_plaza", "米诺斯英雄广场", ModBiome.MINOS_SUNLIT_HILLS.key(), 44, null, 0);
  public static final JigsawBuilder SARGON_GOLDEN_BAZAAR = landmark("sargon_golden_bazaar", "萨尔贡黄金集市", ModBiome.SARGON_ROCKY_DESERT.key(), 34, null, 0);
  public static final JigsawBuilder SARGON_LONG_SPRING_WELL = landmark("sargon_long_spring_well", "萨尔贡长泉水井", ModBiome.SARGON_ROCKY_DESERT.key(), 50, null, 0);
  public static final JigsawBuilder SAMI_CYCLOPS_ALTAR = landmark("sami_cyclops_altar", "萨米独眼巨人祭坛", ModBiome.SAMI_FROZEN_FOREST.key(), 38, null, 0);
  public static final JigsawBuilder SAMI_SNOWPRIEST_LODGE = landmark("sami_snowpriest_lodge", "萨米雪祀居所", ModBiome.SAMI_FROZEN_FOREST.key(), 54, null, 0);
  public static final JigsawBuilder VICTORIA_DEFENCE_CANNON = landmark("victoria_defence_cannon", "维多利亚防御炮台", ModBiome.VICTORIA_MISTY_HIGHLANDS.key(), 32, null, 0);
  public static final JigsawBuilder VICTORIA_STEAM_STATION = landmark("victoria_steam_station", "维多利亚蒸汽车站", ModBiome.VICTORIA_MISTY_HIGHLANDS.key(), 48, null, 0);
  public static final JigsawBuilder URSUS_SARCOPHAGUS_STATION = landmark("ursus_sarcophagus_station", "乌萨斯石棺站", ModBiome.URSUS_FROZEN_STEPPE.key(), 34, null, 0);
  public static final JigsawBuilder URSUS_NORTHERN_MINE_TOWER = landmark("ursus_northern_mine_tower", "乌萨斯北方矿塔", ModBiome.URSUS_FROZEN_STEPPE.key(), 50, null, 0);
  public static final JigsawBuilder KJERAG_KARLAN_MONASTERY = landmark("kjerag_karlan_monastery", "谢拉格喀兰修道院", ModBiome.KJERAG_SNOWY_PEAKS.key(), 40, null, 0);
  public static final JigsawBuilder KJERAG_SACRED_PLAZA = landmark("kjerag_sacred_plaza", "谢拉格圣洁广场", ModBiome.KJERAG_SNOWY_PEAKS.key(), 56, null, 0);
  public static final JigsawBuilder SIRACUSA_FAMILY_COURT = landmark("siracusa_family_court", "叙拉古家族法庭", ModBiome.SIRACUSA_RAINY_WOODLAND.key(), 30, null, 0);
  public static final JigsawBuilder SIRACUSA_FAMILY_THEATRE = landmark("siracusa_family_theatre", "叙拉古家族剧院", ModBiome.SIRACUSA_RAINY_WOODLAND.key(), 46, null, 0);
  public static final JigsawBuilder YAN_YUMEN_BEACON = landmark("yan_yumen_beacon", "炎国玉门烽台", ModBiome.YAN_MOUNTAIN_GROVE.key(), 36, null, 0);
  public static final JigsawBuilder YAN_SHANGSHU_PAVILION = landmark("yan_shangshu_pavilion", "炎国尚蜀亭阁", ModBiome.YAN_MOUNTAIN_GROVE.key(), 52, null, 0);
  public static final JigsawBuilder IBERIA_EYE_LIGHTHOUSE = landmark("iberia_eye_lighthouse", "伊比利亚之眼灯塔", ModBiome.IBERIA_SALT_DELTA.key(), 38, null, 0);
  public static final JigsawBuilder IBERIA_SALTVIND_CHAPEL = landmark("iberia_saltwind_chapel", "伊比利亚盐风礼拜堂", ModBiome.IBERIA_SALT_DELTA.key(), 54, null, 0);

  /**
   * 国家聚落采用高密度放置；最小中心间距为 17 个区块，仍大于两倍的 112 格 Jigsaw
   * 展开半径，因此相邻聚落同时扩展到边界时也不会重叠。
   */
  static final int DENSE_SETTLEMENT_SPACING = 36;
  static final int DENSE_SETTLEMENT_SEPARATION = 16;
  static final int DENSE_SETTLEMENT_JIGSAW_DEPTH = 9;
  static final int DENSE_SETTLEMENT_MAX_DISTANCE = 112;
  private static final List<JigsawBuilder> MUTABLE_SETTLEMENTS = new ArrayList<>();
  public static final JigsawBuilder AEGIR_SUBSEA_ENCLAVE = settlement(
      "aegir_subsea_enclave",
      "阿戈尔海底聚居地",
      ModBiome.AEGIR_ABYSSAL_SEA.key(),
      41000001,
      "pressure_residence",
      "hydroponics_lab",
      "bathysphere_dock",
      "current_archive",
      Types.OCEAN_FLOOR_WG,
      0
  );
  public static final JigsawBuilder BOLIVAR_DOSSOLES_DISTRICT = settlement(
      "bolivar_dossoles_district",
      "玻利瓦尔多索雷斯城区",
      ModBiome.BOLIVAR_PLAIN.key(),
      41000002,
      "canal_house",
      "beach_market",
      "race_workshop",
      "festival_hall"
  );
  public static final JigsawBuilder HIGASHI_SOKOGAWA_TOWN = settlement(
      "higashi_sokogawa_town",
      "东国索谷川町",
      ModBiome.HIGASHI_SHADOW_RIFT.key(),
      41000003,
      "machiya",
      "swordsmith",
      "tea_house",
      "magistrate_house"
  );
  public static final JigsawBuilder DURIN_IDEAL_CITY_BLOCK = settlement(
      "durin_ideal_city_block",
      "杜林理想城街区",
      ModBiome.DURIN_UNDERGROUND_GARDEN.key(),
      41000004,
      "dome_apartment",
      "machine_shop",
      "arcade",
      "transit_station",
      null,
      24
  );
  public static final JigsawBuilder COLUMBIA_FRONTIER_TOWN = settlement(
      "columbia_frontier_town",
      "哥伦比亚边疆城镇",
      ModBiome.COLUMBIA_SANDSTONE_WILDS.key(),
      41000005,
      "prefab_house",
      "pioneer_lab",
      "logistics_depot",
      "sheriff_office"
  );
  public static final JigsawBuilder KAZIMIERZ_KNIGHT_BOROUGH = settlement(
      "kazimierz_knight_borough",
      "卡西米尔骑士城区",
      ModBiome.KAZIMIERZ_KNIGHTLAND.key(),
      41000006,
      "tenement",
      "armor_workshop",
      "sponsor_shop",
      "tournament_inn"
  );
  public static final JigsawBuilder KAZDEL_SARKAZ_SETTLEMENT = settlement(
      "kazdel_sarkaz_settlement",
      "卡兹戴尔萨卡兹聚落",
      ModBiome.KAZDEL_SCARRED_WASTES.key(),
      41000007,
      "canvas_house",
      "forge",
      "mercenary_lodge",
      "provision_store"
  );
  public static final JigsawBuilder LATERANO_MONASTERY_TOWN = settlement(
      "laterano_monastery_town",
      "拉特兰修道院城镇",
      ModBiome.LATERANO_HOLY_FIELDS.key(),
      41000008,
      "white_residence",
      "confectionery",
      "notary_office",
      "bell_chapel"
  );
  public static final JigsawBuilder LEITHANIEN_MUSIC_TOWN = settlement(
      "leithanien_music_town",
      "莱塔尼亚音乐城镇",
      ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(),
      41000009,
      "twilight_house",
      "instrument_workshop",
      "rehearsal_hall",
      "arts_academy"
  );
  public static final JigsawBuilder RIM_BILLITON_MINING_CAMP = settlement(
      "rim_billiton_mining_camp",
      "雷姆必拓采矿营地",
      ModBiome.RIM_BILLITON_MINING_BADLANDS.key(),
      41000010,
      "miner_bunkhouse",
      "ore_workshop",
      "freight_depot",
      "canteen"
  );
  public static final JigsawBuilder MINOS_HEROIC_POLIS = settlement(
      "minos_heroic_polis",
      "米诺斯英雄城邦",
      ModBiome.MINOS_SUNLIT_HILLS.key(),
      41000011,
      "courtyard_house",
      "olive_market",
      "training_hall",
      "council_house"
  );
  public static final JigsawBuilder SARGON_OASIS_TOWN = settlement(
      "sargon_oasis_town",
      "萨尔贡绿洲城镇",
      ModBiome.SARGON_ROCKY_DESERT.key(),
      41000012,
      "adobe_house",
      "spice_market",
      "caravanserai",
      "well_house"
  );
  public static final JigsawBuilder SAMI_SNOWPRIEST_VILLAGE = settlement(
      "sami_snowpriest_village",
      "萨米雪祀村落",
      ModBiome.SAMI_FROZEN_FOREST.key(),
      41000013,
      "snow_lodge",
      "hunter_camp",
      "ritual_house",
      "supply_shed"
  );
  public static final JigsawBuilder VICTORIA_INDUSTRIAL_BOROUGH = settlement(
      "victoria_industrial_borough",
      "维多利亚工业城区",
      ModBiome.VICTORIA_MISTY_HIGHLANDS.key(),
      41000014,
      "brick_tenement",
      "steam_workshop",
      "rail_warehouse",
      "council_hall"
  );
  public static final JigsawBuilder URSUS_NORTHERN_TOWN = settlement(
      "ursus_northern_town",
      "乌萨斯北方城镇",
      ModBiome.URSUS_FROZEN_STEPPE.key(),
      41000015,
      "heated_house",
      "military_storehouse",
      "mine_office",
      "communal_hall"
  );
  public static final JigsawBuilder KJERAG_MOUNTAIN_VILLAGE = settlement(
      "kjerag_mountain_village",
      "谢拉格山地村落",
      ModBiome.KJERAG_SNOWY_PEAKS.key(),
      41000016,
      "stone_chalet",
      "tea_workshop",
      "caravan_post",
      "shrine_house"
  );
  public static final JigsawBuilder SIRACUSA_FAMILY_TOWN = settlement(
      "siracusa_family_town",
      "叙拉古家族城镇",
      ModBiome.SIRACUSA_RAINY_WOODLAND.key(),
      41000017,
      "family_house",
      "trattoria",
      "tailor_shop",
      "meeting_hall",
      0.25F
  );
  public static final JigsawBuilder YAN_SHANGSHU_TOWN = settlement(
      "yan_shangshu_town",
      "炎国尚蜀城镇",
      ModBiome.YAN_MOUNTAIN_GROVE.key(),
      41000018,
      "courtyard_residence",
      "tea_house",
      "artisan_workshop",
      "relay_office"
  );
  public static final JigsawBuilder IBERIA_COASTAL_TOWN = settlement(
      "iberia_coastal_town",
      "伊比利亚滨海城镇",
      ModBiome.IBERIA_SALT_DELTA.key(),
      41000019,
      "saltstone_house",
      "shipwright",
      "fish_market",
      "inquisitor_office"
  );
  public static final List<JigsawBuilder> SETTLEMENTS = List.copyOf(MUTABLE_SETTLEMENTS);

  static {
    validateNationCoverage();
  }

  private ModStructure() {
  }

  /**
   * 如果国家群系失去与聚落的一一绑定关系，则在数据生成前立即失败。
   */
  private static void validateNationCoverage() {
    var biomes = new HashSet<ResourceKey<Biome>>();
    var structureIds = new HashSet<>();
    int fixedOriginCount = 0;
    for (JigsawBuilder settlement : SETTLEMENTS) {
      if (settlement.biome() == null || !biomes.add(settlement.biome())) {
        throw new IllegalStateException("十九国群系必须各自唯一绑定一个本国聚落");
      }
      if (!structureIds.add(settlement.structureKey())) {
        throw new IllegalStateException("国家聚落结构 ID 不得重复");
      }
      if (settlement.unique()
          || settlement.size() != DENSE_SETTLEMENT_JIGSAW_DEPTH
          || settlement.maxDistanceFromCenter() != DENSE_SETTLEMENT_MAX_DISTANCE) {
        throw new IllegalStateException("国家普通聚落不得使用唯一地标放置，且必须遵守统一展开上限");
      }
      if (settlement.fixedOriginPlacement()) {
        fixedOriginCount++;
      } else if (settlement.spacing() != DENSE_SETTLEMENT_SPACING
          || settlement.separation() != DENSE_SETTLEMENT_SEPARATION) {
        throw new IllegalStateException("外围国家聚落必须使用统一高密度随机散布参数");
      }
    }
    if (SETTLEMENTS.size() != 19 || biomes.size() != 19 || fixedOriginCount != 1) {
      throw new IllegalStateException("国家聚落覆盖必须恰好为 19 国");
    }
  }

  private static JigsawBuilder settlement(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int salt,
      String first,
      String second,
      String third,
      String fourth
  ) {
    return registerSettlement(
        path, zhCn, biome, salt, first, second, third, fourth,
        Types.WORLD_SURFACE_WG, 0, 0.0F
    );
  }

  private static JigsawBuilder settlement(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int salt,
      String first,
      String second,
      String third,
      String fourth,
      Types heightmap,
      int startHeight
  ) {
    return registerSettlement(
        path, zhCn, biome, salt, first, second, third, fourth,
        heightmap, startHeight, 0.0F
    );
  }

  private static JigsawBuilder settlement(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int salt,
      String first,
      String second,
      String third,
      String fourth,
      float removeVinesChance
  ) {
    return registerSettlement(
        path, zhCn, biome, salt, first, second, third, fourth,
        Types.WORLD_SURFACE_WG, 0, removeVinesChance
    );
  }

  /**
   * 注册一套国家聚落及其四类建筑模板。
   *
   * @param path              聚落、模板池和结构使用的注册路径
   * @param zhCn              聚落中文名
   * @param biome             聚落限定生成的国家群系
   * @param salt              结构集放置盐值
   * @param first             第一类常见建筑模板名，权重为 4
   * @param second            第二类建筑模板名，权重为 3
   * @param third             第三类建筑模板名，权重为 2
   * @param fourth            第四类建筑模板名，权重为 2
   * @param heightmap         起始结构投影使用的高度图；传入 {@code null} 表示固定起始高度
   * @param startHeight       相对高度图或世界底部的起始高度
   * @param removeVinesChance 每个模板处理时移除藤蔓的概率
   * @return 聚落结构、结构集、处理器和模板池的封装条目
   */
  private static JigsawBuilder registerSettlement(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int salt,
      String first,
      String second,
      String third,
      String fourth,
      Types heightmap,
      int startHeight,
      float removeVinesChance
  ) {
    StructureCatalog structures = Zinecraft.STRUCTURES;
    String templateRoot = "nation_settlements/" + path;
    var templates = new LinkedHashMap<String, Integer>();
    putTemplate(templates, first, 4);
    putTemplate(templates, second, 3);
    putTemplate(templates, third, 2);
    putTemplate(templates, fourth, 2);
    JigsawBuilder entry;
    if ("laterano_monastery_town".equals(path)) {
      entry = structures.fixedOriginSettlement(
          path, zhCn, templateRoot, biome, salt, templates,
          DENSE_SETTLEMENT_JIGSAW_DEPTH, DENSE_SETTLEMENT_MAX_DISTANCE,
          heightmap, startHeight, removeVinesChance
      );
    } else {
      entry = structures.settlement(
          path, zhCn, templateRoot, biome, salt, templates,
          DENSE_SETTLEMENT_SPACING, DENSE_SETTLEMENT_SEPARATION,
          DENSE_SETTLEMENT_JIGSAW_DEPTH, DENSE_SETTLEMENT_MAX_DISTANCE,
          heightmap, startHeight, removeVinesChance
      );
    }
    MUTABLE_SETTLEMENTS.add(entry);
    return entry;
  }

  private static void putTemplate(Map<String, Integer> templates, String path, int weight) {
    if (templates.putIfAbsent(path, weight) != null) {
      throw new IllegalArgumentException("聚落模板不能重复：" + path);
    }
  }

  private static JigsawBuilder landmark(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int ringDistance,
      Types heightmap,
      int startHeight
  ) {
    String templateRoot = "nation_landmarks/" + path;
    return Zinecraft.STRUCTURES.guaranteedLandmark(
        path,
        zhCn,
        ringDistance,
        path.hashCode(),
        7,
        112,
        0.0F,
        biome,
        ModBiome.ALL_TERRA_BIOMES,
        heightmap == null ? Types.WORLD_SURFACE_WG : heightmap,
        startHeight,
        Decoration.SURFACE_STRUCTURES,
        TerrainAdjustment.BEARD_THIN,
        builder -> {
          builder.startPool("start");
          builder.pool("start", Projection.RIGID, pool -> pool.template(templateRoot + "/foundation", 1));
          builder.pool("core", Projection.RIGID, pool -> pool.template(templateRoot + "/core", 1));
          builder.pool("facade", Projection.RIGID, pool -> pool.template(templateRoot + "/facade", 1));
          builder.pool("roof", Projection.RIGID, pool -> pool.template(templateRoot + "/roof", 1));
          builder.pool("annex", Projection.RIGID, pool -> pool.template(templateRoot + "/annex", 1));
          builder.pool("surrounding", Projection.RIGID, pool -> pool.template(templateRoot + "/surrounding", 1));
        }
    );
  }

  public static void bootstrap() {
    ModCityStructure.bootstrap();
  }
}
