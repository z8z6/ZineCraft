package com.cxxcxx.zinecraft.api.combat;

import com.cxxcxx.zinecraft.api.collection.CollectiblePower;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CombatStatTest {
  @Test
  void collectibleEffectsComposeAsStatFunctions() {
    CombatStat base = CombatStat.EMPTY
        .withAttack(100.0)
        .withDefense(80.0)
        .withAttackSpeed(100.0);
    CollectiblePower effect = CollectiblePower.combine(
        stats -> stats.multiplyAttack(0.25).addAttackSpeed(30.0).hope(2),
        stats -> stats.multiplyAttack(0.20).addDefense(15.0).hope(1)
    );

    CombatStat result = effect.apply(base);

    assertEquals(150.0, result.attack());
    assertEquals(95.0, result.defense());
    assertEquals(130.0, result.attackSpeed());
    assertEquals(3, result.hope());
  }

  @Test
  void identityEffectReturnsTheSameSnapshot() {
    assertSame(CombatStat.EMPTY, CollectiblePower.NONE.apply(CombatStat.EMPTY));
  }

  @Test
  void triggeredEffectsAreRegisteredSeparatelyFromNumericValues() {
    CombatStat.PerSecondEffect perSecond = entity -> {};
    CombatStat.PerSecondConditionalEffect perSecondConditional = current -> current;
    CombatStat.KillEffect onKill = (killer, killed) -> {};

    CombatStat result = CombatStat.EMPTY
        .hope(2)
        .addPerSecondEffect(perSecond)
        .addPerSecondConditionalEffect(perSecondConditional)
        .addKillEffect(onKill);

    assertEquals(2, result.hope());
    assertEquals(1, result.perSecondEffects().size());
    assertSame(perSecond, result.perSecondEffects().getFirst());
    assertEquals(1, result.perSecondConditionalEffects().size());
    assertSame(perSecondConditional, result.perSecondConditionalEffects().getFirst());
    assertEquals(1, result.killEffects().size());
    assertSame(onKill, result.killEffects().getFirst());
    assertEquals(0, CombatStat.EMPTY.perSecondEffects().size());
    assertEquals(0, CombatStat.EMPTY.perSecondConditionalEffects().size());
    assertEquals(0, CombatStat.EMPTY.killEffects().size());
  }

  @Test
  void damageReductionAndDefenseIgnoreAccumulateBeforeLimiting() {
    CollectiblePower combined = CollectiblePower.combine(
        stats -> stats.addDamageReduction(0.07).addDefenseIgnore(0.12),
        stats -> stats.addDamageReduction(0.12).addDefenseIgnore(0.21),
        stats -> stats.addDamageReduction(0.17).addDefenseIgnore(0.30)
    );

    CombatStat accumulated = combined.apply(CombatStat.EMPTY);

    assertEquals(0.36, accumulated.damageReduction(), 1.0E-9);
    assertEquals(0.63, accumulated.defenseIgnore(), 1.0E-9);
    assertEquals(1.0, accumulated.addDamageReduction(1.0).limited().damageReduction());
    assertEquals(1.0, accumulated.addDefenseIgnore(1.0).limited().defenseIgnore());
  }

  @Test
  void healingAndSkillPointRegenerationBonusesAccumulate() {
    CombatStat result = CombatStat.EMPTY
        .addHealingAndHealthRegenerationBonus(0.2)
        .addHealingAndHealthRegenerationBonus(0.3)
        .addOffensiveDefensiveSkillPointRegeneration(1.0 / 3.5)
        .addOffensiveDefensiveSkillPointRegeneration(1.0 / 3.0)
        .addNaturalSkillPointRegeneration(0.2)
        .addNaturalSkillPointRegeneration(0.5);

    assertEquals(0.5, result.healingAndHealthRegenerationBonus(), 1.0E-9);
    assertEquals(1.0 / 3.5 + 1.0 / 3.0,
        result.offensiveDefensiveSkillPointRegeneration(), 1.0E-9);
    assertEquals(0.7, result.naturalSkillPointRegeneration(), 1.0E-9);
  }

  @Test
  void damageAndElementalBonusesAccumulate() {
    CombatStat result = CombatStat.EMPTY
        .addTrueDamageBonus(1.0)
        .addTrueDamageBonus(0.5)
        .addElementalDamageBonus(0.2)
        .addElementalDamageBonus(0.35)
        .addElementalDamageReduction(0.15)
        .addElementalDamageReduction(0.25)
        .addPhysicalDamageEvasionRate(0.15)
        .addPhysicalDamageEvasionRate(0.10)
        .addMagicDamageEvasionRate(0.15)
        .addMagicDamageEvasionRate(0.10);

    assertEquals(1.5, result.trueDamageBonus(), 1.0E-9);
    assertEquals(0.55, result.elementalDamageBonus(), 1.0E-9);
    assertEquals(0.4, result.elementalDamageReduction(), 1.0E-9);
    assertEquals(0.25, result.physicalDamageEvasionRate(), 1.0E-9);
    assertEquals(0.25, result.magicDamageEvasionRate(), 1.0E-9);
    assertEquals(1.0, result.addElementalDamageReduction(1.0).limited().elementalDamageReduction());
    assertEquals(1.0, result.addPhysicalDamageEvasionRate(1.0).limited().physicalDamageEvasionRate());
    assertEquals(1.0, result.addMagicDamageEvasionRate(1.0).limited().magicDamageEvasionRate());
  }

  @Test
  void statusDurationBonusesAccumulateAndResolveToTicks() {
    CombatStat result = CombatStat.EMPTY
        .addEnemyStatusDurationBonus(1.0)
        .addEnemyStatusDurationBonus(1.1)
        .addFriendlyStatusDurationReduction(0.25)
        .addFriendlyStatusDurationReduction(0.50);

    assertEquals(2.1, result.enemyStatusDurationBonus(), 1.0E-9);
    assertEquals(0.75, result.friendlyStatusDurationReduction(), 1.0E-9);
    assertEquals(62, result.enemyStatusDurationTicks(20));
    assertEquals(5, result.friendlyStatusDurationTicks(20));
    assertEquals(0, result.addFriendlyStatusDurationReduction(1.0)
        .friendlyStatusDurationTicks(20));
  }

  @Test
  void enemyMovementAndWeightEffectsAccumulateBeforeLimiting() {
    CombatStat result = CombatStat.EMPTY
        .addEnemyMovementSpeedReduction(0.15)
        .addEnemyMovementSpeedReduction(0.20)
        .addEnemyWeightIgnore(1)
        .addEnemyWeightIgnore(2);

    assertEquals(0.35, result.enemyMovementSpeedReduction(), 1.0E-9);
    assertEquals(3, result.enemyWeightIgnore());
    assertEquals(1.0, result.addEnemyMovementSpeedReduction(1.0)
        .limited().enemyMovementSpeedReduction());
  }

  @Test
  void perSecondConditionsReevaluateAgainstTheCurrentSnapshot() {
    CombatStat conditional = CombatStat.EMPTY.addPerSecondConditionalEffect(current ->
        Double.compare(current.maxHealth(), 1.0) == 0
            ? current.addAttackSpeed(50.0)
            : current
    );

    CombatStat active = conditional.withMaxHealth(1.0).withAttackSpeed(100.0)
        .evaluatePerSecondConditionalEffects();
    CombatStat inactive = conditional.withMaxHealth(2.0).withAttackSpeed(100.0)
        .evaluatePerSecondConditionalEffects();

    assertEquals(150.0, active.attackSpeed());
    assertEquals(100.0, inactive.attackSpeed());
  }

  @Test
  void explorationMultipliersAndResourcesRemainInTheSameSnapshot() {
    CombatStat result = CombatStat.EMPTY
        .hope(2)
        .hope(3)
        .commandExperienceMultiplier(1.2)
        .commandExperienceMultiplier(1.5)
        .battleOriginiumIngotMultiplier(1.25);

    assertEquals(5, result.hope());
    assertEquals(1.8, result.commandExperienceMultiplier(), 1.0E-9);
    assertEquals(1.25, result.battleOriginiumIngotMultiplier(), 1.0E-9);
  }
}
