package com.cxxcxx.zinecraft.core.datagen;

import com.cxxcxx.zinecraft.api.world.city.CityLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.CityRegionConnection;
import com.cxxcxx.zinecraft.api.world.city.CityRegionCell;
import com.cxxcxx.zinecraft.api.world.city.NationLayoutPlan;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import com.cxxcxx.zinecraft.api.world.city.TerraLayoutPlan;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
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
import java.util.zip.GZIPOutputStream;

/**
 * 将泰拉三级地理布局计算一次，并导出为运行时资源和人工验收报告。
 */
public final class TerraLayoutDataExporter {
  private static final Gson GSON = new GsonBuilder()
      .disableHtmlEscaping()
      .create();

  private TerraLayoutDataExporter() {
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      throw new IllegalArgumentException("需要依次指定运行时压缩资源目录和验收报告 JSON 的输出路径");
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
    printSummary(plan);
    writeRuntime(Path.of(args[0]), plan);
    write(Path.of(args[1]), GSON.toJson(serialize(plan)));
  }

  private static void printSummary(TerraLayoutPlan plan) {
    List<CityLayoutPlan> cities = plan.nations().stream()
        .flatMap(nation -> nation.cities().stream())
        .toList();
    int totalPlots = cities.stream().mapToInt(city -> city.regions().size()).sum();
    int minPlots = cities.stream().mapToInt(city -> city.regions().size()).min().orElse(0);
    int maxPlots = cities.stream().mapToInt(city -> city.regions().size()).max().orElse(0);
    double maxCoverage = cities.stream().mapToDouble(CityLayoutPlan::plotCoverage).max().orElse(0.0);
    System.out.printf(
        java.util.Locale.ROOT,
        "Terra layout: cities=%d, plots=%d, plot_range=%d..%d, max_coverage=%.6f%n",
        cities.size(), totalPlots, minPlots, maxPlots, maxCoverage
    );
  }

  private static void write(Path path, String json) throws IOException {
    Path normalized = path.toAbsolutePath().normalize();
    Files.createDirectories(normalized.getParent());
    Files.writeString(normalized, json, StandardCharsets.UTF_8);
  }

  private static void writeRuntime(Path directory, TerraLayoutPlan plan) throws IOException {
    Path normalized = directory.toAbsolutePath().normalize();
    Path nationsDirectory = normalized.resolve("nations");
    Files.createDirectories(nationsDirectory);
    try (var files = Files.list(normalized)) {
      for (Path file : files.filter(path -> path.getFileName().toString().endsWith(".json.gz")).toList()) {
        Files.delete(file);
      }
    }
    try (var files = Files.list(nationsDirectory)) {
      for (Path file : files.filter(path -> path.getFileName().toString().endsWith(".json.gz")).toList()) {
        Files.delete(file);
      }
    }
    JsonObject index = new JsonObject();
    index.addProperty("schema_version", 14);
    index.addProperty("coordinate_unit", "minecraft_block");
    index.addProperty("core_size_x", ModDimension.TERRA_CORE_SIZE_X);
    index.addProperty("core_size_z", ModDimension.TERRA_CORE_SIZE_Z);
    index.add("boundary", polygon(plan.boundary()));
    index.add("building_types", buildingTypes(plan));
    JsonArray nationIds = new JsonArray();
    for (NationLayoutPlan nation : plan.nations()) {
      nationIds.add(nation.nation().id());
      JsonObject nationFile = new JsonObject();
      nationFile.addProperty("schema_version", 14);
      nationFile.add("nation", nation(nation));
      writeGzip(nationsDirectory.resolve(nation.nation().id() + ".json.gz"), GSON.toJson(nationFile));
    }
    index.add("nation_ids", nationIds);
    writeGzip(normalized.resolve("index.json.gz"), GSON.toJson(index));
  }

  private static void writeGzip(Path path, String json) throws IOException {
    Files.createDirectories(path.toAbsolutePath().normalize().getParent());
    try (var output = new GZIPOutputStream(Files.newOutputStream(path))) {
      output.write(json.getBytes(StandardCharsets.UTF_8));
    }
  }

  private static JsonObject serialize(TerraLayoutPlan plan) {
    JsonObject root = new JsonObject();
    root.addProperty("schema_version", 14);
    root.addProperty("coordinate_unit", "minecraft_block");
    root.addProperty("core_size_x", ModDimension.TERRA_CORE_SIZE_X);
    root.addProperty("core_size_z", ModDimension.TERRA_CORE_SIZE_Z);
    root.add("boundary", polygon(plan.boundary()));
    root.add("building_types", buildingTypes(plan));
    JsonArray nations = new JsonArray();
    for (NationLayoutPlan nation : plan.nations()) nations.add(nation(nation));
    root.add("nations", nations);
    return root;
  }

  private static JsonObject nation(NationLayoutPlan plan) {
    JsonObject json = place(plan.nation().id(), plan.nation().zhCn(), plan.center(), plan.boundary());
    json.add("neighboring_nation_ids", strings(plan.neighboringNationIds()));
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
    json.add("neighboring_city_ids", strings(plan.neighboringCityIds()));
    json.addProperty("rotation_degrees", plan.city().rotationDegrees());
    json.add("city_core", point(plan.cityCore()));
    json.addProperty("usable_chunk_area", plan.usableChunkArea());
    json.add("terrain_profile", terrainProfile(plan.terrainProfile()));
    json.addProperty("min_plot_count", plan.city().minPlotCount());
    json.addProperty("max_plot_count", plan.city().maxPlotCount());
    json.addProperty("max_plot_coverage", plan.city().maxPlotCoverage());
    json.addProperty("plot_coverage", plan.plotCoverage());
    json.addProperty("road_width_chunks", plan.city().roadWidthChunks());
    JsonArray regions = new JsonArray();
    for (CityRegionCell region : plan.regions()) regions.add(region(region));
    json.add("regions", regions);
    JsonArray roads = new JsonArray();
    for (com.cxxcxx.zinecraft.api.world.city.UrbanRoad road : plan.roads()) {
      JsonObject roadJson = new JsonObject();
      roadJson.addProperty("from_plot_id", road.fromPlotId());
      roadJson.addProperty("to_plot_id", road.toPlotId());
      roadJson.add("chunk_area", chunkRectangle(road.chunkArea()));
      roadJson.add("block_area", rectangle(road.chunkArea().toBlockRectangle()));
      roads.add(roadJson);
    }
    json.add("roads", roads);
    return json;
  }

  private static JsonObject terrainProfile(
      com.cxxcxx.zinecraft.api.world.city.CityTerrainProfile profile
  ) {
    JsonObject json = new JsonObject();
    json.addProperty("ground_y", profile.groundY());
    json.addProperty("foundation_depth", profile.foundationDepth());
    json.addProperty("foundation_blend_depth", profile.foundationBlendDepth());
    json.addProperty("surface_lock_depth", profile.surfaceLockDepth());
    json.addProperty("flat_shoulder", profile.flatShoulder());
    json.addProperty("transition_width", profile.transitionWidth());
    json.addProperty("plane_slope", profile.planeSlope());
    json.addProperty("plane_amplitude", profile.planeAmplitude());
    return json;
  }

  private static JsonObject region(CityRegionCell cell) {
    JsonObject json = place(cell.region().id(), cell.region().zhCn(), cell.center(), cell.boundary());
    json.addProperty("slot_index", cell.slot().index());
    JsonArray connections = new JsonArray();
    for (CityRegionConnection connection : cell.connections()) {
      JsonObject connectionJson = new JsonObject();
      connectionJson.addProperty("neighboring_slot_index", connection.neighboringSlotIndex());
      connectionJson.add("point", point(connection.point()));
      connections.add(connectionJson);
    }
    json.add("connections", connections);
    json.add("mobile_plot", rectangle(cell.mobilePlotBounds()));
    json.add("region_layout", regionLayout(cell.regionLayout()));
    json.addProperty("building_layout", cell.region().buildingLayout().id());
    JsonArray buildingSlots = new JsonArray();
    for (CityRegionBuildingSlot buildingSlot : cell.buildingSlots()) {
      JsonObject buildingSlotJson = new JsonObject();
      buildingSlotJson.addProperty("slot_index", buildingSlot.slot().index());
      buildingSlotJson.addProperty("building_id", buildingSlot.building().path);
      buildingSlotJson.addProperty("building_name", buildingSlot.building().zhCn);
      buildingSlotJson.addProperty("building_name_en_us", buildingSlot.building().enUs);
      buildingSlotJson.add("center", point(buildingSlot.center()));
      buildingSlotJson.add("chunk_area", chunkRectangle(buildingSlot.chunkArea()));
      buildingSlotJson.addProperty("parcel_id", buildingSlot.parcelId());
      buildingSlotJson.addProperty("adjacent_road_id", buildingSlot.adjacentRoadId());
      buildingSlotJson.addProperty("facing", buildingSlot.facing().getName());
      buildingSlotJson.addProperty("rotation", buildingSlot.rotation().name().toLowerCase(java.util.Locale.ROOT));
      buildingSlotJson.add("normalized_slot", point(new PlanarPoint(
          buildingSlot.slot().x(), buildingSlot.slot().z()
      )));
      buildingSlots.add(buildingSlotJson);
    }
    json.add("building_slots", buildingSlots);
    json.add("normalized_slot", point(new PlanarPoint(cell.slot().x(), cell.slot().z())));
    return json;
  }

  private static JsonArray buildingTypes(TerraLayoutPlan plan) {
    java.util.TreeMap<String, JigsawBuilder> types = new java.util.TreeMap<>();
    plan.nations().stream()
        .flatMap(nation -> nation.cities().stream())
        .flatMap(city -> city.regions().stream())
        .flatMap(region -> region.region().buildings().stream())
        .forEach(candidate -> types.putIfAbsent(candidate.building().path, candidate.building()));
    JsonArray json = new JsonArray();
    for (JigsawBuilder building : types.values()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", building.path);
      item.addProperty("zh_cn_name", building.zhCn);
      item.addProperty("en_us_name", building.enUs);
      item.addProperty("footprint_chunks_x", building.footprintChunksX());
      item.addProperty("footprint_chunks_z", building.footprintChunksZ());
      json.add(item);
    }
    return json;
  }

  private static JsonObject regionLayout(RegionLayout layout) {
    JsonObject json = new JsonObject();
    json.addProperty("layout_type", layout.layoutType().name().toLowerCase(java.util.Locale.ROOT));
    json.add("local_center", chunkPoint(layout.localCenter()));
    JsonArray entrances = new JsonArray();
    for (RegionLayout.RegionEntrance entrance : layout.entrances()) {
      JsonObject item = new JsonObject();
      item.addProperty("side", entrance.side().getName());
      item.addProperty("offset_chunks", entrance.offsetChunks());
      item.addProperty("width_chunks", entrance.widthChunks());
      item.addProperty("connected_region_id", entrance.connectedRegionId());
      item.add("point", chunkPoint(entrance.point()));
      entrances.add(item);
    }
    json.add("entrances", entrances);
    JsonArray mobileLayers = new JsonArray();
    for (RegionLayout.MobileLayerPlan layer : layout.mobileLayers()) {
      JsonObject item = new JsonObject();
      item.addProperty("layer", layer.layer().name().toLowerCase(java.util.Locale.ROOT));
      item.addProperty("building_id", layer.buildingId());
      item.add("chunk_area", chunkRectangle(layer.chunkArea()));
      mobileLayers.add(item);
    }
    json.add("mobile_layers", mobileLayers);
    JsonObject graph = new JsonObject();
    JsonArray nodes = new JsonArray();
    for (RegionLayout.RoadNode node : layout.roadGraph().nodes()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", node.id());
      item.add("point", chunkPoint(node.point()));
      item.addProperty("type", node.type().name().toLowerCase(java.util.Locale.ROOT));
      nodes.add(item);
    }
    graph.add("nodes", nodes);
    JsonArray edges = new JsonArray();
    for (RegionLayout.RoadEdge edge : layout.roadGraph().edges()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", edge.id());
      item.addProperty("from_node_id", edge.fromNodeId());
      item.addProperty("to_node_id", edge.toNodeId());
      item.addProperty("road_class", edge.roadClass().name().toLowerCase(java.util.Locale.ROOT));
      item.addProperty("width_chunks", edge.widthChunks());
      item.add("chunk_area", chunkRectangle(edge.chunkArea()));
      edges.add(item);
    }
    graph.add("edges", edges);
    json.add("road_graph", graph);
    JsonArray blocks = new JsonArray();
    for (RegionLayout.UrbanBlock block : layout.urbanBlocks()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", block.id());
      item.addProperty("cell_count", block.cellCount());
      item.add("bounds", chunkRectangle(block.bounds()));
      blocks.add(item);
    }
    json.add("urban_blocks", blocks);
    JsonArray parcels = new JsonArray();
    for (RegionLayout.BuildingParcel parcel : layout.parcels()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", parcel.id());
      item.addProperty("urban_block_id", parcel.urbanBlockId());
      item.add("area", chunkRectangle(parcel.area()));
      item.add("buildable_area", chunkRectangle(parcel.buildableArea()));
      item.addProperty("road_facing", parcel.roadFacing().getName());
      item.addProperty("adjacent_road_id", parcel.adjacentRoadId());
      item.addProperty("adjacent_road_class", parcel.adjacentRoadClass().name().toLowerCase(java.util.Locale.ROOT));
      parcels.add(item);
    }
    json.add("parcels", parcels);
    JsonArray openSpaces = new JsonArray();
    for (RegionLayout.OpenSpace openSpace : layout.openSpaces()) {
      JsonObject item = new JsonObject();
      item.addProperty("id", openSpace.id());
      item.add("area", chunkRectangle(openSpace.area()));
      item.addProperty("type", openSpace.type().name().toLowerCase(java.util.Locale.ROOT));
      openSpaces.add(item);
    }
    json.add("open_spaces", openSpaces);
    json.addProperty("road_coverage", layout.roadCoverage());
    json.addProperty("building_coverage", layout.buildingCoverage());
    json.add("debug_stages", strings(layout.debugStages()));
    return json;
  }

  private static JsonObject chunkPoint(RegionLayout.ChunkPoint point) {
    JsonObject json = new JsonObject();
    json.addProperty("chunk_x", point.chunkX());
    json.addProperty("chunk_z", point.chunkZ());
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

  private static JsonObject rectangle(PlanarRectangle rectangle) {
    JsonObject json = new JsonObject();
    json.add("center", point(rectangle.center()));
    json.addProperty("half_size_x", rectangle.halfSizeX());
    json.addProperty("half_size_z", rectangle.halfSizeZ());
    json.addProperty("rotation_degrees", rectangle.rotationDegrees());
    json.addProperty("area", rectangle.area());
    json.add("corners", polygon(rectangle.corners()));
    return json;
  }

  private static JsonObject chunkRectangle(
      com.cxxcxx.zinecraft.api.world.city.ChunkRectangle rectangle
  ) {
    JsonObject json = new JsonObject();
    json.addProperty("min_chunk_x", rectangle.minChunkX());
    json.addProperty("min_chunk_z", rectangle.minChunkZ());
    json.addProperty("width_chunks", rectangle.widthChunks());
    json.addProperty("length_chunks", rectangle.lengthChunks());
    return json;
  }

  private static JsonArray strings(List<String> values) {
    JsonArray json = new JsonArray();
    for (String value : values) json.add(value);
    return json;
  }
}
