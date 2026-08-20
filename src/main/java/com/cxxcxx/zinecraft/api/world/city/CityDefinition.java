package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.nation.TerraPlace;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 一座城市的显式规划输入。
 *
 * <p>共享规划器只消费这里已经审查过的数据，不根据国家枚举自动发明建筑、城区或道路风格。</p>
 */
public record CityDefinition(
    ResourceLocation id,
    TerraPlace place,
    CityRect planningBounds,
    int planningCellSize,
    long salt,
    TerrainMode terrainMode,
    CityLayout layout,
    List<LandmarkDefinition> landmarks,
    List<DistrictDefinition> districts,
    List<RoadConnection> mainRoadConnections,
    List<CityBuildingDefinition> buildings
) {
  public CityDefinition {
    Objects.requireNonNull(id, "城市定义 ID 不能为空");
    Objects.requireNonNull(place, "城市地图地点不能为空");
    Objects.requireNonNull(planningBounds, "城市规划范围不能为空");
    Objects.requireNonNull(terrainMode, "城市地形模式不能为空");
    Objects.requireNonNull(layout, "城市内部布局不能为空");
    landmarks = List.copyOf(Objects.requireNonNull(landmarks, "城市地标不能为空"));
    districts = List.copyOf(Objects.requireNonNull(districts, "城市城区不能为空"));
    mainRoadConnections = List.copyOf(Objects.requireNonNull(mainRoadConnections, "城市主路连接不能为空"));
    buildings = List.copyOf(Objects.requireNonNull(buildings, "城市建筑目录不能为空"));
    if (!place.type().isUrban()) throw new IllegalArgumentException("城市定义必须绑定城市、聚落或城区地点：" + id);
    if (planningCellSize != 4 && planningCellSize != 8 && planningCellSize != 16) {
      throw new IllegalArgumentException("城市规划网格只能是 4、8 或 16 格：" + id);
    }
    if (planningBounds.minX() < -place.radiusX() || planningBounds.maxXExclusive() > place.radiusX()
        || planningBounds.minZ() < -place.radiusZ() || planningBounds.maxZExclusive() > place.radiusZ()) {
      throw new IllegalArgumentException("城市规划范围不能超过旅行地图地点边界：" + id);
    }
    requireUniqueIds(id, landmarks.stream().map(entry -> entry.id().toString()).toList(), "地标");
    requireUniqueIds(id, districts.stream().map(DistrictDefinition::id).toList(), "城区");
    requireUniqueIds(id, buildings.stream().map(entry -> entry.id().toString()).toList(), "建筑");
    Set<String> ports = new HashSet<>();
    for (LandmarkDefinition landmark : landmarks) {
      if (!planningBounds.contains(landmark.reservedArea())) {
        throw new IllegalArgumentException("地标保留区越出城市：" + landmark.id());
      }
      for (RoadPort port : landmark.roadPorts()) {
        if (!planningBounds.contains(port.x(), port.z())) {
          throw new IllegalArgumentException("地标道路端口越出城市：" + id + "/" + port.id());
        }
        if (!ports.add(port.id())) throw new IllegalArgumentException("城市道路端口重复：" + id + "/" + port.id());
      }
    }
    requireNonOverlappingLandmarks(id, landmarks);
    for (DistrictDefinition district : districts) {
      if (!planningBounds.contains(district.bounds())) {
        throw new IllegalArgumentException("城区越出城市：" + id + "/" + district.id());
      }
    }
    for (RoadConnection connection : mainRoadConnections) {
      if (!ports.contains(connection.fromPort()) || !ports.contains(connection.toPort())) {
        throw new IllegalArgumentException("主路连接引用未知端口：" + id + "/" + connection);
      }
    }
    requireUniqueIds(id, mainRoadConnections.stream().map(CityDefinition::connectionKey).toList(), "主路连接");
  }

  private static void requireUniqueIds(ResourceLocation city, List<String> ids, String category) {
    if (new HashSet<>(ids).size() != ids.size())
      throw new IllegalArgumentException(city + " 存在重复" + category + " ID");
  }

  private static void requireNonOverlappingLandmarks(ResourceLocation city, List<LandmarkDefinition> landmarks) {
    for (int first = 0; first < landmarks.size(); first++) {
      for (int second = first + 1; second < landmarks.size(); second++) {
        if (landmarks.get(first).reservedArea().intersects(landmarks.get(second).reservedArea())) {
          throw new IllegalArgumentException("城市地标保留区重叠：" + city + "/"
              + landmarks.get(first).id() + " 与 " + landmarks.get(second).id());
        }
      }
    }
  }

  private static String connectionKey(RoadConnection connection) {
    return connection.fromPort().compareTo(connection.toPort()) < 0
        ? connection.fromPort() + "->" + connection.toPort()
        : connection.toPort() + "->" + connection.fromPort();
  }

  public enum TerrainMode {
    MOBILE_PLATFORM,
    TERRAIN_FOLLOWING,
    SUBTERRANEAN,
    UNDERWATER
  }

  public record LandmarkDefinition(
      ResourceLocation id,
      CityRect reservedArea,
      Direction facing,
      List<RoadPort> roadPorts
  ) {
    public LandmarkDefinition {
      Objects.requireNonNull(id, "地标 ID 不能为空");
      Objects.requireNonNull(reservedArea, "地标保留区不能为空");
      Objects.requireNonNull(facing, "地标朝向不能为空");
      roadPorts = List.copyOf(Objects.requireNonNull(roadPorts, "地标道路端口不能为空"));
      if (!facing.getAxis().isHorizontal()) throw new IllegalArgumentException("地标朝向必须水平：" + id);
      if (roadPorts.isEmpty()) throw new IllegalArgumentException("地标至少需要一个道路端口：" + id);
    }
  }

  public record RoadPort(String id, int x, int z, Direction direction, CityRoadClass roadClass) {
    public RoadPort {
      id = Objects.requireNonNull(id, "道路端口 ID 不能为空").strip();
      Objects.requireNonNull(direction, "道路端口方向不能为空");
      Objects.requireNonNull(roadClass, "道路端口等级不能为空");
      if (id.isEmpty()) throw new IllegalArgumentException("道路端口 ID 不能为空");
      if (!direction.getAxis().isHorizontal()) throw new IllegalArgumentException("道路端口必须水平朝向：" + id);
    }
  }

  public record RoadConnection(String fromPort, String toPort, CityRoadClass roadClass) {
    public RoadConnection {
      fromPort = Objects.requireNonNull(fromPort, "主路起点端口不能为空").strip();
      toPort = Objects.requireNonNull(toPort, "主路终点端口不能为空").strip();
      Objects.requireNonNull(roadClass, "主路等级不能为空");
      if (fromPort.isEmpty() || toPort.isEmpty()) throw new IllegalArgumentException("主路端口 ID 不能为空");
      if (fromPort.equals(toPort)) throw new IllegalArgumentException("主路不能连接同一端口：" + fromPort);
    }
  }

  public record DistrictDefinition(
      String id,
      CityDistrictType type,
      CityRect bounds,
      int targetDensity,
      int maxBuildingHeight
  ) {
    public DistrictDefinition {
      id = Objects.requireNonNull(id, "城区 ID 不能为空").strip();
      Objects.requireNonNull(type, "城区类型不能为空");
      Objects.requireNonNull(bounds, "城区范围不能为空");
      if (id.isEmpty()) throw new IllegalArgumentException("城区 ID 不能为空");
      if (targetDensity < 0 || targetDensity > 100) throw new IllegalArgumentException("城区密度必须在 0—100：" + id);
      if (maxBuildingHeight <= 0) throw new IllegalArgumentException("城区限高必须为正数：" + id);
    }
  }
}
