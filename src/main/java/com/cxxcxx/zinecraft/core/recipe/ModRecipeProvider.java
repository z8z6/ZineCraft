package com.cxxcxx.zinecraft.core.recipe;

import com.cxxcxx.zinecraft.api.world.feature.MaterialOre;
import com.cxxcxx.zinecraft.core.Zinecraft;
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
    for (MaterialOre ore : Zinecraft.WORLDGEN.features.materialOres) {
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
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.MAGIC_DUST, 4)
        .requires(ModItem.MAGIC_DUST, 2)
        .unlockedBy(getHasName(ModItem.MAGIC_DUST), has(ModItem.MAGIC_DUST))
        .save(output);

    oreSmelting(
        output,
        List.of(ModItem.MAGIC_DUST),
        RecipeCategory.MISC,
        ModItem.MAGIC_DUST,
        0.1F,
        20,
        "magic_dust_copy"
    );

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.PROTOCOL_ORIGINIUM)
        .requires(ModItem.ORIGINITE)
        .requires(ModItem.CRYSTALLINE_CIRCUIT)
        .requires(ModItem.DEVICE_GROUP)
        .unlockedBy(getHasName(ModItem.ORIGINITE), has(ModItem.ORIGINITE))
        .save(output);
  }

  private static void addNationFoodRecipes(RecipeOutput output) {
    nationFoodRecipe(
        output, ModItem.AEGIR_FRESH_SHELLCRAB_SASHIMI,
        Items.SALMON, Items.DRIED_KELP, Items.BEETROOT
    );
    nationFoodRecipe(
        output, ModItem.BOLIVAR_SMOKED_CAPSULE,
        Items.COOKED_CHICKEN, Items.HONEY_BOTTLE, Items.DRIED_KELP
    );
    nationFoodRecipe(
        output, ModItem.HIGASHI_NANO_KAPPO,
        Items.SALMON, Items.DRIED_KELP, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.DURIN_HONEY_SLUGPUDDING,
        Items.HONEY_BOTTLE, Items.COOKED_CHICKEN, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.COLUMBIA_ORIGINIUM_ROASTED_FOWL,
        Items.COOKED_CHICKEN, Items.BLAZE_POWDER, Items.PAPER
    );
    nationFoodRecipe(
        output, ModItem.KAZIMIERZ_KNIGHT_SUPPLEMENT,
        Items.HONEY_BOTTLE, Items.SUGAR, Items.GOLDEN_CARROT
    );
    nationFoodRecipe(
        output, ModItem.KAZDEL_CARTILAGE_TACK,
        Items.COOKED_BEEF, Items.BONE_MEAL, Items.WHEAT
    );
    nationFoodRecipe(
        output, ModItem.LATERANO_SACRED_TONE_SOUP,
        Items.MILK_BUCKET, Items.SUGAR, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.LEITHANIEN_MUSICAL_ROAST_EXTRACT,
        Items.COOKED_BEEF, Items.AMETHYST_SHARD, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.RIM_BILLITON_MINING_RATION,
        Items.COOKED_BEEF, Items.DRIED_KELP, Items.PAPER
    );
    nationFoodRecipe(
        output, ModItem.MINOS_POETRY_GEL,
        Items.HONEY_BOTTLE, Items.MELON_SLICE, Items.AMETHYST_SHARD
    );
    nationFoodRecipe(
        output, ModItem.SARGON_GRASS_CHEESE_GEL,
        Items.WHEAT, Items.MILK_BUCKET, Items.CACTUS
    );
    nationFoodRecipe(
        output, ModItem.SAMI_INSTANT_BONE_SOUP,
        Items.COOKED_RABBIT, Items.BONE, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.VICTORIA_CENTRAL_VALLEY_ROAST,
        Items.COOKED_BEEF, Items.BAKED_POTATO, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.URSUS_HAM_SUPPLEMENT,
        Items.COOKED_PORKCHOP, Items.SUGAR, Items.PAPER
    );
    nationFoodRecipe(
        output, ModItem.KJERAG_VALLEY_PIE,
        Items.BREAD, Items.COOKED_MUTTON, Items.SWEET_BERRIES
    );
    nationFoodRecipe(
        output, ModItem.SIRACUSA_STEW_GATHERING,
        Items.COOKED_BEEF, Items.CARROT, Items.POTATO, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.YAN_WASTELAND_MEAT_STIR_FRY,
        Items.COOKED_PORKCHOP, Items.CARROT, Items.BROWN_MUSHROOM, Items.BOWL
    );
    nationFoodRecipe(
        output, ModItem.IBERIA_CHITIN_CLUSTER,
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
    workshopRecipe(output, ModItem.ORIROCK_CUBE, amount(ModItem.ORIROCK, 3));
    workshopRecipe(output, ModItem.ORIROCK_CLUSTER, amount(ModItem.ORIROCK_CUBE, 5));
    workshopRecipe(output, ModItem.ORIROCK_CONCENTRATION, amount(ModItem.ORIROCK_CLUSTER, 4));
    workshopRecipe(output, ModItem.POLYESTER, amount(ModItem.ESTER_RAW, 3));
    workshopRecipe(output, ModItem.POLYESTER_GROUP, amount(ModItem.POLYESTER, 4));
    workshopRecipe(
        output, ModItem.POLYESTER_BLOCK,
        amount(ModItem.POLYESTER_GROUP, 2),
        amount(ModItem.POLYKETON, 1),
        amount(ModItem.TWISTED_ALCOHOL, 1)
    );
    workshopRecipe(output, ModItem.SUGAR, amount(ModItem.SUGAR_SUBSTITUTE, 3));
    workshopRecipe(output, ModItem.SUGAR_GROUP, amount(ModItem.SUGAR, 4));
    workshopRecipe(
        output, ModItem.SUGAR_POLYMER,
        amount(ModItem.SUGAR_GROUP, 2),
        amount(ModItem.ORIRON_GROUP, 1),
        amount(ModItem.MANGANESE_ORE, 1)
    );
    workshopRecipe(output, ModItem.ORIRON, amount(ModItem.ORIRON_SHARD, 3));
    workshopRecipe(output, ModItem.ORIRON_GROUP, amount(ModItem.ORIRON, 4));
    workshopRecipe(
        output, ModItem.ORIRON_CLUSTER,
        amount(ModItem.ORIRON_GROUP, 2),
        amount(ModItem.DEVICE_GROUP, 1),
        amount(ModItem.POLYESTER_GROUP, 1)
    );
    workshopRecipe(output, ModItem.AKETON, amount(ModItem.DIKETONE, 3));
    workshopRecipe(output, ModItem.POLYKETON, amount(ModItem.AKETON, 4));
    workshopRecipe(
        output, ModItem.KETON_COLLOID,
        amount(ModItem.POLYKETON, 2),
        amount(ModItem.SUGAR_GROUP, 1),
        amount(ModItem.MANGANESE_ORE, 1)
    );
    workshopRecipe(output, ModItem.DEVICE_CORE, amount(ModItem.DAMAGED_DEVICE, 3));
    workshopRecipe(output, ModItem.DEVICE_GROUP, amount(ModItem.DEVICE_CORE, 4));
    workshopRecipe(
        output, ModItem.OPTIMIZED_DEVICE,
        amount(ModItem.DEVICE_GROUP, 1),
        amount(ModItem.ORIROCK_CLUSTER, 2),
        amount(ModItem.GRINDSTONE, 1)
    );
    workshopRecipe(
        output, ModItem.MANGANESE_TRIHYDRATE,
        amount(ModItem.MANGANESE_ORE, 2),
        amount(ModItem.POLYESTER_GROUP, 1),
        amount(ModItem.TWISTED_ALCOHOL, 1)
    );
    workshopRecipe(
        output, ModItem.GRINDSTONE_PENTAHYDRATE,
        amount(ModItem.GRINDSTONE, 1),
        amount(ModItem.ORIRON_GROUP, 1),
        amount(ModItem.DEVICE_GROUP, 1)
    );
    workshopRecipe(
        output, ModItem.RMA70_24,
        amount(ModItem.RMA70_12, 1),
        amount(ModItem.ORIROCK_CLUSTER, 2),
        amount(ModItem.POLYKETON, 1)
    );
    workshopRecipe(
        output, ModItem.WHITE_HORSE_KOHL,
        amount(ModItem.TWISTED_ALCOHOL, 1),
        amount(ModItem.SUGAR_GROUP, 1),
        amount(ModItem.RMA70_12, 1)
    );
    workshopRecipe(
        output, ModItem.CRYSTAL_GROUP,
        amount(ModItem.CRYSTAL_ELEMENT, 2),
        amount(ModItem.GEL, 1),
        amount(ModItem.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.CUTTING_FLUID_SOLUTION,
        amount(ModItem.COMBINED_CUTTING_FLUID, 1),
        amount(ModItem.CRYSTAL_ELEMENT, 1),
        amount(ModItem.RMA70_12, 1)
    );
    workshopRecipe(
        output, ModItem.REFINED_SOLVENT,
        amount(ModItem.SEMI_SYNTHETIC_SOLVENT, 1),
        amount(ModItem.COMBINED_CUTTING_FLUID, 1),
        amount(ModItem.GEL, 1)
    );
    workshopRecipe(
        output, ModItem.INCANDESCENT_ALLOY,
        amount(ModItem.DEVICE_GROUP, 1),
        amount(ModItem.GRINDSTONE, 1),
        amount(ModItem.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.COAGULATING_GEL,
        amount(ModItem.ORIRON_GROUP, 1),
        amount(ModItem.GEL, 1),
        amount(ModItem.LOXIC_KOHL, 1)
    );
    workshopRecipe(
        output, ModItem.CRYSTALLINE_CIRCUIT,
        amount(ModItem.CRYSTAL_GROUP, 1),
        amount(ModItem.COAGULATING_GEL, 2),
        amount(ModItem.INCANDESCENT_ALLOY, 1)
    );
    workshopRecipe(
        output, ModItem.BIPOLAR_NANOSHEET,
        amount(ModItem.OPTIMIZED_DEVICE, 1),
        amount(ModItem.WHITE_HORSE_KOHL, 2)
    );
    workshopRecipe(
        output, ModItem.D32_STEEL,
        amount(ModItem.MANGANESE_TRIHYDRATE, 1),
        amount(ModItem.GRINDSTONE_PENTAHYDRATE, 1),
        amount(ModItem.RMA70_24, 1)
    );
    workshopRecipe(
        output, ModItem.POLYMER_AGENT,
        amount(ModItem.ORIROCK_CONCENTRATION, 1),
        amount(ModItem.ORIRON_CLUSTER, 1),
        amount(ModItem.KETON_COLLOID, 1)
    );

    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.ORIGINIUM_POWDER)
        .requires(ModItem.ORIROCK_CUBE, 2)
        .requires(ModItem.LMD)
        .unlockedBy(getHasName(ModItem.ORIROCK_CUBE), has(ModItem.ORIROCK_CUBE))
        .save(output, Zinecraft.REGISTRAR.id("originium_powder_from_orirock"));
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItem.ORIGINIUM_POWDER)
        .requires(ModItem.DEVICE_CORE)
        .requires(ModItem.LMD)
        .unlockedBy(getHasName(ModItem.DEVICE_CORE), has(ModItem.DEVICE_CORE))
        .save(output, Zinecraft.REGISTRAR.id("originium_powder_from_device"));
  }

  private static void addSkillSummaryRecipes(RecipeOutput output) {
    workshopRecipe(output, ModItem.SKILL_SUMMARY_2, amount(ModItem.SKILL_SUMMARY_1, 3));
    workshopRecipe(output, ModItem.SKILL_SUMMARY_3, amount(ModItem.SKILL_SUMMARY_2, 3));
  }

  private static void addChipConversionRecipes(RecipeOutput output) {
    chipRecipe(output, ModItem.CHIP_VANGUARD, ModItem.CHIP_SUPPORT);
    chipRecipe(output, ModItem.CHIP_SUPPORT, ModItem.CHIP_VANGUARD);
    chipRecipe(output, ModItem.CHIP_GUARD, ModItem.CHIP_SPECIAL);
    chipRecipe(output, ModItem.CHIP_SPECIAL, ModItem.CHIP_GUARD);
    chipRecipe(output, ModItem.CHIP_DEFENDER, ModItem.CHIP_MEDIC);
    chipRecipe(output, ModItem.CHIP_MEDIC, ModItem.CHIP_DEFENDER);
    chipRecipe(output, ModItem.CHIP_SNIPER, ModItem.CHIP_CASTER);
    chipRecipe(output, ModItem.CHIP_CASTER, ModItem.CHIP_SNIPER);
    chipRecipe(output, ModItem.CHIP_VANGUARD_GROUP, ModItem.CHIP_SUPPORT_GROUP);
    chipRecipe(output, ModItem.CHIP_SUPPORT_GROUP, ModItem.CHIP_VANGUARD_GROUP);
    chipRecipe(output, ModItem.CHIP_GUARD_GROUP, ModItem.CHIP_SPECIAL_GROUP);
    chipRecipe(output, ModItem.CHIP_SPECIAL_GROUP, ModItem.CHIP_GUARD_GROUP);
    chipRecipe(output, ModItem.CHIP_DEFENDER_GROUP, ModItem.CHIP_MEDIC_GROUP);
    chipRecipe(output, ModItem.CHIP_MEDIC_GROUP, ModItem.CHIP_DEFENDER_GROUP);
    chipRecipe(output, ModItem.CHIP_SNIPER_GROUP, ModItem.CHIP_CASTER_GROUP);
    chipRecipe(output, ModItem.CHIP_CASTER_GROUP, ModItem.CHIP_SNIPER_GROUP);
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
