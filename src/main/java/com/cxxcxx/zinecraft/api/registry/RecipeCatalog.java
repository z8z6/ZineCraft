package com.cxxcxx.zinecraft.api.registry;


import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RecipeCatalog {
  @NotNull
  private final List<Consumer<RecipeOutput>> generators = new ArrayList<>();

  public void add(Consumer<? super RecipeOutput> generate) {
    this.generators.add(generate::accept);
  }

  public void generate(RecipeOutput output) {
    Iterable iterable = this.generators;
    for (Object object : iterable) {
      Consumer function1 = (Consumer) object;
      function1.accept(output);
    }
  }
}

