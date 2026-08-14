package com.cxxcxx.zinecraft.core.client.weapon

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import net.minecraft.world.phys.Vec3

interface PlayerAnimationService {
  fun play(entity: LivingEntity, animation: ResourceLocation)
  fun stop(entity: LivingEntity, animation: ResourceLocation)
}

interface WeaponAnimationService {
  fun play(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation)
  fun stop(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation)
}

interface WeaponVfxService {
  fun play(entity: LivingEntity, effect: ResourceLocation)
}

interface WeaponSoundService {
  fun play(entity: LivingEntity, sound: ResourceLocation)
}

/** 没有引入玩家动画库时，用原版挥手动画完成可运行的表现后端。 */
object VanillaPlayerAnimationService : PlayerAnimationService {
  override fun play(entity: LivingEntity, animation: ResourceLocation) {
    entity.swing(InteractionHand.MAIN_HAND)
  }

  override fun stop(entity: LivingEntity, animation: ResourceLocation) = Unit
}

/** 测试剑没有骨骼；保留调用边界，未来后端可在客户端独立替换。 */
object NoopWeaponAnimationService : WeaponAnimationService {
  override fun play(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation) = Unit
  override fun stop(entity: LivingEntity, stack: ItemStack, animation: ResourceLocation) = Unit
}

object VanillaWeaponVfxService : WeaponVfxService {
  private val trailId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_sword_trail")
  private val impactId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_sword_impact")
  private val muzzleId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_rifle_muzzle")
  private val rifleImpactId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_rifle_impact")
  private val arcaneCastId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_staff_arcane_cast")
  private val arcaneImpactId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_staff_arcane_impact")
  private val healId = ResourceLocation.fromNamespaceAndPath("zinecraft", "vfx/test_staff_heal")

  override fun play(entity: LivingEntity, effect: ResourceLocation) {
    when (effect) {
      trailId -> trail(entity)
      impactId -> impact(entity)
      muzzleId -> muzzle(entity)
      rifleImpactId -> rangedImpact(entity, ParticleTypes.CRIT)
      arcaneCastId -> muzzle(entity, ParticleTypes.ENCHANT)
      arcaneImpactId -> rangedImpact(entity, ParticleTypes.WITCH)
      healId -> heal(entity)
    }
  }

  private fun trail(entity: LivingEntity) {
    val level = entity.level()
    val look = entity.lookAngle.normalize()
    val right = Vec3(-look.z, 0.0, look.x).normalize()
    val center = entity.eyePosition.add(look.scale(1.25)).add(0.0, -0.4, 0.0)
    for (index in -3..3) {
      val point = center.add(right.scale(index * 0.22)).add(0.0, 0.18 - index * index * 0.018, 0.0)
      level.addParticle(ParticleTypes.ELECTRIC_SPARK, point.x, point.y, point.z, 0.0, 0.01, 0.0)
    }
  }

  private fun impact(entity: LivingEntity) {
    val point = entity.eyePosition.add(entity.lookAngle.normalize().scale(2.0))
    entity.level().addParticle(ParticleTypes.SWEEP_ATTACK, point.x, point.y - 0.25, point.z, 0.0, 0.0, 0.0)
  }

  private fun muzzle(
    entity: LivingEntity,
    particle: net.minecraft.core.particles.SimpleParticleType = ParticleTypes.FLAME
  ) {
    val look = entity.lookAngle.normalize()
    val right = Vec3(-look.z, 0.0, look.x).normalize()
    val point = entity.eyePosition.add(look.scale(0.8)).add(0.0, -0.18, 0.0)
    repeat(5) {
      entity.level().addParticle(particle, point.x, point.y, point.z, 0.0, 0.01, 0.0)
    }
    entity.level().addParticle(ParticleTypes.SMOKE, point.x, point.y, point.z, 0.0, 0.03, 0.0)
    val ejection = point.add(right.scale(0.35))
    entity.level().addParticle(
      ParticleTypes.ELECTRIC_SPARK,
      ejection.x,
      ejection.y,
      ejection.z,
      right.x * 0.08,
      0.04,
      right.z * 0.08
    )
    if (entity === Minecraft.getInstance().player) entity.xRot -= 1.2f
  }

  private fun rangedImpact(entity: LivingEntity, particle: net.minecraft.core.particles.SimpleParticleType) {
    val point = entity.eyePosition.add(entity.lookAngle.normalize().scale(3.0))
    repeat(4) {
      entity.level().addParticle(particle, point.x, point.y, point.z, 0.0, 0.02, 0.0)
    }
  }

  private fun heal(entity: LivingEntity) {
    repeat(8) { index ->
      val angle = index * Math.PI / 4.0
      entity.level().addParticle(
        ParticleTypes.HAPPY_VILLAGER,
        entity.x + kotlin.math.cos(angle) * 0.65,
        entity.y + 0.4 + index * 0.08,
        entity.z + kotlin.math.sin(angle) * 0.65,
        0.0,
        0.03,
        0.0
      )
    }
  }
}

object VanillaWeaponSoundService : WeaponSoundService {
  private val swordId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_sword_swing")
  private val rifleId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_rifle_fire")
  private val reloadId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_rifle_reload")
  private val staffId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_staff_cast")

  override fun play(entity: LivingEntity, sound: ResourceLocation) {
    val resolved = if (sound == ModTaczWeapons.RELOAD_SOUND_CUE_ID) {
      val stack = entity.mainHandItem
      val gun = stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun) ?: return
      val cue =
        if (stack.getOrDefault(WeaponStateComponents.AMMO, gun.capacity) == 0) "reload_empty" else "reload_tactical"
      (gun.assets.sounds[cue] ?: gun.assets.sounds["reload_empty"] ?: gun.assets.sounds["reload_tactical"])?.runtimeId
        ?: return
    } else sound
    val event = when (resolved) {
      swordId -> SoundEvents.PLAYER_ATTACK_SWEEP
      rifleId -> SoundEvents.FIREWORK_ROCKET_BLAST
      reloadId -> SoundEvents.ARMOR_EQUIP_IRON.value()
      staffId -> SoundEvents.EVOKER_CAST_SPELL
      else -> if (resolved.namespace == "zinecraft" && resolved.path.startsWith("tacz/")) {
        SoundEvent.createVariableRangeEvent(resolved)
      } else return
    }
    entity.level().playLocalSound(entity.x, entity.y, entity.z, event, SoundSource.PLAYERS, 0.8f, 1.0f, false)
  }
}
