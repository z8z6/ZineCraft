package com.cxxcxx.zinecraft.core.client.compat.photon;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.weapon.VanillaWeaponVfxService;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationVfxService;
import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/**
 * Photon-backed weapon cues. Any individual failure degrades to the vanilla particle backend.
 */
public final class PhotonWeaponVfxService implements WeaponPresentationVfxService {
  public static final PhotonWeaponVfxService INSTANCE = new PhotonWeaponVfxService();

  private PhotonWeaponVfxService() {
  }

  private static Vector3f offset(LivingEntity entity, ResourceLocation effect) {
    if (effect.equals(PhotonWeaponEffects.EXPLOSION)) {
      Vec3 look = entity.getLookAngle().normalize().scale(3.0);
      return new Vector3f((float) look.x, (float) look.y, (float) look.z);
    }
    if (effect.equals(PhotonWeaponEffects.SWORD_SLASH)) {
      Vec3 look = entity.getLookAngle().normalize().scale(1.2).add(0, -0.35, 0);
      return new Vector3f((float) look.x, (float) look.y, (float) look.z);
    }
    return new Vector3f(0, -1.25F, 0);
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ResourceLocation effect) {
    var fx = PhotonWeaponEffects.get(effect);
    if (fx == null) {
      VanillaWeaponVfxService.INSTANCE.play(entity, effect);
      return;
    }

    try {
      EntityEffectExecutor executor = new EntityEffectExecutor(
          fx,
          entity.level(),
          entity,
          effect.equals(PhotonWeaponEffects.SWORD_SLASH)
              ? EntityEffectExecutor.AutoRotate.LOOK
              : EntityEffectExecutor.AutoRotate.NONE
      );
      executor.setAllowMulti(true);
      executor.setOffset(offset(entity, effect));
      executor.start();
    } catch (RuntimeException exception) {
      Zinecraft.INSTANCE.getLogger().warn("Photon failed to play weapon effect {}; using vanilla particles", effect, exception);
      VanillaWeaponVfxService.INSTANCE.play(entity, effect);
    }
  }
}
