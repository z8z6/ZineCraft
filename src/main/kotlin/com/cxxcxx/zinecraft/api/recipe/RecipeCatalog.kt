package com.cxxcxx.zinecraft.api.recipe

import net.minecraft.data.recipes.RecipeOutput

class RecipeCatalog {
  private val generators = mutableListOf<(RecipeOutput) -> Unit>()

  fun add(generate: (RecipeOutput) -> Unit) {
    generators += generate
  }

  fun generate(output: RecipeOutput) {
    generators.forEach { it(output) }
  }
}
