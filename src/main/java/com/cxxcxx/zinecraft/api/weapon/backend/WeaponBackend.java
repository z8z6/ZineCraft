package com.cxxcxx.zinecraft.api.weapon.backend;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * A small boundary implemented by external or native weapon providers.
 */
public interface WeaponBackend {
  @NotNull
  ResourceLocation id();

  boolean supports(@NotNull ItemStack stack);
}
