package com.cxxcxx.zinecraft.api.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 为数据生成阶段创建行数一致的中英文藏品提示。
 */
public final class CollectibleTooltips {
  public static final int DEFAULT_LINE_CHARACTERS = 42;
  private static final String EMPTY_LINE = "\u200b";

  /** 工具类不允许实例化。 */
  private CollectibleTooltips() {
  }

  /**
   * 使用默认行宽分别换行中英文提示，并补齐两种语言的行数。
   *
   * @return 行数一致的不可变双语提示列表
   */
  public static List<LocalizedTooltipLine> wrapLocalizedTooltip(String zhCn, String enUs) {
    return wrapLocalizedTooltip(zhCn, enUs, DEFAULT_LINE_CHARACTERS, DEFAULT_LINE_CHARACTERS);
  }

  /**
   * 使用指定首行宽度和默认续行宽度分别换行中英文提示。
   *
   * @param firstLineCharacters 首行允许的最大 Unicode 字符数
   * @return 行数一致的不可变双语提示列表
   */
  public static List<LocalizedTooltipLine> wrapLocalizedTooltip(
      String zhCn,
      String enUs,
      int firstLineCharacters
  ) {
    return wrapLocalizedTooltip(zhCn, enUs, firstLineCharacters, DEFAULT_LINE_CHARACTERS);
  }

  /**
   * 使用指定首行和续行宽度分别换行中英文提示，较短语言以不可见空行补齐。
   *
   * @param zhCn 中文提示原文
   * @param enUs 英文提示原文
   * @param firstLineCharacters 首行允许的最大 Unicode 字符数
   * @param continuationCharacters 后续行允许的最大 Unicode 字符数
   * @return 行数一致的不可变双语提示列表
   */
  public static List<LocalizedTooltipLine> wrapLocalizedTooltip(
      String zhCn,
      String enUs,
      int firstLineCharacters,
      int continuationCharacters
  ) {
    requireText(zhCn);
    requireText(enUs);
    if (firstLineCharacters <= 0 || continuationCharacters <= 0) {
      throw new IllegalArgumentException("tooltip 行宽必须大于 0");
    }

    List<String> zhLines = wrap(zhCn, firstLineCharacters, continuationCharacters);
    List<String> enLines = wrap(enUs, firstLineCharacters, continuationCharacters);
    int lineCount = Math.max(zhLines.size(), enLines.size());
    List<LocalizedTooltipLine> result = new ArrayList<>(lineCount);
    for (int index = 0; index < lineCount; index++) {
      result.add(new LocalizedTooltipLine(lineAt(zhLines, index), lineAt(enLines, index)));
    }
    return List.copyOf(result);
  }

  /**
   * 按 Unicode 码点和段落边界拆分单种语言的提示文本。
   */
  private static List<String> wrap(String text, int firstWidth, int continuationWidth) {
    List<String> result = new ArrayList<>();
    int width = firstWidth;
    String normalized = text.replace("\r\n", "\n").replace('\r', '\n');
    for (String paragraph : normalized.split("\n", -1)) {
      if (paragraph.isEmpty()) {
        result.add(EMPTY_LINE);
        width = continuationWidth;
        continue;
      }
      String remaining = paragraph;
      while (!remaining.isEmpty()) {
        int codePoints = remaining.codePointCount(0, remaining.length());
        if (codePoints <= width) {
          result.add(remaining);
          remaining = "";
        } else {
          int end = remaining.offsetByCodePoints(0, width);
          int breakAt = preferredBreak(remaining, end);
          result.add(remaining.substring(0, breakAt).stripTrailing());
          remaining = remaining.substring(breakAt).stripLeading();
        }
        width = continuationWidth;
      }
    }
    return List.copyOf(result);
  }

  /**
   * 在最大截断位置之前优先寻找空白字符，避免切断英文单词。
   */
  private static int preferredBreak(String text, int maximumEnd) {
    for (int index = maximumEnd; index > 0; index = text.offsetByCodePoints(index, -1)) {
      if (Character.isWhitespace(text.codePointBefore(index))) return index;
    }
    return maximumEnd;
  }

  /**
   * 获取指定行；缺失行使用不可见占位文本补齐。
   */
  private static String lineAt(List<String> lines, int index) {
    return index < lines.size() ? lines.get(index) : EMPTY_LINE;
  }

  /**
   * 校验提示原文非空且不是 {@code null}。
   */
  private static void requireText(String value) {
    Objects.requireNonNull(value, "tooltip 文本不能为空");
    if (value.isEmpty()) throw new IllegalArgumentException("tooltip 文本不能为空");
  }
}
