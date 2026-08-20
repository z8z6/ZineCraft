package com.cxxcxx.zinecraft.api.registry.catalog;


import net.minecraft.data.recipes.RecipeOutput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 保存数据生成阶段执行的配方生成器。
 */
public final class RecipeCatalog {
  @NotNull
  private final List<Consumer<RecipeOutput>> generators = new ArrayList<>();

  /**
   * 添加一个配方生成器。
   *
   * @param generate 接收配方输出并写入配方的回调
   */
  public void add(Consumer<? super RecipeOutput> generate) {
    this.generators.add(generate::accept);
  }

  /**
   * 依次执行所有已登记的配方生成器。
   *
   * @param output 数据生成器提供的配方输出目标
   */
  public void generate(RecipeOutput output) {
    Iterable iterable = this.generators;
    for (Object object : iterable) {
      Consumer function1 = (Consumer) object;
      function1.accept(output);
    }
  }
}

