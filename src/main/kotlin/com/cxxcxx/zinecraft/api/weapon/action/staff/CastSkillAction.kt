package com.cxxcxx.zinecraft.api.weapon.action.staff

import com.cxxcxx.zinecraft.api.skill.SkillCastContext
import com.cxxcxx.zinecraft.api.skill.SkillService
import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext
import net.minecraft.resources.ResourceLocation

class CastSkillAction(
  override val id: ResourceLocation,
  private val skillId: ResourceLocation,
  private val skillService: SkillService,
  private val castTick: Int,
  private val durationTicks: Int
) : WeaponAction {
  init {
    require(castTick in 0 until durationTicks) { "施法 tick 必须位于动作时间线内" }
  }

  override fun canStart(context: WeaponContext): Boolean =
    skillService.canCast(skillId, context.toSkillContext())

  override fun createRuntime(context: WeaponContext): WeaponActionRuntime =
    object : TimedWeaponActionRuntime(castTick..castTick, durationTicks) {
      override fun onTick(tick: Int) {
        if (tick == castTick) skillService.cast(skillId, context.toSkillContext())
      }
    }

  private fun WeaponContext.toSkillContext() = SkillCastContext(player, stack, hand)
}
