package com.cxxcxx.zinecraft.api.weapon.action;

import com.cxxcxx.zinecraft.api.weapon.WeaponDefinition;
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
  private final WeaponDefinition definition;

  public WeaponContext(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand, @NotNull WeaponDefinition definition) {
    super();
    this.player = player;
    this.stack = stack;
    this.hand = hand;
    this.definition = definition;
  }

  // $VF: synthetic method
  public static WeaponContext copy$default(
      WeaponContext var0, ServerPlayer var1, ItemStack var2, InteractionHand var3, WeaponDefinition var4, int var5, Object var6
  ) {
    if ((var5 & 1) != 0) {
      var1 = var0.player;
    }

    if ((var5 & 2) != 0) {
      var2 = var0.stack;
    }

    if ((var5 & 4) != 0) {
      var3 = var0.hand;
    }

    if ((var5 & 8) != 0) {
      var4 = var0.definition;
    }

    return var0.copy(var1, var2, var3, var4);
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
  public final WeaponDefinition getDefinition() {
    return this.definition;
  }

  @NotNull
  public final ServerLevel getLevel() {
    ServerLevel serverLevel = this.player.serverLevel();
    return serverLevel;
  }

  @NotNull
  public final ServerPlayer component1() {
    return this.player;
  }

  @NotNull
  public final ItemStack component2() {
    return this.stack;
  }

  @NotNull
  public final InteractionHand component3() {
    return this.hand;
  }

  @NotNull
  public final WeaponDefinition component4() {
    return this.definition;
  }

  @NotNull
  public final WeaponContext copy(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand, @NotNull WeaponDefinition definition) {
    return new WeaponContext(player, stack, hand, definition);
  }

  @Override
  public int hashCode() {
    int i = this.player.hashCode();
    i = i * 31 + this.stack.hashCode();
    i = i * 31 + this.hand.hashCode();
    return i * 31 + this.definition.hashCode();
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
      return this.hand != weaponContext.hand ? false : java.util.Objects.equals(this.definition, weaponContext.definition);
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "WeaponContext(player=" + this.player + ", stack=" + this.stack + ", hand=" + this.hand + ", definition=" + this.definition + ")";
  }
}

