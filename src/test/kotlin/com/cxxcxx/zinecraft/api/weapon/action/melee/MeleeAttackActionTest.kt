package com.cxxcxx.zinecraft.api.weapon.action.melee

import kotlin.test.Test
import kotlin.test.assertEquals

class MeleeAttackActionTest {
  @Test
  fun `uses the sword final attack damage without applying its base twice`() {
    assertEquals(7.0f, resolveActionMeleeDamage(7.0f, 7.0))
    assertEquals(8.05f, resolveActionMeleeDamage(7.0f, 8.05), absoluteTolerance = 0.0001f)
  }

  @Test
  fun `keeps declared damage when attribute data is unavailable`() {
    assertEquals(7.0f, resolveActionMeleeDamage(7.0f, null))
    assertEquals(7.0f, resolveActionMeleeDamage(7.0f, Double.NaN))
  }
}
