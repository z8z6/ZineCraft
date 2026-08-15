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

  @NotNull
  public final String getTemplate() {
    return this.template;
  }

  public final int getWeight() {
    return this.weight;
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

