package com.cxxcxx.zinecraft.api.weapon.combat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class HitscanService {
  @NotNull
  public static final HitscanService INSTANCE = new HitscanService();

  private HitscanService() {
  }

  @Nullable
  public final HitscanService.Hit trace(@NotNull ServerPlayer player, double range, double radius) {
    if (!(range > 0.0)) {
      int j = 0;
      String string1 = "射线范围必须大于 0";
      throw new IllegalArgumentException(string1.toString());
    }

    if (!(radius >= 0.0)) {
      int i = 0;
      String string = "射线半径不能为负数";
      throw new IllegalArgumentException(string.toString());
    }

    Vec3 vec3 = player.getEyePosition();
    Vec3 vec31 = player.getViewVector(1.0F).normalize();
    Vec3 vec32 = vec3.add(vec31.scale(range));
    BlockHitResult blockHitResult = player.serverLevel().clip(new ClipContext(vec3, vec32, Block.COLLIDER, Fluid.NONE, (Entity) player));
    Vec3 vec33 = blockHitResult.getLocation();
    AABB aABB = player.getBoundingBox().expandTowards(vec33.subtract(vec3)).inflate(radius + 1.0);
    HitscanService.Hit hit = null;

    for (LivingEntity livingEntity : player.serverLevel().getEntitiesOfClass(LivingEntity.class, aABB)) {
      if (livingEntity != player && livingEntity.isAlive() && !livingEntity.isSpectator() && player.canAttack(livingEntity)) {
        Vec3 vec35 = (Vec3) livingEntity.getBoundingBox().inflate(radius).clip(vec3, vec33).orElse(null);
        if (vec35 != null) {
          Vec3 vec34 = vec35;
          double d = vec3.distanceTo(vec34);
          if (hit == null || d < hit.getDistance()) {
            hit = new HitscanService.Hit(livingEntity, d);
          }
        }
      }
    }

    return hit;
  }

  public static final class Hit {
    @NotNull
    private final LivingEntity target;
    private final double distance;

    public Hit(@NotNull LivingEntity target, double distance) {
      super();
      this.target = target;
      this.distance = distance;
    }

    // $VF: synthetic method
    public static HitscanService.Hit copy$default(HitscanService.Hit var0, LivingEntity var1, double var2, int var4, Object var5) {
      if ((var4 & 1) != 0) {
        var1 = var0.target;
      }

      if ((var4 & 2) != 0) {
        var2 = var0.distance;
      }

      return var0.copy(var1, var2);
    }

    @NotNull
    public final LivingEntity getTarget() {
      return this.target;
    }

    public final double getDistance() {
      return this.distance;
    }

    @NotNull
    public final LivingEntity component1() {
      return this.target;
    }

    public final double component2() {
      return this.distance;
    }

    @NotNull
    public final HitscanService.Hit copy(@NotNull LivingEntity target, double distance) {
      return new HitscanService.Hit(target, distance);
    }

    @Override
    public int hashCode() {
      int i = this.target.hashCode();
      return i * 31 + Double.hashCode(this.distance);
    }

    @Override
    public boolean equals(@Nullable Object other) {
      if (this == other) {
        return true;
      } else if (!(other instanceof HitscanService.Hit hit)) {
        return false;
      } else {
        return !java.util.Objects.equals(this.target, hit.target) ? false : Double.compare(this.distance, hit.distance) == 0;
      }
    }

    @NotNull
    @Override
    public String toString() {
      return "Hit(target=" + this.target + ", distance=" + this.distance + ")";
    }
  }
}

