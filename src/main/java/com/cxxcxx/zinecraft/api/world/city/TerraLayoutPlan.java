package com.cxxcxx.zinecraft.api.world.city;

import com.cxxcxx.zinecraft.api.world.layout.PlanarPoint;

import java.util.List;
import java.util.Objects;

/**
 * 泰拉 Nation、City、Region 三级 Voronoi 边界计划。
 */
public record TerraLayoutPlan(
    List<PlanarPoint> boundary,
    List<NationLayoutPlan> nations
) {
  public TerraLayoutPlan {
    boundary = List.copyOf(Objects.requireNonNull(boundary, "泰拉地图边界不能为空"));
    nations = List.copyOf(Objects.requireNonNull(nations, "泰拉国家布局不能为空"));
    if (boundary.size() < 3) throw new IllegalArgumentException("泰拉地图边界至少需要三个点");
    validateNationAdjacency(nations);
  }

  private static void validateNationAdjacency(List<NationLayoutPlan> nations) {
    java.util.Map<String, NationLayoutPlan> byId = new java.util.LinkedHashMap<>();
    for (NationLayoutPlan nation : nations) {
      if (byId.putIfAbsent(nation.nation().id(), nation) != null) {
        throw new IllegalArgumentException("泰拉布局存在重复国家 ID：" + nation.nation().id());
      }
    }
    for (NationLayoutPlan nation : nations) {
      for (String neighbor : nation.neighboringNationIds()) {
        NationLayoutPlan neighborPlan = byId.get(neighbor);
        if (neighborPlan == null) throw new IllegalArgumentException("国家引用了未知相邻国家：" + neighbor);
        if (!neighborPlan.neighboringNationIds().contains(nation.nation().id())) {
          throw new IllegalArgumentException("国家邻接关系必须双向：" + nation.nation().id() + "/" + neighbor);
        }
      }
    }
  }

  /** 返回指定国家布局的同级相邻节点。 */
  public List<NationLayoutPlan> neighboringNations(NationLayoutPlan nation) {
    Objects.requireNonNull(nation, "国家布局不能为空");
    if (!nations.contains(nation)) throw new IllegalArgumentException("国家布局不属于当前泰拉计划");
    java.util.Map<String, NationLayoutPlan> byId = nations.stream().collect(java.util.stream.Collectors.toMap(
        plan -> plan.nation().id(),
        java.util.function.Function.identity(),
        (first, second) -> first,
        java.util.LinkedHashMap::new
    ));
    return nation.neighboringNationIds().stream().map(byId::get).toList();
  }
}
