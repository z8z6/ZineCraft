package com.cxxcxx.zinecraft.api.nation;

import java.util.Set;

public record NationRelation(TerraNation from, TerraNation to, int favor, int warDesire,
                             int tradeAffinity, int tension, int trust, Set<NationRelationTag> tags,
                             String rationale, NationRelationEvidence evidence) {
  public NationRelation {
    tags = Set.copyOf(tags);
    if (from == to) throw new IllegalArgumentException("国家不能与自身建立双边关系");
    if (favor < -100 || favor > 100 || trust < -100 || trust > 100)
      throw new IllegalArgumentException("好感度和信任度必须在 -100—100");
    if (warDesire < 0 || warDesire > 100 || tradeAffinity < 0 || tradeAffinity > 100 || tension < 0 || tension > 100)
      throw new IllegalArgumentException("战争欲望、贸易倾向和紧张度必须在 0—100");
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

  public NationRelationEvidence getEvidence() {
    return evidence;
  }
}
