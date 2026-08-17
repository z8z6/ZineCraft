package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.sound.ModSound;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;

public final class ModItem {
  public static final ModItem INSTANCE = new ModItem();
  public final DeferredItem<Item> ORIROCK = item("orirock", "源岩", Rarity.COMMON);
  public final DeferredItem<Item> ORIROCK_CUBE = item("orirock_cube", "固源岩", Rarity.UNCOMMON);
  public final DeferredItem<Item> ORIROCK_CLUSTER = item("orirock_cluster", "固源岩组", Rarity.RARE);
  public final DeferredItem<Item> ORIROCK_CONCENTRATION = item("orirock_concentration", "提纯源岩", Rarity.EPIC);
  public final DeferredItem<Item> ORIGINITE = item("originite", "源石", Rarity.RARE);
  public final DeferredItem<Item> PROTOCOL_ORIGINIUM = item("protocol_originium", "协议源石", Rarity.EPIC);
  public final DeferredItem<Item> ORIGINIUM_POWDER = item("originium_powder", "源石碎片", Rarity.UNCOMMON);
  public final DeferredItem<Item> GRINDSTONE = item("grindstone", "研磨石", Rarity.RARE);
  public final DeferredItem<Item> GRINDSTONE_PENTAHYDRATE = item("grindstone_pentahydrate", "五水研磨石", Rarity.EPIC);
  public final DeferredItem<Item> MANGANESE_ORE = item("manganese_ore", "轻锰矿", Rarity.RARE);
  public final DeferredItem<Item> MANGANESE_TRIHYDRATE = item("manganese_trihydrate", "三水锰矿", Rarity.EPIC);
  public final DeferredItem<Item> RMA70_12 = item("rma70_12", "RMA70-12", Rarity.RARE);
  public final DeferredItem<Item> RMA70_24 = item("rma70_24", "RMA70-24", Rarity.EPIC);
  public final DeferredItem<Item> CRYSTAL_ELEMENT = item("crystal_element", "晶体元件", Rarity.RARE);
  public final DeferredItem<Item> CRYSTAL_GROUP = item("crystal_group", "晶体电路", Rarity.EPIC);
  public final DeferredItem<Item> CRYSTALLINE_CIRCUIT = item("crystalline_circuit", "晶体电子单元", Rarity.EPIC);
  public final DeferredItem<Item> ESTER_RAW = item("ester_raw", "酯原料", Rarity.COMMON);
  public final DeferredItem<Item> POLYESTER = item("polyester", "聚酸酯", Rarity.UNCOMMON);
  public final DeferredItem<Item> POLYESTER_GROUP = item("polyester_group", "聚酸酯组", Rarity.RARE);
  public final DeferredItem<Item> POLYESTER_BLOCK = item("polyester_block", "聚酸酯块", Rarity.EPIC);
  public final DeferredItem<Item> SUGAR_SUBSTITUTE = item("sugar_substitute", "代糖", Rarity.COMMON);
  public final DeferredItem<Item> SUGAR = item("sugar", "糖", Rarity.UNCOMMON);
  public final DeferredItem<Item> SUGAR_GROUP = item("sugar_group", "糖组", Rarity.RARE);
  public final DeferredItem<Item> SUGAR_POLYMER = item("sugar_polymer", "糖聚块", Rarity.EPIC);
  public final DeferredItem<Item> COMBINED_CUTTING_FLUID = item("compound_cutting_fluid", "化合切削液", Rarity.RARE);
  public final DeferredItem<Item> CUTTING_FLUID_SOLUTION = item("cutting_fluid_solution", "切削原液", Rarity.EPIC);
  public final DeferredItem<Item> SEMI_SYNTHETIC_SOLVENT = item("semi_synthetic_solvent", "半自然溶剂", Rarity.RARE);
  public final DeferredItem<Item> REFINED_SOLVENT = item("refined_solvent", "精练溶剂", Rarity.EPIC);
  public final DeferredItem<Item> DAMAGED_DEVICE = item("damaged_device", "破损装置", Rarity.COMMON);
  public final DeferredItem<Item> DEVICE_CORE = item("device", "装置", Rarity.UNCOMMON);
  public final DeferredItem<Item> DEVICE_GROUP = item("integrated_device", "全新装置", Rarity.RARE);
  public final DeferredItem<Item> OPTIMIZED_DEVICE = item("optimized_device", "改良装置", Rarity.EPIC);
  public final DeferredItem<Item> BIPOLAR_NANOSHEET = item("bipolar_nanosheet", "双极纳米片", Rarity.EPIC);
  public final DeferredItem<Item> D32_STEEL = item("d32_steel", "D32钢", Rarity.EPIC);
  public final DeferredItem<Item> ORIRON_SHARD = item("oriron_shard", "异铁碎片", Rarity.COMMON);
  public final DeferredItem<Item> ORIRON = item("oriron", "异铁", Rarity.UNCOMMON);
  public final DeferredItem<Item> ORIRON_GROUP = item("oriron_group", "异铁组", Rarity.RARE);
  public final DeferredItem<Item> ORIRON_CLUSTER = item("oriron_cluster", "异铁块", Rarity.EPIC);
  public final DeferredItem<Item> DIKETONE = item("diketon", "双酮", Rarity.COMMON);
  public final DeferredItem<Item> AKETON = item("aketone", "酮凝集", Rarity.UNCOMMON);
  public final DeferredItem<Item> POLYKETON = item("polyketon", "酮凝集组", Rarity.RARE);
  public final DeferredItem<Item> KETON_COLLOID = item("keton_colloid", "酮阵列", Rarity.EPIC);
  public final DeferredItem<Item> POLYMER_AGENT = item("polymer_agent", "聚合剂", Rarity.EPIC);
  public final DeferredItem<Item> LOXIC_KOHL = item("loxic_kohl", "炽合金", Rarity.RARE);
  public final DeferredItem<Item> INCANDESCENT_ALLOY = item("incandescent_alloy", "炽合金块", Rarity.EPIC);
  public final DeferredItem<Item> GEL = item("gel", "凝胶", Rarity.RARE);
  public final DeferredItem<Item> COAGULATING_GEL = item("coagulating_gel", "聚合凝胶", Rarity.EPIC);
  public final DeferredItem<Item> TWISTED_ALCOHOL = item("twisted_alcohol", "扭转醇", Rarity.RARE);
  public final DeferredItem<Item> WHITE_HORSE_KOHL = item("white_horse_kohl", "白马醇", Rarity.EPIC);
  public final DeferredItem<Item> SKILL_SUMMARY_1 = item("skill_summary_1", "技巧概要·卷1", Rarity.COMMON);
  public final DeferredItem<Item> SKILL_SUMMARY_2 = item("skill_summary_2", "技巧概要·卷2", Rarity.COMMON);
  public final DeferredItem<Item> SKILL_SUMMARY_3 = item("skill_summary_3", "技巧概要·卷3", Rarity.COMMON);
  public final DeferredItem<Item> HEADHUNT_TICKET = item("headhunt_ticket", "寻访凭证", Rarity.COMMON);
  public final DeferredItem<Item> LMD = item("lmd", "龙门币", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_VANGUARD = item("chip_vanguard", "先锋芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_VANGUARD_GROUP = item("chip_vanguard_group", "先锋芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_GUARD = item("chip_guard", "近卫芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_GUARD_GROUP = item("chip_guard_group", "近卫芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SNIPER = item("chip_sniper", "狙击芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SNIPER_GROUP = item("chip_sniper_group", "狙击芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_CASTER = item("chip_caster", "术士芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_CASTER_GROUP = item("chip_caster_group", "术士芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SPECIAL = item("chip_special", "特种芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SPECIAL_GROUP = item("chip_special_group", "特种芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SUPPORT = item("chip_support", "辅助芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_SUPPORT_GROUP = item("chip_support_group", "辅助芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_DEFENDER = item("chip_defender", "重装芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_DEFENDER_GROUP = item("chip_defender_group", "重装芯片组", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_MEDIC = item("chip_medic", "医疗芯片", Rarity.COMMON);
  public final DeferredItem<Item> CHIP_MEDIC_GROUP = item("chip_medic_group", "医疗芯片组", Rarity.COMMON);
  public final DeferredItem<Item> MAGIC_DUST = Zinecraft.ITEMS.builder(
      "magic_dust", "魔法粉尘",
      () -> new Item(
          new Properties()
              .food(
                  new Builder()
                      .nutrition(6)
                      .saturationModifier(0.8F)
                      .alwaysEdible()
                      .fast()
                      .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 2), 1.0F)
                      .build()
              )))
      .enUs("Magic Dust")
      .fuel(600)
      .compost(0.3F)
      .build();
  public final DeferredItem<Item> PICTURES_OF_THE_PAST = ModSound.AMBIENT_PICTURES_OF_THE_PAST.item;
  public final DeferredItem<Item> RANDOM_GODS = ModSound.AMBIENT_RANDOM_GODS.item;
  public final DeferredItem<Item> STRANGER_THINK = ModSound.AMBIENT_STRANGER_THINK.item;

  // 简易注册物品的辅助函数
  private static DeferredItem<Item> item(
      String path,         // 资源路径
      String zhCn,         // 中文翻译
      Rarity rarity        // 物品稀有度
  ) {
    String enUs = TranslationNames.toDisplayName(path);
    return Zinecraft.ITEMS.builder(
        path,
        zhCn,
        () -> new Item(new Properties().rarity(rarity))
    ).enUs(enUs).build();
  }

}
