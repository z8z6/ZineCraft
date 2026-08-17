package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import org.jetbrains.annotations.NotNull;

public final class LateranoHostStructure {
  @NotNull
  public static final LateranoHostStructure INSTANCE = new LateranoHostStructure();
  @NotNull
  public static final JigsawBuildingEntry LATERANO_HOST = Zinecraft.WORLDGEN.getStructures()
      .fixedOriginUndergroundLandmark("laterano_host", "laterano_host/core", NationBiomes.LATERANO_HOLY_FIELDS, -32, 48);

  private LateranoHostStructure() {
  }

}
