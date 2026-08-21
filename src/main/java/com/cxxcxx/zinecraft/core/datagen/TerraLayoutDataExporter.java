package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.core.nation.TerraLayoutCalculator;
import com.cxxcxx.zinecraft.core.registry.ModDimension;
import com.cxxcxx.zinecraft.core.registry.ModNation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.neoforged.fml.loading.LoadingModList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 将泰拉三级地理布局计算一次，并导出为运行时资源和人工验收报告。
 */
public final class TerraLayoutDataExporter {
  private static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .disableHtmlEscaping()
      .create();

  private TerraLayoutDataExporter() {
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      throw new IllegalArgumentException("需要依次指定运行时 JSON 和验收报告 JSON 的输出路径");
    }
    if (LoadingModList.get() == null) {
      LoadingModList.of(List.of(), List.of(), List.of(), List.of(), Map.of());
    }
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
    TerraLayoutPlan plan = new TerraLayoutCalculator().calculate(
        ModNation.ALL,
        ModDimension.TERRA_CORE_HALF_SIZE_X,
        ModDimension.TERRA_CORE_HALF_SIZE_Z
    );
    String json = GSON.toJson(serialize(plan));
    write(Path.of(args[0]), json);
    write(Path.of(args[1]), json);
  }

  private static void write(Path path, String json) throws IOException {
    Path normalized = path.toAbsolutePath().normalize();
    Files.createDirectories(normalized.getParent());
    Files.writeString(normalized, json, StandardCharsets.UTF_8);
  }

  private static JsonObject serialize(TerraLayoutPlan plan) {
    JsonObject root = new JsonObject();
    root.addProperty("schema_version", 5);
    root.addProperty("coordinate_unit", "minecraft_block");
    root.addProperty("core_size_x", ModDimension.TERRA_CORE_SIZE_X);
    root.addProperty("core_size_z", ModDimension.TERRA_CORE_SIZE_Z);
    root.add("boundary", polygon(plan.boundary()));
    JsonArray nations = new JsonArray();
    for (NationLayoutPlan nation : plan.nations()) nations.add(nation(nation));
    root.add("nations", nations);
    return root;
  }

  private static JsonObject nation(NationLayoutPlan plan) {
    JsonObject json = place(plan.nation().id(), plan.nation().zhCn(), plan.center(), plan.boundary());
    json.addProperty("underground", plan.nation().isUnderground());
    json.addProperty("size", plan.nation().size());
    JsonArray points = new JsonArray();
    for (PlanarPoint point : plan.nation().relativePoints()) points.add(point(point));
    json.add("normalized_polyline", points);
    json.add("polyline", polygon(plan.nation().relativePoints().stream()
        .map(point -> new PlanarPoint(
            point.x() * ModDimension.TERRA_CORE_HALF_SIZE_X,
            point.z() * ModDimension.TERRA_CORE_HALF_SIZE_Z
        ))
        .toList()));
    JsonArray cities = new JsonArray();
    for (CityLayoutPlan city : plan.cities()) cities.add(city(city));
    json.add("cities", cities);
    return json;
  }

  private static JsonObject city(CityLayoutPlan plan) {
    JsonObject json = place(plan.city().id(), plan.city().zhCn(), plan.center(), plan.boundary());
    json.addProperty("rotation_degrees", plan.city().rotationDegrees());
    JsonArray regions = new JsonArray();
    for (CityRegionCell region : plan.regions()) regions.add(region(region));
    json.add("regions", regions);
    return json;
  }

  private static JsonObject region(CityRegionCell cell) {
    JsonObject json = place(cell.region().id(), cell.region().zhCn(), cell.center(), cell.boundary());
    json.addProperty("slot_index", cell.slot().index());
    json.add("normalized_slot", point(new PlanarPoint(cell.slot().x(), cell.slot().z())));
    return json;
  }

  private static JsonObject place(
      String id,
      String zhCn,
      PlanarPoint center,
      List<PlanarPoint> boundary
  ) {
    JsonObject json = new JsonObject();
    json.addProperty("id", id);
    json.addProperty("zh_cn_name", zhCn);
    json.add("center", point(center));
    json.add("boundary", polygon(boundary));
    return json;
  }

  private static JsonArray polygon(List<PlanarPoint> boundary) {
    JsonArray points = new JsonArray();
    for (PlanarPoint point : boundary) points.add(point(point));
    return points;
  }

  private static JsonObject point(PlanarPoint point) {
    JsonObject json = new JsonObject();
    json.addProperty("x", point.x());
    json.addProperty("z", point.z());
    return json;
  }
}
