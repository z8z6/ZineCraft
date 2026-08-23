package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;

import java.util.List;
import java.util.Objects;

/**
 * 一个 Chunk 对齐移动 Region 地块及其世界坐标。
 */
public record CityRegionCell(
    LayoutSlot slot,
    TerraCityRegionBuilder region,
    PlanarPoint center,
    List<PlanarPoint> boundary,
    List<CityRegionConnection> connections,
    PlanarRectangle mobilePlotBounds,
    RegionLayout regionLayout,
    List<CityRegionBuildingSlot> buildingSlots
) {
  public CityRegionCell {
    Objects.requireNonNull(slot, "Region slot 不能为空");
    Objects.requireNonNull(region, "Region 不能为空");
    Objects.requireNonNull(center, "Region 世界中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "Region 边界不能为空"));
    connections = List.copyOf(Objects.requireNonNull(connections, "Region 连通点清单不能为空"));
    Objects.requireNonNull(mobilePlotBounds, "移动地块范围不能为空");
    Objects.requireNonNull(regionLayout, "Region 内部布局不能为空");
    buildingSlots = List.copyOf(Objects.requireNonNull(buildingSlots, "建筑 slot 清单不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("Region 边界至少需要三个点");
    if (mobilePlotBounds.rotationDegrees() != 0.0) {
      throw new IllegalArgumentException("移动地块必须与世界 X/Z 轴平行：" + slot.index());
    }
    double minX = mobilePlotBounds.center().x() - mobilePlotBounds.halfSizeX();
    double maxX = mobilePlotBounds.center().x() + mobilePlotBounds.halfSizeX();
    double minZ = mobilePlotBounds.center().z() - mobilePlotBounds.halfSizeZ();
    double maxZ = mobilePlotBounds.center().z() + mobilePlotBounds.halfSizeZ();
    if (!chunkAligned(minX) || !chunkAligned(maxX)
        || !chunkAligned(minZ) || !chunkAligned(maxZ)
        || !chunkAligned(maxX - minX) || !chunkAligned(maxZ - minZ)) {
      throw new IllegalArgumentException("移动地块边界和长宽必须对齐 16 方块区块：" + slot.index());
    }
    if (connections.stream().anyMatch(connection -> connection.neighboringSlotIndex() == slot.index())) {
      throw new IllegalArgumentException("Region 不能与自身槽位相邻：" + slot.index());
    }
    if (connections.stream().map(CityRegionConnection::neighboringSlotIndex).distinct().count()
        != connections.size()) {
      throw new IllegalArgumentException("Region 相邻槽位索引不能重复：" + slot.index());
    }
    if (buildingSlots.size() != regionLayout.parcels().size()) {
      throw new IllegalArgumentException("每个建筑 Parcel 必须恰好生成一个建筑：" + slot.index());
    }
    if (buildingSlots.stream().map(buildingSlot -> buildingSlot.slot().index()).distinct().count()
        != buildingSlots.size()) {
      throw new IllegalArgumentException("Region 建筑 slot 索引不能重复：" + slot.index());
    }
    java.util.Set<com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder> allowedBuildings =
        region.buildings().stream()
            .map(com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding::building)
            .collect(java.util.stream.Collectors.toUnmodifiableSet());
    for (CityRegionBuildingSlot buildingSlot : buildingSlots) {
      if (!allowedBuildings.contains(buildingSlot.building())) {
        throw new IllegalArgumentException("建筑 slot 引用了 Region 未声明的建筑：" + buildingSlot.building().path);
      }
      RegionLayout.BuildingParcel parcel = regionLayout.parcels().stream()
          .filter(candidate -> candidate.id() == buildingSlot.parcelId())
          .findFirst().orElseThrow(() -> new IllegalArgumentException(
              "建筑 slot 引用了未知 Parcel：" + buildingSlot.parcelId()
          ));
      if (parcel.adjacentRoadId() != buildingSlot.adjacentRoadId()
          || parcel.roadFacing() != buildingSlot.facing()) {
        throw new IllegalArgumentException("建筑 slot 与 Parcel 临路信息不一致：" + slot.index());
      }
      if (!mobilePlotBounds.contains(buildingSlot.center())) {
        throw new IllegalArgumentException("建筑 slot 超出移动地块范围：" + slot.index()
            + "/" + buildingSlot.slot().index());
      }
      PlanarRectangle buildingBounds = buildingSlot.chunkArea().toBlockRectangle();
      for (PlanarPoint corner : buildingBounds.corners()) {
        if (!mobilePlotBounds.contains(corner)) {
          throw new IllegalArgumentException("建筑占地超出移动地块范围：" + slot.index()
              + "/" + buildingSlot.slot().index());
        }
      }
      if (Math.abs(buildingSlot.slot().x()) >= 1.0 || Math.abs(buildingSlot.slot().z()) >= 1.0) {
        throw new IllegalArgumentException("建筑 slot 必须严格位于移动地块内部：" + slot.index()
            + "/" + buildingSlot.slot().index());
      }
    }
    for (int first = 0; first < buildingSlots.size(); first++) {
      for (int second = first + 1; second < buildingSlots.size(); second++) {
        if (buildingSlots.get(first).chunkArea().intersects(buildingSlots.get(second).chunkArea())) {
          throw new IllegalArgumentException("Region 建筑占地不能重叠：" + slot.index()
              + "/" + buildingSlots.get(first).slot().index()
              + "/" + buildingSlots.get(second).slot().index());
        }
      }
    }
  }

  public List<Integer> neighboringSlotIndexes() {
    return connections.stream().map(CityRegionConnection::neighboringSlotIndex).toList();
  }

  private static boolean chunkAligned(double value) {
    return Math.abs(value / 16.0 - Math.rint(value / 16.0)) <= 1.0E-9;
  }
}
