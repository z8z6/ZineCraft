package com.cxxcxx.zinecraft.core.item


import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.sound.ModSound
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item


object ModItem {

  private fun item(path: String, zhCn: String, enUs: String = path.toDisplayName()) =
    Zinecraft.ITEMS.register(path, zhCn, enUs)

  private fun ItemWrap(path: String, zhCn: String): ItemEntry<Item> = item(path, zhCn)

  private fun <T : Item> ItemWrap(
    path: String,
    zhCn: String,
    item: T,
    model: ModelTemplate = ModelTemplates.FLAT_ITEM,
    enUs: String = path.toDisplayName()
  ): ItemEntry<T> = Zinecraft.ITEMS.register(path, zhCn, enUs, model) { item }

  val EXAMPLE_ITEM = item("example_item", "示例物品")

  val ORIROCK = item("orirock", "源岩")
  val ORIROCK_CUBE: ItemWrap<Item> = ItemWrap("orirock_cube", "固源岩")
  val ORIROCK_CLUSTER: ItemWrap<Item> = ItemWrap("orirock_cluster", "固源岩组")
  val ORIROCK_CONCENTRATION: ItemWrap<Item> = ItemWrap("orirock_concentration", "提纯源岩")
  val ORIGINITE: ItemWrap<Item> = ItemWrap("originite", "源石")
  val PROTOCOL_ORIGINIUM: ItemWrap<Item> = ItemWrap("protocol_originium", "协议源石")
  val ORIGINIUM_POWDER: ItemWrap<Item> = ItemWrap("originium_powder", "源石碎片")
  val GRINDSTONE: ItemWrap<Item> = ItemWrap("grindstone", "研磨石")
  val GRINDSTONE_PENTAHYDRATE: ItemWrap<Item> = ItemWrap("grindstone_pentahydrate", "五水研磨石")
  val MANGANESE_ORE: ItemWrap<Item> = ItemWrap("manganese_ore", "轻锰矿")
  val MANGANESE_TRIHYDRATE: ItemWrap<Item> = ItemWrap("manganese_trihydrate", "三水锰矿")
  val RMA70_12: ItemWrap<Item> = ItemWrap("rma70_12", "RMA70-12")
  val RMA70_24: ItemWrap<Item> = ItemWrap("rma70_24", "RMA70-24")
  val CRYSTAL_ELEMENT: ItemWrap<Item> = ItemWrap("crystal_element", "晶体元件")
  val CRYSTAL_GROUP: ItemWrap<Item> = ItemWrap("crystal_group", "晶体电路")
  val CRYSTALLINE_CIRCUIT: ItemWrap<Item> = ItemWrap("crystalline_circuit", "晶体电子单元")
  val ESTER_RAW: ItemWrap<Item> = ItemWrap("ester_raw", "酯原料")
  val POLYESTER: ItemWrap<Item> = ItemWrap("polyester", "聚酸酯")
  val POLYESTER_GROUP: ItemWrap<Item> = ItemWrap("polyester_group", "聚酸酯组")
  val POLYESTER_BLOCK: ItemWrap<Item> = ItemWrap("polyester_block", "聚酸酯块")
  val SUGAR_SUBSTITUTE: ItemWrap<Item> = ItemWrap("sugar_substitute", "代糖")
  val SUGAR: ItemWrap<Item> = ItemWrap("sugar", "糖")
  val SUGAR_GROUP: ItemWrap<Item> = ItemWrap("sugar_group", "糖组")
  val SUGAR_POLYMER: ItemWrap<Item> = ItemWrap("sugar_polymer", "糖聚块")
  val COMBINED_CUTTING_FLUID: ItemWrap<Item> = ItemWrap("compound_cutting_fluid", "化合切削液")
  val CUTTING_FLUID_SOLUTION: ItemWrap<Item> = ItemWrap("cutting_fluid_solution", "切削原液")
  val SEMI_SYNTHETIC_SOLVENT: ItemWrap<Item> = ItemWrap("semi_synthetic_solvent", "半自然溶剂")
  val REFINED_SOLVENT: ItemWrap<Item> = ItemWrap("refined_solvent", "精练溶剂")
  val DAMAGED_DEVICE: ItemWrap<Item> = ItemWrap("damaged_device", "破损装置")
  val DEVICE_CORE: ItemWrap<Item> = ItemWrap("device", "装置")
  val DEVICE_GROUP: ItemWrap<Item> = ItemWrap("integrated_device", "全新装置")
  val OPTIMIZED_DEVICE: ItemWrap<Item> = ItemWrap("optimized_device", "改良装置")
  val BIPOLAR_NANOSHEET: ItemWrap<Item> = ItemWrap("bipolar_nanosheet", "双极纳米片")
  val D32_STEEL: ItemWrap<Item> = ItemWrap("d32_steel", "D32钢")
  val ORIRON_SHARD: ItemWrap<Item> = ItemWrap("oriron_shard", "异铁碎片")
  val ORIRON: ItemWrap<Item> = ItemWrap("oriron", "异铁")
  val ORIRON_GROUP: ItemWrap<Item> = ItemWrap("oriron_group", "异铁组")
  val ORIRON_CLUSTER: ItemWrap<Item> = ItemWrap("oriron_cluster", "异铁块")
  val DIKETONE: ItemWrap<Item> = ItemWrap("diketon", "双酮")
  val AKETON: ItemWrap<Item> = ItemWrap("aketone", "酮凝集")
  val POLYKETON: ItemWrap<Item> = ItemWrap("polyketon", "酮凝集组")
  val KETON_COLLOID: ItemWrap<Item> = ItemWrap("keton_colloid", "酮阵列")
  val POLYMER_AGENT: ItemWrap<Item> = ItemWrap("polymer_agent", "聚合剂")
  val LOXIC_KOHL: ItemWrap<Item> = ItemWrap("loxic_kohl", "炽合金")
  val INCANDESCENT_ALLOY: ItemWrap<Item> = ItemWrap("incandescent_alloy", "炽合金块")
  val GEL: ItemWrap<Item> = ItemWrap("gel", "凝胶")
  val COAGULATING_GEL: ItemWrap<Item> = ItemWrap("coagulating_gel", "聚合凝胶")
  val TWISTED_ALCOHOL: ItemWrap<Item> = ItemWrap("twisted_alcohol", "扭转醇")
  val WHITE_HORSE_KOHL: ItemWrap<Item> = ItemWrap("white_horse_kohl", "白马醇")
  val SKILL_SUMMARY_1: ItemWrap<Item> = ItemWrap("skill_summary_1", "技巧概要·卷1")
  val SKILL_SUMMARY_2: ItemWrap<Item> = ItemWrap("skill_summary_2", "技巧概要·卷2")
  val SKILL_SUMMARY_3: ItemWrap<Item> = ItemWrap("skill_summary_3", "技巧概要·卷3")
  val HEADHUNT_TICKET: ItemWrap<Item> = ItemWrap("headhunt_ticket", "寻访凭证")
  val LMD: ItemWrap<Item> = ItemWrap("lmd", "龙门币")
  val CHIP_VANGUARD: ItemWrap<Item> = ItemWrap("chip_vanguard", "先锋芯片")
  val CHIP_VANGUARD_GROUP: ItemWrap<Item> = ItemWrap("chip_vanguard_group", "先锋芯片组")
  val CHIP_GUARD: ItemWrap<Item> = ItemWrap("chip_guard", "近卫芯片")
  val CHIP_GUARD_GROUP: ItemWrap<Item> = ItemWrap("chip_guard_group", "近卫芯片组")
  val CHIP_SNIPER: ItemWrap<Item> = ItemWrap("chip_sniper", "狙击芯片")
  val CHIP_SNIPER_GROUP: ItemWrap<Item> = ItemWrap("chip_sniper_group", "狙击芯片组")
  val CHIP_CASTER: ItemWrap<Item> = ItemWrap("chip_caster", "术士芯片")
  val CHIP_CASTER_GROUP: ItemWrap<Item> = ItemWrap("chip_caster_group", "术士芯片组")
  val CHIP_SPECIAL: ItemWrap<Item> = ItemWrap("chip_special", "特种芯片")
  val CHIP_SPECIAL_GROUP: ItemWrap<Item> = ItemWrap("chip_special_group", "特种芯片组")
  val CHIP_SUPPORT: ItemWrap<Item> = ItemWrap("chip_support", "辅助芯片")
  val CHIP_SUPPORT_GROUP: ItemWrap<Item> = ItemWrap("chip_support_group", "辅助芯片组")
  val CHIP_DEFENDER: ItemWrap<Item> = ItemWrap("chip_defender", "重装芯片")
  val CHIP_DEFENDER_GROUP: ItemWrap<Item> = ItemWrap("chip_defender_group", "重装芯片组")
  val CHIP_MEDIC: ItemWrap<Item> = ItemWrap("chip_medic", "医疗芯片")
  val CHIP_MEDIC_GROUP: ItemWrap<Item> = ItemWrap("chip_medic_group", "医疗芯片组")

  val MAGIC_DUST: ItemWrap<Item> = ItemWrap(
    "magic_dust", "魔法粉尘",
    Item(
      Item.Properties()
        .food(
          FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).alwaysEdible().fast()
            .effect(MobEffectInstance(MobEffects.JUMP, 20 * 30, 2), 1.0f).build()
        )
    )
  ).fuel(30 * 20).compost(0.3f)

  val PICTURES_OF_THE_PAST = ModSound.AMBIENT_PICTURES_OF_THE_PAST.itemEntry
  val RANDOM_GODS = ModSound.AMBIENT_RANDOM_GODS.itemEntry
  val STRANGER_THINK = ModSound.AMBIENT_STRANGER_THINK.itemEntry


  private val ITEM_GROUP = Zinecraft.CREATIVE_TABS.register(
    "item",
    "Zinecraft",
    "Zinecraft",
    { net.minecraft.world.item.ItemStack(D32_STEEL.item) }
  )
  val ZINECRAFT_CORE_ITEM_GROUP_KEY = ITEM_GROUP.key
  val ZINECRAFT_CORE_ITEM_GROUP = ITEM_GROUP.tab


  private fun String.toDisplayName(): String =
    split('_', '.').filter(String::isNotEmpty).joinToString(" ") { word ->
      word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

}

private typealias ItemWrap<T> = ItemEntry<T>

