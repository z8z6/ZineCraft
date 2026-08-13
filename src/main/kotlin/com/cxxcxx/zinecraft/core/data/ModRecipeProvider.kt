package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.item.ModItem
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

    ZinecraftCore.CONTENT.generateRecipes(exporter)
  }
}
