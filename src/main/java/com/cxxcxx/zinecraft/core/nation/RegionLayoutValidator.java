package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.world.city.ChunkRectangle;
import com.cxxcxx.zinecraft.api.world.city.CityRegionBuildingSlot;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.ChunkPoint;
import com.cxxcxx.zinecraft.api.world.city.RegionLayout.RoadEdge;
import net.minecraft.core.Direction;

import java.util.*;

/** Region 内部道路、Parcel 与建筑放置的硬约束校验器。 */
public final class RegionLayoutValidator {
  private RegionLayoutValidator() {
  }

  public static void validate(ChunkRectangle region, RegionLayout layout) {
    Map<RegionLayout.MobileLayer, String> expectedLayers = Map.of(
        RegionLayout.MobileLayer.POWER, "mobile_plot_power_layer",
        RegionLayout.MobileLayer.SUPPORT, "mobile_plot_support_layer",
        RegionLayout.MobileLayer.LIFE, "mobile_plot_life_layer"
    );
    for (RegionLayout.MobileLayerPlan layer : layout.mobileLayers()) {
      if (!region.equals(layer.chunkArea()) || !expectedLayers.get(layer.layer()).equals(layer.buildingId())) {
        throw new IllegalArgumentException("移动地块分层规划必须以唯一建筑覆盖整个 Region：" + layer.layer());
      }
    }
    HashSet<Long> roadCells = roadCells(region, layout);
    if (roadCells.isEmpty()) throw new IllegalArgumentException("Region 内部道路不能为空");
    for (RegionLayout.RegionEntrance entrance : layout.entrances()) {
      if (!roadCells.contains(key(entrance.point()))) {
        throw new IllegalArgumentException("Region 入口未接入内部道路：" + entrance.connectedRegionId());
      }
    }
    ensureRoadsConnected(roadCells);
    for (RegionLayout.BuildingParcel parcel : layout.parcels()) {
      requireInside(region, parcel.area(), "BuildingParcel");
      requireInside(parcel.area(), parcel.buildableArea(), "buildableArea");
      for (int z = parcel.area().minChunkZ(); z < parcel.area().maxChunkZExclusive(); z++) {
        for (int x = parcel.area().minChunkX(); x < parcel.area().maxChunkXExclusive(); x++) {
          if (roadCells.contains(key(x, z))) throw new IllegalArgumentException("BuildingParcel 与道路重叠");
        }
      }
    }
    HashSet<Long> assigned = new HashSet<>(roadCells);
    for (RegionLayout.BuildingParcel parcel : layout.parcels()) {
      for (int z = parcel.area().minChunkZ(); z < parcel.area().maxChunkZExclusive(); z++) {
        for (int x = parcel.area().minChunkX(); x < parcel.area().maxChunkXExclusive(); x++) {
          if (!assigned.add(key(x, z))) throw new IllegalArgumentException("Region Chunk 被重复分配");
        }
      }
    }
    if (assigned.size() != region.areaChunks()) {
      throw new IllegalArgumentException("Region 中每个 Chunk 必须是道路或建筑 Parcel：已分配="
          + assigned.size() + "，总面积=" + region.areaChunks());
    }
  }

  public static void validateBuildings(
      ChunkRectangle region,
      RegionLayout layout,
      List<CityRegionBuildingSlot> buildings
  ) {
    HashSet<Long> occupied = new HashSet<>();
    for (CityRegionBuildingSlot building : buildings) {
      requireInside(region, building.chunkArea(), "建筑");
      for (int z = building.chunkArea().minChunkZ(); z < building.chunkArea().maxChunkZExclusive(); z++) {
        for (int x = building.chunkArea().minChunkX(); x < building.chunkArea().maxChunkXExclusive(); x++) {
          long key = key(x, z);
          if (layout.isRoad(x, z)) throw new IllegalArgumentException("建筑不得覆盖 Region 道路");
          if (!occupied.add(key)) throw new IllegalArgumentException("Region 建筑占地重叠");
        }
      }
      RegionLayout.BuildingParcel parcel = layout.parcels().stream()
          .filter(candidate -> candidate.id() == building.parcelId())
          .findFirst().orElseThrow(() -> new IllegalArgumentException("建筑引用未知 Parcel"));
      if (parcel.adjacentRoadId() != building.adjacentRoadId()
          || parcel.roadFacing() != building.facing()) {
        throw new IllegalArgumentException("建筑朝向或道路引用与 Parcel 不一致");
      }
    }
    if (buildings.size() != layout.parcels().size()) {
      throw new IllegalArgumentException("每个建筑 Parcel 必须恰好对应一个建筑");
    }
    int expectedBuildingChunks = region.areaChunks() - roadCells(region, layout).size();
    if (occupied.size() != expectedBuildingChunks) {
      throw new IllegalArgumentException("所有非道路 Chunk 必须被建筑覆盖：建筑="
          + occupied.size() + "，预期=" + expectedBuildingChunks);
    }
  }

  private static HashSet<Long> roadCells(ChunkRectangle region, RegionLayout layout) {
    HashSet<Long> cells = new HashSet<>();
    for (RoadEdge edge : layout.roadGraph().edges()) {
      requireInside(region, edge.chunkArea(), "道路");
      for (int z = edge.chunkArea().minChunkZ(); z < edge.chunkArea().maxChunkZExclusive(); z++) {
        for (int x = edge.chunkArea().minChunkX(); x < edge.chunkArea().maxChunkXExclusive(); x++) {
          cells.add(key(x, z));
        }
      }
    }
    return cells;
  }

  private static void ensureRoadsConnected(Set<Long> roads) {
    HashSet<Long> visited = new HashSet<>();
    ArrayDeque<Long> queue = new ArrayDeque<>();
    long start = roads.iterator().next();
    visited.add(start);
    queue.add(start);
    while (!queue.isEmpty()) {
      long current = queue.removeFirst();
      int x = (int) (current >> 32);
      int z = (int) current;
      for (Direction direction : Direction.Plane.HORIZONTAL) {
        long next = key(x + direction.getStepX(), z + direction.getStepZ());
        if (roads.contains(next) && visited.add(next)) queue.addLast(next);
      }
    }
    if (visited.size() != roads.size()) throw new IllegalArgumentException("Region RoadGraph 未整体连通");
  }

  private static void requireInside(ChunkRectangle outer, ChunkRectangle inner, String label) {
    if (inner.minChunkX() < outer.minChunkX() || inner.minChunkZ() < outer.minChunkZ()
        || inner.maxChunkXExclusive() > outer.maxChunkXExclusive()
        || inner.maxChunkZExclusive() > outer.maxChunkZExclusive()) {
      throw new IllegalArgumentException(label + " 超出 Region 范围");
    }
  }

  private static long key(ChunkPoint point) {
    return key(point.chunkX(), point.chunkZ());
  }

  private static long key(int x, int z) {
    return ((long) x << 32) ^ (z & 0xffffffffL);
  }
}
