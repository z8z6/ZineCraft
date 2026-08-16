package com.cxxcxx.zinecraft.integration.tacz;

import com.tacz.guns.api.item.IGun;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Public-API-only TaCZ gun detection and identity lookup.
 */
public final class TaczGunResolver {
  @NotNull
  public Optional<ResourceLocation> resolve(@NotNull ItemStack stack) {
    IGun gun = IGun.getIGunOrNull(stack);
    return gun == null ? Optional.empty() : Optional.of(gun.getGunId(stack));
  }
}
