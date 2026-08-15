package com.cxxcxx.zinecraft.core.structure;

import com.cxxcxx.zinecraft.api.world.structure.JigsawBuildingEntry;
import com.cxxcxx.zinecraft.api.world.structure.StructureCatalog;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.NationBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;

public final class NationLandmarks {
  @NotNull
  public static final NationLandmarks INSTANCE = new NationLandmarks();
  @NotNull
  private static final JigsawBuildingEntry AEGIR_VOLCANIC_BEACON = landmark$default(
      INSTANCE, "aegir_volcanic_beacon", NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA(), 40, Types.OCEAN_FLOOR_WG, 0, 16, null
  );
  @NotNull
  private static final JigsawBuildingEntry AEGIR_ABYSSAL_OBSERVATORY = landmark$default(
      INSTANCE, "aegir_abyssal_observatory", NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA(), 52, Types.OCEAN_FLOOR_WG, 0, 16, null
  );
  @NotNull
  private static final JigsawBuildingEntry BOLIVAR_DOSSOLES_YACHT = landmark$default(
      INSTANCE, "bolivar_dossoles_yacht", NationBiomes.INSTANCE.getBOLIVAR_PLAIN(), 30, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry BOLIVAR_RACE_CHECKPOINT = landmark$default(
      INSTANCE, "bolivar_race_checkpoint", NationBiomes.INSTANCE.getBOLIVAR_PLAIN(), 46, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry HIGASHI_RIFT_SHRINE = landmark$default(
      INSTANCE, "higashi_rift_shrine", NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT(), 34, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry HIGASHI_SOKOGAWA_WATCHTOWER = landmark$default(
      INSTANCE, "higashi_sokogawa_watchtower", NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT(), 50, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry DURIN_DOME_STATION = INSTANCE.landmark(
      "durin_dome_station", NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN(), 24, null, 24
  );
  @NotNull
  private static final JigsawBuildingEntry DURIN_WATER_PARK = INSTANCE.landmark(
      "durin_water_park", NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN(), 40, null, 24
  );
  @NotNull
  private static final JigsawBuildingEntry COLUMBIA_FRONTIER_LAB = landmark$default(
      INSTANCE, "columbia_frontier_lab", NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS(), 32, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry COLUMBIA_PRISON_OUTPOST = landmark$default(
      INSTANCE, "columbia_prison_outpost", NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS(), 48, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KAZIMIERZ_ARENA_GATE = landmark$default(
      INSTANCE, "kazimierz_arena_gate", NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND(), 30, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KAZIMIERZ_KNIGHT_MONUMENT = landmark$default(
      INSTANCE, "kazimierz_knight_monument", NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND(), 46, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KAZDEL_BABEL_RUINS = landmark$default(
      INSTANCE, "kazdel_babel_ruins", NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES(), 36, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KAZDEL_SARKAZ_CAMP = landmark$default(
      INSTANCE, "kazdel_sarkaz_camp", NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES(), 52, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry LATERANO_REVELATION_TOWER = landmark$default(
      INSTANCE, "laterano_revelation_tower", NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(), 28, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry LATERANO_AMBROSIUS_CHAPEL = landmark$default(
      INSTANCE, "laterano_ambrosius_chapel", NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS(), 44, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry LEITHANIEN_TWIN_SPIRES = landmark$default(
      INSTANCE, "leithanien_twin_spires", NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST(), 34, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry LEITHANIEN_CONCERT_HALL = landmark$default(
      INSTANCE, "leithanien_concert_hall", NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST(), 50, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry RIM_BILLITON_MINING_DERRICK = landmark$default(
      INSTANCE, "rim_billiton_mining_derrick", NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS(), 32, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry RIM_BILLITON_RAIL_DEPOT = landmark$default(
      INSTANCE, "rim_billiton_rail_depot", NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS(), 48, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry MINOS_HEROES_TEMPLE = landmark$default(
      INSTANCE, "minos_heroes_temple", NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS(), 28, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry MINOS_HEROES_PLAZA = landmark$default(
      INSTANCE, "minos_heroes_plaza", NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS(), 44, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SARGON_GOLDEN_BAZAAR = landmark$default(
      INSTANCE, "sargon_golden_bazaar", NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT(), 34, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SARGON_LONG_SPRING_WELL = landmark$default(
      INSTANCE, "sargon_long_spring_well", NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT(), 50, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SAMI_CYCLOPS_ALTAR = landmark$default(
      INSTANCE, "sami_cyclops_altar", NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST(), 38, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SAMI_SNOWPRIEST_LODGE = landmark$default(
      INSTANCE, "sami_snowpriest_lodge", NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST(), 54, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry VICTORIA_DEFENCE_CANNON = landmark$default(
      INSTANCE, "victoria_defence_cannon", NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(), 32, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry VICTORIA_STEAM_STATION = landmark$default(
      INSTANCE, "victoria_steam_station", NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS(), 48, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry URSUS_SARCOPHAGUS_STATION = landmark$default(
      INSTANCE, "ursus_sarcophagus_station", NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE(), 34, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry URSUS_NORTHERN_MINE_TOWER = landmark$default(
      INSTANCE, "ursus_northern_mine_tower", NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE(), 50, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KJERAG_KARLAN_MONASTERY = landmark$default(
      INSTANCE, "kjerag_karlan_monastery", NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS(), 40, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry KJERAG_SACRED_PLAZA = landmark$default(
      INSTANCE, "kjerag_sacred_plaza", NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS(), 56, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SIRACUSA_FAMILY_COURT = landmark$default(
      INSTANCE, "siracusa_family_court", NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND(), 30, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry SIRACUSA_FAMILY_THEATRE = landmark$default(
      INSTANCE, "siracusa_family_theatre", NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND(), 46, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry YAN_YUMEN_BEACON = landmark$default(
      INSTANCE, "yan_yumen_beacon", NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE(), 36, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry YAN_SHANGSHU_PAVILION = landmark$default(
      INSTANCE, "yan_shangshu_pavilion", NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE(), 52, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry IBERIA_EYE_LIGHTHOUSE = landmark$default(
      INSTANCE, "iberia_eye_lighthouse", NationBiomes.INSTANCE.getIBERIA_SALT_DELTA(), 38, null, 0, 24, null
  );
  @NotNull
  private static final JigsawBuildingEntry IBERIA_SALTVIND_CHAPEL = landmark$default(
      INSTANCE, "iberia_saltwind_chapel", NationBiomes.INSTANCE.getIBERIA_SALT_DELTA(), 54, null, 0, 24, null
  );

  private NationLandmarks() {
  }

  // $VF: synthetic method
  static JigsawBuildingEntry landmark$default(NationLandmarks var0, String var1, ResourceKey var2, int var3, Types var4, int var5, int var6, Object var7) {
    if ((var6 & 8) != 0) {
      var4 = Types.WORLD_SURFACE_WG;
    }

    if ((var6 & 16) != 0) {
      var5 = 0;
    }

    return var0.landmark(var1, var2, var3, var4, var5);
  }

  @NotNull
  public final JigsawBuildingEntry getAEGIR_VOLCANIC_BEACON() {
    return AEGIR_VOLCANIC_BEACON;
  }

  @NotNull
  public final JigsawBuildingEntry getAEGIR_ABYSSAL_OBSERVATORY() {
    return AEGIR_ABYSSAL_OBSERVATORY;
  }

  @NotNull
  public final JigsawBuildingEntry getBOLIVAR_DOSSOLES_YACHT() {
    return BOLIVAR_DOSSOLES_YACHT;
  }

  @NotNull
  public final JigsawBuildingEntry getBOLIVAR_RACE_CHECKPOINT() {
    return BOLIVAR_RACE_CHECKPOINT;
  }

  @NotNull
  public final JigsawBuildingEntry getHIGASHI_RIFT_SHRINE() {
    return HIGASHI_RIFT_SHRINE;
  }

  @NotNull
  public final JigsawBuildingEntry getHIGASHI_SOKOGAWA_WATCHTOWER() {
    return HIGASHI_SOKOGAWA_WATCHTOWER;
  }

  @NotNull
  public final JigsawBuildingEntry getDURIN_DOME_STATION() {
    return DURIN_DOME_STATION;
  }

  @NotNull
  public final JigsawBuildingEntry getDURIN_WATER_PARK() {
    return DURIN_WATER_PARK;
  }

  @NotNull
  public final JigsawBuildingEntry getCOLUMBIA_FRONTIER_LAB() {
    return COLUMBIA_FRONTIER_LAB;
  }

  @NotNull
  public final JigsawBuildingEntry getCOLUMBIA_PRISON_OUTPOST() {
    return COLUMBIA_PRISON_OUTPOST;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZIMIERZ_ARENA_GATE() {
    return KAZIMIERZ_ARENA_GATE;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZIMIERZ_KNIGHT_MONUMENT() {
    return KAZIMIERZ_KNIGHT_MONUMENT;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZDEL_BABEL_RUINS() {
    return KAZDEL_BABEL_RUINS;
  }

  @NotNull
  public final JigsawBuildingEntry getKAZDEL_SARKAZ_CAMP() {
    return KAZDEL_SARKAZ_CAMP;
  }

  @NotNull
  public final JigsawBuildingEntry getLATERANO_REVELATION_TOWER() {
    return LATERANO_REVELATION_TOWER;
  }

  @NotNull
  public final JigsawBuildingEntry getLATERANO_AMBROSIUS_CHAPEL() {
    return LATERANO_AMBROSIUS_CHAPEL;
  }

  @NotNull
  public final JigsawBuildingEntry getLEITHANIEN_TWIN_SPIRES() {
    return LEITHANIEN_TWIN_SPIRES;
  }

  @NotNull
  public final JigsawBuildingEntry getLEITHANIEN_CONCERT_HALL() {
    return LEITHANIEN_CONCERT_HALL;
  }

  @NotNull
  public final JigsawBuildingEntry getRIM_BILLITON_MINING_DERRICK() {
    return RIM_BILLITON_MINING_DERRICK;
  }

  @NotNull
  public final JigsawBuildingEntry getRIM_BILLITON_RAIL_DEPOT() {
    return RIM_BILLITON_RAIL_DEPOT;
  }

  @NotNull
  public final JigsawBuildingEntry getMINOS_HEROES_TEMPLE() {
    return MINOS_HEROES_TEMPLE;
  }

  @NotNull
  public final JigsawBuildingEntry getMINOS_HEROES_PLAZA() {
    return MINOS_HEROES_PLAZA;
  }

  @NotNull
  public final JigsawBuildingEntry getSARGON_GOLDEN_BAZAAR() {
    return SARGON_GOLDEN_BAZAAR;
  }

  @NotNull
  public final JigsawBuildingEntry getSARGON_LONG_SPRING_WELL() {
    return SARGON_LONG_SPRING_WELL;
  }

  @NotNull
  public final JigsawBuildingEntry getSAMI_CYCLOPS_ALTAR() {
    return SAMI_CYCLOPS_ALTAR;
  }

  @NotNull
  public final JigsawBuildingEntry getSAMI_SNOWPRIEST_LODGE() {
    return SAMI_SNOWPRIEST_LODGE;
  }

  @NotNull
  public final JigsawBuildingEntry getVICTORIA_DEFENCE_CANNON() {
    return VICTORIA_DEFENCE_CANNON;
  }

  @NotNull
  public final JigsawBuildingEntry getVICTORIA_STEAM_STATION() {
    return VICTORIA_STEAM_STATION;
  }

  @NotNull
  public final JigsawBuildingEntry getURSUS_SARCOPHAGUS_STATION() {
    return URSUS_SARCOPHAGUS_STATION;
  }

  @NotNull
  public final JigsawBuildingEntry getURSUS_NORTHERN_MINE_TOWER() {
    return URSUS_NORTHERN_MINE_TOWER;
  }

  @NotNull
  public final JigsawBuildingEntry getKJERAG_KARLAN_MONASTERY() {
    return KJERAG_KARLAN_MONASTERY;
  }

  @NotNull
  public final JigsawBuildingEntry getKJERAG_SACRED_PLAZA() {
    return KJERAG_SACRED_PLAZA;
  }

  @NotNull
  public final JigsawBuildingEntry getSIRACUSA_FAMILY_COURT() {
    return SIRACUSA_FAMILY_COURT;
  }

  @NotNull
  public final JigsawBuildingEntry getSIRACUSA_FAMILY_THEATRE() {
    return SIRACUSA_FAMILY_THEATRE;
  }

  @NotNull
  public final JigsawBuildingEntry getYAN_YUMEN_BEACON() {
    return YAN_YUMEN_BEACON;
  }

  @NotNull
  public final JigsawBuildingEntry getYAN_SHANGSHU_PAVILION() {
    return YAN_SHANGSHU_PAVILION;
  }

  @NotNull
  public final JigsawBuildingEntry getIBERIA_EYE_LIGHTHOUSE() {
    return IBERIA_EYE_LIGHTHOUSE;
  }

  @NotNull
  public final JigsawBuildingEntry getIBERIA_SALTVIND_CHAPEL() {
    return IBERIA_SALTVIND_CHAPEL;
  }

  private final JigsawBuildingEntry landmark(String path, ResourceKey<Biome> biome, int ringDistance, Types heightmap, int startHeight) {
    return StructureCatalog.uniqueLandmark$default(
        Zinecraft.INSTANCE.getSTRUCTURES(), path, "nation_landmarks/" + path, biome, ringDistance, 96, heightmap, startHeight, 0.0F, 128, null
    );
  }
}
