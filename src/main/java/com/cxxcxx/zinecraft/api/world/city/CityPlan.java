package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Objects;

/**
 * 城市规划器的不可变纯数据输出。
 */
public record CityPlan(
    ResourceLocation cityId,
    long seed,
    List<LandmarkPlacement> landmarks,
    List<RoadPath> roads,
    List<PlannedDistrict> districts,
    List<CityBlock> blocks,
    List<CityBuildingLot> lots,
    List<CityBuildingPlacement> buildings
) {
  public CityPlan {
    Objects.requireNonNull(cityId, "城市计划 ID 不能为空");
    landmarks = List.copyOf(landmarks);
    roads = List.copyOf(roads);
    districts = List.copyOf(districts);
    blocks = List.copyOf(blocks);
    lots = List.copyOf(lots);
    buildings = List.copyOf(buildings);
  }

  public record GridPoint(int x, int z) {
  }

  public record LandmarkPlacement(CityDefinition.LandmarkDefinition definition) {
    public LandmarkPlacement {
      Objects.requireNonNull(definition, "地标放置不能为空");
    }
  }

  public record RoadPath(String id, CityRoadClass roadClass, int width, List<GridPoint> points) {
    public RoadPath {
      id = Objects.requireNonNull(id, "道路 ID 不能为空").strip();
      Objects.requireNonNull(roadClass, "道路等级不能为空");
      points = List.copyOf(points);
      if (id.isEmpty() || points.size() < 2) throw new IllegalArgumentException("道路必须有 ID 和至少两个节点");
      if (width <= 0 || (width & 1) == 0) throw new IllegalArgumentException("道路宽度必须为正奇数：" + id);
    }
  }

  public record PlannedDistrict(CityDefinition.DistrictDefinition definition) {
    public PlannedDistrict {
      Objects.requireNonNull(definition, "城区计划不能为空");
    }
  }

  public record CityBlock(String id, CityRect bounds, CityDistrictType district) {
    public CityBlock {
      id = Objects.requireNonNull(id, "街区 ID 不能为空").strip();
      Objects.requireNonNull(bounds, "街区范围不能为空");
      Objects.requireNonNull(district, "街区类型不能为空");
      if (id.isEmpty()) throw new IllegalArgumentException("街区 ID 不能为空");
    }
  }
}
