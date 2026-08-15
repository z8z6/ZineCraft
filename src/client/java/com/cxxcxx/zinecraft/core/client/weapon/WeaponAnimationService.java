package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface WeaponAnimationService {
  void play(@NotNull LivingEntity var1, @NotNull ItemStack var2, @NotNull ResourceLocation var3);

  void stop(@NotNull LivingEntity var1, @NotNull ItemStack var2, @NotNull ResourceLocation var3);
}
