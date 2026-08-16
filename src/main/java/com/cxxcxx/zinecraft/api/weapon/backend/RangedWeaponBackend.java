package com.cxxcxx.zinecraft.api.weapon.backend;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface RangedWeaponBackend extends WeaponBackend {
  @NotNull
  Optional<ResourceLocation> weaponIdentity(@NotNull ItemStack stack);
}
