package com.cxxcxx.zinecraft.api.collection;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectibleTooltipsTest {
  @Test
  void doesNotAutomaticallyWrapLongDescriptions() {
    String text = "这是一段不应由程序自行拆分的藏品描述。".repeat(8);

    List<LocalizedTooltipLine> lines = CollectibleTooltips.preserveLocalizedTooltipLines(text, text);

    assertEquals(1, lines.size());
    assertEquals(text, lines.getFirst().getZhCn());
    assertEquals(text, lines.getFirst().getEnUs());
  }

  @Test
  void preservesOnlyExplicitLineBreaks() {
    List<LocalizedTooltipLine> lines = CollectibleTooltips.preserveLocalizedTooltipLines(
        "第一段。\n第二段。",
        "First paragraph.\nSecond paragraph."
    );

    assertEquals(2, lines.size());
    assertEquals("第一段。", lines.get(0).getZhCn());
    assertEquals("第二段。", lines.get(1).getZhCn());
  }

  @Test
  void preservesOriginalWhitespace() {
    List<LocalizedTooltipLine> lines = CollectibleTooltips.preserveLocalizedTooltipLines(
        "  原文前后空格  ",
        "  Original whitespace  "
    );

    assertEquals("  原文前后空格  ", lines.getFirst().getZhCn());
    assertEquals("  Original whitespace  ", lines.getFirst().getEnUs());
  }
}
