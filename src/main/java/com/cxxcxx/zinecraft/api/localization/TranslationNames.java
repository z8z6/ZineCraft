package com.cxxcxx.zinecraft.api.localization;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class TranslationNames {
  private TranslationNames() {
  }

  // 将 chip_defender 转为 Chip Defender
  public static String toDisplayName(String value) {
    return Arrays.stream(value.split("[_.]"))
        .filter(word -> !word.isEmpty())
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
        .collect(Collectors.joining(" "));
  }
}
