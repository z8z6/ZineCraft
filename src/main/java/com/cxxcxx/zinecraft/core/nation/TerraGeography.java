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
  private static final List<NationDefinition> DEFINITIONS = definitions();
  public static final List<TerraPlace> PLACES = buildPlaces();
  private static final Map<TerraNation, List<TerraPlace>> BY_NATION = indexByNation();

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

  public static void bootstrap() {
  }

  private static List<TerraPlace> buildPlaces() {
    Map<TerraNation, ModDimension.MapSite> anchors = new EnumMap<>(TerraNation.class);
    for (ModDimension.MapSite site : ModDimension.TERRA_MAP) {
      if (site.nation() != null) anchors.put(site.nation(), site);
    }

    List<TerraPlace> places = new ArrayList<>();
    Set<String> ids = new HashSet<>();
    for (NationDefinition definition : DEFINITIONS) {
      ModDimension.MapSite anchor = anchors.get(definition.nation());
      if (anchor == null) throw new IllegalStateException("地点目录找不到国家锚点：" + definition.nation());
      List<NamedPlace> urban = definition.places().stream().filter(place -> place.type().isUrban()).toList();
      List<NamedPlace> regions = definition.places().stream().filter(place -> !place.type().isUrban()).toList();
      for (int index = 0; index < urban.size(); index++) {
        NamedPlace named = urban.get(index);
        int[] offset = urbanOffset(index, definition.rotationDegrees());
        add(places, ids, named, definition.nation(), anchor.x() + offset[0], anchor.z() + offset[1]);
      }
      for (int index = 0; index < regions.size(); index++) {
        NamedPlace named = regions.get(index);
        int[] offset = regionOffset(index, regions.size(), definition.rotationDegrees());
        add(places, ids, named, definition.nation(), anchor.x() + offset[0], anchor.z() + offset[1]);
      }
    }
    if (placesByNationCount(places) != TerraNation.entries().size()) {
      throw new IllegalStateException("必须为十九国全部设计城市或地区分布");
    }
    return List.copyOf(places);
  }

  private static int placesByNationCount(List<TerraPlace> places) {
    return (int) places.stream().map(TerraPlace::nation).distinct().count();
  }

  private static void add(
      List<TerraPlace> places,
      Set<String> ids,
      NamedPlace named,
      TerraNation nation,
      int x,
      int z
  ) {
    String localId = named.type().id() + "_" + Integer.toUnsignedString(named.zhCn().hashCode(), 36);
    String id = nation.getId() + "/" + localId;
    if (!ids.add(id)) throw new IllegalStateException("泰拉地点 ID 重复：" + id);
    int radiusX = named.type().isUrban() ? urbanRadius(named.type()) : regionRadius(named.type());
    int radiusZ = named.type().isUrban() ? radiusX : radiusX * 3 / 4;
    TerraPlace place = new TerraPlace(id, nation, named.type(), named.zhCn(), named.zhCn(), x, z, radiusX, radiusZ);
    validateNationAssignment(place);
    places.add(place);
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
      case DISTRICT -> 360;
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

  private static List<NationDefinition> definitions() {
    return List.of(
        nation(TerraNation.AEGIR, 248, city("弥利亚留姆")),
        nation(TerraNation.BOLIVAR, 205,
            city("多索雷斯"), city("拉乌尼达"), city("特科马"), feature("黑流树海"), feature("内河")),
        nation(TerraNation.HIGASHI, 315,
            city("南院行在御机大社"), city("北院镇守锁川城"), city("姬户城"), city("后川城"), city("二户城"),
            city("新安芸市"), settlement("露华村"), feature("常暗裂谷")),
        nation(TerraNation.DURIN, 330, city("际崖城"), city("天际城")),
        nation(TerraNation.COLUMBIA, 190,
            city("特里蒙"), city("提卡伦多"), city("堡垒山城"), city("纽莱堡市"), city("圣苏菲城"), city("铸铁城"),
            city("北诺斯维尔"), city("新曼法斯特"), city("蓝卡坞"), city("吉沃吉亚"), settlement("铁驮镇"),
            settlement("达维镇"), region("麦克斯特区"), region("螺旋桨天堂"), feature("加斯帕荒原"),
            feature("骸骨荒原"), feature("苏里根湖")),
        nation(TerraNation.KAZIMIERZ, 250,
            city("卡瓦莱利亚基（大骑士领）"), city("茨沃涅克"), city("奥格尼斯科"), settlement("滴水村"),
            settlement("垒石村"), settlement("沥泉村"), feature("黄金平原")),
        nation(TerraNation.KAZDEL, 300, city("卡兹戴尔城（今卡兹戴尔）"), settlement("贝罗尼村")),
        nation(TerraNation.LATERANO, 25,
            district("司提望区"), district("安布罗修区"), district("法柏尔区"), district("格芬区"),
            district("米迦莱昂区"), district("圣马尔索区"), district("伊卡莱西亚区")),
        nation(TerraNation.LEITHANIEN, 270,
            city("崔林特尔梅"), city("沃伦姆德"), city("维谢海姆"), city("格林登"), region("海登施威尔大区"),
            region("瓦瑟领大区"), region("福特冈大区"), region("奥施登海姆大区"), region("凯普拉尼大区"),
            region("恩瓦德大区"), region("鲁珀坎大区"), region("施彤领大区（费尔斯）"), region("厄登赫尔大区"),
            region("乌提卡领")),
        nation(TerraNation.RIM_BILLITON, 12,
            city("终极大铁屯"), city("钢铁萝卜城"), city("铁腕城"), settlement("大涌泉镇"), settlement("洋蓟村"),
            settlement("芜菁镇"), settlement("太阳谷"), settlement("红砂镇"), settlement("锈渣子镇"),
            region("格林梅多自治州"), region("比格皮勒自治州"), region("双倍黑尔梅特矿区"), region("零号公路"),
            region("南境"), feature("大风滩"), feature("咧嘴谷")),
        nation(TerraNation.MINOS, 140,
            city("科林尼亚"), city("雅赛努斯"), city("拉刻代蒙"), settlement("阿克罗蒂村"), city("爱琴"),
            feature("阿涅斯河"), feature("荷谟伊山"), feature("特尔斐运河"), feature("赫里亚山")),
        nation(TerraNation.SARGON, 155,
            settlement("长泉镇"), city("费坤城"), region("阿卡胡拉"), region("伊巴特地区"), feature("凯尔图恩谷地"),
            region("瓦伊凡"), region("米纳特哈玛仪")),
        nation(TerraNation.SAMI, 235, settlement("察帕特"), region("原初之地"), feature("冬牙群山")),
        nation(TerraNation.VICTORIA, 105,
            city("伦蒂尼姆"), settlement("红脊镇"), settlement("切特雷镇"), settlement("布伦特伍德镇"),
            settlement("吉布森镇"), city("格瑞威治"), city("丽茵卡登"), city("卡拉顿"), city("博森德尔"),
            region("多伦郡"), region("小丘郡"), region("阿斯卡拉郡"), region("林顿郡"), region("半岛郡"),
            region("塞克郡"), region("开夏郡"), region("橡林郡"), region("特伦特郡"), feature("石高原野"),
            feature("克拉斯德内海"), feature("赤鬃山脉"), feature("银石崖"), feature("暮辉河")),
        nation(TerraNation.URSUS, 300,
            city("圣骏堡"), city("泽尔格勒（卫星城）"), city("切尔诺伯格"), city("图利斯卡亚"),
            city("新彼得罗夫斯克"), city("塔曼格勒德"), city("维亚特诺"), city("扎莫列斯"), city("彼得达诺尔"),
            city("布列洁诺伊"), settlement("维罗比斯科镇"), region("格里高利省"), region("中心矿区"),
            region("卫星城矿区"), region("奥洛涅茨矿区"), region("沃尔格勒矿区"), region("乌拉尔矿区"),
            region("克拉斯矿区"), region("奥涅加矿区")),
        nation(TerraNation.KJERAG, 225,
            city("图里卡姆"), feature("少女峰"), feature("马特洪峰"), feature("喀兰峰"), feature("银心湖")),
        nation(TerraNation.SIRACUSA, 55,
            city("蒙特卢佩"), city("七丘城"), city("怀特城"), city("拉克玛蒂瓦城"), city("帕勒莫"),
            city("沃尔西尼"), city("新沃尔西尼")),
        nation(TerraNation.YAN, 345,
            city("百灶"), city("龙门"), city("姜齐城"), city("夕城"), city("黄城"), city("春都"), city("花郡"),
            city("勾吴城"), city("丹燕城"), city("尚蜀"), city("玉门"), city("大荒城"), settlement("邙山镇"),
            settlement("婆山镇"), district("新峦区"), district("流云区"), feature("邙山"), feature("数舟峰"),
            feature("取江峰（攥江峰）"), feature("忘水坪"), feature("居奇山"), feature("泥泥峰"), feature("昏谭峰"),
            feature("别离峰"), feature("梓云峰"), feature("青銮峰"), feature("寻日峰")),
        nation(TerraNation.IBERIA, 90,
            city("佩尔多尼朵拉（佩尔多尼）"), city("盐风城"), settlement("潮石镇"), city("格兰法洛"),
            city("颂圣棱堡"), city("港都"), settlement("雅隆镇"), feature("盐漠"))
    );
  }

  private static NationDefinition nation(TerraNation nation, int rotationDegrees, NamedPlace... places) {
    return new NationDefinition(nation, rotationDegrees, List.of(places));
  }

  private static NamedPlace city(String name) {
    return new NamedPlace(name, TerraPlaceType.CITY);
  }

  private static NamedPlace settlement(String name) {
    return new NamedPlace(name, TerraPlaceType.SETTLEMENT);
  }

  private static NamedPlace district(String name) {
    return new NamedPlace(name, TerraPlaceType.DISTRICT);
  }

  private static NamedPlace region(String name) {
    return new NamedPlace(name, TerraPlaceType.REGION);
  }

  private static NamedPlace feature(String name) {
    return new NamedPlace(name, TerraPlaceType.NATURAL_FEATURE);
  }

  private record NationDefinition(TerraNation nation, int rotationDegrees, List<NamedPlace> places) {
  }

  private record NamedPlace(String zhCn, TerraPlaceType type) {
  }
}
