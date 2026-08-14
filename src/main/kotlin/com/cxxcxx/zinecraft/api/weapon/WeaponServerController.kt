package com.cxxcxx.zinecraft.api.weapon

import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionCancelledPayload
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionRequestPayload
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionStartedPayload
import com.cxxcxx.zinecraft.core.Zinecraft
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack
import java.util.UUID

/** 服务端是动作运行时、命中和伤害的唯一权威。 */
object WeaponServerController {
  private data class ActiveAction(
    val weapon: WeaponDefinition,
    val actionId: net.minecraft.resources.ResourceLocation,
    val hand: InteractionHand,
    val stack: ItemStack,
    val runtime: WeaponActionRuntime
  )

  private val activeActions = mutableMapOf<UUID, ActiveAction>()

  fun initialize() {
    ServerPlayNetworking.registerGlobalReceiver(WeaponActionRequestPayload.TYPE) { payload, context ->
      request(context.player(), payload.input, InteractionHand.MAIN_HAND)
    }
    ServerTickEvents.END_SERVER_TICK.register(::tick)
    ServerPlayConnectionEvents.DISCONNECT.register { handler, _ -> activeActions.remove(handler.player.uuid) }
  }

  fun request(player: ServerPlayer, input: WeaponInput, hand: InteractionHand = InteractionHand.MAIN_HAND) {
    if (activeActions.containsKey(player.uuid)) return

    val stack = player.getItemInHand(hand)
    val definition = Zinecraft.WEAPONS.definition(stack) ?: return
    val actionId = definition.action(input) ?: return
    val action = Zinecraft.WEAPONS.action(actionId) ?: return
    val context = WeaponContext(player, stack, hand, definition)
    if (!action.canStart(context)) return

    player.resetAttackStrengthTicker()
    activeActions[player.uuid] = ActiveAction(definition, actionId, hand, stack, action.createRuntime(context))
    broadcast(
      player,
      WeaponActionStartedPayload(player.id, definition.id, actionId, player.serverLevel().gameTime)
    )
  }

  private fun tick(server: MinecraftServer) {
    val iterator = activeActions.iterator()
    while (iterator.hasNext()) {
      val (playerId, active) = iterator.next()
      val player = server.playerList.getPlayer(playerId)
      if (player == null || !isStillValid(player, active)) {
        if (player != null) broadcast(player, WeaponActionCancelledPayload(player.id, active.actionId))
        iterator.remove()
        continue
      }

      active.runtime.tick()
      if (active.runtime.finished) iterator.remove()
    }
  }

  private fun isStillValid(player: ServerPlayer, active: ActiveAction): Boolean =
    player.isAlive && !player.isSpectator && player.getItemInHand(active.hand) === active.stack &&
        Zinecraft.WEAPONS.definition(player.getItemInHand(active.hand))?.id == active.weapon.id

  private fun broadcast(
    player: ServerPlayer,
    payload: net.minecraft.network.protocol.common.custom.CustomPacketPayload
  ) {
    val recipients = PlayerLookup.tracking(player).toMutableSet()
    recipients += player
    recipients.filter { ServerPlayNetworking.canSend(it, payload.type()) }.forEach {
      ServerPlayNetworking.send(it, payload)
    }
  }
}
