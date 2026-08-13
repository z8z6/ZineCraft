package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import net.minecraft.world.level.levelgen.Heightmap

/** 每个国家群系各有两座世界级唯一地标；模板是对 PRTS 所载代表建筑的 Minecraft 缩略表达。 */
object NationLandmarks {
  /** 阿戈尔弥利亚留姆火山能源信标。 */
  val AEGIR_VOLCANIC_BEACON = landmark(
    "aegir_volcanic_beacon",
    NationBiomes.AEGIR_ABYSSAL_SEA,
    40,
    Heightmap.Types.OCEAN_FLOOR_WG
  )

  /** 阿戈尔海巡队深海观测穹顶。 */
  val AEGIR_ABYSSAL_OBSERVATORY =
    landmark("aegir_abyssal_observatory", NationBiomes.AEGIR_ABYSSAL_SEA, 52, Heightmap.Types.OCEAN_FLOOR_WG)

  /** 玻利瓦尔多索雷斯游艇。 */
  val BOLIVAR_DOSSOLES_YACHT = landmark("bolivar_dossoles_yacht", NationBiomes.BOLIVAR_PLAIN, 30)

  /** 多索雷斯极限铁人赛检查站。 */
  val BOLIVAR_RACE_CHECKPOINT = landmark("bolivar_race_checkpoint", NationBiomes.BOLIVAR_PLAIN, 46)

  /** 东国常暗裂谷神社。 */
  val HIGASHI_RIFT_SHRINE = landmark("higashi_rift_shrine", NationBiomes.HIGASHI_SHADOW_RIFT, 34)

  /** 东国锁川边境望楼。 */
  val HIGASHI_SOKOGAWA_WATCHTOWER = landmark("higashi_sokogawa_watchtower", NationBiomes.HIGASHI_SHADOW_RIFT, 50)

  /** 杜林际崖城穹顶车站。 */
  val DURIN_DOME_STATION = landmark(
    "durin_dome_station",
    NationBiomes.DURIN_UNDERGROUND_GARDEN,
    24,
    heightmap = null,
    startHeight = 24
  )

  /** 际崖城“大水坑”水上乐园。 */
  val DURIN_WATER_PARK = landmark(
    "durin_water_park",
    NationBiomes.DURIN_UNDERGROUND_GARDEN,
    40,
    heightmap = null,
    startHeight = 24
  )

  /** 哥伦比亚拓荒区科研站。 */
  val COLUMBIA_FRONTIER_LAB = landmark("columbia_frontier_lab", NationBiomes.COLUMBIA_SANDSTONE_WILDS, 32)

  /** 哥伦比亚移动监狱补给站。 */
  val COLUMBIA_PRISON_OUTPOST = landmark("columbia_prison_outpost", NationBiomes.COLUMBIA_SANDSTONE_WILDS, 48)

  /** 卡西米尔大骑士竞技场门楼。 */
  val KAZIMIERZ_ARENA_GATE = landmark("kazimierz_arena_gate", NationBiomes.KAZIMIERZ_KNIGHTLAND, 30)

  /** 卡西米尔征战骑士纪念碑。 */
  val KAZIMIERZ_KNIGHT_MONUMENT = landmark("kazimierz_knight_monument", NationBiomes.KAZIMIERZ_KNIGHTLAND, 46)

  /** 卡兹戴尔巴别塔遗迹。 */
  val KAZDEL_BABEL_RUINS = landmark("kazdel_babel_ruins", NationBiomes.KAZDEL_SCARRED_WASTES, 36)

  /** 卡兹戴尔萨卡兹流动营地。 */
  val KAZDEL_SARKAZ_CAMP = landmark("kazdel_sarkaz_camp", NationBiomes.KAZDEL_SCARRED_WASTES, 52)

  /** 拉特兰建城传说中的启示石塔。 */
  val LATERANO_REVELATION_TOWER = landmark("laterano_revelation_tower", NationBiomes.LATERANO_HOLY_FIELDS, 28)

  /** 拉特兰安布罗修修道院缩略礼拜堂。 */
  val LATERANO_AMBROSIUS_CHAPEL = landmark("laterano_ambrosius_chapel", NationBiomes.LATERANO_HOLY_FIELDS, 44)

  /** 莱塔尼亚崔林特尔梅黑白双塔。 */
  val LEITHANIEN_TWIN_SPIRES = landmark("leithanien_twin_spires", NationBiomes.LEITHANIEN_TWILIGHT_FOREST, 34)

  /** 莱塔尼亚夕照音乐厅。 */
  val LEITHANIEN_CONCERT_HALL = landmark("leithanien_concert_hall", NationBiomes.LEITHANIEN_TWILIGHT_FOREST, 50)

  /** 雷姆必拓源石矿井架。 */
  val RIM_BILLITON_MINING_DERRICK =
    landmark("rim_billiton_mining_derrick", NationBiomes.RIM_BILLITON_MINING_BADLANDS, 32)

  /** 雷姆必拓矿石运输铁路站。 */
  val RIM_BILLITON_RAIL_DEPOT = landmark("rim_billiton_rail_depot", NationBiomes.RIM_BILLITON_MINING_BADLANDS, 48)

  /** 米诺斯十二英雄神殿。 */
  val MINOS_HEROES_TEMPLE = landmark("minos_heroes_temple", NationBiomes.MINOS_SUNLIT_HILLS, 28)

  /** 米诺斯科林尼亚英雄广场。 */
  val MINOS_HEROES_PLAZA = landmark("minos_heroes_plaza", NationBiomes.MINOS_SUNLIT_HILLS, 44)

  /** 萨尔贡黄金之城宝石集市。 */
  val SARGON_GOLDEN_BAZAAR = landmark("sargon_golden_bazaar", NationBiomes.SARGON_ROCKY_DESERT, 34)

  /** 萨尔贡长泉镇古井。 */
  val SARGON_LONG_SPRING_WELL = landmark("sargon_long_spring_well", NationBiomes.SARGON_ROCKY_DESERT, 50)

  /** 萨米独眼巨人祭坛。 */
  val SAMI_CYCLOPS_ALTAR = landmark("sami_cyclops_altar", NationBiomes.SAMI_FROZEN_FOREST, 38)

  /** 萨米雪祀仪式屋。 */
  val SAMI_SNOWPRIEST_LODGE = landmark("sami_snowpriest_lodge", NationBiomes.SAMI_FROZEN_FOREST, 54)

  /** 维多利亚伦蒂尼姆城防炮。 */
  val VICTORIA_DEFENCE_CANNON = landmark("victoria_defence_cannon", NationBiomes.VICTORIA_MISTY_HIGHLANDS, 32)

  /** 维多利亚蒸汽铁路站。 */
  val VICTORIA_STEAM_STATION = landmark("victoria_steam_station", NationBiomes.VICTORIA_MISTY_HIGHLANDS, 48)

  /** 乌萨斯切尔诺伯格石棺站。 */
  val URSUS_SARCOPHAGUS_STATION = landmark("ursus_sarcophagus_station", NationBiomes.URSUS_FROZEN_STEPPE, 34)

  /** 乌萨斯远北矿区哨塔。 */
  val URSUS_NORTHERN_MINE_TOWER = landmark("ursus_northern_mine_tower", NationBiomes.URSUS_FROZEN_STEPPE, 50)

  /** 谢拉格喀兰峰蔓珠院。 */
  val KJERAG_KARLAN_MONASTERY = landmark("kjerag_karlan_monastery", NationBiomes.KJERAG_SNOWY_PEAKS, 40)

  /** 谢拉格圣山大典广场。 */
  val KJERAG_SACRED_PLAZA = landmark("kjerag_sacred_plaza", NationBiomes.KJERAG_SNOWY_PEAKS, 56)

  /** 叙拉古家族联合法院。 */
  val SIRACUSA_FAMILY_COURT = landmark("siracusa_family_court", NationBiomes.SIRACUSA_RAINY_WOODLAND, 30)

  /** 叙拉古家族歌剧院。 */
  val SIRACUSA_FAMILY_THEATRE = landmark("siracusa_family_theatre", NationBiomes.SIRACUSA_RAINY_WOODLAND, 46)

  /** 炎国玉门烽火台。 */
  val YAN_YUMEN_BEACON = landmark("yan_yumen_beacon", NationBiomes.YAN_MOUNTAIN_GROVE, 36)

  /** 炎国尚蜀山间亭。 */
  val YAN_SHANGSHU_PAVILION = landmark("yan_shangshu_pavilion", NationBiomes.YAN_MOUNTAIN_GROVE, 52)

  /** 伊比利亚之眼灯塔。 */
  val IBERIA_EYE_LIGHTHOUSE = landmark("iberia_eye_lighthouse", NationBiomes.IBERIA_SALT_DELTA, 38)

  /** 伊比利亚盐风城审判庭礼拜堂。 */
  val IBERIA_SALTVIND_CHAPEL = landmark("iberia_saltwind_chapel", NationBiomes.IBERIA_SALT_DELTA, 54)

  private fun landmark(
    path: String,
    biome: net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>,
    ringDistance: Int,
    heightmap: Heightmap.Types? = Heightmap.Types.WORLD_SURFACE_WG,
    startHeight: Int = 0
  ) = Zinecraft.STRUCTURES.uniqueLandmark(
    path = path,
    template = "nation_landmarks/$path",
    biome = biome,
    ringDistance = ringDistance,
    maxDistanceFromCenter = 96,
    heightmap = heightmap,
    startHeight = startHeight
  )
}
