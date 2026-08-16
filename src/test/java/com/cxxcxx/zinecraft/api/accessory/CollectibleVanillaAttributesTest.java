package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectibleVanillaAttributesTest {
  @Test
  void attackPercentageKeepsItsMultiplier() {
    var modifier = CombatStatModifier.collectibleMultiplier(CombatStat.ATTACK, 0.25);

    assertEquals(0.25, CollectibleVanillaAttributes.amount(modifier));
  }

  @Test
  void attackSpeedPointsBecomeVanillaSpeedMultiplier() {
    var modifier = CombatStatModifier.collectibleAddition(CombatStat.ATTACK_SPEED, 30.0);

    assertEquals(0.30, CollectibleVanillaAttributes.amount(modifier));
  }

  @Test
  void healthAdditionRemainsAFlatVanillaValue() {
    var modifier = CombatStatModifier.collectibleAddition(CombatStat.MAX_HEALTH, 5.0);

    assertEquals(5.0, CollectibleVanillaAttributes.amount(modifier));
  }
}
