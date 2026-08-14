package com.cxxcxx.zinecraft.core.weapon

import com.cxxcxx.zinecraft.api.skill.SkillCastContext
import com.cxxcxx.zinecraft.api.skill.SkillEffect
import com.cxxcxx.zinecraft.api.weapon.combat.HitscanService
import com.cxxcxx.zinecraft.core.Zinecraft

object ModWeaponSkillEffects {
  val ARCANE_BOLT = Zinecraft.REGISTRAR.id("skill/arcane_bolt")
  val MENDING_LIGHT = Zinecraft.REGISTRAR.id("skill/mending_light")

  init {
    Zinecraft.SKILL_SERVICE.register(ARCANE_BOLT, object : SkillEffect {
      override fun canCast(context: SkillCastContext): Boolean =
        context.player.isAlive && !context.player.isSpectator

      override fun cast(context: SkillCastContext) {
        val target = HitscanService.trace(context.player, 24.0, 0.45)?.target ?: return
        target.hurt(context.player.damageSources().indirectMagic(context.player, context.player), 8.0f)
      }
    })

    Zinecraft.SKILL_SERVICE.register(MENDING_LIGHT, object : SkillEffect {
      override fun canCast(context: SkillCastContext): Boolean =
        context.player.isAlive && context.player.health < context.player.maxHealth

      override fun cast(context: SkillCastContext) {
        context.player.heal(6.0f)
      }
    })
  }
}
