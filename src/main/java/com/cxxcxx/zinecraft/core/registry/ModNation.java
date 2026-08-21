package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

import java.util.List;

/**
 * 泰拉国家静态声明。
 */
public final class ModNation {
  public static final NationBuilder DURIN = Zinecraft.NATIONS.nation("durin", "杜林")
      .position(-0.6417,0.3505)
      .underground()
      .size(2_000)
      .cities(() -> List.of(ModCity.NEW_ZERUERTZA, ModCity.ORTZIMUGA))
      .build();
  public static final NationBuilder BOLIVAR = Zinecraft.NATIONS.nation("bolivar", "玻利瓦尔")
      .position(-0.7882,-0.1886)
      .position(-0.9133,-0.4052)
      .cities(() -> List.of(ModCity.DOSSOLES, ModCity.LA_UNIDAD, ModCity.TECOMA))
      .build();
  public static final NationBuilder COLUMBIA = Zinecraft.NATIONS.nation("columbia", "哥伦比亚")
      .position(-0.4473,-0.4265)
      .position(-0.5885,-0.0927)
      .position(-0.7030,-0.2987)
      .position(-0.8201,-0.4833)
      .cities(() -> List.of(
          ModCity.TRIMOUNTS, ModCity.TKARONTO, ModCity.BUNKERHILL_CITY,
          ModCity.NULAITEBURGH, ModCity.SAINT_SOPHIA_CITY, ModCity.IRONFORGE,
          ModCity.NORTHVILLE, ModCity.NEW_MANFIST, ModCity.BLUECARD,
          ModCity.GIVOGIA, ModCity.STEELHAM, ModCity.DAVISTOWN,
          ModCity.MAX_DC, ModCity.PROPELLER_PARADISE
      ))
      .build();
  public static final NationBuilder KAZIMIERZ = Zinecraft.NATIONS.nation("kazimierz", "卡西米尔")
      .position(-0.2902,-0.3555)
      .position(-0.1731,-0.3910)
      .position(-0.2450,-0.5259)
      .cities(() -> List.of(
          ModCity.GRAND_KNIGHT_TERRITORY, ModCity.DZWONEK, ModCity.OGNISKO,
          ModCity.DEWVILLE, ModCity.ROCKVILLE, ModCity.STRUMYKOWO
      ))
      .build();
  public static final NationBuilder KAZDEL = Zinecraft.NATIONS.nation("kazdel", "卡兹戴尔")
      .position(0.4767,-0.2205)
      .cities(() -> List.of(ModCity.KAZDEL, ModCity.BELLONY_VILLAGE))
      .build();
  public static final NationBuilder LATERANO = Zinecraft.NATIONS.nation("laterano", "拉特兰")
      .position(0.1278,0.1771)
      .cities(() -> List.of(
          ModCity.PAGUS_STEVONUS, ModCity.PAGUS_AMBROSIUS, ModCity.PAGUS_FABER,
          ModCity.PAGUS_GRIFFIN, ModCity.PAGUS_MICHAELION, ModCity.PAGUS_SAINT_MARCEL,
          ModCity.PAGUS_ECCLESIA
      ))
      .build();
  public static final NationBuilder LEITHANIEN = Zinecraft.NATIONS.nation("leithanien", "莱塔尼亚")
      .position(-0.0426,-0.2276)
      .position(0.0400,-0.0856)
      .position(0.1491,-0.1992)
      .cities(() -> List.of(
          ModCity.ZWILLINGSTURME, ModCity.WOLUMONDE, ModCity.VYSEHEIM,
          ModCity.GRINDEN, ModCity.KREIS_HELDENSCHWERT, ModCity.WASSERLAND,
          ModCity.KREIS_FURTGANG, ModCity.KREIS_OSTENHEIM, ModCity.KREIS_KEPLANI,
          ModCity.KREIS_EINGEWEIDE, ModCity.LUPUKARN, ModCity.STURMLAND_FELS,
          ModCity.ERDENHERRE, ModCity.URTICA_GRAFSCHAFT
      ))
      .build();
  public static final NationBuilder SIRACUSA = Zinecraft.NATIONS.nation("siracusa", "叙拉古")
      .position(0.2104,-0.0430)
      .position(0.2982,-0.1353)
      .cities(() -> List.of(
          ModCity.MONTELUPE, ModCity.SETTE_COLLI, ModCity.WHITE_CITY,
          ModCity.LOCOMOTIVA_CITY, ModCity.PALERMO, ModCity.VOLSINII,
          ModCity.NUOVA_VOLSINII
      ))
      .build();
  public static final NationBuilder RIM_BILLITON = Zinecraft.NATIONS.nation("rim_billiton", "雷姆必拓")
      .position(0.3542,0.0706)
      .position(0.5432,0.2907)
      .cities(() -> List.of(
          ModCity.ULTIMATE_IRON_HOLD, ModCity.IRON_CARROT_CITY, ModCity.IRON_FIST_CITY,
          ModCity.GREAT_SPRING_TOWN, ModCity.ARTICHOKE_VILLAGE, ModCity.TURNIP_TOWN,
          ModCity.SUN_VALLEY, ModCity.RED_SAND_TOWN, ModCity.RUSTDREG_TOWN,
          ModCity.GREENMEADOW_SHIRE, ModCity.BIG_PILLAR_SHIRE, ModCity.DOUBLE_HELMET_MINE,
          ModCity.HIGHWAY_ZERO, ModCity.SOUTHERN_REACH
      ))
      .build();
  public static final NationBuilder MINOS = Zinecraft.NATIONS.nation("minos", "米诺斯")
      .position(-0.5405,0.2090)
      .cities(() -> List.of(
          ModCity.KORINTHIA, ModCity.ATHENIUS, ModCity.LACHEDAMON,
          ModCity.AKROTIRI_VILLAGE, ModCity.AEGEAN
      ))
      .build();
  public static final NationBuilder SARGON = Zinecraft.NATIONS.nation("sargon", "萨尔贡")
      .position(-0.9613,0.2019)
      .position(-0.7642,0.3049)
      .position(-0.6417,0.4505)
      .position(-0.4367,0.4150)
      .position(-0.2663,0.5889)
      .cities(() -> List.of(
          ModCity.LONG_SPRING_TOWN, ModCity.PHECON, ModCity.ACAHUALLA,
          ModCity.IBUT_REGION, ModCity.WEST_VOUIVRE, ModCity.MENAT_HAMAIT
      ))
      .build();
  public static final NationBuilder SIESTA = Zinecraft.NATIONS.nation("siesta", "汐斯塔")
      .position(-0.2849,0.2801)
      .cities(() -> List.of(ModCity.SIESTA))
      .build();
  public static final NationBuilder VICTORIA = Zinecraft.NATIONS.nation("victoria", "维多利亚")
      .position(-0.1385,-0.2276)
      .position(-0.1544,-0.0572)
      .position(-0.1677,0.1593)
      .position(-0.0346,0.0564)
      .cities(() -> List.of(
          ModCity.LONDINIUM, ModCity.REDRIDGE, ModCity.CHETLEIGH,
          ModCity.BRENTWOOD, ModCity.GIBSONHAM, ModCity.GREEWICH,
          ModCity.LYNNCARDINE, ModCity.CALADON, ModCity.BOSCHENDAL,
          ModCity.COUNTY_TORON, ModCity.COUNTY_HILLOCK, ModCity.COUNTY_ASCARAT,
          ModCity.COUNTY_LYNTON, ModCity.COUNTY_PENINSULA, ModCity.SYKES,
          ModCity.CASTSHIRE, ModCity.COUNTY_OAK_GROVE, ModCity.TRENT
      ))
      .build();
  public static final NationBuilder SAMI = Zinecraft.NATIONS.nation("sami", "萨米")
      .position(-0.9527,-0.8099)
      .position(-0.8328,-0.8383)
      .cities(() -> List.of(ModCity.CAPPAT, ModCity.FIRST_LAND))
      .build();
  public static final NationBuilder URSUS = Zinecraft.NATIONS.nation("ursus", "乌萨斯")
      .position(-0.7214, -0.6944)
      .position(-0.0821, -0.5789)
      .position(0.0547, -0.3783)
      .position(0.2416, -0.5272)
      .position(0.6451, -0.9072)
      .cities(() -> List.of(
          ModCity.DEITY_GRYPHERBURG, ModCity.ZELGRAD, ModCity.CHERNOBOG,
          ModCity.TULISKAYA, ModCity.NOVO_PETROVSK, ModCity.TAMANGRAD,
          ModCity.VYATNO, ModCity.ZAMOLESK, ModCity.PETRODANOR,
          ModCity.BREZHENOY, ModCity.VEROBINSK, ModCity.GRIGORY_GOVERNORATE
      ))
      .build();
  public static final NationBuilder KJERAG = Zinecraft.NATIONS.nation("kjerag", "谢拉格")
      .position(-0.3754,-0.1637)
      .cities(() -> List.of(ModCity.TURICUM))
      .build();
  public static final NationBuilder HIGASHI = Zinecraft.NATIONS.nation("higashi", "东国")
      .position(0.7909,-0.7993)
      .position(0.7323,-0.6643)
      .cities(() -> List.of(
          ModCity.SOUTHERN_COURT_IMPERIAL_SHRINE, ModCity.NORTHERN_COURT_SOKOGAWA_CASTLE,
          ModCity.HIMEJI_CASTLE, ModCity.USHIROKAWA_CASTLE, ModCity.NITO_JO,
          ModCity.SHIN_AKI_CITY, ModCity.ROKA_VILLAGE
      ))
      .build();
  public static final NationBuilder YAN = Zinecraft.NATIONS.nation("yan", "炎")
      .position(0.5991,-0.5472)
      .position(0.8335,-0.5756)
      .position(0.9080,-0.3661)
      .position(0.7456,-0.2418)
      .cities(() -> List.of(
          ModCity.BAIZAO, ModCity.LUNGMEN, ModCity.JIANGQI,
          ModCity.HSI, ModCity.OCHRE, ModCity.SPRING_CITY,
          ModCity.FLORIA_COUNTY, ModCity.KOU_WU, ModCity.DANYAN,
          ModCity.SHANGSHU, ModCity.YUMEN, ModCity.DAHUANG,
          ModCity.MANGSHAN_TOWN, ModCity.PO_SHAN
      ))
      .build();
  public static final NationBuilder AEGIR = Zinecraft.NATIONS.nation("aegir", "阿戈尔")
      .position(0.2796,0.5179)
      .position(0.5193,0.5108)
      .position(0.8175,0.5747)
      .cities(() -> List.of(ModCity.MILLIARIUM))
      .build();
  public static final NationBuilder IBERIA = Zinecraft.NATIONS.nation("iberia", "伊比利亚")
      .position(0.0692,0.3830)
      .position(-0.0453,0.6741)
      .position(0.1997,0.5428)
      .cities(() -> List.of(
          ModCity.PERDONILLA_PERDONI, ModCity.SAL_VIENTO, ModCity.ROCAMAREA,
          ModCity.GRAN_FARO, ModCity.BASTION_DE_CANTICOS, ModCity.PORT_CITY,
          ModCity.AARON
      ))
      .build();

  public static final List<NationBuilder> ALL = List.copyOf(Zinecraft.NATIONS.entries());

  private ModNation() {
  }

  public static void bootstrap() {
  }
}
