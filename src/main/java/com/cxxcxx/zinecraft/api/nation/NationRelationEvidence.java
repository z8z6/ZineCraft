package com.cxxcxx.zinecraft.api.nation;

import java.net.URI;
import java.util.Objects;

/**
 * 支持一条显式国家关系的外部资料。
 */
public record NationRelationEvidence(String sourceUrl, String factSummary) {
  public NationRelationEvidence {
    sourceUrl = requireText(sourceUrl, "关系资料 URL");
    factSummary = requireText(factSummary, "关系资料摘要");
    URI source = URI.create(sourceUrl);
    if (!"https".equalsIgnoreCase(source.getScheme()) || source.getHost() == null) {
      throw new IllegalArgumentException("关系资料必须使用具有主机名的 HTTPS 来源：" + sourceUrl);
    }
  }

  private static String requireText(String value, String field) {
    String text = Objects.requireNonNull(value, field + "不能为空").strip();
    if (text.isEmpty()) throw new IllegalArgumentException(field + "不能为空");
    return text;
  }

  public String getSourceUrl() {
    return sourceUrl;
  }

  public String getFactSummary() {
    return factSummary;
  }
}
