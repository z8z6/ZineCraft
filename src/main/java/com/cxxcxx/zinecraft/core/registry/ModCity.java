package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

import java.util.List;

/**
 * PRTS 城市目录在泰拉固定地图上的游戏化布局。
 *
 * <p>地名与国家归属属于资料事实；坐标、范围和边界属于 Zinecraft 原创玩法布局。</p>
 */
public final class ModCity {
  public static final TerraCityBuilder MILLIARIUM = city(0.000, 0.000, 248, "弥利亚留姆", ModCityRegion.MILLIARIUM_CORE, ModCityRegion.MILLIARIUM_SUBURB);
  public static final TerraCityBuilder DOSSOLES = city(0.000, 0.000, 205, "多索雷斯", ModCityRegion.DOSSOLES_CORE, ModCityRegion.DOSSOLES_SUBURB);
  public static final TerraCityBuilder LA_UNIDAD = city(-0.381, -0.178, 205, "拉乌尼达", ModCityRegion.LA_UNIDAD_CORE, ModCityRegion.LA_UNIDAD_SUBURB);
  public static final TerraCityBuilder TECOMA = city(0.469, -0.148, 205, "特科马", ModCityRegion.TECOMA_CORE, ModCityRegion.TECOMA_SUBURB);
  public static final TerraCityBuilder SOUTHERN_COURT_IMPERIAL_SHRINE = city(0.000, 0.000, 315, "南院行在御机大社", ModCityRegion.SOUTHERN_COURT_IMPERIAL_SHRINE_CORE, ModCityRegion.SOUTHERN_COURT_IMPERIAL_SHRINE_SUBURB);
  public static final TerraCityBuilder NORTHERN_COURT_SOKOGAWA_CASTLE = city(0.297, -0.297, 315, "北院镇守锁川城", ModCityRegion.NORTHERN_COURT_SOKOGAWA_CASTLE_CORE, ModCityRegion.NORTHERN_COURT_SOKOGAWA_CASTLE_SUBURB);
  public static final TerraCityBuilder HIMEJI_CASTLE = city(-0.022, 0.491, 315, "姬户城", ModCityRegion.HIMEJI_CASTLE_CORE, ModCityRegion.HIMEJI_CASTLE_SUBURB);
  public static final TerraCityBuilder USHIROKAWA_CASTLE = city(-0.351, -0.418, 315, "后川城", ModCityRegion.USHIROKAWA_CASTLE_CORE, ModCityRegion.USHIROKAWA_CASTLE_SUBURB);
  public static final TerraCityBuilder NITO_JO = city(0.587, 0.078, 315, "二户城", ModCityRegion.NITO_JO_CORE, ModCityRegion.NITO_JO_SUBURB);
  public static final TerraCityBuilder SHIN_AKI_CITY = city(-0.518, 0.362, 315, "新安芸市", ModCityRegion.SHIN_AKI_CITY_CORE, ModCityRegion.SHIN_AKI_CITY_SUBURB);
  public static final TerraCityBuilder ROKA_VILLAGE = city(0.146, -0.654, 315, "露华村", ModCityRegion.ROKA_VILLAGE_CORE, ModCityRegion.ROKA_VILLAGE_SUBURB);
  public static final TerraCityBuilder NEW_ZERUERTZA = city(0.000, 0.000, 330, "际崖城", ModCityRegion.NEW_ZERUERTZA_CORE, ModCityRegion.NEW_ZERUERTZA_SUBURB);
  public static final TerraCityBuilder ORTZIMUGA = city(0.364, -0.210, 330, "天际城", ModCityRegion.ORTZIMUGA_CORE, ModCityRegion.ORTZIMUGA_SUBURB);
  public static final TerraCityBuilder TRIMOUNTS = city(0.000, 0.000, 190, "特里蒙", ModCityRegion.TRIMOUNTS_CORE, ModCityRegion.TRIMOUNTS_SUBURB);
  public static final TerraCityBuilder TKARONTO = city(-0.414, -0.073, 190, "提卡伦多", ModCityRegion.TKARONTO_CORE, ModCityRegion.TKARONTO_SUBURB);
  public static final TerraCityBuilder BUNKERHILL_CITY = city(0.414, -0.264, 190, "堡垒山城", ModCityRegion.BUNKERHILL_CITY_CORE, ModCityRegion.BUNKERHILL_CITY_SUBURB);
  public static final TerraCityBuilder NULAITEBURGH = city(-0.142, 0.527, 190, "纽莱堡市", ModCityRegion.NULAITEBURGH_CORE, ModCityRegion.NULAITEBURGH_SUBURB);
  public static final TerraCityBuilder SAINT_SOPHIA_CITY = city(-0.274, -0.526, 190, "圣苏菲城", ModCityRegion.SAINT_SOPHIA_CITY_CORE, ModCityRegion.SAINT_SOPHIA_CITY_SUBURB);
  public static final TerraCityBuilder IRONFORGE = city(0.594, 0.217, 190, "铸铁城", ModCityRegion.IRONFORGE_CORE, ModCityRegion.IRONFORGE_SUBURB);
  public static final TerraCityBuilder NORTHVILLE = city(-0.618, 0.256, 190, "北诺斯维尔", ModCityRegion.NORTHVILLE_CORE, ModCityRegion.NORTHVILLE_SUBURB);
  public static final TerraCityBuilder NEW_MANFIST = city(0.298, -0.637, 190, "新曼法斯特", ModCityRegion.NEW_MANFIST_CORE, ModCityRegion.NEW_MANFIST_SUBURB);
  public static final TerraCityBuilder BLUECARD = city(0.221, 0.701, 190, "蓝卡坞", ModCityRegion.BLUECARD_CORE, ModCityRegion.BLUECARD_SUBURB);
  public static final TerraCityBuilder GIVOGIA = city(-0.662, -0.383, 190, "吉沃吉亚", ModCityRegion.GIVOGIA_CORE, ModCityRegion.GIVOGIA_SUBURB);
  public static final TerraCityBuilder STEELHAM = city(0.774, -0.170, 190, "铁驮镇", ModCityRegion.STEELHAM_CORE, ModCityRegion.STEELHAM_SUBURB);
  public static final TerraCityBuilder DAVISTOWN = city(-0.470, 0.670, 190, "达维镇", ModCityRegion.DAVISTOWN_CORE, ModCityRegion.DAVISTOWN_SUBURB);
  public static final TerraCityBuilder MAX_DC = city(-0.109, -0.837, 190, "麦克斯特区", ModCityRegion.MAX_DC_CORE, ModCityRegion.MAX_DC_SUBURB);
  public static final TerraCityBuilder PROPELLER_PARADISE = city(0.664, 0.559, 190, "螺旋桨天堂", ModCityRegion.PROPELLER_PARADISE_CORE, ModCityRegion.PROPELLER_PARADISE_SUBURB);
  public static final TerraCityBuilder GRAND_KNIGHT_TERRITORY = city(0.000, 0.000, 250, "卡瓦莱利亚基（大骑士领）", ModCityRegion.GRAND_KNIGHT_TERRITORY_CORE, ModCityRegion.GRAND_KNIGHT_TERRITORY_SUBURB);
  public static final TerraCityBuilder DZWONEK = city(-0.144, -0.395, 250, "茨沃涅克", ModCityRegion.DZWONEK_CORE, ModCityRegion.DZWONEK_SUBURB);
  public static final TerraCityBuilder OGNISKO = city(0.436, 0.227, 250, "奥格尼斯科", ModCityRegion.OGNISKO_CORE, ModCityRegion.OGNISKO_SUBURB);
  public static final TerraCityBuilder DEWVILLE = city(-0.527, 0.142, 250, "滴水村", ModCityRegion.DEWVILLE_CORE, ModCityRegion.DEWVILLE_SUBURB);
  public static final TerraCityBuilder ROCKVILLE = city(0.318, -0.499, 250, "垒石村", ModCityRegion.ROCKVILLE_CORE, ModCityRegion.ROCKVILLE_SUBURB);
  public static final TerraCityBuilder STRUMYKOWO = city(0.110, 0.623, 250, "沥泉村", ModCityRegion.STRUMYKOWO_CORE, ModCityRegion.STRUMYKOWO_SUBURB);
  public static final TerraCityBuilder KAZDEL = city(0.000, 0.000, 300, "卡兹戴尔城（今卡兹戴尔）", ModCityRegion.KAZDEL_CORE, ModCityRegion.KAZDEL_SUBURB);
  public static final TerraCityBuilder BELLONY_VILLAGE = city(0.210, -0.364, 300, "贝罗尼村", ModCityRegion.BELLONY_VILLAGE_CORE, ModCityRegion.BELLONY_VILLAGE_SUBURB);
  public static final TerraCityBuilder PAGUS_STEVONUS = city(0.000, 0.000, 25, "司提望区", ModCityRegion.PAGUS_STEVONUS_CORE, ModCityRegion.PAGUS_STEVONUS_SUBURB);
  public static final TerraCityBuilder PAGUS_AMBROSIUS = city(0.381, 0.178, 25, "安布罗修区", ModCityRegion.PAGUS_AMBROSIUS_CORE, ModCityRegion.PAGUS_AMBROSIUS_SUBURB);
  public static final TerraCityBuilder PAGUS_FABER = city(-0.469, 0.148, 25, "法柏尔区", ModCityRegion.PAGUS_FABER_CORE, ModCityRegion.PAGUS_FABER_SUBURB);
  public static final TerraCityBuilder PAGUS_GRIFFIN = city(0.274, -0.473, 25, "格芬区", ModCityRegion.PAGUS_GRIFFIN_CORE, ModCityRegion.PAGUS_GRIFFIN_SUBURB);
  public static final TerraCityBuilder PAGUS_MICHAELION = city(0.128, 0.578, 25, "米迦莱昂区", ModCityRegion.PAGUS_MICHAELION_CORE, ModCityRegion.PAGUS_MICHAELION_SUBURB);
  public static final TerraCityBuilder PAGUS_SAINT_MARCEL = city(-0.518, -0.363, 25, "圣马尔索区", ModCityRegion.PAGUS_SAINT_MARCEL_CORE, ModCityRegion.PAGUS_SAINT_MARCEL_SUBURB);
  public static final TerraCityBuilder PAGUS_ECCLESIA = city(0.664, -0.087, 25, "伊卡莱西亚区", ModCityRegion.PAGUS_ECCLESIA_CORE, ModCityRegion.PAGUS_ECCLESIA_SUBURB);
  public static final TerraCityBuilder ZWILLINGSTURME = city(0.000, 0.000, 270, "崔林特尔梅", ModCityRegion.ZWILLINGSTURME_CORE, ModCityRegion.ZWILLINGSTURME_SUBURB);
  public static final TerraCityBuilder WOLUMONDE = city(0.000, -0.420, 270, "沃伦姆德", ModCityRegion.WOLUMONDE_CORE, ModCityRegion.WOLUMONDE_SUBURB);
  public static final TerraCityBuilder VYSEHEIM = city(0.332, 0.362, 270, "维谢海姆", ModCityRegion.VYSEHEIM_CORE, ModCityRegion.VYSEHEIM_SUBURB);
  public static final TerraCityBuilder GRINDEN = city(-0.544, -0.048, 270, "格林登", ModCityRegion.GRINDEN_CORE, ModCityRegion.GRINDEN_SUBURB);
  public static final TerraCityBuilder KREIS_HELDENSCHWERT = city(0.470, -0.360, 270, "海登施威尔大区", ModCityRegion.KREIS_HELDENSCHWERT_CORE, ModCityRegion.KREIS_HELDENSCHWERT_SUBURB);
  public static final TerraCityBuilder WASSERLAND = city(-0.110, 0.623, 270, "瓦瑟领大区", ModCityRegion.WASSERLAND_CORE, ModCityRegion.WASSERLAND_SUBURB);
  public static final TerraCityBuilder KREIS_FURTGANG = city(-0.359, -0.565, 270, "福特冈大区", ModCityRegion.KREIS_FURTGANG_CORE, ModCityRegion.KREIS_FURTGANG_SUBURB);
  public static final TerraCityBuilder KREIS_OSTENHEIM = city(0.679, 0.182, 270, "奥施登海姆大区", ModCityRegion.KREIS_OSTENHEIM_CORE, ModCityRegion.KREIS_OSTENHEIM_SUBURB);
  public static final TerraCityBuilder KREIS_KEPLANI = city(-0.652, 0.338, 270, "凯普拉尼大区", ModCityRegion.KREIS_KEPLANI_CORE, ModCityRegion.KREIS_KEPLANI_SUBURB);
  public static final TerraCityBuilder KREIS_EINGEWEIDE = city(0.262, -0.718, 270, "恩瓦德大区", ModCityRegion.KREIS_EINGEWEIDE_CORE, ModCityRegion.KREIS_EINGEWEIDE_SUBURB);
  public static final TerraCityBuilder LUPUKARN = city(0.302, 0.732, 270, "鲁珀坎大区", ModCityRegion.LUPUKARN_CORE, ModCityRegion.LUPUKARN_SUBURB);
  public static final TerraCityBuilder STURMLAND_FELS = city(-0.742, -0.347, 270, "施彤领大区（费尔斯）", ModCityRegion.STURMLAND_FELS_CORE, ModCityRegion.STURMLAND_FELS_SUBURB);
  public static final TerraCityBuilder ERDENHERRE = city(0.806, -0.253, 270, "厄登赫尔大区", ModCityRegion.ERDENHERRE_CORE, ModCityRegion.ERDENHERRE_SUBURB);
  public static final TerraCityBuilder URTICA_GRAFSCHAFT = city(-0.435, 0.751, 270, "乌提卡领", ModCityRegion.URTICA_GRAFSCHAFT_CORE, ModCityRegion.URTICA_GRAFSCHAFT_SUBURB);
  public static final TerraCityBuilder ULTIMATE_IRON_HOLD = city(0.000, 0.000, 12, "终极大铁屯", ModCityRegion.ULTIMATE_IRON_HOLD_CORE, ModCityRegion.ULTIMATE_IRON_HOLD_SUBURB);
  public static final TerraCityBuilder IRON_CARROT_CITY = city(0.411, 0.087, 12, "钢铁萝卜城", ModCityRegion.IRON_CARROT_CITY_CORE, ModCityRegion.IRON_CARROT_CITY_SUBURB);
  public static final TerraCityBuilder IRON_FIST_CITY = city(-0.423, 0.250, 12, "铁腕城", ModCityRegion.IRON_FIST_CITY_CORE, ModCityRegion.IRON_FIST_CITY_SUBURB);
  public static final TerraCityBuilder GREAT_SPRING_TOWN = city(0.160, -0.522, 12, "大涌泉镇", ModCityRegion.GREAT_SPRING_TOWN_CORE, ModCityRegion.GREAT_SPRING_TOWN_SUBURB);
  public static final TerraCityBuilder ARTICHOKE_VILLAGE = city(0.255, 0.534, 12, "洋蓟村", ModCityRegion.ARTICHOKE_VILLAGE_CORE, ModCityRegion.ARTICHOKE_VILLAGE_SUBURB);
  public static final TerraCityBuilder TURNIP_TOWN = city(-0.586, -0.238, 12, "芜菁镇", ModCityRegion.TURNIP_TOWN_CORE, ModCityRegion.TURNIP_TOWN_SUBURB);
  public static final TerraCityBuilder SUN_VALLEY = city(0.627, -0.234, 12, "太阳谷", ModCityRegion.SUN_VALLEY_CORE, ModCityRegion.SUN_VALLEY_SUBURB);
  public static final TerraCityBuilder RED_SAND_TOWN = city(-0.320, 0.626, 12, "红砂镇", ModCityRegion.RED_SAND_TOWN_CORE, ModCityRegion.RED_SAND_TOWN_SUBURB);
  public static final TerraCityBuilder RUSTDREG_TOWN = city(-0.196, -0.708, 12, "锈渣子镇", ModCityRegion.RUSTDREG_TOWN_CORE, ModCityRegion.RUSTDREG_TOWN_SUBURB);
  public static final TerraCityBuilder GREENMEADOW_SHIRE = city(0.648, 0.406, 12, "格林梅多自治州", ModCityRegion.GREENMEADOW_SHIRE_CORE, ModCityRegion.GREENMEADOW_SHIRE_SUBURB);
  public static final TerraCityBuilder BIG_PILLAR_SHIRE = city(-0.779, 0.143, 12, "比格皮勒自治州", ModCityRegion.BIG_PILLAR_SHIRE_CORE, ModCityRegion.BIG_PILLAR_SHIRE_SUBURB);
  public static final TerraCityBuilder DOUBLE_HELMET_MINE = city(0.494, -0.653, 12, "双倍黑尔梅特矿区", ModCityRegion.DOUBLE_HELMET_MINE_CORE, ModCityRegion.DOUBLE_HELMET_MINE_SUBURB);
  public static final TerraCityBuilder HIGHWAY_ZERO = city(0.080, 0.840, 12, "零号公路", ModCityRegion.HIGHWAY_ZERO_CORE, ModCityRegion.HIGHWAY_ZERO_SUBURB);
  public static final TerraCityBuilder SOUTHERN_REACH = city(-0.645, -0.582, 12, "南境", ModCityRegion.SOUTHERN_REACH_CORE, ModCityRegion.SOUTHERN_REACH_SUBURB);
  public static final TerraCityBuilder KORINTHIA = city(0.000, 0.000, 140, "科林尼亚", ModCityRegion.KORINTHIA_CORE, ModCityRegion.KORINTHIA_SUBURB);
  public static final TerraCityBuilder ATHENIUS = city(-0.322, 0.270, 140, "雅赛努斯", ModCityRegion.ATHENIUS_CORE, ModCityRegion.ATHENIUS_SUBURB);
  public static final TerraCityBuilder LACHEDAMON = city(0.064, -0.487, 140, "拉刻代蒙", ModCityRegion.LACHEDAMON_CORE, ModCityRegion.LACHEDAMON_SUBURB);
  public static final TerraCityBuilder AKROTIRI_VILLAGE = city(0.314, 0.447, 140, "阿克罗蒂村", ModCityRegion.AKROTIRI_VILLAGE_CORE, ModCityRegion.AKROTIRI_VILLAGE_SUBURB);
  public static final TerraCityBuilder AEGEAN = city(-0.578, -0.129, 140, "爱琴", ModCityRegion.AEGEAN_CORE, ModCityRegion.AEGEAN_SUBURB);
  public static final TerraCityBuilder LONG_SPRING_TOWN = city(0.000, 0.000, 155, "长泉镇", ModCityRegion.LONG_SPRING_TOWN_CORE, ModCityRegion.LONG_SPRING_TOWN_SUBURB);
  public static final TerraCityBuilder PHECON = city(-0.381, 0.178, 155, "费坤城", ModCityRegion.PHECON_CORE, ModCityRegion.PHECON_SUBURB);
  public static final TerraCityBuilder ACAHUALLA = city(0.188, -0.454, 155, "阿卡胡拉", ModCityRegion.ACAHUALLA_CORE, ModCityRegion.ACAHUALLA_SUBURB);
  public static final TerraCityBuilder IBUT_REGION = city(0.186, 0.514, 155, "伊巴特地区", ModCityRegion.IBUT_REGION_CORE, ModCityRegion.IBUT_REGION_SUBURB);
  public static final TerraCityBuilder WEST_VOUIVRE = city(-0.525, -0.274, 155, "瓦伊凡", ModCityRegion.WEST_VOUIVRE_CORE, ModCityRegion.WEST_VOUIVRE_SUBURB);
  public static final TerraCityBuilder MENAT_HAMAIT = city(0.611, -0.163, 155, "米纳特哈玛仪", ModCityRegion.MENAT_HAMAIT_CORE, ModCityRegion.MENAT_HAMAIT_SUBURB);
  public static final TerraCityBuilder SIESTA = city(0.000, 0.000, 0, "汐斯塔", ModCityRegion.SIESTA_CORE, ModCityRegion.SIESTA_SUBURB);
  public static final TerraCityBuilder CAPPAT = city(0.000, 0.000, 235, "察帕特", ModCityRegion.CAPPAT_CORE, ModCityRegion.CAPPAT_SUBURB);
  public static final TerraCityBuilder FIRST_LAND = city(-0.241, -0.344, 235, "原初之地", ModCityRegion.FIRST_LAND_CORE, ModCityRegion.FIRST_LAND_SUBURB);
  public static final TerraCityBuilder LONDINIUM = city(0.000, 0.000, 105, "伦蒂尼姆", ModCityRegion.LONDINIUM_CORE, ModCityRegion.LONDINIUM_SUBURB);
  public static final TerraCityBuilder REDRIDGE = city(-0.109, 0.406, 105, "红脊镇", ModCityRegion.REDRIDGE_CORE, ModCityRegion.REDRIDGE_SUBURB);
  public static final TerraCityBuilder CHETLEIGH = city(-0.227, -0.436, 105, "切特雷镇", ModCityRegion.CHETLEIGH_CORE, ModCityRegion.CHETLEIGH_SUBURB);
  public static final TerraCityBuilder BRENTWOOD = city(0.513, 0.187, 105, "布伦特伍德镇", ModCityRegion.BRENTWOOD_CORE, ModCityRegion.BRENTWOOD_SUBURB);
  public static final TerraCityBuilder GIBSONHAM = city(-0.547, 0.226, 105, "吉布森镇", ModCityRegion.GIBSONHAM_CORE, ModCityRegion.GIBSONHAM_SUBURB);
  public static final TerraCityBuilder GREEWICH = city(0.268, -0.574, 105, "格瑞威治", ModCityRegion.GREEWICH_CORE, ModCityRegion.GREEWICH_SUBURB);
  public static final TerraCityBuilder LYNNCARDINE = city(0.201, 0.638, 105, "丽茵卡登", ModCityRegion.LYNNCARDINE_CORE, ModCityRegion.LYNNCARDINE_SUBURB);
  public static final TerraCityBuilder CALADON = city(-0.609, -0.352, 105, "卡拉顿", ModCityRegion.CALADON_CORE, ModCityRegion.CALADON_SUBURB);
  public static final TerraCityBuilder BOSCHENDAL = city(0.718, -0.158, 105, "博森德尔", ModCityRegion.BOSCHENDAL_CORE, ModCityRegion.BOSCHENDAL_SUBURB);
  public static final TerraCityBuilder COUNTY_TORON = city(-0.439, 0.626, 105, "多伦郡", ModCityRegion.COUNTY_TORON_CORE, ModCityRegion.COUNTY_TORON_SUBURB);
  public static final TerraCityBuilder COUNTY_HILLOCK = city(-0.102, -0.786, 105, "小丘郡", ModCityRegion.COUNTY_HILLOCK_CORE, ModCityRegion.COUNTY_HILLOCK_SUBURB);
  public static final TerraCityBuilder COUNTY_ASCARAT = city(0.626, 0.527, 105, "阿斯卡拉郡", ModCityRegion.COUNTY_ASCARAT_CORE, ModCityRegion.COUNTY_ASCARAT_SUBURB);
  public static final TerraCityBuilder COUNTY_LYNTON = city(-0.843, 0.036, 105, "林顿郡", ModCityRegion.COUNTY_LYNTON_CORE, ModCityRegion.COUNTY_LYNTON_SUBURB);
  public static final TerraCityBuilder COUNTY_PENINSULA = city(0.615, -0.613, 105, "半岛郡", ModCityRegion.COUNTY_PENINSULA_CORE, ModCityRegion.COUNTY_PENINSULA_SUBURB);
  public static final TerraCityBuilder SYKES = city(-0.041, 0.891, 105, "塞克郡", ModCityRegion.SYKES_CORE, ModCityRegion.SYKES_SUBURB);
  public static final TerraCityBuilder CASTSHIRE = city(-0.586, -0.702, 105, "开夏郡", ModCityRegion.CASTSHIRE_CORE, ModCityRegion.CASTSHIRE_SUBURB);
  public static final TerraCityBuilder COUNTY_OAK_GROVE = city(0.928, 0.124, 105, "橡林郡", ModCityRegion.COUNTY_OAK_GROVE_CORE, ModCityRegion.COUNTY_OAK_GROVE_SUBURB);
  public static final TerraCityBuilder TRENT = city(-0.786, 0.547, 105, "特伦特郡", ModCityRegion.TRENT_CORE, ModCityRegion.TRENT_SUBURB);
  public static final TerraCityBuilder DEITY_GRYPHERBURG = city(0.000, 0.000, 300, "圣骏堡", ModCityRegion.DEITY_GRYPHERBURG_CORE, ModCityRegion.DEITY_GRYPHERBURG_SUBURB);
  public static final TerraCityBuilder ZELGRAD = city(0.210, -0.364, 300, "泽尔格勒（卫星城）", ModCityRegion.ZELGRAD_CORE, ModCityRegion.ZELGRAD_SUBURB);
  public static final TerraCityBuilder CHERNOBOG = city(0.106, 0.480, 300, "切尔诺伯格", ModCityRegion.CHERNOBOG_CORE, ModCityRegion.CHERNOBOG_SUBURB);
  public static final TerraCityBuilder TULISKAYA = city(-0.447, -0.314, 300, "图利斯卡亚", ModCityRegion.TULISKAYA_CORE, ModCityRegion.TULISKAYA_SUBURB);
  public static final TerraCityBuilder NOVO_PETROVSK = city(0.587, -0.078, 300, "新彼得罗夫斯克", ModCityRegion.NOVO_PETROVSK_CORE, ModCityRegion.NOVO_PETROVSK_SUBURB);
  public static final TerraCityBuilder TAMANGRAD = city(-0.407, 0.485, 300, "塔曼格勒德", ModCityRegion.TAMANGRAD_CORE, ModCityRegion.TAMANGRAD_SUBURB);
  public static final TerraCityBuilder VYATNO = city(-0.029, -0.669, 300, "维亚特诺", ModCityRegion.VYATNO_CORE, ModCityRegion.VYATNO_SUBURB);
  public static final TerraCityBuilder ZAMOLESK = city(0.497, 0.498, 300, "扎莫列斯", ModCityRegion.ZAMOLESK_CORE, ModCityRegion.ZAMOLESK_SUBURB);
  public static final TerraCityBuilder PETRODANOR = city(-0.734, -0.033, 300, "彼得达诺尔", ModCityRegion.PETRODANOR_CORE, ModCityRegion.PETRODANOR_SUBURB);
  public static final TerraCityBuilder BREZHENOY = city(0.586, -0.490, 300, "布列洁诺伊",
      ModCityRegion.CENTRAL_MINING_AREA, ModCityRegion.SATELLITE_CITY_MINING_AREA,
      ModCityRegion.OLONETS_MINING_AREA, ModCityRegion.VOLGOGRAD_MINING_AREA,
      ModCityRegion.URAL_MINING_AREA, ModCityRegion.KRAS_MINING_AREA, ModCityRegion.ONEGA_MINING_AREA, ModCityRegion.BREZHENOY_SUBURB
  );
  public static final TerraCityBuilder VEROBINSK = city(-0.105, 0.786, 300, "维罗比斯科镇", ModCityRegion.VEROBINSK_CORE, ModCityRegion.VEROBINSK_SUBURB);
  public static final TerraCityBuilder GRIGORY_GOVERNORATE = city(-0.469, -0.671, 300, "格里高利省", ModCityRegion.GRIGORY_GOVERNORATE_CORE, ModCityRegion.GRIGORY_GOVERNORATE_SUBURB);
  public static final TerraCityBuilder TURICUM = city(0.000, 0.000, 225, "图里卡姆", ModCityRegion.TURICUM_CORE, ModCityRegion.TURICUM_SUBURB);
  public static final TerraCityBuilder MONTELUPE = city(0.000, 0.000, 55, "蒙特卢佩", ModCityRegion.MONTELUPE_CORE, ModCityRegion.MONTELUPE_SUBURB);
  public static final TerraCityBuilder SETTE_COLLI = city(0.241, 0.344, 55, "七丘城", ModCityRegion.SETTE_COLLI_CORE, ModCityRegion.SETTE_COLLI_SUBURB);
  public static final TerraCityBuilder WHITE_CITY = city(-0.480, -0.106, 55, "怀特城", ModCityRegion.WHITE_CITY_CORE, ModCityRegion.WHITE_CITY_SUBURB);
  public static final TerraCityBuilder LOCOMOTIVA_CITY = city(0.473, -0.273, 55, "拉克玛蒂瓦城", ModCityRegion.LOCOMOTIVA_CITY_CORE, ModCityRegion.LOCOMOTIVA_CITY_SUBURB);
  public static final TerraCityBuilder PALERMO = city(-0.178, 0.565, 55, "帕勒莫", ModCityRegion.PALERMO_CORE, ModCityRegion.PALERMO_SUBURB);
  public static final TerraCityBuilder VOLSINII = city(-0.267, -0.574, 55, "沃尔西尼", ModCityRegion.VOLSINII_CORE, ModCityRegion.VOLSINII_SUBURB);
  public static final TerraCityBuilder NUOVA_VOLSINII = city(0.618, 0.257, 55, "新沃尔西尼", ModCityRegion.NUOVA_VOLSINII_CORE, ModCityRegion.NUOVA_VOLSINII_SUBURB);
  public static final TerraCityBuilder BAIZAO = city(0.000, 0.000, 345, "百灶", ModCityRegion.BAIZAO_CORE, ModCityRegion.BAIZAO_SUBURB);
  public static final TerraCityBuilder LUNGMEN = city(0.406, -0.109, 345, "龙门", ModCityRegion.LUNGMEN_CORE, ModCityRegion.LUNGMEN_SUBURB);
  public static final TerraCityBuilder JIANGQI = city(-0.264, 0.414, 345, "姜齐城", ModCityRegion.JIANGQI_CORE, ModCityRegion.JIANGQI_SUBURB);
  public static final TerraCityBuilder HSI = city(-0.095, -0.538, 345, "夕城", ModCityRegion.HSI_CORE, ModCityRegion.HSI_SUBURB);
  public static final TerraCityBuilder OCHRE = city(0.470, 0.361, 345, "黄城", ModCityRegion.OCHRE_CORE, ModCityRegion.OCHRE_SUBURB);
  public static final TerraCityBuilder SPRING_CITY = city(-0.630, 0.055, 345, "春都", ModCityRegion.SPRING_CITY_CORE, ModCityRegion.SPRING_CITY_SUBURB);
  public static final TerraCityBuilder FLORIA_COUNTY = city(0.453, -0.494, 345, "花郡", ModCityRegion.FLORIA_COUNTY_CORE, ModCityRegion.FLORIA_COUNTY_SUBURB);
  public static final TerraCityBuilder KOU_WU = city(-0.001, 0.703, 345, "勾吴城", ModCityRegion.KOU_WU_CORE, ModCityRegion.KOU_WU_SUBURB);
  public static final TerraCityBuilder DANYAN = city(-0.496, -0.542, 345, "丹燕城", ModCityRegion.DANYAN_CORE, ModCityRegion.DANYAN_SUBURB);
  public static final TerraCityBuilder SHANGSHU = city(0.762, 0.068, 345, "尚蜀",
      ModCityRegion.SHANGSHU_CORE, ModCityRegion.XINLUAN, ModCityRegion.LIUYUN, ModCityRegion.SHANGSHU_SUBURB
  );
  public static final TerraCityBuilder YUMEN = city(-0.629, 0.482, 345, "玉门", ModCityRegion.YUMEN_CORE, ModCityRegion.YUMEN_SUBURB);
  public static final TerraCityBuilder DAHUANG = city(0.143, -0.806, 345, "大荒城", ModCityRegion.DAHUANG_CORE, ModCityRegion.DAHUANG_SUBURB);
  public static final TerraCityBuilder MANGSHAN_TOWN = city(0.453, 0.713, 345, "邙山镇", ModCityRegion.MANGSHAN_TOWN_CORE, ModCityRegion.MANGSHAN_TOWN_SUBURB);
  public static final TerraCityBuilder PO_SHAN = city(-0.838, -0.226, 345, "婆山镇", ModCityRegion.PO_SHAN_CORE, ModCityRegion.PO_SHAN_SUBURB);
  public static final TerraCityBuilder PERDONILLA_PERDONI = city(0.000, 0.000, 90, "佩尔多尼朵拉（佩尔多尼）", ModCityRegion.PERDONILLA_PERDONI_CORE, ModCityRegion.PERDONILLA_PERDONI_SUBURB);
  public static final TerraCityBuilder SAL_VIENTO = city(0.000, 0.420, 90, "盐风城", ModCityRegion.SAL_VIENTO_CORE, ModCityRegion.SAL_VIENTO_SUBURB);
  public static final TerraCityBuilder ROCAMAREA = city(-0.332, -0.362, 90, "潮石镇", ModCityRegion.ROCAMAREA_CORE, ModCityRegion.ROCAMAREA_SUBURB);
  public static final TerraCityBuilder GRAN_FARO = city(0.544, 0.048, 90, "格兰法洛", ModCityRegion.GRAN_FARO_CORE, ModCityRegion.GRAN_FARO_SUBURB);
  public static final TerraCityBuilder BASTION_DE_CANTICOS = city(-0.470, 0.360, 90, "颂圣棱堡", ModCityRegion.BASTION_DE_CANTICOS_CORE, ModCityRegion.BASTION_DE_CANTICOS_SUBURB);
  public static final TerraCityBuilder PORT_CITY = city(0.110, -0.623, 90, "港都", ModCityRegion.PORT_CITY_CORE, ModCityRegion.PORT_CITY_SUBURB);
  public static final TerraCityBuilder AARON = city(0.359, 0.565, 90, "雅隆镇", ModCityRegion.AARON_CORE, ModCityRegion.AARON_SUBURB);

  public static final List<TerraCityBuilder> CITIES = freezeCities();

  static {
    Zinecraft.TRANSLATIONS.add(
        "journeymap.zinecraft.terra_cities", "泰拉城市与聚落", "Terra Cities and Settlements"
    );
  }

  private ModCity() {
  }

  public static List<TerraCityBuilder> citiesIn(NationBuilder nation) {
    return Zinecraft.CITIES.citiesIn(nation);
  }

  public static TerraCityBuilder city(NationBuilder nation, String zhCn) {
    return Zinecraft.CITIES.require(nation, zhCn);
  }

  private static TerraCityBuilder city(
      double relativeX,
      double relativeZ,
      int rotationDegrees,
      String zhCn,
      TerraCityRegionBuilder... regions
  ) {
    return Zinecraft.CITIES.city(zhCn)
        .position(relativeX, relativeZ)
        .rotation(rotationDegrees)
        .regions(regions)
        .build();
  }

  private static List<TerraCityBuilder> freezeCities() {
    List<TerraCityBuilder> cities = List.copyOf(Zinecraft.CITIES.entries());
    Zinecraft.CITIES.validateOwnership(Zinecraft.CITY_REGIONS, Zinecraft.STRUCTURES);
    if (cities.stream().map(Zinecraft.CITIES::nationOf).distinct().count() != Zinecraft.NATIONS.entries().size()) {
      throw new IllegalStateException("必须为全部泰拉国家设计城市");
    }
    return cities;
  }

  public static void bootstrap() {
  }
}
