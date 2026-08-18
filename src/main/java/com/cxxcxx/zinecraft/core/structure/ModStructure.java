package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

public final class ModStructure {
  public static final JigsawBuildingEntry STARGATE = Zinecraft.WORLDGEN.structures.jigsawBuilding(
      "stargate",
      "雪原星门",
      "Snowfield Stargate",
      8,
      4,
      958853901,
      1,
      32,
      0.0F,
      Biomes.SNOWY_PLAINS,
      false,
      0,
      Types.WORLD_SURFACE_WG,
      0,
      false,
      false,
      Decoration.SURFACE_STRUCTURES,
      TerrainAdjustment.BEARD_THIN,
      builder -> builder.pool("start", Projection.RIGID, pool -> pool.template("stargate", 1))
  );

  private ModStructure() {
  }

  public static void bootstrap() {
  }
}
