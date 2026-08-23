package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionConnection;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import com.cxxcxx.zinecraft.api.world.city.ChunkRectangle;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

/**
 * 读取数据生成阶段计算好的泰拉 Nation、City、Region 边界。
 */
public final class TerraLayoutResource {
  public static final String DIRECTORY = "/data/" + Zinecraft.MOD_ID + "/terra_layout/";
  public static final String INDEX_PATH = DIRECTORY + "index.json.gz";

  private TerraLayoutResource() {
  }

  public static TerraLayoutPlan load() {
    return Holder.INSTANCE;
  }

  /** 在模组公共初始化阶段主动完成布局解析，避免首次进入世界时阻塞主线程。 */
  public static void preload() {
    load();
  }

  public static Optional<CityRegionCell> findRegion(TerraCityRegionBuilder target) {
    Objects.requireNonNull(target, "目标 Region 不能为空");
    return load().nations().stream()
        .flatMap(nation -> nation.cities().stream())
        .flatMap(city -> city.regions().stream())
        .filter(region -> region.region() == target)
        .findFirst();
  }

  /**
   * 查找完整覆盖指定区块的移动地块所属 Region。移动地块边界按右侧/下侧开区间处理，
   * 因而相邻矩形即使共享边界也不会同时认领同一区块。
   */
  public static Optional<CityRegionCell> mobilePlotRegion(int chunkX, int chunkZ) {
    return mobilePlotTerrain(chunkX, chunkZ).map(MobilePlotTerrain::region);
  }

  /** 返回移动地块及其城市级唯一地形 Profile，供结构使用冻结高度。 */
  public static Optional<MobilePlotTerrain> mobilePlotTerrain(int chunkX, int chunkZ) {
    long minBlockX = (long) chunkX * 16L;
    long minBlockZ = (long) chunkZ * 16L;
    long maxBlockX = minBlockX + 16L;
    long maxBlockZ = minBlockZ + 16L;
    for (NationLayoutPlan nation : load().nations()) {
      for (CityLayoutPlan city : nation.cities()) {
        for (CityRegionCell region : city.regions()) {
          PlanarRectangle bounds = region.mobilePlotBounds();
          double plotMinX = bounds.center().x() - bounds.halfSizeX();
          double plotMaxX = bounds.center().x() + bounds.halfSizeX();
          double plotMinZ = bounds.center().z() - bounds.halfSizeZ();
          double plotMaxZ = bounds.center().z() + bounds.halfSizeZ();
          if (minBlockX >= plotMinX && maxBlockX <= plotMaxX
              && minBlockZ >= plotMinZ && maxBlockZ <= plotMaxZ) {
            return Optional.of(new MobilePlotTerrain(region, city.terrainProfile()));
          }
        }
      }
    }
    return Optional.empty();
  }

  public record MobilePlotTerrain(CityRegionCell region, CityTerrainProfile profile) {
  }

  private static TerraLayoutPlan read() {
    long startedAt = System.nanoTime();
    try {
      JsonObject index = readCompressed(INDEX_PATH);
      if (index.get("schema_version").getAsInt() != 14) {
        throw new IllegalStateException("不支持的泰拉布局 schema_version");
      }
      ArrayList<NationLayoutPlan> nations = new ArrayList<>();
      for (JsonElement element : index.getAsJsonArray("nation_ids")) {
        String nationId = element.getAsString();
        JsonObject nationFile = readCompressed(DIRECTORY + "nations/" + nationId + ".json.gz");
        if (nationFile.get("schema_version").getAsInt() != 14) {
          throw new IllegalStateException("国家布局 schema_version 不一致：" + nationId);
        }
        nations.add(nation(nationFile.getAsJsonObject("nation")));
      }
      TerraLayoutPlan plan = new TerraLayoutPlan(polygon(index.getAsJsonArray("boundary")), nations);
      int cityCount = plan.nations().stream().mapToInt(nation -> nation.cities().size()).sum();
      int regionCount = plan.nations().stream()
          .flatMap(nation -> nation.cities().stream())
          .mapToInt(city -> city.regions().size())
          .sum();
      long elapsedMillis = (System.nanoTime() - startedAt) / 1_000_000L;
      Zinecraft.LOGGER.info(
          "已预加载泰拉布局：国家={}，城市={}，Region={}，耗时={} ms",
          plan.nations().size(), cityCount, regionCount, elapsedMillis
      );
      return plan;
    } catch (IOException | RuntimeException exception) {
      throw new IllegalStateException("无法读取泰拉布局压缩资源：" + INDEX_PATH, exception);
    }
  }

  private static JsonObject readCompressed(String path) throws IOException {
    var stream = TerraLayoutResource.class.getResourceAsStream(path);
    if (stream == null) throw new IllegalStateException("缺少泰拉布局资源：" + path);
    try (stream; var gzip = new GZIPInputStream(stream);
         var reader = new InputStreamReader(gzip, StandardCharsets.UTF_8)) {
      return JsonParser.parseReader(reader).getAsJsonObject();
    }
  }

  private static NationLayoutPlan nation(JsonObject json) {
    NationBuilder nation = Zinecraft.NATIONS.requireById(json.get("id").getAsString());
    ArrayList<CityLayoutPlan> cities = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("cities")) {
      cities.add(city(nation, element.getAsJsonObject()));
    }
    List<PlanarPoint> boundary = polygon(json.getAsJsonArray("boundary"));
    return new NationLayoutPlan(
        nation,
        point(json.getAsJsonObject("center")),
        boundary,
        cities,
        strings(json.getAsJsonArray("neighboring_nation_ids"))
    );
  }

  private static CityLayoutPlan city(NationBuilder nation, JsonObject json) {
    String id = json.get("id").getAsString();
    TerraCityBuilder city = nation.cities().stream()
        .filter(candidate -> candidate.id().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("泰拉布局引用未知城市：" + nation.id() + "/" + id));
    ArrayList<CityRegionCell> regions = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("regions")) {
      regions.add(region(city, element.getAsJsonObject()));
    }
    return new CityLayoutPlan(
        nation,
        city,
        point(json.getAsJsonObject("center")),
        polygon(json.getAsJsonArray("boundary")),
        point(json.getAsJsonObject("city_core")),
        json.get("usable_chunk_area").getAsInt(),
        terrainProfile(json.getAsJsonObject("terrain_profile")),
        regions,
        roads(json.getAsJsonArray("roads")),
        json.get("plot_coverage").getAsDouble(),
        strings(json.getAsJsonArray("neighboring_city_ids"))
    );
  }

  private static CityTerrainProfile terrainProfile(JsonObject json) {
    return new CityTerrainProfile(
        json.get("ground_y").getAsInt(),
        json.get("foundation_depth").getAsInt(),
        json.get("foundation_blend_depth").getAsInt(),
        json.get("surface_lock_depth").getAsInt(),
        json.get("flat_shoulder").getAsInt(),
        json.get("transition_width").getAsInt(),
        json.get("plane_slope").getAsDouble(),
        json.get("plane_amplitude").getAsDouble()
    );
  }

  private static CityRegionCell region(TerraCityBuilder city, JsonObject json) {
    String id = json.get("id").getAsString();
    TerraCityRegionBuilder region = city.regions().stream()
        .filter(candidate -> candidate.id().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("泰拉布局引用未知城区：" + city.id() + "/" + id));
    String buildingLayout = json.get("building_layout").getAsString();
    if (!region.buildingLayout().id().equals(buildingLayout)) {
      throw new IllegalStateException(
          "泰拉布局 Region 建筑布局与声明不一致：" + region.id() + "/" + buildingLayout
      );
    }
    JsonObject normalizedSlot = json.getAsJsonObject("normalized_slot");
    LayoutSlot slot = new LayoutSlot(
        json.get("slot_index").getAsInt(),
        normalizedSlot.get("x").getAsDouble(),
        normalizedSlot.get("z").getAsDouble()
    );
    JsonObject mobilePlot = json.getAsJsonObject("mobile_plot");
    PlanarPoint rectangleCenter = point(mobilePlot.getAsJsonObject("center"));
    return new CityRegionCell(
        slot,
        region,
        point(json.getAsJsonObject("center")),
        polygon(json.getAsJsonArray("boundary")),
        connections(json.getAsJsonArray("connections")),
        new PlanarRectangle(
            rectangleCenter,
            mobilePlot.get("half_size_x").getAsDouble(),
            mobilePlot.get("half_size_z").getAsDouble(),
            mobilePlot.get("rotation_degrees").getAsDouble()
        ),
        regionLayout(json.getAsJsonObject("region_layout")),
        buildingSlots(region, json.getAsJsonArray("building_slots"))
    );
  }

  private static List<CityRegionBuildingSlot> buildingSlots(
      TerraCityRegionBuilder region,
      JsonArray json
  ) {
    ArrayList<CityRegionBuildingSlot> slots = new ArrayList<>(json.size());
    for (JsonElement element : json) {
      JsonObject slot = element.getAsJsonObject();
      String buildingId = slot.get("building_id").getAsString();
      JigsawBuilder building = region.buildings().stream()
          .map(com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding::building)
          .filter(candidate -> candidate.path.equals(buildingId))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
              "泰拉布局引用 Region 未声明的建筑：" + region.id() + "/" + buildingId
          ));
      JsonObject normalizedSlot = slot.getAsJsonObject("normalized_slot");
      JsonObject chunkArea = slot.getAsJsonObject("chunk_area");
      slots.add(new CityRegionBuildingSlot(
          new LayoutSlot(
              slot.get("slot_index").getAsInt(),
              normalizedSlot.get("x").getAsDouble(),
              normalizedSlot.get("z").getAsDouble()
          ),
          point(slot.getAsJsonObject("center")),
          new com.cxxcxx.zinecraft.api.world.city.ChunkRectangle(
              chunkArea.get("min_chunk_x").getAsInt(),
              chunkArea.get("min_chunk_z").getAsInt(),
              chunkArea.get("width_chunks").getAsInt(),
              chunkArea.get("length_chunks").getAsInt()
          ),
          slot.get("parcel_id").getAsInt(),
          slot.get("adjacent_road_id").getAsInt(),
          net.minecraft.core.Direction.byName(slot.get("facing").getAsString()),
          net.minecraft.world.level.block.Rotation.valueOf(
              slot.get("rotation").getAsString().toUpperCase(java.util.Locale.ROOT)
          ),
          building
      ));
    }
    return List.copyOf(slots);
  }

  private static RegionLayout regionLayout(JsonObject json) {
    RegionLayout.RegionLayoutType layoutType = enumValue(
        RegionLayout.RegionLayoutType.class, json.get("layout_type").getAsString()
    );
    ArrayList<RegionLayout.RegionEntrance> entrances = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("entrances")) {
      JsonObject item = element.getAsJsonObject();
      entrances.add(new RegionLayout.RegionEntrance(
          requireDirection(item.get("side").getAsString()),
          item.get("offset_chunks").getAsInt(),
          item.get("width_chunks").getAsInt(),
          item.get("connected_region_id").getAsInt(),
          chunkPoint(item.getAsJsonObject("point"))
      ));
    }
    ArrayList<RegionLayout.MobileLayerPlan> mobileLayers = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("mobile_layers")) {
      JsonObject item = element.getAsJsonObject();
      mobileLayers.add(new RegionLayout.MobileLayerPlan(
          enumValue(RegionLayout.MobileLayer.class, item.get("layer").getAsString()),
          item.get("building_id").getAsString(),
          chunkRectangle(item.getAsJsonObject("chunk_area"))
      ));
    }
    JsonObject graphJson = json.getAsJsonObject("road_graph");
    ArrayList<RegionLayout.RoadNode> nodes = new ArrayList<>();
    for (JsonElement element : graphJson.getAsJsonArray("nodes")) {
      JsonObject item = element.getAsJsonObject();
      nodes.add(new RegionLayout.RoadNode(
          item.get("id").getAsInt(), chunkPoint(item.getAsJsonObject("point")),
          enumValue(RegionLayout.RoadNodeType.class, item.get("type").getAsString())
      ));
    }
    ArrayList<RegionLayout.RoadEdge> edges = new ArrayList<>();
    for (JsonElement element : graphJson.getAsJsonArray("edges")) {
      JsonObject item = element.getAsJsonObject();
      edges.add(new RegionLayout.RoadEdge(
          item.get("id").getAsInt(), item.get("from_node_id").getAsInt(),
          item.get("to_node_id").getAsInt(),
          enumValue(RegionLayout.RoadClass.class, item.get("road_class").getAsString()),
          item.get("width_chunks").getAsInt(), chunkRectangle(item.getAsJsonObject("chunk_area"))
      ));
    }
    ArrayList<RegionLayout.UrbanBlock> blocks = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("urban_blocks")) {
      JsonObject item = element.getAsJsonObject();
      blocks.add(new RegionLayout.UrbanBlock(
          item.get("id").getAsInt(), item.get("cell_count").getAsInt(),
          chunkRectangle(item.getAsJsonObject("bounds"))
      ));
    }
    ArrayList<RegionLayout.BuildingParcel> parcels = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("parcels")) {
      JsonObject item = element.getAsJsonObject();
      parcels.add(new RegionLayout.BuildingParcel(
          item.get("id").getAsInt(), item.get("urban_block_id").getAsInt(),
          chunkRectangle(item.getAsJsonObject("area")),
          chunkRectangle(item.getAsJsonObject("buildable_area")),
          requireDirection(item.get("road_facing").getAsString()),
          item.get("adjacent_road_id").getAsInt(),
          enumValue(RegionLayout.RoadClass.class, item.get("adjacent_road_class").getAsString())
      ));
    }
    ArrayList<RegionLayout.OpenSpace> openSpaces = new ArrayList<>();
    for (JsonElement element : json.getAsJsonArray("open_spaces")) {
      JsonObject item = element.getAsJsonObject();
      openSpaces.add(new RegionLayout.OpenSpace(
          item.get("id").getAsInt(), chunkRectangle(item.getAsJsonObject("area")),
          enumValue(RegionLayout.OpenSpaceType.class, item.get("type").getAsString())
      ));
    }
    return new RegionLayout(
        layoutType, chunkPoint(json.getAsJsonObject("local_center")), entrances,
        new RegionLayout.RoadGraph(nodes, edges), mobileLayers, blocks, parcels, openSpaces,
        json.get("road_coverage").getAsDouble(), json.get("building_coverage").getAsDouble(),
        strings(json.getAsJsonArray("debug_stages"))
    );
  }

  private static ChunkRectangle chunkRectangle(JsonObject area) {
    return new ChunkRectangle(
        area.get("min_chunk_x").getAsInt(), area.get("min_chunk_z").getAsInt(),
        area.get("width_chunks").getAsInt(), area.get("length_chunks").getAsInt()
    );
  }

  private static RegionLayout.ChunkPoint chunkPoint(JsonObject json) {
    return new RegionLayout.ChunkPoint(json.get("chunk_x").getAsInt(), json.get("chunk_z").getAsInt());
  }

  private static net.minecraft.core.Direction requireDirection(String name) {
    net.minecraft.core.Direction direction = net.minecraft.core.Direction.byName(name);
    if (direction == null || !direction.getAxis().isHorizontal()) {
      throw new IllegalStateException("非法 Region 水平方向：" + name);
    }
    return direction;
  }

  private static <E extends Enum<E>> E enumValue(Class<E> type, String name) {
    return Enum.valueOf(type, name.toUpperCase(java.util.Locale.ROOT));
  }

  private static List<PlanarPoint> polygon(JsonArray json) {
    ArrayList<PlanarPoint> points = new ArrayList<>(json.size());
    for (JsonElement element : json) points.add(point(element.getAsJsonObject()));
    return List.copyOf(points);
  }

  private static PlanarPoint point(JsonObject json) {
    return new PlanarPoint(json.get("x").getAsDouble(), json.get("z").getAsDouble());
  }

  private static List<String> strings(JsonArray json) {
    ArrayList<String> values = new ArrayList<>(json.size());
    for (JsonElement element : json) values.add(element.getAsString());
    return List.copyOf(values);
  }

  private static List<CityRegionConnection> connections(JsonArray json) {
    ArrayList<CityRegionConnection> connections = new ArrayList<>(json.size());
    for (JsonElement element : json) {
      JsonObject connection = element.getAsJsonObject();
      connections.add(new CityRegionConnection(
          connection.get("neighboring_slot_index").getAsInt(),
          point(connection.getAsJsonObject("point"))
      ));
    }
    return List.copyOf(connections);
  }

  private static List<com.cxxcxx.zinecraft.api.world.city.UrbanRoad> roads(JsonArray json) {
    ArrayList<com.cxxcxx.zinecraft.api.world.city.UrbanRoad> roads = new ArrayList<>(json.size());
    for (JsonElement element : json) {
      JsonObject road = element.getAsJsonObject();
      JsonObject area = road.getAsJsonObject("chunk_area");
      roads.add(new com.cxxcxx.zinecraft.api.world.city.UrbanRoad(
          road.get("from_plot_id").getAsInt(),
          road.get("to_plot_id").getAsInt(),
          new com.cxxcxx.zinecraft.api.world.city.ChunkRectangle(
              area.get("min_chunk_x").getAsInt(),
              area.get("min_chunk_z").getAsInt(),
              area.get("width_chunks").getAsInt(),
              area.get("length_chunks").getAsInt()
          )
      ));
    }
    return List.copyOf(roads);
  }

  private static final class Holder {
    private static final TerraLayoutPlan INSTANCE = read();
  }
}
