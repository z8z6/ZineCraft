package com.cxxcxx.zinecraft.api.combat;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CombatFormulasTest {
  @Test
  void followsArknightsAttributeOrderAndCollectibleStacking() {
    var modifiers = List.of(
        CombatStatModifier.collectibleMultiplier(CombatStat.ATTACK, 0.15),
        CombatStatModifier.collectibleMultiplier(CombatStat.ATTACK, 0.25),
        CombatStatModifier.directAddition(CombatStat.ATTACK, 10.0),
        CombatStatModifier.directMultiplier(CombatStat.ATTACK, 0.5),
        CombatStatModifier.finalAddition(CombatStat.ATTACK, 5.0),
        CombatStatModifier.finalScaler(CombatStat.ATTACK, 0.8)
    );
    assertEquals(184.0, CombatStatFormula.resolve(CombatStat.ATTACK, 100.0, modifiers), 0.0001);
  }

  @Test
  void appliesPhysicalArtsTrueAndFivePercentFloor() {
    assertEquals(60.0, CombatFormulas.damage(CombatDamageType.PHYSICAL, 100.0, 40.0, CombatRequest.DEFAULT));
    assertEquals(5.0, CombatFormulas.damage(CombatDamageType.PHYSICAL, 100.0, 500.0, CombatRequest.DEFAULT));
    assertEquals(60.0, CombatFormulas.damage(CombatDamageType.ARTS, 100.0, 40.0, CombatRequest.DEFAULT));
    assertEquals(5.0, CombatFormulas.damage(CombatDamageType.ARTS, 100.0, 500.0, CombatRequest.DEFAULT));
    assertEquals(100.0, CombatFormulas.damage(CombatDamageType.TRUE, 100.0, 500.0, CombatRequest.DEFAULT));
  }

  @Test
  void appliesPenetrationHealingAndAttackSpeed() {
    var penetration = new CombatRequest(1.0, 0.0, 0.5, 20.0, 1.2);
    assertEquals(84.0, CombatFormulas.damage(CombatDamageType.PHYSICAL, 100.0, 80.0, penetration), 0.0001);
    assertEquals(50.0, CombatFormulas.healing(100.0, 0.5, 0.0));
    assertEquals(1.0, CombatFormulas.attackInterval(1.7, 170.0), 0.0001);
    assertEquals(1.0 / 6.0, CombatFormulas.attackInterval(1.0, 600.0), 0.0001);
    assertEquals(new CombatActionTiming(4, 12), CombatActionTiming.scale(7, 20, 170.0));
    assertEquals(new CombatActionTiming(35, 100), CombatActionTiming.scale(7, 20, 20.0));
  }
}
