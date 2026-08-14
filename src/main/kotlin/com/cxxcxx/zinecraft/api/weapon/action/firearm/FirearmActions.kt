package com.cxxcxx.zinecraft.api.weapon.action.firearm

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

class FirearmFireAction(
  override val id: ResourceLocation,
  private val fireTick: Int,
  private val durationTicks: Int,
  private val damage: Float,
  private val range: Double
) : WeaponAction {
  init {
    require(fireTick in 0 until durationTicks) { "开火 tick 必须位于动作时间线内" }
    require(damage > 0.0f) { "枪械伤害必须大于 0" }
    require(range > 0.0) { "枪械射程必须大于 0" }
  }

  override fun canStart(context: WeaponContext): Boolean =
    context.player.isAlive && !context.player.isSpectator &&
        context.stack.getOrDefault(WeaponStateComponents.AMMO, 0) > 0

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime =
    object : TimedWeaponActionRuntime(fireTick..fireTick, durationTicks) {
      override fun onTick(tick: Int) {
        if (tick != fireTick) return
        val ammo = context.stack.getOrDefault(WeaponStateComponents.AMMO, 0)
        if (ammo <= 0) return
        context.stack.set(WeaponStateComponents.AMMO, ammo - 1)

        val aiming = context.stack.getOrDefault(WeaponStateComponents.AIMING, false)
        val target = HitscanService.trace(context.player, range, if (aiming) 0.65 else 0.15)?.target ?: return
        target.hurt(context.player.damageSources().playerAttack(context.player), damage)
      }
    }
}

class FirearmReloadAction(
  override val id: ResourceLocation,
  private val reloadTick: Int,
  private val durationTicks: Int,
  private val capacity: Int,
  private val ammunition: Item
) : WeaponAction {
  init {
    require(reloadTick in 0 until durationTicks) { "装填 tick 必须位于动作时间线内" }
    require(capacity > 0) { "弹容量必须大于 0" }
  }

  override fun canStart(context: WeaponContext): Boolean {
    if (context.stack.getOrDefault(WeaponStateComponents.AMMO, capacity) >= capacity) return false
    return context.player.isCreative || countAmmunition(context) > 0
  }

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime =
    object : TimedWeaponActionRuntime(reloadTick..reloadTick, durationTicks) {
      override fun onTick(tick: Int) {
        if (tick != reloadTick) return
        val current = context.stack.getOrDefault(WeaponStateComponents.AMMO, capacity)
        val missing = capacity - current
        if (missing <= 0) return
        val loaded = if (context.player.isCreative) missing else consumeAmmunition(context, missing)
        if (loaded > 0) context.stack.set(WeaponStateComponents.AMMO, current + loaded)
      }
    }

  private fun countAmmunition(context: WeaponContext): Int {
    val inventory = context.player.inventory
    var count = 0
    for (slot in 0 until inventory.containerSize) {
      val stack = inventory.getItem(slot)
      if (stack.`is`(ammunition)) count += stack.count
    }
    return count
  }

  private fun consumeAmmunition(context: WeaponContext, requested: Int): Int {
    val inventory = context.player.inventory
    var remaining = requested
    for (slot in 0 until inventory.containerSize) {
      val stack = inventory.getItem(slot)
      if (!stack.`is`(ammunition)) continue
      val consumed = minOf(remaining, stack.count)
      stack.shrink(consumed)
      remaining -= consumed
      if (remaining == 0) break
    }
    return requested - remaining
  }
}

class ToggleAimAction(
  override val id: ResourceLocation,
  private val durationTicks: Int = 6
) : WeaponAction {
  init {
    require(durationTicks > 0) { "瞄准切换时间必须大于 0" }
  }

  override fun canStart(context: WeaponContext): Boolean = context.player.isAlive && !context.player.isSpectator

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime =
    object : TimedWeaponActionRuntime(0..0, durationTicks) {
      override fun onTick(tick: Int) {
        if (tick == 0) {
          val aiming = context.stack.getOrDefault(WeaponStateComponents.AIMING, false)
          context.stack.set(WeaponStateComponents.AIMING, !aiming)
        }
      }
    }
}
