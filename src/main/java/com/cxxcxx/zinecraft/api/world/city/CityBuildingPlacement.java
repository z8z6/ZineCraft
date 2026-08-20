package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.world.level.block.Rotation;

import java.util.Objects;

/**
 * 建筑选择阶段的纯数据结果；实际 NBT 放置由城市结构 Piece 完成。
 */
public record CityBuildingPlacement(
    CityBuildingLot lot,
    CityBuildingDefinition building,
    Rotation rotation,
    CityRect occupiedArea
) {
  public CityBuildingPlacement {
    Objects.requireNonNull(lot, "建筑地块不能为空");
    Objects.requireNonNull(building, "建筑定义不能为空");
    Objects.requireNonNull(rotation, "建筑旋转不能为空");
    Objects.requireNonNull(occupiedArea, "建筑占地不能为空");
    if (!lot.bounds().contains(occupiedArea)) throw new IllegalArgumentException("建筑占地越出地块：" + lot.id());
  }
}
