package com.cxxcxx.zinecraft.api.recipe;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class RecipeCatalog {
  @NotNull
  private final List<Function1<RecipeOutput, Unit>> generators = new ArrayList<>();

  public final void add(@NotNull Function1<? super RecipeOutput, Unit> generate) {
    this.generators.add(output -> generate.invoke(output));
  }

  public final void generate(@NotNull RecipeOutput output) {
    Iterable iterable = this.generators;
    int i = 0;

    for (Object object : iterable) {
      Function1 function1 = (Function1) object;
      int j = 0;
      function1.invoke(output);
    }
  }
}

