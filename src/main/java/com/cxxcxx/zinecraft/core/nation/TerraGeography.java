package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.nation.TerraPlaceType;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.registry.ModDimension;

import java.util.*;

/**
 * PRTS 地名目录在泰拉固定地图上的游戏化布局。
 *
 * <p>地名与国家归属属于资料事实；坐标、范围和边界属于 Zinecraft 原创玩法布局。</p>
 */
public final class TerraGeography {
  private static final double GOLDEN_ANGLE = Math.toRadians(137.50776405);
  private static final List<TerraPlace> MUTABLE_PLACES = new ArrayList<>();
  private static final Set<String> PLACE_IDS = new HashSet<>();
  private static final Map<TerraNation, Integer> URBAN_INDICES = new EnumMap<>(TerraNation.class);
  private static final Map<TerraNation, Integer> REGION_INDICES = new EnumMap<>(TerraNation.class);
  private static final Map<TerraNation, Integer> REGION_COUNTS = Map.ofEntries(
      Map.entry(TerraNation.BOLIVAR, 2),
      Map.entry(TerraNation.COLUMBIA, 5),
      Map.entry(TerraNation.HIGASHI, 1),
      Map.entry(TerraNation.IBERIA, 1),
      Map.entry(TerraNation.KAZIMIERZ, 1),
      Map.entry(TerraNation.KJERAG, 4),
      Map.entry(TerraNation.LATERANO, 7),
      Map.entry(TerraNation.LEITHANIEN, 10),
      Map.entry(TerraNation.MINOS, 4),
      Map.entry(TerraNation.RIM_BILLITON, 7),
      Map.entry(TerraNation.SAMI, 2),
      Map.entry(TerraNation.SARGON, 5),
      Map.entry(TerraNation.URSUS, 8),
      Map.entry(TerraNation.VICTORIA, 14),
      Map.entry(TerraNation.YAN, 13)
  );

  public static final TerraPlace MILLIARIUM = registerPlace(TerraNation.AEGIR, 248, TerraPlaceType.CITY, "弥利亚留姆");
  public static final TerraPlace DOSSOLES = registerPlace(TerraNation.BOLIVAR, 205, TerraPlaceType.CITY, "多索雷斯");
  public static final TerraPlace LA_UNIDAD = registerPlace(TerraNation.BOLIVAR, 205, TerraPlaceType.CITY, "拉乌尼达");
  public static final TerraPlace TECOMA = registerPlace(TerraNation.BOLIVAR, 205, TerraPlaceType.CITY, "特科马");
  public static final TerraPlace BLACK_FLOW = registerPlace(TerraNation.BOLIVAR, 205, TerraPlaceType.NATURAL_FEATURE, "黑流树海");
  public static final TerraPlace INLAND_RIVER = registerPlace(TerraNation.BOLIVAR, 205, TerraPlaceType.NATURAL_FEATURE, "内河");
  public static final TerraPlace SOUTHERN_COURT_IMPERIAL_SHRINE = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "南院行在御机大社");
  public static final TerraPlace NORTHERN_COURT_SOKOGAWA_CASTLE = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "北院镇守锁川城");
  public static final TerraPlace HIMEJI_CASTLE = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "姬户城");
  public static final TerraPlace USHIROKAWA_CASTLE = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "后川城");
  public static final TerraPlace NITO_JO = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "二户城");
  public static final TerraPlace SHIN_AKI_CITY = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.CITY, "新安芸市");
  public static final TerraPlace ROKA_VILLAGE = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.SETTLEMENT, "露华村");
  public static final TerraPlace TOKOYAMI_RIFT = registerPlace(TerraNation.HIGASHI, 315, TerraPlaceType.NATURAL_FEATURE, "常暗裂谷");
  public static final TerraPlace NEW_ZERUERTZA = registerPlace(TerraNation.DURIN, 330, TerraPlaceType.CITY, "际崖城");
  public static final TerraPlace ORTZIMUGA = registerPlace(TerraNation.DURIN, 330, TerraPlaceType.CITY, "天际城");
  public static final TerraPlace TRIMOUNTS = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "特里蒙");
  public static final TerraPlace TKARONTO = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "提卡伦多");
  public static final TerraPlace BUNKERHILL_CITY = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "堡垒山城");
  public static final TerraPlace NULAITEBURGH = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "纽莱堡市");
  public static final TerraPlace SAINT_SOPHIA_CITY = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "圣苏菲城");
  public static final TerraPlace IRONFORGE = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "铸铁城");
  public static final TerraPlace NORTHVILLE = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "北诺斯维尔");
  public static final TerraPlace NEW_MANFIST = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "新曼法斯特");
  public static final TerraPlace BLUECARD = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "蓝卡坞");
  public static final TerraPlace GIVOGIA = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.CITY, "吉沃吉亚");
  public static final TerraPlace STEELHAM = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.SETTLEMENT, "铁驮镇");
  public static final TerraPlace DAVISTOWN = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.SETTLEMENT, "达维镇");
  public static final TerraPlace MAX_DC = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.REGION, "麦克斯特区");
  public static final TerraPlace PROPELLER_PARADISE = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.REGION, "螺旋桨天堂");
  public static final TerraPlace GASPAR_WILDLAND = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.NATURAL_FEATURE, "加斯帕荒原");
  public static final TerraPlace BLEACHED_WASTELAND = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.NATURAL_FEATURE, "骸骨荒原");
  public static final TerraPlace LAKE_SULLIGEN = registerPlace(TerraNation.COLUMBIA, 190, TerraPlaceType.NATURAL_FEATURE, "苏里根湖");
  public static final TerraPlace GRAND_KNIGHT_TERRITORY = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.CITY, "卡瓦莱利亚基（大骑士领）");
  public static final TerraPlace DZWONEK = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.CITY, "茨沃涅克");
  public static final TerraPlace OGNISKO = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.CITY, "奥格尼斯科");
  public static final TerraPlace DEWVILLE = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.SETTLEMENT, "滴水村");
  public static final TerraPlace ROCKVILLE = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.SETTLEMENT, "垒石村");
  public static final TerraPlace STRUMYKOWO = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.SETTLEMENT, "沥泉村");
  public static final TerraPlace GOLDEN_PLAINS = registerPlace(TerraNation.KAZIMIERZ, 250, TerraPlaceType.NATURAL_FEATURE, "黄金平原");
  public static final TerraPlace KAZDEL = registerPlace(TerraNation.KAZDEL, 300, TerraPlaceType.CITY, "卡兹戴尔城（今卡兹戴尔）");
  public static final TerraPlace BELLONY_VILLAGE = registerPlace(TerraNation.KAZDEL, 300, TerraPlaceType.SETTLEMENT, "贝罗尼村");
  public static final TerraPlace PAGUS_STEVONUS = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "司提望区");
  public static final TerraPlace PAGUS_AMBROSIUS = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "安布罗修区");
  public static final TerraPlace PAGUS_FABER = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "法柏尔区");
  public static final TerraPlace PAGUS_GRIFFIN = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "格芬区");
  public static final TerraPlace PAGUS_MICHAELION = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "米迦莱昂区");
  public static final TerraPlace PAGUS_SAINT_MARCEL = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "圣马尔索区");
  public static final TerraPlace PAGUS_ECCLESIA = registerPlace(TerraNation.LATERANO, 25, TerraPlaceType.REGION, "伊卡莱西亚区");
  public static final TerraPlace ZWILLINGSTURME = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.CITY, "崔林特尔梅");
  public static final TerraPlace WOLUMONDE = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.CITY, "沃伦姆德");
  public static final TerraPlace VYSEHEIM = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.CITY, "维谢海姆");
  public static final TerraPlace GRINDEN = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.CITY, "格林登");
  public static final TerraPlace KREIS_HELDENSCHWERT = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "海登施威尔大区");
  public static final TerraPlace WASSERLAND = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "瓦瑟领大区");
  public static final TerraPlace KREIS_FURTGANG = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "福特冈大区");
  public static final TerraPlace KREIS_OSTENHEIM = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "奥施登海姆大区");
  public static final TerraPlace KREIS_KEPLANI = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "凯普拉尼大区");
  public static final TerraPlace KREIS_EINGEWEIDE = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "恩瓦德大区");
  public static final TerraPlace LUPUKARN = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "鲁珀坎大区");
  public static final TerraPlace STURMLAND_FELS = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "施彤领大区（费尔斯）");
  public static final TerraPlace ERDENHERRE = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "厄登赫尔大区");
  public static final TerraPlace URTICA_GRAFSCHAFT = registerPlace(TerraNation.LEITHANIEN, 270, TerraPlaceType.REGION, "乌提卡领");
  public static final TerraPlace ULTIMATE_IRON_HOLD = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.CITY, "终极大铁屯");
  public static final TerraPlace IRON_CARROT_CITY = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.CITY, "钢铁萝卜城");
  public static final TerraPlace IRON_FIST_CITY = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.CITY, "铁腕城");
  public static final TerraPlace GREAT_SPRING_TOWN = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "大涌泉镇");
  public static final TerraPlace ARTICHOKE_VILLAGE = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "洋蓟村");
  public static final TerraPlace TURNIP_TOWN = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "芜菁镇");
  public static final TerraPlace SUN_VALLEY = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "太阳谷");
  public static final TerraPlace RED_SAND_TOWN = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "红砂镇");
  public static final TerraPlace RUSTDREG_TOWN = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.SETTLEMENT, "锈渣子镇");
  public static final TerraPlace GREENMEADOW_SHIRE = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.REGION, "格林梅多自治州");
  public static final TerraPlace BIG_PILLAR_SHIRE = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.REGION, "比格皮勒自治州");
  public static final TerraPlace DOUBLE_HELMET_MINE = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.REGION, "双倍黑尔梅特矿区");
  public static final TerraPlace HIGHWAY_ZERO = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.REGION, "零号公路");
  public static final TerraPlace SOUTHERN_REACH = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.REGION, "南境");
  public static final TerraPlace WINDY_BEACH = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.NATURAL_FEATURE, "大风滩");
  public static final TerraPlace GRINNING_VALLEY = registerPlace(TerraNation.RIM_BILLITON, 12, TerraPlaceType.NATURAL_FEATURE, "咧嘴谷");
  public static final TerraPlace KORINTHIA = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.CITY, "科林尼亚");
  public static final TerraPlace ATHENIUS = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.CITY, "雅赛努斯");
  public static final TerraPlace LACHEDAMON = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.CITY, "拉刻代蒙");
  public static final TerraPlace AKROTIRI_VILLAGE = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.SETTLEMENT, "阿克罗蒂村");
  public static final TerraPlace AEGEAN = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.CITY, "爱琴");
  public static final TerraPlace AGNES_RIVER = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.NATURAL_FEATURE, "阿涅斯河");
  public static final TerraPlace MOUNT_HYMNOS = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.NATURAL_FEATURE, "荷谟伊山");
  public static final TerraPlace DELPHI_CANAL = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.NATURAL_FEATURE, "特尔斐运河");
  public static final TerraPlace HELIA_MOUNTAINS = registerPlace(TerraNation.MINOS, 140, TerraPlaceType.NATURAL_FEATURE, "赫里亚山");
  public static final TerraPlace LONG_SPRING_TOWN = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.SETTLEMENT, "长泉镇");
  public static final TerraPlace PHECON = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.CITY, "费坤城");
  public static final TerraPlace ACAHUALLA = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.REGION, "阿卡胡拉");
  public static final TerraPlace IBUT_REGION = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.REGION, "伊巴特地区");
  public static final TerraPlace KERTHUN_VALLEY = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.NATURAL_FEATURE, "凯尔图恩谷地");
  public static final TerraPlace WEST_VOUIVRE = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.REGION, "瓦伊凡");
  public static final TerraPlace MENAT_HAMAIT = registerPlace(TerraNation.SARGON, 155, TerraPlaceType.REGION, "米纳特哈玛仪");
  public static final TerraPlace CAPPAT = registerPlace(TerraNation.SAMI, 235, TerraPlaceType.SETTLEMENT, "察帕特");
  public static final TerraPlace FIRST_LAND = registerPlace(TerraNation.SAMI, 235, TerraPlaceType.REGION, "原初之地");
  public static final TerraPlace FJAL_VETRTONN = registerPlace(TerraNation.SAMI, 235, TerraPlaceType.NATURAL_FEATURE, "冬牙群山");
  public static final TerraPlace LONDINIUM = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.CITY, "伦蒂尼姆");
  public static final TerraPlace REDRIDGE = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.SETTLEMENT, "红脊镇");
  public static final TerraPlace CHETLEIGH = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.SETTLEMENT, "切特雷镇");
  public static final TerraPlace BRENTWOOD = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.SETTLEMENT, "布伦特伍德镇");
  public static final TerraPlace GIBSONHAM = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.SETTLEMENT, "吉布森镇");
  public static final TerraPlace GREEWICH = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.CITY, "格瑞威治");
  public static final TerraPlace LYNNCARDINE = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.CITY, "丽茵卡登");
  public static final TerraPlace CALADON = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.CITY, "卡拉顿");
  public static final TerraPlace BOSCHENDAL = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.CITY, "博森德尔");
  public static final TerraPlace COUNTY_TORON = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "多伦郡");
  public static final TerraPlace COUNTY_HILLOCK = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "小丘郡");
  public static final TerraPlace COUNTY_ASCARAT = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "阿斯卡拉郡");
  public static final TerraPlace COUNTY_LYNTON = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "林顿郡");
  public static final TerraPlace COUNTY_PENINSULA = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "半岛郡");
  public static final TerraPlace SYKES = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "塞克郡");
  public static final TerraPlace CASTSHIRE = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "开夏郡");
  public static final TerraPlace COUNTY_OAK_GROVE = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "橡林郡");
  public static final TerraPlace TRENT = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.REGION, "特伦特郡");
  public static final TerraPlace SCATHANNA_FIELDS = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.NATURAL_FEATURE, "石高原野");
  public static final TerraPlace CLARISIDE = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.NATURAL_FEATURE, "克拉斯德内海");
  public static final TerraPlace REDMANE_MOUNTAINS = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.NATURAL_FEATURE, "赤鬃山脉");
  public static final TerraPlace SILVERROCK_BLUFFS = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.NATURAL_FEATURE, "银石崖");
  public static final TerraPlace DUSKGLOW_RIVER = registerPlace(TerraNation.VICTORIA, 105, TerraPlaceType.NATURAL_FEATURE, "暮辉河");
  public static final TerraPlace DEITY_GRYPHERBURG = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "圣骏堡");
  public static final TerraPlace ZELGRAD = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "泽尔格勒（卫星城）");
  public static final TerraPlace CHERNOBOG = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "切尔诺伯格");
  public static final TerraPlace TULISKAYA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "图利斯卡亚");
  public static final TerraPlace NOVO_PETROVSK = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "新彼得罗夫斯克");
  public static final TerraPlace TAMANGRAD = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "塔曼格勒德");
  public static final TerraPlace VYATNO = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "维亚特诺");
  public static final TerraPlace ZAMOLESK = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "扎莫列斯");
  public static final TerraPlace PETRODANOR = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "彼得达诺尔");
  public static final TerraPlace BREZHENOY = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.CITY, "布列洁诺伊");
  public static final TerraPlace VEROBINSK = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.SETTLEMENT, "维罗比斯科镇");
  public static final TerraPlace GRIGORY_GOVERNORATE = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "格里高利省");
  public static final TerraPlace CENTRAL_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "中心矿区");
  public static final TerraPlace SATELLITE_CITY_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "卫星城矿区");
  public static final TerraPlace OLONETS_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "奥洛涅茨矿区");
  public static final TerraPlace VOLGOGRAD_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "沃尔格勒矿区");
  public static final TerraPlace URAL_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "乌拉尔矿区");
  public static final TerraPlace KRAS_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "克拉斯矿区");
  public static final TerraPlace ONEGA_MINING_AREA = registerPlace(TerraNation.URSUS, 300, TerraPlaceType.REGION, "奥涅加矿区");
  public static final TerraPlace TURICUM = registerPlace(TerraNation.KJERAG, 225, TerraPlaceType.CITY, "图里卡姆");
  public static final TerraPlace JUNGFRAU = registerPlace(TerraNation.KJERAG, 225, TerraPlaceType.NATURAL_FEATURE, "少女峰");
  public static final TerraPlace MATTERHORN = registerPlace(TerraNation.KJERAG, 225, TerraPlaceType.NATURAL_FEATURE, "马特洪峰");
  public static final TerraPlace MOUNT_KARLAN = registerPlace(TerraNation.KJERAG, 225, TerraPlaceType.NATURAL_FEATURE, "喀兰峰");
  public static final TerraPlace LAKE_SILBERNEHERZE = registerPlace(TerraNation.KJERAG, 225, TerraPlaceType.NATURAL_FEATURE, "银心湖");
  public static final TerraPlace MONTELUPE = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "蒙特卢佩");
  public static final TerraPlace SETTE_COLLI = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "七丘城");
  public static final TerraPlace WHITE_CITY = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "怀特城");
  public static final TerraPlace LOCOMOTIVA_CITY = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "拉克玛蒂瓦城");
  public static final TerraPlace PALERMO = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "帕勒莫");
  public static final TerraPlace VOLSINII = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "沃尔西尼");
  public static final TerraPlace NUOVA_VOLSINII = registerPlace(TerraNation.SIRACUSA, 55, TerraPlaceType.CITY, "新沃尔西尼");
  public static final TerraPlace BAIZAO = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "百灶");
  public static final TerraPlace LUNGMEN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "龙门");
  public static final TerraPlace JIANGQI = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "姜齐城");
  public static final TerraPlace HSI = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "夕城");
  public static final TerraPlace OCHRE = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "黄城");
  public static final TerraPlace SPRING_CITY = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "春都");
  public static final TerraPlace FLORIA_COUNTY = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "花郡");
  public static final TerraPlace KOU_WU = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "勾吴城");
  public static final TerraPlace DANYAN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "丹燕城");
  public static final TerraPlace SHANGSHU = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "尚蜀");
  public static final TerraPlace YUMEN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "玉门");
  public static final TerraPlace DAHUANG = registerPlace(TerraNation.YAN, 345, TerraPlaceType.CITY, "大荒城");
  public static final TerraPlace MANGSHAN_TOWN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.SETTLEMENT, "邙山镇");
  public static final TerraPlace PO_SHAN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.SETTLEMENT, "婆山镇");
  public static final TerraPlace XINLUAN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.REGION, "新峦区");
  public static final TerraPlace LIUYUN = registerPlace(TerraNation.YAN, 345, TerraPlaceType.REGION, "流云区");
  public static final TerraPlace MOUNT_MANG = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "邙山");
  public static final TerraPlace SHUZHOU_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "数舟峰");
  public static final TerraPlace QUJIANG_ZHUANJIANG_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "取江峰（攥江峰）");
  public static final TerraPlace FIELD_OF_FORGOTTEN_WATER = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "忘水坪");
  public static final TerraPlace MOUNT_JUQI = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "居奇山");
  public static final TerraPlace NINI_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "泥泥峰");
  public static final TerraPlace HUNTAN_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "昏谭峰");
  public static final TerraPlace BIELI_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "别离峰");
  public static final TerraPlace ZIYUN_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "梓云峰");
  public static final TerraPlace QINGLUAN_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "青銮峰");
  public static final TerraPlace XUNRI_PEAK = registerPlace(TerraNation.YAN, 345, TerraPlaceType.NATURAL_FEATURE, "寻日峰");
  public static final TerraPlace PERDONILLA_PERDONI = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.CITY, "佩尔多尼朵拉（佩尔多尼）");
  public static final TerraPlace SAL_VIENTO = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.CITY, "盐风城");
  public static final TerraPlace ROCAMAREA = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.SETTLEMENT, "潮石镇");
  public static final TerraPlace GRAN_FARO = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.CITY, "格兰法洛");
  public static final TerraPlace BASTION_DE_CANTICOS = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.CITY, "颂圣棱堡");
  public static final TerraPlace PORT_CITY = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.CITY, "港都");
  public static final TerraPlace AARON = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.SETTLEMENT, "雅隆镇");
  public static final TerraPlace SALT_FLAT = registerPlace(TerraNation.IBERIA, 90, TerraPlaceType.NATURAL_FEATURE, "盐漠");

  public static final List<TerraPlace> PLACES = freezePlaces();
  private static final Map<TerraNation, List<TerraPlace>> BY_NATION = indexByNation();
  private static final Map<PlaceName, TerraPlace> BY_NAME = indexByName();

  static {
    Zinecraft.TRANSLATIONS.add(
        "journeymap.zinecraft.terra_cities", "泰拉城市与聚落", "Terra Cities and Settlements"
    );
    Zinecraft.TRANSLATIONS.add(
        "journeymap.zinecraft.terra_regions", "泰拉重要地区", "Terra Regions"
    );
    PLACES.forEach(place -> Zinecraft.TRANSLATIONS.add(
        place.translationKey(), place.zhCn(), place.enUs()
    ));
  }

  private TerraGeography() {
  }

  public static List<TerraPlace> placesIn(TerraNation nation) {
    return BY_NATION.get(nation);
  }

  /**
   * 取得结构声明使用的城市或地区字段。
   *
   * @param nation 地点所属国家
   * @param zhCn   {@link #PLACES} 中的中文名称
   * @return 地理目录中的唯一地点
   */
  public static TerraPlace place(TerraNation nation, String zhCn) {
    TerraPlace place = BY_NAME.get(new PlaceName(
        Objects.requireNonNull(nation, "地点所属国家不能为空"),
        Objects.requireNonNull(zhCn, "地点名称不能为空").strip()
    ));
    if (place == null) throw new IllegalArgumentException("泰拉地理目录不存在地点：" + nation.getId() + "/" + zhCn);
    return place;
  }

  public static void bootstrap() {
  }

  private static TerraPlace registerPlace(
      TerraNation nation,
      int rotationDegrees,
      TerraPlaceType type,
      String zhCn
  ) {
    ModDimension.MapSite anchor = ModDimension.TERRA_MAP.stream()
        .filter(site -> site.nation() == nation)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("地点目录找不到国家锚点：" + nation));
    Map<TerraNation, Integer> indices = type.isUrban() ? URBAN_INDICES : REGION_INDICES;
    int index = indices.merge(nation, 1, Integer::sum) - 1;
    int[] offset = type.isUrban()
        ? urbanOffset(index, rotationDegrees)
        : regionOffset(index, REGION_COUNTS.getOrDefault(nation, 0), rotationDegrees);
    String localId = type.id() + "_" + Integer.toUnsignedString(zhCn.hashCode(), 36);
    String id = nation.getId() + "/" + localId;
    if (!PLACE_IDS.add(id)) throw new IllegalStateException("泰拉地点 ID 重复：" + id);
    int radiusX = type.isUrban() ? urbanRadius(type) : regionRadius(type);
    int radiusZ = type.isUrban() ? radiusX : radiusX * 3 / 4;
    TerraPlace place = new TerraPlace(
        id, nation, type, zhCn, zhCn,
        anchor.x() + offset[0], anchor.z() + offset[1], radiusX, radiusZ
    );
    validateNationAssignment(place);
    MUTABLE_PLACES.add(place);
    return place;
  }

  private static List<TerraPlace> freezePlaces() {
    List<TerraPlace> places = List.copyOf(MUTABLE_PLACES);
    if (places.stream().map(TerraPlace::nation).distinct().count() != TerraNation.entries().size()) {
      throw new IllegalStateException("必须为十九国全部设计城市或地区分布");
    }
    if (!REGION_COUNTS.equals(REGION_INDICES)) {
      throw new IllegalStateException("地区布局数量与静态地点声明不一致");
    }
    return places;
  }

  private static int[] urbanOffset(int index, int rotationDegrees) {
    if (index == 0) return new int[]{0, 0};
    double angle = Math.toRadians(rotationDegrees) + (index - 1) * GOLDEN_ANGLE;
    double distance = 620.0 + 430.0 * Math.sqrt(index);
    return offset(angle, distance);
  }

  private static int[] regionOffset(int index, int count, int rotationDegrees) {
    int firstRingCount = count > 7 ? (count + 1) / 2 : count;
    int ring = index >= firstRingCount ? 1 : 0;
    int ringStart = ring == 0 ? 0 : firstRingCount;
    int ringCount = ring == 0 ? firstRingCount : count - ringStart;
    int ringIndex = index - ringStart;
    double angle = Math.toRadians(rotationDegrees + 23) + Math.PI * 2.0 * ringIndex / Math.max(1, ringCount)
        + ring * Math.toRadians(19);
    return offset(angle, ring == 0 ? 2_250.0 : 3_150.0);
  }

  private static int[] offset(double angle, double distance) {
    return new int[]{
        (int) Math.round(Math.cos(angle) * distance),
        (int) Math.round(Math.sin(angle) * distance)
    };
  }

  private static int urbanRadius(TerraPlaceType type) {
    return switch (type) {
      case CITY -> 420;
      case SETTLEMENT -> 280;
      default -> throw new IllegalArgumentException("不是城市地点类型：" + type);
    };
  }

  private static int regionRadius(TerraPlaceType type) {
    return switch (type) {
      case REGION -> 820;
      case NATURAL_FEATURE -> 1_050;
      default -> throw new IllegalArgumentException("不是地区地点类型：" + type);
    };
  }

  private static void validateNationAssignment(TerraPlace place) {
    ModDimension.MapSite nearest = null;
    long nearestDistance = Long.MAX_VALUE;
    for (ModDimension.MapSite site : ModDimension.TERRA_MAP) {
      long dx = (long) place.x() - site.x();
      long dz = (long) place.z() - site.z();
      long distance = dx * dx + dz * dz;
      if (distance < nearestDistance) {
        nearest = site;
        nearestDistance = distance;
      }
    }
    if (nearest == null || nearest.nation() != place.nation()) {
      throw new IllegalStateException("游戏化地点坐标越出国家边界：" + place.id());
    }
  }

  private static Map<TerraNation, List<TerraPlace>> indexByNation() {
    Map<TerraNation, List<TerraPlace>> index = new EnumMap<>(TerraNation.class);
    for (TerraNation nation : TerraNation.entries()) {
      index.put(nation, PLACES.stream().filter(place -> place.nation() == nation).toList());
    }
    return Map.copyOf(index);
  }

  private static Map<PlaceName, TerraPlace> indexByName() {
    Map<PlaceName, TerraPlace> index = new HashMap<>();
    for (TerraPlace place : PLACES) {
      PlaceName key = new PlaceName(place.nation(), place.zhCn());
      if (index.putIfAbsent(key, place) != null) {
        throw new IllegalStateException("同一国家存在重复地点名称：" + place.nation().getId() + "/" + place.zhCn());
      }
    }
    return Map.copyOf(index);
  }

  private record PlaceName(TerraNation nation, String zhCn) {
  }
}
