package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingBuilder;
import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.JigsawPoolBuilder;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

public final class ModStructure {
  @NotNull
  public static final ModStructure INSTANCE = new ModStructure();
  @NotNull
  private static final JigsawBuildingEntry PORTAL_RUINS_COMMON = StructureCatalog.simpleBuilding$default(
      Zinecraft.INSTANCE.getSTRUCTURES(), "portal_ruins_common", "portal_ruins/common", 36, 30, 958853901, 0, 0.6F, 32, null
  );
  @NotNull
  private static final JigsawBuildingEntry THREE_PIECE_JIGSAW = StructureCatalog.jigsawBuilding$default(
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
      (kotlin.jvm.functions.Function1<JigsawBuildingBuilder, Unit>) (builder -> THREE_PIECE_JIGSAW$lambda$0(builder)),
      65504,
      null
  );

  private ModStructure() {
  }

  private static final Unit THREE_PIECE_JIGSAW$lambda$0$0(JigsawPoolBuilder $this$pool) {
    JigsawPoolBuilder.template$default($this$pool, "jigsaw_example/start", 0, 2, null);
    return Unit.INSTANCE;
  }

  private static final Unit THREE_PIECE_JIGSAW$lambda$0$1(JigsawPoolBuilder $this$pool) {
    JigsawPoolBuilder.template$default($this$pool, "jigsaw_example/middle", 0, 2, null);
    return Unit.INSTANCE;
  }

  private static final Unit THREE_PIECE_JIGSAW$lambda$0$2(JigsawPoolBuilder $this$pool) {
    JigsawPoolBuilder.template$default($this$pool, "jigsaw_example/end", 0, 2, null);
    return Unit.INSTANCE;
  }

  private static final Unit THREE_PIECE_JIGSAW$lambda$0(JigsawBuildingBuilder $this$jigsawBuilding) {
    JigsawBuildingBuilder.pool$default($this$jigsawBuilding, "start", null,
        (kotlin.jvm.functions.Function1<JigsawPoolBuilder, Unit>) (pool -> THREE_PIECE_JIGSAW$lambda$0$0(pool)), 2, null);
    JigsawBuildingBuilder.pool$default($this$jigsawBuilding, "middle", null,
        (kotlin.jvm.functions.Function1<JigsawPoolBuilder, Unit>) (pool -> THREE_PIECE_JIGSAW$lambda$0$1(pool)), 2, null);
    JigsawBuildingBuilder.pool$default($this$jigsawBuilding, "end", null,
        (kotlin.jvm.functions.Function1<JigsawPoolBuilder, Unit>) (pool -> THREE_PIECE_JIGSAW$lambda$0$2(pool)), 2, null);
    return Unit.INSTANCE;
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

