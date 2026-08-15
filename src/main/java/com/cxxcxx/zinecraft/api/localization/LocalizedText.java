package com.cxxcxx.zinecraft.api.localization;

public record LocalizedText(String zhCn, String enUs) {
  public String getZhCn() {
    return zhCn;
  }

  public String getEnUs() {
    return enUs;
  }
}
