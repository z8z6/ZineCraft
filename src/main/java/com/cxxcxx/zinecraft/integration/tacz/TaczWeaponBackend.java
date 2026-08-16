package com.cxxcxx.zinecraft.integration.tacz;

import com.cxxcxx.zinecraft.api.weapon.backend.RangedWeaponBackend;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class TaczWeaponBackend implements RangedWeaponBackend {
  public static final TaczWeaponBackend INSTANCE = new TaczWeaponBackend();
  private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("zinecraft", "tacz");
  private final TaczGunResolver resolver = new TaczGunResolver();

  private TaczWeaponBackend() {
  }

  @Override
  public @NotNull ResourceLocation id() {
    return ID;
  }

  @Override
  public boolean supports(@NotNull ItemStack stack) {
    return resolver.resolve(stack).isPresent();
  }

  @Override
  public @NotNull Optional<ResourceLocation> weaponIdentity(@NotNull ItemStack stack) {
    return resolver.resolve(stack);
  }
}
