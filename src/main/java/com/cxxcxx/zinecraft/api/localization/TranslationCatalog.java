package com.cxxcxx.zinecraft.api.localization;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public final class TranslationCatalog {
  private static final Pattern KEY = Pattern.compile("[A-Za-z0-9_.:/-]+");
  private final LinkedHashMap<String, LocalizedText> entries = new LinkedHashMap<>();

  public static void addWithDefaults(TranslationCatalog self, String key, String zhCn, String enUs, int mask, Object marker) {
    self.add(key, zhCn, (mask & 4) != 0 ? zhCn : enUs);
  }

  public static void replaceWithDefaults(TranslationCatalog self, String key, String zhCn, String enUs, int mask, Object marker) {
    self.replace(key, zhCn, (mask & 4) != 0 ? zhCn : enUs);
  }

  public LinkedHashMap<String, LocalizedText> getEntries() {
    return entries;
  }

  public void add(String key, String zhCn, String enUs) {
    var text = validatedText(key, zhCn, enUs);
    var previous = entries.putIfAbsent(key, text);
    if (previous != null && !previous.equals(text)) throw new IllegalArgumentException("翻译键重复且内容冲突：" + key);
  }

  public void replace(String key, String zhCn, String enUs) {
    var text = validatedText(key, zhCn, enUs);
    if (!entries.containsKey(key)) throw new IllegalArgumentException("不能替换尚未注册的翻译键：" + key);
    entries.put(key, text);
  }

  private LocalizedText validatedText(String key, String zhCn, String enUs) {
    if (!KEY.matcher(key).matches()) throw new IllegalArgumentException("翻译键格式无效：" + key);
    if (zhCn.isBlank()) throw new IllegalArgumentException("中文翻译不能为空：" + key);
    if (enUs.isBlank()) throw new IllegalArgumentException("英文翻译不能为空：" + key);
    return new LocalizedText(zhCn, enUs);
  }
}
