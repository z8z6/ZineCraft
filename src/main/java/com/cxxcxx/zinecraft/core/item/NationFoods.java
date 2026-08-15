package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

public final class NationFoods {
  @NotNull
  public static final NationFoods INSTANCE = new NationFoods();
  @NotNull
  private static final ItemEntry<Item> AEGIR_FRESH_SHELLCRAB_SASHIMI = food$default(
      INSTANCE, "aegir_fresh_shellcrab_sashimi", "阿戈尔鲜切蟹生", "Aegir Fresh-Cut Shellcrab Sashimi", 6, 0.7F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> BOLIVAR_SMOKED_CAPSULE = food$default(
      INSTANCE, "bolivar_smoked_capsule", "玻利瓦尔熏烤胶囊", "Bolivar Smoked Capsule", 7, 0.8F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> HIGASHI_NANO_KAPPO = food$default(
      INSTANCE, "higashi_nano_kappo", "东国纳米割烹", "Higashi Nano Kappo", 8, 0.9F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> DURIN_HONEY_SLUGPUDDING = food$default(
      INSTANCE, "durin_honey_slugpudding", "杜林蜜味源石虫布丁", "Durin Honey Slugpudding", 5, 0.6F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> COLUMBIA_ORIGINIUM_ROASTED_FOWL = food$default(
      INSTANCE, "columbia_originium_roasted_fowl", "哥伦比亚炮灰羽", "Columbia Originium-Roasted Fowl", 8, 0.8F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> KAZIMIERZ_KNIGHT_SUPPLEMENT = INSTANCE.food(
      "kazimierz_knight_supplement", "卡西米尔骑士补充剂", "Kazimierz Knight Supplement", 4, 0.5F, true, true
  );
  @NotNull
  private static final ItemEntry<Item> KAZDEL_CARTILAGE_TACK = food$default(
      INSTANCE, "kazdel_cartilage_tack", "卡兹戴尔软骨饼干", "Kazdel Cartilage Tack", 5, 0.7F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> LATERANO_SACRED_TONE_SOUP = food$default(
      INSTANCE, "laterano_sacred_tone_soup", "拉特兰圣音汤醇", "Laterano Sacred-Tone Soup Extract", 6, 0.8F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> LEITHANIEN_MUSICAL_ROAST_EXTRACT = food$default(
      INSTANCE, "leithanien_musical_roast_extract", "莱塔尼亚音乐速烤萃取酯", "Leithanien Musical Flash-Roast Extract", 7, 0.9F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> RIM_BILLITON_MINING_RATION = food$default(
      INSTANCE, "rim_billiton_mining_ration", "雷姆必拓矿区应急餐", "Rim Billiton Mining Ration", 6, 0.8F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> MINOS_POETRY_GEL = food$default(
      INSTANCE, "minos_poetry_gel", "米诺斯诗歌凝胶", "Minos Poetry Gel", 5, 0.7F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> SARGON_GRASS_CHEESE_GEL = food$default(
      INSTANCE, "sargon_grass_cheese_gel", "萨尔贡青草芝士凝胶", "Sargon Grass Cheese Gel", 5, 0.6F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> SAMI_INSTANT_BONE_SOUP = food$default(
      INSTANCE, "sami_instant_bone_soup", "萨米即食骨汤", "Sami Instant Bone Soup", 8, 1.0F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> VICTORIA_CENTRAL_VALLEY_ROAST = food$default(
      INSTANCE, "victoria_central_valley_roast", "维多利亚中央谷地混合烤肉", "Victoria Central Valley Mixed Roast", 10, 1.2F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> URSUS_HAM_SUPPLEMENT = food$default(
      INSTANCE, "ursus_ham_supplement", "乌萨斯火腿补剂", "Ursus Ham Supplement", 10, 1.1F, true, false, 64, null
  );
  @NotNull
  private static final ItemEntry<Item> KJERAG_VALLEY_PIE = food$default(
      INSTANCE, "kjerag_valley_pie", "谢拉格谷地馅饼", "Kjerag Valley Pie", 8, 1.0F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> SIRACUSA_STEW_GATHERING = food$default(
      INSTANCE, "siracusa_stew_gathering", "叙拉古炖锅集会", "Siracusa Stew Gathering", 9, 1.1F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> YAN_WASTELAND_MEAT_STIR_FRY = food$default(
      INSTANCE, "yan_wasteland_meat_stir_fry", "炎国荒地菜肉酮", "Yan Wasteland Meat-and-Vegetable Extract", 9, 1.0F, false, false, 96, null
  );
  @NotNull
  private static final ItemEntry<Item> IBERIA_CHITIN_CLUSTER = food$default(
      INSTANCE, "iberia_chitin_cluster", "伊比利亚甲壳质聚块", "Iberia Chitin Cluster", 6, 0.9F, true, false, 64, null
  );

  private NationFoods() {
  }

  // $VF: synthetic method
  static ItemEntry food$default(
      NationFoods var0, String var1, String var2, String var3, int var4, float var5, boolean var6, boolean var7, int var8, Object var9
  ) {
    if ((var8 & 32) != 0) {
      var6 = false;
    }

    if ((var8 & 64) != 0) {
      var7 = false;
    }

    return var0.food(var1, var2, var3, var4, var5, var6, var7);
  }

  @NotNull
  public final ItemEntry<Item> getAEGIR_FRESH_SHELLCRAB_SASHIMI() {
    return AEGIR_FRESH_SHELLCRAB_SASHIMI;
  }

  @NotNull
  public final ItemEntry<Item> getBOLIVAR_SMOKED_CAPSULE() {
    return BOLIVAR_SMOKED_CAPSULE;
  }

  @NotNull
  public final ItemEntry<Item> getHIGASHI_NANO_KAPPO() {
    return HIGASHI_NANO_KAPPO;
  }

  @NotNull
  public final ItemEntry<Item> getDURIN_HONEY_SLUGPUDDING() {
    return DURIN_HONEY_SLUGPUDDING;
  }

  @NotNull
  public final ItemEntry<Item> getCOLUMBIA_ORIGINIUM_ROASTED_FOWL() {
    return COLUMBIA_ORIGINIUM_ROASTED_FOWL;
  }

  @NotNull
  public final ItemEntry<Item> getKAZIMIERZ_KNIGHT_SUPPLEMENT() {
    return KAZIMIERZ_KNIGHT_SUPPLEMENT;
  }

  @NotNull
  public final ItemEntry<Item> getKAZDEL_CARTILAGE_TACK() {
    return KAZDEL_CARTILAGE_TACK;
  }

  @NotNull
  public final ItemEntry<Item> getLATERANO_SACRED_TONE_SOUP() {
    return LATERANO_SACRED_TONE_SOUP;
  }

  @NotNull
  public final ItemEntry<Item> getLEITHANIEN_MUSICAL_ROAST_EXTRACT() {
    return LEITHANIEN_MUSICAL_ROAST_EXTRACT;
  }

  @NotNull
  public final ItemEntry<Item> getRIM_BILLITON_MINING_RATION() {
    return RIM_BILLITON_MINING_RATION;
  }

  @NotNull
  public final ItemEntry<Item> getMINOS_POETRY_GEL() {
    return MINOS_POETRY_GEL;
  }

  @NotNull
  public final ItemEntry<Item> getSARGON_GRASS_CHEESE_GEL() {
    return SARGON_GRASS_CHEESE_GEL;
  }

  @NotNull
  public final ItemEntry<Item> getSAMI_INSTANT_BONE_SOUP() {
    return SAMI_INSTANT_BONE_SOUP;
  }

  @NotNull
  public final ItemEntry<Item> getVICTORIA_CENTRAL_VALLEY_ROAST() {
    return VICTORIA_CENTRAL_VALLEY_ROAST;
  }

  @NotNull
  public final ItemEntry<Item> getURSUS_HAM_SUPPLEMENT() {
    return URSUS_HAM_SUPPLEMENT;
  }

  @NotNull
  public final ItemEntry<Item> getKJERAG_VALLEY_PIE() {
    return KJERAG_VALLEY_PIE;
  }

  @NotNull
  public final ItemEntry<Item> getSIRACUSA_STEW_GATHERING() {
    return SIRACUSA_STEW_GATHERING;
  }

  @NotNull
  public final ItemEntry<Item> getYAN_WASTELAND_MEAT_STIR_FRY() {
    return YAN_WASTELAND_MEAT_STIR_FRY;
  }

  @NotNull
  public final ItemEntry<Item> getIBERIA_CHITIN_CLUSTER() {
    return IBERIA_CHITIN_CLUSTER;
  }

  private final ItemEntry<Item> food(String path, String zhCn, String enUs, int nutrition, float saturation, boolean fast, boolean alwaysEdible) {
    Builder builder = new Builder().nutrition(nutrition).saturationModifier(saturation);
    if (fast) {
      builder.fast();
    }

    if (alwaysEdible) {
      builder.alwaysEdible();
    }

    ItemCatalog itemCatalog = Zinecraft.INSTANCE.getITEMS();
    Properties properties = new Properties().food(builder.build());
    return ItemCatalog.register$default(itemCatalog, path, zhCn, enUs, null, properties, false, 40, null).compost(0.65F);
  }
}
