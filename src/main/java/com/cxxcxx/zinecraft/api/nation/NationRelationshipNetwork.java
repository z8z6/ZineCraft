package com.cxxcxx.zinecraft.api.nation;

import com.cxxcxx.zinecraft.api.registry.builder.NationBuilder;

import java.util.*;

/**
 * 泰拉国家状态和全部有向关系的不可变初始快照。
 */
public final class NationRelationshipNetwork {
  private final Map<NationBuilder, NationState> statesByNation;
  private final Map<NationRelationKey, NationRelation> relationsByKey;
  private final Map<NationBuilder, List<NationRelation>> relationsBySource;
  private final List<NationRelation> allRelations;

  public NationRelationshipNetwork(
      Collection<NationBuilder> nations,
      Collection<NationState> states,
      Collection<NationRelation> explicitRelations
  ) {
    Objects.requireNonNull(nations, "国家目录不能为空");
    Objects.requireNonNull(states, "国家状态集合不能为空");
    Objects.requireNonNull(explicitRelations, "显式国家关系集合不能为空");

    LinkedHashMap<NationBuilder, NationState> stateIndex = new LinkedHashMap<>();
    for (NationState state : states) {
      Objects.requireNonNull(state, "国家状态不能包含 null");
      NationState previous = stateIndex.putIfAbsent(state.nation(), state);
      if (previous != null) throw new IllegalArgumentException("国家状态重复：" + state.nation().getId());
    }
    Set<NationBuilder> expectedNations = Set.copyOf(nations);
    if (!stateIndex.keySet().equals(expectedNations)) {
      Set<NationBuilder> missing = new HashSet<>(expectedNations);
      missing.removeAll(stateIndex.keySet());
      throw new IllegalArgumentException("必须为全部泰拉国家提供状态，缺少：" + missing);
    }

    LinkedHashMap<NationRelationKey, NationRelation> relationIndex = new LinkedHashMap<>();
    for (NationBuilder from : nations) {
      for (NationBuilder to : nations) {
        if (!from.equals(to)) relationIndex.put(new NationRelationKey(from, to), defaultRelation(stateIndex, from, to));
      }
    }
    Set<NationRelationKey> explicitKeys = new HashSet<>();
    for (NationRelation relation : explicitRelations) {
      Objects.requireNonNull(relation, "显式国家关系不能包含 null");
      NationRelationKey key = new NationRelationKey(relation.from(), relation.to());
      if (!explicitKeys.add(key)) throw new IllegalArgumentException("显式有向国家关系重复：" + key);
      relationIndex.put(key, relation);
    }

    LinkedHashMap<NationBuilder, List<NationRelation>> sourceIndex = new LinkedHashMap<>();
    for (NationBuilder nation : nations) {
      List<NationRelation> outgoing = relationIndex.values().stream()
          .filter(relation -> relation.from().equals(nation))
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
      Map<NationBuilder, NationState> states,
      NationBuilder from,
      NationBuilder to
  ) {
    NationState a = states.get(from);
    NationState b = states.get(to);
    return new NationRelation(from, to, 0, a.aggression() / 5, (a.openness() + b.openness()) / 10,
        10, 0, Set.of(), "中性基线", null);
  }

  public NationState state(NationBuilder nation) {
    return statesByNation.get(Objects.requireNonNull(nation, "国家不能为空"));
  }

  public NationRelation relation(NationBuilder from, NationBuilder to) {
    Objects.requireNonNull(from, "关系起点国家不能为空");
    Objects.requireNonNull(to, "关系终点国家不能为空");
    if (from.equals(to)) throw new IllegalArgumentException("不能查询国家与自身的关系：" + from.getId());
    return relationsByKey.get(new NationRelationKey(from, to));
  }

  public List<NationRelation> relationsFrom(NationBuilder nation) {
    return relationsBySource.get(Objects.requireNonNull(nation, "国家不能为空"));
  }

  public Collection<NationRelation> allRelations() {
    return allRelations;
  }
}
