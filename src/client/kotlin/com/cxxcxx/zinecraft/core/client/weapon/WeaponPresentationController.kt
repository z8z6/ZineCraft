package com.cxxcxx.zinecraft.core.client.weapon

import com.cxxcxx.zinecraft.api.weapon.WeaponPresentation
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionCancelledPayload
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionStartedPayload
import com.cxxcxx.zinecraft.core.Zinecraft
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczWeaponAnimationService
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczPlayerAnimationService

object WeaponPresentationController {
  private data class Key(val entityId: Int, val actionId: ResourceLocation)

  private data class ActivePresentation(
    val startGameTick: Long,
    val timeline: WeaponPresentation,
    var animationStarted: Boolean = false,
    val playedVfx: BooleanArray = BooleanArray(timeline.vfx.size),
    val playedSounds: BooleanArray = BooleanArray(timeline.sounds.size)
  )

  private val active = mutableMapOf<Key, ActivePresentation>()
  private val playerAnimations: PlayerAnimationService = TaczPlayerAnimationService
  private val weaponAnimations: WeaponAnimationService = TaczWeaponAnimationService
  private val vfx: WeaponVfxService = VanillaWeaponVfxService
  private val sounds: WeaponSoundService = VanillaWeaponSoundService

  fun initialize() {
    ClientPlayNetworking.registerGlobalReceiver(WeaponActionStartedPayload.TYPE) { payload, context ->
      context.client().execute { start(payload) }
    }
    ClientPlayNetworking.registerGlobalReceiver(WeaponActionCancelledPayload.TYPE) { payload, context ->
      context.client().execute { cancel(payload) }
    }
    ClientTickEvents.END_CLIENT_TICK.register(::tick)
    ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> active.clear() }
  }

  private fun start(payload: WeaponActionStartedPayload) {
    val definition = Zinecraft.WEAPONS.definition(payload.weaponId) ?: return
    val timeline = definition.presentation(payload.actionId) ?: return
    active[Key(payload.entityId, payload.actionId)] = ActivePresentation(
      payload.startGameTick,
      timeline
    )
  }

  private fun cancel(payload: WeaponActionCancelledPayload) {
    active.remove(Key(payload.entityId, payload.actionId))?.let { stop(payload.entityId, it) }
  }

  private fun tick(client: Minecraft) {
    val level = client.level ?: return
    val iterator = active.iterator()
    while (iterator.hasNext()) {
      val (key, running) = iterator.next()
      val entity = level.getEntity(key.entityId) as? LivingEntity
      val elapsed = level.gameTime - running.startGameTick
      if (elapsed >= running.timeline.durationTicks) {
        if (entity != null) stop(entity, running)
        iterator.remove()
        continue
      }
      if (entity == null || elapsed < 0) continue

      if (!running.animationStarted) {
        running.timeline.playerAnimation?.let { playerAnimations.play(entity, it) }
        running.timeline.weaponAnimation?.let { weaponAnimations.play(entity, entity.mainHandItem, it) }
        running.animationStarted = true
      }
      running.timeline.vfx.forEachIndexed { index, cue ->
        if (!running.playedVfx[index] && elapsed >= cue.tick) {
          vfx.play(entity, cue.id)
          running.playedVfx[index] = true
        }
      }
      running.timeline.sounds.forEachIndexed { index, cue ->
        if (!running.playedSounds[index] && elapsed >= cue.tick) {
          sounds.play(entity, cue.id)
          running.playedSounds[index] = true
        }
      }
    }
  }

  private fun stop(entityId: Int, running: ActivePresentation) {
    val entity = Minecraft.getInstance().level?.getEntity(entityId) as? LivingEntity ?: return
    stop(entity, running)
  }

  private fun stop(entity: LivingEntity, running: ActivePresentation) {
    running.timeline.playerAnimation?.let { playerAnimations.stop(entity, it) }
    running.timeline.weaponAnimation?.let { weaponAnimations.stop(entity, entity.mainHandItem, it) }
  }
}
