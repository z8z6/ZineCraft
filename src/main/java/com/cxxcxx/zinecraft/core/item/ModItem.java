package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public final class ModItem {
  public static final ItemBuilder<Item> ORIROCK = item("orirock", "源岩", Rarity.COMMON, null);
  public static final ItemBuilder<Item> ORIROCK_CUBE = item("orirock_cube", "固源岩", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> ORIROCK_CLUSTER = item("orirock_cluster", "固源岩组", Rarity.RARE);
  public static final ItemBuilder<Item> ORIROCK_CONCENTRATION = item("orirock_concentration", "提纯源岩", Rarity.EPIC);
  public static final ItemBuilder<Item> ORIGINITE = item("originite", "源石", Rarity.RARE);
  public static final ItemBuilder<Item> PROTOCOL_ORIGINIUM = item("protocol_originium", "协议源石", Rarity.EPIC);
  public static final ItemBuilder<Item> ORIGINIUM_POWDER = item("originium_powder", "源石碎片", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> GRINDSTONE = item("grindstone", "研磨石", Rarity.RARE);
  public static final ItemBuilder<Item> GRINDSTONE_PENTAHYDRATE = item("grindstone_pentahydrate", "五水研磨石", Rarity.EPIC);
  public static final ItemBuilder<Item> MANGANESE_ORE = item("manganese_ore", "轻锰矿", Rarity.RARE);
  public static final ItemBuilder<Item> MANGANESE_TRIHYDRATE = item("manganese_trihydrate", "三水锰矿", Rarity.EPIC);
  public static final ItemBuilder<Item> RMA70_12 = item("rma70_12", "RMA70-12", Rarity.RARE);
  public static final ItemBuilder<Item> RMA70_24 = item("rma70_24", "RMA70-24", Rarity.EPIC);
  public static final ItemBuilder<Item> CRYSTAL_ELEMENT = item("crystal_element", "晶体元件", Rarity.RARE);
  public static final ItemBuilder<Item> CRYSTAL_GROUP = item("crystal_group", "晶体电路", Rarity.EPIC);
  public static final ItemBuilder<Item> CRYSTALLINE_CIRCUIT = item("crystalline_circuit", "晶体电子单元", Rarity.EPIC);
  public static final ItemBuilder<Item> ESTER_RAW = item("ester_raw", "酯原料");
  public static final ItemBuilder<Item> POLYESTER = item("polyester", "聚酸酯", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> POLYESTER_GROUP = item("polyester_group", "聚酸酯组", Rarity.RARE);
  public static final ItemBuilder<Item> POLYESTER_BLOCK = item("polyester_block", "聚酸酯块", Rarity.EPIC);
  public static final ItemBuilder<Item> SUGAR_SUBSTITUTE = item("sugar_substitute", "代糖");
  public static final ItemBuilder<Item> SUGAR = item("sugar", "糖", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> SUGAR_GROUP = item("sugar_group", "糖组", Rarity.RARE);
  public static final ItemBuilder<Item> SUGAR_POLYMER = item("sugar_polymer", "糖聚块", Rarity.EPIC);
  public static final ItemBuilder<Item> COMBINED_CUTTING_FLUID = item("compound_cutting_fluid", "化合切削液", Rarity.RARE);
  public static final ItemBuilder<Item> CUTTING_FLUID_SOLUTION = item("cutting_fluid_solution", "切削原液", Rarity.EPIC);
  public static final ItemBuilder<Item> SEMI_SYNTHETIC_SOLVENT = item("semi_synthetic_solvent", "半自然溶剂", Rarity.RARE);
  public static final ItemBuilder<Item> REFINED_SOLVENT = item("refined_solvent", "精练溶剂", Rarity.EPIC);
  public static final ItemBuilder<Item> DAMAGED_DEVICE = item("damaged_device", "破损装置");
  public static final ItemBuilder<Item> DEVICE_CORE = item("device", "装置", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> DEVICE_GROUP = item("integrated_device", "全新装置", Rarity.RARE);
  public static final ItemBuilder<Item> OPTIMIZED_DEVICE = item("optimized_device", "改良装置", Rarity.EPIC);
  public static final ItemBuilder<Item> BIPOLAR_NANOSHEET = item("bipolar_nanosheet", "双极纳米片", Rarity.EPIC);
  public static final ItemBuilder<Item> D32_STEEL = item("d32_steel", "D32钢", Rarity.EPIC);
  public static final ItemBuilder<Item> ORIRON_SHARD = item("oriron_shard", "异铁碎片");
  public static final ItemBuilder<Item> ORIRON = item("oriron", "异铁", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> ORIRON_GROUP = item("oriron_group", "异铁组", Rarity.RARE);
  public static final ItemBuilder<Item> ORIRON_CLUSTER = item("oriron_cluster", "异铁块", Rarity.EPIC);
  public static final ItemBuilder<Item> DIKETONE = item("diketon", "双酮");
  public static final ItemBuilder<Item> AKETON = item("aketone", "酮凝集", Rarity.UNCOMMON);
  public static final ItemBuilder<Item> POLYKETON = item("polyketon", "酮凝集组", Rarity.RARE);
  public static final ItemBuilder<Item> KETON_COLLOID = item("keton_colloid", "酮阵列", Rarity.EPIC);
  public static final ItemBuilder<Item> POLYMER_AGENT = item("polymer_agent", "聚合剂", Rarity.EPIC);
  public static final ItemBuilder<Item> LOXIC_KOHL = item("loxic_kohl", "炽合金", Rarity.RARE);
  public static final ItemBuilder<Item> INCANDESCENT_ALLOY = item("incandescent_alloy", "炽合金块", Rarity.EPIC);
  public static final ItemBuilder<Item> GEL = item("gel", "凝胶", Rarity.RARE);
  public static final ItemBuilder<Item> COAGULATING_GEL = item("coagulating_gel", "聚合凝胶", Rarity.EPIC);
  public static final ItemBuilder<Item> TWISTED_ALCOHOL = item("twisted_alcohol", "扭转醇", Rarity.RARE);
  public static final ItemBuilder<Item> WHITE_HORSE_KOHL = item("white_horse_kohl", "白马醇", Rarity.EPIC);
  public static final ItemBuilder<Item> SKILL_SUMMARY_1 = item("skill_summary_1", "技巧概要·卷1");
  public static final ItemBuilder<Item> SKILL_SUMMARY_2 = item("skill_summary_2", "技巧概要·卷2");
  public static final ItemBuilder<Item> SKILL_SUMMARY_3 = item("skill_summary_3", "技巧概要·卷3");
  public static final ItemBuilder<Item> HEADHUNT_TICKET = item("headhunt_ticket", "寻访凭证");
  public static final ItemBuilder<Item> LMD = item("lmd", "龙门币");
  public static final ItemBuilder<Item> CHIP_VANGUARD = item("chip_vanguard", "先锋芯片");
  public static final ItemBuilder<Item> CHIP_VANGUARD_GROUP = item("chip_vanguard_group", "先锋芯片组");
  public static final ItemBuilder<Item> CHIP_GUARD = item("chip_guard", "近卫芯片");
  public static final ItemBuilder<Item> CHIP_GUARD_GROUP = item("chip_guard_group", "近卫芯片组");
  public static final ItemBuilder<Item> CHIP_SNIPER = item("chip_sniper", "狙击芯片");
  public static final ItemBuilder<Item> CHIP_SNIPER_GROUP = item("chip_sniper_group", "狙击芯片组");
  public static final ItemBuilder<Item> CHIP_CASTER = item("chip_caster", "术士芯片");
  public static final ItemBuilder<Item> CHIP_CASTER_GROUP = item("chip_caster_group", "术士芯片组");
  public static final ItemBuilder<Item> CHIP_SPECIAL = item("chip_special", "特种芯片");
  public static final ItemBuilder<Item> CHIP_SPECIAL_GROUP = item("chip_special_group", "特种芯片组");
  public static final ItemBuilder<Item> CHIP_SUPPORT = item("chip_support", "辅助芯片");
  public static final ItemBuilder<Item> CHIP_SUPPORT_GROUP = item("chip_support_group", "辅助芯片组");
  public static final ItemBuilder<Item> CHIP_DEFENDER = item("chip_defender", "重装芯片");
  public static final ItemBuilder<Item> CHIP_DEFENDER_GROUP = item("chip_defender_group", "重装芯片组");
  public static final ItemBuilder<Item> CHIP_MEDIC = item("chip_medic", "医疗芯片");
  public static final ItemBuilder<Item> CHIP_MEDIC_GROUP = item("chip_medic_group", "医疗芯片组");

  public static final ItemBuilder<Item> AEGIR_FRESH_SHELLCRAB_SASHIMI = food(
      "aegir_fresh_shellcrab_sashimi", "阿戈尔鲜切蟹生", 6, 0.7f);

  public static final ItemBuilder<Item> BOLIVAR_SMOKED_CAPSULE = food(
      "bolivar_smoked_capsule", "玻利瓦尔熏烤胶囊", 7, 0.8f);

  public static final ItemBuilder<Item> HIGASHI_NANO_KAPPO = food(
      "higashi_nano_kappo", "东国纳米割烹", 8, 0.9f);

  public static final ItemBuilder<Item> DURIN_HONEY_SLUGPUDDING = food(
      "durin_honey_slugpudding",
      "杜林蜜味源石虫布丁", 5, 0.6f);

  public static final ItemBuilder<Item> COLUMBIA_ORIGINIUM_ROASTED_FOWL = food(
      "columbia_originium_roasted_fowl",
      "哥伦比亚炮灰羽", 6, 0.7f);

  public static final ItemBuilder<Item> KAZIMIERZ_KNIGHT_SUPPLEMENT = food(
      "kazimierz_knight_supplement",
      "卡西米尔骑士补充剂", 6, 0.7f);

  public static final ItemBuilder<Item> KAZDEL_CARTILAGE_TACK = food(
      "kazdel_cartilage_tack",
      "卡兹戴尔软骨饼干", 6, 0.7f);

  public static final ItemBuilder<Item> LATERANO_SACRED_TONE_SOUP = food(
      "laterano_sacred_tone_soup",
      "拉特兰圣音汤醇", 6, 0.7f);

  public static final ItemBuilder<Item> LEITHANIEN_MUSICAL_ROAST_EXTRACT = food(
      "leithanien_musical_roast_extract",
      "莱塔尼亚音乐速烤萃取酯", 6, 0.7f);

  public static final ItemBuilder<Item> RIM_BILLITON_MINING_RATION = food(
      "rim_billiton_mining_ration",
      "雷姆必拓矿区应急餐", 6, 0.7f);

  public static final ItemBuilder<Item> MINOS_POETRY_GEL = food(
      "minos_poetry_gel",
      "米诺斯诗歌凝胶", 6, 0.7f);

  public static final ItemBuilder<Item> SARGON_GRASS_CHEESE_GEL = food(
      "sargon_grass_cheese_gel",
      "萨尔贡青草芝士凝胶", 6, 0.7f);

  public static final ItemBuilder<Item> SAMI_INSTANT_BONE_SOUP = food(
      "sami_instant_bone_soup",
      "萨米即食骨汤", 6, 0.7f);

  public static final ItemBuilder<Item> VICTORIA_CENTRAL_VALLEY_ROAST = food(
      "victoria_central_valley_roast",
      "维多利亚中央谷地混合烤肉", 6, 0.7f);

  public static final ItemBuilder<Item> URSUS_HAM_SUPPLEMENT = food(
      "ursus_ham_supplement",
      "乌萨斯火腿补剂", 6, 0.7f);

  public static final ItemBuilder<Item> KJERAG_VALLEY_PIE = food(
      "kjerag_valley_pie",
      "谢拉格谷地馅饼", 6, 0.7f);

  public static final ItemBuilder<Item> SIRACUSA_STEW_GATHERING = food(
      "siracusa_stew_gathering",
      "叙拉古炖锅集会", 6, 0.7f);

  public static final ItemBuilder<Item> YAN_WASTELAND_MEAT_STIR_FRY = food(
      "yan_wasteland_meat_stir_fry",
      "炎国荒地菜肉酮", 6, 0.7f);

  public static final ItemBuilder<Item> IBERIA_CHITIN_CLUSTER = food(
      "iberia_chitin_cluster",
      "伊比利亚甲壳质聚块", 6, 0.7f);

  private static ItemBuilder<Item> item(String path, String zhCn) {
    return item(path, zhCn, Rarity.COMMON);
  }

  private static ItemBuilder<Item> item(String path, String zhCn, Rarity rarity) {
    return item(path, zhCn, rarity, net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM);
  }

  private static ItemBuilder<Item> item(String path, String zhCn, Rarity rarity, ModelTemplate model) {
    return Zinecraft.ITEMS.builder(
        path, zhCn, () -> new Item(new Item.Properties().rarity(rarity)), model, true
    );
  }

  private static ItemBuilder<Item> food(String path, String zhCn, int nutrition, float saturation) {
    var food = new FoodProperties.Builder()
        .nutrition(nutrition)
        .saturationModifier(saturation)
        .build();
    return Zinecraft.ITEMS.builder(path, zhCn, () -> new Item(new Item.Properties().food(food)));
  }

  private ModItem() {
  }

  public static void bootstrap() {
  }

}
