package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

public final class ModLandmark {

  public static final ModLandmark INSTANCE = new ModLandmark();

  public static final JigsawBuildingEntry AEGIR_VOLCANIC_BEACON = landmark("aegir_volcanic_beacon", "阿戈尔火山信标", ModBiome.AEGIR_ABYSSAL_SEA, 40, Types.OCEAN_FLOOR_WG, 0);

  public static final JigsawBuildingEntry AEGIR_ABYSSAL_OBSERVATORY = landmark("aegir_abyssal_observatory", "阿戈尔深渊观测站", ModBiome.AEGIR_ABYSSAL_SEA, 52, Types.OCEAN_FLOOR_WG, 0);

  public static final JigsawBuildingEntry BOLIVAR_DOSSOLES_YACHT = landmark("bolivar_dossoles_yacht", "玻利瓦尔多索雷斯游艇", ModBiome.BOLIVAR_PLAIN, 30, null, 0);

  public static final JigsawBuildingEntry BOLIVAR_RACE_CHECKPOINT = landmark("bolivar_race_checkpoint", "玻利瓦尔竞速检查站", ModBiome.BOLIVAR_PLAIN, 46, null, 0);

  public static final JigsawBuildingEntry HIGASHI_RIFT_SHRINE = landmark("higashi_rift_shrine", "东国裂谷神社", ModBiome.HIGASHI_SHADOW_RIFT, 34, null, 0);

  public static final JigsawBuildingEntry HIGASHI_SOKOGAWA_WATCHTOWER = landmark("higashi_sokogawa_watchtower", "东国索谷川瞭望塔", ModBiome.HIGASHI_SHADOW_RIFT, 50, null, 0);

  public static final JigsawBuildingEntry DURIN_DOME_STATION = landmark(
      "durin_dome_station", "杜林穹顶车站", ModBiome.DURIN_UNDERGROUND_GARDEN, 24, null, 24
  );

  public static final JigsawBuildingEntry DURIN_WATER_PARK = landmark(
      "durin_water_park", "杜林水上乐园", ModBiome.DURIN_UNDERGROUND_GARDEN, 40, null, 24
  );

  public static final JigsawBuildingEntry COLUMBIA_FRONTIER_LAB = landmark("columbia_frontier_lab", "哥伦比亚边疆实验室", ModBiome.COLUMBIA_SANDSTONE_WILDS, 32, null, 0);

  public static final JigsawBuildingEntry COLUMBIA_PRISON_OUTPOST = landmark("columbia_prison_outpost", "哥伦比亚监狱哨站", ModBiome.COLUMBIA_SANDSTONE_WILDS, 48, null, 0);

  public static final JigsawBuildingEntry KAZIMIERZ_ARENA_GATE = landmark("kazimierz_arena_gate", "卡西米尔竞技场大门", ModBiome.KAZIMIERZ_KNIGHTLAND, 30, null, 0);

  public static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_MONUMENT = landmark("kazimierz_knight_monument", "卡西米尔骑士纪念碑", ModBiome.KAZIMIERZ_KNIGHTLAND, 46, null, 0);

  public static final JigsawBuildingEntry KAZDEL_BABEL_RUINS = landmark("kazdel_babel_ruins", "卡兹戴尔巴别塔遗迹", ModBiome.KAZDEL_SCARRED_WASTES, 36, null, 0);

  public static final JigsawBuildingEntry KAZDEL_SARKAZ_CAMP = landmark("kazdel_sarkaz_camp", "卡兹戴尔萨卡兹营地", ModBiome.KAZDEL_SCARRED_WASTES, 52, null, 0);

  public static final JigsawBuildingEntry LATERANO_REVELATION_TOWER = landmark("laterano_revelation_tower", "拉特兰启示石塔", ModBiome.LATERANO_HOLY_FIELDS, 28, null, 0);

  public static final JigsawBuildingEntry LATERANO_AMBROSIUS_CHAPEL = landmark("laterano_ambrosius_chapel", "拉特兰安布罗修礼拜堂", ModBiome.LATERANO_HOLY_FIELDS, 44, null, 0);

  public static final JigsawBuildingEntry LEITHANIEN_TWIN_SPIRES = landmark("leithanien_twin_spires", "莱塔尼亚双塔", ModBiome.LEITHANIEN_TWILIGHT_FOREST, 34, null, 0);

  public static final JigsawBuildingEntry LEITHANIEN_CONCERT_HALL = landmark("leithanien_concert_hall", "莱塔尼亚音乐厅", ModBiome.LEITHANIEN_TWILIGHT_FOREST, 50, null, 0);

  public static final JigsawBuildingEntry RIM_BILLITON_MINING_DERRICK = landmark("rim_billiton_mining_derrick", "雷姆必拓采矿井架", ModBiome.RIM_BILLITON_MINING_BADLANDS, 32, null, 0);

  public static final JigsawBuildingEntry RIM_BILLITON_RAIL_DEPOT = landmark("rim_billiton_rail_depot", "雷姆必拓铁路货站", ModBiome.RIM_BILLITON_MINING_BADLANDS, 48, null, 0);

  public static final JigsawBuildingEntry MINOS_HEROES_TEMPLE = landmark("minos_heroes_temple", "米诺斯英雄神殿", ModBiome.MINOS_SUNLIT_HILLS, 28, null, 0);

  public static final JigsawBuildingEntry MINOS_HEROES_PLAZA = landmark("minos_heroes_plaza", "米诺斯英雄广场", ModBiome.MINOS_SUNLIT_HILLS, 44, null, 0);

  public static final JigsawBuildingEntry SARGON_GOLDEN_BAZAAR = landmark("sargon_golden_bazaar", "萨尔贡黄金集市", ModBiome.SARGON_ROCKY_DESERT, 34, null, 0);

  public static final JigsawBuildingEntry SARGON_LONG_SPRING_WELL = landmark("sargon_long_spring_well", "萨尔贡长泉水井", ModBiome.SARGON_ROCKY_DESERT, 50, null, 0);

  public static final JigsawBuildingEntry SAMI_CYCLOPS_ALTAR = landmark("sami_cyclops_altar", "萨米独眼巨人祭坛", ModBiome.SAMI_FROZEN_FOREST, 38, null, 0);

  public static final JigsawBuildingEntry SAMI_SNOWPRIEST_LODGE = landmark("sami_snowpriest_lodge", "萨米雪祀居所", ModBiome.SAMI_FROZEN_FOREST, 54, null, 0);

  public static final JigsawBuildingEntry VICTORIA_DEFENCE_CANNON = landmark("victoria_defence_cannon", "维多利亚防御炮台", ModBiome.VICTORIA_MISTY_HIGHLANDS, 32, null, 0);

  public static final JigsawBuildingEntry VICTORIA_STEAM_STATION = landmark("victoria_steam_station", "维多利亚蒸汽车站", ModBiome.VICTORIA_MISTY_HIGHLANDS, 48, null, 0);

  public static final JigsawBuildingEntry URSUS_SARCOPHAGUS_STATION = landmark("ursus_sarcophagus_station", "乌萨斯石棺站", ModBiome.URSUS_FROZEN_STEPPE, 34, null, 0);

  public static final JigsawBuildingEntry URSUS_NORTHERN_MINE_TOWER = landmark("ursus_northern_mine_tower", "乌萨斯北方矿塔", ModBiome.URSUS_FROZEN_STEPPE, 50, null, 0);

  public static final JigsawBuildingEntry KJERAG_KARLAN_MONASTERY = landmark("kjerag_karlan_monastery", "谢拉格喀兰修道院", ModBiome.KJERAG_SNOWY_PEAKS, 40, null, 0);

  public static final JigsawBuildingEntry KJERAG_SACRED_PLAZA = landmark("kjerag_sacred_plaza", "谢拉格圣洁广场", ModBiome.KJERAG_SNOWY_PEAKS, 56, null, 0);

  public static final JigsawBuildingEntry SIRACUSA_FAMILY_COURT = landmark("siracusa_family_court", "叙拉古家族法庭", ModBiome.SIRACUSA_RAINY_WOODLAND, 30, null, 0);

  public static final JigsawBuildingEntry SIRACUSA_FAMILY_THEATRE = landmark("siracusa_family_theatre", "叙拉古家族剧院", ModBiome.SIRACUSA_RAINY_WOODLAND, 46, null, 0);

  public static final JigsawBuildingEntry YAN_YUMEN_BEACON = landmark("yan_yumen_beacon", "炎国玉门烽台", ModBiome.YAN_MOUNTAIN_GROVE, 36, null, 0);

  public static final JigsawBuildingEntry YAN_SHANGSHU_PAVILION = landmark("yan_shangshu_pavilion", "炎国尚蜀亭阁", ModBiome.YAN_MOUNTAIN_GROVE, 52, null, 0);

  public static final JigsawBuildingEntry IBERIA_EYE_LIGHTHOUSE = landmark("iberia_eye_lighthouse", "伊比利亚之眼灯塔", ModBiome.IBERIA_SALT_DELTA, 38, null, 0);

  public static final JigsawBuildingEntry IBERIA_SALTVIND_CHAPEL = landmark("iberia_saltwind_chapel", "伊比利亚盐风礼拜堂", ModBiome.IBERIA_SALT_DELTA, 54, null, 0);

  private ModLandmark() {
  }

  private static JigsawBuildingEntry landmark(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      int ringDistance,
      Types heightmap,
      int startHeight
  ) {
    String templateRoot = "nation_landmarks/" + path;
    return Zinecraft.WORLDGEN.getStructures().guaranteedLandmark(
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
          builder.setStartPool("start");
          builder.pool("start", Projection.RIGID, pool -> pool.template(templateRoot + "/foundation", 1));
          builder.pool("core", Projection.RIGID, pool -> pool.template(templateRoot + "/core", 1));
          builder.pool("facade", Projection.RIGID, pool -> pool.template(templateRoot + "/facade", 1));
          builder.pool("roof", Projection.RIGID, pool -> pool.template(templateRoot + "/roof", 1));
          builder.pool("annex", Projection.RIGID, pool -> pool.template(templateRoot + "/annex", 1));
          builder.pool("surrounding", Projection.RIGID, pool -> pool.template(templateRoot + "/surrounding", 1));
        }
    );
  }
}
