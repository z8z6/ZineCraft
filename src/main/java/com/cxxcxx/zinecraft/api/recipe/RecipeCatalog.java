package com.cxxcxx.zinecraft.api.recipe;


import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RecipeCatalog {
  @NotNull
  private final List<Consumer<RecipeOutput>> generators = new ArrayList<>();

  public final void add(@NotNull Consumer<? super RecipeOutput> generate) {
    this.generators.add(output -> generate.accept(output));
  }

  public final void generate(@NotNull RecipeOutput output) {
    Iterable iterable = this.generators;
    int i = 0;

    for (Object object : iterable) {
      Consumer function1 = (Consumer) object;
      int j = 0;
      function1.accept(output);
    }
  }
}

