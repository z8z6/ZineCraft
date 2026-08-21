package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import org.jetbrains.annotations.Nullable;

public final class ModStructure {
  public static final JigsawBuilder STARGATE = Zinecraft.STRUCTURES.jigsaw("stargate", "萨米星门")
      .enUs("Sami Stargate")
      .biome(ModBiome.SAMI_FROZEN_FOREST.key())
      .layout(1, 32)
      .height(Types.WORLD_SURFACE_WG, 0)
      .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN)
      .pool("start", Projection.RIGID, pool -> pool.template("stargate", 1))
      .build();

  public static final JigsawBuilder AEGIR_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("aegir_shop", "阿戈尔商铺", 32);
  public static final JigsawBuilder BOLIVAR_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("bolivar_shop", "玻利瓦尔商铺", 32);
  public static final JigsawBuilder HIGASHI_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("higashi_shop", "东国商铺", 32);
  public static final JigsawBuilder DURIN_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("durin_shop", "杜林商铺", 32);
  public static final JigsawBuilder COLUMBIA_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("columbia_shop", "哥伦比亚商铺", 32);
  public static final JigsawBuilder KAZIMIERZ_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("kazimierz_shop", "卡西米尔商铺", 32);
  public static final JigsawBuilder KAZDEL_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("kazdel_shop", "卡兹戴尔商铺", 32);
  public static final JigsawBuilder LATERANO_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("laterano_shop", "拉特兰商铺", 32);
  public static final JigsawBuilder LEITHANIEN_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("leithanien_shop", "莱塔尼亚商铺", 32);
  public static final JigsawBuilder RIM_BILLITON_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("rim_billiton_shop", "雷姆必拓商铺", 32);
  public static final JigsawBuilder MINOS_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("minos_shop", "米诺斯商铺", 32);
  public static final JigsawBuilder SARGON_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("sargon_shop", "萨尔贡商铺", 32);
  public static final JigsawBuilder SAMI_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("sami_shop", "萨米商铺", 32);
  public static final JigsawBuilder VICTORIA_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("victoria_shop", "维多利亚商铺", 32);
  public static final JigsawBuilder URSUS_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("ursus_shop", "乌萨斯商铺", 32);
  public static final JigsawBuilder KJERAG_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("kjerag_shop", "谢拉格商铺", 32);
  public static final JigsawBuilder SIRACUSA_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("siracusa_shop", "叙拉古商铺", 32);
  public static final JigsawBuilder SIESTA_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("siesta_shop", "汐斯塔商铺", 32);
  public static final JigsawBuilder YAN_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("yan_shop", "炎商铺", 32);
  public static final JigsawBuilder IBERIA_SHOP = Zinecraft.STRUCTURES.embeddedBuilding("iberia_shop", "伊比利亚商铺", 32);

  public static final JigsawBuilder AEGIR_VOLCANIC_BEACON = building("aegir_volcanic_beacon", "阿戈尔火山信标", ModBiome.AEGIR_ABYSSAL_SEA.key(), Types.OCEAN_FLOOR_WG, 0);
  public static final JigsawBuilder AEGIR_ABYSSAL_OBSERVATORY = building("aegir_abyssal_observatory", "阿戈尔深渊观测站", ModBiome.AEGIR_ABYSSAL_SEA.key(), Types.OCEAN_FLOOR_WG, 0);
  public static final JigsawBuilder BOLIVAR_DOSSOLES_YACHT = building("bolivar_dossoles_yacht", "玻利瓦尔多索雷斯游艇", ModBiome.BOLIVAR_PLAIN.key(), null, 0);
  public static final JigsawBuilder BOLIVAR_RACE_CHECKPOINT = building("bolivar_race_checkpoint", "玻利瓦尔竞速检查站", ModBiome.BOLIVAR_PLAIN.key(), null, 0);
  public static final JigsawBuilder HIGASHI_RIFT_SHRINE = building("higashi_rift_shrine", "东国裂谷神社", ModBiome.HIGASHI_SHADOW_RIFT.key(), null, 0);
  public static final JigsawBuilder HIGASHI_SOKOGAWA_WATCHTOWER = building("higashi_sokogawa_watchtower", "东国索谷川瞭望塔", ModBiome.HIGASHI_SHADOW_RIFT.key(), null, 0);
  public static final JigsawBuilder DURIN_DOME_STATION = building("durin_dome_station", "杜林穹顶车站", ModBiome.DURIN_UNDERGROUND_GARDEN.key(), null, 24);
  public static final JigsawBuilder DURIN_WATER_PARK = building("durin_water_park", "杜林水上乐园", ModBiome.DURIN_UNDERGROUND_GARDEN.key(), null, 24);
  public static final JigsawBuilder COLUMBIA_FRONTIER_LAB = building("columbia_frontier_lab", "哥伦比亚边疆实验室", ModBiome.COLUMBIA_SANDSTONE_WILDS.key(), null, 0);
  public static final JigsawBuilder COLUMBIA_PRISON_OUTPOST = building("columbia_prison_outpost", "哥伦比亚监狱哨站", ModBiome.COLUMBIA_SANDSTONE_WILDS.key(), null, 0);
  public static final JigsawBuilder KAZIMIERZ_ARENA_GATE = building("kazimierz_arena_gate", "卡西米尔竞技场大门", ModBiome.KAZIMIERZ_KNIGHTLAND.key(), null, 0);
  public static final JigsawBuilder KAZIMIERZ_KNIGHT_MONUMENT = building("kazimierz_knight_monument", "卡西米尔骑士纪念碑", ModBiome.KAZIMIERZ_KNIGHTLAND.key(), null, 0);
  public static final JigsawBuilder KAZDEL_BABEL_RUINS = building("kazdel_babel_ruins", "卡兹戴尔巴别塔遗迹", ModBiome.KAZDEL_SCARRED_WASTES.key(), null, 0);
  public static final JigsawBuilder KAZDEL_SARKAZ_CAMP = building("kazdel_sarkaz_camp", "卡兹戴尔萨卡兹营地", ModBiome.KAZDEL_SCARRED_WASTES.key(), null, 0);
  public static final JigsawBuilder LATERANO_REVELATION_TOWER = building("laterano_revelation_tower", "拉特兰启示石塔", ModBiome.LATERANO_HOLY_FIELDS.key(), null, 0);
  public static final JigsawBuilder LATERANO_AMBROSIUS_CHAPEL = building("laterano_ambrosius_chapel", "拉特兰安布罗修礼拜堂", ModBiome.LATERANO_HOLY_FIELDS.key(), null, 0);
  public static final JigsawBuilder LATERANO_HOST = Zinecraft.STRUCTURES.jigsaw("laterano_host", "拉特兰主机")
      .biome(ModBiome.LATERANO_HOLY_FIELDS.key())
      .layout(1, 32)
      .height(Types.WORLD_SURFACE_WG, 0)
      .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN)
      .pool("start", Projection.RIGID, pool -> pool.template("laterano_host/core", 1))
      .build();
  public static final JigsawBuilder LEITHANIEN_TWIN_SPIRES = building("leithanien_twin_spires", "莱塔尼亚双塔", ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(), null, 0);
  public static final JigsawBuilder LEITHANIEN_CONCERT_HALL = building("leithanien_concert_hall", "莱塔尼亚音乐厅", ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(), null, 0);
  public static final JigsawBuilder RIM_BILLITON_MINING_DERRICK = building("rim_billiton_mining_derrick", "雷姆必拓采矿井架", ModBiome.RIM_BILLITON_MINING_BADLANDS.key(), null, 0);
  public static final JigsawBuilder RIM_BILLITON_RAIL_DEPOT = building("rim_billiton_rail_depot", "雷姆必拓铁路货站", ModBiome.RIM_BILLITON_MINING_BADLANDS.key(), null, 0);
  public static final JigsawBuilder MINOS_HEROES_TEMPLE = building("minos_heroes_temple", "米诺斯英雄神殿", ModBiome.MINOS_SUNLIT_HILLS.key(), null, 0);
  public static final JigsawBuilder MINOS_HEROES_PLAZA = building("minos_heroes_plaza", "米诺斯英雄广场", ModBiome.MINOS_SUNLIT_HILLS.key(), null, 0);
  public static final JigsawBuilder SARGON_GOLDEN_BAZAAR = building("sargon_golden_bazaar", "萨尔贡黄金集市", ModBiome.SARGON_ROCKY_DESERT.key(), null, 0);
  public static final JigsawBuilder SARGON_LONG_SPRING_WELL = building("sargon_long_spring_well", "萨尔贡长泉水井", ModBiome.SARGON_ROCKY_DESERT.key(), null, 0);
  public static final JigsawBuilder SAMI_CYCLOPS_ALTAR = building("sami_cyclops_altar", "萨米独眼巨人祭坛", ModBiome.SAMI_FROZEN_FOREST.key(), null, 0);
  public static final JigsawBuilder SAMI_SNOWPRIEST_LODGE = building("sami_snowpriest_lodge", "萨米雪祀居所", ModBiome.SAMI_FROZEN_FOREST.key(), null, 0);
  public static final JigsawBuilder VICTORIA_DEFENCE_CANNON = building("victoria_defence_cannon", "维多利亚防御炮台", ModBiome.VICTORIA_MISTY_HIGHLANDS.key(), null, 0);
  public static final JigsawBuilder VICTORIA_STEAM_STATION = building("victoria_steam_station", "维多利亚蒸汽车站", ModBiome.VICTORIA_MISTY_HIGHLANDS.key(), null, 0);
  public static final JigsawBuilder URSUS_SARCOPHAGUS_STATION = building("ursus_sarcophagus_station", "乌萨斯石棺站", ModBiome.URSUS_FROZEN_STEPPE.key(), null, 0);
  public static final JigsawBuilder URSUS_NORTHERN_MINE_TOWER = building("ursus_northern_mine_tower", "乌萨斯北方矿塔", ModBiome.URSUS_FROZEN_STEPPE.key(), null, 0);
  public static final JigsawBuilder KJERAG_KARLAN_MONASTERY = building("kjerag_karlan_monastery", "谢拉格喀兰修道院", ModBiome.KJERAG_SNOWY_PEAKS.key(), null, 0);
  public static final JigsawBuilder KJERAG_SACRED_PLAZA = building("kjerag_sacred_plaza", "谢拉格圣洁广场", ModBiome.KJERAG_SNOWY_PEAKS.key(), null, 0);
  public static final JigsawBuilder SIRACUSA_FAMILY_COURT = building("siracusa_family_court", "叙拉古家族法庭", ModBiome.SIRACUSA_RAINY_WOODLAND.key(), null, 0);
  public static final JigsawBuilder SIRACUSA_FAMILY_THEATRE = building("siracusa_family_theatre", "叙拉古家族剧院", ModBiome.SIRACUSA_RAINY_WOODLAND.key(), null, 0);
  public static final JigsawBuilder YAN_YUMEN_BEACON = building("yan_yumen_beacon", "炎国玉门烽台", ModBiome.YAN_MOUNTAIN_GROVE.key(), null, 0);
  public static final JigsawBuilder YAN_SHANGSHU_PAVILION = building("yan_shangshu_pavilion", "炎国尚蜀亭阁", ModBiome.YAN_MOUNTAIN_GROVE.key(), null, 0);
  public static final JigsawBuilder IBERIA_EYE_LIGHTHOUSE = building("iberia_eye_lighthouse", "伊比利亚之眼灯塔", ModBiome.IBERIA_SALT_DELTA.key(), null, 0);
  public static final JigsawBuilder IBERIA_SALTVIND_CHAPEL = building("iberia_saltwind_chapel", "伊比利亚盐风礼拜堂", ModBiome.IBERIA_SALT_DELTA.key(), null, 0);

  private ModStructure() {
  }

  private static JigsawBuilder building(
      String path,
      String zhCn,
      ResourceKey<Biome> biome,
      @Nullable Types heightmap,
      int startHeight
  ) {
    return Zinecraft.STRUCTURES.jigsaw(path, zhCn)
        .biome(biome)
        .layout(7, 112)
        .height(heightmap == null ? Types.WORLD_SURFACE_WG : heightmap, startHeight)
        .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN)
        .startPool("start")
        .pool("start", Projection.RIGID, pool -> pool.template(path + "/foundation", 1))
        .pool("core", Projection.RIGID, pool -> pool.template(path + "/core", 1))
        .pool("facade", Projection.RIGID, pool -> pool.template(path + "/facade", 1))
        .pool("roof", Projection.RIGID, pool -> pool.template(path + "/roof", 1))
        .pool("annex", Projection.RIGID, pool -> pool.template(path + "/annex", 1))
        .pool("surrounding", Projection.RIGID, pool -> pool.template(path + "/surrounding", 1))
        .build();
  }

  public static void bootstrap() {
  }
}
