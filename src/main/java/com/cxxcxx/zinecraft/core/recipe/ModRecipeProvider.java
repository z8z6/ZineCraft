package com.cxxcxx.zinecraft.core.recipe;

import com.cxxcxx.zinecraft.api.world.feature.MaterialOre;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.block.ModBlock;
import com.cxxcxx.zinecraft.core.item.ModFood;
import com.cxxcxx.zinecraft.core.item.ModItem;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class ModRecipeProvider extends RecipeProvider {
  public ModRecipeProvider(PackOutput output, CompletableFuture<Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  private static void addOreCookingRecipes(RecipeOutput output) {
    for (MaterialOre ore : ModBlock.INSTANCE.ORES) {
      oreCooking(output, ore.block(), ore.drop(), ore.cookingGroup());
    }
  }

  private static void oreCooking(RecipeOutput output, ItemLike ore, ItemLike result, String group) {
    Ingredient ingredient = Ingredient.of(ore);
    SimpleCookingRecipeBuilder.smelting(ingredient, RecipeCategory.MISC, result, 0.7F, 200)
        .group(group)
        .unlockedBy(getHasName(ore), has(ore))
        .save(output, Zinecraft.REGISTRAR.id(group + "_from_smelting"));
    SimpleCookingRecipeBuilder.blasting(ingredient, RecipeCategory.MISC, result, 0.7F, 100)
        .group(group)
        .unlockedBy(getHasName(ore), has(ore))
        .save(output, Zinecraft.REGISTRAR.id(group + "_from_blasting"));
  }

  private static void addVanillaUtilityRecipes(RecipeOutput output) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT)
        .requires(Items.COARSE_DIRT)
        .unlockedBy(getHasName(Items.COARSE_DIRT), has(Items.COARSE_DIRT))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
        .pattern("ll")
        .pattern("ll")
        .define('l', ItemTags.LOGS)
        .group("multi_bench")
        .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
        .save(output);

    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LOOM, 4)
        .pattern("ww")
        .pattern("ll")
        .define('w', ItemTags.WOOL)
        .define('l', ItemTags.LOGS)
        .group("multi_bench")
        .unlockedBy(getHasName(Items.LOOM), has(Items.LOOM))
        .save(output);

    doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON))
        .unlockedBy(getHasName(Items.OAK_BUTTON), has(Items.OAK_BUTTON))
        .save(output);

    oreSmelting(
        output,
        List.of(Items.BREAD, Items.COOKIE, Items.HAY_BLOCK),
        RecipeCategory.FOOD,
        Items.WHEAT,
        0.1F,
        300,
        "food_to_wheat"
    );
  }

  private static void addCoreRecipes(RecipeOutput output) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.INSTANCE.MAGIC_DUST, 4)
        .requires(ModItem.INSTANCE.MAGIC_DUST, 2)
        .unlockedBy(getHasName(ModItem.INSTANCE.MAGIC_DUST), has(ModItem.INSTANCE.MAGIC_DUST))
        .save(output);

    oreSmelting(
        output,
        List.of(ModItem.INSTANCE.MAGIC_DUST),
        RecipeCategory.MISC,
        ModItem.INSTANCE.MAGIC_DUST,
        0.1F,
        20,
        "magic_dust_copy"
    );

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.INSTANCE.PROTOCOL_ORIGINIUM)
        .requires(ModItem.INSTANCE.ORIGINITE)
        .requires(ModItem.INSTANCE.CRYSTALLINE_CIRCUIT)
        .requires(ModItem.INSTANCE.DEVICE_GROUP)
        .unlockedBy(getHasName(ModItem.INSTANCE.ORIGINITE), has(ModItem.INSTANCE.ORIGINITE))
        .save(output);
  }

  private static void addNationFoodRecipes(RecipeOutput output) {
    nationFoodRecipe(
        output, ModFood.AEGIR_FRESH_SHELLCRAB_SASHIMI,
        Items.SALMON, Items.DRIED_KELP, Items.BEETROOT
    );
    nationFoodRecipe(
        output, ModFood.BOLIVAR_SMOKED_CAPSULE,
        Items.COOKED_CHICKEN, Items.HONEY_BOTTLE, Items.DRIED_KELP
    );
    nationFoodRecipe(
        output, ModFood.HIGASHI_NANO_KAPPO,
        Items.SALMON, Items.DRIED_KELP, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.DURIN_HONEY_SLUGPUDDING,
        Items.HONEY_BOTTLE, Items.COOKED_CHICKEN, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.COLUMBIA_ORIGINIUM_ROASTED_FOWL,
        Items.COOKED_CHICKEN, Items.BLAZE_POWDER, Items.PAPER
    );
    nationFoodRecipe(
        output, ModFood.KAZIMIERZ_KNIGHT_SUPPLEMENT,
        Items.HONEY_BOTTLE, Items.SUGAR, Items.GOLDEN_CARROT
    );
    nationFoodRecipe(
        output, ModFood.KAZDEL_CARTILAGE_TACK,
        Items.COOKED_BEEF, Items.BONE_MEAL, Items.WHEAT
    );
    nationFoodRecipe(
        output, ModFood.LATERANO_SACRED_TONE_SOUP,
        Items.MILK_BUCKET, Items.SUGAR, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.LEITHANIEN_MUSICAL_ROAST_EXTRACT,
        Items.COOKED_BEEF, Items.AMETHYST_SHARD, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.RIM_BILLITON_MINING_RATION,
        Items.COOKED_BEEF, Items.DRIED_KELP, Items.PAPER
    );
    nationFoodRecipe(
        output, ModFood.MINOS_POETRY_GEL,
        Items.HONEY_BOTTLE, Items.MELON_SLICE, Items.AMETHYST_SHARD
    );
    nationFoodRecipe(
        output, ModFood.SARGON_GRASS_CHEESE_GEL,
        Items.WHEAT, Items.MILK_BUCKET, Items.CACTUS
    );
    nationFoodRecipe(
        output, ModFood.SAMI_INSTANT_BONE_SOUP,
        Items.COOKED_RABBIT, Items.BONE, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.VICTORIA_CENTRAL_VALLEY_ROAST,
        Items.COOKED_BEEF, Items.BAKED_POTATO, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.URSUS_HAM_SUPPLEMENT,
        Items.COOKED_PORKCHOP, Items.SUGAR, Items.PAPER
    );
    nationFoodRecipe(
        output, ModFood.KJERAG_VALLEY_PIE,
        Items.BREAD, Items.COOKED_MUTTON, Items.SWEET_BERRIES
    );
    nationFoodRecipe(
        output, ModFood.SIRACUSA_STEW_GATHERING,
        Items.COOKED_BEEF, Items.CARROT, Items.POTATO, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.YAN_WASTELAND_MEAT_STIR_FRY,
        Items.COOKED_PORKCHOP, Items.CARROT, Items.BROWN_MUSHROOM, Items.BOWL
    );
    nationFoodRecipe(
        output, ModFood.IBERIA_CHITIN_CLUSTER,
        Items.NAUTILUS_SHELL, Items.COOKED_COD, Items.DRIED_KELP
    );
  }

  private static void nationFoodRecipe(RecipeOutput output, ItemLike result, ItemLike... ingredients) {
    if (ingredients.length == 0) {
      throw new IllegalArgumentException("国家食物配方必须至少有一种原料");
    }
    ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, result);
    for (ItemLike ingredient : ingredients) {
      recipe.requires(ingredient);
    }
    recipe.unlockedBy(getHasName(ingredients[0]), has(ingredients[0])).save(output);
  }

  private static void addArknightsMaterialRecipes(RecipeOutput output) {
    workshopRecipe(output, ModItem.INSTANCE.ORIROCK_CUBE, amount(ModItem.INSTANCE.ORIROCK, 3));
    workshopRecipe(output, ModItem.INSTANCE.ORIROCK_CLUSTER, amount(ModItem.INSTANCE.ORIROCK_CUBE, 5));
    workshopRecipe(output, ModItem.INSTANCE.ORIROCK_CONCENTRATION, amount(ModItem.INSTANCE.ORIROCK_CLUSTER, 4));
    workshopRecipe(output, ModItem.INSTANCE.POLYESTER, amount(ModItem.INSTANCE.ESTER_RAW, 3));
    workshopRecipe(output, ModItem.INSTANCE.POLYESTER_GROUP, amount(ModItem.INSTANCE.POLYESTER, 4));
    workshopRecipe(
        output, ModItem.INSTANCE.POLYESTER_BLOCK,
        amount(ModItem.INSTANCE.POLYESTER_GROUP, 2),
        amount(ModItem.INSTANCE.POLYKETON, 1),
        amount(ModItem.INSTANCE.TWISTED_ALCOHOL, 1)
    );
    workshopRecipe(output, ModItem.INSTANCE.SUGAR, amount(ModItem.INSTANCE.SUGAR_SUBSTITUTE, 3));
    workshopRecipe(output, ModItem.INSTANCE.SUGAR_GROUP, amount(ModItem.INSTANCE.SUGAR, 4));
    workshopRecipe(
        output, ModItem.INSTANCE.SUGAR_POLYMER,
        amount(ModItem.INSTANCE.SUGAR_GROUP, 2),
        amount(ModItem.INSTANCE.ORIRON_GROUP, 1),
        amount(ModItem.INSTANCE.MANGANESE_ORE, 1)
    );
    workshopRecipe(output, ModItem.INSTANCE.ORIRON, amount(ModItem.INSTANCE.ORIRON_SHARD, 3));
    workshopRecipe(output, ModItem.INSTANCE.ORIRON_GROUP, amount(ModItem.INSTANCE.ORIRON, 4));
    workshopRecipe(
        output, ModItem.INSTANCE.ORIRON_CLUSTER,
        amount(ModItem.INSTANCE.ORIRON_GROUP, 2),
        amount(ModItem.INSTANCE.DEVICE_GROUP, 1),
        amount(ModItem.INSTANCE.POLYESTER_GROUP, 1)
    );
    workshopRecipe(output, ModItem.INSTANCE.AKETON, amount(ModItem.INSTANCE.DIKETONE, 3));
    workshopRecipe(output, ModItem.INSTANCE.POLYKETON, amount(ModItem.INSTANCE.AKETON, 4));
    workshopRecipe(
        output, ModItem.INSTANCE.KETON_COLLOID,
        amount(ModItem.INSTANCE.POLYKETON, 2),
        amount(ModItem.INSTANCE.SUGAR_GROUP, 1),
        amount(ModItem.INSTANCE.MANGANESE_ORE, 1)
    );
    workshopRecipe(output, ModItem.INSTANCE.DEVICE_CORE, amount(ModItem.INSTANCE.DAMAGED_DEVICE, 3));
    workshopRecipe(output, ModItem.INSTANCE.DEVICE_GROUP, amount(ModItem.INSTANCE.DEVICE_CORE, 4));
    workshopRecipe(
        output, ModItem.INSTANCE.OPTIMIZED_DEVICE,
        amount(ModItem.INSTANCE.DEVICE_GROUP, 1),
        amount(ModItem.INSTANCE.ORIROCK_CLUSTER, 2),
        amount(ModItem.INSTANCE.GRINDSTONE, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.MANGANESE_TRIHYDRATE,
        amount(ModItem.INSTANCE.MANGANESE_ORE, 2),
        amount(ModItem.INSTANCE.POLYESTER_GROUP, 1),
        amount(ModItem.INSTANCE.TWISTED_ALCOHOL, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.GRINDSTONE_PENTAHYDRATE,
        amount(ModItem.INSTANCE.GRINDSTONE, 1),
        amount(ModItem.INSTANCE.ORIRON_GROUP, 1),
        amount(ModItem.INSTANCE.DEVICE_GROUP, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.RMA70_24,
        amount(ModItem.INSTANCE.RMA70_12, 1),
        amount(ModItem.INSTANCE.ORIROCK_CLUSTER, 2),
        amount(ModItem.INSTANCE.POLYKETON, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.WHITE_HORSE_KOHL,
        amount(ModItem.INSTANCE.TWISTED_ALCOHOL, 1),
        amount(ModItem.INSTANCE.SUGAR_GROUP, 1),
        amount(ModItem.INSTANCE.RMA70_12, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.CRYSTAL_GROUP,
        amount(ModItem.INSTANCE.CRYSTAL_ELEMENT, 2),
        amount(ModItem.INSTANCE.GEL, 1),
        amount(ModItem.INSTANCE.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.CUTTING_FLUID_SOLUTION,
        amount(ModItem.INSTANCE.COMBINED_CUTTING_FLUID, 1),
        amount(ModItem.INSTANCE.CRYSTAL_ELEMENT, 1),
        amount(ModItem.INSTANCE.RMA70_12, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.REFINED_SOLVENT,
        amount(ModItem.INSTANCE.SEMI_SYNTHETIC_SOLVENT, 1),
        amount(ModItem.INSTANCE.COMBINED_CUTTING_FLUID, 1),
        amount(ModItem.INSTANCE.GEL, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.INCANDESCENT_ALLOY,
        amount(ModItem.INSTANCE.DEVICE_GROUP, 1),
        amount(ModItem.INSTANCE.GRINDSTONE, 1),
        amount(ModItem.INSTANCE.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.COAGULATING_GEL,
        amount(ModItem.INSTANCE.ORIRON_GROUP, 1),
        amount(ModItem.INSTANCE.GEL, 1),
        amount(ModItem.INSTANCE.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.CRYSTALLINE_CIRCUIT,
        amount(ModItem.INSTANCE.CRYSTAL_GROUP, 1),
        amount(ModItem.INSTANCE.COAGULATING_GEL, 2),
        amount(ModItem.INSTANCE.INCANDESCENT_ALLOY, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.BIPOLAR_NANOSHEET,
        amount(ModItem.INSTANCE.OPTIMIZED_DEVICE, 1),
        amount(ModItem.INSTANCE.WHITE_HORSE_KOHL, 2)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.D32_STEEL,
        amount(ModItem.INSTANCE.MANGANESE_TRIHYDRATE, 1),
        amount(ModItem.INSTANCE.GRINDSTONE_PENTAHYDRATE, 1),
        amount(ModItem.INSTANCE.RMA70_24, 1)
    );
    workshopRecipe(
        output, ModItem.INSTANCE.POLYMER_AGENT,
        amount(ModItem.INSTANCE.ORIROCK_CONCENTRATION, 1),
        amount(ModItem.INSTANCE.ORIRON_CLUSTER, 1),
        amount(ModItem.INSTANCE.KETON_COLLOID, 1)
    );

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.INSTANCE.ORIGINIUM_POWDER)
        .requires(ModItem.INSTANCE.ORIROCK_CUBE, 2)
        .requires(ModItem.INSTANCE.LMD)
        .unlockedBy(getHasName(ModItem.INSTANCE.ORIROCK_CUBE), has(ModItem.INSTANCE.ORIROCK_CUBE))
        .save(output, Zinecraft.REGISTRAR.id("originium_powder_from_orirock"));
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.INSTANCE.ORIGINIUM_POWDER)
        .requires(ModItem.INSTANCE.DEVICE_CORE)
        .requires(ModItem.INSTANCE.LMD)
        .unlockedBy(getHasName(ModItem.INSTANCE.DEVICE_CORE), has(ModItem.INSTANCE.DEVICE_CORE))
        .save(output, Zinecraft.REGISTRAR.id("originium_powder_from_device"));
  }

  private static void addSkillSummaryRecipes(RecipeOutput output) {
    workshopRecipe(output, ModItem.INSTANCE.SKILL_SUMMARY_2, amount(ModItem.INSTANCE.SKILL_SUMMARY_1, 3));
    workshopRecipe(output, ModItem.INSTANCE.SKILL_SUMMARY_3, amount(ModItem.INSTANCE.SKILL_SUMMARY_2, 3));
  }

  private static void addChipConversionRecipes(RecipeOutput output) {
    chipRecipe(output, ModItem.INSTANCE.CHIP_VANGUARD, ModItem.INSTANCE.CHIP_SUPPORT);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SUPPORT, ModItem.INSTANCE.CHIP_VANGUARD);
    chipRecipe(output, ModItem.INSTANCE.CHIP_GUARD, ModItem.INSTANCE.CHIP_SPECIAL);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SPECIAL, ModItem.INSTANCE.CHIP_GUARD);
    chipRecipe(output, ModItem.INSTANCE.CHIP_DEFENDER, ModItem.INSTANCE.CHIP_MEDIC);
    chipRecipe(output, ModItem.INSTANCE.CHIP_MEDIC, ModItem.INSTANCE.CHIP_DEFENDER);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SNIPER, ModItem.INSTANCE.CHIP_CASTER);
    chipRecipe(output, ModItem.INSTANCE.CHIP_CASTER, ModItem.INSTANCE.CHIP_SNIPER);
    chipRecipe(output, ModItem.INSTANCE.CHIP_VANGUARD_GROUP, ModItem.INSTANCE.CHIP_SUPPORT_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SUPPORT_GROUP, ModItem.INSTANCE.CHIP_VANGUARD_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_GUARD_GROUP, ModItem.INSTANCE.CHIP_SPECIAL_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SPECIAL_GROUP, ModItem.INSTANCE.CHIP_GUARD_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_DEFENDER_GROUP, ModItem.INSTANCE.CHIP_MEDIC_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_MEDIC_GROUP, ModItem.INSTANCE.CHIP_DEFENDER_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_SNIPER_GROUP, ModItem.INSTANCE.CHIP_CASTER_GROUP);
    chipRecipe(output, ModItem.INSTANCE.CHIP_CASTER_GROUP, ModItem.INSTANCE.CHIP_SNIPER_GROUP);
  }

  private static void workshopRecipe(
      RecipeOutput output,
      ItemLike result,
      IngredientAmount... ingredients
  ) {
    if (ingredients.length == 0) {
      throw new IllegalArgumentException("加工站配方必须至少有一种原料");
    }
    ShapelessRecipeBuilder recipe = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result);
    for (IngredientAmount ingredient : ingredients) {
      recipe.requires(ingredient.item(), ingredient.count());
    }
    ItemLike unlockIngredient = ingredients[0].item();
    recipe.unlockedBy(getHasName(unlockIngredient), has(unlockIngredient)).save(output);
  }

  private static void chipRecipe(RecipeOutput output, ItemLike result, ItemLike source) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 2)
        .requires(source, 3)
        .unlockedBy(getHasName(source), has(source))
        .save(output);
  }

  private static IngredientAmount amount(ItemLike item, int count) {
    return new IngredientAmount(item, count);
  }

  @Override
  public void buildRecipes(RecipeOutput output) {
    addOreCookingRecipes(output);
    addVanillaUtilityRecipes(output);
    addCoreRecipes(output);
    addArknightsMaterialRecipes(output);
    addSkillSummaryRecipes(output);
    addChipConversionRecipes(output);
    addNationFoodRecipes(output);
    Zinecraft.RECIPES.generate(output);
  }

  private record IngredientAmount(ItemLike item, int count) {
    private IngredientAmount {
      Objects.requireNonNull(item, "item");
      if (count <= 0) {
        throw new IllegalArgumentException("配方原料数量必须大于 0");
      }
    }
  }
}
