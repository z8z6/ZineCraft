package com.cxxcxx.zinecraft.api.nation;

import java.util.*;

public final class NationRelationshipNetwork {
  private final Map<TerraNation, NationState> statesByNation = new EnumMap<>(TerraNation.class);
  private final Map<NationRelationKey, NationRelation> relationsByKey = new LinkedHashMap<>();

  public NationRelationshipNetwork(Collection<NationState> states, Collection<NationRelation> explicitRelations) {
    states.forEach(state -> statesByNation.put(state.nation(), state));
    if (!statesByNation.keySet().equals(Set.of(TerraNation.values())))
      throw new IllegalArgumentException("必须为全部泰拉国家提供状态");
    for (var from : TerraNation.values())
      for (var to : TerraNation.values())
        if (from != to)
          relationsByKey.put(new NationRelationKey(from, to), defaultRelation(from, to));
    for (var relation : explicitRelations) {
      var key = new NationRelationKey(relation.from(), relation.to());
      if (relationsByKey.put(key, relation) == relation) throw new IllegalArgumentException("存在重复的有向国家关系");
    }
  }

  public NationState state(TerraNation nation) {
    return statesByNation.get(nation);
  }

  public NationRelation relation(TerraNation from, TerraNation to) {
    if (from == to) throw new IllegalArgumentException("不能查询国家与自身的关系");
    return relationsByKey.get(new NationRelationKey(from, to));
  }

  public List<NationRelation> relationsFrom(TerraNation nation) {
    return relationsByKey.values().stream().filter(r -> r.from() == nation).sorted(Comparator.comparing(r -> r.to().getId())).toList();
  }

  public Collection<NationRelation> allRelations() {
    return relationsByKey.values();
  }

  private NationRelation defaultRelation(TerraNation from, TerraNation to) {
    var a = statesByNation.get(from);
    var b = statesByNation.get(to);
    return new NationRelation(from, to, 0, a.aggression() / 5, (a.openness() + b.openness()) / 10,
        10, 0, Set.of(), "中性基线", null);
  }
}
