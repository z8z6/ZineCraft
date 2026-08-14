package com.cxxcxx.zinecraft.api.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import net.minecraft.resources.ResourceLocation
import kotlin.math.ceil

class TaczFireAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean {
    val gun = context.gun() ?: return false
    return context.player.isAlive && !context.player.isSpectator &&
        context.stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity) > 0 &&
        !context.stack.getOrDefault(WeaponStateComponents.NEEDS_BOLT, false)
  }

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime {
    val gun = requireNotNull(context.gun())
    val fireMode = fireMode(context)
    val shots = if (fireMode == "burst") gun.burstCount else 1
    val interval = ceil(1200.0 / if (fireMode == "burst") gun.burstRpm else gun.rpm).toInt().coerceAtLeast(1)
    val lastShot = (shots - 1) * interval
    return object : TimedWeaponActionRuntime(0..lastShot, lastShot + interval) {
      override fun onTick(tick: Int) {
        if (tick % interval != 0 || tick > lastShot) return
        val ammo = context.stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity)
        if (ammo <= 0) return
        context.stack.set(WeaponStateComponents.AMMO, ammo - 1)
        if (gun.bolt == "manual_action") context.stack.set(WeaponStateComponents.NEEDS_BOLT, true)
        val aiming = context.stack.getOrDefault(WeaponStateComponents.AIMING, false)
        val hit = HitscanService.trace(context.player, gun.range, if (aiming) 0.08 else 0.3) ?: return
        repeat(gun.projectileCount) {
          hit.target.hurt(context.player.damageSources().playerAttack(context.player), gun.damage)
        }
      }
    }
  }
}

class TaczReloadAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean {
    val gun = context.gun() ?: return false
    if (context.stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity) >= gun.capacity) return false
    return context.player.isCreative || countAmmo(context, gun.ammoId) > 0
  }

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime {
    val gun = requireNotNull(context.gun())
    val manual = gun.feedType == "manual"
    val currentAtStart = context.stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity)
    val rounds = if (manual) (gun.capacity - currentAtStart).coerceAtLeast(1) else 1
    val lastFeed = if (manual) gun.reloadFeedTicks * rounds else gun.reloadFeedTicks
    val duration = (lastFeed + (gun.reloadDurationTicks - gun.reloadFeedTicks)).coerceAtLeast(1)
    return object : TimedWeaponActionRuntime(gun.reloadFeedTicks..lastFeed, duration) {
      override fun onTick(tick: Int) {
        if (tick < gun.reloadFeedTicks || tick > lastFeed || tick % gun.reloadFeedTicks != 0) return
        val current = context.stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity)
        val missing = gun.capacity - current
        if (missing <= 0) return
        val requested = if (manual) 1 else missing
        val loaded = if (context.player.isCreative) requested else consumeAmmo(context, gun.ammoId, requested)
        if (loaded > 0) context.stack.set(WeaponStateComponents.AMMO, current + loaded)
        if (loaded > 0) context.stack.set(WeaponStateComponents.NEEDS_BOLT, false)
      }
    }
  }

  private fun countAmmo(context: WeaponContext, id: ResourceLocation): Int {
    var count = 0
    val inventory = context.player.inventory
    for (slot in 0 until inventory.containerSize) {
      val stack = inventory.getItem(slot)
      if (stack.get(WeaponStateComponents.TACZ_AMMO_ID) == id) count += stack.count
    }
    return count
  }

  private fun consumeAmmo(context: WeaponContext, id: ResourceLocation, requested: Int): Int {
    var remaining = requested
    val inventory = context.player.inventory
    for (slot in 0 until inventory.containerSize) {
      val stack = inventory.getItem(slot)
      if (stack.get(WeaponStateComponents.TACZ_AMMO_ID) != id) continue
      val amount = minOf(remaining, stack.count)
      stack.shrink(amount)
      remaining -= amount
      if (remaining == 0) break
    }
    return requested - remaining
  }
}

class TaczFireSelectAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean = (context.gun()?.fireModes?.size ?: 0) > 1
  override fun createRuntime(context: WeaponContext): WeaponActionRuntime = object : TimedWeaponActionRuntime(0..0, 4) {
    override fun onTick(tick: Int) {
      if (tick != 0) return
      val gun = context.gun() ?: return
      val current = fireMode(context)
      val next =
        gun.fireModes[(gun.fireModes.indexOf(current).takeIf { it >= 0 } ?: 0).let { (it + 1) % gun.fireModes.size }]
      context.stack.set(WeaponStateComponents.FIRE_MODE, fireModeOrdinal(next))
    }
  }
}

class TaczInspectAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean = context.gun() != null && context.player.isAlive
  override fun createRuntime(context: WeaponContext): WeaponActionRuntime =
    object : TimedWeaponActionRuntime(0..0, 40) {
      override fun onTick(tick: Int) = Unit
    }
}

class TaczMeleeAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean = context.gun() != null && context.player.isAlive
  override fun createRuntime(context: WeaponContext): WeaponActionRuntime {
    val gun = requireNotNull(context.gun())
    return object : TimedWeaponActionRuntime(2..2, gun.meleeCooldownTicks.coerceAtLeast(3)) {
      override fun onTick(tick: Int) {
        if (tick != 2) return
        HitscanService.trace(context.player, gun.meleeDistance, 0.6)?.target
          ?.hurt(context.player.damageSources().playerAttack(context.player), gun.meleeDamage)
      }
    }
  }
}

class TaczBoltAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean = context.gun()?.bolt == "manual_action" &&
      context.stack.getOrDefault(WeaponStateComponents.NEEDS_BOLT, false)

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime {
    val duration = context.gun()?.boltActionTicks?.coerceAtLeast(1) ?: 1
    return object : TimedWeaponActionRuntime(0..0, duration) {
      override fun onTick(tick: Int) {
        if (tick == 0) context.stack.set(WeaponStateComponents.NEEDS_BOLT, false)
      }
    }
  }
}

class TaczAimAction(override val id: ResourceLocation) : WeaponAction {
  override fun canStart(context: WeaponContext): Boolean = context.gun() != null && context.player.isAlive

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime {
    val duration = context.gun()?.aimTicks ?: 1
    return object : TimedWeaponActionRuntime(0..0, duration) {
      override fun onTick(tick: Int) {
        if (tick == 0) {
          val aiming = context.stack.getOrDefault(WeaponStateComponents.AIMING, false)
          context.stack.set(WeaponStateComponents.AIMING, !aiming)
        }
      }
    }
  }
}

private fun WeaponContext.gun(): TaczGunSpec? =
  stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun)

private fun fireMode(context: WeaponContext): String =
  when (context.stack.getOrDefault(WeaponStateComponents.FIRE_MODE, 0)) {
    0 -> "auto"
    1 -> "semi"
    2 -> "burst"
    else -> "unknown"
  }.let { selected -> context.gun()?.fireModes?.let { if (selected in it) selected else it.firstOrNull() } ?: selected }

private fun fireModeOrdinal(mode: String): Int = when (mode) {
  "auto" -> 0; "semi" -> 1; "burst" -> 2; else -> 3
}
