package com.cxxcxx.zinecraft.api.nation;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 一条有向国家关系；没有资料支持的默认中性边允许 {@code evidence} 为空。
 */
public record NationRelation(
    TerraNation from,
    TerraNation to,
    int favor,
    int warDesire,
    int tradeAffinity,
    int tension,
    int trust,
    Set<NationRelationTag> tags,
    String rationale,
    @Nullable NationRelationEvidence evidence
) {
  public NationRelation {
    Objects.requireNonNull(from, "关系起点国家不能为空");
    Objects.requireNonNull(to, "关系终点国家不能为空");
    Objects.requireNonNull(tags, "关系标签集合不能为空");
    rationale = Objects.requireNonNull(rationale, "关系说明不能为空").strip();
    if (rationale.isEmpty()) throw new IllegalArgumentException("关系说明不能为空");
    tags = Set.copyOf(tags);
    if (from == to) throw new IllegalArgumentException("国家不能与自身建立关系：" + from.getId());
    requireSignedMetric(favor, "好感度");
    requireSignedMetric(trust, "信任度");
    requirePercentage(warDesire, "战争欲望");
    requirePercentage(tradeAffinity, "贸易倾向");
    requirePercentage(tension, "紧张度");
    if (!tags.isEmpty() && evidence == null) {
      throw new IllegalArgumentException("带事实标签的显式国家关系必须提供资料依据");
    }
  }

  private static void requireSignedMetric(int value, String field) {
    if (value < -100 || value > 100) {
      throw new IllegalArgumentException(field + "必须在 -100—100：" + value);
    }
  }

  private static void requirePercentage(int value, String field) {
    if (value < 0 || value > 100) {
      throw new IllegalArgumentException(field + "必须在 0—100：" + value);
    }
  }

  public Optional<NationRelationEvidence> evidenceOptional() {
    return Optional.ofNullable(evidence);
  }

  public TerraNation getFrom() {
    return from;
  }

  public TerraNation getTo() {
    return to;
  }

  public int getFavor() {
    return favor;
  }

  public int getWarDesire() {
    return warDesire;
  }

  public int getTradeAffinity() {
    return tradeAffinity;
  }

  public int getTension() {
    return tension;
  }

  public int getTrust() {
    return trust;
  }

  public Set<NationRelationTag> getTags() {
    return tags;
  }

  public String getRationale() {
    return rationale;
  }

  @Nullable
  public NationRelationEvidence getEvidence() {
    return evidence;
  }
}
