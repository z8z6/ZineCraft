package com.cxxcxx.zinecraft.api.world.structure;

import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class JigsawPoolDefinition {
  @NotNull
  private final String name;
  @NotNull
  private final List<JigsawTemplateElement> templates;
  @NotNull
  private final Projection projection;

  public JigsawPoolDefinition(@NotNull String name, @NotNull List<JigsawTemplateElement> templates, @NotNull Projection projection) {
    super();
    this.name = name;
    this.templates = templates;
    this.projection = projection;
  }

  // $VF: synthetic method
  public static JigsawPoolDefinition copy$default(JigsawPoolDefinition var0, String var1, List var2, Projection var3, int var4, Object var5) {
    if ((var4 & 1) != 0) {
      var1 = var0.name;
    }

    if ((var4 & 2) != 0) {
      var2 = var0.templates;
    }

    if ((var4 & 4) != 0) {
      var3 = var0.projection;
    }

    return var0.copy(var1, var2, var3);
  }

  @NotNull
  public final String getName() {
    return this.name;
  }

  @NotNull
  public final List<JigsawTemplateElement> getTemplates() {
    return this.templates;
  }

  @NotNull
  public final Projection getProjection() {
    return this.projection;
  }

  @NotNull
  public final String component1() {
    return this.name;
  }

  @NotNull
  public final List<JigsawTemplateElement> component2() {
    return this.templates;
  }

  @NotNull
  public final Projection component3() {
    return this.projection;
  }

  @NotNull
  public final JigsawPoolDefinition copy(@NotNull String name, @NotNull List<JigsawTemplateElement> templates, @NotNull Projection projection) {
    return new JigsawPoolDefinition(name, templates, projection);
  }

  @Override
  public int hashCode() {
    int i = this.name.hashCode();
    i = i * 31 + this.templates.hashCode();
    return i * 31 + this.projection.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof JigsawPoolDefinition jigsawPoolDefinition)) {
      return false;
    } else if (!java.util.Objects.equals(this.name, jigsawPoolDefinition.name)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.templates, jigsawPoolDefinition.templates) ? false : this.projection == jigsawPoolDefinition.projection;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "JigsawPoolDefinition(name=" + this.name + ", templates=" + this.templates + ", projection=" + this.projection + ")";
  }
}

