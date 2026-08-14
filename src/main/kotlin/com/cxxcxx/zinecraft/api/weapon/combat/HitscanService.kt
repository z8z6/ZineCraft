package com.cxxcxx.zinecraft.api.weapon.combat

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.ClipContext

object HitscanService {
  data class Hit(val target: LivingEntity, val distance: Double)

  /** 方块裁剪与目标选择都在服务端完成，客户端只显示近似弹道表现。 */
  fun trace(player: ServerPlayer, range: Double, radius: Double): Hit? {
    require(range > 0.0) { "射线范围必须大于 0" }
    require(radius >= 0.0) { "射线半径不能为负数" }

    val start = player.eyePosition
    val direction = player.getViewVector(1.0f).normalize()
    val intendedEnd = start.add(direction.scale(range))
    val blockHit = player.serverLevel().clip(
      ClipContext(start, intendedEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player)
    )
    val end = blockHit.location
    val searchBox = player.boundingBox.expandTowards(end.subtract(start)).inflate(radius + 1.0)
    var closest: Hit? = null

    for (target in player.serverLevel().getEntitiesOfClass(LivingEntity::class.java, searchBox)) {
      if (target === player || !target.isAlive || target.isSpectator || !player.canAttack(target)) continue
      val intersection = target.boundingBox.inflate(radius).clip(start, end).orElse(null) ?: continue
      val distance = start.distanceTo(intersection)
      if (closest == null || distance < closest.distance) closest = Hit(target, distance)
    }
    return closest
  }
}
