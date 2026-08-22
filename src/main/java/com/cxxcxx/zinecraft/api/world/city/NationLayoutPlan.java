package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;
import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 一个国家的 Voronoi 边界及其城市划分结果。
 */
public record NationLayoutPlan(
    NationBuilder nation,
    PlanarPoint center,
    List<PlanarPoint> boundary,
    List<CityLayoutPlan> cities,
    List<String> neighboringNationIds
) {
  public NationLayoutPlan {
    Objects.requireNonNull(nation, "国家不能为空");
    Objects.requireNonNull(center, "国家中心不能为空");
    boundary = List.copyOf(Objects.requireNonNull(boundary, "国家边界不能为空"));
    cities = List.copyOf(Objects.requireNonNull(cities, "国家城市布局不能为空"));
    neighboringNationIds = List.copyOf(Objects.requireNonNull(neighboringNationIds, "相邻国家 ID 不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("国家边界至少需要三个点");
    if (neighboringNationIds.contains(nation.id())) {
      throw new IllegalArgumentException("国家不能与自身相邻：" + nation.id());
    }
    if (neighboringNationIds.stream().distinct().count() != neighboringNationIds.size()) {
      throw new IllegalArgumentException("相邻国家 ID 不能重复：" + nation.id());
    }
    validateCityAdjacency(cities);
  }

  private static void validateCityAdjacency(List<CityLayoutPlan> cities) {
    java.util.Map<String, CityLayoutPlan> byId = new java.util.LinkedHashMap<>();
    for (CityLayoutPlan city : cities) {
      if (byId.putIfAbsent(city.city().id(), city) != null) {
        throw new IllegalArgumentException("国家存在重复城市 ID：" + city.city().id());
      }
    }
    for (CityLayoutPlan city : cities) {
      for (String neighbor : city.neighboringCityIds()) {
        CityLayoutPlan neighborPlan = byId.get(neighbor);
        if (neighborPlan == null) throw new IllegalArgumentException("城市引用了未知相邻城市：" + neighbor);
        if (!neighborPlan.neighboringCityIds().contains(city.city().id())) {
          throw new IllegalArgumentException("城市邻接关系必须双向：" + city.city().id() + "/" + neighbor);
        }
      }
    }
  }

  /** 返回指定城市布局的同级相邻节点。 */
  public List<CityLayoutPlan> neighboringCities(CityLayoutPlan city) {
    Objects.requireNonNull(city, "城市布局不能为空");
    if (!cities.contains(city)) throw new IllegalArgumentException("城市布局不属于当前国家计划");
    java.util.Map<String, CityLayoutPlan> byId = cities.stream().collect(java.util.stream.Collectors.toMap(
        plan -> plan.city().id(),
        java.util.function.Function.identity(),
        (first, second) -> first,
        java.util.LinkedHashMap::new
    ));
    return city.neighboringCityIds().stream().map(byId::get).toList();
  }
}
