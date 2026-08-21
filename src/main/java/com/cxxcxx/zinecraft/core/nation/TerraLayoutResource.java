package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
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
    if (root.get("schema_version").getAsInt() != 5) {
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
    return new NationLayoutPlan(nation, point(json.getAsJsonObject("center")), boundary, cities);
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
        regions
    );
  }

  private static CityRegionCell region(TerraCityBuilder city, JsonObject json) {
    String id = json.get("id").getAsString();
    TerraCityRegionBuilder region = city.regions().stream()
        .filter(candidate -> candidate.id().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("泰拉布局引用未知城区：" + city.id() + "/" + id));
    JsonObject normalizedSlot = json.getAsJsonObject("normalized_slot");
    LayoutSlot slot = new LayoutSlot(
        json.get("slot_index").getAsInt(),
        normalizedSlot.get("x").getAsDouble(),
        normalizedSlot.get("z").getAsDouble()
    );
    return new CityRegionCell(
        slot,
        region,
        point(json.getAsJsonObject("center")),
        polygon(json.getAsJsonArray("boundary"))
    );
  }

  private static List<PlanarPoint> polygon(JsonArray json) {
    ArrayList<PlanarPoint> points = new ArrayList<>(json.size());
    for (JsonElement element : json) points.add(point(element.getAsJsonObject()));
    return List.copyOf(points);
  }

  private static PlanarPoint point(JsonObject json) {
    return new PlanarPoint(json.get("x").getAsDouble(), json.get("z").getAsDouble());
  }

  private static final class Holder {
    private static final TerraLayoutPlan INSTANCE = read();
  }
}
