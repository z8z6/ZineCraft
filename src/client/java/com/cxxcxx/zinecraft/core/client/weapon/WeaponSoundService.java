package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface WeaponSoundService {
  void play(@NotNull LivingEntity var1, @NotNull ResourceLocation var2);
}
