package com.cxxcxx.zinecraft.api.weapon.action.melee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MeleeAttackActionTest {
  @Test
  void usesSwordFinalAttackDamageWithoutApplyingItsBaseTwice() {
    assertEquals(7.0F, MeleeDamage.resolveActionMeleeDamage(7.0F, 7.0));
    assertEquals(8.05F, MeleeDamage.resolveActionMeleeDamage(7.0F, 8.05), 0.0001F);
  }

  @Test
  void keepsDeclaredDamageWhenAttributeDataIsUnavailable() {
    assertEquals(7.0F, MeleeDamage.resolveActionMeleeDamage(7.0F, null));
    assertEquals(7.0F, MeleeDamage.resolveActionMeleeDamage(7.0F, Double.NaN));
  }
}
