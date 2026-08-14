package com.cxxcxx.zinecraft.core.item

import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.core.Zinecraft
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item

/**
 * 十九国代表食品。
 *
 * 名称优先采用“生息演算”已有国家食品；资料未明确覆盖的国家使用终末地料理原型做项目内转译。
 * 这些物品只表达食物文化与生存补给，不把玩法食品误写为官方国菜。
 */
object NationFoods {
  val AEGIR_FRESH_SHELLCRAB_SASHIMI =
    food("aegir_fresh_shellcrab_sashimi", "阿戈尔鲜切蟹生", "Aegir Fresh-Cut Shellcrab Sashimi", 6, 0.7f)
  val BOLIVAR_SMOKED_CAPSULE =
    food("bolivar_smoked_capsule", "玻利瓦尔熏烤胶囊", "Bolivar Smoked Capsule", 7, 0.8f, fast = true)
  val HIGASHI_NANO_KAPPO =
    food("higashi_nano_kappo", "东国纳米割烹", "Higashi Nano Kappo", 8, 0.9f)
  val DURIN_HONEY_SLUGPUDDING =
    food("durin_honey_slugpudding", "杜林蜜味源石虫布丁", "Durin Honey Slugpudding", 5, 0.6f, fast = true)
  val COLUMBIA_ORIGINIUM_ROASTED_FOWL =
    food("columbia_originium_roasted_fowl", "哥伦比亚炮灰羽", "Columbia Originium-Roasted Fowl", 8, 0.8f)
  val KAZIMIERZ_KNIGHT_SUPPLEMENT =
    food(
      "kazimierz_knight_supplement",
      "卡西米尔骑士补充剂",
      "Kazimierz Knight Supplement",
      4,
      0.5f,
      fast = true,
      alwaysEdible = true
    )
  val KAZDEL_CARTILAGE_TACK =
    food("kazdel_cartilage_tack", "卡兹戴尔软骨饼干", "Kazdel Cartilage Tack", 5, 0.7f, fast = true)
  val LATERANO_SACRED_TONE_SOUP =
    food("laterano_sacred_tone_soup", "拉特兰圣音汤醇", "Laterano Sacred-Tone Soup Extract", 6, 0.8f)
  val LEITHANIEN_MUSICAL_ROAST_EXTRACT =
    food(
      "leithanien_musical_roast_extract",
      "莱塔尼亚音乐速烤萃取酯",
      "Leithanien Musical Flash-Roast Extract",
      7,
      0.9f,
      fast = true
    )
  val RIM_BILLITON_MINING_RATION =
    food("rim_billiton_mining_ration", "雷姆必拓矿区应急餐", "Rim Billiton Mining Ration", 6, 0.8f, fast = true)
  val MINOS_POETRY_GEL =
    food("minos_poetry_gel", "米诺斯诗歌凝胶", "Minos Poetry Gel", 5, 0.7f, fast = true)
  val SARGON_GRASS_CHEESE_GEL =
    food("sargon_grass_cheese_gel", "萨尔贡青草芝士凝胶", "Sargon Grass Cheese Gel", 5, 0.6f, fast = true)
  val SAMI_INSTANT_BONE_SOUP =
    food("sami_instant_bone_soup", "萨米即食骨汤", "Sami Instant Bone Soup", 8, 1.0f)
  val VICTORIA_CENTRAL_VALLEY_ROAST =
    food("victoria_central_valley_roast", "维多利亚中央谷地混合烤肉", "Victoria Central Valley Mixed Roast", 10, 1.2f)
  val URSUS_HAM_SUPPLEMENT =
    food("ursus_ham_supplement", "乌萨斯火腿补剂", "Ursus Ham Supplement", 10, 1.1f, fast = true)
  val KJERAG_VALLEY_PIE =
    food("kjerag_valley_pie", "谢拉格谷地馅饼", "Kjerag Valley Pie", 8, 1.0f)
  val SIRACUSA_STEW_GATHERING =
    food("siracusa_stew_gathering", "叙拉古炖锅集会", "Siracusa Stew Gathering", 9, 1.1f)
  val YAN_WASTELAND_MEAT_STIR_FRY =
    food("yan_wasteland_meat_stir_fry", "炎国荒地菜肉酮", "Yan Wasteland Meat-and-Vegetable Extract", 9, 1.0f)
  val IBERIA_CHITIN_CLUSTER =
    food("iberia_chitin_cluster", "伊比利亚甲壳质聚块", "Iberia Chitin Cluster", 6, 0.9f, fast = true)

  private fun food(
    path: String,
    zhCn: String,
    enUs: String,
    nutrition: Int,
    saturation: Float,
    fast: Boolean = false,
    alwaysEdible: Boolean = false
  ): ItemEntry<Item> {
    val food = FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation)
    if (fast) food.fast()
    if (alwaysEdible) food.alwaysEdible()
    return Zinecraft.ITEMS.register(
      path,
      zhCn,
      enUs,
      properties = Item.Properties().food(food.build())
    ).compost(0.65f)
  }
}
