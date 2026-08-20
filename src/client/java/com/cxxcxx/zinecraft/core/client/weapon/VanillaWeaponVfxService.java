package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static com.cxxcxx.zinecraft.core.registry.ModWeaponPresentation.*;

public final class VanillaWeaponVfxService implements WeaponPresentationVfxService {
  @NotNull
  public static final VanillaWeaponVfxService INSTANCE = new VanillaWeaponVfxService();
  private VanillaWeaponVfxService() {
  }

  static void muzzleWithDefaults(VanillaWeaponVfxService var0, LivingEntity var1, SimpleParticleType var2, int var3, Object var4) {
    if ((var3 & 2) != 0) {
      SimpleParticleType var10000 = ParticleTypes.FLAME;
      var2 = var10000;
    }

    var0.muzzle(var1, var2);
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ResourceLocation effect) {
    if (effect.equals(TEST_SWORD_TRAIL.getId())) {
      this.trail(entity);
    } else if (effect.equals(TEST_SWORD_IMPACT.getId())) {
      this.impact(entity);
    } else if (effect.equals(RIFLE_MUZZLE.getId())) {
      muzzleWithDefaults(this, entity, null, 2, null);
    } else if (effect.equals(RIFLE_IMPACT.getId())) {
      SimpleParticleType var10002 = ParticleTypes.CRIT;
      this.rangedImpact(entity, var10002);
    } else if (effect.equals(STAFF_ARCANE_CAST.getId())) {
      SimpleParticleType var4 = ParticleTypes.ENCHANT;
      this.muzzle(entity, var4);
    } else if (effect.equals(STAFF_ARCANE_IMPACT.getId())) {
      SimpleParticleType var5 = ParticleTypes.WITCH;
      this.rangedImpact(entity, var5);
    } else if (effect.equals(STAFF_HEAL.getId())) {
      this.heal(entity);
    }
  }

  private final void trail(LivingEntity entity) {
    Level level = entity.level();
    Vec3 look = entity.getLookAngle().normalize();
    Vec3 right = new Vec3(-look.z, 0.0, look.x).normalize();
    Vec3 center = entity.getEyePosition().add(look.scale(1.25)).add(0.0, -0.4, 0.0);

    for (int index = -3; index < 4; index++) {
      Vec3 point = center.add(right.scale(index * 0.22)).add(0.0, 0.18 - index * index * 0.018, 0.0);
      level.addParticle((ParticleOptions) ParticleTypes.ELECTRIC_SPARK, point.x, point.y, point.z, 0.0, 0.01, 0.0);
    }
  }

  private final void impact(LivingEntity entity) {
    Vec3 point = entity.getEyePosition().add(entity.getLookAngle().normalize().scale(2.0));
    entity.level().addParticle((ParticleOptions) ParticleTypes.SWEEP_ATTACK, point.x, point.y - 0.25, point.z, 0.0, 0.0, 0.0);
  }

  private final void muzzle(LivingEntity entity, SimpleParticleType particle) {
    Vec3 look = entity.getLookAngle().normalize();
    Vec3 right = new Vec3(-look.z, 0.0, look.x).normalize();
    Vec3 point = entity.getEyePosition().add(look.scale(0.8)).add(0.0, -0.18, 0.0);
    byte ejection = 5;

    for (int it = 0; it < ejection; it++) {
      int var9 = 0;
      entity.level().addParticle((ParticleOptions) particle, point.x, point.y, point.z, 0.0, 0.01, 0.0);
    }

    entity.level().addParticle((ParticleOptions) ParticleTypes.SMOKE, point.x, point.y, point.z, 0.0, 0.03, 0.0);
    Vec3 ejectionx = point.add(right.scale(0.35));
    entity.level().addParticle((ParticleOptions) ParticleTypes.ELECTRIC_SPARK, ejectionx.x, ejectionx.y, ejectionx.z, right.x * 0.08, 0.04, right.z * 0.08);
    if (entity == Minecraft.getInstance().player) {
      LocalPlayer var11 = (LocalPlayer) entity;
      var11.setXRot(var11.getXRot() - 1.2F);
    }
  }

  private final void rangedImpact(LivingEntity entity, SimpleParticleType particle) {
    Vec3 point = entity.getEyePosition().add(entity.getLookAngle().normalize().scale(3.0));
    byte var4 = 4;

    for (int it = 0; it < var4; it++) {
      int var7 = 0;
      entity.level().addParticle((ParticleOptions) particle, point.x, point.y, point.z, 0.0, 0.02, 0.0);
    }
  }

  private final void heal(LivingEntity entity) {
    byte var2 = 8;

    for (int var3 = 0; var3 < var2; var3++) {
      int index = var3;
      int var5 = 0;
      double angle = index * Math.PI / 4.0;
      entity.level()
          .addParticle(
              (ParticleOptions) ParticleTypes.HAPPY_VILLAGER,
              entity.getX() + Math.cos(angle) * 0.65,
              entity.getY() + 0.4 + index * 0.08,
              entity.getZ() + Math.sin(angle) * 0.65,
              0.0,
              0.03,
              0.0
          );
    }
  }
}
