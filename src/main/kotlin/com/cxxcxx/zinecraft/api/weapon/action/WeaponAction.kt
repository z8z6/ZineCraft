package com.cxxcxx.zinecraft.api.weapon.action

import com.cxxcxx.zinecraft.api.weapon.WeaponDefinition
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack

enum class ActionPhase {
  STARTUP,
  ACTIVE,
  RECOVERY,
  FINISHED
}

data class WeaponContext(
  val player: ServerPlayer,
  val stack: ItemStack,
  val hand: InteractionHand,
  val definition: WeaponDefinition
) {
  val level: ServerLevel
    get() = player.serverLevel()
}

interface WeaponAction {
  val id: ResourceLocation

  fun canStart(context: WeaponContext): Boolean

  fun createRuntime(context: WeaponContext): WeaponActionRuntime
}

interface WeaponActionRuntime {
  val currentTick: Int
  val phase: ActionPhase
  val finished: Boolean

  fun tick()
}
