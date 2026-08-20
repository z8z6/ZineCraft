package com.cxxcxx.zinecraft.api.weapon.action;

import com.cxxcxx.zinecraft.api.registry.builder.WeaponBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WeaponContext {
  @NotNull
  private final ServerPlayer player;
  @NotNull
  private final ItemStack stack;
  @NotNull
  private final InteractionHand hand;
  @NotNull
  private final WeaponBuilder weapon;

  public WeaponContext(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand, @NotNull WeaponBuilder weapon) {
    super();
    this.player = player;
    this.stack = stack;
    this.hand = hand;
    this.weapon = weapon;
  }

  @NotNull
  public final ServerPlayer getPlayer() {
    return this.player;
  }

  @NotNull
  public final ItemStack getStack() {
    return this.stack;
  }

  @NotNull
  public final InteractionHand getHand() {
    return this.hand;
  }

  @NotNull
  public final WeaponBuilder getWeapon() {
    return this.weapon;
  }

  @NotNull
  public final ServerLevel getLevel() {
    ServerLevel serverLevel = this.player.serverLevel();
    return serverLevel;
  }

  @Override
  public int hashCode() {
    int i = this.player.hashCode();
    i = i * 31 + this.stack.hashCode();
    i = i * 31 + this.hand.hashCode();
    return i * 31 + this.weapon.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof WeaponContext weaponContext)) {
      return false;
    } else if (!java.util.Objects.equals(this.player, weaponContext.player)) {
      return false;
    } else if (!java.util.Objects.equals(this.stack, weaponContext.stack)) {
      return false;
    } else {
      return this.hand == weaponContext.hand && java.util.Objects.equals(this.weapon, weaponContext.weapon);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponContext(player=" + this.player + ", stack=" + this.stack + ", hand=" + this.hand + ", weapon=" + this.weapon + ")";
  }
}

