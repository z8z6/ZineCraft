package com.cxxcxx.zinecraft.api.weapon.combat;

import kotlin.jvm.functions.Function1;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MeleeHitboxService {
  @NotNull
  public static final MeleeHitboxService INSTANCE = new MeleeHitboxService();

  private MeleeHitboxService() {
  }

  private static final boolean findTargets$lambda$2(ServerPlayer $player, Vec3 $eye, double $range, Vec3 $facing, double $minimumDot, LivingEntity target) {
    boolean bl;
    if (target != $player && target.isAlive() && !target.isSpectator() && $player.canAttack(target)) {
      Vec3 vec3 = target.getBoundingBox().getCenter().subtract($eye);
      bl = vec3.lengthSqr() <= $range * $range
          && $facing.dot(vec3.lengthSqr() == 0.0 ? Vec3.ZERO : vec3.normalize()) >= $minimumDot
          && $player.hasLineOfSight((Entity) target);
    } else {
      bl = false;
    }

    return bl;
  }

  private static final boolean findTargets$lambda$3(Function1 $tmp0, Object p0) {
    return (Boolean) $tmp0.invoke(p0);
  }

  @NotNull
  public final List<LivingEntity> findTargets(@NotNull ServerPlayer player, double range, double arcDegrees) {
    if (!(range > 0.0)) {
      int j = 0;
      String string1 = "近战范围必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    } else if (0.0 <= arcDegrees ? !(arcDegrees <= 360.0) : true) {
      int i = 0;
      String string = "近战弧度必须在 0 到 360 度之间";
      throw new IllegalArgumentException(string.toString());
    } else {
      Vec3 vec3 = player.getEyePosition();
      Vec3 vec31 = player.getViewVector(1.0F).normalize();
      double d = Math.cos(Math.toRadians(arcDegrees / 2.0));
      AABB aABB = player.getBoundingBox().inflate(range);
      List<LivingEntity> list = player.serverLevel().getEntitiesOfClass(
          LivingEntity.class, aABB,
          target -> findTargets$lambda$2(player, vec3, range, vec31, d, target)
      );
      return list;
    }
  }
}

