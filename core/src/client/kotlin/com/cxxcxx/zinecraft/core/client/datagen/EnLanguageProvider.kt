package com.cxxcxx.zinecraft.core.client.datagen

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture


class EnLanguageProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricLanguageProvider(dataOutput, "en_us", registryLookup) {

  // generated/assets/zinecraft-core/lang/en_us.json
  override fun generateTranslations(holderLookup: HolderLookup.Provider?, translationBuilder: TranslationBuilder?) {
    translationBuilder?.add(ModItem.EXAMPLE_ITEM, "Example Item")
    translationBuilder?.add(ModItem.ORIROCK, "Orirock")
    translationBuilder?.add(ModItem.ORIROCK_CUBE, "Orirock Cube")
    translationBuilder?.add(ModItem.ORIROCK_CLUSTER, "Orirock Cluster")
    translationBuilder?.add(ModItem.ORIROCK_CONCENTRATION, "Orirock Concentration")
    translationBuilder?.add(ModItem.ORIGINITE, "Originite")
    translationBuilder?.add(ModItem.ORIGINIUM_POWDER, "Originium Powder")
    translationBuilder?.add(ModItem.GRINDSTONE, "Grindstone")
    translationBuilder?.add(ModItem.GRINDSTONE_PENTAHYDRATE, "Grindstone Pentahydrate")
    translationBuilder?.add(ModItem.MANGANESE_ORE, "Manganese Ore")
    translationBuilder?.add(ModItem.MANGANESE_TRIHYDRATE, "Manganese Trihydrate")
    translationBuilder?.add(ModItem.RMA70_12, "RMA70-12")
    translationBuilder?.add(ModItem.RMA70_24, "RMA70-24")
    translationBuilder?.add(ModItem.CRYSTAL_ELEMENT, "Crystal Element")
    translationBuilder?.add(ModItem.CRYSTAL_GROUP, "Crystal Circuit")
    translationBuilder?.add(ModItem.CRYSTALLINE_CIRCUIT, "Crystalline Circuit")
    translationBuilder?.add(ModItem.ESTER_RAW, "Raw Ester")
    translationBuilder?.add(ModItem.POLYESTER, "Polyester")
    translationBuilder?.add(ModItem.POLYESTER_GROUP, "Polyester Group")
    translationBuilder?.add(ModItem.POLYESTER_BLOCK, "Polyester Block")
    translationBuilder?.add(ModItem.SUGAR_SUBSTITUTE, "Sugar Substitute")
    translationBuilder?.add(ModItem.SUGAR, "Sugar")
    translationBuilder?.add(ModItem.SUGAR_GROUP, "Sugar Cluster")
    translationBuilder?.add(ModItem.SUGAR_POLYMER, "Sugar Polymer")
    translationBuilder?.add(ModItem.COMBINED_CUTTING_FLUID, "Compound Cutting Fluid")
    translationBuilder?.add(ModItem.CUTTING_FLUID_SOLUTION, "Cutting Fluid Solution")
    translationBuilder?.add(ModItem.SEMI_SYNTHETIC_SOLVENT, "Semi-Synthetic Solvent")
    translationBuilder?.add(ModItem.REFINED_SOLVENT,"Refined Solvent")
    translationBuilder?.add(ModItem.DAMAGED_DEVICE, "Damaged Device")
    translationBuilder?.add(ModItem.DEVICE_CORE, "Device")
    translationBuilder?.add(ModItem.DEVICE_GROUP, "Integrated Device")
    translationBuilder?.add(ModItem.OPTIMIZED_DEVICE, "Optimized Device")
    translationBuilder?.add(ModItem.BIPOLAR_NANOSHEET, "Bipolar Nanosheet")
    translationBuilder?.add(ModItem.D32_STEEL, "D32 Steel")
    translationBuilder?.add(ModItem.ORIRON_SHARD, "Oriron Shard")
    translationBuilder?.add(ModItem.ORIRON, "Oriron")
    translationBuilder?.add(ModItem.ORIRON_GROUP, "Oriron Group")
    translationBuilder?.add(ModItem.ORIRON_CLUSTER, "Oriron Cluster")
    translationBuilder?.add(ModItem.DIKETONE, "Diketone")
    translationBuilder?.add(ModItem.AKETON, "Aketon")
    translationBuilder?.add(ModItem.POLYKETON, "Polyketon")
    translationBuilder?.add(ModItem.KETON_COLLOID, "Keton Colloid")
    translationBuilder?.add(ModItem.POLYMER_AGENT,"Polymer agent")
    translationBuilder?.add(ModItem.LOXIC_KOHL, "Loxic Kohl")
    translationBuilder?.add(ModItem.INCANDESCENT_ALLOY, "Incandescent Alloy")
    translationBuilder?.add(ModItem.GEL, "Gel")
    translationBuilder?.add(ModItem.COAGULATING_GEL, "Coagulating Gel")
    translationBuilder?.add(ModItem.TWISTED_ALCOHOL, "Twisted Alcohol")
    translationBuilder?.add(ModItem.WHITE_HORSE_KOHL, "White Horse Kohl")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_1, "Skill Summary Vol.1")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_2, "Skill Summary Vol.2")
    translationBuilder?.add(ModItem.SKILL_SUMMARY_3, "Skill Summary Vol.3")
    translationBuilder?.add(ModItem.HEADHUNT_TICKET, "Headhunting Ticket")
    translationBuilder?.add(ModItem.LMD, "LMD")
    translationBuilder?.add(ModItem.CHIP_VANGUARD,"Vanguard Chip")
    translationBuilder?.add(ModItem.CHIP_VANGUARD_GROUP,"Vanguard Chip Set")
    translationBuilder?.add(ModItem.CHIP_GUARD, "Guard Chip")
    translationBuilder?.add(ModItem.CHIP_GUARD_GROUP, "Guard Chip Set")
    translationBuilder?.add(ModItem.CHIP_SNIPER, "Sniper Chip")
    translationBuilder?.add(ModItem.CHIP_SNIPER_GROUP, "Sniper Chip Set")
    translationBuilder?.add(ModItem.CHIP_CASTER, "Caster Chip")
    translationBuilder?.add(ModItem.CHIP_CASTER_GROUP, "Caster Chip Set")
    translationBuilder?.add(ModItem.CHIP_SPECIAL, "Specialist Chip")
    translationBuilder?.add(ModItem.CHIP_SPECIAL_GROUP, "Specialist Chip Set")
    translationBuilder?.add(ModItem.CHIP_SUPPORT, "Supporter Chip")
    translationBuilder?.add(ModItem.CHIP_SUPPORT_GROUP, "Supporter Chip Set")
    translationBuilder?.add(ModItem.CHIP_DEFENDER, "Defender Chip")
    translationBuilder?.add(ModItem.CHIP_DEFENDER_GROUP, "Defender Chip Set")
    translationBuilder?.add(ModItem.CHIP_MEDIC, "Medic Chip")
    translationBuilder?.add(ModItem.CHIP_MEDIC_GROUP, "Medic Chip Set")
    translationBuilder?.add(ModItem.MAGIC_DUST, "Magic Dust")

    translationBuilder?.add(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), "Example Entity Block")
  }
}