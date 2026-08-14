package com.cxxcxx.zinecraft.api.nation

/** 泰拉维度当前实现的十九个国家；ID 与国家群系前缀保持一致。 */
enum class TerraNation(val id: String, val zhCn: String, val enUs: String) {
  AEGIR("aegir", "阿戈尔", "Aegir"),
  BOLIVAR("bolivar", "玻利瓦尔", "Bolivar"),
  HIGASHI("higashi", "东国", "Higashi"),
  DURIN("durin", "杜林", "Durin"),
  COLUMBIA("columbia", "哥伦比亚", "Columbia"),
  KAZIMIERZ("kazimierz", "卡西米尔", "Kazimierz"),
  KAZDEL("kazdel", "卡兹戴尔", "Kazdel"),
  LATERANO("laterano", "拉特兰", "Laterano"),
  LEITHANIEN("leithanien", "莱塔尼亚", "Leithanien"),
  RIM_BILLITON("rim_billiton", "雷姆必拓", "Rim Billiton"),
  MINOS("minos", "米诺斯", "Minos"),
  SARGON("sargon", "萨尔贡", "Sargon"),
  SAMI("sami", "萨米", "Sami"),
  VICTORIA("victoria", "维多利亚", "Victoria"),
  URSUS("ursus", "乌萨斯", "Ursus"),
  KJERAG("kjerag", "谢拉格", "Kjerag"),
  SIRACUSA("siracusa", "叙拉古", "Siracusa"),
  YAN("yan", "炎", "Yan"),
  IBERIA("iberia", "伊比利亚", "Iberia");

  companion object {
    private val BY_ID = entries.associateBy(TerraNation::id)

    fun byId(id: String): TerraNation? = BY_ID[id]
  }
}
