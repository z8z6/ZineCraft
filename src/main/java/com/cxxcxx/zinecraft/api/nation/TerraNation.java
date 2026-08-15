package com.cxxcxx.zinecraft.api.nation;

import java.util.Arrays;
import java.util.List;

public enum TerraNation {
  AEGIR("aegir", "阿戈尔", "Aegir"), BOLIVAR("bolivar", "玻利瓦尔", "Bolivar"),
  HIGASHI("higashi", "东国", "Higashi"), DURIN("durin", "杜林", "Durin"),
  COLUMBIA("columbia", "哥伦比亚", "Columbia"), KAZIMIERZ("kazimierz", "卡西米尔", "Kazimierz"),
  KAZDEL("kazdel", "卡兹戴尔", "Kazdel"), LATERANO("laterano", "拉特兰", "Laterano"),
  LEITHANIEN("leithanien", "莱塔尼亚", "Leithanien"), RIM_BILLITON("rim_billiton", "雷姆必拓", "Rim Billiton"),
  MINOS("minos", "米诺斯", "Minos"), SARGON("sargon", "萨尔贡", "Sargon"),
  SAMI("sami", "萨米", "Sami"), VICTORIA("victoria", "维多利亚", "Victoria"),
  URSUS("ursus", "乌萨斯", "Ursus"), KJERAG("kjerag", "谢拉格", "Kjerag"),
  SIRACUSA("siracusa", "叙拉古", "Siracusa"), YAN("yan", "炎", "Yan"),
  IBERIA("iberia", "伊比利亚", "Iberia");

  public static final Access ACCESS = new Access();
  private final String id;
  private final String zhCn;
  private final String enUs;

  TerraNation(String id, String zhCn, String enUs) {
    this.id = id;
    this.zhCn = zhCn;
    this.enUs = enUs;
  }

  public static List<TerraNation> getEntries() {
    return List.of(values());
  }

  public String getId() {
    return id;
  }

  public String getZhCn() {
    return zhCn;
  }

  public String getEnUs() {
    return enUs;
  }

  public static final class Access {
    public TerraNation byId(String id) {
      return Arrays.stream(values()).filter(nation -> nation.id.equals(id)).findFirst().orElse(null);
    }
  }
}
