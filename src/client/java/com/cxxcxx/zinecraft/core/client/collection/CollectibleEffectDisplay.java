package com.cxxcxx.zinecraft.core.client.collection;

import com.cxxcxx.zinecraft.api.collection.CollectibleItem;
import com.cxxcxx.zinecraft.api.collection.CollectibleSpecialCondition;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;

/**
 * 将已装备藏品的结构化探索效果转换为能力页文本及逐乘区悬浮详情。
 */
public final class CollectibleEffectDisplay {
  private static final String PREFIX = "menu.tabs.attribute.collectible_effects.";
  private static final String ATTRIBUTE_PREFIX = "menu.tabs.attribute.";
  private static final DecimalFormat NUMBER = new DecimalFormat("0.##");
  private static final double EPSILON = 1.0E-9;

  private CollectibleEffectDisplay() {
  }

  /** 一行探索属性及与 L2Tabs 原属性一致的悬浮详情。 */
  public record Entry(
      Component line,
      Component name,
      double base,
      double addition,
      double multipliedBase,
      double multipliedTotal,
      double finalValue,
      String unit,
      List<Contribution> additions,
      List<Contribution> totalMultipliers
  ) {
    public Entry {
      additions = List.copyOf(additions);
      totalMultipliers = List.copyOf(totalMultipliers);
    }

    public List<Component> tooltip(boolean showDetails) {
      List<Component> tooltip = new ArrayList<>();
      tooltip.add(name.copy().withStyle(ChatFormatting.GOLD));
      tooltip.add(Component.translatable(
          ATTRIBUTE_PREFIX + "base", number(base, unit, false)
      ).withStyle(ChatFormatting.BLUE));
      tooltip.add(Component.translatable(
          ATTRIBUTE_PREFIX + "add", number(addition, unit, true)
      ).withStyle(ChatFormatting.BLUE));
      if (showDetails) additions.forEach(value -> tooltip.add(value.additionLine(unit)));
      tooltip.add(Component.translatable(
          ATTRIBUTE_PREFIX + "mult_base", number(multipliedBase, "%", true)
      ).withStyle(ChatFormatting.BLUE));
      tooltip.add(Component.translatable(
          ATTRIBUTE_PREFIX + "mult_all", factor(multipliedTotal)
      ).withStyle(ChatFormatting.BLUE));
      if (showDetails) totalMultipliers.forEach(value -> tooltip.add(value.multiplierLine()));
      tooltip.add(Component.translatable(
          ATTRIBUTE_PREFIX + "format",
          number(base, unit, false),
          number(addition, unit, true),
          number(multipliedBase, "%", true),
          number(multipliedTotal, "", false),
          number(finalValue, unit, false)
      ));
      if (!showDetails) {
        tooltip.add(Component.translatable(ATTRIBUTE_PREFIX + "detail").withStyle(ChatFormatting.GRAY));
      }
      return List.copyOf(tooltip);
    }
  }

  private record Contribution(double value, String collectibleName) {
    private Component additionLine(String unit) {
      return number(value, unit, true).append(source());
    }

    private Component multiplierLine() {
      return factor(value).append(source());
    }

    private Component source() {
      return Component.literal(" [" + collectibleName + "]").withStyle(ChatFormatting.DARK_GRAY);
    }
  }

  private record Step(String collectibleName, CombatStat before, CombatStat after) {
  }

  private record Trace(CombatStat finalStats, List<Step> steps) {
  }

  public static List<Entry> entries(LivingEntity entity) {
    Trace trace = trace(entity);
    List<Entry> result = new ArrayList<>();
    addValue(result, trace, "hope", CombatStat::hope);
    addValue(result, trace, "originium_ingots", CombatStat::originiumIngots);
    addValue(result, trace, "action_points", CombatStat::actionPoints);
    addValue(result, trace, "anti_interference", CombatStat::antiInterferenceIndex);
    addValue(result, trace, "collapse", CombatStat::collapseValue);
    addValue(result, trace, "mental_burden_limit", CombatStat::mentalBurdenLimit);
    addValue(result, trace, "thoughts", CombatStat::thoughts);
    addValue(result, trace, "candles", CombatStat::candles);
    addValue(result, trace, "squad_capacity", CombatStat::squadCapacity);
    addValue(result, trace, "deployment_limit", CombatStat::deploymentLimit);
    addValue(result, trace, "initial_deployment_points", CombatStat::initialDeploymentPoints);
    addValue(result, trace, "keys", CombatStat::keys);
    addValue(result, trace, "dice", CombatStat::dice);
    addValue(result, trace, "light", CombatStat::light);
    addMultiplier(result, trace, "command_experience", CombatStat::commandExperienceMultiplier);
    addValue(result, trace, "hope_per_node", CombatStat::hopePerNonCombatNode);
    addValue(result, trace, "ingots_per_node", CombatStat::originiumIngotsPerNonCombatNode);
    addMultiplier(result, trace, "battle_ingots", CombatStat::battleOriginiumIngotMultiplier);
    addValue(result, trace, "failure_recovery", CombatStat::oneTimeFailureRecoveryObjectiveLife);
    return List.copyOf(result);
  }

  private static Trace trace(LivingEntity entity) {
    Objects.requireNonNull(entity, "entity");
    List<ItemStack> stacks = new ArrayList<>();
    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> stack.getItem() instanceof CollectibleItem
    ).forEach(slot -> stacks.add(slot.stack())));

    CombatStat current = CombatStat.EMPTY.withCollectibleEffectTier(
        CollectibleSpecialCondition.tier(entity)
    );
    List<Step> steps = new ArrayList<>();
    for (ItemStack stack : stacks) {
      if (!(stack.getItem() instanceof CollectibleItem item)) continue;
      CombatStat before = current;
      current = Objects.requireNonNull(
          item.collectible().power.apply(before),
          "藏品效果不能返回 null：" + item.collectible().path
      );
      steps.add(new Step(stack.getHoverName().getString(), before, current));
    }
    return new Trace(current, List.copyOf(steps));
  }

  private static void addValue(
      List<Entry> entries,
      Trace trace,
      String key,
      ToDoubleFunction<CombatStat> getter
  ) {
    double finalValue = getter.applyAsDouble(trace.finalStats());
    List<Contribution> additions = new ArrayList<>();
    for (Step step : trace.steps()) {
      double value = getter.applyAsDouble(step.after()) - getter.applyAsDouble(step.before());
      if (!approximately(value, 0.0)) {
        additions.add(new Contribution(value, step.collectibleName()));
      }
    }
    Component name = Component.translatable(PREFIX + key);
    entries.add(new Entry(
        line(name, finalValue, ""),
        name,
        0.0,
        finalValue,
        0.0,
        1.0,
        finalValue,
        "",
        additions,
        List.of()
    ));
  }

  private static void addMultiplier(
      List<Entry> entries,
      Trace trace,
      String key,
      ToDoubleFunction<CombatStat> getter
  ) {
    double finalMultiplier = getter.applyAsDouble(trace.finalStats());
    List<Contribution> multipliers = new ArrayList<>();
    for (Step step : trace.steps()) {
      double before = getter.applyAsDouble(step.before());
      double after = getter.applyAsDouble(step.after());
      double factor = approximately(before, 0.0) ? 1.0 : after / before;
      if (!approximately(factor, 1.0)) {
        multipliers.add(new Contribution(factor, step.collectibleName()));
      }
    }
    Component name = Component.translatable(PREFIX + key);
    entries.add(new Entry(
        line(name, (finalMultiplier - 1.0) * 100.0, "%"),
        name,
        100.0,
        0.0,
        0.0,
        finalMultiplier,
        finalMultiplier * 100.0,
        "%",
        List.of(),
        multipliers
    ));
  }

  private static Component line(Component name, double value, String unit) {
    return Component.literal(format(value) + unit + " ").append(name);
  }

  private static MutableComponent number(double value, String unit, boolean signed) {
    double normalized = normalize(value);
    String prefix = signed && normalized >= 0.0 ? "+" : "";
    return Component.literal(prefix + format(normalized) + unit)
        .withStyle(normalized >= 0.0 ? ChatFormatting.GREEN : ChatFormatting.RED);
  }

  private static MutableComponent factor(double value) {
    return Component.literal("x" + format(value)).withStyle(ChatFormatting.GREEN);
  }

  private static String format(double value) {
    return NUMBER.format(normalize(value));
  }

  private static double normalize(double value) {
    return approximately(value, 0.0) ? 0.0 : value;
  }

  private static boolean approximately(double left, double right) {
    return Math.abs(left - right) <= EPSILON;
  }
}
