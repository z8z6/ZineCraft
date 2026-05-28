package com.cxxcxx.zinecraft.core.client.datagen

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplates


class ModModelProvider(output: FabricDataOutput) : FabricModelProvider(output) {

  // generated/assets/zinecraft-core/blockstates
  // generated/assets/zinecraft-core/models/block
  override fun generateBlockStateModels(blockStateModelGenerator: BlockModelGenerators?) {
    blockStateModelGenerator?.createTrivialCube(ModBlock.EXAMPLE_ENTITY_BLOCK);
  }

  override fun generateItemModels(itemModelGenerator: ItemModelGenerators?) {
    itemModelGenerator?.generateFlatItem(ModItem.EXAMPLE_ITEM, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIROCK_CUBE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIROCK_CLUSTER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIROCK_CONCENTRATION, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIGINITE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIGINIUM_POWDER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.GRINDSTONE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.GRINDSTONE_PENTAHYDRATE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.MANGANESE_ORE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.MANGANESE_TRIHYDRATE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.RMA70_12, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.RMA70_24, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CRYSTAL_ELEMENT, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CRYSTAL_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CRYSTALLINE_CIRCUIT, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ESTER_RAW, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.POLYESTER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.POLYESTER_BLOCK, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SUGAR_SUBSTITUTE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SUGAR, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SUGAR_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SUGAR_POLYMER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.COMBINED_CUTTING_FLUID, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CUTTING_FLUID_SOLUTION, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SEMI_SYNTHETIC_SOLVENT, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.DAMAGED_DEVICE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.DEVICE_CORE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.DEVICE_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.OPTIMIZED_DEVICE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.BIPOLAR_NANOSHEET, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.D32_STEEL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIRON_SHARD, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIRON, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.ORIRON_CLUSTER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.DIKETONE, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.AKETON, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.POLYKETON, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.KETON_COLLOID, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.LOXIC_KOHL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.INCANDESCENT_ALLOY, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.GEL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.COAGULATING_GEL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.TWISTED_ALCOHOL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.WHITE_HORSE_KOHL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SKILL_SUMMARY_1, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SKILL_SUMMARY_2, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.SKILL_SUMMARY_3, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.HEADHUNT_TICKET, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.LMD, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_GUARD, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_GUARD_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SNIPER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SNIPER_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_CASTER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_CASTER_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SPECIAL, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SPECIAL_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SUPPORT, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_SUPPORT_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_DEFENDER, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_DEFENDER_GROUP, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_MEDIC, ModelTemplates.FLAT_ITEM)
    itemModelGenerator?.generateFlatItem(ModItem.CHIP_MEDIC_GROUP, ModelTemplates.FLAT_ITEM)

    itemModelGenerator?.generateFlatItem(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), ModelTemplates.FLAT_ITEM)
  }

  override fun getName(): String {
    return "ModModelProvider"
  }
}