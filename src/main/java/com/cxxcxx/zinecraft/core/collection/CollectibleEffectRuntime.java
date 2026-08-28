package com.cxxcxx.zinecraft.core.collection;

import com.cxxcxx.zinecraft.api.collection.CollectibleCombatStats;
import com.cxxcxx.zinecraft.api.collection.CollectibleItem;
import com.cxxcxx.zinecraft.api.collection.CollectibleSpecialCondition;
import com.cxxcxx.zinecraft.api.collection.network.CollectibleFailureRecoveryPayload;
import com.cxxcxx.zinecraft.api.combat.CombatMitigationType;
import com.cxxcxx.zinecraft.api.combat.CombatStat;
import com.cxxcxx.zinecraft.api.combat.CombatStatusService;
import com.cxxcxx.zinecraft.core.Zinecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Comparator;

/** 将服务端战斗事件转发给藏品注册的触发函数。 */
@EventBusSubscriber(modid = Zinecraft.MOD_ID)
public final class CollectibleEffectRuntime {
  private CollectibleEffectRuntime() {
  }

  /** 首次加入世界的敌方实体只在生成时固化一次可转换基础属性藏品效果。 */
  @SubscribeEvent
  public static void onEntityJoin(EntityJoinLevelEvent event) {
    if (event.loadedFromDisk()
        || !(event.getLevel() instanceof ServerLevel level)
        || !(event.getEntity() instanceof LivingEntity enemy)
        || !(enemy instanceof Enemy)) {
      return;
    }
    EnemySpawnStatService.apply(level, enemy);
  }

  /** 所有藏品先累加到同一 CombatStat，再统一判定闪避并计算减伤、敌方受伤乘区与无视防御。 */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onIncomingDamage(LivingIncomingDamageEvent event) {
    if (event.getEntity().level().isClientSide) return;
    if (event.getSource().getEntity() instanceof LivingEntity attacker
        && attacker != event.getEntity()
        && CombatStatusService.preventsAttacking(attacker)) {
      event.setCanceled(true);
      return;
    }

    CombatStat targetStats = CollectibleCombatStats.apply(event.getEntity(), CombatStat.EMPTY).limited();
    double evasionRate = evasionRate(event.getSource(), targetStats);
    if (evasionRate > 0.0 && event.getEntity().getRandom().nextDouble() < evasionRate) {
      event.setCanceled(true);
      return;
    }

    if (event.getSource().getEntity() instanceof Enemy) {
      double reduction = targetStats.damageReduction();
      if (reduction > 0.0) event.setAmount((float) (event.getAmount() * (1.0 - reduction)));
    }

    if (event.getEntity() instanceof Enemy
        && event.getSource().getEntity() instanceof LivingEntity attacker) {
      CombatStat attackerStats = CollectibleCombatStats.apply(attacker, CombatStat.EMPTY).limited();
      double damageTakenMultiplier = enemyDamageTakenMultiplier(event.getSource(), attackerStats);
      if (Double.compare(damageTakenMultiplier, 1.0) != 0) {
        event.setAmount((float) (event.getAmount() * damageTakenMultiplier));
      }

      double defenseIgnore = attackerStats.defenseIgnore();
      if (defenseIgnore > 0.0) {
        event.addReductionModifier(
            DamageContainer.Reduction.ARMOR,
            (container, armorReduction) -> armorReduction * (float) (1.0 - defenseIgnore)
        );
      }
    }
  }

  private static double enemyDamageTakenMultiplier(DamageSource source, CombatStat stats) {
    if (source.is(damageType("true"))) return 1.0;
    if (source.is(damageType("physical"))) {
      return stats.enemyDamageTakenMultiplier(CombatMitigationType.PHYSICAL);
    }
    if (source.is(damageType("magic"))
        || source.is(damageType("arts"))
        || source.is(damageType("fire"))
        || source.is(damageType("ice"))
        || source.is(damageType("lightning"))
        || source.is(damageType("poison"))) {
      return stats.enemyDamageTakenMultiplier(CombatMitigationType.MAGIC);
    }
    return source.is(DamageTypeTags.BYPASSES_ARMOR)
        ? stats.enemyDamageTakenMultiplier(CombatMitigationType.MAGIC)
        : stats.enemyDamageTakenMultiplier(CombatMitigationType.PHYSICAL);
  }

  private static double evasionRate(DamageSource source, CombatStat stats) {
    if (source.is(damageType("true"))) return 0.0;
    if (source.is(damageType("physical"))) return stats.physicalDamageEvasionRate();
    if (source.is(damageType("magic"))
        || source.is(damageType("arts"))
        || source.is(damageType("fire"))
        || source.is(damageType("ice"))
        || source.is(damageType("lightning"))
        || source.is(damageType("poison"))) {
      return stats.magicDamageEvasionRate();
    }
    if (source.getEntity() == null) return 0.0;
    return source.is(DamageTypeTags.BYPASSES_ARMOR)
        ? stats.magicDamageEvasionRate()
        : stats.physicalDamageEvasionRate();
  }

  private static ResourceKey<DamageType> damageType(String path) {
    return ResourceKey.create(Registries.DAMAGE_TYPE, Zinecraft.id(path));
  }
  /** 所有藏品的治疗与生命回复加成相加后，在最终回复入口统一结算。 */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onLivingHeal(LivingHealEvent event) {
    if (event.getEntity().level().isClientSide) return;
    double bonus = CollectibleCombatStats.apply(event.getEntity(), CombatStat.EMPTY)
        .limited()
        .healingAndHealthRegenerationBonus();
    if (bonus > 0.0) event.setAmount((float) (event.getAmount() * (1.0 + bonus)));
  }

  /** 将登记的一次失败保护近似为不死图腾：取消死亡、恢复生命并销毁触发的藏品。 */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onFatalDamage(LivingDeathEvent event) {
    LivingEntity entity = event.getEntity();
    if (entity.level().isClientSide || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      return;
    }

    CuriosApi.getCuriosInventory(entity).ifPresent(handler -> handler.findCurios(
        stack -> recoveryHealth(entity, stack) > 0
    ).stream().max(Comparator.comparingInt(slot -> recoveryHealth(entity, slot.stack())))
        .ifPresent(slot -> {
          int recoveryHealth = recoveryHealth(entity, slot.stack());
          ItemStack activated = slot.stack().copyWithCount(1);
          handler.setEquippedCurio(
              slot.slotContext().identifier(),
              slot.slotContext().index(),
              ItemStack.EMPTY
          );
          entity.setHealth(Math.min((float) recoveryHealth, entity.getMaxHealth()));
          event.setCanceled(true);
          if (entity instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new CollectibleFailureRecoveryPayload(activated));
          }
        }));
  }

  private static int recoveryHealth(LivingEntity entity, ItemStack stack) {
    if (!(stack.getItem() instanceof CollectibleItem item)) return 0;
    return item.collectible().power.apply(CombatStat.EMPTY.withCollectibleEffectTier(
        CollectibleSpecialCondition.tier(entity)
    )).oneTimeFailureRecoveryObjectiveLife();
  }

  /** 在死亡确认阶段执行击杀者装备藏品的击杀能力。 */
  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onLivingDeath(LivingDeathEvent event) {
    if (event.getEntity().level().isClientSide) return;
    if (event.getSource().getEntity() instanceof LivingEntity killer && killer != event.getEntity()) {
      CollectibleCombatStats.triggerKillEffects(killer, event.getEntity());
    }
  }
}
