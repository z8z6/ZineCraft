package com.cxxcxx.zinecraft.api.nation;

public record NationRelationEvidence(String sourceUrl, String factSummary) {
  public NationRelationEvidence {
    if (!sourceUrl.startsWith("https://")) throw new IllegalArgumentException("关系资料必须使用 HTTPS 来源");
    if (factSummary.isBlank()) throw new IllegalArgumentException("关系资料摘要不能为空");
  }

  public String getSourceUrl() {
    return sourceUrl;
  }

  public String getFactSummary() {
    return factSummary;
  }
}
