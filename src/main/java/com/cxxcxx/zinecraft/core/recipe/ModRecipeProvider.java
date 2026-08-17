package com.cxxcxx.zinecraft.core.recipe;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.block.MaterialOres;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.item.NationFoods;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public final class ModRecipeProvider extends RecipeProvider {
  public ModRecipeProvider(@NotNull PackOutput output, @NotNull CompletableFuture<Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  public void buildRecipes(@NotNull RecipeOutput exporter) {
    this.addMaterialOreCookingRecipes(exporter);
    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike) Items.DIRT)
        .requires((ItemLike) Items.COARSE_DIRT)
        .unlockedBy(RecipeHelpers.getHasName((ItemLike) Items.COARSE_DIRT), RecipeHelpers.has((ItemLike) Items.COARSE_DIRT))
        .save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike) Items.CRAFTING_TABLE, 4)
        .pattern("ll")
        .pattern("ll")
        .define('l', ItemTags.LOGS)
        .group("multi_bench")
        .unlockedBy(RecipeHelpers.getHasName((ItemLike) Items.CRAFTING_TABLE), RecipeHelpers.has((ItemLike) Items.CRAFTING_TABLE))
        .save(exporter);
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike) Items.LOOM, 4)
        .pattern("ww")
        .pattern("ll")
        .define('w', ItemTags.WOOL)
        .define('l', ItemTags.LOGS)
        .group("multi_bench")
        .unlockedBy(RecipeHelpers.getHasName((ItemLike) Items.LOOM), RecipeHelpers.has((ItemLike) Items.LOOM))
        .save(exporter);
    ItemLike itemLike = (ItemLike) Items.OAK_DOOR;
    ItemLike[] itemLikes = new ItemLike[]{Items.OAK_BUTTON};
    RecipeHelpers.doorBuilder(itemLike, Ingredient.of(itemLikes))
        .unlockedBy(RecipeHelpers.getHasName((ItemLike) Items.OAK_BUTTON), RecipeHelpers.has((ItemLike) Items.OAK_BUTTON))
        .save(exporter);
    Item[] items = new Item[]{Items.BREAD, Items.COOKIE, Items.HAY_BLOCK};
    RecipeHelpers.oreSmelting(exporter, java.util.List.of(items), RecipeCategory.FOOD, (ItemLike) Items.WHEAT, 0.1F, 300, "food_to_wheat");
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.MAGIC_DUST.getItem(), 4)
        .requires((ItemLike) ModItem.MAGIC_DUST.getItem(), 2)
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.MAGIC_DUST.getItem()),
            RecipeHelpers.has((ItemLike) ModItem.MAGIC_DUST.getItem())
        )
        .save(exporter);
    RecipeHelpers.oreSmelting(
        exporter,
        java.util.List.of(ModItem.MAGIC_DUST.getItem()),
        RecipeCategory.MISC,
        (ItemLike) ModItem.MAGIC_DUST.getItem(),
        0.1F,
        20,
        "magic_dust_copy"
    );
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.PROTOCOL_ORIGINIUM.getItem())
        .requires((ItemLike) ModItem.ORIGINITE.getItem())
        .requires((ItemLike) ModItem.CRYSTALLINE_CIRCUIT.getItem())
        .requires((ItemLike) ModItem.DEVICE_GROUP.getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.ORIGINITE.getItem()),
            RecipeHelpers.has((ItemLike) ModItem.ORIGINITE.getItem())
        )
        .save(exporter);
    this.addArknightsMaterialRecipes(exporter);
    this.addSkillSummaryRecipes(exporter);
    this.addChipConversionRecipes(exporter);
    ItemLike itemLike1 = NationFoods.AEGIR_FRESH_SHELLCRAB_SASHIMI;
    itemLikes = new ItemLike[3];
    Item item = Items.SALMON;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.BEETROOT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.BOLIVAR_SMOKED_CAPSULE;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_CHICKEN;
    itemLikes[0] = item;
    item = Items.HONEY_BOTTLE;
    itemLikes[1] = item;
    item = Items.DRIED_KELP;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.HIGASHI_NANO_KAPPO;
    itemLikes = new ItemLike[3];
    item = Items.SALMON;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.DURIN_HONEY_SLUGPUDDING;
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.COOKED_CHICKEN;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.COLUMBIA_ORIGINIUM_ROASTED_FOWL;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_CHICKEN;
    itemLikes[0] = item;
    item = Items.BLAZE_POWDER;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.KAZIMIERZ_KNIGHT_SUPPLEMENT;
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.GOLDEN_CARROT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.KAZDEL_CARTILAGE_TACK;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.BONE_MEAL;
    itemLikes[1] = item;
    item = Items.WHEAT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.LATERANO_SACRED_TONE_SOUP;
    itemLikes = new ItemLike[3];
    item = Items.MILK_BUCKET;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.LEITHANIEN_MUSICAL_ROAST_EXTRACT;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.AMETHYST_SHARD;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.RIM_BILLITON_MINING_RATION;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.MINOS_POETRY_GEL;
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.MELON_SLICE;
    itemLikes[1] = item;
    item = Items.AMETHYST_SHARD;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.SARGON_GRASS_CHEESE_GEL;
    itemLikes = new ItemLike[3];
    item = Items.WHEAT;
    itemLikes[0] = item;
    item = Items.MILK_BUCKET;
    itemLikes[1] = item;
    item = Items.CACTUS;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.SAMI_INSTANT_BONE_SOUP;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_RABBIT;
    itemLikes[0] = item;
    item = Items.BONE;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.VICTORIA_CENTRAL_VALLEY_ROAST;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.BAKED_POTATO;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.URSUS_HAM_SUPPLEMENT;
    itemLikes = new ItemLike[3];
    item = Items.COOKED_PORKCHOP;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.KJERAG_VALLEY_PIE;
    itemLikes = new ItemLike[3];
    item = Items.BREAD;
    itemLikes[0] = item;
    item = Items.COOKED_MUTTON;
    itemLikes[1] = item;
    item = Items.SWEET_BERRIES;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.SIRACUSA_STEW_GATHERING;
    itemLikes = new ItemLike[4];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.CARROT;
    itemLikes[1] = item;
    item = Items.POTATO;
    itemLikes[2] = item;
    item = Items.BOWL;
    itemLikes[3] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.YAN_WASTELAND_MEAT_STIR_FRY;
    itemLikes = new ItemLike[4];
    item = Items.COOKED_PORKCHOP;
    itemLikes[0] = item;
    item = Items.CARROT;
    itemLikes[1] = item;
    item = Items.BROWN_MUSHROOM;
    itemLikes[2] = item;
    item = Items.BOWL;
    itemLikes[3] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.IBERIA_CHITIN_CLUSTER;
    itemLikes = new ItemLike[3];
    item = Items.NAUTILUS_SHELL;
    itemLikes[0] = item;
    item = Items.COOKED_COD;
    itemLikes[1] = item;
    item = Items.DRIED_KELP;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    Zinecraft.RECIPES.generate(exporter);
  }

  /**
   * 矿石方块可通过普通熔炉或高炉还原为其直接掉落的基础材料。
   */
  private void addMaterialOreCookingRecipes(RecipeOutput exporter) {
    this.oreCooking(exporter, MaterialOres.ORIGINITE_ORE.getBlock(), ModItem.ORIGINITE.getItem(), "originite");
    this.oreCooking(exporter, MaterialOres.ORIROCK_ORE.getBlock(), ModItem.ORIROCK.getItem(), "orirock");
    this.oreCooking(exporter, MaterialOres.ORIRON_ORE.getBlock(), ModItem.ORIRON_SHARD.getItem(), "oriron_shard");
    this.oreCooking(exporter, MaterialOres.MANGANESE_ORE.getBlock(), ModItem.MANGANESE_ORE.getItem(), "manganese_ore");
    this.oreCooking(exporter, MaterialOres.GRINDSTONE_ORE.getBlock(), ModItem.GRINDSTONE.getItem(), "grindstone");
    this.oreCooking(exporter, MaterialOres.RMA70_ORE.getBlock(), ModItem.RMA70_12.getItem(), "rma70_12");
    this.oreCooking(exporter, MaterialOres.CRYSTAL_ELEMENT_ORE.getBlock(), ModItem.CRYSTAL_ELEMENT.getItem(), "crystal_element");
    this.oreCooking(exporter, MaterialOres.LOXIC_KOHL_ORE.getBlock(), ModItem.LOXIC_KOHL.getItem(), "loxic_kohl");
  }

  private void oreCooking(RecipeOutput exporter, ItemLike ore, ItemLike result, String group) {
    var ingredient = Ingredient.of(ore);
    SimpleCookingRecipeBuilder.smelting(ingredient, RecipeCategory.MISC, result, 0.7F, 200)
        .group(group)
        .unlockedBy(RecipeHelpers.getHasName(ore), RecipeHelpers.has(ore))
        .save(exporter, Zinecraft.REGISTRAR.id(group + "_from_smelting"));
    SimpleCookingRecipeBuilder.blasting(ingredient, RecipeCategory.MISC, result, 0.7F, 100)
        .group(group)
        .unlockedBy(RecipeHelpers.getHasName(ore), RecipeHelpers.has(ore))
        .save(exporter, Zinecraft.REGISTRAR.id(group + "_from_blasting"));
  }

  private final void nationFoodRecipe(RecipeOutput exporter, ItemLike result, ItemLike... ingredients) {
    ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, result);
    ItemLike[] unlockIngredient = ingredients;
    ShapelessRecipeBuilder shapelessRecipeBuilder1 = shapelessRecipeBuilder;
    int i = 0;
    int j = 0;

    for (int k = unlockIngredient.length; j < k; j++) {
      Object object = unlockIngredient[j];
      ItemLike itemLike = (ItemLike) object;
      int l = 0;
      shapelessRecipeBuilder1.requires(itemLike);
    }

    ItemLike itemLike1 = (ItemLike) ingredients[0];
    shapelessRecipeBuilder.unlockedBy(RecipeHelpers.getHasName(itemLike1), RecipeHelpers.has(itemLike1)).save(exporter);
  }

  private final void addArknightsMaterialRecipes(RecipeOutput exporter) {
    ItemLike itemLike = (ItemLike) ModItem.ORIROCK_CUBE.getItem();
    Pair[] pairs = new Pair[]{Pair.of(ModItem.ORIROCK.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.ORIROCK_CLUSTER.getItem();
    pairs = new Pair[]{Pair.of(ModItem.ORIROCK_CUBE.getItem(), 5)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.ORIROCK_CONCENTRATION.getItem();
    pairs = new Pair[]{Pair.of(ModItem.ORIROCK_CLUSTER.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.POLYESTER.getItem();
    pairs = new Pair[]{Pair.of(ModItem.ESTER_RAW.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.POLYESTER_GROUP.getItem();
    pairs = new Pair[]{Pair.of(ModItem.POLYESTER.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.POLYESTER_BLOCK.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.POLYESTER_GROUP.getItem(), 2),
        Pair.of(ModItem.POLYKETON.getItem(), 1),
        Pair.of(ModItem.TWISTED_ALCOHOL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.SUGAR.getItem();
    pairs = new Pair[]{Pair.of(ModItem.SUGAR_SUBSTITUTE.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.SUGAR_GROUP.getItem();
    pairs = new Pair[]{Pair.of(ModItem.SUGAR.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.SUGAR_POLYMER.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.SUGAR_GROUP.getItem(), 2),
        Pair.of(ModItem.ORIRON_GROUP.getItem(), 1),
        Pair.of(ModItem.MANGANESE_ORE.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.ORIRON.getItem();
    pairs = new Pair[]{Pair.of(ModItem.ORIRON_SHARD.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.ORIRON_GROUP.getItem();
    pairs = new Pair[]{Pair.of(ModItem.ORIRON.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.ORIRON_CLUSTER.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.ORIRON_GROUP.getItem(), 2),
        Pair.of(ModItem.DEVICE_GROUP.getItem(), 1),
        Pair.of(ModItem.POLYESTER_GROUP.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.AKETON.getItem();
    pairs = new Pair[]{Pair.of(ModItem.DIKETONE.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.POLYKETON.getItem();
    pairs = new Pair[]{Pair.of(ModItem.AKETON.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.KETON_COLLOID.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.POLYKETON.getItem(), 2),
        Pair.of(ModItem.SUGAR_GROUP.getItem(), 1),
        Pair.of(ModItem.MANGANESE_ORE.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.DEVICE_CORE.getItem();
    pairs = new Pair[]{Pair.of(ModItem.DAMAGED_DEVICE.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.DEVICE_GROUP.getItem();
    pairs = new Pair[]{Pair.of(ModItem.DEVICE_CORE.getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.OPTIMIZED_DEVICE.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.DEVICE_GROUP.getItem(), 1),
        Pair.of(ModItem.ORIROCK_CLUSTER.getItem(), 2),
        Pair.of(ModItem.GRINDSTONE.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.MANGANESE_TRIHYDRATE.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.MANGANESE_ORE.getItem(), 2),
        Pair.of(ModItem.POLYESTER_GROUP.getItem(), 1),
        Pair.of(ModItem.TWISTED_ALCOHOL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.GRINDSTONE_PENTAHYDRATE.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.GRINDSTONE.getItem(), 1),
        Pair.of(ModItem.ORIRON_GROUP.getItem(), 1),
        Pair.of(ModItem.DEVICE_GROUP.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.RMA70_24.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.RMA70_12.getItem(), 1),
        Pair.of(ModItem.ORIROCK_CLUSTER.getItem(), 2),
        Pair.of(ModItem.POLYKETON.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.WHITE_HORSE_KOHL.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.TWISTED_ALCOHOL.getItem(), 1),
        Pair.of(ModItem.SUGAR_GROUP.getItem(), 1),
        Pair.of(ModItem.RMA70_12.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.CRYSTAL_GROUP.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.CRYSTAL_ELEMENT.getItem(), 2),
        Pair.of(ModItem.GEL.getItem(), 1),
        Pair.of(ModItem.LOXIC_KOHL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.CUTTING_FLUID_SOLUTION.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.COMBINED_CUTTING_FLUID.getItem(), 1),
        Pair.of(ModItem.CRYSTAL_ELEMENT.getItem(), 1),
        Pair.of(ModItem.RMA70_12.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.REFINED_SOLVENT.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.SEMI_SYNTHETIC_SOLVENT.getItem(), 1),
        Pair.of(ModItem.COMBINED_CUTTING_FLUID.getItem(), 1),
        Pair.of(ModItem.GEL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INCANDESCENT_ALLOY.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.DEVICE_GROUP.getItem(), 1),
        Pair.of(ModItem.GRINDSTONE.getItem(), 1),
        Pair.of(ModItem.LOXIC_KOHL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.COAGULATING_GEL.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.ORIRON_GROUP.getItem(), 1),
        Pair.of(ModItem.GEL.getItem(), 1),
        Pair.of(ModItem.LOXIC_KOHL.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.CRYSTALLINE_CIRCUIT.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.CRYSTAL_GROUP.getItem(), 1),
        Pair.of(ModItem.COAGULATING_GEL.getItem(), 2),
        Pair.of(ModItem.INCANDESCENT_ALLOY.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.BIPOLAR_NANOSHEET.getItem();
    pairs = new Pair[]{Pair.of(ModItem.OPTIMIZED_DEVICE.getItem(), 1), Pair.of(ModItem.WHITE_HORSE_KOHL.getItem(), 2)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.D32_STEEL.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.MANGANESE_TRIHYDRATE.getItem(), 1),
        Pair.of(ModItem.GRINDSTONE_PENTAHYDRATE.getItem(), 1),
        Pair.of(ModItem.RMA70_24.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.POLYMER_AGENT.getItem();
    pairs = new Pair[]{
        Pair.of(ModItem.ORIROCK_CONCENTRATION.getItem(), 1),
        Pair.of(ModItem.ORIRON_CLUSTER.getItem(), 1),
        Pair.of(ModItem.KETON_COLLOID.getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.ORIGINIUM_POWDER.getItem())
        .requires((ItemLike) ModItem.ORIROCK_CUBE.getItem(), 2)
        .requires((ItemLike) ModItem.LMD.getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.ORIROCK_CUBE.getItem()),
            RecipeHelpers.has((ItemLike) ModItem.ORIROCK_CUBE.getItem())
        )
        .save(exporter, Zinecraft.REGISTRAR.id("originium_powder_from_orirock"));
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.ORIGINIUM_POWDER.getItem())
        .requires((ItemLike) ModItem.DEVICE_CORE.getItem())
        .requires((ItemLike) ModItem.LMD.getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.DEVICE_CORE.getItem()),
            RecipeHelpers.has((ItemLike) ModItem.DEVICE_CORE.getItem())
        )
        .save(exporter, Zinecraft.REGISTRAR.id("originium_powder_from_device"));
  }

  private final void addSkillSummaryRecipes(RecipeOutput exporter) {
    ItemLike itemLike = (ItemLike) ModItem.SKILL_SUMMARY_2.getItem();
    Pair[] pairs = new Pair[]{Pair.of(ModItem.SKILL_SUMMARY_1.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.SKILL_SUMMARY_3.getItem();
    pairs = new Pair[]{Pair.of(ModItem.SKILL_SUMMARY_2.getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
  }

  private final void addChipConversionRecipes(RecipeOutput exporter) {
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_VANGUARD.getItem(), (ItemLike) ModItem.CHIP_SUPPORT.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SUPPORT.getItem(), (ItemLike) ModItem.CHIP_VANGUARD.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_GUARD.getItem(), (ItemLike) ModItem.CHIP_SPECIAL.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SPECIAL.getItem(), (ItemLike) ModItem.CHIP_GUARD.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_DEFENDER.getItem(), (ItemLike) ModItem.CHIP_MEDIC.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_MEDIC.getItem(), (ItemLike) ModItem.CHIP_DEFENDER.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SNIPER.getItem(), (ItemLike) ModItem.CHIP_CASTER.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_CASTER.getItem(), (ItemLike) ModItem.CHIP_SNIPER.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_VANGUARD_GROUP.getItem(), (ItemLike) ModItem.CHIP_SUPPORT_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SUPPORT_GROUP.getItem(), (ItemLike) ModItem.CHIP_VANGUARD_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_GUARD_GROUP.getItem(), (ItemLike) ModItem.CHIP_SPECIAL_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SPECIAL_GROUP.getItem(), (ItemLike) ModItem.CHIP_GUARD_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_DEFENDER_GROUP.getItem(), (ItemLike) ModItem.CHIP_MEDIC_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_MEDIC_GROUP.getItem(), (ItemLike) ModItem.CHIP_DEFENDER_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_SNIPER_GROUP.getItem(), (ItemLike) ModItem.CHIP_CASTER_GROUP.getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.CHIP_CASTER_GROUP.getItem(), (ItemLike) ModItem.CHIP_SNIPER_GROUP.getItem());
  }

  private final void workshopRecipe(RecipeOutput exporter, ItemLike result, Pair<? extends ItemLike, Integer>... ingredients) {
    if (ingredients.length == 0) {
      int n = 0;
      String string = "加工站配方必须至少有一种原料";
      throw new IllegalArgumentException(string.toString());
    }

    ShapelessRecipeBuilder shapelessRecipeBuilder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result);
    Object[] objects = ingredients;
    int i = 0;
    int j = 0;

    for (int k = objects.length; j < k; j++) {
      Object object = objects[j];
      Pair object1 = (Pair) object;
      int l = 0;
      ItemLike itemLike = (ItemLike) object1.getFirst();
      int m = ((Number) object1.getSecond()).intValue();
      shapelessRecipeBuilder.requires(itemLike, m);
    }

    ItemLike itemLike1 = (ItemLike) ((Pair) ingredients[0]).getFirst();
    shapelessRecipeBuilder.unlockedBy(RecipeHelpers.getHasName(itemLike1), RecipeHelpers.has(itemLike1)).save(exporter);
  }

  private final void chipRecipe(RecipeOutput exporter, ItemLike result, ItemLike source) {
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, 2)
        .requires(source, 3)
        .unlockedBy(RecipeHelpers.getHasName(source), RecipeHelpers.has(source))
        .save(exporter);
  }

  /**
   * Local bridge for names retained by the decompiler; delegates to vanilla RecipeProvider helpers.
   */
  private static final class RecipeHelpers {
    private static String getHasName(ItemLike item) {
      return ModRecipeProvider.getHasName(item);
    }

    private static net.minecraft.advancements.Criterion<?> has(ItemLike item) {
      return ModRecipeProvider.has(item);
    }

    private static net.minecraft.data.recipes.RecipeBuilder doorBuilder(ItemLike item, Ingredient ingredient) {
      return ModRecipeProvider.doorBuilder(item, ingredient);
    }

    private static void oreSmelting(RecipeOutput output, java.util.List<ItemLike> inputs, RecipeCategory category, ItemLike result, float experience, int time, String group) {
      ModRecipeProvider.oreSmelting(output, inputs, category, result, experience, time, group);
    }

  }
}
