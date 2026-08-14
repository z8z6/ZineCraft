package com.cxxcxx.zinecraft.api.localization

/** Collects localized text that is emitted by the language data providers. */
class TranslationCatalog {
  internal val entries = linkedMapOf<String, LocalizedText>()

  fun add(key: String, zhCn: String, enUs: String = zhCn) {
    val text = validatedText(key, zhCn, enUs)
    val previous = entries.putIfAbsent(key, text)
    require(previous == null || previous == text) { "翻译键重复且内容冲突：$key；需要覆盖时请显式调用 replace" }
  }

  /** 显式替换已注册翻译，避免拼写错误把普通新增静默变成覆盖。 */
  fun replace(key: String, zhCn: String, enUs: String = zhCn) {
    val text = validatedText(key, zhCn, enUs)
    require(entries.containsKey(key)) { "不能替换尚未注册的翻译键：$key" }
    entries[key] = text
  }

  private fun validatedText(key: String, zhCn: String, enUs: String): LocalizedText {
    require(key.matches(Regex("[A-Za-z0-9_.:/-]+"))) { "翻译键格式无效：$key" }
    require(zhCn.isNotBlank()) { "中文翻译不能为空：$key" }
    require(enUs.isNotBlank()) { "英文翻译不能为空：$key" }
    return LocalizedText(zhCn, enUs)
  }
}

data class LocalizedText(val zhCn: String, val enUs: String)

internal fun String.toDisplayName(): String =
  split('_', '.').filter(String::isNotEmpty).joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  }
