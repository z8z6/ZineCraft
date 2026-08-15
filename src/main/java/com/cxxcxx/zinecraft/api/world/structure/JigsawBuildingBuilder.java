package com.cxxcxx.zinecraft.api.world.structure;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class JigsawBuildingBuilder {
  @NotNull
  private final String path;
  @NotNull
  private final List<JigsawPoolDefinition> pools;
  @NotNull
  private String startPool;

  public JigsawBuildingBuilder(@NotNull String path) {
    super();
    this.path = path;
    this.startPool = "start";
    this.pools = new ArrayList<>();
  }

  // $VF: synthetic method
  public static void pool$default(JigsawBuildingBuilder var0, String var1, Projection var2, Function1 var3, int var4, Object var5) {
    if ((var4 & 2) != 0) {
      var2 = Projection.RIGID;
    }

    var0.pool(var1, var2, var3);
  }

  @NotNull
  public final String getStartPool() {
    return this.startPool;
  }

  public final void setStartPool(@NotNull String var1) {
    this.startPool = var1/* $VF was: <set-?> */;
  }

  public final void pool(@NotNull String name, @NotNull Projection projection, @NotNull Function1<? super JigsawPoolBuilder, Unit> build) {
    if (StringsKt.isBlank(name)) {
      int k = 0;
      String string1 = "Jigsaw pool 名称不能为空";
      throw new IllegalArgumentException(string1.toString());
    }

    var iterable = this.pools;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      Iterator iterator = iterable.iterator();

      while (true) {
        if (!iterator.hasNext()) {
          bl = true;
          break;
        }

        Object object = iterator.next();
        JigsawPoolDefinition jigsawPoolDefinition = (JigsawPoolDefinition) object;
        int j = 0;
        if (java.util.Objects.equals(jigsawPoolDefinition.getName(), name)) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string = "Jigsaw pool 重复: " + this.path + "/" + name;
      throw new IllegalArgumentException(string.toString());
    }

    iterable = this.pools;
    JigsawPoolBuilder jigsawPoolBuilder = new JigsawPoolBuilder(name, projection);
    build.invoke(jigsawPoolBuilder);
    iterable.add(jigsawPoolBuilder.build$zinecraft());
  }

  @NotNull
  public final JigsawBuildingDefinition build$zinecraft() {
    if (this.pools.isEmpty()) {
      int k = 0;
      String string1 = "Jigsaw 建筑至少需要一个模板池: " + this.path;
      throw new IllegalArgumentException(string1.toString());
    }

    var iterable = this.pools;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = false;
    } else {
      Iterator iterator = iterable.iterator();

      while (true) {
        if (!iterator.hasNext()) {
          bl = false;
          break;
        }

        Object object = iterator.next();
        JigsawPoolDefinition jigsawPoolDefinition = (JigsawPoolDefinition) object;
        int j = 0;
        if (java.util.Objects.equals(jigsawPoolDefinition.getName(), this.startPool)) {
          bl = true;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string = "找不到起始模板池: " + this.path + "/" + this.startPool;
      throw new IllegalArgumentException(string.toString());
    } else {
      return new JigsawBuildingDefinition(this.startPool, CollectionsKt.toList(this.pools));
    }
  }
}

