package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.core.Zinecraft;

/**
 * 为探险家罗盘等读取原版结构翻译键的界面提供完整的中英文名称。
 */
public final class StructureTranslations {
  public static final StructureTranslations INSTANCE = new StructureTranslations();

  private static final String[][] NAMES = {
      {"aegir_subsea_enclave", "阿戈尔海底聚居地", "Aegir Subsea Enclave"},
      {"bolivar_dossoles_district", "玻利瓦尔多索雷斯城区", "Bolivar Dossoles District"},
      {"higashi_sokogawa_town", "东国索谷川町", "Higashi Sokogawa Town"},
      {"durin_ideal_city_block", "杜林理想城街区", "Durin Ideal City Block"},
      {"columbia_frontier_town", "哥伦比亚边疆城镇", "Columbia Frontier Town"},
      {"kazimierz_knight_borough", "卡西米尔骑士城区", "Kazimierz Knight Borough"},
      {"kazdel_sarkaz_settlement", "卡兹戴尔萨卡兹聚落", "Kazdel Sarkaz Settlement"},
      {"laterano_monastery_town", "拉特兰修道院城镇", "Laterano Monastery Town"},
      {"leithanien_music_town", "莱塔尼亚音乐城镇", "Leithanien Music Town"},
      {"rim_billiton_mining_camp", "雷姆必拓采矿营地", "Rim Billiton Mining Camp"},
      {"minos_heroic_polis", "米诺斯英雄城邦", "Minos Heroic Polis"},
      {"sargon_oasis_town", "萨尔贡绿洲城镇", "Sargon Oasis Town"},
      {"sami_snowpriest_village", "萨米雪祀村落", "Sami Snowpriest Village"},
      {"victoria_industrial_borough", "维多利亚工业城区", "Victoria Industrial Borough"},
      {"ursus_northern_town", "乌萨斯北方城镇", "Ursus Northern Town"},
      {"kjerag_mountain_village", "谢拉格山地村落", "Kjerag Mountain Village"},
      {"siracusa_family_town", "叙拉古家族城镇", "Siracusa Family Town"},
      {"yan_shangshu_town", "炎国尚蜀城镇", "Yan Shangshu Town"},
      {"iberia_coastal_town", "伊比利亚滨海城镇", "Iberia Coastal Town"},
      {"aegir_volcanic_beacon", "阿戈尔火山信标", "Aegir Volcanic Beacon"},
      {"aegir_abyssal_observatory", "阿戈尔深渊观测站", "Aegir Abyssal Observatory"},
      {"bolivar_dossoles_yacht", "玻利瓦尔多索雷斯游艇", "Bolivar Dossoles Yacht"},
      {"bolivar_race_checkpoint", "玻利瓦尔竞速检查站", "Bolivar Race Checkpoint"},
      {"higashi_rift_shrine", "东国裂谷神社", "Higashi Rift Shrine"},
      {"higashi_sokogawa_watchtower", "东国索谷川瞭望塔", "Higashi Sokogawa Watchtower"},
      {"durin_dome_station", "杜林穹顶车站", "Durin Dome Station"},
      {"durin_water_park", "杜林水上乐园", "Durin Water Park"},
      {"columbia_frontier_lab", "哥伦比亚边疆实验室", "Columbia Frontier Lab"},
      {"columbia_prison_outpost", "哥伦比亚监狱哨站", "Columbia Prison Outpost"},
      {"kazimierz_arena_gate", "卡西米尔竞技场大门", "Kazimierz Arena Gate"},
      {"kazimierz_knight_monument", "卡西米尔骑士纪念碑", "Kazimierz Knight Monument"},
      {"kazdel_babel_ruins", "卡兹戴尔巴别塔遗迹", "Kazdel Babel Ruins"},
      {"kazdel_sarkaz_camp", "卡兹戴尔萨卡兹营地", "Kazdel Sarkaz Camp"},
      {"laterano_revelation_tower", "拉特兰启示石塔", "Laterano Revelation Tower"},
      {"laterano_ambrosius_chapel", "拉特兰安布罗修礼拜堂", "Laterano Ambrosius Chapel"},
      {"leithanien_twin_spires", "莱塔尼亚双塔", "Leithanien Twin Spires"},
      {"leithanien_concert_hall", "莱塔尼亚音乐厅", "Leithanien Concert Hall"},
      {"rim_billiton_mining_derrick", "雷姆必拓采矿井架", "Rim Billiton Mining Derrick"},
      {"rim_billiton_rail_depot", "雷姆必拓铁路货站", "Rim Billiton Rail Depot"},
      {"minos_heroes_temple", "米诺斯英雄神殿", "Minos Heroes Temple"},
      {"minos_heroes_plaza", "米诺斯英雄广场", "Minos Heroes Plaza"},
      {"sargon_golden_bazaar", "萨尔贡黄金集市", "Sargon Golden Bazaar"},
      {"sargon_long_spring_well", "萨尔贡长泉水井", "Sargon Long Spring Well"},
      {"sami_cyclops_altar", "萨米独眼巨人祭坛", "Sami Cyclops Altar"},
      {"sami_snowpriest_lodge", "萨米雪祀居所", "Sami Snowpriest Lodge"},
      {"victoria_defence_cannon", "维多利亚防御炮台", "Victoria Defence Cannon"},
      {"victoria_steam_station", "维多利亚蒸汽车站", "Victoria Steam Station"},
      {"ursus_sarcophagus_station", "乌萨斯石棺站", "Ursus Sarcophagus Station"},
      {"ursus_northern_mine_tower", "乌萨斯北方矿塔", "Ursus Northern Mine Tower"},
      {"kjerag_karlan_monastery", "谢拉格喀兰修道院", "Kjerag Karlan Monastery"},
      {"kjerag_sacred_plaza", "谢拉格圣洁广场", "Kjerag Sacred Plaza"},
      {"siracusa_family_court", "叙拉古家族法庭", "Siracusa Family Court"},
      {"siracusa_family_theatre", "叙拉古家族剧院", "Siracusa Family Theatre"},
      {"yan_yumen_beacon", "炎国玉门烽台", "Yan Yumen Beacon"},
      {"yan_shangshu_pavilion", "炎国尚蜀亭阁", "Yan Shangshu Pavilion"},
      {"iberia_eye_lighthouse", "伊比利亚之眼灯塔", "Iberia Eye Lighthouse"},
      {"iberia_saltwind_chapel", "伊比利亚盐风礼拜堂", "Iberia Saltwind Chapel"},
      {"laterano_host", "拉特兰主机", "Laterano Host"},
      {"portal_ruins_common", "通用星门遗迹", "Common Stargate Ruins"},
      {"jigsaw_example", "拼图结构示例", "Jigsaw Structure Example"}
  };

  static {
    for (String[] name : NAMES) {
      Zinecraft.INSTANCE.getTRANSLATIONS().add("structure.zinecraft." + name[0], name[1], name[2]);
    }
  }

  private StructureTranslations() {
  }
}
