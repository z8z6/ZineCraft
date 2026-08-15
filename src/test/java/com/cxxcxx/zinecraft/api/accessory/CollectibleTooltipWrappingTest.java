package com.cxxcxx.zinecraft.api.accessory;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectibleTooltipWrappingTest {
  @Test
  void pairsDifferentLocalizationLengthsWithoutChangingVisibleText() {
    String zhCn = "短中文原文";
    String enUs = "A deliberately longer English localization that needs several independently rendered tooltip lines.";
    List<LocalizedTooltipLine> lines = CollectibleTooltips.wrapLocalizedTooltip(zhCn, enUs, 24, 42);

    assertTrue(lines.size() > 1);
    assertEquals(zhCn, lines.stream().map(LocalizedTooltipLine::getZhCn).reduce("", String::concat).replace("\u200B", ""));
    assertEquals(enUs, lines.stream().map(LocalizedTooltipLine::getEnUs).reduce("", String::concat).replace("\u200B", ""));
    assertTrue(lines.getFirst().getZhCn().codePointCount(0, lines.getFirst().getZhCn().length()) <= 24);
    assertTrue(lines.getFirst().getEnUs().codePointCount(0, lines.getFirst().getEnUs().length()) <= 24);
    assertTrue(lines.stream().skip(1).allMatch(line -> line.getZhCn().length() <= 42 && line.getEnUs().length() <= 42));
  }
}
