package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.PlotSize;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.RegionLayoutType;
import com.cxxcxx.zinecraft.core.Zinecraft;

import java.util.List;

/**
 * 泰拉城区及其合法建筑静态声明。
 */
public final class ModCityRegion {
  public static final TerraCityRegionBuilder MILLIARIUM_CORE = region(ModNation.AEGIR, "弥利亚留姆核心区", RegionLayoutType.CONCENTRIC, ModStructure.AEGIR_VOLCANIC_BEACON, ModStructure.AEGIR_ABYSSAL_OBSERVATORY, ModStructure.AEGIR_SHOP).unique();
  public static final TerraCityRegionBuilder DOSSOLES_CORE = region(ModNation.BOLIVAR, "多索雷斯核心区", RegionLayoutType.CONCENTRIC, ModStructure.BOLIVAR_DOSSOLES_YACHT, ModStructure.BOLIVAR_RACE_CHECKPOINT).unique();
  public static final TerraCityRegionBuilder LA_UNIDAD_CORE = region(ModNation.BOLIVAR, "拉乌尼达核心区", RegionLayoutType.CONCENTRIC, ModStructure.BOLIVAR_SHOP).unique();
  public static final TerraCityRegionBuilder TECOMA_CORE = region(ModNation.BOLIVAR, "特科马核心区", RegionLayoutType.CONCENTRIC, ModStructure.BOLIVAR_SHOP).unique();
  public static final TerraCityRegionBuilder SOUTHERN_COURT_IMPERIAL_SHRINE_CORE = region(ModNation.HIGASHI, "南院行在御机大社核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder NORTHERN_COURT_SOKOGAWA_CASTLE_CORE = region(ModNation.HIGASHI, "北院镇守锁川城核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_RIFT_SHRINE, ModStructure.HIGASHI_SOKOGAWA_WATCHTOWER).unique();
  public static final TerraCityRegionBuilder HIMEJI_CASTLE_CORE = region(ModNation.HIGASHI, "姬户城核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder USHIROKAWA_CASTLE_CORE = region(ModNation.HIGASHI, "后川城核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder NITO_JO_CORE = region(ModNation.HIGASHI, "二户城核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder SHIN_AKI_CITY_CORE = region(ModNation.HIGASHI, "新安芸市核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder ROKA_VILLAGE_CORE = region(ModNation.HIGASHI, "露华村核心区", RegionLayoutType.CONCENTRIC, ModStructure.HIGASHI_SHOP).unique();
  public static final TerraCityRegionBuilder NEW_ZERUERTZA_CORE = region(ModNation.DURIN, "际崖城核心区", RegionLayoutType.CONCENTRIC, ModStructure.DURIN_DOME_STATION, ModStructure.DURIN_WATER_PARK).unique();
  public static final TerraCityRegionBuilder ORTZIMUGA_CORE = region(ModNation.DURIN, "天际城核心区", RegionLayoutType.CONCENTRIC, ModStructure.DURIN_SHOP).unique();
  public static final TerraCityRegionBuilder TRIMOUNTS_CORE = region(ModNation.COLUMBIA, "特里蒙核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_FRONTIER_LAB, ModStructure.COLUMBIA_PRISON_OUTPOST).unique();
  public static final TerraCityRegionBuilder TKARONTO_CORE = region(ModNation.COLUMBIA, "提卡伦多核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder BUNKERHILL_CITY_CORE = region(ModNation.COLUMBIA, "堡垒山城核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder NULAITEBURGH_CORE = region(ModNation.COLUMBIA, "纽莱堡市核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder SAINT_SOPHIA_CITY_CORE = region(ModNation.COLUMBIA, "圣苏菲城核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder IRONFORGE_CORE = region(ModNation.COLUMBIA, "铸铁城核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder NORTHVILLE_CORE = region(ModNation.COLUMBIA, "北诺斯维尔核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder NEW_MANFIST_CORE = region(ModNation.COLUMBIA, "新曼法斯特核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder BLUECARD_CORE = region(ModNation.COLUMBIA, "蓝卡坞核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder GIVOGIA_CORE = region(ModNation.COLUMBIA, "吉沃吉亚核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder STEELHAM_CORE = region(ModNation.COLUMBIA, "铁驮镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder DAVISTOWN_CORE = region(ModNation.COLUMBIA, "达维镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder MAX_DC_CORE = region(ModNation.COLUMBIA, "麦克斯特区核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder PROPELLER_PARADISE_CORE = region(ModNation.COLUMBIA, "螺旋桨天堂核心区", RegionLayoutType.CONCENTRIC, ModStructure.COLUMBIA_SHOP).unique();
  public static final TerraCityRegionBuilder GRAND_KNIGHT_TERRITORY_CORE = region(ModNation.KAZIMIERZ, "卡瓦莱利亚基（大骑士领）核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_ARENA_GATE, ModStructure.KAZIMIERZ_KNIGHT_MONUMENT).unique();
  public static final TerraCityRegionBuilder DZWONEK_CORE = region(ModNation.KAZIMIERZ, "茨沃涅克核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_SHOP).unique();
  public static final TerraCityRegionBuilder OGNISKO_CORE = region(ModNation.KAZIMIERZ, "奥格尼斯科核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_SHOP).unique();
  public static final TerraCityRegionBuilder DEWVILLE_CORE = region(ModNation.KAZIMIERZ, "滴水村核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_SHOP).unique();
  public static final TerraCityRegionBuilder ROCKVILLE_CORE = region(ModNation.KAZIMIERZ, "垒石村核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_SHOP).unique();
  public static final TerraCityRegionBuilder STRUMYKOWO_CORE = region(ModNation.KAZIMIERZ, "沥泉村核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZIMIERZ_SHOP).unique();
  public static final TerraCityRegionBuilder KAZDEL_CORE = region(ModNation.KAZDEL, "卡兹戴尔城（今卡兹戴尔）核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZDEL_BABEL_RUINS, ModStructure.KAZDEL_SARKAZ_CAMP).unique();
  public static final TerraCityRegionBuilder BELLONY_VILLAGE_CORE = region(ModNation.KAZDEL, "贝罗尼村核心区", RegionLayoutType.CONCENTRIC, ModStructure.KAZDEL_SHOP).unique();
  public static final TerraCityRegionBuilder PAGUS_STEVONUS_CORE = region(ModNation.LATERANO, "司提望区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_REVELATION_TOWER).unique();
  public static final TerraCityRegionBuilder PAGUS_AMBROSIUS_CORE = region(ModNation.LATERANO, "安布罗修区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_AMBROSIUS_CHAPEL).unique();
  public static final TerraCityRegionBuilder PAGUS_FABER_CORE = region(ModNation.LATERANO, "法柏尔区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_SHOP).unique();
  public static final TerraCityRegionBuilder PAGUS_GRIFFIN_CORE = region(ModNation.LATERANO, "格芬区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_SHOP).unique();
  public static final TerraCityRegionBuilder PAGUS_MICHAELION_CORE = region(ModNation.LATERANO, "米迦莱昂区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_SHOP).unique();
  public static final TerraCityRegionBuilder PAGUS_SAINT_MARCEL_CORE = region(ModNation.LATERANO, "圣马尔索区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_SHOP).unique();
  public static final TerraCityRegionBuilder PAGUS_ECCLESIA_CORE = region(ModNation.LATERANO, "伊卡莱西亚区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LATERANO_HOST).unique();
  public static final TerraCityRegionBuilder ZWILLINGSTURME_CORE = region(ModNation.LEITHANIEN, "崔林特尔梅核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_TWIN_SPIRES, ModStructure.LEITHANIEN_CONCERT_HALL).unique();
  public static final TerraCityRegionBuilder WOLUMONDE_CORE = region(ModNation.LEITHANIEN, "沃伦姆德核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder VYSEHEIM_CORE = region(ModNation.LEITHANIEN, "维谢海姆核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder GRINDEN_CORE = region(ModNation.LEITHANIEN, "格林登核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder KREIS_HELDENSCHWERT_CORE = region(ModNation.LEITHANIEN, "海登施威尔大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder WASSERLAND_CORE = region(ModNation.LEITHANIEN, "瓦瑟领大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder KREIS_FURTGANG_CORE = region(ModNation.LEITHANIEN, "福特冈大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder KREIS_OSTENHEIM_CORE = region(ModNation.LEITHANIEN, "奥施登海姆大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder KREIS_KEPLANI_CORE = region(ModNation.LEITHANIEN, "凯普拉尼大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder KREIS_EINGEWEIDE_CORE = region(ModNation.LEITHANIEN, "恩瓦德大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder LUPUKARN_CORE = region(ModNation.LEITHANIEN, "鲁珀坎大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder STURMLAND_FELS_CORE = region(ModNation.LEITHANIEN, "施彤领大区（费尔斯）核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder ERDENHERRE_CORE = region(ModNation.LEITHANIEN, "厄登赫尔大区核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder URTICA_GRAFSCHAFT_CORE = region(ModNation.LEITHANIEN, "乌提卡领核心区", RegionLayoutType.CONCENTRIC, ModStructure.LEITHANIEN_SHOP).unique();
  public static final TerraCityRegionBuilder ULTIMATE_IRON_HOLD_CORE = region(ModNation.RIM_BILLITON, "终极大铁屯核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_RAIL_DEPOT).unique();
  public static final TerraCityRegionBuilder IRON_CARROT_CITY_CORE = region(ModNation.RIM_BILLITON, "钢铁萝卜城核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder IRON_FIST_CITY_CORE = region(ModNation.RIM_BILLITON, "铁腕城核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder GREAT_SPRING_TOWN_CORE = region(ModNation.RIM_BILLITON, "大涌泉镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder ARTICHOKE_VILLAGE_CORE = region(ModNation.RIM_BILLITON, "洋蓟村核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder TURNIP_TOWN_CORE = region(ModNation.RIM_BILLITON, "芜菁镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder SUN_VALLEY_CORE = region(ModNation.RIM_BILLITON, "太阳谷核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder RED_SAND_TOWN_CORE = region(ModNation.RIM_BILLITON, "红砂镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder RUSTDREG_TOWN_CORE = region(ModNation.RIM_BILLITON, "锈渣子镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder GREENMEADOW_SHIRE_CORE = region(ModNation.RIM_BILLITON, "格林梅多自治州核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder BIG_PILLAR_SHIRE_CORE = region(ModNation.RIM_BILLITON, "比格皮勒自治州核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder DOUBLE_HELMET_MINE_CORE = region(ModNation.RIM_BILLITON, "双倍黑尔梅特矿区核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_MINING_DERRICK).unique();
  public static final TerraCityRegionBuilder HIGHWAY_ZERO_CORE = region(ModNation.RIM_BILLITON, "零号公路核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder SOUTHERN_REACH_CORE = region(ModNation.RIM_BILLITON, "南境核心区", RegionLayoutType.CONCENTRIC, ModStructure.RIM_BILLITON_SHOP).unique();
  public static final TerraCityRegionBuilder KORINTHIA_CORE = region(ModNation.MINOS, "科林尼亚核心区", RegionLayoutType.CONCENTRIC, ModStructure.MINOS_HEROES_TEMPLE, ModStructure.MINOS_HEROES_PLAZA).unique();
  public static final TerraCityRegionBuilder ATHENIUS_CORE = region(ModNation.MINOS, "雅赛努斯核心区", RegionLayoutType.CONCENTRIC, ModStructure.MINOS_SHOP).unique();
  public static final TerraCityRegionBuilder LACHEDAMON_CORE = region(ModNation.MINOS, "拉刻代蒙核心区", RegionLayoutType.CONCENTRIC, ModStructure.MINOS_SHOP).unique();
  public static final TerraCityRegionBuilder AKROTIRI_VILLAGE_CORE = region(ModNation.MINOS, "阿克罗蒂村核心区", RegionLayoutType.CONCENTRIC, ModStructure.MINOS_SHOP).unique();
  public static final TerraCityRegionBuilder AEGEAN_CORE = region(ModNation.MINOS, "爱琴核心区", RegionLayoutType.CONCENTRIC, ModStructure.MINOS_SHOP).unique();
  public static final TerraCityRegionBuilder LONG_SPRING_TOWN_CORE = region(ModNation.SARGON, "长泉镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_LONG_SPRING_WELL).unique();
  public static final TerraCityRegionBuilder PHECON_CORE = region(ModNation.SARGON, "费坤城核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_SHOP).unique();
  public static final TerraCityRegionBuilder ACAHUALLA_CORE = region(ModNation.SARGON, "阿卡胡拉核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_SHOP).unique();
  public static final TerraCityRegionBuilder IBUT_REGION_CORE = region(ModNation.SARGON, "伊巴特地区核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_SHOP).unique();
  public static final TerraCityRegionBuilder WEST_VOUIVRE_CORE = region(ModNation.SARGON, "瓦伊凡核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_SHOP).unique();
  public static final TerraCityRegionBuilder MENAT_HAMAIT_CORE = region(ModNation.SARGON, "米纳特哈玛仪核心区", RegionLayoutType.CONCENTRIC, ModStructure.SARGON_GOLDEN_BAZAAR).unique();
  public static final TerraCityRegionBuilder SIESTA_CORE = region(ModNation.SIESTA, "汐斯塔核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIESTA_SHOP).unique();
  public static final TerraCityRegionBuilder CAPPAT_CORE = region(ModNation.SAMI, "察帕特核心区", RegionLayoutType.CONCENTRIC, ModStructure.SAMI_SNOWPRIEST_LODGE, ModStructure.SAMI_SHOP).unique();
  public static final TerraCityRegionBuilder FIRST_LAND_CORE = region(ModNation.SAMI, "原初之地核心区", RegionLayoutType.CONCENTRIC, ModStructure.STARGATE, ModStructure.SAMI_CYCLOPS_ALTAR).unique();
  public static final TerraCityRegionBuilder LONDINIUM_CORE = region(ModNation.VICTORIA, "伦蒂尼姆核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_DEFENCE_CANNON, ModStructure.VICTORIA_STEAM_STATION).unique();
  public static final TerraCityRegionBuilder REDRIDGE_CORE = region(ModNation.VICTORIA, "红脊镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder CHETLEIGH_CORE = region(ModNation.VICTORIA, "切特雷镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder BRENTWOOD_CORE = region(ModNation.VICTORIA, "布伦特伍德镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder GIBSONHAM_CORE = region(ModNation.VICTORIA, "吉布森镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder GREEWICH_CORE = region(ModNation.VICTORIA, "格瑞威治核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder LYNNCARDINE_CORE = region(ModNation.VICTORIA, "丽茵卡登核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder CALADON_CORE = region(ModNation.VICTORIA, "卡拉顿核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder BOSCHENDAL_CORE = region(ModNation.VICTORIA, "博森德尔核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_TORON_CORE = region(ModNation.VICTORIA, "多伦郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_HILLOCK_CORE = region(ModNation.VICTORIA, "小丘郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_ASCARAT_CORE = region(ModNation.VICTORIA, "阿斯卡拉郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_LYNTON_CORE = region(ModNation.VICTORIA, "林顿郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_PENINSULA_CORE = region(ModNation.VICTORIA, "半岛郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder SYKES_CORE = region(ModNation.VICTORIA, "塞克郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder CASTSHIRE_CORE = region(ModNation.VICTORIA, "开夏郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder COUNTY_OAK_GROVE_CORE = region(ModNation.VICTORIA, "橡林郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder TRENT_CORE = region(ModNation.VICTORIA, "特伦特郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.VICTORIA_SHOP).unique();
  public static final TerraCityRegionBuilder DEITY_GRYPHERBURG_CORE = region(ModNation.URSUS, "圣骏堡核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder ZELGRAD_CORE = region(ModNation.URSUS, "泽尔格勒（卫星城）核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder CHERNOBOG_CORE = region(ModNation.URSUS, "切尔诺伯格核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SARCOPHAGUS_STATION).unique();
  public static final TerraCityRegionBuilder TULISKAYA_CORE = region(ModNation.URSUS, "图利斯卡亚核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder NOVO_PETROVSK_CORE = region(ModNation.URSUS, "新彼得罗夫斯克核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder TAMANGRAD_CORE = region(ModNation.URSUS, "塔曼格勒德核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder VYATNO_CORE = region(ModNation.URSUS, "维亚特诺核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder ZAMOLESK_CORE = region(ModNation.URSUS, "扎莫列斯核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder PETRODANOR_CORE = region(ModNation.URSUS, "彼得达诺尔核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder VEROBINSK_CORE = region(ModNation.URSUS, "维罗比斯科镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder GRIGORY_GOVERNORATE_CORE = region(ModNation.URSUS, "格里高利省核心区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_SHOP).unique();
  public static final TerraCityRegionBuilder TURICUM_CORE = region(ModNation.KJERAG, "图里卡姆核心区", RegionLayoutType.CONCENTRIC, ModStructure.KJERAG_KARLAN_MONASTERY, ModStructure.KJERAG_SACRED_PLAZA, ModStructure.KJERAG_SHOP).unique();
  public static final TerraCityRegionBuilder MONTELUPE_CORE = region(ModNation.SIRACUSA, "蒙特卢佩核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_FAMILY_COURT).unique();
  public static final TerraCityRegionBuilder SETTE_COLLI_CORE = region(ModNation.SIRACUSA, "七丘城核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_SHOP).unique();
  public static final TerraCityRegionBuilder WHITE_CITY_CORE = region(ModNation.SIRACUSA, "怀特城核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_SHOP).unique();
  public static final TerraCityRegionBuilder LOCOMOTIVA_CITY_CORE = region(ModNation.SIRACUSA, "拉克玛蒂瓦城核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_SHOP).unique();
  public static final TerraCityRegionBuilder PALERMO_CORE = region(ModNation.SIRACUSA, "帕勒莫核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_SHOP).unique();
  public static final TerraCityRegionBuilder VOLSINII_CORE = region(ModNation.SIRACUSA, "沃尔西尼核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_FAMILY_THEATRE).unique();
  public static final TerraCityRegionBuilder NUOVA_VOLSINII_CORE = region(ModNation.SIRACUSA, "新沃尔西尼核心区", RegionLayoutType.CONCENTRIC, ModStructure.SIRACUSA_SHOP).unique();
  public static final TerraCityRegionBuilder BAIZAO_CORE = region(ModNation.YAN, "百灶核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder LUNGMEN_CORE = region(ModNation.YAN, "龙门核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder JIANGQI_CORE = region(ModNation.YAN, "姜齐城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder HSI_CORE = region(ModNation.YAN, "夕城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder OCHRE_CORE = region(ModNation.YAN, "黄城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder SPRING_CITY_CORE = region(ModNation.YAN, "春都核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder FLORIA_COUNTY_CORE = region(ModNation.YAN, "花郡核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder KOU_WU_CORE = region(ModNation.YAN, "勾吴城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder DANYAN_CORE = region(ModNation.YAN, "丹燕城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder YUMEN_CORE = region(ModNation.YAN, "玉门核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_YUMEN_BEACON).unique();
  public static final TerraCityRegionBuilder DAHUANG_CORE = region(ModNation.YAN, "大荒城核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder MANGSHAN_TOWN_CORE = region(ModNation.YAN, "邙山镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder PO_SHAN_CORE = region(ModNation.YAN, "婆山镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHOP).unique();
  public static final TerraCityRegionBuilder PERDONILLA_PERDONI_CORE = region(ModNation.IBERIA, "佩尔多尼朵拉（佩尔多尼）核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SHOP).unique();
  public static final TerraCityRegionBuilder SAL_VIENTO_CORE = region(ModNation.IBERIA, "盐风城核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SALTVIND_CHAPEL).unique();
  public static final TerraCityRegionBuilder ROCAMAREA_CORE = region(ModNation.IBERIA, "潮石镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SHOP).unique();
  public static final TerraCityRegionBuilder GRAN_FARO_CORE = region(ModNation.IBERIA, "格兰法洛核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_EYE_LIGHTHOUSE).unique();
  public static final TerraCityRegionBuilder BASTION_DE_CANTICOS_CORE = region(ModNation.IBERIA, "颂圣棱堡核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SHOP).unique();
  public static final TerraCityRegionBuilder PORT_CITY_CORE = region(ModNation.IBERIA, "港都核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SHOP).unique();
  public static final TerraCityRegionBuilder AARON_CORE = region(ModNation.IBERIA, "雅隆镇核心区", RegionLayoutType.CONCENTRIC, ModStructure.IBERIA_SHOP).unique();
  public static final TerraCityRegionBuilder CENTRAL_MINING_AREA = region(ModNation.URSUS, "中心矿区", RegionLayoutType.CONCENTRIC, ModStructure.URSUS_NORTHERN_MINE_TOWER)
      .unique()
      .plotSizes(new PlotSize(40, 32), new PlotSize(32, 32), new PlotSize(32, 24));
  public static final TerraCityRegionBuilder SATELLITE_CITY_MINING_AREA = region(ModNation.URSUS, "卫星城矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder OLONETS_MINING_AREA = region(ModNation.URSUS, "奥洛涅茨矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder VOLGOGRAD_MINING_AREA = region(ModNation.URSUS, "沃尔格勒矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder URAL_MINING_AREA = region(ModNation.URSUS, "乌拉尔矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder KRAS_MINING_AREA = region(ModNation.URSUS, "克拉斯矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder ONEGA_MINING_AREA = region(ModNation.URSUS, "奥涅加矿区", RegionLayoutType.GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder SHANGSHU_CORE = region(ModNation.YAN, "尚蜀核心区", RegionLayoutType.CONCENTRIC, ModStructure.YAN_SHANGSHU_PAVILION).unique();
  public static final TerraCityRegionBuilder XINLUAN = region(ModNation.YAN, "新峦区", RegionLayoutType.GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder LIUYUN = region(ModNation.YAN, "流云区", RegionLayoutType.GRID, ModStructure.YAN_SHOP);

  public static final TerraCityRegionBuilder MILLIARIUM_SUBURB = region(ModNation.AEGIR, "弥利亚留姆郊区", RegionLayoutType.RADIAL_GRID, ModStructure.AEGIR_SHOP);
  public static final TerraCityRegionBuilder DOSSOLES_SUBURB = region(ModNation.BOLIVAR, "多索雷斯郊区", RegionLayoutType.RADIAL_GRID, ModStructure.BOLIVAR_SHOP);
  public static final TerraCityRegionBuilder LA_UNIDAD_SUBURB = region(ModNation.BOLIVAR, "拉乌尼达郊区", RegionLayoutType.RADIAL_GRID, ModStructure.BOLIVAR_SHOP);
  public static final TerraCityRegionBuilder TECOMA_SUBURB = region(ModNation.BOLIVAR, "特科马郊区", RegionLayoutType.RADIAL_GRID, ModStructure.BOLIVAR_SHOP);
  public static final TerraCityRegionBuilder SOUTHERN_COURT_IMPERIAL_SHRINE_SUBURB = region(ModNation.HIGASHI, "南院行在御机大社郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder NORTHERN_COURT_SOKOGAWA_CASTLE_SUBURB = region(ModNation.HIGASHI, "北院镇守锁川城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder HIMEJI_CASTLE_SUBURB = region(ModNation.HIGASHI, "姬户城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder USHIROKAWA_CASTLE_SUBURB = region(ModNation.HIGASHI, "后川城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder NITO_JO_SUBURB = region(ModNation.HIGASHI, "二户城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder SHIN_AKI_CITY_SUBURB = region(ModNation.HIGASHI, "新安芸市郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder ROKA_VILLAGE_SUBURB = region(ModNation.HIGASHI, "露华村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.HIGASHI_SHOP);
  public static final TerraCityRegionBuilder NEW_ZERUERTZA_SUBURB = region(ModNation.DURIN, "际崖城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.DURIN_SHOP);
  public static final TerraCityRegionBuilder ORTZIMUGA_SUBURB = region(ModNation.DURIN, "天际城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.DURIN_SHOP);
  public static final TerraCityRegionBuilder TRIMOUNTS_SUBURB = region(ModNation.COLUMBIA, "特里蒙郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder TKARONTO_SUBURB = region(ModNation.COLUMBIA, "提卡伦多郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder BUNKERHILL_CITY_SUBURB = region(ModNation.COLUMBIA, "堡垒山城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder NULAITEBURGH_SUBURB = region(ModNation.COLUMBIA, "纽莱堡市郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder SAINT_SOPHIA_CITY_SUBURB = region(ModNation.COLUMBIA, "圣苏菲城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder IRONFORGE_SUBURB = region(ModNation.COLUMBIA, "铸铁城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder NORTHVILLE_SUBURB = region(ModNation.COLUMBIA, "北诺斯维尔郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder NEW_MANFIST_SUBURB = region(ModNation.COLUMBIA, "新曼法斯特郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder BLUECARD_SUBURB = region(ModNation.COLUMBIA, "蓝卡坞郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder GIVOGIA_SUBURB = region(ModNation.COLUMBIA, "吉沃吉亚郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder STEELHAM_SUBURB = region(ModNation.COLUMBIA, "铁驮镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder DAVISTOWN_SUBURB = region(ModNation.COLUMBIA, "达维镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder MAX_DC_SUBURB = region(ModNation.COLUMBIA, "麦克斯特区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder PROPELLER_PARADISE_SUBURB = region(ModNation.COLUMBIA, "螺旋桨天堂郊区", RegionLayoutType.RADIAL_GRID, ModStructure.COLUMBIA_SHOP);
  public static final TerraCityRegionBuilder GRAND_KNIGHT_TERRITORY_SUBURB = region(ModNation.KAZIMIERZ, "卡瓦莱利亚基（大骑士领）郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder DZWONEK_SUBURB = region(ModNation.KAZIMIERZ, "茨沃涅克郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder OGNISKO_SUBURB = region(ModNation.KAZIMIERZ, "奥格尼斯科郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder DEWVILLE_SUBURB = region(ModNation.KAZIMIERZ, "滴水村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder ROCKVILLE_SUBURB = region(ModNation.KAZIMIERZ, "垒石村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder STRUMYKOWO_SUBURB = region(ModNation.KAZIMIERZ, "沥泉村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZIMIERZ_SHOP);
  public static final TerraCityRegionBuilder KAZDEL_SUBURB = region(ModNation.KAZDEL, "卡兹戴尔城（今卡兹戴尔）郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZDEL_SHOP);
  public static final TerraCityRegionBuilder BELLONY_VILLAGE_SUBURB = region(ModNation.KAZDEL, "贝罗尼村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KAZDEL_SHOP);
  public static final TerraCityRegionBuilder PAGUS_STEVONUS_SUBURB = region(ModNation.LATERANO, "司提望区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_AMBROSIUS_SUBURB = region(ModNation.LATERANO, "安布罗修区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_FABER_SUBURB = region(ModNation.LATERANO, "法柏尔区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_GRIFFIN_SUBURB = region(ModNation.LATERANO, "格芬区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_MICHAELION_SUBURB = region(ModNation.LATERANO, "米迦莱昂区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_SAINT_MARCEL_SUBURB = region(ModNation.LATERANO, "圣马尔索区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder PAGUS_ECCLESIA_SUBURB = region(ModNation.LATERANO, "伊卡莱西亚区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LATERANO_SHOP);
  public static final TerraCityRegionBuilder ZWILLINGSTURME_SUBURB = region(ModNation.LEITHANIEN, "崔林特尔梅郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder WOLUMONDE_SUBURB = region(ModNation.LEITHANIEN, "沃伦姆德郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder VYSEHEIM_SUBURB = region(ModNation.LEITHANIEN, "维谢海姆郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder GRINDEN_SUBURB = region(ModNation.LEITHANIEN, "格林登郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder KREIS_HELDENSCHWERT_SUBURB = region(ModNation.LEITHANIEN, "海登施威尔大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder WASSERLAND_SUBURB = region(ModNation.LEITHANIEN, "瓦瑟领大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder KREIS_FURTGANG_SUBURB = region(ModNation.LEITHANIEN, "福特冈大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder KREIS_OSTENHEIM_SUBURB = region(ModNation.LEITHANIEN, "奥施登海姆大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder KREIS_KEPLANI_SUBURB = region(ModNation.LEITHANIEN, "凯普拉尼大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder KREIS_EINGEWEIDE_SUBURB = region(ModNation.LEITHANIEN, "恩瓦德大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder LUPUKARN_SUBURB = region(ModNation.LEITHANIEN, "鲁珀坎大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder STURMLAND_FELS_SUBURB = region(ModNation.LEITHANIEN, "施彤领大区（费尔斯）郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder ERDENHERRE_SUBURB = region(ModNation.LEITHANIEN, "厄登赫尔大区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder URTICA_GRAFSCHAFT_SUBURB = region(ModNation.LEITHANIEN, "乌提卡领郊区", RegionLayoutType.RADIAL_GRID, ModStructure.LEITHANIEN_SHOP);
  public static final TerraCityRegionBuilder ULTIMATE_IRON_HOLD_SUBURB = region(ModNation.RIM_BILLITON, "终极大铁屯郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder IRON_CARROT_CITY_SUBURB = region(ModNation.RIM_BILLITON, "钢铁萝卜城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder IRON_FIST_CITY_SUBURB = region(ModNation.RIM_BILLITON, "铁腕城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder GREAT_SPRING_TOWN_SUBURB = region(ModNation.RIM_BILLITON, "大涌泉镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder ARTICHOKE_VILLAGE_SUBURB = region(ModNation.RIM_BILLITON, "洋蓟村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder TURNIP_TOWN_SUBURB = region(ModNation.RIM_BILLITON, "芜菁镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder SUN_VALLEY_SUBURB = region(ModNation.RIM_BILLITON, "太阳谷郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder RED_SAND_TOWN_SUBURB = region(ModNation.RIM_BILLITON, "红砂镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder RUSTDREG_TOWN_SUBURB = region(ModNation.RIM_BILLITON, "锈渣子镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder GREENMEADOW_SHIRE_SUBURB = region(ModNation.RIM_BILLITON, "格林梅多自治州郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder BIG_PILLAR_SHIRE_SUBURB = region(ModNation.RIM_BILLITON, "比格皮勒自治州郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder DOUBLE_HELMET_MINE_SUBURB = region(ModNation.RIM_BILLITON, "双倍黑尔梅特矿区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder HIGHWAY_ZERO_SUBURB = region(ModNation.RIM_BILLITON, "零号公路郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder SOUTHERN_REACH_SUBURB = region(ModNation.RIM_BILLITON, "南境郊区", RegionLayoutType.RADIAL_GRID, ModStructure.RIM_BILLITON_SHOP);
  public static final TerraCityRegionBuilder KORINTHIA_SUBURB = region(ModNation.MINOS, "科林尼亚郊区", RegionLayoutType.RADIAL_GRID, ModStructure.MINOS_SHOP);
  public static final TerraCityRegionBuilder ATHENIUS_SUBURB = region(ModNation.MINOS, "雅赛努斯郊区", RegionLayoutType.RADIAL_GRID, ModStructure.MINOS_SHOP);
  public static final TerraCityRegionBuilder LACHEDAMON_SUBURB = region(ModNation.MINOS, "拉刻代蒙郊区", RegionLayoutType.RADIAL_GRID, ModStructure.MINOS_SHOP);
  public static final TerraCityRegionBuilder AKROTIRI_VILLAGE_SUBURB = region(ModNation.MINOS, "阿克罗蒂村郊区", RegionLayoutType.RADIAL_GRID, ModStructure.MINOS_SHOP);
  public static final TerraCityRegionBuilder AEGEAN_SUBURB = region(ModNation.MINOS, "爱琴郊区", RegionLayoutType.RADIAL_GRID, ModStructure.MINOS_SHOP);
  public static final TerraCityRegionBuilder LONG_SPRING_TOWN_SUBURB = region(ModNation.SARGON, "长泉镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder PHECON_SUBURB = region(ModNation.SARGON, "费坤城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder ACAHUALLA_SUBURB = region(ModNation.SARGON, "阿卡胡拉郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder IBUT_REGION_SUBURB = region(ModNation.SARGON, "伊巴特地区郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder WEST_VOUIVRE_SUBURB = region(ModNation.SARGON, "瓦伊凡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder MENAT_HAMAIT_SUBURB = region(ModNation.SARGON, "米纳特哈玛仪郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SARGON_SHOP);
  public static final TerraCityRegionBuilder SIESTA_SUBURB = region(ModNation.SIESTA, "汐斯塔郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIESTA_SHOP);
  public static final TerraCityRegionBuilder CAPPAT_SUBURB = region(ModNation.SAMI, "察帕特郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SAMI_SHOP);
  public static final TerraCityRegionBuilder FIRST_LAND_SUBURB = region(ModNation.SAMI, "原初之地郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SAMI_SHOP);
  public static final TerraCityRegionBuilder LONDINIUM_SUBURB = region(ModNation.VICTORIA, "伦蒂尼姆郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder REDRIDGE_SUBURB = region(ModNation.VICTORIA, "红脊镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder CHETLEIGH_SUBURB = region(ModNation.VICTORIA, "切特雷镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder BRENTWOOD_SUBURB = region(ModNation.VICTORIA, "布伦特伍德镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder GIBSONHAM_SUBURB = region(ModNation.VICTORIA, "吉布森镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder GREEWICH_SUBURB = region(ModNation.VICTORIA, "格瑞威治郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder LYNNCARDINE_SUBURB = region(ModNation.VICTORIA, "丽茵卡登郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder CALADON_SUBURB = region(ModNation.VICTORIA, "卡拉顿郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder BOSCHENDAL_SUBURB = region(ModNation.VICTORIA, "博森德尔郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_TORON_SUBURB = region(ModNation.VICTORIA, "多伦郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_HILLOCK_SUBURB = region(ModNation.VICTORIA, "小丘郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_ASCARAT_SUBURB = region(ModNation.VICTORIA, "阿斯卡拉郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_LYNTON_SUBURB = region(ModNation.VICTORIA, "林顿郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_PENINSULA_SUBURB = region(ModNation.VICTORIA, "半岛郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder SYKES_SUBURB = region(ModNation.VICTORIA, "塞克郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder CASTSHIRE_SUBURB = region(ModNation.VICTORIA, "开夏郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder COUNTY_OAK_GROVE_SUBURB = region(ModNation.VICTORIA, "橡林郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder TRENT_SUBURB = region(ModNation.VICTORIA, "特伦特郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.VICTORIA_SHOP);
  public static final TerraCityRegionBuilder DEITY_GRYPHERBURG_SUBURB = region(ModNation.URSUS, "圣骏堡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder ZELGRAD_SUBURB = region(ModNation.URSUS, "泽尔格勒（卫星城）郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder CHERNOBOG_SUBURB = region(ModNation.URSUS, "切尔诺伯格郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder TULISKAYA_SUBURB = region(ModNation.URSUS, "图利斯卡亚郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder NOVO_PETROVSK_SUBURB = region(ModNation.URSUS, "新彼得罗夫斯克郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder TAMANGRAD_SUBURB = region(ModNation.URSUS, "塔曼格勒德郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder VYATNO_SUBURB = region(ModNation.URSUS, "维亚特诺郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder ZAMOLESK_SUBURB = region(ModNation.URSUS, "扎莫列斯郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder PETRODANOR_SUBURB = region(ModNation.URSUS, "彼得达诺尔郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder BREZHENOY_SUBURB = region(ModNation.URSUS, "布列洁诺伊郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder VEROBINSK_SUBURB = region(ModNation.URSUS, "维罗比斯科镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder GRIGORY_GOVERNORATE_SUBURB = region(ModNation.URSUS, "格里高利省郊区", RegionLayoutType.RADIAL_GRID, ModStructure.URSUS_SHOP);
  public static final TerraCityRegionBuilder TURICUM_SUBURB = region(ModNation.KJERAG, "图里卡姆郊区", RegionLayoutType.RADIAL_GRID, ModStructure.KJERAG_SHOP);
  public static final TerraCityRegionBuilder MONTELUPE_SUBURB = region(ModNation.SIRACUSA, "蒙特卢佩郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder SETTE_COLLI_SUBURB = region(ModNation.SIRACUSA, "七丘城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder WHITE_CITY_SUBURB = region(ModNation.SIRACUSA, "怀特城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder LOCOMOTIVA_CITY_SUBURB = region(ModNation.SIRACUSA, "拉克玛蒂瓦城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder PALERMO_SUBURB = region(ModNation.SIRACUSA, "帕勒莫郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder VOLSINII_SUBURB = region(ModNation.SIRACUSA, "沃尔西尼郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder NUOVA_VOLSINII_SUBURB = region(ModNation.SIRACUSA, "新沃尔西尼郊区", RegionLayoutType.RADIAL_GRID, ModStructure.SIRACUSA_SHOP);
  public static final TerraCityRegionBuilder BAIZAO_SUBURB = region(ModNation.YAN, "百灶郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder LUNGMEN_SUBURB = region(ModNation.YAN, "龙门郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder JIANGQI_SUBURB = region(ModNation.YAN, "姜齐城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder HSI_SUBURB = region(ModNation.YAN, "夕城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder OCHRE_SUBURB = region(ModNation.YAN, "黄城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder SPRING_CITY_SUBURB = region(ModNation.YAN, "春都郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder FLORIA_COUNTY_SUBURB = region(ModNation.YAN, "花郡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder KOU_WU_SUBURB = region(ModNation.YAN, "勾吴城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder DANYAN_SUBURB = region(ModNation.YAN, "丹燕城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder SHANGSHU_SUBURB = region(ModNation.YAN, "尚蜀郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder YUMEN_SUBURB = region(ModNation.YAN, "玉门郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder DAHUANG_SUBURB = region(ModNation.YAN, "大荒城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder MANGSHAN_TOWN_SUBURB = region(ModNation.YAN, "邙山镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder PO_SHAN_SUBURB = region(ModNation.YAN, "婆山镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.YAN_SHOP);
  public static final TerraCityRegionBuilder PERDONILLA_PERDONI_SUBURB = region(ModNation.IBERIA, "佩尔多尼朵拉（佩尔多尼）郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder SAL_VIENTO_SUBURB = region(ModNation.IBERIA, "盐风城郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder ROCAMAREA_SUBURB = region(ModNation.IBERIA, "潮石镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder GRAN_FARO_SUBURB = region(ModNation.IBERIA, "格兰法洛郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder BASTION_DE_CANTICOS_SUBURB = region(ModNation.IBERIA, "颂圣棱堡郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder PORT_CITY_SUBURB = region(ModNation.IBERIA, "港都郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);
  public static final TerraCityRegionBuilder AARON_SUBURB = region(ModNation.IBERIA, "雅隆镇郊区", RegionLayoutType.RADIAL_GRID, ModStructure.IBERIA_SHOP);

  public static final List<TerraCityRegionBuilder> REGIONS = List.copyOf(Zinecraft.CITY_REGIONS.entries());

  static {
    Zinecraft.TRANSLATIONS.add(
        "journeymap.zinecraft.terra_regions", "泰拉城区", "Terra City Regions"
    );
  }

  private ModCityRegion() {
  }

  private static TerraCityRegionBuilder region(
      NationBuilder nation,
      String zhCn,
      RegionLayoutType regionLayoutType,
      JigsawBuilder... buildings
  ) {
    int spatialWeight = zhCn.endsWith("核心区") ? 100 : zhCn.endsWith("郊区") ? 30 : 50;
    TerraCityRegionBuilder region = Zinecraft.CITY_REGIONS.region(nation, zhCn)
        .weight(spatialWeight)
        .regionLayout(regionLayoutType);
    JigsawBuilder nationalShop = ModStructure.shopFor(nation.id());
    region.building(nationalShop, 1, false);
    region.building(ModStructure.mediumShopFor(nationalShop), 2, false);
    for (JigsawBuilder building : buildings) {
      if (building != nationalShop) region.building(building, 1, true);
    }
    if (zhCn.endsWith("核心区")) {
      region.plotSizes(
          new PlotSize(40, 32),
          new PlotSize(32, 32),
          new PlotSize(32, 24)
      );
    }
    return region.build();
  }

  public static void bootstrap() {
  }
}
