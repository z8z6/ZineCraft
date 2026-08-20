package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * 一座移动城市逐城显式声明的 Blockout 地标与建筑目录。
 */
public record MobileCityProgram(
    String placeId,
    CityLayout layout,
    List<Landmark> landmarks,
    List<Building> buildings
) {
  public MobileCityProgram {
    if (placeId == null || placeId.isBlank()) throw new IllegalArgumentException("城市地点 ID 不能为空");
    Objects.requireNonNull(layout, "城市内部布局不能为空");
    landmarks = List.copyOf(Objects.requireNonNull(landmarks, "城市地标声明不能为空"));
    buildings = List.copyOf(Objects.requireNonNull(buildings, "逐城建筑目录不能为空"));
    if (landmarks.isEmpty()) throw new IllegalArgumentException(placeId + " 至少需要一个地标声明");
    if (buildings.isEmpty()) throw new IllegalArgumentException(placeId + " 至少需要一种普通建筑");
    if (new HashSet<>(landmarks.stream().map(Landmark::structureId).toList()).size() != landmarks.size())
      throw new IllegalArgumentException(placeId + " 存在重复地标 ID");
    if (new HashSet<>(buildings.stream().map(Building::structureId).toList()).size() != buildings.size())
      throw new IllegalArgumentException(placeId + " 存在重复普通建筑 ID");
  }

  public record Landmark(
      ResourceLocation structureId,
      ResourceLocation template,
      String zhCn,
      int width,
      int depth,
      int height,
      int towerX,
      int towerZ,
      int towerHeight
  ) {
    public Landmark {
      Objects.requireNonNull(structureId, "城市地标结构 ID 不能为空");
      Objects.requireNonNull(template, "城市地标 NBT 不能为空");
      if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("城市地标名称不能为空");
      int area = Math.multiplyExact(width, depth);
      if (area < 200 || area > 300 || height <= 0 || towerHeight <= 0) {
        throw new IllegalArgumentException(structureId + " 地标 Blockout 尺寸非法");
      }
    }

    public int area() {
      return width * depth;
    }
  }

  public record Building(
      ResourceLocation structureId,
      ResourceLocation template,
      String zhCn,
      int width,
      int depth,
      int height,
      int weight
  ) {
    public Building {
      Objects.requireNonNull(structureId, "普通建筑结构 ID 不能为空");
      Objects.requireNonNull(template, "普通建筑 NBT 不能为空");
      if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("普通建筑名称不能为空");
      if (width <= 0 || depth <= 0) throw new IllegalArgumentException(structureId + " 普通建筑占地必须为正数");
      if (height < 6 || height > 22) throw new IllegalArgumentException(structureId + " 高度必须为 6—22 格");
      if (weight <= 0 || weight > 10_000) throw new IllegalArgumentException(structureId + " 权重必须为 1—10000");
    }
  }
}
