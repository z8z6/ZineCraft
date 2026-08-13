package com.cxxcxx.zinecraft.api.localization

/** Collects localized text that is emitted by the language data providers. */
class TranslationCatalog {
  internal val entries = linkedMapOf<String, LocalizedText>()

  fun add(key: String, zhCn: String, enUs: String = zhCn) {
    entries[key] = LocalizedText(zhCn, enUs)
  }
}

data class LocalizedText(val zhCn: String, val enUs: String)

internal fun String.toDisplayName(): String =
  split('_', '.').filter(String::isNotEmpty).joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  }
