package com.cxxcxx.zinecraft.api.nation;

import java.util.Arrays;
import java.util.Optional;

public enum NationRelationTag {
  TRADE("trade"),
  INVESTMENT("investment"),
  RELIGIOUS_TIES("religious_ties"),
  MEDIATION("mediation"),
  HISTORIC_WAR("historic_war"),
  ACTIVE_CONFLICT("active_conflict"),
  PROXY_CONFLICT("proxy_conflict"),
  OCCUPATION("occupation"),
  BORDER_TENSION("border_tension"),
  DIPLOMATIC_EXCLUSION("diplomatic_exclusion"),
  TECHNOLOGY_TRANSFER("technology_transfer");

  private final String id;

  NationRelationTag(String id) {
    this.id = id;
  }

  public static Optional<NationRelationTag> findById(String id) {
    if (id == null) return Optional.empty();
    return Arrays.stream(values()).filter(tag -> tag.id.equals(id)).findFirst();
  }

  public String id() {
    return id;
  }
}
