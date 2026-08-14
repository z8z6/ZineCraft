package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.cxxcxx.zinecraft.core.client.weapon.WeaponAnimationService
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

object TaczWeaponAnimationService : WeaponAnimationService {
  override fun play(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation) {
    TaczItemRenderer.play(entity, stack, animation)
  }

  override fun stop(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation) {
    TaczItemRenderer.stop(stack, animation)
  }
}
