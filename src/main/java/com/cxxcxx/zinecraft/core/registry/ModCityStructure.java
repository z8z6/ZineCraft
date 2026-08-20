package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.nation.TerraNation;
import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import com.cxxcxx.zinecraft.api.registry.builder.BiomeBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.city.*;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.nation.TerraGeography;
import com.cxxcxx.zinecraft.core.worldgen.city.GridCityLayout;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;

import java.util.*;

/**
 * 固定在旅行地图城市坐标上的四层移动城市 Blockout。
 */
public final class ModCityStructure {
  public static final int PLATE_WIDTH = 32;
  public static final int PLATE_DEPTH = 32;
  public static final int LAYER_COUNT = 4;
  public static final int LAYER_HEIGHT = 8;
  private static final Map<String, MobileCityProgram> CITY_PROGRAMS = cityPrograms();
  private static final List<ResourceLocation> MUTABLE_REGION_LANDMARKS = new ArrayList<>();
  public static final List<MobileCityBlockoutDefinition> DEFINITIONS = registerPlaces();
  public static final List<ResourceLocation> REGION_LANDMARKS = List.copyOf(MUTABLE_REGION_LANDMARKS);

  private ModCityStructure() {
  }

  private static List<MobileCityBlockoutDefinition> registerPlaces() {
    List<MobileCityBlockoutDefinition> definitions = new ArrayList<>();
    Set<String> ids = new HashSet<>();
    Set<Long> chunks = new HashSet<>();
    EnumSet<TerraNation> nations = EnumSet.noneOf(TerraNation.class);
    for (TerraPlace place : TerraGeography.PLACES) {
      if (!place.type().isUrban()) {
        registerRegionLandmark(place, ids, chunks);
        continue;
      }
      String cityPath = "blockout/terra_city/" + place.id();
      String templatePath = "blockout/terra_cities/" + place.id();
      ResourceLocation structureId = Zinecraft.id(cityPath);
      ResourceLocation templateRoot = Zinecraft.id(templatePath);
      ResourceLocation placeholderBlock = nationWall(place.nation());
      MobileCityProgram program = CITY_PROGRAMS.get(place.id());
      if (program == null) throw new IllegalStateException("城市缺少逐城 Java 建筑声明：" + place.id());
      MobileCityBlockoutDefinition definition = new MobileCityBlockoutDefinition(
          structureId,
          place,
          templateRoot,
          PLATE_WIDTH,
          PLATE_DEPTH,
          LAYER_COUNT,
          LAYER_HEIGHT,
          program,
          placeholderBlock
      );
      if (!ids.add(structureId.toString())) throw new IllegalStateException("移动城市结构 ID 重复：" + structureId);
      int chunkX = Math.floorDiv(place.x(), 16) - 1;
      int chunkZ = Math.floorDiv(place.z(), 16) - 1;
      long chunkKey = ((long) chunkX << 32) ^ (chunkZ & 0xffffffffL);
      if (!chunks.add(chunkKey)) throw new IllegalStateException("移动城市固定区块重叠：" + place.id());
      registerStructure(definition, chunkX, chunkZ);
      definitions.add(definition);
      nations.add(place.nation());
    }
    long expected = TerraGeography.PLACES.stream().filter(place -> place.type().isUrban()).count();
    if (definitions.size() != expected
        || CITY_PROGRAMS.size() != expected
        || ids.size() != TerraGeography.PLACES.size()
        || MUTABLE_REGION_LANDMARKS.size() != TerraGeography.PLACES.size() - expected
        || !nations.equals(EnumSet.allOf(TerraNation.class))) {
      throw new IllegalStateException("移动城市 Blockout 必须覆盖十九国全部城市、聚落与城区");
    }
    return List.copyOf(definitions);
  }

  private static Map<String, MobileCityProgram> cityPrograms() {
    List<MobileCityProgram> declarations = List.of(
        city("aegir/city_rk1itf", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 1, 2, 3)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 14, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("bolivar/city_brmsww", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 1, 3, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("bolivar/city_csgzc5", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 1, 4, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("bolivar/city_hd6gk", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 1, 2, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_13381j", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 1, 3, 3)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_spmkw6", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 1, 4, 3)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_dncdf", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 1, 2, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_crnz3", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 1, 3, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_bzd37", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 1, 4, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/city_d7rher", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 1, 2, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("higashi/settlement_mjzhx", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 1, 3, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("durin/city_mgol9", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 1, 4, 4)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("durin/city_ds6j6", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 2, 2, 5)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_hh4zq", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 2, 3, 5)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_cxt6o5", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 2, 4, 5)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_bhfpou", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 2, 2, 5)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_gj6u8l", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 2, 3, 5)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_bjg8q0", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 2, 4, 5)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_mjajp", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 2, 2, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_1muane4", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 2, 3, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_1jvxrar", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 2, 4, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_jv8vu", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 2, 2, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/city_b1vhe3", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 2, 3, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/settlement_mjgnu", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 2, 4, 6)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("columbia/settlement_lod1t", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 3, 2, 7)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/city_vdd9bt", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 3, 3, 7)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/city_gzycqp", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 3, 4, 7)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/city_7wu2jr", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 3, 2, 7)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/settlement_gs0wh", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 3, 3, 7)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/settlement_deq4w", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 3, 4, 7)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazimierz/settlement_gfy8d", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 3, 2, 8)), building("residence", "住宅", 9, 9, 12, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 9, 100), building("public_building", "公共建筑", 10, 10, 11, 100)),
        city("kazdel/city_vvl1q1", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 3, 3, 8)), building("residence", "住宅", 9, 9, 8, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 9, 100)),
        city("kazdel/settlement_ibuewf", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 3, 4, 8)), building("residence", "住宅", 9, 9, 8, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 9, 100)),
        city("laterano/district_b0e0yf", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 3, 2, 8)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_f4ri6h", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 3, 3, 8)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_e5tfi8", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 3, 4, 8)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_fwb4a", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 4, 2, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_82r4mu", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 4, 3, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_5ezpcz", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 4, 4, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("laterano/district_1e5v3vp", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 4, 2, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 15, 100)),
        city("leithanien/city_mbm8g7", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 4, 3, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 13, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("leithanien/city_e0qpro", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 4, 4, 3)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 13, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("leithanien/city_gli2ql", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 4, 2, 4)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 13, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("leithanien/city_frw8w", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 4, 3, 4)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 10, 100), building("workshop", "工坊", 10, 9, 13, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("rim_billiton/city_bxl0ik", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 4, 4, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/city_w7q3o0", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 4, 2, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/city_meuu2", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 4, 3, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_bpe083", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 4, 4, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_gm5ct", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 5, 2, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_jrxhe", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 5, 3, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_dsh72", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 5, 4, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_j4yp3", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 5, 2, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("rim_billiton/settlement_j9a34i", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 5, 3, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 8, 100)),
        city("minos/city_fsqqf8", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 5, 4, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("minos/city_jlp43f", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 5, 2, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("minos/city_csze3s", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 5, 3, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("minos/settlement_u0135m", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 5, 4, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("minos/city_k277", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 5, 2, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("sargon/settlement_mfm1p", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 5, 3, 6)), building("residence", "住宅", 9, 9, 8, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 10, 100)),
        city("sargon/city_l40df", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 5, 4, 6)), building("residence", "住宅", 9, 9, 8, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 10, 100)),
        city("sami/settlement_dx2cj", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 6, 2, 7)), building("residence", "住宅", 9, 9, 8, 100), building("shop", "商店", 9, 10, 7, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 7, 100)),
        city("victoria/city_aj8obq", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 6, 3, 7)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/settlement_j6hzz", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 6, 4, 7)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/settlement_atyhhe", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 6, 2, 7)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/settlement_1y9mecn", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 6, 3, 7)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/settlement_azuysz", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 6, 4, 7)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/city_dmrw8s", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 6, 2, 8)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/city_aevowi", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 6, 3, 8)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/city_cpalz", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 6, 4, 8)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("victoria/city_ayawwh", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 6, 2, 8)), building("residence", "住宅", 9, 9, 13, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 14, 100)),
        city("ursus/city_di8r9", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 6, 3, 8)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_1r22fca", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 6, 4, 8)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_1csmvru", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 1, 9, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_1usuttp", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 1, 10, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_18xe8sb", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 1, 11, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_3mpu0p", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 1, 9, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_gcir0n", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 1, 10, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_cy1wt1", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 1, 11, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_uui7tp", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 1, 9, 4)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/city_n8tsm5", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 1, 10, 4)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("ursus/settlement_1nxlgno", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 1, 11, 4)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 11, 100), building("workshop", "工坊", 10, 9, 12, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("kjerag/city_bkredv", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 1, 9, 4)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 10, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_h78y13", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 1, 10, 4)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_bt4ah", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 1, 11, 4)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_em5px", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 2, 9, 5)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_iblfip", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 2, 10, 5)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_e6wha", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 2, 11, 5)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_e2uzpq", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 2, 9, 5)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("siracusa/city_1kiqwam", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 2, 10, 5)), building("residence", "住宅", 9, 9, 11, 100), building("shop", "商店", 9, 10, 9, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_krso", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 2, 11, 5)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_rywf", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 2, 9, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_dxemy", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 2, 10, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_fmu1", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 2, 11, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_rhje", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 2, 9, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_i648", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 2, 10, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_n0wg", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 2, 11, 6)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_ckf4o", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 3, 9, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_c0b6a", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 3, 10, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_gena", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 3, 11, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_kh33", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 3, 9, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/city_dox0j", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 14, 3, 10, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/settlement_ln03z", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 15, 3, 11, 7)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/settlement_dojwc", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 16, 3, 9, 8)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/district_fcgck", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 17, 3, 10, 8)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("yan/district_gdwwa", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 18, 3, 11, 8)), building("residence", "住宅", 9, 9, 9, 100), building("shop", "商店", 9, 10, 8, 100), building("workshop", "工坊", 10, 9, 11, 100), building("public_building", "公共建筑", 10, 10, 12, 100)),
        city("iberia/city_1yc9adx", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 19, 3, 9, 8)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/city_i4yvk", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 20, 3, 10, 8)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/settlement_gwsde", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 21, 3, 11, 8)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/city_dhqx0a", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 10, 4, 9, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/city_jlr2sx", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 11, 4, 10, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/city_jjce", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 12, 4, 11, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100)),
        city("iberia/settlement_mtfd2", GridCityLayout.DEFAULT, List.of(landmark("city_center", "城市中心", 15, 17, 13, 4, 9, 3)), building("residence", "住宅", 9, 9, 10, 100), building("shop", "商店", 9, 10, 12, 100), building("workshop", "工坊", 10, 9, 8, 100), building("public_building", "公共建筑", 10, 10, 13, 100))
    );
    Map<String, MobileCityProgram> programs = new LinkedHashMap<>();
    for (MobileCityProgram declaration : declarations) {
      validateOrdinaryBuildingLayout(declaration);
      if (programs.put(declaration.placeId(), declaration) != null) {
        throw new IllegalStateException("逐城 Java 建筑声明重复：" + declaration.placeId());
      }
    }
    return Map.copyOf(programs);
  }

  private static void validateOrdinaryBuildingLayout(MobileCityProgram program) {
    CityRect plate = CityRect.sized(0, 0, PLATE_WIDTH, PLATE_DEPTH);
    var district = new CityDefinition.DistrictDefinition(
        "blockout",
        CityDistrictType.RESIDENTIAL,
        plate,
        100,
        22
    );
    var lots = program.layout().createBuildingLots(new CityLayout.Context(
        plate,
        List.of(),
        List.of(district),
        CityRoadClass.LOCAL,
        (x, z) -> new CityPlanner.TerrainCell(24, 0, 0, true)
    ));
    if (lots.isEmpty()) throw new IllegalStateException(program.placeId() + " 的布局没有合法普通建筑用地");
    for (MobileCityProgram.Building building : program.buildings()) {
      boolean fits = lots.stream().anyMatch(lot ->
          building.width() <= lot.bounds().width() && building.depth() <= lot.bounds().depth()
      );
      if (!fits)
        throw new IllegalStateException(program.placeId() + " 的普通建筑越出布局用地：" + building.structureId());
    }
  }

  private static MobileCityProgram city(
      String placeId,
      GridCityLayout layout,
      List<LandmarkSpec> landmarkSpecs,
      BuildingSpec... buildingSpecs
  ) {
    TerraPlace place = TerraGeography.PLACES.stream()
        .filter(candidate -> candidate.id().equals(placeId))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("逐城结构声明引用未知地点：" + placeId));
    List<MobileCityProgram.Landmark> landmarks = landmarkSpecs.stream()
        .map(spec -> registerLandmark(place, spec))
        .toList();
    List<MobileCityProgram.Building> buildings = Arrays.stream(buildingSpecs)
        .map(spec -> registerBuilding(place, spec))
        .toList();
    return new MobileCityProgram(placeId, layout, landmarks, buildings);
  }

  private static LandmarkSpec landmark(
      String id,
      String zhCn,
      int width,
      int depth,
      int height,
      int towerX,
      int towerZ,
      int towerHeight
  ) {
    return new LandmarkSpec(id, zhCn, width, depth, height, towerX, towerZ, towerHeight);
  }

  private static BuildingSpec building(
      String id,
      String zhCn,
      int width,
      int depth,
      int height,
      int weight
  ) {
    return new BuildingSpec(id, zhCn, width, depth, height, weight);
  }

  private static MobileCityProgram.Landmark registerLandmark(TerraPlace place, LandmarkSpec spec) {
    ResourceLocation structureId = cityAssetId(place, spec.id());
    ResourceLocation template = cityAssetTemplate(place, spec.id());
    Zinecraft.STRUCTURES.embeddedBuilding(
        structureId.getPath(),
        place.zhCn() + "_" + spec.zhCn(),
        place.enUs() + " City Center",
        template.getPath(),
        32
    );
    return new MobileCityProgram.Landmark(
        structureId, template, place.zhCn() + "_" + spec.zhCn(),
        spec.width(), spec.depth(), spec.height(), spec.towerX(), spec.towerZ(), spec.towerHeight()
    );
  }

  private static MobileCityProgram.Building registerBuilding(TerraPlace place, BuildingSpec spec) {
    ResourceLocation structureId = cityAssetId(place, spec.id());
    ResourceLocation template = cityAssetTemplate(place, spec.id());
    Zinecraft.STRUCTURES.embeddedBuilding(
        structureId.getPath(),
        place.zhCn() + "_" + spec.zhCn(),
        place.enUs() + " " + spec.id(),
        template.getPath(),
        32
    );
    return new MobileCityProgram.Building(
        structureId, template, place.zhCn() + "_" + spec.zhCn(),
        spec.width(), spec.depth(), spec.height(), spec.weight()
    );
  }

  private static ResourceLocation cityAssetId(TerraPlace place, String role) {
    return Zinecraft.id("city_asset/" + place.id() + "/" + role);
  }

  private static ResourceLocation cityAssetTemplate(TerraPlace place, String role) {
    return Zinecraft.id("blockout/terra_city_assets/" + place.id() + "/" + role);
  }

  private record LandmarkSpec(
      String id,
      String zhCn,
      int width,
      int depth,
      int height,
      int towerX,
      int towerZ,
      int towerHeight
  ) {
  }

  private record BuildingSpec(String id, String zhCn, int width, int depth, int height, int weight) {
  }

  private static void registerRegionLandmark(TerraPlace place, Set<String> ids, Set<Long> chunks) {
    ResourceLocation structureId = Zinecraft.id("blockout/terra_region/" + place.id());
    if (!ids.add(structureId.toString())) throw new IllegalStateException("地区地标结构 ID 重复：" + structureId);
    int chunkX = Math.floorDiv(place.x(), 16) - 1;
    int chunkZ = Math.floorDiv(place.z(), 16) - 1;
    long chunkKey = ((long) chunkX << 32) ^ (chunkZ & 0xffffffffL);
    if (!chunks.add(chunkKey)) throw new IllegalStateException("城市或地区固定区块重叠：" + place.id());
    List<net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>> biomes =
        ModBiome.NATIONAL_BIOMES.get(place.nation()).stream().map(BiomeBuilder::key).toList();
    String template = "blockout/terra_regions/" + place.id() + "/landmark";
    Zinecraft.STRUCTURES
        .jigsaw(structureId.getPath(), place.zhCn() + "地区地标占位")
        .enUs(place.enUs() + " Region Landmark Blockout")
        .fixedAt(chunkX, chunkZ)
        .allowedBiomes(biomes)
        .layout(1, 32)
        .height(Types.WORLD_SURFACE_WG, 0)
        .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)
        .pool("start", Projection.RIGID, pool -> pool.template(template))
        .build();
    MUTABLE_REGION_LANDMARKS.add(structureId);
  }

  private static void registerStructure(
      MobileCityBlockoutDefinition definition,
      int chunkX,
      int chunkZ
  ) {
    TerraPlace place = definition.place();
    List<net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>> biomes =
        ModBiome.NATIONAL_BIOMES.get(place.nation()).stream().map(BiomeBuilder::key).toList();
    String templateRoot = definition.templateRoot().getPath();
    JigsawBuilder builder = Zinecraft.STRUCTURES
        .jigsaw(definition.structureId().getPath(), place.zhCn() + "移动城市占位")
        .enUs(place.enUs() + " Mobile City Blockout")
        .fixedAt(chunkX, chunkZ)
        .allowedBiomes(biomes)
        .layout(1, 112)
        .generation(Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)
        .startPool("start");
    if (place.nation() == TerraNation.AEGIR) {
      builder.height(Types.OCEAN_FLOOR_WG, 0);
    } else if (place.nation() == TerraNation.DURIN) {
      builder.height(null, 24);
    } else {
      builder.height(Types.WORLD_SURFACE_WG, 0);
    }
    builder
        .pool("start", Projection.RIGID, pool -> pool.template(templateRoot + "/center"))
        .pool("north", Projection.RIGID, pool -> pool.template(templateRoot + "/north"))
        .pool("south", Projection.RIGID, pool -> pool.template(templateRoot + "/south"))
        .pool("east", Projection.RIGID, pool -> pool.template(templateRoot + "/east"))
        .pool("west", Projection.RIGID, pool -> pool.template(templateRoot + "/west"))
        .build();
  }

  private static ResourceLocation nationWall(TerraNation nation) {
    String path = switch (nation) {
      case AEGIR -> "aegir_pressure_tile";
      case BOLIVAR -> "bolivar_dossoles_stucco";
      case HIGASHI -> "higashi_machiya_plaster";
      case DURIN -> "durin_ideal_city_panel";
      case COLUMBIA -> "columbia_frontier_panel";
      case KAZIMIERZ -> "kazimierz_arena_masonry";
      case KAZDEL -> "kazdel_fortress_plate";
      case LATERANO -> "laterano_basilica_marble";
      case LEITHANIEN -> "leithanien_resonant_brick";
      case RIM_BILLITON -> "rim_billiton_corrugated_steel";
      case MINOS -> "minos_heroic_masonry";
      case SARGON -> "sargon_oasis_adobe";
      case SAMI -> "sami_tribal_timber";
      case VICTORIA -> "victoria_industrial_brick";
      case URSUS -> "ursus_imperial_masonry";
      case KJERAG -> "kjerag_monastery_stone";
      case SIRACUSA -> "siracusa_family_masonry";
      case YAN -> "yan_courtyard_brick";
      case IBERIA -> "iberia_coastal_masonry";
    };
    return Zinecraft.id(path);
  }

  public static void bootstrap() {
  }
}
