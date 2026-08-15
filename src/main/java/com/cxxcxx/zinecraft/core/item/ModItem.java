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
  private static final ItemEntry<Item> EXAMPLE_ITEM = itemWithDefaults(INSTANCE, "example_item", "示例物品", null, 4, null);
  @NotNull
  private static final ItemEntry<Item> ORIROCK = itemWithDefaults(INSTANCE, "orirock", "源岩", null, 4, null);
  @NotNull
  private static final ItemEntry<Item> ORIROCK_CUBE = INSTANCE.ItemWrap("orirock_cube", "固源岩");
  @NotNull
  private static final ItemEntry<Item> ORIROCK_CLUSTER = INSTANCE.ItemWrap("orirock_cluster", "固源岩组");
  @NotNull
  private static final ItemEntry<Item> ORIROCK_CONCENTRATION = INSTANCE.ItemWrap("orirock_concentration", "提纯源岩");
  @NotNull
  private static final ItemEntry<Item> ORIGINITE = INSTANCE.ItemWrap("originite", "源石");
  @NotNull
  private static final ItemEntry<Item> PROTOCOL_ORIGINIUM = INSTANCE.ItemWrap("protocol_originium", "协议源石");
  @NotNull
  private static final ItemEntry<Item> ORIGINIUM_POWDER = INSTANCE.ItemWrap("originium_powder", "源石碎片");
  @NotNull
  private static final ItemEntry<Item> GRINDSTONE = INSTANCE.ItemWrap("grindstone", "研磨石");
  @NotNull
  private static final ItemEntry<Item> GRINDSTONE_PENTAHYDRATE = INSTANCE.ItemWrap("grindstone_pentahydrate", "五水研磨石");
  @NotNull
  private static final ItemEntry<Item> MANGANESE_ORE = INSTANCE.ItemWrap("manganese_ore", "轻锰矿");
  @NotNull
  private static final ItemEntry<Item> MANGANESE_TRIHYDRATE = INSTANCE.ItemWrap("manganese_trihydrate", "三水锰矿");
  @NotNull
  private static final ItemEntry<Item> RMA70_12 = INSTANCE.ItemWrap("rma70_12", "RMA70-12");
  @NotNull
  private static final ItemEntry<Item> RMA70_24 = INSTANCE.ItemWrap("rma70_24", "RMA70-24");
  @NotNull
  private static final ItemEntry<Item> CRYSTAL_ELEMENT = INSTANCE.ItemWrap("crystal_element", "晶体元件");
  @NotNull
  private static final ItemEntry<Item> CRYSTAL_GROUP = INSTANCE.ItemWrap("crystal_group", "晶体电路");
  @NotNull
  private static final ItemEntry<Item> CRYSTALLINE_CIRCUIT = INSTANCE.ItemWrap("crystalline_circuit", "晶体电子单元");
  @NotNull
  private static final ItemEntry<Item> ESTER_RAW = INSTANCE.ItemWrap("ester_raw", "酯原料");
  @NotNull
  private static final ItemEntry<Item> POLYESTER = INSTANCE.ItemWrap("polyester", "聚酸酯");
  @NotNull
  private static final ItemEntry<Item> POLYESTER_GROUP = INSTANCE.ItemWrap("polyester_group", "聚酸酯组");
  @NotNull
  private static final ItemEntry<Item> POLYESTER_BLOCK = INSTANCE.ItemWrap("polyester_block", "聚酸酯块");
  @NotNull
  private static final ItemEntry<Item> SUGAR_SUBSTITUTE = INSTANCE.ItemWrap("sugar_substitute", "代糖");
  @NotNull
  private static final ItemEntry<Item> SUGAR = INSTANCE.ItemWrap("sugar", "糖");
  @NotNull
  private static final ItemEntry<Item> SUGAR_GROUP = INSTANCE.ItemWrap("sugar_group", "糖组");
  @NotNull
  private static final ItemEntry<Item> SUGAR_POLYMER = INSTANCE.ItemWrap("sugar_polymer", "糖聚块");
  @NotNull
  private static final ItemEntry<Item> COMBINED_CUTTING_FLUID = INSTANCE.ItemWrap("compound_cutting_fluid", "化合切削液");
  @NotNull
  private static final ItemEntry<Item> CUTTING_FLUID_SOLUTION = INSTANCE.ItemWrap("cutting_fluid_solution", "切削原液");
  @NotNull
  private static final ItemEntry<Item> SEMI_SYNTHETIC_SOLVENT = INSTANCE.ItemWrap("semi_synthetic_solvent", "半自然溶剂");
  @NotNull
  private static final ItemEntry<Item> REFINED_SOLVENT = INSTANCE.ItemWrap("refined_solvent", "精练溶剂");
  @NotNull
  private static final ItemEntry<Item> DAMAGED_DEVICE = INSTANCE.ItemWrap("damaged_device", "破损装置");
  @NotNull
  private static final ItemEntry<Item> DEVICE_CORE = INSTANCE.ItemWrap("device", "装置");
  @NotNull
  private static final ItemEntry<Item> DEVICE_GROUP = INSTANCE.ItemWrap("integrated_device", "全新装置");
  @NotNull
  private static final ItemEntry<Item> OPTIMIZED_DEVICE = INSTANCE.ItemWrap("optimized_device", "改良装置");
  @NotNull
  private static final ItemEntry<Item> BIPOLAR_NANOSHEET = INSTANCE.ItemWrap("bipolar_nanosheet", "双极纳米片");
  @NotNull
  private static final ItemEntry<Item> D32_STEEL = INSTANCE.ItemWrap("d32_steel", "D32钢");
  @NotNull
  private static final CreativeTabEntry ITEM_GROUP = CreativeTabCatalog.registerWithDefaults(
      Zinecraft.INSTANCE.getCREATIVE_TABS(), "item", "Zinecraft", "Zinecraft", ModItem::ITEM_GROUPHelper0, false, 16, null
  );
  @NotNull
  private static final ResourceKey<CreativeModeTab> ZINECRAFT_CORE_ITEM_GROUP_KEY = ITEM_GROUP.getKey();
  @NotNull
  private static final CreativeModeTab ZINECRAFT_CORE_ITEM_GROUP = ITEM_GROUP.getTab();
  @NotNull
  private static final ItemEntry<Item> ORIRON_SHARD = INSTANCE.ItemWrap("oriron_shard", "异铁碎片");
  @NotNull
  private static final ItemEntry<Item> ORIRON = INSTANCE.ItemWrap("oriron", "异铁");
  @NotNull
  private static final ItemEntry<Item> ORIRON_GROUP = INSTANCE.ItemWrap("oriron_group", "异铁组");
  @NotNull
  private static final ItemEntry<Item> ORIRON_CLUSTER = INSTANCE.ItemWrap("oriron_cluster", "异铁块");
  @NotNull
  private static final ItemEntry<Item> DIKETONE = INSTANCE.ItemWrap("diketon", "双酮");
  @NotNull
  private static final ItemEntry<Item> AKETON = INSTANCE.ItemWrap("aketone", "酮凝集");
  @NotNull
  private static final ItemEntry<Item> POLYKETON = INSTANCE.ItemWrap("polyketon", "酮凝集组");
  @NotNull
  private static final ItemEntry<Item> KETON_COLLOID = INSTANCE.ItemWrap("keton_colloid", "酮阵列");
  @NotNull
  private static final ItemEntry<Item> POLYMER_AGENT = INSTANCE.ItemWrap("polymer_agent", "聚合剂");
  @NotNull
  private static final ItemEntry<Item> LOXIC_KOHL = INSTANCE.ItemWrap("loxic_kohl", "炽合金");
  @NotNull
  private static final ItemEntry<Item> INCANDESCENT_ALLOY = INSTANCE.ItemWrap("incandescent_alloy", "炽合金块");
  @NotNull
  private static final ItemEntry<Item> GEL = INSTANCE.ItemWrap("gel", "凝胶");
  @NotNull
  private static final ItemEntry<Item> COAGULATING_GEL = INSTANCE.ItemWrap("coagulating_gel", "聚合凝胶");
  @NotNull
  private static final ItemEntry<Item> TWISTED_ALCOHOL = INSTANCE.ItemWrap("twisted_alcohol", "扭转醇");
  @NotNull
  private static final ItemEntry<Item> WHITE_HORSE_KOHL = INSTANCE.ItemWrap("white_horse_kohl", "白马醇");
  @NotNull
  private static final ItemEntry<Item> SKILL_SUMMARY_1 = INSTANCE.ItemWrap("skill_summary_1", "技巧概要·卷1");
  @NotNull
  private static final ItemEntry<Item> SKILL_SUMMARY_2 = INSTANCE.ItemWrap("skill_summary_2", "技巧概要·卷2");
  @NotNull
  private static final ItemEntry<Item> SKILL_SUMMARY_3 = INSTANCE.ItemWrap("skill_summary_3", "技巧概要·卷3");
  @NotNull
  private static final ItemEntry<Item> HEADHUNT_TICKET = INSTANCE.ItemWrap("headhunt_ticket", "寻访凭证");
  @NotNull
  private static final ItemEntry<Item> LMD = INSTANCE.ItemWrap("lmd", "龙门币");
  @NotNull
  private static final ItemEntry<Item> CHIP_VANGUARD = INSTANCE.ItemWrap("chip_vanguard", "先锋芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_VANGUARD_GROUP = INSTANCE.ItemWrap("chip_vanguard_group", "先锋芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_GUARD = INSTANCE.ItemWrap("chip_guard", "近卫芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_GUARD_GROUP = INSTANCE.ItemWrap("chip_guard_group", "近卫芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_SNIPER = INSTANCE.ItemWrap("chip_sniper", "狙击芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_SNIPER_GROUP = INSTANCE.ItemWrap("chip_sniper_group", "狙击芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_CASTER = INSTANCE.ItemWrap("chip_caster", "术士芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_CASTER_GROUP = INSTANCE.ItemWrap("chip_caster_group", "术士芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_SPECIAL = INSTANCE.ItemWrap("chip_special", "特种芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_SPECIAL_GROUP = INSTANCE.ItemWrap("chip_special_group", "特种芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_SUPPORT = INSTANCE.ItemWrap("chip_support", "辅助芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_SUPPORT_GROUP = INSTANCE.ItemWrap("chip_support_group", "辅助芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_DEFENDER = INSTANCE.ItemWrap("chip_defender", "重装芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_DEFENDER_GROUP = INSTANCE.ItemWrap("chip_defender_group", "重装芯片组");
  @NotNull
  private static final ItemEntry<Item> CHIP_MEDIC = INSTANCE.ItemWrap("chip_medic", "医疗芯片");
  @NotNull
  private static final ItemEntry<Item> CHIP_MEDIC_GROUP = INSTANCE.ItemWrap("chip_medic_group", "医疗芯片组");
  @NotNull
  private static final ItemEntry<Item> MAGIC_DUST = ItemWrapWithDefaults(
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
  private static final ItemEntry<Item> PICTURES_OF_THE_PAST = ModSound.INSTANCE.getAMBIENT_PICTURES_OF_THE_PAST().getItemEntry();
  @NotNull
  private static final ItemEntry<Item> RANDOM_GODS = ModSound.INSTANCE.getAMBIENT_RANDOM_GODS().getItemEntry();
  @NotNull
  private static final ItemEntry<Item> STRANGER_THINK = ModSound.INSTANCE.getAMBIENT_STRANGER_THINK().getItemEntry();

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
    return ItemCatalog.registerWithDefaults(Zinecraft.INSTANCE.getITEMS(), path, zhCn, enUs, null, null, false, 56, null);
  }

  private final ItemEntry<Item> ItemWrap(String path, String zhCn) {
    return itemWithDefaults(this, path, zhCn, null, 4, null);
  }

  private final <T extends Item> ItemEntry<T> ItemWrap(String path, String zhCn, Supplier<T> item, ModelTemplate model, String enUs) {
    return Zinecraft.INSTANCE.getITEMS().register(path, zhCn, enUs, model, true, item::get);
  }

  @NotNull
  public final ItemEntry<Item> getEXAMPLE_ITEM() {
    return EXAMPLE_ITEM;
  }

  @NotNull
  public final ItemEntry<Item> getORIROCK() {
    return ORIROCK;
  }

  @NotNull
  public final ItemEntry<Item> getORIROCK_CUBE() {
    return ORIROCK_CUBE;
  }

  @NotNull
  public final ItemEntry<Item> getORIROCK_CLUSTER() {
    return ORIROCK_CLUSTER;
  }

  @NotNull
  public final ItemEntry<Item> getORIROCK_CONCENTRATION() {
    return ORIROCK_CONCENTRATION;
  }

  @NotNull
  public final ItemEntry<Item> getORIGINITE() {
    return ORIGINITE;
  }

  @NotNull
  public final ItemEntry<Item> getPROTOCOL_ORIGINIUM() {
    return PROTOCOL_ORIGINIUM;
  }

  @NotNull
  public final ItemEntry<Item> getORIGINIUM_POWDER() {
    return ORIGINIUM_POWDER;
  }

  @NotNull
  public final ItemEntry<Item> getGRINDSTONE() {
    return GRINDSTONE;
  }

  @NotNull
  public final ItemEntry<Item> getGRINDSTONE_PENTAHYDRATE() {
    return GRINDSTONE_PENTAHYDRATE;
  }

  @NotNull
  public final ItemEntry<Item> getMANGANESE_ORE() {
    return MANGANESE_ORE;
  }

  @NotNull
  public final ItemEntry<Item> getMANGANESE_TRIHYDRATE() {
    return MANGANESE_TRIHYDRATE;
  }

  @NotNull
  public final ItemEntry<Item> getRMA70_12() {
    return RMA70_12;
  }

  @NotNull
  public final ItemEntry<Item> getRMA70_24() {
    return RMA70_24;
  }

  @NotNull
  public final ItemEntry<Item> getCRYSTAL_ELEMENT() {
    return CRYSTAL_ELEMENT;
  }

  @NotNull
  public final ItemEntry<Item> getCRYSTAL_GROUP() {
    return CRYSTAL_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCRYSTALLINE_CIRCUIT() {
    return CRYSTALLINE_CIRCUIT;
  }

  @NotNull
  public final ItemEntry<Item> getESTER_RAW() {
    return ESTER_RAW;
  }

  @NotNull
  public final ItemEntry<Item> getPOLYESTER() {
    return POLYESTER;
  }

  @NotNull
  public final ItemEntry<Item> getPOLYESTER_GROUP() {
    return POLYESTER_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getPOLYESTER_BLOCK() {
    return POLYESTER_BLOCK;
  }

  @NotNull
  public final ItemEntry<Item> getSUGAR_SUBSTITUTE() {
    return SUGAR_SUBSTITUTE;
  }

  @NotNull
  public final ItemEntry<Item> getSUGAR() {
    return SUGAR;
  }

  @NotNull
  public final ItemEntry<Item> getSUGAR_GROUP() {
    return SUGAR_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getSUGAR_POLYMER() {
    return SUGAR_POLYMER;
  }

  @NotNull
  public final ItemEntry<Item> getCOMBINED_CUTTING_FLUID() {
    return COMBINED_CUTTING_FLUID;
  }

  @NotNull
  public final ItemEntry<Item> getCUTTING_FLUID_SOLUTION() {
    return CUTTING_FLUID_SOLUTION;
  }

  @NotNull
  public final ItemEntry<Item> getSEMI_SYNTHETIC_SOLVENT() {
    return SEMI_SYNTHETIC_SOLVENT;
  }

  @NotNull
  public final ItemEntry<Item> getREFINED_SOLVENT() {
    return REFINED_SOLVENT;
  }

  @NotNull
  public final ItemEntry<Item> getDAMAGED_DEVICE() {
    return DAMAGED_DEVICE;
  }

  @NotNull
  public final ItemEntry<Item> getDEVICE_CORE() {
    return DEVICE_CORE;
  }

  @NotNull
  public final ItemEntry<Item> getDEVICE_GROUP() {
    return DEVICE_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getOPTIMIZED_DEVICE() {
    return OPTIMIZED_DEVICE;
  }

  @NotNull
  public final ItemEntry<Item> getBIPOLAR_NANOSHEET() {
    return BIPOLAR_NANOSHEET;
  }

  @NotNull
  public final ItemEntry<Item> getD32_STEEL() {
    return D32_STEEL;
  }

  @NotNull
  public final ItemEntry<Item> getORIRON_SHARD() {
    return ORIRON_SHARD;
  }

  @NotNull
  public final ItemEntry<Item> getORIRON() {
    return ORIRON;
  }

  @NotNull
  public final ItemEntry<Item> getORIRON_GROUP() {
    return ORIRON_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getORIRON_CLUSTER() {
    return ORIRON_CLUSTER;
  }

  @NotNull
  public final ItemEntry<Item> getDIKETONE() {
    return DIKETONE;
  }

  @NotNull
  public final ItemEntry<Item> getAKETON() {
    return AKETON;
  }

  @NotNull
  public final ItemEntry<Item> getPOLYKETON() {
    return POLYKETON;
  }

  @NotNull
  public final ItemEntry<Item> getKETON_COLLOID() {
    return KETON_COLLOID;
  }

  @NotNull
  public final ItemEntry<Item> getPOLYMER_AGENT() {
    return POLYMER_AGENT;
  }

  @NotNull
  public final ItemEntry<Item> getLOXIC_KOHL() {
    return LOXIC_KOHL;
  }

  @NotNull
  public final ItemEntry<Item> getINCANDESCENT_ALLOY() {
    return INCANDESCENT_ALLOY;
  }

  @NotNull
  public final ItemEntry<Item> getGEL() {
    return GEL;
  }

  @NotNull
  public final ItemEntry<Item> getCOAGULATING_GEL() {
    return COAGULATING_GEL;
  }

  @NotNull
  public final ItemEntry<Item> getTWISTED_ALCOHOL() {
    return TWISTED_ALCOHOL;
  }

  @NotNull
  public final ItemEntry<Item> getWHITE_HORSE_KOHL() {
    return WHITE_HORSE_KOHL;
  }

  @NotNull
  public final ItemEntry<Item> getSKILL_SUMMARY_1() {
    return SKILL_SUMMARY_1;
  }

  @NotNull
  public final ItemEntry<Item> getSKILL_SUMMARY_2() {
    return SKILL_SUMMARY_2;
  }

  @NotNull
  public final ItemEntry<Item> getSKILL_SUMMARY_3() {
    return SKILL_SUMMARY_3;
  }

  @NotNull
  public final ItemEntry<Item> getHEADHUNT_TICKET() {
    return HEADHUNT_TICKET;
  }

  @NotNull
  public final ItemEntry<Item> getLMD() {
    return LMD;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_VANGUARD() {
    return CHIP_VANGUARD;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_VANGUARD_GROUP() {
    return CHIP_VANGUARD_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_GUARD() {
    return CHIP_GUARD;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_GUARD_GROUP() {
    return CHIP_GUARD_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SNIPER() {
    return CHIP_SNIPER;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SNIPER_GROUP() {
    return CHIP_SNIPER_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_CASTER() {
    return CHIP_CASTER;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_CASTER_GROUP() {
    return CHIP_CASTER_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SPECIAL() {
    return CHIP_SPECIAL;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SPECIAL_GROUP() {
    return CHIP_SPECIAL_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SUPPORT() {
    return CHIP_SUPPORT;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_SUPPORT_GROUP() {
    return CHIP_SUPPORT_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_DEFENDER() {
    return CHIP_DEFENDER;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_DEFENDER_GROUP() {
    return CHIP_DEFENDER_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_MEDIC() {
    return CHIP_MEDIC;
  }

  @NotNull
  public final ItemEntry<Item> getCHIP_MEDIC_GROUP() {
    return CHIP_MEDIC_GROUP;
  }

  @NotNull
  public final ItemEntry<Item> getMAGIC_DUST() {
    return MAGIC_DUST;
  }

  @NotNull
  public final ItemEntry<Item> getPICTURES_OF_THE_PAST() {
    return PICTURES_OF_THE_PAST;
  }

  @NotNull
  public final ItemEntry<Item> getRANDOM_GODS() {
    return RANDOM_GODS;
  }

  @NotNull
  public final ItemEntry<Item> getSTRANGER_THINK() {
    return STRANGER_THINK;
  }

  @NotNull
  public final ResourceKey<CreativeModeTab> getZINECRAFT_CORE_ITEM_GROUP_KEY() {
    return ZINECRAFT_CORE_ITEM_GROUP_KEY;
  }

  @NotNull
  public final CreativeModeTab getZINECRAFT_CORE_ITEM_GROUP() {
    return ZINECRAFT_CORE_ITEM_GROUP;
  }

  private final String toDisplayName(String _this_toDisplayName) {
    return com.cxxcxx.zinecraft.api.localization.TranslationNames.toDisplayName(_this_toDisplayName);
  }
}
