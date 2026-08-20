package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.core.Direction;

import java.util.Objects;

/**
 * 道路切割完成后等待填充的一块城市用地。
 */
public record CityBuildingLot(
    String id,
    CityRect bounds,
    Direction roadFacing,
    CityDistrictType district,
    CityRoadClass roadClass,
    int maxHeight
) {
  public CityBuildingLot {
    id = Objects.requireNonNull(id, "地块 ID 不能为空").strip();
    bounds = Objects.requireNonNull(bounds, "地块范围不能为空");
    roadFacing = Objects.requireNonNull(roadFacing, "地块临路方向不能为空");
    district = Objects.requireNonNull(district, "地块城区不能为空");
    roadClass = Objects.requireNonNull(roadClass, "地块道路等级不能为空");
    if (id.isEmpty()) throw new IllegalArgumentException("地块 ID 不能为空");
    if (!roadFacing.getAxis().isHorizontal()) throw new IllegalArgumentException("地块临路方向必须水平：" + id);
    if (maxHeight <= 0) throw new IllegalArgumentException("地块限高必须为正数：" + id);
  }
}
