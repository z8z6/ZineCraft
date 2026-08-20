package com.cxxcxx.zinecraft.api.nation;

import java.util.*;

/**
 * 十九国状态和全部有向关系的不可变初始快照。
 */
public final class NationRelationshipNetwork {
  private final Map<TerraNation, NationState> statesByNation;
  private final Map<NationRelationKey, NationRelation> relationsByKey;
  private final Map<TerraNation, List<NationRelation>> relationsBySource;
  private final List<NationRelation> allRelations;

  public NationRelationshipNetwork(Collection<NationState> states, Collection<NationRelation> explicitRelations) {
    Objects.requireNonNull(states, "国家状态集合不能为空");
    Objects.requireNonNull(explicitRelations, "显式国家关系集合不能为空");

    EnumMap<TerraNation, NationState> stateIndex = new EnumMap<>(TerraNation.class);
    for (NationState state : states) {
      Objects.requireNonNull(state, "国家状态不能包含 null");
      NationState previous = stateIndex.putIfAbsent(state.nation(), state);
      if (previous != null) throw new IllegalArgumentException("国家状态重复：" + state.nation().getId());
    }
    Set<TerraNation> expectedNations = Set.of(TerraNation.values());
    if (!stateIndex.keySet().equals(expectedNations)) {
      Set<TerraNation> missing = new HashSet<>(expectedNations);
      missing.removeAll(stateIndex.keySet());
      throw new IllegalArgumentException("必须为全部泰拉国家提供状态，缺少：" + missing);
    }

    LinkedHashMap<NationRelationKey, NationRelation> relationIndex = new LinkedHashMap<>();
    for (TerraNation from : TerraNation.entries()) {
      for (TerraNation to : TerraNation.entries()) {
        if (from != to) relationIndex.put(new NationRelationKey(from, to), defaultRelation(stateIndex, from, to));
      }
    }
    Set<NationRelationKey> explicitKeys = new HashSet<>();
    for (NationRelation relation : explicitRelations) {
      Objects.requireNonNull(relation, "显式国家关系不能包含 null");
      NationRelationKey key = new NationRelationKey(relation.from(), relation.to());
      if (!explicitKeys.add(key)) throw new IllegalArgumentException("显式有向国家关系重复：" + key);
      relationIndex.put(key, relation);
    }

    EnumMap<TerraNation, List<NationRelation>> sourceIndex = new EnumMap<>(TerraNation.class);
    for (TerraNation nation : TerraNation.entries()) {
      List<NationRelation> outgoing = relationIndex.values().stream()
          .filter(relation -> relation.from() == nation)
          .sorted(Comparator.comparing(relation -> relation.to().getId()))
          .toList();
      sourceIndex.put(nation, outgoing);
    }
    this.statesByNation = Collections.unmodifiableMap(stateIndex);
    this.relationsByKey = Collections.unmodifiableMap(relationIndex);
    this.relationsBySource = Collections.unmodifiableMap(sourceIndex);
    this.allRelations = List.copyOf(relationIndex.values());
  }

  private static NationRelation defaultRelation(
      Map<TerraNation, NationState> states,
      TerraNation from,
      TerraNation to
  ) {
    NationState a = states.get(from);
    NationState b = states.get(to);
    return new NationRelation(from, to, 0, a.aggression() / 5, (a.openness() + b.openness()) / 10,
        10, 0, Set.of(), "中性基线", null);
  }

  public NationState state(TerraNation nation) {
    return statesByNation.get(Objects.requireNonNull(nation, "国家不能为空"));
  }

  public NationRelation relation(TerraNation from, TerraNation to) {
    Objects.requireNonNull(from, "关系起点国家不能为空");
    Objects.requireNonNull(to, "关系终点国家不能为空");
    if (from == to) throw new IllegalArgumentException("不能查询国家与自身的关系：" + from.getId());
    return relationsByKey.get(new NationRelationKey(from, to));
  }

  public List<NationRelation> relationsFrom(TerraNation nation) {
    return relationsBySource.get(Objects.requireNonNull(nation, "国家不能为空"));
  }

  public Collection<NationRelation> allRelations() {
    return allRelations;
  }
}
