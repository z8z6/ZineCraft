package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.core.Direction;

import java.util.List;
import java.util.Objects;

/** 一个 Region 内道路优先生成后的完整 Chunk 级规划结果。 */
public record RegionLayout(
    RegionLayoutType layoutType,
    ChunkPoint localCenter,
    List<RegionEntrance> entrances,
    RoadGraph roadGraph,
    List<MobileLayerPlan> mobileLayers,
    List<UrbanBlock> urbanBlocks,
    List<BuildingParcel> parcels,
    List<OpenSpace> openSpaces,
    double roadCoverage,
    double buildingCoverage,
    List<String> debugStages
) {
  public RegionLayout {
    Objects.requireNonNull(layoutType, "Region 布局类型不能为空");
    Objects.requireNonNull(localCenter, "Region 局部中心不能为空");
    entrances = List.copyOf(Objects.requireNonNull(entrances, "Region 入口不能为空"));
    Objects.requireNonNull(roadGraph, "Region 道路图不能为空");
    mobileLayers = List.copyOf(Objects.requireNonNull(mobileLayers, "移动地块分层规划不能为空"));
    if (mobileLayers.size() != MobileLayer.values().length
        || mobileLayers.stream().map(MobileLayerPlan::layer).distinct().count() != MobileLayer.values().length) {
      throw new IllegalArgumentException("移动地块必须完整规划动力、支持、生活三层");
    }
    urbanBlocks = List.copyOf(Objects.requireNonNull(urbanBlocks, "UrbanBlock 清单不能为空"));
    parcels = List.copyOf(Objects.requireNonNull(parcels, "BuildingParcel 清单不能为空"));
    openSpaces = List.copyOf(Objects.requireNonNull(openSpaces, "开放空间清单不能为空"));
    debugStages = List.copyOf(Objects.requireNonNull(debugStages, "调试阶段不能为空"));
    if (!Double.isFinite(roadCoverage) || roadCoverage < 0.0 || roadCoverage > 1.0) {
      throw new IllegalArgumentException("Region 道路覆盖率无效");
    }
    if (!Double.isFinite(buildingCoverage) || buildingCoverage < 0.0 || buildingCoverage > 1.0) {
      throw new IllegalArgumentException("Region 建筑覆盖率无效");
    }
  }

  public boolean isRoad(int chunkX, int chunkZ) {
    return roadGraph.edges().stream().anyMatch(edge -> edge.chunkArea().contains(chunkX, chunkZ));
  }

  public RegionLayout withBuildingCoverage(double coverage) {
    return new RegionLayout(
        layoutType, localCenter, entrances, roadGraph, mobileLayers, urbanBlocks, parcels, openSpaces,
        roadCoverage, coverage, debugStages
    );
  }

  public enum RegionLayoutType { GRID, CONCENTRIC, RADIAL_GRID, SPINE, CAMPUS, HYBRID }

  public enum RoadClass {
    PRIMARY(3), SECONDARY(2), SERVICE(1);

    private final int priority;

    RoadClass(int priority) {
      this.priority = priority;
    }

    public int priority() {
      return priority;
    }
  }

  public enum RoadNodeType { ENTRANCE, INTERSECTION, CENTER, PLAZA, LANDMARK }

  public enum OpenSpaceType { PLAZA, GREEN, SERVICE, EMPTY }

  public enum MobileLayer { POWER, SUPPORT, LIFE }

  public record MobileLayerPlan(MobileLayer layer, String buildingId, ChunkRectangle chunkArea) {
    public MobileLayerPlan {
      Objects.requireNonNull(layer, "移动地块层级不能为空");
      if (buildingId == null || buildingId.isBlank()) throw new IllegalArgumentException("分层建筑 ID 不能为空");
      Objects.requireNonNull(chunkArea, "分层规划区域不能为空");
    }
  }

  public record ChunkPoint(int chunkX, int chunkZ) {
  }

  public record RegionEntrance(
      Direction side,
      int offsetChunks,
      int widthChunks,
      int connectedRegionId,
      ChunkPoint point
  ) {
    public RegionEntrance {
      Objects.requireNonNull(side, "Region 入口方向不能为空");
      Objects.requireNonNull(point, "Region 入口坐标不能为空");
      if (!side.getAxis().isHorizontal()) throw new IllegalArgumentException("Region 入口只能位于水平边界");
      if (offsetChunks < 0 || widthChunks <= 0 || connectedRegionId < 0) {
        throw new IllegalArgumentException("Region 入口参数无效");
      }
    }
  }

  public record RoadNode(int id, ChunkPoint point, RoadNodeType type) {
    public RoadNode {
      if (id < 0) throw new IllegalArgumentException("道路节点 ID 不能为负数");
      Objects.requireNonNull(point, "道路节点坐标不能为空");
      Objects.requireNonNull(type, "道路节点类型不能为空");
    }
  }

  public record RoadEdge(
      int id,
      int fromNodeId,
      int toNodeId,
      RoadClass roadClass,
      int widthChunks,
      ChunkRectangle chunkArea
  ) {
    public RoadEdge {
      if (id < 0 || fromNodeId < 0 || toNodeId < 0 || fromNodeId == toNodeId) {
        throw new IllegalArgumentException("道路边节点无效");
      }
      Objects.requireNonNull(roadClass, "道路等级不能为空");
      Objects.requireNonNull(chunkArea, "道路 Chunk 范围不能为空");
      if (widthChunks <= 0) throw new IllegalArgumentException("道路宽度必须为正数");
      if (chunkArea.widthChunks() != widthChunks && chunkArea.lengthChunks() != widthChunks) {
        throw new IllegalArgumentException("道路矩形必须有一边等于声明宽度");
      }
    }
  }

  public record RoadGraph(List<RoadNode> nodes, List<RoadEdge> edges) {
    public RoadGraph {
      nodes = List.copyOf(Objects.requireNonNull(nodes, "道路节点清单不能为空"));
      edges = List.copyOf(Objects.requireNonNull(edges, "道路边清单不能为空"));
      if (nodes.stream().map(RoadNode::id).distinct().count() != nodes.size()) {
        throw new IllegalArgumentException("道路节点 ID 不能重复");
      }
      if (edges.stream().map(RoadEdge::id).distinct().count() != edges.size()) {
        throw new IllegalArgumentException("道路边 ID 不能重复");
      }
    }
  }

  public record UrbanBlock(int id, int cellCount, ChunkRectangle bounds) {
    public UrbanBlock {
      if (id < 0) throw new IllegalArgumentException("UrbanBlock ID 不能为负数");
      Objects.requireNonNull(bounds, "UrbanBlock 边界不能为空");
      if (cellCount <= 0) throw new IllegalArgumentException("UrbanBlock 不能为空");
    }
  }

  public record BuildingParcel(
      int id,
      int urbanBlockId,
      ChunkRectangle area,
      ChunkRectangle buildableArea,
      Direction roadFacing,
      int adjacentRoadId,
      RoadClass adjacentRoadClass
  ) {
    public BuildingParcel {
      if (id < 0 || urbanBlockId < 0) throw new IllegalArgumentException("BuildingParcel ID 无效");
      Objects.requireNonNull(area, "Parcel 范围不能为空");
      Objects.requireNonNull(buildableArea, "Parcel 可建范围不能为空");
      Objects.requireNonNull(roadFacing, "Parcel 临路方向不能为空");
      Objects.requireNonNull(adjacentRoadClass, "Parcel 临路等级不能为空");
      if (adjacentRoadId < 0) throw new IllegalArgumentException("Parcel 必须邻接道路");
    }
  }

  public record OpenSpace(int id, ChunkRectangle area, OpenSpaceType type) {
    public OpenSpace {
      if (id < 0) throw new IllegalArgumentException("开放空间 ID 不能为负数");
      Objects.requireNonNull(area, "开放空间范围不能为空");
      Objects.requireNonNull(type, "开放空间类型不能为空");
    }
  }

  public record RoadConfig(
      int primaryWidthChunks,
      int secondaryWidthChunks,
      int serviceWidthChunks,
      List<Integer> gridSpacingChunks,
      double extraEdgeRatio,
      int maxCandidateAttempts
  ) {
    public static final RoadConfig DEFAULT = new RoadConfig(2, 1, 1, List.of(3, 4, 5, 6), 0.35, 64);

    public RoadConfig {
      gridSpacingChunks = List.copyOf(Objects.requireNonNull(gridSpacingChunks, "道路间距不能为空"));
      if (primaryWidthChunks <= 0 || secondaryWidthChunks <= 0 || serviceWidthChunks <= 0
          || gridSpacingChunks.isEmpty() || gridSpacingChunks.stream().anyMatch(spacing -> spacing < 2)
          || extraEdgeRatio < 0.0 || extraEdgeRatio > 1.0 || maxCandidateAttempts <= 0) {
        throw new IllegalArgumentException("Region 道路配置无效");
      }
    }

    public int width(RoadClass roadClass) {
      return switch (roadClass) {
        case PRIMARY -> primaryWidthChunks;
        case SECONDARY -> secondaryWidthChunks;
        case SERVICE -> serviceWidthChunks;
      };
    }
  }

  public record LayoutWeight(RegionLayoutType layoutType, int weight) {
    public LayoutWeight {
      Objects.requireNonNull(layoutType, "Region 布局权重类型不能为空");
      if (weight <= 0) throw new IllegalArgumentException("Region 布局权重必须为正数");
    }
  }
}
