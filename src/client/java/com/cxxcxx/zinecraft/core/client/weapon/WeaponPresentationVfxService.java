package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Client-only playback backend for Zinecraft WeaponPresentationBuilder timelines.
 */
public interface WeaponPresentationVfxService {
  void play(@NotNull LivingEntity entity, @NotNull ResourceLocation effect);
}
