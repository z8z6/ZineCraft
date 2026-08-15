package com.cxxcxx.zinecraft.api.world.structure;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JigsawTemplateElement {
  @NotNull
  private final String template;
  private final int weight;

  public JigsawTemplateElement(@NotNull String template, int weight) {
    super();
    this.template = template;
    this.weight = weight;
  }

  // $VF: synthetic method
  public static JigsawTemplateElement copy$default(JigsawTemplateElement var0, String var1, int var2, int var3, Object var4) {
    if ((var3 & 1) != 0) {
      var1 = var0.template;
    }

    if ((var3 & 2) != 0) {
      var2 = var0.weight;
    }

    return var0.copy(var1, var2);
  }

  @NotNull
  public final String getTemplate() {
    return this.template;
  }

  public final int getWeight() {
    return this.weight;
  }

  @NotNull
  public final String component1() {
    return this.template;
  }

  public final int component2() {
    return this.weight;
  }

  @NotNull
  public final JigsawTemplateElement copy(@NotNull String template, int weight) {
    return new JigsawTemplateElement(template, weight);
  }

  @Override
  public int hashCode() {
    int i = this.template.hashCode();
    return i * 31 + Integer.hashCode(this.weight);
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof JigsawTemplateElement jigsawTemplateElement)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.template, jigsawTemplateElement.template) ? false : this.weight == jigsawTemplateElement.weight;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "JigsawTemplateElement(template=" + this.template + ", weight=" + this.weight + ")";
  }
}

