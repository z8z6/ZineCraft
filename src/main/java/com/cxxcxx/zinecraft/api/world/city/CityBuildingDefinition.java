package com.cxxcxx.zinecraft.api.world.city;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.Set;

/**
 * 一座经过城市资料审查的建筑模板声明。
 */
public record CityBuildingDefinition(
    ResourceLocation id,
    ResourceLocation template,
    int width,
    int depth,
    int height,
    Direction entranceFacing,
    int clearance,
    int weight,
    Set<CityDistrictType> allowedDistricts,
    Set<CityRoadClass> allowedRoadClasses
) {
  public CityBuildingDefinition {
    Objects.requireNonNull(id, "建筑定义 ID 不能为空");
    Objects.requireNonNull(template, "建筑模板 ID 不能为空");
    Objects.requireNonNull(entranceFacing, "建筑入口方向不能为空");
    Objects.requireNonNull(allowedDistricts, "建筑允许城区不能为空");
    Objects.requireNonNull(allowedRoadClasses, "建筑允许道路等级不能为空");
    if (!entranceFacing.getAxis().isHorizontal()) throw new IllegalArgumentException("建筑入口必须水平朝向：" + id);
    if (width <= 0 || depth <= 0 || height <= 0) throw new IllegalArgumentException("建筑尺寸必须为正数：" + id);
    if (clearance < 0 || clearance > 32) throw new IllegalArgumentException("建筑退界必须在 0—32 格：" + id);
    if (weight <= 0 || weight > 10_000) throw new IllegalArgumentException("建筑权重必须在 1—10000：" + id);
    allowedDistricts = Set.copyOf(allowedDistricts);
    allowedRoadClasses = Set.copyOf(allowedRoadClasses);
    if (allowedDistricts.isEmpty()) throw new IllegalArgumentException("建筑至少允许一种城区：" + id);
    if (allowedRoadClasses.isEmpty()) throw new IllegalArgumentException("建筑至少允许一种道路：" + id);
  }
}
