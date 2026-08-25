package com.cxxcxx.zinecraft.core.collection;

import com.cxxcxx.zinecraft.api.collection.CollectibleCombatStats;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** 在敌方第一次生成时固化当前队伍藏品提供的可转换基础属性快照。 */
public final class EnemySpawnStatService {
  private static final String APPLIED_TAG = "ZinecraftEnemySpawnStatsApplied";

  private EnemySpawnStatService() {
  }

  public static void apply(ServerLevel level, LivingEntity enemy) {
    if (enemy.getPersistentData().getBoolean(APPLIED_TAG)) return;

    CombatStat collected = CollectibleCombatStats.applyAll(level.players(), CombatStat.EMPTY);
    var modifiers = CombatStat.toVanillaModifiers(
        base -> collected.applyEnemySpawnStatEffects(enemy, base),
        Zinecraft.id("collectible/enemy_spawn")
    );
    boolean maxHealthChanged = modifiers.containsKey(Attributes.MAX_HEALTH);
    modifiers.forEach((attribute, modifier) -> {
      var instance = enemy.getAttribute(attribute);
      if (instance != null) instance.addOrReplacePermanentModifier(modifier);
    });
    if (maxHealthChanged) enemy.setHealth(enemy.getMaxHealth());
    enemy.getPersistentData().putBoolean(APPLIED_TAG, true);
  }
}
