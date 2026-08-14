package com.cxxcxx.zinecraft.core.recipe

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.item.ModItem
import com.cxxcxx.zinecraft.core.item.NationFoods
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(
  output: FabricDataOutput,
  registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricRecipeProvider(output, registriesFuture) {

  override fun buildRecipes(exporter: RecipeOutput) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT)
      .requires(Items.COARSE_DIRT)
      .unlockedBy(getHasName(Items.COARSE_DIRT), has(Items.COARSE_DIRT))
      .save(exporter)

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
      .pattern("ll").pattern("ll")
      .define('l', ItemTags.LOGS)
      .group("multi_bench")
      .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
      .save(exporter)

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LOOM, 4)
      .pattern("ww").pattern("ll")
      .define('w', ItemTags.WOOL).define('l', ItemTags.LOGS)
      .group("multi_bench")
      .unlockedBy(getHasName(Items.LOOM), has(Items.LOOM))
      .save(exporter)

    doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON))
      .unlockedBy(getHasName(Items.OAK_BUTTON), has(Items.OAK_BUTTON))
      .save(exporter)

    oreSmelting(
      exporter,
      listOf(Items.BREAD, Items.COOKIE, Items.HAY_BLOCK),
      RecipeCategory.FOOD,
      Items.WHEAT,
      0.1f,
      300,
      "food_to_wheat"
    )

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.MAGIC_DUST.item, 4)
      .requires(ModItem.MAGIC_DUST.item, 2)
      .unlockedBy(getHasName(ModItem.MAGIC_DUST.item), has(ModItem.MAGIC_DUST.item))
      .save(exporter)

    oreSmelting(
      exporter,
      listOf(ModItem.MAGIC_DUST.item),
      RecipeCategory.MISC,
      ModItem.MAGIC_DUST.item,
      0.1f,
      20,
      "magic_dust_copy"
    )

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.PROTOCOL_ORIGINIUM.item)
      .requires(ModItem.ORIGINITE.item)
      .requires(ModItem.CRYSTALLINE_CIRCUIT.item)
      .requires(ModItem.DEVICE_GROUP.item)
      .unlockedBy(getHasName(ModItem.ORIGINITE.item), has(ModItem.ORIGINITE.item))
      .save(exporter)

    addArknightsMaterialRecipes(exporter)
    addSkillSummaryRecipes(exporter)
    addChipConversionRecipes(exporter)

    nationFoodRecipe(
      exporter,
      NationFoods.AEGIR_FRESH_SHELLCRAB_SASHIMI,
      Items.SALMON,
      Items.DRIED_KELP,
      Items.BEETROOT
    )
    nationFoodRecipe(
      exporter,
      NationFoods.BOLIVAR_SMOKED_CAPSULE,
      Items.COOKED_CHICKEN,
      Items.HONEY_BOTTLE,
      Items.DRIED_KELP
    )
    nationFoodRecipe(exporter, NationFoods.HIGASHI_NANO_KAPPO, Items.SALMON, Items.DRIED_KELP, Items.BOWL)
    nationFoodRecipe(
      exporter,
      NationFoods.DURIN_HONEY_SLUGPUDDING,
      Items.HONEY_BOTTLE,
      Items.COOKED_CHICKEN,
      Items.BOWL
    )
    nationFoodRecipe(
      exporter,
      NationFoods.COLUMBIA_ORIGINIUM_ROASTED_FOWL,
      Items.COOKED_CHICKEN,
      Items.BLAZE_POWDER,
      Items.PAPER
    )
    nationFoodRecipe(
      exporter,
      NationFoods.KAZIMIERZ_KNIGHT_SUPPLEMENT,
      Items.HONEY_BOTTLE,
      Items.SUGAR,
      Items.GOLDEN_CARROT
    )
    nationFoodRecipe(exporter, NationFoods.KAZDEL_CARTILAGE_TACK, Items.COOKED_BEEF, Items.BONE_MEAL, Items.WHEAT)
    nationFoodRecipe(exporter, NationFoods.LATERANO_SACRED_TONE_SOUP, Items.MILK_BUCKET, Items.SUGAR, Items.BOWL)
    nationFoodRecipe(
      exporter,
      NationFoods.LEITHANIEN_MUSICAL_ROAST_EXTRACT,
      Items.COOKED_BEEF,
      Items.AMETHYST_SHARD,
      Items.BOWL
    )
    nationFoodRecipe(exporter, NationFoods.RIM_BILLITON_MINING_RATION, Items.COOKED_BEEF, Items.DRIED_KELP, Items.PAPER)
    nationFoodRecipe(
      exporter,
      NationFoods.MINOS_POETRY_GEL,
      Items.HONEY_BOTTLE,
      Items.MELON_SLICE,
      Items.AMETHYST_SHARD
    )
    nationFoodRecipe(exporter, NationFoods.SARGON_GRASS_CHEESE_GEL, Items.WHEAT, Items.MILK_BUCKET, Items.CACTUS)
    nationFoodRecipe(exporter, NationFoods.SAMI_INSTANT_BONE_SOUP, Items.COOKED_RABBIT, Items.BONE, Items.BOWL)
    nationFoodRecipe(
      exporter,
      NationFoods.VICTORIA_CENTRAL_VALLEY_ROAST,
      Items.COOKED_BEEF,
      Items.BAKED_POTATO,
      Items.BOWL
    )
    nationFoodRecipe(exporter, NationFoods.URSUS_HAM_SUPPLEMENT, Items.COOKED_PORKCHOP, Items.SUGAR, Items.PAPER)
    nationFoodRecipe(exporter, NationFoods.KJERAG_VALLEY_PIE, Items.BREAD, Items.COOKED_MUTTON, Items.SWEET_BERRIES)
    nationFoodRecipe(
      exporter,
      NationFoods.SIRACUSA_STEW_GATHERING,
      Items.COOKED_BEEF,
      Items.CARROT,
      Items.POTATO,
      Items.BOWL
    )
    nationFoodRecipe(
      exporter,
      NationFoods.YAN_WASTELAND_MEAT_STIR_FRY,
      Items.COOKED_PORKCHOP,
      Items.CARROT,
      Items.BROWN_MUSHROOM,
      Items.BOWL
    )
    nationFoodRecipe(
      exporter,
      NationFoods.IBERIA_CHITIN_CLUSTER,
      Items.NAUTILUS_SHELL,
      Items.COOKED_COD,
      Items.DRIED_KELP
    )

    Zinecraft.RECIPES.generate(exporter)
  }

  private fun nationFoodRecipe(
    exporter: RecipeOutput,
    result: ItemLike,
    vararg ingredients: ItemLike
  ) {
    val builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, result)
    ingredients.forEach(builder::requires)
    val unlockIngredient = ingredients.first()
    builder
      .unlockedBy(getHasName(unlockIngredient), has(unlockIngredient))
      .save(exporter)
  }

  /** PRTS 加工站的精英材料配方；Minecraft 工作台承接原料数量，不模拟心情和龙门币费用。 */
  private fun addArknightsMaterialRecipes(exporter: RecipeOutput) {
    workshopRecipe(exporter, ModItem.ORIROCK_CUBE.item, ModItem.ORIROCK.item to 3)
    workshopRecipe(exporter, ModItem.ORIROCK_CLUSTER.item, ModItem.ORIROCK_CUBE.item to 5)
    workshopRecipe(exporter, ModItem.ORIROCK_CONCENTRATION.item, ModItem.ORIROCK_CLUSTER.item to 4)

    workshopRecipe(exporter, ModItem.POLYESTER.item, ModItem.ESTER_RAW.item to 3)
    workshopRecipe(exporter, ModItem.POLYESTER_GROUP.item, ModItem.POLYESTER.item to 4)
    workshopRecipe(
      exporter,
      ModItem.POLYESTER_BLOCK.item,
      ModItem.POLYESTER_GROUP.item to 2,
      ModItem.POLYKETON.item to 1,
      ModItem.TWISTED_ALCOHOL.item to 1
    )

    workshopRecipe(exporter, ModItem.SUGAR.item, ModItem.SUGAR_SUBSTITUTE.item to 3)
    workshopRecipe(exporter, ModItem.SUGAR_GROUP.item, ModItem.SUGAR.item to 4)
    workshopRecipe(
      exporter,
      ModItem.SUGAR_POLYMER.item,
      ModItem.SUGAR_GROUP.item to 2,
      ModItem.ORIRON_GROUP.item to 1,
      ModItem.MANGANESE_ORE.item to 1
    )

    workshopRecipe(exporter, ModItem.ORIRON.item, ModItem.ORIRON_SHARD.item to 3)
    workshopRecipe(exporter, ModItem.ORIRON_GROUP.item, ModItem.ORIRON.item to 4)
    workshopRecipe(
      exporter,
      ModItem.ORIRON_CLUSTER.item,
      ModItem.ORIRON_GROUP.item to 2,
      ModItem.DEVICE_GROUP.item to 1,
      ModItem.POLYESTER_GROUP.item to 1
    )

    workshopRecipe(exporter, ModItem.AKETON.item, ModItem.DIKETONE.item to 3)
    workshopRecipe(exporter, ModItem.POLYKETON.item, ModItem.AKETON.item to 4)
    workshopRecipe(
      exporter,
      ModItem.KETON_COLLOID.item,
      ModItem.POLYKETON.item to 2,
      ModItem.SUGAR_GROUP.item to 1,
      ModItem.MANGANESE_ORE.item to 1
    )

    workshopRecipe(exporter, ModItem.DEVICE_CORE.item, ModItem.DAMAGED_DEVICE.item to 3)
    workshopRecipe(exporter, ModItem.DEVICE_GROUP.item, ModItem.DEVICE_CORE.item to 4)
    workshopRecipe(
      exporter,
      ModItem.OPTIMIZED_DEVICE.item,
      ModItem.DEVICE_GROUP.item to 1,
      ModItem.ORIROCK_CLUSTER.item to 2,
      ModItem.GRINDSTONE.item to 1
    )

    workshopRecipe(
      exporter,
      ModItem.MANGANESE_TRIHYDRATE.item,
      ModItem.MANGANESE_ORE.item to 2,
      ModItem.POLYESTER_GROUP.item to 1,
      ModItem.TWISTED_ALCOHOL.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.GRINDSTONE_PENTAHYDRATE.item,
      ModItem.GRINDSTONE.item to 1,
      ModItem.ORIRON_GROUP.item to 1,
      ModItem.DEVICE_GROUP.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.RMA70_24.item,
      ModItem.RMA70_12.item to 1,
      ModItem.ORIROCK_CLUSTER.item to 2,
      ModItem.POLYKETON.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.WHITE_HORSE_KOHL.item,
      ModItem.TWISTED_ALCOHOL.item to 1,
      ModItem.SUGAR_GROUP.item to 1,
      ModItem.RMA70_12.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.CRYSTAL_GROUP.item,
      ModItem.CRYSTAL_ELEMENT.item to 2,
      ModItem.GEL.item to 1,
      ModItem.LOXIC_KOHL.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.CUTTING_FLUID_SOLUTION.item,
      ModItem.COMBINED_CUTTING_FLUID.item to 1,
      ModItem.CRYSTAL_ELEMENT.item to 1,
      ModItem.RMA70_12.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.REFINED_SOLVENT.item,
      ModItem.SEMI_SYNTHETIC_SOLVENT.item to 1,
      ModItem.COMBINED_CUTTING_FLUID.item to 1,
      ModItem.GEL.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.INCANDESCENT_ALLOY.item,
      ModItem.DEVICE_GROUP.item to 1,
      ModItem.GRINDSTONE.item to 1,
      ModItem.LOXIC_KOHL.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.COAGULATING_GEL.item,
      ModItem.ORIRON_GROUP.item to 1,
      ModItem.GEL.item to 1,
      ModItem.LOXIC_KOHL.item to 1
    )

    workshopRecipe(
      exporter,
      ModItem.CRYSTALLINE_CIRCUIT.item,
      ModItem.CRYSTAL_GROUP.item to 1,
      ModItem.COAGULATING_GEL.item to 2,
      ModItem.INCANDESCENT_ALLOY.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.BIPOLAR_NANOSHEET.item,
      ModItem.OPTIMIZED_DEVICE.item to 1,
      ModItem.WHITE_HORSE_KOHL.item to 2
    )
    workshopRecipe(
      exporter,
      ModItem.D32_STEEL.item,
      ModItem.MANGANESE_TRIHYDRATE.item to 1,
      ModItem.GRINDSTONE_PENTAHYDRATE.item to 1,
      ModItem.RMA70_24.item to 1
    )
    workshopRecipe(
      exporter,
      ModItem.POLYMER_AGENT.item,
      ModItem.ORIROCK_CONCENTRATION.item to 1,
      ModItem.ORIRON_CLUSTER.item to 1,
      ModItem.KETON_COLLOID.item to 1
    )

    // PRTS 制造站有两种源石碎片路线；一个龙门币物品在工作台中代表一次制造费用凭证。
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.ORIGINIUM_POWDER.item)
      .requires(ModItem.ORIROCK_CUBE.item, 2)
      .requires(ModItem.LMD.item)
      .unlockedBy(getHasName(ModItem.ORIROCK_CUBE.item), has(ModItem.ORIROCK_CUBE.item))
      .save(exporter, Zinecraft.REGISTRAR.id("originium_powder_from_orirock"))
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.ORIGINIUM_POWDER.item)
      .requires(ModItem.DEVICE_CORE.item)
      .requires(ModItem.LMD.item)
      .unlockedBy(getHasName(ModItem.DEVICE_CORE.item), has(ModItem.DEVICE_CORE.item))
      .save(exporter, Zinecraft.REGISTRAR.id("originium_powder_from_device"))
  }

  private fun addSkillSummaryRecipes(exporter: RecipeOutput) {
    workshopRecipe(exporter, ModItem.SKILL_SUMMARY_2.item, ModItem.SKILL_SUMMARY_1.item to 3)
    workshopRecipe(exporter, ModItem.SKILL_SUMMARY_3.item, ModItem.SKILL_SUMMARY_2.item to 3)
  }

  /** PRTS 芯片转换按职业配对，三枚原芯片转换为两枚目标芯片。 */
  private fun addChipConversionRecipes(exporter: RecipeOutput) {
    chipRecipe(exporter, ModItem.CHIP_VANGUARD.item, ModItem.CHIP_SUPPORT.item)
    chipRecipe(exporter, ModItem.CHIP_SUPPORT.item, ModItem.CHIP_VANGUARD.item)
    chipRecipe(exporter, ModItem.CHIP_GUARD.item, ModItem.CHIP_SPECIAL.item)
    chipRecipe(exporter, ModItem.CHIP_SPECIAL.item, ModItem.CHIP_GUARD.item)
    chipRecipe(exporter, ModItem.CHIP_DEFENDER.item, ModItem.CHIP_MEDIC.item)
    chipRecipe(exporter, ModItem.CHIP_MEDIC.item, ModItem.CHIP_DEFENDER.item)
    chipRecipe(exporter, ModItem.CHIP_SNIPER.item, ModItem.CHIP_CASTER.item)
    chipRecipe(exporter, ModItem.CHIP_CASTER.item, ModItem.CHIP_SNIPER.item)

    chipRecipe(exporter, ModItem.CHIP_VANGUARD_GROUP.item, ModItem.CHIP_SUPPORT_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_SUPPORT_GROUP.item, ModItem.CHIP_VANGUARD_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_GUARD_GROUP.item, ModItem.CHIP_SPECIAL_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_SPECIAL_GROUP.item, ModItem.CHIP_GUARD_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_DEFENDER_GROUP.item, ModItem.CHIP_MEDIC_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_MEDIC_GROUP.item, ModItem.CHIP_DEFENDER_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_SNIPER_GROUP.item, ModItem.CHIP_CASTER_GROUP.item)
    chipRecipe(exporter, ModItem.CHIP_CASTER_GROUP.item, ModItem.CHIP_SNIPER_GROUP.item)
  }

  private fun workshopRecipe(
    exporter: RecipeOutput,
    result: ItemLike,
    vararg ingredients: Pair<ItemLike, Int>
  ) {
    require(ingredients.isNotEmpty()) { "加工站配方必须至少有一种原料" }
    val builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
    ingredients.forEach { (item, count) -> builder.requires(item, count) }
    val unlockIngredient = ingredients.first().first
    builder.unlockedBy(getHasName(unlockIngredient), has(unlockIngredient)).save(exporter)
  }

  private fun chipRecipe(exporter: RecipeOutput, result: ItemLike, source: ItemLike) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 2)
      .requires(source, 3)
      .unlockedBy(getHasName(source), has(source))
      .save(exporter)
  }
}
