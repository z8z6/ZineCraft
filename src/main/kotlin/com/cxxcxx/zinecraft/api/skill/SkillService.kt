package com.cxxcxx.zinecraft.api.skill

import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack

data class SkillCastContext(
  val player: ServerPlayer,
  val stack: ItemStack,
  val hand: InteractionHand
)

interface SkillEffect {
  fun canCast(context: SkillCastContext): Boolean
  fun cast(context: SkillCastContext)
}

/** 服务端技能效果入口；法杖 Action 只负责时间线，不内嵌具体法术。 */
class SkillService {
  private val effects = mutableMapOf<ResourceLocation, SkillEffect>()

  fun register(id: ResourceLocation, effect: SkillEffect): ResourceLocation {
    require(effects.putIfAbsent(id, effect) == null) { "重复的技能效果 ID：$id" }
    return id
  }

  fun canCast(id: ResourceLocation, context: SkillCastContext): Boolean = effects[id]?.canCast(context) == true

  fun cast(id: ResourceLocation, context: SkillCastContext): Boolean {
    val effect = effects[id] ?: return false
    if (!effect.canCast(context)) return false
    effect.cast(context)
    return true
  }
}
