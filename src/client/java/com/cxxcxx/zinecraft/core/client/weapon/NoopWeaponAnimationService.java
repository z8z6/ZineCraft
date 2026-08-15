package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class NoopWeaponAnimationService implements WeaponAnimationService {
  @NotNull
  public static final NoopWeaponAnimationService INSTANCE = new NoopWeaponAnimationService();

  private NoopWeaponAnimationService() {
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull ResourceLocation animation) {
  }

  @Override
  public void stop(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull ResourceLocation animation) {
  }
}
