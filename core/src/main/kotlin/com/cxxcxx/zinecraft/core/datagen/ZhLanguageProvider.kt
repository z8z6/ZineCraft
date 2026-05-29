package com.cxxcxx.zinecraft.core.client.datagen

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ZhLanguageProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricLanguageProvider(dataOutput, "zh_cn", registryLookup) {

  // generated/assets/zinecraft-core/lang/zh_cn.json
  override fun generateTranslations(holderLookup: HolderLookup.Provider?, translationBuilder: TranslationBuilder?) {
    translationBuilder?.add(ModItem.EXAMPLE_ITEM, "示例物品")
    translationBuilder?.add(ModItem.ORIROCK, "源岩")
    translationBuilder?.add(ModItem.ORIROCK_CUBE, "固源岩")
    translationBuilder?.add(ModItem.ORIROCK_CLUSTER, "固源岩组")
    translationBuilder?.add(ModItem.ORIROCK_CONCENTRATION, "提纯源岩")
    translationBuilder?.add(ModItem.ORIGINITE, "源石")
    translationBuilder?.add(ModItem.ORIGINIUM_POWDER, "源石碎片")
    translationBuilder?.add(ModItem.GRINDSTONE, "研磨石")
    translationBuilder?.add(ModItem.GRINDSTONE_PENTAHYDRATE, "五水研磨石")
    translationBuilder?.add(ModItem.MANGANESE_ORE, "轻锰矿")
    translationBuilder?.add(ModItem.MANGANESE_TRIHYDRATE, "三水锰矿")
    translationBuilder?.add(ModItem.RMA70_12, "RMA70-12")
    translationBuilder?.add(ModItem.RMA70_24, "RMA70-24")
    translationBuilder?.add(ModItem.CRYSTAL_ELEMENT, "晶体元件")
    translationBuilder?.add(ModItem.CRYSTAL_GROUP, "晶体电路")
    translationBuilder?.add(ModItem.CRYSTALLINE_CIRCUIT, "晶体电子单元")
    translationBuilder?.add(ModItem.ESTER_RAW, "酯原料")
    translationBuilder?.add(ModItem.POLYESTER, "聚酸酯")
    translationBuilder?.add(ModItem.POLYESTER_GROUP, "聚酸酯组")
    translationBuilder?.add(ModItem.POLYESTER_BLOCK, "聚酸酯块")
    translationBuilder?.add(ModItem.SUGAR_SUBSTITUTE, "代糖")
    translationBuilder?.add(ModItem.SUGAR, "糖")
    translationBuilder?.add(ModItem.SUGAR_GROUP, "糖组")
    translationBuilder?.add(ModItem.SUGAR_POLYMER, "糖聚块")
    translationBuilder?.add(ModItem.COMBINED_CUTTING_FLUID, "化合切削液")
    translationBuilder?.add(ModItem.CUTTING_FLUID_SOLUTION, "切削原液")
    translationBuilder?.add(ModItem.SEMI_SYNTHETIC_SOLVENT, "半自然溶剂")
    translationBuilder?.add(ModItem.REFINED_SOLVENT,"精炼溶剂")
    translationBuilder?.add(ModItem.DAMAGED_DEVICE, "破损装置")
    translationBuilder?.add(ModItem.DEVICE_CORE, "装置")
    translationBuilder?.add(ModItem.DEVICE_GROUP, "全新装置")
    translationBuilder?.add(ModItem.OPTIMIZED_DEVICE, "改良装置")
    translationBuilder?.add(ModItem.BIPOLAR_NANOSHEET, "双极纳米片")
    translationBuilder?.add(ModItem.D32_STEEL, "D32钢")
    translationBuilder?.add(ModItem.ORIRON_SHARD, "异铁碎片")
    translationBuilder?.add(ModItem.ORIRON, "异铁")
    translationBuilder?.add(ModItem.ORIRON_GROUP, "异铁组")
    translationBuilder?.add(ModItem.ORIRON_CLUSTER, "异铁块")
    translationBuilder?.add(ModItem.DIKETONE, "双酮")
    translationBuilder?.add(ModItem.AKETON, "酮凝集")
    translationBuilder?.add(ModItem.POLYKETON, "酮凝集组")
    translationBuilder?.add(ModItem.KETON_COLLOID, "酮阵列")
    translationBuilder?.add(ModItem.POLYMER_AGENT,"聚合剂")
    translationBuilder?.add(ModItem.LOXIC_KOHL, "炽合金")
    translationBuilder?.add(ModItem.INCANDESCENT_ALLOY, "炽合金块")
    translationBuilder?.add(ModItem.GEL, "凝胶")
    translationBuilder?.add(ModItem.COAGULATING_GEL, "聚合凝胶")
    translationBuilder?.add(ModItem.TWISTED_ALCOHOL, "扭转醇")
    translationBuilder?.add(ModItem.WHITE_HORSE_KOHL, "白马醇")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_1, "技巧概要·卷1")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_2, "技巧概要·卷2")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_3, "技巧概要·卷3")
    translationBuilder?.add(ModItem.HEADHUNT_TICKET, "寻访凭证")
    translationBuilder?.add(ModItem.LMD, "龙门币")
    translationBuilder?.add(ModItem.CHIP_VANGUARD,"先锋芯片")
    translationBuilder?.add(ModItem.CHIP_VANGUARD_GROUP,"先锋芯片组")
    translationBuilder?.add(ModItem.CHIP_GUARD, "近卫芯片")
    translationBuilder?.add(ModItem.CHIP_GUARD_GROUP, "近卫芯片组")
    translationBuilder?.add(ModItem.CHIP_SNIPER, "狙击芯片")
    translationBuilder?.add(ModItem.CHIP_SNIPER_GROUP, "狙击芯片组")
    translationBuilder?.add(ModItem.CHIP_CASTER, "术士芯片")
    translationBuilder?.add(ModItem.CHIP_CASTER_GROUP, "术士芯片组")
    translationBuilder?.add(ModItem.CHIP_SPECIAL, "特种芯片")
    translationBuilder?.add(ModItem.CHIP_SPECIAL_GROUP, "特种芯片组")
    translationBuilder?.add(ModItem.CHIP_SUPPORT, "辅助芯片")
    translationBuilder?.add(ModItem.CHIP_SUPPORT_GROUP, "辅助芯片组")
    translationBuilder?.add(ModItem.CHIP_DEFENDER, "重装芯片")
    translationBuilder?.add(ModItem.CHIP_DEFENDER_GROUP, "重装芯片组")
    translationBuilder?.add(ModItem.CHIP_MEDIC, "医疗芯片")
    translationBuilder?.add(ModItem.CHIP_MEDIC_GROUP, "医疗芯片组")
    translationBuilder?.add(ModItem.MAGIC_DUST, "魔法粉尘")

    translationBuilder?.add(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), "示例实体方块")
  }
}