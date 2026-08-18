package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;

public final class LateranoHostStructure {
  public static final JigsawBuildingEntry LATERANO_HOST = Zinecraft.WORLDGEN.structures
      .fixedOriginUndergroundLandmark(
          "laterano_host", "拉特兰主机", "laterano_host/core",
          ModBiome.LATERANO_HOLY_FIELDS, -32, 48
      );

  private LateranoHostStructure() {
  }

  public static void bootstrap() {
  }
}
