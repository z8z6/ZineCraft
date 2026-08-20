package com.cxxcxx.zinecraft.api.registry.catalog;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 收集模组的本地化文本，并校验翻译键及中英文内容。
 */
public final class TranslationCatalog {
  private static final Pattern KEY = Pattern.compile("[A-Za-z0-9_.:/-]+");
  private final LinkedHashMap<String, LocalizedText> entries = new LinkedHashMap<>();

  /**
   * 将以下划线或点分隔的注册路径转换为英文展示名称。
   *
   * @param value 注册路径或翻译键片段
   * @return 各单词首字母大写并以空格连接的名称
   */
  public static String toDisplayName(String value) {
    return Arrays.stream(value.split("[_.]"))
        .filter(word -> !word.isEmpty())
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
        .collect(Collectors.joining(" "));
  }

  /**
   * 返回按登记顺序保存的可变翻译映射。
   *
   * @return 翻译键到中英文文本的映射
   */
  public LinkedHashMap<String, LocalizedText> getEntries() {
    return entries;
  }

  /**
   * 添加翻译；相同键和内容可重复登记，内容冲突时抛出异常。
   *
   * @param key  翻译键
   * @param zhCn 简体中文文本
   * @param enUs 英文文本
   */
  public void add(String key, String zhCn, String enUs) {
    var text = validatedText(key, zhCn, enUs);
    var previous = entries.putIfAbsent(key, text);
    if (previous != null && !previous.equals(text)) throw new IllegalArgumentException("翻译键重复且内容冲突：" + key);
  }

  /**
   * 替换一个已经登记的翻译。
   *
   * @param key 翻译键
   * @param zhCn 新的简体中文文本
   * @param enUs 新的英文文本
   */
  public void replace(String key, String zhCn, String enUs) {
    var text = validatedText(key, zhCn, enUs);
    if (!entries.containsKey(key)) throw new IllegalArgumentException("不能替换尚未注册的翻译键：" + key);
    entries.put(key, text);
  }

  /**
   * 校验翻译键和文本，并创建本地化文本值。
   *
   * @param key 翻译键
   * @param zhCn 简体中文文本
   * @param enUs 英文文本
   * @return 通过校验的本地化文本
   */
  private LocalizedText validatedText(String key, String zhCn, String enUs) {
    if (!KEY.matcher(key).matches()) throw new IllegalArgumentException("翻译键格式无效：" + key);
    if (zhCn.isBlank()) throw new IllegalArgumentException("中文翻译不能为空：" + key);
    if (enUs.isBlank()) throw new IllegalArgumentException("英文翻译不能为空：" + key);
    return new LocalizedText(zhCn, enUs);
  }

  /**
   * 一条翻译的简体中文和英文文本。
   *
   * @param zhCn 简体中文文本
   * @param enUs 英文文本
   */
  public record LocalizedText(String zhCn, String enUs) {
    /** @return 简体中文文本 */
    public String getZhCn() {
      return zhCn;
    }

    /** @return 英文文本 */
    public String getEnUs() {
      return enUs;
    }
  }
}
