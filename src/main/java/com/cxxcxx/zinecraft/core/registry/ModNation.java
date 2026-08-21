package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;

import java.util.List;

/**
 * 泰拉国家静态声明。
 */
public final class ModNation {
  public static final NationBuilder DURIN = Zinecraft.NATIONS.nation("durin", "杜林")
      .position(0.68, 0.62)
      .underground()
      .size(2_000)
      .cities(() -> List.of(ModCity.NEW_ZERUERTZA, ModCity.ORTZIMUGA))
      .build();
  public static final NationBuilder BOLIVAR = Zinecraft.NATIONS.nation("bolivar", "玻利瓦尔")
      .position(-0.90, -0.36)
      .cities(() -> List.of(ModCity.DOSSOLES, ModCity.LA_UNIDAD, ModCity.TECOMA))
      .build();
  public static final NationBuilder COLUMBIA = Zinecraft.NATIONS.nation("columbia", "哥伦比亚")
      .position(-0.66, -0.38)
      .cities(() -> List.of(
          ModCity.TRIMOUNTS, ModCity.TKARONTO, ModCity.BUNKERHILL_CITY,
          ModCity.NULAITEBURGH, ModCity.SAINT_SOPHIA_CITY, ModCity.IRONFORGE,
          ModCity.NORTHVILLE, ModCity.NEW_MANFIST, ModCity.BLUECARD,
          ModCity.GIVOGIA, ModCity.STEELHAM, ModCity.DAVISTOWN,
          ModCity.MAX_DC, ModCity.PROPELLER_PARADISE
      ))
      .build();
  public static final NationBuilder KAZIMIERZ = Zinecraft.NATIONS.nation("kazimierz", "卡西米尔")
      .position(-0.36, -0.50)
      .cities(() -> List.of(
          ModCity.GRAND_KNIGHT_TERRITORY, ModCity.DZWONEK, ModCity.OGNISKO,
          ModCity.DEWVILLE, ModCity.ROCKVILLE, ModCity.STRUMYKOWO
      ))
      .build();
  public static final NationBuilder KAZDEL = Zinecraft.NATIONS.nation("kazdel", "卡兹戴尔")
      .position(0.75, -0.75)
      .cities(() -> List.of(ModCity.KAZDEL, ModCity.BELLONY_VILLAGE))
      .build();
  public static final NationBuilder LATERANO = Zinecraft.NATIONS.nation("laterano", "拉特兰")
      .position(0.15, 0.15)
      .cities(() -> List.of(
          ModCity.PAGUS_STEVONUS, ModCity.PAGUS_AMBROSIUS, ModCity.PAGUS_FABER,
          ModCity.PAGUS_GRIFFIN, ModCity.PAGUS_MICHAELION, ModCity.PAGUS_SAINT_MARCEL,
          ModCity.PAGUS_ECCLESIA
      ))
      .build();
  public static final NationBuilder LEITHANIEN = Zinecraft.NATIONS.nation("leithanien", "莱塔尼亚")
      .position(0.10, -0.20)
      .cities(() -> List.of(
          ModCity.ZWILLINGSTURME, ModCity.WOLUMONDE, ModCity.VYSEHEIM,
          ModCity.GRINDEN, ModCity.KREIS_HELDENSCHWERT, ModCity.WASSERLAND,
          ModCity.KREIS_FURTGANG, ModCity.KREIS_OSTENHEIM, ModCity.KREIS_KEPLANI,
          ModCity.KREIS_EINGEWEIDE, ModCity.LUPUKARN, ModCity.STURMLAND_FELS,
          ModCity.ERDENHERRE, ModCity.URTICA_GRAFSCHAFT
      ))
      .build();
  public static final NationBuilder SIRACUSA = Zinecraft.NATIONS.nation("siracusa", "叙拉古")
      .position(0.20, -0.15)
      .cities(() -> List.of(
          ModCity.MONTELUPE, ModCity.SETTE_COLLI, ModCity.WHITE_CITY,
          ModCity.LOCOMOTIVA_CITY, ModCity.PALERMO, ModCity.VOLSINII,
          ModCity.NUOVA_VOLSINII
      ))
      .build();
  public static final NationBuilder RIM_BILLITON = Zinecraft.NATIONS.nation("rim_billiton", "雷姆必拓")
      .position(0.5, 0.1)
      .cities(() -> List.of(
          ModCity.ULTIMATE_IRON_HOLD, ModCity.IRON_CARROT_CITY, ModCity.IRON_FIST_CITY,
          ModCity.GREAT_SPRING_TOWN, ModCity.ARTICHOKE_VILLAGE, ModCity.TURNIP_TOWN,
          ModCity.SUN_VALLEY, ModCity.RED_SAND_TOWN, ModCity.RUSTDREG_TOWN,
          ModCity.GREENMEADOW_SHIRE, ModCity.BIG_PILLAR_SHIRE, ModCity.DOUBLE_HELMET_MINE,
          ModCity.HIGHWAY_ZERO, ModCity.SOUTHERN_REACH
      ))
      .build();
  public static final NationBuilder MINOS = Zinecraft.NATIONS.nation("minos", "米诺斯")
      .position(-0.40, 0.20)
      .cities(() -> List.of(
          ModCity.KORINTHIA, ModCity.ATHENIUS, ModCity.LACHEDAMON,
          ModCity.AKROTIRI_VILLAGE, ModCity.AEGEAN
      ))
      .build();
  public static final NationBuilder SARGON = Zinecraft.NATIONS.nation("sargon", "萨尔贡")
      .position(-0.82, 0.60)
      .cities(() -> List.of(
          ModCity.LONG_SPRING_TOWN, ModCity.PHECON, ModCity.ACAHUALLA,
          ModCity.IBUT_REGION, ModCity.WEST_VOUIVRE, ModCity.MENAT_HAMAIT
      ))
      .build();
  public static final NationBuilder SIESTA = Zinecraft.NATIONS.nation("siesta", "汐斯塔")
      .position(-0.12, -0.38)
      .cities(() -> List.of(ModCity.SIESTA))
      .build();
  public static final NationBuilder VICTORIA = Zinecraft.NATIONS.nation("victoria", "维多利亚")
      .position(0.0, 0.0)
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
      .position(-0.80, -0.82)
      .cities(() -> List.of(ModCity.CAPPAT, ModCity.FIRST_LAND))
      .build();
  public static final NationBuilder URSUS = Zinecraft.NATIONS.nation("ursus", "乌萨斯")
      .position(-0.58, -0.72)
      .position(0.0, -0.72)
      .position(0.0, -0.50)
      .position(0.75, -0.92)
      .cities(() -> List.of(
          ModCity.DEITY_GRYPHERBURG, ModCity.ZELGRAD, ModCity.CHERNOBOG,
          ModCity.TULISKAYA, ModCity.NOVO_PETROVSK, ModCity.TAMANGRAD,
          ModCity.VYATNO, ModCity.ZAMOLESK, ModCity.PETRODANOR,
          ModCity.BREZHENOY, ModCity.VEROBINSK, ModCity.GRIGORY_GOVERNORATE
      ))
      .build();
  public static final NationBuilder KJERAG = Zinecraft.NATIONS.nation("kjerag", "谢拉格")
      .position(-0.38, 0.20)
      .cities(() -> List.of(ModCity.TURICUM))
      .build();
  public static final NationBuilder HIGASHI = Zinecraft.NATIONS.nation("higashi", "东国")
      .position(0.82, -0.80)
      .cities(() -> List.of(
          ModCity.SOUTHERN_COURT_IMPERIAL_SHRINE, ModCity.NORTHERN_COURT_SOKOGAWA_CASTLE,
          ModCity.HIMEJI_CASTLE, ModCity.USHIROKAWA_CASTLE, ModCity.NITO_JO,
          ModCity.SHIN_AKI_CITY, ModCity.ROKA_VILLAGE
      ))
      .build();
  public static final NationBuilder YAN = Zinecraft.NATIONS.nation("yan", "炎")
      .position(0.78, -0.60)
      .cities(() -> List.of(
          ModCity.BAIZAO, ModCity.LUNGMEN, ModCity.JIANGQI,
          ModCity.HSI, ModCity.OCHRE, ModCity.SPRING_CITY,
          ModCity.FLORIA_COUNTY, ModCity.KOU_WU, ModCity.DANYAN,
          ModCity.SHANGSHU, ModCity.YUMEN, ModCity.DAHUANG,
          ModCity.MANGSHAN_TOWN, ModCity.PO_SHAN
      ))
      .build();
  public static final NationBuilder AEGIR = Zinecraft.NATIONS.nation("aegir", "阿戈尔")
      .position(0.78, 0.85)
      .cities(() -> List.of(ModCity.MILLIARIUM))
      .build();
  public static final NationBuilder IBERIA = Zinecraft.NATIONS.nation("iberia", "伊比利亚")
      .position(0.62, 0.62)
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
