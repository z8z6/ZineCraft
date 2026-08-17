package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.item.CreativeTabCatalog;
import com.cxxcxx.zinecraft.api.item.CreativeTabEntry;
import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.sound.ModSound;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class ModItem {
  @NotNull
  public static final ModItem INSTANCE = new ModItem();
  @NotNull
  public static final ItemEntry<Item> EXAMPLE_ITEM = itemWithDefaults(INSTANCE, "example_item", "示例物品", null, 4, null);
  @NotNull
  public static final ItemEntry<Item> ORIROCK = itemWithDefaults(INSTANCE, "orirock", "源岩", null, 4, null);
  @NotNull
  public static final ItemEntry<Item> ORIROCK_CUBE = INSTANCE.ItemWrap("orirock_cube", "固源岩");
  @NotNull
  public static final ItemEntry<Item> ORIROCK_CLUSTER = INSTANCE.ItemWrap("orirock_cluster", "固源岩组");
  @NotNull
  public static final ItemEntry<Item> ORIROCK_CONCENTRATION = INSTANCE.ItemWrap("orirock_concentration", "提纯源岩");
  @NotNull
  public static final ItemEntry<Item> ORIGINITE = INSTANCE.ItemWrap("originite", "源石");
  @NotNull
  public static final ItemEntry<Item> PROTOCOL_ORIGINIUM = INSTANCE.ItemWrap("protocol_originium", "协议源石");
  @NotNull
  public static final ItemEntry<Item> ORIGINIUM_POWDER = INSTANCE.ItemWrap("originium_powder", "源石碎片");
  @NotNull
  public static final ItemEntry<Item> GRINDSTONE = INSTANCE.ItemWrap("grindstone", "研磨石");
  @NotNull
  public static final ItemEntry<Item> GRINDSTONE_PENTAHYDRATE = INSTANCE.ItemWrap("grindstone_pentahydrate", "五水研磨石");
  @NotNull
  public static final ItemEntry<Item> MANGANESE_ORE = INSTANCE.ItemWrap("manganese_ore", "轻锰矿");
  @NotNull
  public static final ItemEntry<Item> MANGANESE_TRIHYDRATE = INSTANCE.ItemWrap("manganese_trihydrate", "三水锰矿");
  @NotNull
  public static final ItemEntry<Item> RMA70_12 = INSTANCE.ItemWrap("rma70_12", "RMA70-12");
  @NotNull
  public static final ItemEntry<Item> RMA70_24 = INSTANCE.ItemWrap("rma70_24", "RMA70-24");
  @NotNull
  public static final ItemEntry<Item> CRYSTAL_ELEMENT = INSTANCE.ItemWrap("crystal_element", "晶体元件");
  @NotNull
  public static final ItemEntry<Item> CRYSTAL_GROUP = INSTANCE.ItemWrap("crystal_group", "晶体电路");
  @NotNull
  public static final ItemEntry<Item> CRYSTALLINE_CIRCUIT = INSTANCE.ItemWrap("crystalline_circuit", "晶体电子单元");
  @NotNull
  public static final ItemEntry<Item> ESTER_RAW = INSTANCE.ItemWrap("ester_raw", "酯原料");
  @NotNull
  public static final ItemEntry<Item> POLYESTER = INSTANCE.ItemWrap("polyester", "聚酸酯");
  @NotNull
  public static final ItemEntry<Item> POLYESTER_GROUP = INSTANCE.ItemWrap("polyester_group", "聚酸酯组");
  @NotNull
  public static final ItemEntry<Item> POLYESTER_BLOCK = INSTANCE.ItemWrap("polyester_block", "聚酸酯块");
  @NotNull
  public static final ItemEntry<Item> SUGAR_SUBSTITUTE = INSTANCE.ItemWrap("sugar_substitute", "代糖");
  @NotNull
  public static final ItemEntry<Item> SUGAR = INSTANCE.ItemWrap("sugar", "糖");
  @NotNull
  public static final ItemEntry<Item> SUGAR_GROUP = INSTANCE.ItemWrap("sugar_group", "糖组");
  @NotNull
  public static final ItemEntry<Item> SUGAR_POLYMER = INSTANCE.ItemWrap("sugar_polymer", "糖聚块");
  @NotNull
  public static final ItemEntry<Item> COMBINED_CUTTING_FLUID = INSTANCE.ItemWrap("compound_cutting_fluid", "化合切削液");
  @NotNull
  public static final ItemEntry<Item> CUTTING_FLUID_SOLUTION = INSTANCE.ItemWrap("cutting_fluid_solution", "切削原液");
  @NotNull
  public static final ItemEntry<Item> SEMI_SYNTHETIC_SOLVENT = INSTANCE.ItemWrap("semi_synthetic_solvent", "半自然溶剂");
  @NotNull
  public static final ItemEntry<Item> REFINED_SOLVENT = INSTANCE.ItemWrap("refined_solvent", "精练溶剂");
  @NotNull
  public static final ItemEntry<Item> DAMAGED_DEVICE = INSTANCE.ItemWrap("damaged_device", "破损装置");
  @NotNull
  public static final ItemEntry<Item> DEVICE_CORE = INSTANCE.ItemWrap("device", "装置");
  @NotNull
  public static final ItemEntry<Item> DEVICE_GROUP = INSTANCE.ItemWrap("integrated_device", "全新装置");
  @NotNull
  public static final ItemEntry<Item> OPTIMIZED_DEVICE = INSTANCE.ItemWrap("optimized_device", "改良装置");
  @NotNull
  public static final ItemEntry<Item> BIPOLAR_NANOSHEET = INSTANCE.ItemWrap("bipolar_nanosheet", "双极纳米片");
  @NotNull
  public static final ItemEntry<Item> D32_STEEL = INSTANCE.ItemWrap("d32_steel", "D32钢");
  @NotNull
  public static final ItemEntry<Item> ORIRON_SHARD = INSTANCE.ItemWrap("oriron_shard", "异铁碎片");
  @NotNull
  public static final ItemEntry<Item> ORIRON = INSTANCE.ItemWrap("oriron", "异铁");
  @NotNull
  public static final ItemEntry<Item> ORIRON_GROUP = INSTANCE.ItemWrap("oriron_group", "异铁组");
  @NotNull
  public static final ItemEntry<Item> ORIRON_CLUSTER = INSTANCE.ItemWrap("oriron_cluster", "异铁块");
  @NotNull
  public static final ItemEntry<Item> DIKETONE = INSTANCE.ItemWrap("diketon", "双酮");
  @NotNull
  public static final ItemEntry<Item> AKETON = INSTANCE.ItemWrap("aketone", "酮凝集");
  @NotNull
  public static final ItemEntry<Item> POLYKETON = INSTANCE.ItemWrap("polyketon", "酮凝集组");
  @NotNull
  public static final ItemEntry<Item> KETON_COLLOID = INSTANCE.ItemWrap("keton_colloid", "酮阵列");
  @NotNull
  public static final ItemEntry<Item> POLYMER_AGENT = INSTANCE.ItemWrap("polymer_agent", "聚合剂");
  @NotNull
  public static final ItemEntry<Item> LOXIC_KOHL = INSTANCE.ItemWrap("loxic_kohl", "炽合金");
  @NotNull
  public static final ItemEntry<Item> INCANDESCENT_ALLOY = INSTANCE.ItemWrap("incandescent_alloy", "炽合金块");
  @NotNull
  public static final ItemEntry<Item> GEL = INSTANCE.ItemWrap("gel", "凝胶");
  @NotNull
  public static final ItemEntry<Item> COAGULATING_GEL = INSTANCE.ItemWrap("coagulating_gel", "聚合凝胶");
  @NotNull
  public static final ItemEntry<Item> TWISTED_ALCOHOL = INSTANCE.ItemWrap("twisted_alcohol", "扭转醇");
  @NotNull
  public static final ItemEntry<Item> WHITE_HORSE_KOHL = INSTANCE.ItemWrap("white_horse_kohl", "白马醇");
  @NotNull
  public static final ItemEntry<Item> SKILL_SUMMARY_1 = INSTANCE.ItemWrap("skill_summary_1", "技巧概要·卷1");
  @NotNull
  public static final ItemEntry<Item> SKILL_SUMMARY_2 = INSTANCE.ItemWrap("skill_summary_2", "技巧概要·卷2");
  @NotNull
  public static final ItemEntry<Item> SKILL_SUMMARY_3 = INSTANCE.ItemWrap("skill_summary_3", "技巧概要·卷3");
  @NotNull
  public static final ItemEntry<Item> HEADHUNT_TICKET = INSTANCE.ItemWrap("headhunt_ticket", "寻访凭证");
  @NotNull
  public static final ItemEntry<Item> LMD = INSTANCE.ItemWrap("lmd", "龙门币");
  @NotNull
  public static final ItemEntry<Item> CHIP_VANGUARD = INSTANCE.ItemWrap("chip_vanguard", "先锋芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_VANGUARD_GROUP = INSTANCE.ItemWrap("chip_vanguard_group", "先锋芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_GUARD = INSTANCE.ItemWrap("chip_guard", "近卫芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_GUARD_GROUP = INSTANCE.ItemWrap("chip_guard_group", "近卫芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_SNIPER = INSTANCE.ItemWrap("chip_sniper", "狙击芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_SNIPER_GROUP = INSTANCE.ItemWrap("chip_sniper_group", "狙击芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_CASTER = INSTANCE.ItemWrap("chip_caster", "术士芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_CASTER_GROUP = INSTANCE.ItemWrap("chip_caster_group", "术士芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_SPECIAL = INSTANCE.ItemWrap("chip_special", "特种芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_SPECIAL_GROUP = INSTANCE.ItemWrap("chip_special_group", "特种芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_SUPPORT = INSTANCE.ItemWrap("chip_support", "辅助芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_SUPPORT_GROUP = INSTANCE.ItemWrap("chip_support_group", "辅助芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_DEFENDER = INSTANCE.ItemWrap("chip_defender", "重装芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_DEFENDER_GROUP = INSTANCE.ItemWrap("chip_defender_group", "重装芯片组");
  @NotNull
  public static final ItemEntry<Item> CHIP_MEDIC = INSTANCE.ItemWrap("chip_medic", "医疗芯片");
  @NotNull
  public static final ItemEntry<Item> CHIP_MEDIC_GROUP = INSTANCE.ItemWrap("chip_medic_group", "医疗芯片组");
  @NotNull
  public static final ItemEntry<Item> MAGIC_DUST = ItemWrapWithDefaults(
      INSTANCE,
      "magic_dust",
      "魔法粉尘",
      () -> new Item(
          new Properties()
              .food(
                  new Builder()
                      .nutrition(6)
                      .saturationModifier(0.8F)
                      .alwaysEdible()
                      .fast()
                      .effect(new MobEffectInstance(MobEffects.JUMP, 600, 2), 1.0F)
                      .build()
              )
      ),
      null,
      null,
      24,
      null
  )
      .fuel(600)
      .compost(0.3F);
  @NotNull
  public static final ItemEntry<Item> PICTURES_OF_THE_PAST = ModSound.AMBIENT_PICTURES_OF_THE_PAST.getItemEntry();
  @NotNull
  public static final ItemEntry<Item> RANDOM_GODS = ModSound.AMBIENT_RANDOM_GODS.getItemEntry();
  @NotNull
  public static final ItemEntry<Item> STRANGER_THINK = ModSound.AMBIENT_STRANGER_THINK.getItemEntry();
  @NotNull
  private static final CreativeTabEntry ITEM_GROUP = CreativeTabCatalog.registerWithDefaults(
      Zinecraft.CREATIVE_TABS, "item", "Zinecraft", "Zinecraft", ModItem::ITEM_GROUPHelper0, false, 16, null
  );
  @NotNull
  public static final ResourceKey<CreativeModeTab> ZINECRAFT_CORE_ITEM_GROUP_KEY = ITEM_GROUP.getKey();
  @NotNull
  public static final CreativeModeTab ZINECRAFT_CORE_ITEM_GROUP = ITEM_GROUP.getTab();

  private ModItem() {
  }

  static ItemEntry itemWithDefaults(ModItem var0, String var1, String var2, String var3, int var4, Object var5) {
    if ((var4 & 4) != 0) {
      var3 = var0.toDisplayName(var1);
    }

    return var0.item(var1, var2, var3);
  }

  static ItemEntry ItemWrapWithDefaults(ModItem var0, String var1, String var2, Supplier<? extends Item> var3, ModelTemplate var4, String var5, int var6, Object var7) {
    if ((var6 & 8) != 0) {
      ModelTemplate modelTemplate = ModelTemplates.FLAT_ITEM;
      var4 = modelTemplate;
    }

    if ((var6 & 16) != 0) {
      var5 = var0.toDisplayName(var1);
    }

    return var0.ItemWrap(var1, var2, var3, var4, var5);
  }

  private static final Item ItemWrapHelper0(Item _item) {
    return _item;
  }

  private static final ItemStack ITEM_GROUPHelper0() {
    return new ItemStack((ItemLike) D32_STEEL.getItem());
  }

  private static final CharSequence toDisplayNameHelper0(String word) {
    String string = word;
    String string2;
    if (string.length() > 0) {
      StringBuilder stringBuilder2 = new StringBuilder();
      char it = string.charAt(0);
      StringBuilder stringBuilder = stringBuilder2;
      int i = 0;
      StringBuilder stringBuilder1 = stringBuilder.append(Character.isLowerCase(it) ? Character.toTitleCase(it) : it);
      String string1 = string;
      byte b = 1;
      String string3 = string1.substring(b);
      string2 = stringBuilder1.append(string3).toString();
    } else {
      string2 = string;
    }

    return string2;
  }

  private final ItemEntry<Item> item(String path, String zhCn, String enUs) {
    return ItemCatalog.registerWithDefaults(
        Zinecraft.ITEMS,
        path,
        zhCn,
        enUs,
        null,
        CraftingMaterialRarities.properties(path),
        false,
        40,
        null
    );
  }

  private final ItemEntry<Item> ItemWrap(String path, String zhCn) {
    return itemWithDefaults(this, path, zhCn, null, 4, null);
  }

  private final <T extends Item> ItemEntry<T> ItemWrap(String path, String zhCn, Supplier<T> item, ModelTemplate model, String enUs) {
    return Zinecraft.ITEMS.register(path, zhCn, enUs, model, true, item::get);
  }

  private final String toDisplayName(String _this_toDisplayName) {
    return com.cxxcxx.zinecraft.api.localization.TranslationNames.toDisplayName(_this_toDisplayName);
  }
}
