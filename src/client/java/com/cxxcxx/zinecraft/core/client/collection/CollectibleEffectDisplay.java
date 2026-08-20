package com.cxxcxx.zinecraft.core.client.collection;

import com.cxxcxx.zinecraft.api.collection.CollectibleExplorationEffects;
import com.cxxcxx.zinecraft.api.collection.CollectiblePower;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 将已装备藏品的结构化探索效果转换为能力页文本。
 */
public final class CollectibleEffectDisplay {
  private static final String PREFIX = "menu.tabs.attribute.collectible_effects.";
  private static final DecimalFormat NUMBER = new DecimalFormat("0.##");

  private CollectibleEffectDisplay() {
  }

  public static List<Component> lines(LivingEntity entity) {
    CollectiblePower.Exploration effect = CollectibleExplorationEffects.equipped(entity);
    if (effect.isEmpty()) return List.of(Component.translatable(PREFIX + "none").withStyle(ChatFormatting.GRAY));

    List<Component> result = new ArrayList<>();
    addValue(result, "hope", effect.hope());
    addValue(result, "objective_life", effect.objectiveLife());
    addValue(result, "temporary_objective_life", effect.temporaryObjectiveLife());
    addValue(result, "originium_ingots", effect.originiumIngots());
    addValue(result, "squad_capacity", effect.squadCapacity());
    addValue(result, "deployment_limit", effect.deploymentLimit());
    addValue(result, "initial_deployment_points", effect.initialDeploymentPoints());
    addValue(result, "keys", effect.keys());
    addValue(result, "dice", effect.dice());
    addValue(result, "light", effect.light());
    addMultiplier(result, "command_experience", effect.commandExperienceMultiplier());
    addValue(result, "hope_per_node", effect.hopePerNonCombatNode());
    addValue(result, "ingots_per_node", effect.originiumIngotsPerNonCombatNode());
    addMultiplier(result, "battle_ingots", effect.battleOriginiumIngotMultiplier());
    addValue(result, "failure_recovery", effect.oneTimeFailureRecoveryObjectiveLife());
    return List.copyOf(result);
  }

  private static void addValue(List<Component> lines, String key, int value) {
    if (value != 0) lines.add(Component.translatable(PREFIX + key, signed(value)));
  }

  private static void addMultiplier(List<Component> lines, String key, double multiplier) {
    if (Double.compare(multiplier, 1.0) != 0) {
      double percentage = (multiplier - 1.0) * 100.0;
      lines.add(Component.translatable(PREFIX + key, signed(percentage).append("%")));
    }
  }

  private static MutableComponent signed(double value) {
    String text = (value > 0.0 ? "+" : "") + NUMBER.format(value);
    return Component.literal(text).withStyle(value >= 0.0 ? ChatFormatting.GREEN : ChatFormatting.RED);
  }
}
