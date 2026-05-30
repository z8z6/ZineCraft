package com.cxxcxx.zinecraft.core.datagen

import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.*
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.*
import java.util.concurrent.CompletableFuture

class ModRecipeProvider(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricRecipeProvider(output, registriesFuture) {

    override fun buildRecipes(exporter: RecipeOutput) {
        //无序配方
        //砂土做泥土
        //RecipeCategory：配方在配方书中的分类
        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT)
            .requires(Items.COARSE_DIRT)
            .unlockedBy(getHasName(Items.COARSE_DIRT), has(Items.COARSE_DIRT))
            .save(exporter);

        //有序配方
        //原木做工作台
        ShapedRecipeBuilder
            .shaped(RecipeCategory.MISC, Items.CRAFTING_TABLE, 4)
            .pattern("ll")
            .pattern("ll")
            .define('l', ItemTags.LOGS) // 'l' means "any log"
            .group("multi_bench") // Put it in a group called "multi_bench" - groups are shown in one slot in the recipe book
            .unlockedBy(getHasName(Items.CRAFTING_TABLE), has(Items.CRAFTING_TABLE))
            .save(exporter);
        //羊毛做织布机
        ShapedRecipeBuilder
            .shaped(RecipeCategory.MISC, Items.LOOM, 4)
            .pattern("ww")
            .pattern("ll")
            .define('w', ItemTags.WOOL) // 'w' means "any wool"
            .define('l', ItemTags.LOGS)
            .group("multi_bench")
            .unlockedBy(getHasName(Items.LOOM), has(Items.LOOM))
            .save(exporter);
        //按钮做同类的门，doorBuilder对ShapedRecipeBuilder进行了一定的封装
        doorBuilder(Items.OAK_DOOR, Ingredient.of(Items.OAK_BUTTON)) // Using a helper method!
            .unlockedBy(getHasName(Items.OAK_BUTTON), has(Items.OAK_BUTTON))
            .save(exporter);
        //熔炼配方
        oreSmelting(
            exporter,
            listOf(Items.BREAD, Items.COOKIE, Items.HAY_BLOCK), // Inputs
            RecipeCategory.FOOD, // Category
            Items.WHEAT, // Output
            0.1f, // Experience
            300, // Cooking time
            "food_to_wheat" // group
        );


        ShapelessRecipeBuilder
            .shapeless(RecipeCategory.MISC, ModItem.MAGIC_DUST, 4)
            .requires(ModItem.MAGIC_DUST, 2)
            .unlockedBy(getHasName(ModItem.MAGIC_DUST), has(ModItem.MAGIC_DUST))
            .save(exporter)

        oreSmelting(
            exporter,
            listOf(ModItem.MAGIC_DUST),
            RecipeCategory.MISC,
            ModItem.MAGIC_DUST,
            0.1f,
            20,
            "magic_dust_copy"
        )
    }

    override fun getName(): String {
        return "ModRecipeProvider"
    }
}