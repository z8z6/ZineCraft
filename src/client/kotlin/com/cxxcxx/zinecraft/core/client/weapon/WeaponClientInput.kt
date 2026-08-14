package com.cxxcxx.zinecraft.core.client.weapon

import com.cxxcxx.zinecraft.api.weapon.WeaponInput
import com.cxxcxx.zinecraft.api.weapon.network.WeaponActionRequestPayload
import com.cxxcxx.zinecraft.core.Zinecraft
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.Minecraft
import net.minecraft.client.KeyMapping
import net.minecraft.world.phys.HitResult
import net.minecraft.world.item.SwordItem
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import org.lwjgl.glfw.GLFW

object WeaponClientInput {
  private val reloadKey = KeyBindingHelper.registerKeyBinding(
    KeyMapping("key.zinecraft.weapon_reload", GLFW.GLFW_KEY_R, "key.categories.zinecraft.weapon")
  )
  private val fireSelectKey = KeyBindingHelper.registerKeyBinding(
    KeyMapping("key.zinecraft.weapon_fire_select", GLFW.GLFW_KEY_B, "key.categories.zinecraft.weapon")
  )
  private val inspectKey = KeyBindingHelper.registerKeyBinding(
    KeyMapping("key.zinecraft.weapon_inspect", GLFW.GLFW_KEY_X, "key.categories.zinecraft.weapon")
  )
  private val meleeKey = KeyBindingHelper.registerKeyBinding(
    KeyMapping("key.zinecraft.weapon_melee", GLFW.GLFW_KEY_V, "key.categories.zinecraft.weapon")
  )

  fun initialize() {
    ClientTickEvents.END_CLIENT_TICK.register { client ->
      while (reloadKey.consumeClick()) request(client, WeaponInput.RELOAD)
      while (fireSelectKey.consumeClick()) request(client, WeaponInput.FIRE_SELECT)
      while (inspectKey.consumeClick()) request(client, WeaponInput.INSPECT)
      while (meleeKey.consumeClick()) request(client, WeaponInput.MELEE)
    }
  }

  /** 由极薄的 Minecraft.startAttack Mixin 转发，方块破坏仍交给原版。 */
  @JvmStatic
  fun requestPrimary(): Boolean {
    val client = Minecraft.getInstance()
    val player = client.player ?: return false
    if (Zinecraft.WEAPONS.definition(player.mainHandItem) == null) return false
    if (client.hitResult?.type == HitResult.Type.BLOCK && player.mainHandItem.item is SwordItem) return false
    if (!ClientPlayNetworking.canSend(WeaponActionRequestPayload.TYPE)) return false

    val input = if (player.mainHandItem.item === ModTaczWeapons.GUN_ITEM.item &&
      player.mainHandItem.getOrDefault(WeaponStateComponents.NEEDS_BOLT, false)
    ) WeaponInput.BOLT else WeaponInput.PRIMARY
    ClientPlayNetworking.send(WeaponActionRequestPayload(input))
    return true
  }

  private fun request(client: Minecraft, input: WeaponInput) {
    val player = client.player ?: return
    if (Zinecraft.WEAPONS.definition(player.mainHandItem)?.action(input) == null) return
    if (ClientPlayNetworking.canSend(WeaponActionRequestPayload.TYPE)) {
      ClientPlayNetworking.send(WeaponActionRequestPayload(input))
    }
  }
}
