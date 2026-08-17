package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.neoforge.registries.DeferredItem;

public final class ModFood {
  public static final ModFood INSTANCE = new ModFood();
  public static final DeferredItem<Item> AEGIR_FRESH_SHELLCRAB_SASHIMI = food(
      "aegir_fresh_shellcrab_sashimi",
      "阿戈尔鲜切蟹生",
      6, 0.7F, false, false
  );
  public static final DeferredItem<Item> BOLIVAR_SMOKED_CAPSULE = food(
      "bolivar_smoked_capsule",
      "玻利瓦尔熏烤胶囊",
      7, 0.8F, true, false
  );
  public static final DeferredItem<Item> HIGASHI_NANO_KAPPO = food(
      "higashi_nano_kappo",
      "东国纳米割烹",
      8, 0.9F, false, false
  );
  public static final DeferredItem<Item> DURIN_HONEY_SLUGPUDDING = food(
      "durin_honey_slugpudding",
      "杜林蜜味源石虫布丁",
      5, 0.6F, true, false
  );
  public static final DeferredItem<Item> COLUMBIA_ORIGINIUM_ROASTED_FOWL = food(
      "columbia_originium_roasted_fowl",
      "哥伦比亚炮灰羽",
      8, 0.8F, false, false
  );
  public static final DeferredItem<Item> KAZIMIERZ_KNIGHT_SUPPLEMENT = food(
      "kazimierz_knight_supplement",
      "卡西米尔骑士补充剂",
      4, 0.5F, true, true
  );
  public static final DeferredItem<Item> KAZDEL_CARTILAGE_TACK = food(
      "kazdel_cartilage_tack",
      "卡兹戴尔软骨饼干",
      5, 0.7F, true, false
  );
  public static final DeferredItem<Item> LATERANO_SACRED_TONE_SOUP = food(
      "laterano_sacred_tone_soup",
      "拉特兰圣音汤醇",
      6, 0.8F, false, false
  );
  public static final DeferredItem<Item> LEITHANIEN_MUSICAL_ROAST_EXTRACT = food(
      "leithanien_musical_roast_extract",
      "莱塔尼亚音乐速烤萃取酯",
      7, 0.9F, true, false
  );
  public static final DeferredItem<Item> RIM_BILLITON_MINING_RATION = food(
      "rim_billiton_mining_ration",
      "雷姆必拓矿区应急餐",
      6, 0.8F, true, false
  );
  public static final DeferredItem<Item> MINOS_POETRY_GEL = food(
      "minos_poetry_gel",
      "米诺斯诗歌凝胶",
      5, 0.7F, true, false
  );
  public static final DeferredItem<Item> SARGON_GRASS_CHEESE_GEL = food(
      "sargon_grass_cheese_gel",
      "萨尔贡青草芝士凝胶",
      5, 0.6F, true, false
  );
  public static final DeferredItem<Item> SAMI_INSTANT_BONE_SOUP = food(
      "sami_instant_bone_soup",
      "萨米即食骨汤",
      8, 1.0F, false, false
  );
  public static final DeferredItem<Item> VICTORIA_CENTRAL_VALLEY_ROAST = food(
      "victoria_central_valley_roast",
      "维多利亚中央谷地混合烤肉",
      10, 1.2F, false, false
  );
  public static final DeferredItem<Item> URSUS_HAM_SUPPLEMENT = food(
      "ursus_ham_supplement",
      "乌萨斯火腿补剂",
      10, 1.1F, true, false
  );
  public static final DeferredItem<Item> KJERAG_VALLEY_PIE = food(
      "kjerag_valley_pie",
      "谢拉格谷地馅饼",
      8, 1.0F, false, false
  );
  public static final DeferredItem<Item> SIRACUSA_STEW_GATHERING = food(
      "siracusa_stew_gathering",
      "叙拉古炖锅集会",
      9, 1.1F, false, false
  );
  public static final DeferredItem<Item> YAN_WASTELAND_MEAT_STIR_FRY = food(
      "yan_wasteland_meat_stir_fry",
      "炎国荒地菜肉酮",
      9, 1.0F, false, false
  );
  public static final DeferredItem<Item> IBERIA_CHITIN_CLUSTER = food(
      "iberia_chitin_cluster",
      "伊比利亚甲壳质聚块",
      6, 0.9F, true, false
  );

  private static DeferredItem<Item> food(
      String path,
      String zhCn,
      int nutrition,
      float saturation,
      boolean fast,
      boolean alwaysEdible) {
    Builder builder = new Builder().nutrition(nutrition).saturationModifier(saturation);
    if (fast) builder.fast();
    if (alwaysEdible) builder.alwaysEdible();
    String enUs = TranslationNames.toDisplayName(path);
    Properties properties = new Properties().food(builder.build());
    return Zinecraft.ITEMS.builder(path, zhCn, () -> new Item(properties))
        .enUs(enUs)
        .compost(0.65F)
        .build();
  }
}
