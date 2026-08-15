package com.cxxcxx.zinecraft.api.nation;

public record NationRelationKey(TerraNation from, TerraNation to) {
  public TerraNation getFrom() {
    return from;
  }

  public TerraNation getTo() {
    return to;
  }
}
