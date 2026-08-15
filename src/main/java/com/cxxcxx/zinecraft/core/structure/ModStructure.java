package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingBuilder;
import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolBuilder;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;

import org.jetbrains.annotations.NotNull;

public final class ModStructure {
  @NotNull
  public static final ModStructure INSTANCE = new ModStructure();
  @NotNull
  private static final JigsawBuildingEntry PORTAL_RUINS_COMMON = StructureCatalog.simpleBuildingWithDefaults(
      Zinecraft.INSTANCE.getSTRUCTURES(), "portal_ruins_common", "portal_ruins/common", 36, 30, 958853901, 0, 0.6F, 32, null
  );
  @NotNull
  private static final JigsawBuildingEntry THREE_PIECE_JIGSAW = StructureCatalog.jigsawBuildingWithDefaults(
      Zinecraft.INSTANCE.getSTRUCTURES(),
      "jigsaw_example",
      40,
      20,
      31579842,
      2,
      0,
      0.0F,
      null,
      false,
      0,
      null,
      0,
      false,
      false,
      null,
      null,
      (java.util.function.Consumer<JigsawBuildingBuilder>) (builder -> THREE_PIECE_JIGSAWHelper0(builder)),
      65504,
      null
  );

  private ModStructure() {
  }

  private static void THREE_PIECE_JIGSAWHelper0$0(JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, "jigsaw_example/start", 0, 2, null);
    return;
  }

  private static void THREE_PIECE_JIGSAWHelper0$1(JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, "jigsaw_example/middle", 0, 2, null);
    return;
  }

  private static void THREE_PIECE_JIGSAWHelper0$2(JigsawPoolBuilder _this_pool) {
    JigsawPoolBuilder.templateWithDefaults(_this_pool, "jigsaw_example/end", 0, 2, null);
    return;
  }

  private static void THREE_PIECE_JIGSAWHelper0(JigsawBuildingBuilder _this_jigsawBuilding) {
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "start", null,
        (java.util.function.Consumer<JigsawPoolBuilder>) (pool -> THREE_PIECE_JIGSAWHelper0$0(pool)), 2, null);
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "middle", null,
        (java.util.function.Consumer<JigsawPoolBuilder>) (pool -> THREE_PIECE_JIGSAWHelper0$1(pool)), 2, null);
    JigsawBuildingBuilder.poolWithDefaults(_this_jigsawBuilding, "end", null,
        (java.util.function.Consumer<JigsawPoolBuilder>) (pool -> THREE_PIECE_JIGSAWHelper0$2(pool)), 2, null);
    return;
  }

  @NotNull
  public final JigsawBuildingEntry getPORTAL_RUINS_COMMON() {
    return PORTAL_RUINS_COMMON;
  }

  @NotNull
  public final JigsawBuildingEntry getTHREE_PIECE_JIGSAW() {
    return THREE_PIECE_JIGSAW;
  }
}

