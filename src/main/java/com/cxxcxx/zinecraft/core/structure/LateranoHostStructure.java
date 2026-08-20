package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.registry.ModBiome;

public final class LateranoHostStructure {
  public static final JigsawBuilder LATERANO_HOST = Zinecraft.STRUCTURES
      .fixedOriginUndergroundLandmark(
          "laterano_host", "拉特兰主机", "laterano_host/core",
          ModBiome.LATERANO_HOLY_FIELDS.key(), -32, 48
      );

  private LateranoHostStructure() {
  }

  public static void bootstrap() {
  }
}
