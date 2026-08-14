package com.cxxcxx.zinecraft.api.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.item.ActionWeaponItem
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class TaczGunItem(properties: Properties) : ActionWeaponItem(properties) {
  override fun getName(stack: ItemStack): Component {
    val spec = stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun)
    return spec?.let { Component.translatable(it.translationKey) } ?: super.getName(stack)
  }

  override fun appendHoverText(
    stack: ItemStack,
    context: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
    val id = stack.get(WeaponStateComponents.TACZ_GUN_ID) ?: return
    val spec = TaczGunPacks.gun(id) ?: return
    val ammo = stack.getOrDefault(WeaponStateComponents.AMMO, spec.capacity).coerceAtMost(spec.capacity)
    tooltipComponents += Component.translatable("item.zinecraft.firearm.ammo", ammo, spec.capacity)
      .withStyle(ChatFormatting.YELLOW)
    tooltipComponents += Component.translatable("item.zinecraft.tacz_gun.caliber", spec.ammoId)
      .withStyle(ChatFormatting.GRAY)
    tooltipComponents += Component.translatable("item.zinecraft.tacz_gun.stats", spec.damage, spec.rpm)
      .withStyle(ChatFormatting.GRAY)
    val fireMode = when (stack.getOrDefault(WeaponStateComponents.FIRE_MODE, 0)) {
      0 -> "auto"; 1 -> "semi"; 2 -> "burst"; else -> "unknown"
    }
    tooltipComponents += Component.translatable(
      "item.zinecraft.tacz_gun.fire_mode",
      Component.translatable("item.zinecraft.tacz_gun.fire_mode.$fireMode")
    )
      .withStyle(ChatFormatting.GRAY)
    spec.tooltipKey?.let { tooltipComponents += Component.translatable(it).withStyle(ChatFormatting.DARK_GRAY) }
    if (tooltipFlag.isAdvanced) {
      tooltipComponents += Component.literal("TaCZ ${spec.id} · ${spec.pack.sourceName}")
        .withStyle(ChatFormatting.DARK_GRAY)
    }
  }
}

class TaczAmmoItem(properties: Properties) : Item(properties) {
  override fun getName(stack: ItemStack): Component {
    val spec = stack.get(WeaponStateComponents.TACZ_AMMO_ID)?.let(TaczGunPacks::ammo)
    return spec?.let { Component.translatable(it.translationKey) } ?: super.getName(stack)
  }

  override fun appendHoverText(
    stack: ItemStack,
    context: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
    if (!tooltipFlag.isAdvanced) return
    stack.get(WeaponStateComponents.TACZ_AMMO_ID)?.let {
      tooltipComponents += Component.literal("TaCZ $it").withStyle(ChatFormatting.DARK_GRAY)
    }
  }
}
