package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.util.CollectionSupport;
import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class ModSettlement {
  /**
   * 国家聚落采用高密度放置；最小中心间距为 17 个区块，仍大于两倍的 112 格 Jigsaw
   * 展开半径，因此相邻聚落同时扩展到边界时也不会重叠。
   */
  static final int DENSE_SETTLEMENT_SPACING = 36;
  private static final List<JigsawBuildingEntry> MUTABLE_SETTLEMENTS = new ArrayList<>();
  static final int DENSE_SETTLEMENT_SEPARATION = 16;
  static final int DENSE_SETTLEMENT_JIGSAW_DEPTH = 9;
  static final int DENSE_SETTLEMENT_MAX_DISTANCE = 112;
  public static final JigsawBuildingEntry AEGIR_SUBSEA_ENCLAVE = settlement(
      "aegir_subsea_enclave",
      "阿戈尔海底聚居地",
      ModBiome.AEGIR_ABYSSAL_SEA,
      41000001,
      "pressure_residence",
      "hydroponics_lab",
      "bathysphere_dock",
      "current_archive",
      Types.OCEAN_FLOOR_WG,
      0
  );
  public static final JigsawBuildingEntry BOLIVAR_DOSSOLES_DISTRICT = settlement(
      "bolivar_dossoles_district",
      "玻利瓦尔多索雷斯城区",
      ModBiome.BOLIVAR_PLAIN,
      41000002,
      "canal_house",
      "beach_market",
      "race_workshop",
      "festival_hall"
  );
  public static final JigsawBuildingEntry HIGASHI_SOKOGAWA_TOWN = settlement(
      "higashi_sokogawa_town",
      "东国索谷川町",
      ModBiome.HIGASHI_SHADOW_RIFT,
      41000003,
      "machiya",
      "swordsmith",
      "tea_house",
      "magistrate_house"
  );
  public static final JigsawBuildingEntry DURIN_IDEAL_CITY_BLOCK = settlement(
      "durin_ideal_city_block",
      "杜林理想城街区",
      ModBiome.DURIN_UNDERGROUND_GARDEN,
      41000004,
      "dome_apartment",
      "machine_shop",
      "arcade",
      "transit_station",
      null,
      24
  );
  public static final JigsawBuildingEntry COLUMBIA_FRONTIER_TOWN = settlement(
      "columbia_frontier_town",
      "哥伦比亚边疆城镇",
      ModBiome.COLUMBIA_SANDSTONE_WILDS,
      41000005,
      "prefab_house",
      "pioneer_lab",
      "logistics_depot",
      "sheriff_office"
  );
  public static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_BOROUGH = settlement(
      "kazimierz_knight_borough",
      "卡西米尔骑士城区",
      ModBiome.KAZIMIERZ_KNIGHTLAND,
      41000006,
      "tenement",
      "armor_workshop",
      "sponsor_shop",
      "tournament_inn"
  );
  public static final JigsawBuildingEntry KAZDEL_SARKAZ_SETTLEMENT = settlement(
      "kazdel_sarkaz_settlement",
      "卡兹戴尔萨卡兹聚落",
      ModBiome.KAZDEL_SCARRED_WASTES,
      41000007,
      "canvas_house",
      "forge",
      "mercenary_lodge",
      "provision_store"
  );
  public static final JigsawBuildingEntry LATERANO_MONASTERY_TOWN = settlement(
      "laterano_monastery_town",
      "拉特兰修道院城镇",
      ModBiome.LATERANO_HOLY_FIELDS,
      41000008,
      "white_residence",
      "confectionery",
      "notary_office",
      "bell_chapel"
  );
  public static final JigsawBuildingEntry LEITHANIEN_MUSIC_TOWN = settlement(
      "leithanien_music_town",
      "莱塔尼亚音乐城镇",
      ModBiome.LEITHANIEN_TWILIGHT_FOREST,
      41000009,
      "twilight_house",
      "instrument_workshop",
      "rehearsal_hall",
      "arts_academy"
  );
  public static final JigsawBuildingEntry RIM_BILLITON_MINING_CAMP = settlement(
      "rim_billiton_mining_camp",
      "雷姆必拓采矿营地",
      ModBiome.RIM_BILLITON_MINING_BADLANDS,
      41000010,
      "miner_bunkhouse",
      "ore_workshop",
      "freight_depot",
      "canteen"
  );
  public static final JigsawBuildingEntry MINOS_HEROIC_POLIS = settlement(
      "minos_heroic_polis",
      "米诺斯英雄城邦",
      ModBiome.MINOS_SUNLIT_HILLS,
      41000011,
      "courtyard_house",
      "olive_market",
      "training_hall",
      "council_house"
  );
  public static final JigsawBuildingEntry SARGON_OASIS_TOWN = settlement(
      "sargon_oasis_town",
      "萨尔贡绿洲城镇",
      ModBiome.SARGON_ROCKY_DESERT,
      41000012,
      "adobe_house",
      "spice_market",
      "caravanserai",
      "well_house"
  );
  public static final JigsawBuildingEntry SAMI_SNOWPRIEST_VILLAGE = settlement(
      "sami_snowpriest_village",
      "萨米雪祀村落",
      ModBiome.SAMI_FROZEN_FOREST,
      41000013,
      "snow_lodge",
      "hunter_camp",
      "ritual_house",
      "supply_shed"
  );
  public static final JigsawBuildingEntry VICTORIA_INDUSTRIAL_BOROUGH = settlement(
      "victoria_industrial_borough",
      "维多利亚工业城区",
      ModBiome.VICTORIA_MISTY_HIGHLANDS,
      41000014,
      "brick_tenement",
      "steam_workshop",
      "rail_warehouse",
      "council_hall"
  );
  public static final JigsawBuildingEntry URSUS_NORTHERN_TOWN = settlement(
      "ursus_northern_town",
      "乌萨斯北方城镇",
      ModBiome.URSUS_FROZEN_STEPPE,
      41000015,
      "heated_house",
      "military_storehouse",
      "mine_office",
      "communal_hall"
  );
  public static final JigsawBuildingEntry KJERAG_MOUNTAIN_VILLAGE = settlement(
      "kjerag_mountain_village",
      "谢拉格山地村落",
      ModBiome.KJERAG_SNOWY_PEAKS,
      41000016,
      "stone_chalet",
      "tea_workshop",
      "caravan_post",
      "shrine_house"
  );
  public static final JigsawBuildingEntry SIRACUSA_FAMILY_TOWN = settlement(
      "siracusa_family_town",
      "叙拉古家族城镇",
      ModBiome.SIRACUSA_RAINY_WOODLAND,
      41000017,
      "family_house",
      "trattoria",
      "tailor_shop",
      "meeting_hall",
      0.25F
  );
  public static final JigsawBuildingEntry YAN_SHANGSHU_TOWN = settlement(
      "yan_shangshu_town",
      "炎国尚蜀城镇",
      ModBiome.YAN_MOUNTAIN_GROVE,
      41000018,
      "courtyard_residence",
      "tea_house",
      "artisan_workshop",
      "relay_office"
  );
  public static final JigsawBuildingEntry IBERIA_COASTAL_TOWN = settlement(
      "iberia_coastal_town",
      "伊比利亚滨海城镇",
      ModBiome.IBERIA_SALT_DELTA,
      41000019,
      "saltstone_house",
      "shipwright",
      "fish_market",
      "inquisitor_office"
  );
  public static final List<JigsawBuildingEntry> SETTLEMENTS = List.copyOf(MUTABLE_SETTLEMENTS);

  static {
    validateNationCoverage();
  }

  private ModSettlement() {
  }

  /**
   * 如果国家群系失去与聚落的一一绑定关系，则在数据生成前立即失败。
   */
  private static void validateNationCoverage() {
    var biomes = new HashSet<ResourceKey<Biome>>();
    var structureIds = new HashSet<>();
    int fixedOriginCount = 0;
    for (JigsawBuildingEntry settlement : SETTLEMENTS) {
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
    if (SETTLEMENTS.size() != 19 || biomes.size() != 19 || fixedOriginCount != 1) {
      throw new IllegalStateException("国家聚落覆盖必须恰好为 19 国");
    }
  }

  private static JigsawBuildingEntry settlement(
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

  private static JigsawBuildingEntry settlement(
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

  private static JigsawBuildingEntry settlement(
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
  private static JigsawBuildingEntry registerSettlement(
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
    StructureCatalog structures = Zinecraft.WORLDGEN.structures;
    String templateRoot = "nation_settlements/" + path;
    var templates = CollectionSupport.linkedMapOf(
        Pair.of(first, 4),
        Pair.of(second, 3),
        Pair.of(third, 2),
        Pair.of(fourth, 2)
    );
    JigsawBuildingEntry entry;
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

  public static void bootstrap() {
  }
}
