package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.jetbrains.annotations.NotNull;

public final class NationLandmarks {
  @NotNull
  public static final NationLandmarks INSTANCE = new NationLandmarks();
  @NotNull
  public static final JigsawBuildingEntry AEGIR_VOLCANIC_BEACON = INSTANCE.modularLandmark("aegir_volcanic_beacon", NationBiomes.AEGIR_ABYSSAL_SEA, 40, Types.OCEAN_FLOOR_WG, 0);
  @NotNull
  public static final JigsawBuildingEntry AEGIR_ABYSSAL_OBSERVATORY = INSTANCE.modularLandmark("aegir_abyssal_observatory", NationBiomes.AEGIR_ABYSSAL_SEA, 52, Types.OCEAN_FLOOR_WG, 0);
  @NotNull
  public static final JigsawBuildingEntry BOLIVAR_DOSSOLES_YACHT = INSTANCE.modularLandmark("bolivar_dossoles_yacht", NationBiomes.BOLIVAR_PLAIN, 30, null, 0);
  @NotNull
  public static final JigsawBuildingEntry BOLIVAR_RACE_CHECKPOINT = INSTANCE.modularLandmark("bolivar_race_checkpoint", NationBiomes.BOLIVAR_PLAIN, 46, null, 0);
  @NotNull
  public static final JigsawBuildingEntry HIGASHI_RIFT_SHRINE = INSTANCE.modularLandmark("higashi_rift_shrine", NationBiomes.HIGASHI_SHADOW_RIFT, 34, null, 0);
  @NotNull
  public static final JigsawBuildingEntry HIGASHI_SOKOGAWA_WATCHTOWER = INSTANCE.modularLandmark("higashi_sokogawa_watchtower", NationBiomes.HIGASHI_SHADOW_RIFT, 50, null, 0);
  @NotNull
  public static final JigsawBuildingEntry DURIN_DOME_STATION = INSTANCE.modularLandmark(
      "durin_dome_station", NationBiomes.DURIN_UNDERGROUND_GARDEN, 24, null, 24
  );
  @NotNull
  public static final JigsawBuildingEntry DURIN_WATER_PARK = INSTANCE.modularLandmark(
      "durin_water_park", NationBiomes.DURIN_UNDERGROUND_GARDEN, 40, null, 24
  );
  @NotNull
  public static final JigsawBuildingEntry COLUMBIA_FRONTIER_LAB = INSTANCE.modularLandmark("columbia_frontier_lab", NationBiomes.COLUMBIA_SANDSTONE_WILDS, 32, null, 0);
  @NotNull
  public static final JigsawBuildingEntry COLUMBIA_PRISON_OUTPOST = INSTANCE.modularLandmark("columbia_prison_outpost", NationBiomes.COLUMBIA_SANDSTONE_WILDS, 48, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KAZIMIERZ_ARENA_GATE = INSTANCE.modularLandmark("kazimierz_arena_gate", NationBiomes.KAZIMIERZ_KNIGHTLAND, 30, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_MONUMENT = INSTANCE.modularLandmark("kazimierz_knight_monument", NationBiomes.KAZIMIERZ_KNIGHTLAND, 46, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KAZDEL_BABEL_RUINS = INSTANCE.modularLandmark("kazdel_babel_ruins", NationBiomes.KAZDEL_SCARRED_WASTES, 36, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KAZDEL_SARKAZ_CAMP = INSTANCE.modularLandmark("kazdel_sarkaz_camp", NationBiomes.KAZDEL_SCARRED_WASTES, 52, null, 0);
  @NotNull
  public static final JigsawBuildingEntry LATERANO_REVELATION_TOWER = INSTANCE.modularLandmark("laterano_revelation_tower", NationBiomes.LATERANO_HOLY_FIELDS, 28, null, 0);
  @NotNull
  public static final JigsawBuildingEntry LATERANO_AMBROSIUS_CHAPEL = INSTANCE.modularLandmark("laterano_ambrosius_chapel", NationBiomes.LATERANO_HOLY_FIELDS, 44, null, 0);
  @NotNull
  public static final JigsawBuildingEntry LEITHANIEN_TWIN_SPIRES = INSTANCE.modularLandmark("leithanien_twin_spires", NationBiomes.LEITHANIEN_TWILIGHT_FOREST, 34, null, 0);
  @NotNull
  public static final JigsawBuildingEntry LEITHANIEN_CONCERT_HALL = INSTANCE.modularLandmark("leithanien_concert_hall", NationBiomes.LEITHANIEN_TWILIGHT_FOREST, 50, null, 0);
  @NotNull
  public static final JigsawBuildingEntry RIM_BILLITON_MINING_DERRICK = INSTANCE.modularLandmark("rim_billiton_mining_derrick", NationBiomes.RIM_BILLITON_MINING_BADLANDS, 32, null, 0);
  @NotNull
  public static final JigsawBuildingEntry RIM_BILLITON_RAIL_DEPOT = INSTANCE.modularLandmark("rim_billiton_rail_depot", NationBiomes.RIM_BILLITON_MINING_BADLANDS, 48, null, 0);
  @NotNull
  public static final JigsawBuildingEntry MINOS_HEROES_TEMPLE = INSTANCE.modularLandmark("minos_heroes_temple", NationBiomes.MINOS_SUNLIT_HILLS, 28, null, 0);
  @NotNull
  public static final JigsawBuildingEntry MINOS_HEROES_PLAZA = INSTANCE.modularLandmark("minos_heroes_plaza", NationBiomes.MINOS_SUNLIT_HILLS, 44, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SARGON_GOLDEN_BAZAAR = INSTANCE.modularLandmark("sargon_golden_bazaar", NationBiomes.SARGON_ROCKY_DESERT, 34, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SARGON_LONG_SPRING_WELL = INSTANCE.modularLandmark("sargon_long_spring_well", NationBiomes.SARGON_ROCKY_DESERT, 50, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SAMI_CYCLOPS_ALTAR = INSTANCE.modularLandmark("sami_cyclops_altar", NationBiomes.SAMI_FROZEN_FOREST, 38, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SAMI_SNOWPRIEST_LODGE = INSTANCE.modularLandmark("sami_snowpriest_lodge", NationBiomes.SAMI_FROZEN_FOREST, 54, null, 0);
  @NotNull
  public static final JigsawBuildingEntry VICTORIA_DEFENCE_CANNON = INSTANCE.modularLandmark("victoria_defence_cannon", NationBiomes.VICTORIA_MISTY_HIGHLANDS, 32, null, 0);
  @NotNull
  public static final JigsawBuildingEntry VICTORIA_DEFENCE_CANNON_PREVIEW = INSTANCE.defenceCannonPreview();
  @NotNull
  public static final JigsawBuildingEntry VICTORIA_STEAM_STATION = INSTANCE.modularLandmark("victoria_steam_station", NationBiomes.VICTORIA_MISTY_HIGHLANDS, 48, null, 0);
  @NotNull
  public static final JigsawBuildingEntry URSUS_SARCOPHAGUS_STATION = INSTANCE.modularLandmark("ursus_sarcophagus_station", NationBiomes.URSUS_FROZEN_STEPPE, 34, null, 0);
  @NotNull
  public static final JigsawBuildingEntry URSUS_NORTHERN_MINE_TOWER = INSTANCE.modularLandmark("ursus_northern_mine_tower", NationBiomes.URSUS_FROZEN_STEPPE, 50, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KJERAG_KARLAN_MONASTERY = INSTANCE.modularLandmark("kjerag_karlan_monastery", NationBiomes.KJERAG_SNOWY_PEAKS, 40, null, 0);
  @NotNull
  public static final JigsawBuildingEntry KJERAG_SACRED_PLAZA = INSTANCE.modularLandmark("kjerag_sacred_plaza", NationBiomes.KJERAG_SNOWY_PEAKS, 56, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SIRACUSA_FAMILY_COURT = INSTANCE.modularLandmark("siracusa_family_court", NationBiomes.SIRACUSA_RAINY_WOODLAND, 30, null, 0);
  @NotNull
  public static final JigsawBuildingEntry SIRACUSA_FAMILY_THEATRE = INSTANCE.modularLandmark("siracusa_family_theatre", NationBiomes.SIRACUSA_RAINY_WOODLAND, 46, null, 0);
  @NotNull
  public static final JigsawBuildingEntry YAN_YUMEN_BEACON = INSTANCE.modularLandmark("yan_yumen_beacon", NationBiomes.YAN_MOUNTAIN_GROVE, 36, null, 0);
  @NotNull
  public static final JigsawBuildingEntry YAN_SHANGSHU_PAVILION = INSTANCE.modularLandmark("yan_shangshu_pavilion", NationBiomes.YAN_MOUNTAIN_GROVE, 52, null, 0);
  @NotNull
  public static final JigsawBuildingEntry IBERIA_EYE_LIGHTHOUSE = INSTANCE.modularLandmark("iberia_eye_lighthouse", NationBiomes.IBERIA_SALT_DELTA, 38, null, 0);
  @NotNull
  public static final JigsawBuildingEntry IBERIA_SALTVIND_CHAPEL = INSTANCE.modularLandmark("iberia_saltwind_chapel", NationBiomes.IBERIA_SALT_DELTA, 54, null, 0);

  private NationLandmarks() {
  }

  private JigsawBuildingEntry modularLandmark(
      String path,
      ResourceKey<Biome> biome,
      int ringDistance,
      Types heightmap,
      int startHeight
  ) {
    String templateRoot = "nation_landmarks/" + path;
    return Zinecraft.WORLDGEN.getStructures().guaranteedLandmark(
        path,
        ringDistance,
        path.hashCode(),
        7,
        112,
        0.0F,
        biome,
        NationBiomes.INSTANCE.getALL_TERRA_BIOMES(),
        heightmap == null ? Types.WORLD_SURFACE_WG : heightmap,
        startHeight,
        Decoration.SURFACE_STRUCTURES,
        TerrainAdjustment.BEARD_THIN,
        builder -> {
          builder.setStartPool("start");
          builder.pool("start", Projection.RIGID, pool -> pool.template(templateRoot + "/foundation", 1));
          builder.pool("core", Projection.RIGID, pool -> pool.template(templateRoot + "/core", 1));
          builder.pool("facade", Projection.RIGID, pool -> pool.template(templateRoot + "/facade", 1));
          builder.pool("roof", Projection.RIGID, pool -> pool.template(templateRoot + "/roof", 1));
          builder.pool("annex", Projection.RIGID, pool -> pool.template(templateRoot + "/annex", 1));
          builder.pool("surrounding", Projection.RIGID, pool -> pool.template(templateRoot + "/surrounding", 1));
        }
    );
  }

  private JigsawBuildingEntry defenceCannonPreview() {
    String path = "victoria_defence_cannon_preview";
    String templateRoot = "blockout/victoria/londinium/defence_cannon";
    return Zinecraft.WORLDGEN.getStructures().jigsawBuilding(
        path,
        37,
        36,
        path.hashCode(),
        6,
        112,
        0.0F,
        NationBiomes.VICTORIA_MISTY_HIGHLANDS,
        true,
        36,
        Types.WORLD_SURFACE_WG,
        0,
        false,
        false,
        Decoration.SURFACE_STRUCTURES,
        TerrainAdjustment.BEARD_THIN,
        builder -> {
          builder.setStartPool("start");
          builder.pool("start", Projection.RIGID, pool -> pool.template(templateRoot + "/wall_rear_left", 1));
          builder.pool("front_left", Projection.RIGID, pool -> pool.template(templateRoot + "/wall_front_left", 1));
          builder.pool("rear_right", Projection.RIGID, pool -> pool.template(templateRoot + "/wall_rear_right", 1));
          builder.pool("front_right", Projection.RIGID, pool -> pool.template(templateRoot + "/wall_front_right", 1));
          builder.pool("turret", Projection.RIGID, pool -> pool.template(templateRoot + "/turret_core", 1));
          builder.pool("barrel_root", Projection.RIGID, pool -> pool.template(templateRoot + "/barrel_root", 1));
          builder.pool("barrel_muzzle", Projection.RIGID, pool -> pool.template(templateRoot + "/barrel_muzzle", 1));
        }
    );
  }
}
