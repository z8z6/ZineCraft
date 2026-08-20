package com.cxxcxx.zinecraft.api.nation;

import java.util.Objects;

public record NationRelationKey(TerraNation from, TerraNation to) {
  public NationRelationKey {
    Objects.requireNonNull(from, "关系起点国家不能为空");
    Objects.requireNonNull(to, "关系终点国家不能为空");
    if (from == to) throw new IllegalArgumentException("国家关系键不能指向自身：" + from.getId());
  }

  public TerraNation getFrom() {
    return from;
  }

  public TerraNation getTo() {
    return to;
  }
}
