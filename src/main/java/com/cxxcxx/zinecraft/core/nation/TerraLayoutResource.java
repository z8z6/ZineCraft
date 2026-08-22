package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionConnection;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
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

/**
 * 读取数据生成阶段计算好的泰拉 Nation、City、Region 边界。
 */
public final class TerraLayoutResource {
  public static final String PATH = "/data/" + Zinecraft.MOD_ID + "/terra_layout.json";

  private TerraLayoutResource() {
  }

  public static TerraLayoutPlan load() {
    return Holder.INSTANCE;
  }

  /**
   * 查找完整覆盖指定区块的移动地块所属 Region。移动地块边界按右侧/下侧开区间处理，
   * 因而相邻矩形即使共享边界也不会同时认领同一区块。
   */
  public static Optional<CityRegionCell> mobilePlotRegion(int chunkX, int chunkZ) {
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
            return Optional.of(region);
          }
        }
      }
    }
    return Optional.empty();
  }

  private static TerraLayoutPlan read() {
    try (var stream = TerraLayoutResource.class.getResourceAsStream(PATH)) {
      if (stream == null) throw new IllegalStateException("缺少泰拉布局资源：" + PATH);
      try (var reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
        return parse(JsonParser.parseReader(reader).getAsJsonObject());
      }
    } catch (IOException | RuntimeException exception) {
      throw new IllegalStateException("无法读取泰拉布局资源：" + PATH, exception);
    }
  }

  private static TerraLayoutPlan parse(JsonObject root) {
    if (root.get("schema_version").getAsInt() != 9) {
      throw new IllegalStateException("不支持的泰拉布局 schema_version");
    }
    ArrayList<NationLayoutPlan> nations = new ArrayList<>();
    for (JsonElement element : root.getAsJsonArray("nations")) {
      nations.add(nation(element.getAsJsonObject()));
    }
    return new TerraLayoutPlan(polygon(root.getAsJsonArray("boundary")), nations);
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
        regions,
        strings(json.getAsJsonArray("neighboring_city_ids"))
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
      slots.add(new CityRegionBuildingSlot(
          new LayoutSlot(
              slot.get("slot_index").getAsInt(),
              normalizedSlot.get("x").getAsDouble(),
              normalizedSlot.get("z").getAsDouble()
          ),
          point(slot.getAsJsonObject("center")),
          building
      ));
    }
    return List.copyOf(slots);
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

  private static final class Holder {
    private static final TerraLayoutPlan INSTANCE = read();
  }
}
