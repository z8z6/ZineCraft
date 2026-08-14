package com.cxxcxx.zinecraft.core.client.weapon.tacz

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks
import com.cxxcxx.zinecraft.core.client.weapon.PlayerAnimationService
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons
import net.minecraft.client.model.HumanoidModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Pose
import net.minecraft.world.InteractionHand
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.PI

/** Plays TaCZ's PlayerAnimator-format JSON without requiring a Forge-only TaCZ binary. */
object TaczPlayerAnimationService : PlayerAnimationService {
  private data class OneShot(val clip: String, val startedAtNanos: Long)

  private val animations = ConcurrentHashMap<String, Map<String, TaczAnimationClip>>()
  private val oneShots = ConcurrentHashMap<UUID, OneShot>()

  override fun play(entity: LivingEntity, animation: ResourceLocation) {
    val aiming = entity.mainHandItem.getOrDefault(WeaponStateComponents.AIMING, false)
    val prone = isProne(entity)
    val clip = when (animation) {
      ModTaczWeapons.PLAYER_FIRE_ANIMATION_ID -> if (prone) {
        if (aiming) "lie_aim_fire" else "lie_normal_fire"
      } else if (aiming) "aim_fire_upper" else "normal_fire_upper"

      ModTaczWeapons.PLAYER_RELOAD_ANIMATION_ID -> if (prone) "lie_reload" else "reload_upper"
      ModTaczWeapons.PLAYER_MELEE_ANIMATION_ID -> "melee_${entity.random.nextInt(3) + 1}_upper".replace(
        "melee_1",
        "melee"
      )

      else -> return entity.swing(InteractionHand.MAIN_HAND)
    }
    oneShots[entity.uuid] = OneShot(clip, System.nanoTime())
  }

  override fun stop(entity: LivingEntity, animation: ResourceLocation) = Unit

  @JvmStatic
  fun apply(entity: LivingEntity, model: HumanoidModel<*>, limbSwingAmount: Float) {
    val stack = entity.mainHandItem
    val gun = stack.get(WeaponStateComponents.TACZ_GUN_ID)?.let(TaczGunPacks::gun) ?: return
    if (entity.pose == Pose.SLEEPING || entity.pose == Pose.FALL_FLYING || entity.onClimbable() || entity.isSwimming) return
    val path = gun.assets.playerAnimationPath
    val clips = path?.let {
      animations.computeIfAbsent(it) { source ->
        TaczGunPacks.snapshot.open(source)?.use(TaczBedrockParser::animations) ?: emptyMap()
      }
    } ?: emptyMap()

    if (clips.isEmpty()) {
      applyFallback(model, stack.getOrDefault(WeaponStateComponents.AIMING, false))
      return
    }

    val now = System.nanoTime()
    val moving = limbSwingAmount > 0.05f
    val prone = isProne(entity)
    val aiming = stack.getOrDefault(WeaponStateComponents.AIMING, false)
    val upperName = when {
      prone && moving -> "lie_move"
      prone && aiming -> "lie_aim"
      prone -> "lie"
      aiming -> "aim_upper"
      entity.isSprinting && entity.pose == Pose.CROUCHING -> "crouch_walk_upper"
      entity.isSprinting -> "run_upper"
      moving && entity.pose == Pose.CROUCHING -> "crouch_walk_upper"
      moving -> "walk_upper"
      else -> "hold_upper"
    }
    val lowerName = when {
      prone -> null
      entity.vehicle != null -> "ride_lower"
      entity.isSprinting && entity.pose == Pose.CROUCHING -> "crouch_walk_lower"
      entity.isSprinting -> "run_lower"
      moving && entity.pose == Pose.CROUCHING -> "crouch_walk_lower"
      moving -> "walk_lower"
      entity.pose == Pose.CROUCHING -> "crouch_lower"
      else -> "hold_lower"
    }
    val time = entity.tickCount / 20f
    lowerName?.let { clips[it]?.sample(time)?.let { transforms -> applyTransforms(model, transforms) } }
    clips[upperName]?.sample(time)?.let { applyTransforms(model, it) }

    oneShots[entity.uuid]?.let { shot ->
      val clip = clips[shot.clip]
      val elapsed = (now - shot.startedAtNanos) / 1_000_000_000f
      if (clip == null || elapsed > clip.length) oneShots.remove(entity.uuid, shot)
      else applyTransforms(model, clip.sample(elapsed))
    }
  }

  private fun applyTransforms(model: HumanoidModel<*>, transforms: Map<String, TaczBoneTransform>) {
    apply(model.head, transforms["head"])
    apply(model.body, transforms["body"])
    apply(model.rightArm, transforms["right_arm"])
    apply(model.leftArm, transforms["left_arm"])
    apply(model.rightLeg, transforms["right_leg"])
    apply(model.leftLeg, transforms["left_leg"])
  }

  private fun apply(part: ModelPart, transform: TaczBoneTransform?) {
    transform ?: return
    val radians = (PI / 180.0).toFloat()
    part.xRot = transform.rotation.x * radians
    part.yRot = transform.rotation.y * radians
    part.zRot = transform.rotation.z * radians
    part.x += transform.position.x
    part.y -= transform.position.y
    part.z += transform.position.z
  }

  private fun applyFallback(model: HumanoidModel<*>, aiming: Boolean) {
    val head = model.head
    val pitch = if (aiming) -1.6f else -1.4f
    model.rightArm.yRot = head.yRot - if (aiming) 0.35f else 0.3f
    model.leftArm.yRot = head.yRot + 0.8f
    model.rightArm.xRot = head.xRot + pitch
    model.leftArm.xRot = head.xRot + pitch
  }

  private fun isProne(entity: LivingEntity): Boolean = !entity.isSwimming && entity.pose == Pose.SWIMMING
}
