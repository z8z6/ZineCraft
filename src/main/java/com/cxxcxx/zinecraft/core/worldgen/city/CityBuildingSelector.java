package com.cxxcxx.zinecraft.core.worldgen.city;

import com.cxxcxx.zinecraft.api.world.city.CityBuildingDefinition;
import com.cxxcxx.zinecraft.api.world.city.CityBuildingLot;
import com.cxxcxx.zinecraft.api.world.city.CityBuildingPlacement;
import com.cxxcxx.zinecraft.api.world.city.CityRect;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;

import java.util.*;

/**
 * 对一个已切分地块执行确定性的建筑适配与选择。
 */
public final class CityBuildingSelector {
  private static Optional<Candidate> candidate(CityBuildingLot lot, CityBuildingDefinition definition) {
    if (!definition.allowedDistricts().contains(lot.district())
        || !definition.allowedRoadClasses().contains(lot.roadClass())
        || definition.height() > lot.maxHeight()) {
      return Optional.empty();
    }
    Rotation rotation = rotationFor(definition.entranceFacing(), lot.roadFacing());
    int width = swapsAxes(rotation) ? definition.depth() : definition.width();
    int depth = swapsAxes(rotation) ? definition.width() : definition.depth();
    int clearance = definition.clearance();
    if (width + clearance * 2 > lot.bounds().width() || depth + clearance * 2 > lot.bounds().depth()) {
      return Optional.empty();
    }
    return Optional.of(new Candidate(definition, rotation, alignToRoad(lot, width, depth, clearance)));
  }

  private static CityRect alignToRoad(CityBuildingLot lot, int width, int depth, int clearance) {
    CityRect bounds = lot.bounds();
    int x = bounds.minX() + (bounds.width() - width) / 2;
    int z = bounds.minZ() + (bounds.depth() - depth) / 2;
    Direction road = lot.roadFacing();
    if (road == Direction.NORTH) z = bounds.minZ() + clearance;
    if (road == Direction.SOUTH) z = bounds.maxZExclusive() - clearance - depth;
    if (road == Direction.WEST) x = bounds.minX() + clearance;
    if (road == Direction.EAST) x = bounds.maxXExclusive() - clearance - width;
    return CityRect.sized(x, z, width, depth);
  }

  private static Rotation rotationFor(Direction templateEntrance, Direction roadFacing) {
    for (Rotation rotation : Rotation.values()) {
      if (rotation.rotate(templateEntrance) == roadFacing) return rotation;
    }
    throw new IllegalArgumentException("无法将建筑入口旋转到道路方向：" + templateEntrance + " -> " + roadFacing);
  }

  private static boolean swapsAxes(Rotation rotation) {
    return rotation == Rotation.CLOCKWISE_90 || rotation == Rotation.COUNTERCLOCKWISE_90;
  }

  public Optional<CityBuildingPlacement> select(
      long citySeed,
      CityBuildingLot lot,
      Collection<CityBuildingDefinition> definitions
  ) {
    Objects.requireNonNull(lot, "城市地块不能为空");
    Objects.requireNonNull(definitions, "城市建筑目录不能为空");

    List<Candidate> candidates = new ArrayList<>();
    definitions.stream()
        .sorted(Comparator.comparing(definition -> definition.id().toString()))
        .forEach(definition -> candidate(lot, definition).ifPresent(candidates::add));
    if (candidates.isEmpty()) return Optional.empty();

    long totalWeight = candidates.stream().mapToLong(candidate -> candidate.definition().weight()).sum();
    long cursor = Long.remainderUnsigned(CityPlanningSeeds.lotSeed(citySeed, lot.id()), totalWeight);
    for (Candidate candidate : candidates) {
      cursor -= candidate.definition().weight();
      if (cursor < 0) {
        return Optional.of(new CityBuildingPlacement(
            lot, candidate.definition(), candidate.rotation(), candidate.occupiedArea()
        ));
      }
    }
    throw new IllegalStateException("城市建筑权重选择未命中：" + lot.id());
  }

  private record Candidate(
      CityBuildingDefinition definition,
      Rotation rotation,
      CityRect occupiedArea
  ) {
  }
}
