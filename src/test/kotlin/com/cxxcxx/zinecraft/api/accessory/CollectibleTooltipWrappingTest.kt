package com.cxxcxx.zinecraft.api.accessory

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CollectibleTooltipWrappingTest {
  @Test
  fun `pairs different localization lengths without changing visible text`() {
    val zhCn = "短中文原文"
    val enUs = "A deliberately longer English localization that needs several independently rendered tooltip lines."
    val lines = wrapLocalizedTooltip(zhCn, enUs, firstLineCharacters = 24, continuationCharacters = 42)

    assertTrue(lines.size > 1)
    assertEquals(zhCn, lines.joinToString("") { it.zhCn }.replace("\u200B", ""))
    assertEquals(enUs, lines.joinToString("") { it.enUs }.replace("\u200B", ""))
    assertTrue(lines.first().zhCn.codePointCount(0, lines.first().zhCn.length) <= 24)
    assertTrue(lines.first().enUs.codePointCount(0, lines.first().enUs.length) <= 24)
    assertTrue(lines.drop(1).all { it.zhCn.length <= 42 && it.enUs.length <= 42 })
  }
}
