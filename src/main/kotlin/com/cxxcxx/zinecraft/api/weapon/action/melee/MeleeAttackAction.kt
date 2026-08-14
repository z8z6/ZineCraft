package com.cxxcxx.zinecraft.api.weapon.action.melee

import com.cxxcxx.zinecraft.api.weapon.action.ActionPhase
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext
import com.cxxcxx.zinecraft.api.weapon.combat.MeleeHitboxService
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.ai.attributes.Attributes

class MeleeAttackAction(
  override val id: ResourceLocation,
  private val hitTick: Int,
  private val durationTicks: Int,
  private val damage: Float,
  private val range: Double,
  private val arcDegrees: Double
) : WeaponAction {
  init {
    require(hitTick in 0 until durationTicks) { "命中 tick 必须位于动作时间线内" }
    require(damage > 0.0f) { "近战伤害必须大于 0" }
    require(range > 0.0) { "近战范围必须大于 0" }
    require(arcDegrees in 0.0..360.0) { "近战弧度必须在 0 到 360 度之间" }
  }

  override fun canStart(context: WeaponContext): Boolean =
    context.player.isAlive && !context.player.isSpectator && !context.player.isUsingItem

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime = Runtime(context)

  private inner class Runtime(private val context: WeaponContext) : WeaponActionRuntime {
    override var currentTick: Int = 0
      private set

    override val phase: ActionPhase
      get() = when {
        currentTick < hitTick -> ActionPhase.STARTUP
        currentTick == hitTick -> ActionPhase.ACTIVE
        currentTick < durationTicks -> ActionPhase.RECOVERY
        else -> ActionPhase.FINISHED
      }

    override val finished: Boolean
      get() = phase == ActionPhase.FINISHED

    override fun tick() {
      if (finished) return
      if (currentTick == hitTick) performHit()
      currentTick++
    }

    private fun performHit() {
      val player = context.player
      val targets = MeleeHitboxService.findTargets(player, range, arcDegrees)
      val attackDamage = player.getAttribute(Attributes.ATTACK_DAMAGE)
      val resolvedDamage = resolveActionMeleeDamage(damage, attackDamage?.value)
      var damagedAny = false
      for (target in targets) {
        damagedAny = target.hurt(player.damageSources().playerAttack(player), resolvedDamage) || damagedAny
      }
      if (damagedAny) {
        context.stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND)
      }
    }
  }
}

/**
 * 动作剑已经通过物品属性把声明伤害写入玩家的 ATTACK_DAMAGE；直接读取最终值可以只应用一次
 * 饰品修饰。声明伤害仅在属性不可用时兜底，枪械仍使用独立伤害管线。
 */
internal fun resolveActionMeleeDamage(baseDamage: Float, attackDamage: Double?): Float {
  require(baseDamage.isFinite() && baseDamage > 0f) { "动作近战基础伤害必须是有限正数" }
  return attackDamage?.takeIf { it.isFinite() && it > 0.0 }?.toFloat() ?: baseDamage
}
