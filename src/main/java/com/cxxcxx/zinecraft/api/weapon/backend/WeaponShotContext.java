package com.cxxcxx.zinecraft.api.weapon.backend;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Immutable, backend-neutral facts that were available when a weapon fired.
 */
public record WeaponShotContext(
    @NotNull LivingEntity shooter,
    @NotNull ItemStack weapon,
    @NotNull Vec3 position,
    @NotNull Vec3 direction,
    @NotNull ResourceLocation weaponIdentity,
    @NotNull ResourceLocation backendId
) {
  public WeaponShotContext {
    Objects.requireNonNull(shooter, "shooter");
    weapon = Objects.requireNonNull(weapon, "weapon").copy();
    Objects.requireNonNull(position, "position");
    Objects.requireNonNull(direction, "direction");
    Objects.requireNonNull(weaponIdentity, "weaponIdentity");
    Objects.requireNonNull(backendId, "backendId");
  }
}
