package com.cxxcxx.zinecraft.core.nation;

import com.cxxcxx.zinecraft.api.nation.TerraCityRegionBuilding;
import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityBuilder;
import com.cxxcxx.zinecraft.api.registry.builder.TerraCityRegionBuilder;
import com.cxxcxx.zinecraft.api.world.city.*;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import com.cxxcxx.zinecraft.api.world.layout.PlanarRectangle;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

/** 在 City 边界内生成 Chunk 对齐、道路连通的移动 Region 地块。 */
public final class CityLayoutCalculator {
  private final MobileCityLayoutGenerator layoutGenerator;
  private final RegionLayoutGenerator regionLayoutGenerator;

  public CityLayoutCalculator() {
    this.layoutGenerator = new MobileCityLayoutGenerator();
    this.regionLayoutGenerator = new RegionLayoutGenerator();
  }

  public CityLayoutPlan calculate(
      NationBuilder nation,
      TerraCityBuilder city,
      PlanarPoint cityCenter,
      List<PlanarPoint> cityBoundary,
      RandomGenerator random
  ) {
    Objects.requireNonNull(nation, "城市所属国家不能为空");
    Objects.requireNonNull(city, "城市不能为空");
    Objects.requireNonNull(cityCenter, "城市中心不能为空");
    List<PlanarPoint> boundary = List.copyOf(Objects.requireNonNull(cityBoundary, "城市边界不能为空"));
    Objects.requireNonNull(random, "城市布局随机源不能为空");
    if (!nation.cities().contains(city)) {
      throw new IllegalArgumentException("国家没有声明该城市：" + nation.id() + "/" + city.id());
    }
    for (TerraCityRegionBuilder region : city.regions()) {
      if (region.nation() != nation) {
        throw new IllegalArgumentException("城市引用了其他国家的 Region：" + region.id());
      }
    }

    LayoutGenerationResult result = layoutGenerator.generate(city, boundary, random);
    if (!result.success()) {
      throw new IllegalArgumentException(
          "城市移动地块生成失败 [" + result.failureReason() + "]：" + result.message()
      );
    }
    MobileCityLayout mobileLayout = result.layout();
    ArrayList<ArrayList<CityRegionConnection>> connections = new ArrayList<>(mobileLayout.plots().size());
    for (int index = 0; index < mobileLayout.plots().size(); index++) connections.add(new ArrayList<>());
    for (UrbanRoad road : mobileLayout.roads()) {
      PlanarPoint point = road.chunkArea().centerBlocks();
      connections.get(road.fromPlotId()).add(new CityRegionConnection(road.toPlotId(), point));
      connections.get(road.toPlotId()).add(new CityRegionConnection(road.fromPlotId(), point));
    }

    double halfWidth = boundary.stream().mapToDouble(point -> Math.abs(point.x() - cityCenter.x()))
        .max().orElse(1.0);
    double halfLength = boundary.stream().mapToDouble(point -> Math.abs(point.z() - cityCenter.z()))
        .max().orElse(1.0);
    long regionSeedBase = random.nextLong();
    List<CityRegionCell> regions = mobileLayout.plots().stream().map(plot -> {
      PlanarRectangle bounds = plot.chunkArea().toBlockRectangle();
      PlanarPoint center = bounds.center();
      LayoutSlot slot = new LayoutSlot(
          plot.id(),
          (center.x() - cityCenter.x()) / Math.max(1.0, halfWidth),
          (center.z() - cityCenter.z()) / Math.max(1.0, halfLength)
      );
      long regionSeed = mixSeed(regionSeedBase, city.id(), plot.type().id(), plot.id());
      Random regionRandom = new Random(regionSeed);
      RegionLayout regionLayout = regionLayoutGenerator.generate(
          plot.chunkArea(), plot.type(), connections.get(plot.id()), regionSeed
      );
      List<CityRegionBuildingSlot> buildingSlots = buildingSlots(
          plot.type(), bounds, regionLayout, regionRandom
      );
      int buildingArea = buildingSlots.stream().mapToInt(candidate -> candidate.chunkArea().areaChunks()).sum();
      regionLayout = regionLayout.withBuildingCoverage(buildingArea / (double) plot.chunkArea().areaChunks());
      RegionLayoutValidator.validateBuildings(plot.chunkArea(), regionLayout, buildingSlots);
      return new CityRegionCell(
          slot,
          plot.type(),
          center,
          bounds.corners(),
          connections.get(plot.id()),
          bounds,
          regionLayout,
          buildingSlots
      );
    }).toList();
    return new CityLayoutPlan(
        nation,
        city,
        cityCenter,
        boundary,
        mobileLayout.cityCore(),
        mobileLayout.usableChunkArea(),
        nation.isUnderground() ? CityTerrainProfile.UNDERGROUND : CityTerrainProfile.SURFACE,
        regions,
        mobileLayout.roads(),
        mobileLayout.coverage(),
        List.of()
    );
  }

  private List<CityRegionBuildingSlot> buildingSlots(
      TerraCityRegionBuilder region,
      PlanarRectangle bounds,
      RegionLayout regionLayout,
      RandomGenerator random
  ) {
    ArrayList<CityRegionBuildingSlot> placed = new ArrayList<>(regionLayout.parcels().size());
    java.util.HashSet<JigsawBuilder> usedUniqueBuildings = new java.util.HashSet<>();
    for (RegionLayout.BuildingParcel parcel : regionLayout.parcels()) {
      Rotation rotation = CityRegionBuildingSlot.rotationForFacing(parcel.roadFacing());
      List<TerraCityRegionBuilding> compatible = region.buildings().stream()
          .filter(candidate -> !candidate.unique() || !usedUniqueBuildings.contains(candidate.building()))
          .filter(candidate -> fitsExactly(candidate.building(), parcel.area(), rotation))
          .toList();
      if (compatible.isEmpty()) {
        throw new IllegalArgumentException("Region 没有与 Parcel 尺寸匹配的建筑："
            + region.id() + "/" + parcel.area());
      }
      TerraCityRegionBuilding selected = select(compatible, random);
      JigsawBuilder building = selected.building();
      if (selected.unique()) usedUniqueBuildings.add(building);
      ChunkRectangle area = parcel.area();
      PlanarPoint center = area.centerBlocks();
      LayoutSlot actualSlot = new LayoutSlot(
          parcel.id(),
          (center.x() - bounds.center().x()) / bounds.halfSizeX(),
          (center.z() - bounds.center().z()) / bounds.halfSizeZ()
      );
      Direction facing = parcel.roadFacing();
      java.util.Set<Direction> supportedFaces = building.connectionFaces().stream()
          .map(rotation::rotate)
          .collect(java.util.stream.Collectors.toUnmodifiableSet());
      List<RegionLayout.BuildingRoadConnection> roadConnections = parcel.roadConnections().stream()
          .filter(connection -> supportedFaces.contains(connection.face()))
          .toList();
      if (roadConnections.isEmpty()) {
        throw new IllegalArgumentException("建筑模板没有朝向道路的真实入口：" + building.path);
      }
      placed.add(new CityRegionBuildingSlot(
          actualSlot, center, area, parcel.id(), parcel.adjacentRoadId(), facing, rotation, building,
          roadConnections
      ));
    }
    return List.copyOf(placed);
  }

  private static boolean fitsExactly(
      JigsawBuilder building,
      ChunkRectangle area,
      Rotation rotation
  ) {
    boolean quarterTurn = rotation == Rotation.CLOCKWISE_90
        || rotation == Rotation.COUNTERCLOCKWISE_90;
    int width = quarterTurn ? building.footprintChunksZ() : building.footprintChunksX();
    int length = quarterTurn ? building.footprintChunksX() : building.footprintChunksZ();
    return width == area.widthChunks() && length == area.lengthChunks();
  }

  private static TerraCityRegionBuilding select(
      List<TerraCityRegionBuilding> candidates,
      RandomGenerator random
  ) {
    int totalWeight = candidates.stream().mapToInt(TerraCityRegionBuilding::weight).sum();
    int cursor = random.nextInt(totalWeight);
    for (TerraCityRegionBuilding candidate : candidates) {
      if (cursor < candidate.weight()) return candidate;
      cursor -= candidate.weight();
    }
    throw new IllegalStateException("建筑权重选择未命中");
  }

  private static long mixSeed(long base, String cityId, String regionId, int slotIndex) {
    long value = base ^ Integer.toUnsignedLong(cityId.hashCode()) * 0x9E3779B97F4A7C15L;
    value ^= Integer.toUnsignedLong(regionId.hashCode()) * 0xC2B2AE3D27D4EB4FL;
    value ^= Integer.toUnsignedLong(slotIndex) * 0x165667B19E3779F9L;
    value ^= value >>> 33;
    value *= 0xff51afd7ed558ccdl;
    value ^= value >>> 33;
    return value;
  }
}
