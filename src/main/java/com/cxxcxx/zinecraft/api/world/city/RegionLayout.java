package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;

import java.util.ArrayList;
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
      throw new IllegalArgumentException("移动地块必须完整规划动力、支持、生活、地表四层");
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

  public boolean isRoad(MobileLayer layer, int chunkX, int chunkZ) {
    return layer(layer).roadGraph().edges().stream()
        .anyMatch(edge -> edge.chunkArea().contains(chunkX, chunkZ));
  }

  public MobileLayerPlan layer(MobileLayer layer) {
    return mobileLayers.stream().filter(candidate -> candidate.layer() == layer).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Region 缺少移动层：" + layer));
  }

  public RoadTilePlan roadTile(MobileLayer layer, int chunkX, int chunkZ) {
    if (!isRoad(layer, chunkX, chunkZ)) {
      throw new IllegalArgumentException("非道路 Chunk 不能解析道路构件：" + chunkX + "," + chunkZ);
    }
    ArrayList<Direction> connections = new ArrayList<>(4);
    for (Direction direction : List.of(
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    )) {
      int neighborX = chunkX + direction.getStepX();
      int neighborZ = chunkZ + direction.getStepZ();
      boolean connected = isRoad(layer, neighborX, neighborZ)
          || layer == MobileLayer.SURFACE && entrances.stream().anyMatch(entrance ->
              entrance.point().chunkX() == chunkX && entrance.point().chunkZ() == chunkZ
                  && entrance.side() == direction
          );
      if (connected) connections.add(direction);
    }
    boolean north = connections.contains(Direction.NORTH);
    boolean east = connections.contains(Direction.EAST);
    boolean south = connections.contains(Direction.SOUTH);
    boolean west = connections.contains(Direction.WEST);
    int count = connections.size();
    RoadTileType type;
    Rotation rotation;
    if (count == 0) {
      type = RoadTileType.ISOLATED;
      rotation = Rotation.NONE;
    } else if (count == 1) {
      type = RoadTileType.END;
      rotation = north ? Rotation.NONE : east ? Rotation.CLOCKWISE_90
          : south ? Rotation.CLOCKWISE_180 : Rotation.COUNTERCLOCKWISE_90;
    } else if (count == 2 && north && south) {
      type = RoadTileType.STRAIGHT;
      rotation = Rotation.NONE;
    } else if (count == 2 && east && west) {
      type = RoadTileType.STRAIGHT;
      rotation = Rotation.CLOCKWISE_90;
    } else if (count == 2) {
      type = RoadTileType.CORNER;
      rotation = north && east ? Rotation.NONE : east && south ? Rotation.CLOCKWISE_90
          : south && west ? Rotation.CLOCKWISE_180 : Rotation.COUNTERCLOCKWISE_90;
    } else if (count == 3) {
      type = RoadTileType.TEE;
      rotation = !south ? Rotation.NONE : !west ? Rotation.CLOCKWISE_90
          : !north ? Rotation.CLOCKWISE_180 : Rotation.COUNTERCLOCKWISE_90;
    } else {
      type = RoadTileType.CROSS;
      rotation = Rotation.NONE;
    }
    return new RoadTilePlan(new ChunkPoint(chunkX, chunkZ), type, rotation, connections);
  }

  public RegionLayout withBuildingCoverage(double coverage) {
    List<MobileLayerPlan> updatedLayers = mobileLayers.stream().map(layer ->
        layer.layer() == MobileLayer.SURFACE
            ? new MobileLayerPlan(
                layer.layer(), layer.layoutType(), layer.buildingId(), layer.chunkArea(), layer.roadGraph(),
                layer.urbanBlocks(), layer.parcels(), layer.openSpaces(), layer.roadCoverage(),
                coverage, layer.stairChunks()
            )
            : layer
    ).toList();
    return new RegionLayout(
        layoutType, localCenter, entrances, roadGraph, updatedLayers, urbanBlocks, parcels, openSpaces,
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

  public enum RoadTileType {
    ISOLATED("isolated"), END("end"), STRAIGHT("straight"), CORNER("corner"),
    TEE("tee"), CROSS("cross");

    private final String id;

    RoadTileType(String id) {
      this.id = id;
    }

    public String id() {
      return id;
    }
  }

  public enum MobileLayer { POWER, SUPPORT, LIFE, SURFACE }

  public record MobileLayerPlan(
      MobileLayer layer,
      RegionLayoutType layoutType,
      String buildingId,
      ChunkRectangle chunkArea,
      RoadGraph roadGraph,
      List<UrbanBlock> urbanBlocks,
      List<BuildingParcel> parcels,
      List<OpenSpace> openSpaces,
      double roadCoverage,
      double buildingCoverage,
      List<ChunkPoint> stairChunks
  ) {
    public MobileLayerPlan {
      Objects.requireNonNull(layer, "移动地块层级不能为空");
      Objects.requireNonNull(layoutType, "分层布局类型不能为空");
      if (buildingId == null || buildingId.isBlank()) throw new IllegalArgumentException("分层建筑 ID 不能为空");
      Objects.requireNonNull(chunkArea, "分层规划区域不能为空");
      Objects.requireNonNull(roadGraph, "分层道路图不能为空");
      urbanBlocks = List.copyOf(Objects.requireNonNull(urbanBlocks, "分层 UrbanBlock 不能为空"));
      parcels = List.copyOf(Objects.requireNonNull(parcels, "分层 Parcel 不能为空"));
      openSpaces = List.copyOf(Objects.requireNonNull(openSpaces, "分层开放空间不能为空"));
      stairChunks = List.copyOf(Objects.requireNonNull(stairChunks, "分层楼梯位置不能为空"));
      if (stairChunks.size() < 4 || stairChunks.stream().distinct().count() != stairChunks.size()) {
        throw new IllegalArgumentException("每层至少需要四个互不重叠的楼梯");
      }
      if (!Double.isFinite(roadCoverage) || roadCoverage < 0.0 || roadCoverage > 1.0
          || !Double.isFinite(buildingCoverage) || buildingCoverage < 0.0 || buildingCoverage > 1.0) {
        throw new IllegalArgumentException("分层覆盖率无效：" + layer);
      }
    }
  }

  public record ChunkPoint(int chunkX, int chunkZ) {
  }

  public record RoadTilePlan(
      ChunkPoint point,
      RoadTileType type,
      Rotation rotation,
      List<Direction> connections
  ) {
    public RoadTilePlan {
      Objects.requireNonNull(point, "道路构件坐标不能为空");
      Objects.requireNonNull(type, "道路构件类型不能为空");
      Objects.requireNonNull(rotation, "道路构件旋转不能为空");
      connections = List.copyOf(Objects.requireNonNull(connections, "道路构件连通方向不能为空"));
    }
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
      RoadClass adjacentRoadClass,
      List<BuildingRoadConnection> roadConnections
  ) {
    public BuildingParcel(
        int id, int urbanBlockId, ChunkRectangle area, ChunkRectangle buildableArea,
        Direction roadFacing, int adjacentRoadId, RoadClass adjacentRoadClass
    ) {
      this(id, urbanBlockId, area, buildableArea, roadFacing, adjacentRoadId, adjacentRoadClass,
          List.of(new BuildingRoadConnection(roadFacing, adjacentRoadId, adjacentRoadClass)));
    }

    public BuildingParcel {
      if (id < 0 || urbanBlockId < 0) throw new IllegalArgumentException("BuildingParcel ID 无效");
      Objects.requireNonNull(area, "Parcel 范围不能为空");
      Objects.requireNonNull(buildableArea, "Parcel 可建范围不能为空");
      Objects.requireNonNull(roadFacing, "Parcel 临路方向不能为空");
      Objects.requireNonNull(adjacentRoadClass, "Parcel 临路等级不能为空");
      if (adjacentRoadId < 0) throw new IllegalArgumentException("Parcel 必须邻接道路");
      roadConnections = List.copyOf(Objects.requireNonNull(roadConnections, "Parcel 连通面不能为空"));
      if (roadConnections.isEmpty()) throw new IllegalArgumentException("Parcel 至少需要一个连通面");
      if (!roadConnections.getFirst().equals(
          new BuildingRoadConnection(roadFacing, adjacentRoadId, adjacentRoadClass))) {
        throw new IllegalArgumentException("Parcel 主连通面必须位于连通面列表首位");
      }
      if (roadConnections.stream().distinct().count() != roadConnections.size()) {
        throw new IllegalArgumentException("Parcel 连通面不能重复");
      }
    }
  }

  public record BuildingRoadConnection(Direction face, int roadId, RoadClass roadClass) {
    public BuildingRoadConnection {
      Objects.requireNonNull(face, "建筑连通面不能为空");
      Objects.requireNonNull(roadClass, "建筑连通道路等级不能为空");
      if (!face.getAxis().isHorizontal() || roadId < 0) {
        throw new IllegalArgumentException("建筑连通面必须引用水平道路");
      }
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
    public static final RoadConfig DEFAULT = new RoadConfig(1, 1, 1, List.of(3, 4, 5, 6), 0.35, 64);

    public RoadConfig {
      gridSpacingChunks = List.copyOf(Objects.requireNonNull(gridSpacingChunks, "道路间距不能为空"));
      if (primaryWidthChunks != 1 || secondaryWidthChunks != 1 || serviceWidthChunks != 1
          || gridSpacingChunks.isEmpty() || gridSpacingChunks.stream().anyMatch(spacing -> spacing < 2)
          || extraEdgeRatio < 0.0 || extraEdgeRatio > 1.0 || maxCandidateAttempts <= 0) {
        throw new IllegalArgumentException("Region 道路必须统一为单 Chunk 宽，且其他配置必须有效");
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
