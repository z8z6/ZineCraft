package com.cxxcxx.zinecraft.api.weapon.item

import com.cxxcxx.zinecraft.api.weapon.WeaponInput
import com.cxxcxx.zinecraft.api.weapon.WeaponServerController
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

open class ActionWeaponItem(properties: Properties) : Item(properties) {
  override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
    val stack = player.getItemInHand(hand)
    if (!level.isClientSide && player is ServerPlayer) {
      WeaponServerController.request(player, WeaponInput.SECONDARY, hand)
    }
    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide)
  }
}

class FirearmItem(
  val capacity: Int,
  properties: Properties
) : ActionWeaponItem(properties) {
  init {
    require(capacity > 0) { "枪械弹容量必须大于 0" }
  }

  override fun appendHoverText(
    stack: ItemStack,
    context: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
    val ammo = stack.getOrDefault(WeaponStateComponents.AMMO, capacity)
    tooltipComponents += Component.translatable("item.zinecraft.firearm.ammo", ammo, capacity)
      .withStyle(ChatFormatting.YELLOW)
    val aimingKey = if (stack.getOrDefault(WeaponStateComponents.AIMING, false)) {
      "item.zinecraft.firearm.aiming"
    } else {
      "item.zinecraft.firearm.hip_fire"
    }
    tooltipComponents += Component.translatable(aimingKey).withStyle(ChatFormatting.GRAY)
  }
}
