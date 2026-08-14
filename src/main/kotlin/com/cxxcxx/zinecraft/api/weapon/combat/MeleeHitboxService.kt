package com.cxxcxx.zinecraft.api.weapon.combat

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import kotlin.math.cos

object MeleeHitboxService {
  /**
   * 第一阶段使用距离、视线与朝向锥体，保证命中由服务端空间状态决定。
   * 骨骼命中箱、延迟补偿等留给确有需求的后续阶段。
   */
  fun findTargets(player: ServerPlayer, range: Double, arcDegrees: Double): List<LivingEntity> {
    require(range > 0.0) { "近战范围必须大于 0" }
    require(arcDegrees in 0.0..360.0) { "近战弧度必须在 0 到 360 度之间" }

    val eye = player.eyePosition
    val facing = player.getViewVector(1.0f).normalize()
    val minimumDot = cos(Math.toRadians(arcDegrees / 2.0))
    val searchBox = player.boundingBox.inflate(range)
    return player.serverLevel().getEntitiesOfClass(LivingEntity::class.java, searchBox) { target ->
      if (target === player || !target.isAlive || target.isSpectator || !player.canAttack(target)) {
        false
      } else {
        val toTarget = target.boundingBox.center.subtract(eye)
        toTarget.lengthSqr() <= range * range &&
            facing.dot(if (toTarget.lengthSqr() == 0.0) Vec3.ZERO else toTarget.normalize()) >= minimumDot &&
            player.hasLineOfSight(target)
      }
    }
  }
}
