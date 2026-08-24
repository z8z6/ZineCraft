package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.JigsawBuilder;
import com.cxxcxx.zinecraft.api.world.layout.LayoutSlot;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.Direction;

import java.util.Objects;
import java.util.List;

/** Region 移动地块内一个已分配建筑类型的规划槽位。 */
public record CityRegionBuildingSlot(
    LayoutSlot slot,
    PlanarPoint center,
    ChunkRectangle chunkArea,
    int parcelId,
    int adjacentRoadId,
    Direction facing,
    Rotation rotation,
    JigsawBuilder building,
    List<RegionLayout.BuildingRoadConnection> roadConnections
) {
  public CityRegionBuildingSlot {
    Objects.requireNonNull(slot, "建筑 slot 不能为空");
    Objects.requireNonNull(center, "建筑 slot 世界坐标不能为空");
    Objects.requireNonNull(chunkArea, "建筑 slot Chunk 占地不能为空");
    Objects.requireNonNull(facing, "建筑 slot 朝向不能为空");
    Objects.requireNonNull(rotation, "建筑 slot 朝向不能为空");
    Objects.requireNonNull(building, "建筑 slot 建筑不能为空");
    roadConnections = List.copyOf(Objects.requireNonNull(roadConnections, "建筑连通面不能为空"));
    if (parcelId < 0 || adjacentRoadId < 0) throw new IllegalArgumentException("建筑 Parcel/道路 ID 无效");
    if (roadConnections.isEmpty()
        || roadConnections.getFirst().roadId() != adjacentRoadId
        || roadConnections.getFirst().face() != facing) {
      throw new IllegalArgumentException("建筑主连通面必须位于连通面列表首位：" + slot.index());
    }
    if (!center.equals(chunkArea.centerBlocks())) {
      throw new IllegalArgumentException("建筑 slot 中心必须是 Chunk 占地中心：" + slot.index());
    }
    if (rotation != rotationForFacing(facing)) {
      throw new IllegalArgumentException("建筑旋转与临路朝向不一致：" + slot.index());
    }
    int expectedX = rotatedFootprintChunksX(
        building.footprintChunksX(), building.footprintChunksZ(), rotation
    );
    int expectedZ = rotatedFootprintChunksZ(
        building.footprintChunksX(), building.footprintChunksZ(), rotation
    );
    if (chunkArea.widthChunks() != expectedX || chunkArea.lengthChunks() != expectedZ) {
      throw new IllegalArgumentException("建筑 slot 占地与注册尺寸不一致：" + building.path);
    }
  }

  /** 内置模板正面为南（+Z），据目标临路方向换算模板旋转。 */
  public static Rotation rotationForFacing(Direction facing) {
    return switch (facing) {
      case SOUTH -> Rotation.NONE;
      case WEST -> Rotation.CLOCKWISE_90;
      case NORTH -> Rotation.CLOCKWISE_180;
      case EAST -> Rotation.COUNTERCLOCKWISE_90;
      default -> throw new IllegalArgumentException("建筑只能朝水平道路：" + facing);
    };
  }

  /** 返回建筑模板旋转后在世界 X 轴方向占用的 Chunk 数。 */
  public static int rotatedFootprintChunksX(int chunksX, int chunksZ, Rotation rotation) {
    return isQuarterTurn(rotation) ? chunksZ : chunksX;
  }

  /** 返回建筑模板旋转后在世界 Z 轴方向占用的 Chunk 数。 */
  public static int rotatedFootprintChunksZ(int chunksX, int chunksZ, Rotation rotation) {
    return isQuarterTurn(rotation) ? chunksX : chunksZ;
  }

  private static boolean isQuarterTurn(Rotation rotation) {
    Objects.requireNonNull(rotation, "建筑旋转不能为空");
    return rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;
  }
}
