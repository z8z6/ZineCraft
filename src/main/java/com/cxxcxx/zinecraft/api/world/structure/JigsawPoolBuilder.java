package com.cxxcxx.zinecraft.api.world.structure;

import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class JigsawPoolBuilder {
  @NotNull
  private final String name;
  @NotNull
  private final Projection projection;
  @NotNull
  private final List<JigsawTemplateElement> templates;

  public JigsawPoolBuilder(@NotNull String name, @NotNull Projection projection) {
    super();
    this.name = name;
    this.projection = projection;
    this.templates = new ArrayList<>();
  }

  // $VF: synthetic method
  public static void template$default(JigsawPoolBuilder var0, String var1, int var2, int var3, Object var4) {
    if ((var3 & 2) != 0) {
      var2 = 1;
    }

    var0.template(var1, var2);
  }

  public final void template(@NotNull String path, int weight) {
    if (StringsKt.isBlank(path)) {
      int j = 0;
      String string1 = "Jigsaw 模板路径不能为空";
      throw new IllegalArgumentException(string1.toString());
    }

    if (weight <= 0) {
      int i = 0;
      String string = "Jigsaw 模板权重必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }

    this.templates.add(new JigsawTemplateElement(path, weight));
  }

  @NotNull
  public final JigsawPoolDefinition build$zinecraft() {
    if (this.templates.isEmpty()) {
      int i = 0;
      String string = "Jigsaw 模板池不能为空: " + this.name;
      throw new IllegalArgumentException(string.toString());
    } else {
      return new JigsawPoolDefinition(this.name, CollectionsKt.toList(this.templates), this.projection);
    }
  }
}

