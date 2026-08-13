package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomes
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.Heightmap

/** 十九个国家群系中可重复生成的大型 Jigsaw 城镇、村落和营地。 */
object NationSettlements {
  /** 阿戈尔海底聚居区：压力住宅、水培实验室、深潜码头与海流档案馆。 */
  val AEGIR_SUBSEA_ENCLAVE = settlement(
    "aegir_subsea_enclave", NationBiomes.AEGIR_ABYSSAL_SEA, 41000001,
    "pressure_residence", "hydroponics_lab", "bathysphere_dock", "current_archive",
    spacing = 64, heightmap = Heightmap.Types.OCEAN_FLOOR_WG
  )

  /** 玻利瓦尔多索雷斯街区：运河住宅、海滩市场、赛事工坊与庆典厅。 */
  val BOLIVAR_DOSSOLES_DISTRICT = settlement(
    "bolivar_dossoles_district", NationBiomes.BOLIVAR_PLAIN, 41000002,
    "canal_house", "beach_market", "race_workshop", "festival_hall"
  )

  /** 东国锁川町：町屋、锻刀铺、茶屋与奉行所。 */
  val HIGASHI_SOKOGAWA_TOWN = settlement(
    "higashi_sokogawa_town", NationBiomes.HIGASHI_SHADOW_RIFT, 41000003,
    "machiya", "swordsmith", "tea_house", "magistrate_house"
  )

  /** 杜林理想城街区：穹顶公寓、机械工坊、游戏厅与轨道站。 */
  val DURIN_IDEAL_CITY_BLOCK = settlement(
    "durin_ideal_city_block", NationBiomes.DURIN_UNDERGROUND_GARDEN, 41000004,
    "dome_apartment", "machine_shop", "arcade", "transit_station",
    spacing = 60, heightmap = null, startHeight = 24
  )

  /** 哥伦比亚拓荒镇：装配住宅、拓荒实验室、物流仓库与治安所。 */
  val COLUMBIA_FRONTIER_TOWN = settlement(
    "columbia_frontier_town", NationBiomes.COLUMBIA_SANDSTONE_WILDS, 41000005,
    "prefab_house", "pioneer_lab", "logistics_depot", "sheriff_office"
  )

  /** 卡西米尔骑士城区：公寓、甲胄工坊、赞助商店与竞赛旅店。 */
  val KAZIMIERZ_KNIGHT_BOROUGH = settlement(
    "kazimierz_knight_borough", NationBiomes.KAZIMIERZ_KNIGHTLAND, 41000006,
    "tenement", "armor_workshop", "sponsor_shop", "tournament_inn"
  )

  /** 卡兹戴尔萨卡兹聚落：帐屋、铸炉、佣兵会所与补给铺。 */
  val KAZDEL_SARKAZ_SETTLEMENT = settlement(
    "kazdel_sarkaz_settlement", NationBiomes.KAZDEL_SCARRED_WASTES, 41000007,
    "canvas_house", "forge", "mercenary_lodge", "provision_store"
  )

  /** 拉特兰修道院镇：白石住宅、甜品店、公证所与钟楼礼拜堂。 */
  val LATERANO_MONASTERY_TOWN = settlement(
    "laterano_monastery_town", NationBiomes.LATERANO_HOLY_FIELDS, 41000008,
    "white_residence", "confectionery", "notary_office", "bell_chapel"
  )

  /** 莱塔尼亚音乐镇：暮色住宅、乐器工坊、排练厅与艺术学院。 */
  val LEITHANIEN_MUSIC_TOWN = settlement(
    "leithanien_music_town", NationBiomes.LEITHANIEN_TWILIGHT_FOREST, 41000009,
    "twilight_house", "instrument_workshop", "rehearsal_hall", "arts_academy"
  )

  /** 雷姆必拓矿业营地：矿工宿舍、矿石工坊、货运站与食堂。 */
  val RIM_BILLITON_MINING_CAMP = settlement(
    "rim_billiton_mining_camp", NationBiomes.RIM_BILLITON_MINING_BADLANDS, 41000010,
    "miner_bunkhouse", "ore_workshop", "freight_depot", "canteen"
  )

  /** 米诺斯英雄城邦：院落住宅、橄榄市场、训练厅与议事厅。 */
  val MINOS_HEROIC_POLIS = settlement(
    "minos_heroic_polis", NationBiomes.MINOS_SUNLIT_HILLS, 41000011,
    "courtyard_house", "olive_market", "training_hall", "council_house"
  )

  /** 萨尔贡绿洲镇：土坯住宅、香料市场、商旅驿站与水井房。 */
  val SARGON_OASIS_TOWN = settlement(
    "sargon_oasis_town", NationBiomes.SARGON_ROCKY_DESERT, 41000012,
    "adobe_house", "spice_market", "caravanserai", "well_house"
  )

  /** 萨米雪祀村：雪屋、猎人营地、仪式屋与补给棚。 */
  val SAMI_SNOWPRIEST_VILLAGE = settlement(
    "sami_snowpriest_village", NationBiomes.SAMI_FROZEN_FOREST, 41000013,
    "snow_lodge", "hunter_camp", "ritual_house", "supply_shed"
  )

  /** 维多利亚工业城区：砖砌公寓、蒸汽工坊、铁路仓库与市政厅。 */
  val VICTORIA_INDUSTRIAL_BOROUGH = settlement(
    "victoria_industrial_borough", NationBiomes.VICTORIA_MISTY_HIGHLANDS, 41000014,
    "brick_tenement", "steam_workshop", "rail_warehouse", "council_hall"
  )

  /** 乌萨斯北方城镇：保温住宅、军需仓库、矿务所与公共大厅。 */
  val URSUS_NORTHERN_TOWN = settlement(
    "ursus_northern_town", NationBiomes.URSUS_FROZEN_STEPPE, 41000015,
    "heated_house", "military_storehouse", "mine_office", "communal_hall"
  )

  /** 谢拉格山村：石木民居、茶坊、商队驿站与祭祀屋。 */
  val KJERAG_MOUNTAIN_VILLAGE = settlement(
    "kjerag_mountain_village", NationBiomes.KJERAG_SNOWY_PEAKS, 41000016,
    "stone_chalet", "tea_workshop", "caravan_post", "shrine_house"
  )

  /** 叙拉古家族镇：家族住宅、餐馆、裁缝铺与议事厅。 */
  val SIRACUSA_FAMILY_TOWN = settlement(
    "siracusa_family_town", NationBiomes.SIRACUSA_RAINY_WOODLAND, 41000017,
    "family_house", "trattoria", "tailor_shop", "meeting_hall",
    removeVinesChance = 0.25f
  )

  /** 炎国尚蜀山城：院落住宅、茶馆、百工坊与驿站。 */
  val YAN_SHANGSHU_TOWN = settlement(
    "yan_shangshu_town", NationBiomes.YAN_MOUNTAIN_GROVE, 41000018,
    "courtyard_residence", "tea_house", "artisan_workshop", "relay_office"
  )

  /** 伊比利亚海岸镇：盐石住宅、造船坊、鱼市与审判庭办事处。 */
  val IBERIA_COASTAL_TOWN = settlement(
    "iberia_coastal_town", NationBiomes.IBERIA_SALT_DELTA, 41000019,
    "saltstone_house", "shipwright", "fish_market", "inquisitor_office"
  )

  private fun settlement(
    path: String,
    biome: ResourceKey<Biome>,
    salt: Int,
    first: String,
    second: String,
    third: String,
    fourth: String,
    spacing: Int = 52,
    heightmap: Heightmap.Types? = Heightmap.Types.WORLD_SURFACE_WG,
    startHeight: Int = 0,
    removeVinesChance: Float = 0f
  ) = Zinecraft.STRUCTURES.settlement(
    path = path,
    templateRoot = "nation_settlements/$path",
    biome = biome,
    salt = salt,
    buildingTemplates = linkedMapOf(first to 4, second to 3, third to 2, fourth to 2),
    spacing = spacing,
    separation = 24,
    size = 7,
    maxDistanceFromCenter = 112,
    heightmap = heightmap,
    startHeight = startHeight,
    removeVinesChance = removeVinesChance
  )
}
