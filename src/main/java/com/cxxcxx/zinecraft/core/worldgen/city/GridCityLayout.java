package com.cxxcxx.zinecraft.core.worldgen.city;

import com.cxxcxx.zinecraft.api.world.city.*;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 默认棋盘格城市布局：道路间隔切分地块，并剔除地标占地和不可建地形。
 */
public record GridCityLayout(
    int lotWidth,
    int lotDepth,
    int roadWidth,
    int terrainSampleStep,
    int maxSlopeCost
) implements CityLayout {
  public static final GridCityLayout DEFAULT = new GridCityLayout(12, 12, 4, 4, 8);

  public GridCityLayout {
    if (lotWidth <= 0 || lotDepth <= 0) throw new IllegalArgumentException("棋盘格地块尺寸必须为正数");
    if (roadWidth <= 0 || (roadWidth & 1) != 0) throw new IllegalArgumentException("棋盘格道路宽度必须为正偶数");
    if (terrainSampleStep <= 0 || maxSlopeCost < 0) throw new IllegalArgumentException("棋盘格地形参数非法");
  }

  private static boolean intersectsReserved(CityRect lot, List<CityRect> reservedAreas) {
    return reservedAreas.stream().anyMatch(lot::intersects);
  }

  private static Direction roadFacing(int row, int column) {
    return switch (Math.floorMod(row * 2 + column, 4)) {
      case 0 -> Direction.NORTH;
      case 1 -> Direction.EAST;
      case 2 -> Direction.SOUTH;
      default -> Direction.WEST;
    };
  }

  @Override
  public List<CityBuildingLot> createBuildingLots(Context context) {
    List<CityBuildingLot> lots = new ArrayList<>();
    int pitchX = lotWidth + roadWidth;
    int pitchZ = lotDepth + roadWidth;
    int startX = context.bounds().minX() + roadWidth / 2;
    int startZ = context.bounds().minZ() + roadWidth / 2;
    int row = 0;
    for (int z = startZ; z + lotDepth <= context.bounds().maxZExclusive(); z += pitchZ, row++) {
      int column = 0;
      for (int x = startX; x + lotWidth <= context.bounds().maxXExclusive(); x += pitchX, column++) {
        CityRect lot = CityRect.sized(x, z, lotWidth, lotDepth);
        if (intersectsReserved(lot, context.reservedAreas()) || !isBuildable(lot, context.terrain())) continue;
        Optional<CityDefinition.DistrictDefinition> district = context.districts().stream()
            .filter(candidate -> candidate.bounds().contains(lot))
            .findFirst();
        if (district.isEmpty()) continue;
        lots.add(new CityBuildingLot(
            "grid_" + row + "_" + column,
            lot,
            roadFacing(row, column),
            district.get().type(),
            context.roadClass(),
            district.get().maxBuildingHeight()
        ));
      }
    }
    return List.copyOf(lots);
  }

  private boolean isBuildable(CityRect lot, CityPlanner.TerrainModel terrain) {
    for (int z = lot.minZ(); z < lot.maxZExclusive(); z += terrainSampleStep) {
      for (int x = lot.minX(); x < lot.maxXExclusive(); x += terrainSampleStep) {
        CityPlanner.TerrainCell cell = terrain.sample(x, z);
        if (!cell.buildable() || cell.slopeCost() > maxSlopeCost) return false;
      }
    }
    CityPlanner.TerrainCell farCorner = terrain.sample(lot.maxXExclusive() - 1, lot.maxZExclusive() - 1);
    return farCorner.buildable() && farCorner.slopeCost() <= maxSlopeCost;
  }
}
