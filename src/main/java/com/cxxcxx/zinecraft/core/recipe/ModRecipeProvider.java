package com.cxxcxx.zinecraft.core.recipe;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.item.ModItem;
import com.cxxcxx.zinecraft.core.item.NationFoods;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
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
    RecipeHelpers.oreSmelting(exporter, CollectionsKt.listOf(items), RecipeCategory.FOOD, (ItemLike) Items.WHEAT, 0.1F, 300, "food_to_wheat");
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.INSTANCE.getMAGIC_DUST().getItem(), 4)
        .requires((ItemLike) ModItem.INSTANCE.getMAGIC_DUST().getItem(), 2)
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.INSTANCE.getMAGIC_DUST().getItem()),
            RecipeHelpers.has((ItemLike) ModItem.INSTANCE.getMAGIC_DUST().getItem())
        )
        .save(exporter);
    RecipeHelpers.oreSmelting(
        exporter,
        CollectionsKt.listOf(ModItem.INSTANCE.getMAGIC_DUST().getItem()),
        RecipeCategory.MISC,
        (ItemLike) ModItem.INSTANCE.getMAGIC_DUST().getItem(),
        0.1F,
        20,
        "magic_dust_copy"
    );
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.INSTANCE.getPROTOCOL_ORIGINIUM().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getORIGINITE().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getCRYSTALLINE_CIRCUIT().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getDEVICE_GROUP().getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.INSTANCE.getORIGINITE().getItem()),
            RecipeHelpers.has((ItemLike) ModItem.INSTANCE.getORIGINITE().getItem())
        )
        .save(exporter);
    this.addArknightsMaterialRecipes(exporter);
    this.addSkillSummaryRecipes(exporter);
    this.addChipConversionRecipes(exporter);
    ItemLike itemLike1 = NationFoods.INSTANCE.getAEGIR_FRESH_SHELLCRAB_SASHIMI();
    itemLikes = new ItemLike[3];
    Item item = Items.SALMON;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.BEETROOT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getBOLIVAR_SMOKED_CAPSULE();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_CHICKEN;
    itemLikes[0] = item;
    item = Items.HONEY_BOTTLE;
    itemLikes[1] = item;
    item = Items.DRIED_KELP;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getHIGASHI_NANO_KAPPO();
    itemLikes = new ItemLike[3];
    item = Items.SALMON;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getDURIN_HONEY_SLUGPUDDING();
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.COOKED_CHICKEN;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getCOLUMBIA_ORIGINIUM_ROASTED_FOWL();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_CHICKEN;
    itemLikes[0] = item;
    item = Items.BLAZE_POWDER;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getKAZIMIERZ_KNIGHT_SUPPLEMENT();
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.GOLDEN_CARROT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getKAZDEL_CARTILAGE_TACK();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.BONE_MEAL;
    itemLikes[1] = item;
    item = Items.WHEAT;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getLATERANO_SACRED_TONE_SOUP();
    itemLikes = new ItemLike[3];
    item = Items.MILK_BUCKET;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getLEITHANIEN_MUSICAL_ROAST_EXTRACT();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.AMETHYST_SHARD;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getRIM_BILLITON_MINING_RATION();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.DRIED_KELP;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getMINOS_POETRY_GEL();
    itemLikes = new ItemLike[3];
    item = Items.HONEY_BOTTLE;
    itemLikes[0] = item;
    item = Items.MELON_SLICE;
    itemLikes[1] = item;
    item = Items.AMETHYST_SHARD;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getSARGON_GRASS_CHEESE_GEL();
    itemLikes = new ItemLike[3];
    item = Items.WHEAT;
    itemLikes[0] = item;
    item = Items.MILK_BUCKET;
    itemLikes[1] = item;
    item = Items.CACTUS;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getSAMI_INSTANT_BONE_SOUP();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_RABBIT;
    itemLikes[0] = item;
    item = Items.BONE;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getVICTORIA_CENTRAL_VALLEY_ROAST();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_BEEF;
    itemLikes[0] = item;
    item = Items.BAKED_POTATO;
    itemLikes[1] = item;
    item = Items.BOWL;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getURSUS_HAM_SUPPLEMENT();
    itemLikes = new ItemLike[3];
    item = Items.COOKED_PORKCHOP;
    itemLikes[0] = item;
    item = Items.SUGAR;
    itemLikes[1] = item;
    item = Items.PAPER;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getKJERAG_VALLEY_PIE();
    itemLikes = new ItemLike[3];
    item = Items.BREAD;
    itemLikes[0] = item;
    item = Items.COOKED_MUTTON;
    itemLikes[1] = item;
    item = Items.SWEET_BERRIES;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    itemLike1 = NationFoods.INSTANCE.getSIRACUSA_STEW_GATHERING();
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
    itemLike1 = NationFoods.INSTANCE.getYAN_WASTELAND_MEAT_STIR_FRY();
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
    itemLike1 = NationFoods.INSTANCE.getIBERIA_CHITIN_CLUSTER();
    itemLikes = new ItemLike[3];
    item = Items.NAUTILUS_SHELL;
    itemLikes[0] = item;
    item = Items.COOKED_COD;
    itemLikes[1] = item;
    item = Items.DRIED_KELP;
    itemLikes[2] = item;
    this.nationFoodRecipe(exporter, itemLike1, itemLikes);
    Zinecraft.INSTANCE.getRECIPES().generate(exporter);
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

    ItemLike itemLike1 = (ItemLike) ArraysKt.first(ingredients);
    shapelessRecipeBuilder.unlockedBy(RecipeHelpers.getHasName(itemLike1), RecipeHelpers.has(itemLike1)).save(exporter);
  }

  private final void addArknightsMaterialRecipes(RecipeOutput exporter) {
    ItemLike itemLike = (ItemLike) ModItem.INSTANCE.getORIROCK_CUBE().getItem();
    Pair[] pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getORIROCK().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getORIROCK_CLUSTER().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getORIROCK_CUBE().getItem(), 5)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getORIROCK_CONCENTRATION().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getORIROCK_CLUSTER().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getPOLYESTER().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getESTER_RAW().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getPOLYESTER_GROUP().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getPOLYESTER().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getPOLYESTER_BLOCK().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getPOLYESTER_GROUP().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getPOLYKETON().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getTWISTED_ALCOHOL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getSUGAR().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getSUGAR_SUBSTITUTE().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getSUGAR_GROUP().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getSUGAR().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getSUGAR_POLYMER().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getSUGAR_GROUP().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getORIRON_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getMANGANESE_ORE().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getORIRON().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getORIRON_SHARD().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getORIRON_GROUP().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getORIRON().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getORIRON_CLUSTER().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getORIRON_GROUP().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getDEVICE_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getPOLYESTER_GROUP().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getAKETON().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getDIKETONE().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getPOLYKETON().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getAKETON().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getKETON_COLLOID().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getPOLYKETON().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getSUGAR_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getMANGANESE_ORE().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getDEVICE_CORE().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getDAMAGED_DEVICE().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getDEVICE_GROUP().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getDEVICE_CORE().getItem(), 4)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getOPTIMIZED_DEVICE().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getDEVICE_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getORIROCK_CLUSTER().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getGRINDSTONE().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getMANGANESE_TRIHYDRATE().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getMANGANESE_ORE().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getPOLYESTER_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getTWISTED_ALCOHOL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getGRINDSTONE_PENTAHYDRATE().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getGRINDSTONE().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getORIRON_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getDEVICE_GROUP().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getRMA70_24().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getRMA70_12().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getORIROCK_CLUSTER().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getPOLYKETON().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getWHITE_HORSE_KOHL().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getTWISTED_ALCOHOL().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getSUGAR_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getRMA70_12().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getCRYSTAL_GROUP().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getCRYSTAL_ELEMENT().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getGEL().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getLOXIC_KOHL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getCUTTING_FLUID_SOLUTION().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getCOMBINED_CUTTING_FLUID().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getCRYSTAL_ELEMENT().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getRMA70_12().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getREFINED_SOLVENT().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getSEMI_SYNTHETIC_SOLVENT().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getCOMBINED_CUTTING_FLUID().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getGEL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getINCANDESCENT_ALLOY().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getDEVICE_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getGRINDSTONE().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getLOXIC_KOHL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getCOAGULATING_GEL().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getORIRON_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getGEL().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getLOXIC_KOHL().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getCRYSTALLINE_CIRCUIT().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getCRYSTAL_GROUP().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getCOAGULATING_GEL().getItem(), 2),
        TuplesKt.to(ModItem.INSTANCE.getINCANDESCENT_ALLOY().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getBIPOLAR_NANOSHEET().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getOPTIMIZED_DEVICE().getItem(), 1), TuplesKt.to(ModItem.INSTANCE.getWHITE_HORSE_KOHL().getItem(), 2)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getD32_STEEL().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getMANGANESE_TRIHYDRATE().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getGRINDSTONE_PENTAHYDRATE().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getRMA70_24().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getPOLYMER_AGENT().getItem();
    pairs = new Pair[]{
        TuplesKt.to(ModItem.INSTANCE.getORIROCK_CONCENTRATION().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getORIRON_CLUSTER().getItem(), 1),
        TuplesKt.to(ModItem.INSTANCE.getKETON_COLLOID().getItem(), 1)
    };
    this.workshopRecipe(exporter, itemLike, pairs);
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.INSTANCE.getORIGINIUM_POWDER().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getORIROCK_CUBE().getItem(), 2)
        .requires((ItemLike) ModItem.INSTANCE.getLMD().getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.INSTANCE.getORIROCK_CUBE().getItem()),
            RecipeHelpers.has((ItemLike) ModItem.INSTANCE.getORIROCK_CUBE().getItem())
        )
        .save(exporter, Zinecraft.INSTANCE.getREGISTRAR().id("originium_powder_from_orirock"));
    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike) ModItem.INSTANCE.getORIGINIUM_POWDER().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getDEVICE_CORE().getItem())
        .requires((ItemLike) ModItem.INSTANCE.getLMD().getItem())
        .unlockedBy(
            RecipeHelpers.getHasName((ItemLike) ModItem.INSTANCE.getDEVICE_CORE().getItem()),
            RecipeHelpers.has((ItemLike) ModItem.INSTANCE.getDEVICE_CORE().getItem())
        )
        .save(exporter, Zinecraft.INSTANCE.getREGISTRAR().id("originium_powder_from_device"));
  }

  private final void addSkillSummaryRecipes(RecipeOutput exporter) {
    ItemLike itemLike = (ItemLike) ModItem.INSTANCE.getSKILL_SUMMARY_2().getItem();
    Pair[] pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getSKILL_SUMMARY_1().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
    itemLike = (ItemLike) ModItem.INSTANCE.getSKILL_SUMMARY_3().getItem();
    pairs = new Pair[]{TuplesKt.to(ModItem.INSTANCE.getSKILL_SUMMARY_2().getItem(), 3)};
    this.workshopRecipe(exporter, itemLike, pairs);
  }

  private final void addChipConversionRecipes(RecipeOutput exporter) {
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_VANGUARD().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SUPPORT().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SUPPORT().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_VANGUARD().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_GUARD().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SPECIAL().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SPECIAL().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_GUARD().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_DEFENDER().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_MEDIC().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_MEDIC().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_DEFENDER().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SNIPER().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_CASTER().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_CASTER().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SNIPER().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_VANGUARD_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SUPPORT_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SUPPORT_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_VANGUARD_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_GUARD_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SPECIAL_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SPECIAL_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_GUARD_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_DEFENDER_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_MEDIC_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_MEDIC_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_DEFENDER_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_SNIPER_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_CASTER_GROUP().getItem());
    this.chipRecipe(exporter, (ItemLike) ModItem.INSTANCE.getCHIP_CASTER_GROUP().getItem(), (ItemLike) ModItem.INSTANCE.getCHIP_SNIPER_GROUP().getItem());
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
      ItemLike itemLike = (ItemLike) object1.component1();
      int m = ((Number) object1.component2()).intValue();
      shapelessRecipeBuilder.requires(itemLike, m);
    }

    ItemLike itemLike1 = (ItemLike) ((Pair) ArraysKt.first(ingredients)).getFirst();
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

