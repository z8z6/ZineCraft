package com.cxxcxx.zinecraft.core.client.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture


class ModRecipeProvider(
  output: FabricDataOutput,
  registriesFuture: CompletableFuture<HolderLookup.Provider?>?
) : FabricRecipeProvider(output, registriesFuture) {

  override fun buildRecipes(exporter: RecipeOutput) {
    ShapelessRecipeBuilder
      .shapeless(RecipeCategory.BUILDING_BLOCKS, Items.DIRT)
      .requires(Items.COARSE_DIRT)
      .unlockedBy(getHasName(Items.COARSE_DIRT), has(Items.COARSE_DIRT))
      .save(exporter);
  }

  override fun getName(): String {
    return "ModRecipeProvider"
  }
}