package com.cxxcxx.zinecraft.api.weapon.action;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface WeaponAction {
  @NotNull
  ResourceLocation getId();

  boolean canStart(@NotNull WeaponContext var1);

  @NotNull
  WeaponActionRuntime createRuntime(@NotNull WeaponContext var1);
}
