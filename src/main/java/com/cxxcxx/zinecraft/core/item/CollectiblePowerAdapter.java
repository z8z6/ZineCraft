package com.cxxcxx.zinecraft.core.item;

import com.cxxcxx.zinecraft.api.accessory.CollectiblePower;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Converts unconditional PRTS combat wording without inventing equivalents for exploration-only rules.
 */
final class CollectiblePowerAdapter {
  private static final Pattern PERCENT_REGEN = Pattern.compile("每秒回复(\\d+(?:\\.\\d+)?)%的最大生命值");
  private static final Pattern FLAT_REGEN = Pattern.compile("每秒回复(\\d+(?:\\.\\d+)?)点生命");
  private static final List<String> CONDITIONAL_MARKERS = List.of(
      "时", "后", "每有", "范围内", "周围", "战斗编队", "目标生命", "阻挡", "部署过", "技能未开启"
  );

  private CollectiblePowerAdapter() {
  }

  static Adaptation adapt(String originalRule) {
    var percentRegen = PERCENT_REGEN.matcher(originalRule);
    if (percentRegen.find() && unconditionalFriendly(originalRule)) {
      double percent = Double.parseDouble(percentRegen.group(1));
      return runtime(new CollectiblePower.Regeneration((float) (percent / 100.0), 20));
    }
    var flatRegen = FLAT_REGEN.matcher(originalRule);
    if (flatRegen.find() && unconditionalFriendly(originalRule)) {
      return runtime(new CollectiblePower.FlatRegeneration(Float.parseFloat(flatRegen.group(1)), 20));
    }

    if (!unconditionalFriendly(originalRule)) return source(originalRule);
    List<CollectiblePower.CombatStatBoost> boosts = new ArrayList<>();
    addGroupedPercent(originalRule, "攻击力、防御力、生命", boosts, CombatStat.ATTACK, CombatStat.DEFENSE, CombatStat.MAX_HEALTH);
    addGroupedPercent(originalRule, "攻击力和防御力", boosts, CombatStat.ATTACK, CombatStat.DEFENSE);
    addPercent(originalRule, "攻击力", CombatStat.ATTACK, boosts);
    addPercent(originalRule, "防御力", CombatStat.DEFENSE, boosts);
    addPercent(originalRule, "最大生命值", CombatStat.MAX_HEALTH, boosts);
    if (!originalRule.contains("最大生命值")) addPercent(originalRule, "生命", CombatStat.MAX_HEALTH, boosts);
    addFlat(originalRule, "法术抗性", CombatStat.RESISTANCE, boosts);
    addFlat(originalRule, "攻击速度", CombatStat.ATTACK_SPEED, boosts);
    if (boosts.isEmpty()) return source(originalRule);
    CollectiblePower power = boosts.size() == 1 ? boosts.getFirst() : new CollectiblePower.CombatStatSet(boosts);
    return runtime(power);
  }

  private static boolean unconditionalFriendly(String rule) {
    if (rule.contains("敌方") || rule.contains("敌人")) return false;
    if (CONDITIONAL_MARKERS.stream().anyMatch(rule::contains)) return false;
    return rule.startsWith("所有我方") || rule.startsWith("所有干员") || rule.startsWith("所有【")
        || rule.startsWith("所有近卫") || rule.startsWith("所有辅助");
  }

  private static void addGroupedPercent(
      String rule,
      String phrase,
      List<CollectiblePower.CombatStatBoost> boosts,
      CombatStat... stats
  ) {
    var matcher = Pattern.compile(Pattern.quote(phrase) + "([+-])(\\d+(?:\\.\\d+)?)%").matcher(rule);
    if (!matcher.find()) return;
    double amount = signed(matcher.group(1), matcher.group(2)) / 100.0;
    for (CombatStat stat : stats) boosts.add(percent(stat, amount));
  }

  private static void addPercent(String rule, String label, CombatStat stat, List<CollectiblePower.CombatStatBoost> boosts) {
    if (rule.contains("攻击力、防御力、生命") || (rule.contains("攻击力和防御力") && (stat == CombatStat.ATTACK || stat == CombatStat.DEFENSE)))
      return;
    var matcher = Pattern.compile(Pattern.quote(label) + "([+-])(\\d+(?:\\.\\d+)?)%").matcher(rule);
    if (matcher.find()) boosts.add(percent(stat, signed(matcher.group(1), matcher.group(2)) / 100.0));
  }

  private static void addFlat(String rule, String label, CombatStat stat, List<CollectiblePower.CombatStatBoost> boosts) {
    var matcher = Pattern.compile(Pattern.quote(label) + "([+-])(\\d+(?:\\.\\d+)?)(?=[，。；、]|$)").matcher(rule);
    if (matcher.find()) boosts.add(flat(stat, signed(matcher.group(1), matcher.group(2))));
  }

  private static double signed(String sign, String number) {
    double value = Double.parseDouble(number);
    return "-".equals(sign) ? -value : value;
  }

  private static CollectiblePower.CombatStatBoost percent(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleMultiplier(stat, amount));
  }

  private static CollectiblePower.CombatStatBoost flat(CombatStat stat, double amount) {
    return new CollectiblePower.CombatStatBoost(CombatStatModifier.collectibleAddition(stat, amount));
  }

  private static Adaptation runtime(CollectiblePower power) {
    return new Adaptation("按 PRTS 原数值应用于装备者", "Applied to the wearer using the original PRTS value", power);
  }

  private static Adaptation source(String rule) {
    return new Adaptation(
        "已登记原始探索规则；等待对应的节点、招募或部署系统触发",
        "Original exploration rule registered; requires its matching node, recruitment or deployment system",
        new CollectiblePower.SourceRule(rule)
    );
  }

  record Adaptation(String zhCn, String enUs, CollectiblePower power) {
  }
}
