package com.cxxcxx.zinecraft.core.item


import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack


object ModItem {

  val EXAMPLE_ITEM: Item = register("example_item", Item(Item.Properties()))

  // 源岩
  val ORIROCK: Item = register("orirock", Item(Item.Properties()))

  // 固源岩
  val ORIROCK_CUBE: Item = register("orirock_cube", Item(Item.Properties()))

  // 固源岩组
  val ORIROCK_CLUSTER: Item = register("orirock_cluster", Item(Item.Properties()))

  // 提纯源岩
  val ORIROCK_CONCENTRATION: Item = register("orirock_concentration", Item(Item.Properties()))

  // 源石
  val ORIGINITE: Item = register("originite", Item(Item.Properties()))

  // 源石碎片
  val ORIGINIUM_POWDER: Item = register("originium_powder", Item(Item.Properties()))

  // 研磨石
  val GRINDSTONE: Item = register("grindstone", Item(Item.Properties()))

  // 五水研磨石
  val GRINDSTONE_PENTAHYDRATE: Item = register("grindstone_pentahydrate", Item(Item.Properties()))

  // 轻锰矿
  val MANGANESE_ORE: Item = register("manganese_ore", Item(Item.Properties()))

  // 三水锰矿
  val MANGANESE_TRIHYDRATE: Item = register("manganese_trihydrate", Item(Item.Properties()))

  // RMA70-12
  val RMA70_12: Item = register("rma70_12", Item(Item.Properties()))

  // RMA70-24
  val RMA70_24: Item = register("rma70_24", Item(Item.Properties()))

  // 晶体元件
  val CRYSTAL_ELEMENT: Item = register("crystal_element", Item(Item.Properties()))

  // 晶体电路
  val CRYSTAL_GROUP: Item = register("crystal_group", Item(Item.Properties()))

  // 晶体电子单元
  val CRYSTALLINE_CIRCUIT: Item = register("crystalline_circuit", Item(Item.Properties()))

  // 酯原料
  val ESTER_RAW: Item = register("ester_raw", Item(Item.Properties()))

  // 聚酸酯
  val POLYESTER: Item = register("polyester", Item(Item.Properties()))

  // 聚酸酯组
  val POLYESTER_GROUP: Item = register("polyester_group", Item(Item.Properties()))

  // 聚酸酯块
  val POLYESTER_BLOCK: Item = register("polyester_block", Item(Item.Properties()))

  // 代糖
  val SUGAR_SUBSTITUTE: Item = register("sugar_substitute", Item(Item.Properties()))

  // 糖
  val SUGAR: Item = register("sugar", Item(Item.Properties()))

  // 糖组
  val SUGAR_GROUP: Item = register("sugar_group", Item(Item.Properties()))

  // 糖聚块
  val SUGAR_POLYMER: Item = register("sugar_polymer", Item(Item.Properties()))

  // 化合切削液
  val COMBINED_CUTTING_FLUID: Item = register("compound_cutting_fluid", Item(Item.Properties()))

  // 切削原液
  val CUTTING_FLUID_SOLUTION: Item = register("cutting_fluid_solution", Item(Item.Properties()))

  // 半自然溶剂
  val SEMI_SYNTHETIC_SOLVENT: Item = register("semi_synthetic_solvent", Item(Item.Properties()))

  // 精练溶剂
  val REFINED_SOLVENT: Item = register("refined_solvent", Item(Item.Properties()))

  // 破损装置
  val DAMAGED_DEVICE: Item = register("damaged_device", Item(Item.Properties()))

  // 装置
  val DEVICE_CORE: Item = register("device", Item(Item.Properties()))

  // 全新装置
  val DEVICE_GROUP: Item = register("integrated_device", Item(Item.Properties()))

  // 改良装置
  val OPTIMIZED_DEVICE: Item = register("optimized_device", Item(Item.Properties()))

  // 双极纳米片
  val BIPOLAR_NANOSHEET: Item = register("bipolar_nanosheet", Item(Item.Properties()))

  // D32钢
  val D32_STEEL: Item = register("d32_steel", Item(Item.Properties()))

  // 异铁碎片
  val ORIRON_SHARD: Item = register("oriron_shard", Item(Item.Properties()))

  // 异铁
  val ORIRON: Item = register("oriron", Item(Item.Properties()))

  // 异铁组
  val ORIRON_GROUP: Item = register("oriron_group", Item(Item.Properties()))

  // 异铁块
  val ORIRON_CLUSTER: Item = register("oriron_cluster", Item(Item.Properties()))

  // 双酮
  val DIKETONE: Item = register("diketon", Item(Item.Properties()))

  // 酮凝集
  val AKETON: Item = register("aketone", Item(Item.Properties()))

  // 酮凝集组
  val POLYKETON: Item = register("polyketon", Item(Item.Properties()))

  // 酮阵列
  val KETON_COLLOID: Item = register("keton_colloid", Item(Item.Properties()))

  // 聚合剂
  val POLYMER_AGENT: Item = register("polymer_agent", Item(Item.Properties()))

  // 炽合金
  val LOXIC_KOHL: Item = register("loxic_kohl", Item(Item.Properties()))

  // 炽合金块
  val INCANDESCENT_ALLOY: Item = register("incandescent_alloy", Item(Item.Properties()))

  // 凝胶
  val GEL: Item = register("gel", Item(Item.Properties()))

  // 聚合凝胶
  val COAGULATING_GEL: Item = register("coagulating_gel", Item(Item.Properties()))

  // 扭转醇
  val TWISTED_ALCOHOL: Item = register("twisted_alcohol", Item(Item.Properties()))

  // 白马醇
  val WHITE_HORSE_KOHL: Item = register("white_horse_kohl", Item(Item.Properties()))

  // 技巧概要·卷1
  val SKILL_SUMMARY_1: Item = register("skill_summary_1", Item(Item.Properties()))

  // 技巧概要·卷2
  val SKILL_SUMMARY_2: Item = register("skill_summary_2", Item(Item.Properties()))

  // 技巧概要·卷3
  val SKILL_SUMMARY_3: Item = register("skill_summary_3", Item(Item.Properties()))

  // 寻访凭证
  val HEADHUNT_TICKET: Item = register("headhunt_ticket", Item(Item.Properties()))

  // 龙门币
  val LMD: Item = register("lmd", Item(Item.Properties()))

  // 先锋芯片
  val CHIP_VANGUARD: Item = register("chip_vanguard", Item(Item.Properties()))

  // 先锋芯片组
  val CHIP_VANGUARD_GROUP: Item = register("chip_vanguard_group", Item(Item.Properties()))

  // 近卫芯片
  val CHIP_GUARD: Item = register("chip_guard", Item(Item.Properties()))

  // 近卫芯片组
  val CHIP_GUARD_GROUP: Item = register("chip_guard_group", Item(Item.Properties()))

  // 狙击芯片
  val CHIP_SNIPER: Item = register("chip_sniper", Item(Item.Properties()))

  // 狙击芯片组
  val CHIP_SNIPER_GROUP: Item = register("chip_sniper_group", Item(Item.Properties()))

  // 术士芯片
  val CHIP_CASTER: Item = register("chip_caster", Item(Item.Properties()))

  // 术士芯片组
  val CHIP_CASTER_GROUP: Item = register("chip_caster_group", Item(Item.Properties()))

  // 特种芯片
  val CHIP_SPECIAL: Item = register("chip_special", Item(Item.Properties()))

  // 特种芯片组
  val CHIP_SPECIAL_GROUP: Item = register("chip_special_group", Item(Item.Properties()))

  // 辅助芯片
  val CHIP_SUPPORT: Item = register("chip_support", Item(Item.Properties()))

  // 辅助芯片组
  val CHIP_SUPPORT_GROUP: Item = register("chip_support_group", Item(Item.Properties()))

  // 重装芯片
  val CHIP_DEFENDER: Item = register("chip_defender", Item(Item.Properties()))

  // 重装芯片组
  val CHIP_DEFENDER_GROUP: Item = register("chip_defender_group", Item(Item.Properties()))

  // 医疗芯片
  val CHIP_MEDIC: Item = register("chip_medic", Item(Item.Properties()))

  // 医疗芯片组
  val CHIP_MEDIC_GROUP: Item = register("chip_medic_group", Item(Item.Properties()))

  // 魔法粉尘
  val MAGIC_DUST: Item = register(
      "magic_dust",
      Item(
          Item.Properties()
              .food(FoodProperties.Builder().nutrition(6).saturationModifier(0.8f).alwaysEdible().build())
      )
  )

  fun <T : Item> register(path: String, item: T): T {
    val id = ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, path)
    return Registry.register(BuiltInRegistries.ITEM, id, item)
  }

  fun init() {
    initCreativeTab()
  }


  val ZINECRAFT_CORE_ITEM_GROUP_KEY: ResourceKey<CreativeModeTab?> = ResourceKey.create(
    BuiltInRegistries.CREATIVE_MODE_TAB.key(),
    ResourceLocation.fromNamespaceAndPath(ZinecraftCore.MOD_ID, "item")
  )
  val ZINECRAFT_CORE_ITEM_GROUP: CreativeModeTab = FabricItemGroup.builder()
    .icon({ ItemStack(D32_STEEL) })
    .title(Component.translatable("itemGroup." + ZinecraftCore.MOD_ID))
    .build()

  fun initCreativeTab() {
    // Register the group.
    Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ZINECRAFT_CORE_ITEM_GROUP_KEY, ZINECRAFT_CORE_ITEM_GROUP);

    ItemGroupEvents.modifyEntriesEvent(ZINECRAFT_CORE_ITEM_GROUP_KEY)
      .register(ModifyEntries { content: FabricItemGroupEntries ->
        content.accept(EXAMPLE_ITEM)
        content.accept(ORIROCK)
        content.accept(ORIROCK_CUBE)
        content.accept(ORIROCK_CLUSTER)
        content.accept(ORIROCK_CONCENTRATION)
        content.accept(ORIGINITE)
        content.accept(ORIGINIUM_POWDER)
        content.accept(GRINDSTONE)
        content.accept(GRINDSTONE_PENTAHYDRATE)
        content.accept(MANGANESE_ORE)
        content.accept(MANGANESE_TRIHYDRATE)
        content.accept(RMA70_12)
        content.accept(RMA70_24)
        content.accept(CRYSTAL_ELEMENT)
        content.accept(CRYSTAL_GROUP)
        content.accept(CRYSTALLINE_CIRCUIT)
        content.accept(ESTER_RAW)
        content.accept(POLYESTER)
        content.accept(POLYESTER_GROUP)
        content.accept(POLYESTER_BLOCK)
        content.accept(SUGAR_SUBSTITUTE)
        content.accept(SUGAR)
        content.accept(SUGAR_GROUP)
        content.accept(SUGAR_POLYMER)
        content.accept(COMBINED_CUTTING_FLUID)
        content.accept(CUTTING_FLUID_SOLUTION)
        content.accept(SEMI_SYNTHETIC_SOLVENT)
        content.accept(REFINED_SOLVENT)
        content.accept(DAMAGED_DEVICE)
        content.accept(DEVICE_CORE)
        content.accept(DEVICE_GROUP)
        content.accept(OPTIMIZED_DEVICE)
        content.accept(BIPOLAR_NANOSHEET)
        content.accept(D32_STEEL)
        content.accept(ORIRON_SHARD)
        content.accept(ORIRON)
        content.accept(ORIRON_GROUP)
        content.accept(ORIRON_CLUSTER)
        content.accept(DIKETONE)
        content.accept(AKETON)
        content.accept(POLYKETON)
        content.accept(KETON_COLLOID)
        content.accept(POLYMER_AGENT)
        content.accept(LOXIC_KOHL)
        content.accept(INCANDESCENT_ALLOY)
        content.accept(GEL)
        content.accept(COAGULATING_GEL)
        content.accept(TWISTED_ALCOHOL)
        content.accept(WHITE_HORSE_KOHL)
        content.accept(SKILL_SUMMARY_1)
        content.accept(SKILL_SUMMARY_2)
        content.accept(SKILL_SUMMARY_3)
        content.accept(HEADHUNT_TICKET)
        content.accept(LMD)
        content.accept(CHIP_VANGUARD)
        content.accept(CHIP_VANGUARD_GROUP)
        content.accept(CHIP_GUARD)
        content.accept(CHIP_GUARD_GROUP)
        content.accept(CHIP_SNIPER)
        content.accept(CHIP_SNIPER_GROUP)
        content.accept(CHIP_CASTER)
        content.accept(CHIP_CASTER_GROUP)
        content.accept(CHIP_SPECIAL)
        content.accept(CHIP_SPECIAL_GROUP)
        content.accept(CHIP_SUPPORT)
        content.accept(CHIP_SUPPORT_GROUP)
        content.accept(CHIP_DEFENDER)
        content.accept(CHIP_DEFENDER_GROUP)
        content.accept(CHIP_MEDIC)
        content.accept(CHIP_MEDIC_GROUP)
        content.accept(MAGIC_DUST)

        content.accept(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem())
      })
  }

}

