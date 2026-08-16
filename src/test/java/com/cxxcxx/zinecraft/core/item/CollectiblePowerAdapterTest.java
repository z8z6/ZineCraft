package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class CollectiblePowerAdapterTest {
  @Test
  void mapsUnconditionalCombatValuesWithoutChangingAttackSpeedIntoPercent() {
    var adaptation = CollectiblePowerAdapter.adapt("所有【近卫】干员的防御力-40%，但攻击力+40%，攻击速度+30");
    var set = assertInstanceOf(CollectiblePower.CombatStatSet.class, adaptation.power());
    assertTrue(set.boosts().stream().map(CollectiblePower.CombatStatBoost::modifier)
        .anyMatch(modifier -> modifier.equals(CombatStatModifier.collectibleAddition(CombatStat.ATTACK_SPEED, 30.0))));
  }

  @Test
  void mapsBothRegenerationForms() {
    assertInstanceOf(CollectiblePower.Regeneration.class,
        CollectiblePowerAdapter.adapt("所有我方单位每秒回复2%的最大生命值").power());
    var flat = assertInstanceOf(CollectiblePower.FlatRegeneration.class,
        CollectiblePowerAdapter.adapt("所有我方单位每秒回复10点生命").power());
    assertEquals(10.0F, flat.health());
  }

  @Test
  void registersEveryCatalogRuleAndNeverFallsBackToArchiveOnly() throws Exception {
    try (var input = getClass().getResourceAsStream("/zinecraft/collectibles/phantom_crimson_solitaire.json")) {
      var catalog = JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonArray();
      assertEquals(245, catalog.size());
      int runtimeRules = 0;
      for (var element : catalog) {
        String rule = element.getAsJsonObject().get("originalEffectZhCn").getAsString();
        var power = CollectiblePowerAdapter.adapt(rule).power();
        assertFalse(power instanceof CollectiblePower.ArchiveOnly);
        if (!(power instanceof CollectiblePower.SourceRule)) runtimeRules++;
      }
      assertTrue(runtimeRules >= 25, "Expected the catalog to contain a substantial runtime-mapped subset");
    }
  }
}
