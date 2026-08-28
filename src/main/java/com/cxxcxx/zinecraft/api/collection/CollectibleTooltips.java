package com.cxxcxx.zinecraft.api.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 为数据生成阶段创建保持原文分行的中英文藏品提示。 */
public final class CollectibleTooltips {
  private static final String EMPTY_LINE = "\u200b";

  private CollectibleTooltips() {
  }

  /**
   * 只按原文中明确存在的换行拆分提示，不根据行宽、空格或标点自动折行。
   * 较短语言使用不可见行补齐，以保持双语翻译键数量一致。
   */
  public static List<LocalizedTooltipLine> preserveLocalizedTooltipLines(String zhCn, String enUs) {
    requireText(zhCn);
    requireText(enUs);

    List<String> zhLines = originalLines(zhCn);
    List<String> enLines = originalLines(enUs);
    int lineCount = Math.max(zhLines.size(), enLines.size());
    List<LocalizedTooltipLine> result = new ArrayList<>(lineCount);
    for (int index = 0; index < lineCount; index++) {
      result.add(new LocalizedTooltipLine(lineAt(zhLines, index), lineAt(enLines, index)));
    }
    return List.copyOf(result);
  }

  private static List<String> originalLines(String text) {
    String normalized = text.replace("\r\n", "\n").replace('\r', '\n');
    return List.of(normalized.split("\n", -1)).stream()
        .map(line -> line.isEmpty() ? EMPTY_LINE : line)
        .toList();
  }

  private static String lineAt(List<String> lines, int index) {
    return index < lines.size() ? lines.get(index) : EMPTY_LINE;
  }

  private static void requireText(String value) {
    Objects.requireNonNull(value, "tooltip 文本不能为空");
    if (value.isEmpty()) throw new IllegalArgumentException("tooltip 文本不能为空");
  }
}
