package com.cxxcxx.zinecraft.api.collection;

/** 藏品原规则相对于当前 Minecraft 服务端运行时的实现完整度。 */
public enum CollectibleImplementationStatus {
  UNIMPLEMENTED("unimplemented"),
  PARTIALLY_IMPLEMENTED("partially_implemented"),
  FULLY_IMPLEMENTED("fully_implemented");

  private final String translationSuffix;

  CollectibleImplementationStatus(String translationSuffix) {
    this.translationSuffix = translationSuffix;
  }

  public String translationSuffix() {
    return translationSuffix;
  }
}
