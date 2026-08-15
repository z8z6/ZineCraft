package com.cxxcxx.zinecraft.api.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SkillCastContext {
  @NotNull
  private final ServerPlayer player;
  @NotNull
  private final ItemStack stack;
  @NotNull
  private final InteractionHand hand;

  public SkillCastContext(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand) {
    super();
    this.player = player;
    this.stack = stack;
    this.hand = hand;
  }

  // $VF: synthetic method
  public static SkillCastContext copy$default(SkillCastContext var0, ServerPlayer var1, ItemStack var2, InteractionHand var3, int var4, Object var5) {
    if ((var4 & 1) != 0) {
      var1 = var0.player;
    }

    if ((var4 & 2) != 0) {
      var2 = var0.stack;
    }

    if ((var4 & 4) != 0) {
      var3 = var0.hand;
    }

    return var0.copy(var1, var2, var3);
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
  public final SkillCastContext copy(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand) {
    return new SkillCastContext(player, stack, hand);
  }

  @Override
  public int hashCode() {
    int i = this.player.hashCode();
    i = i * 31 + this.stack.hashCode();
    return i * 31 + this.hand.hashCode();
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof SkillCastContext skillCastContext)) {
      return false;
    } else if (!java.util.Objects.equals(this.player, skillCastContext.player)) {
      return false;
    } else {
      return !java.util.Objects.equals(this.stack, skillCastContext.stack) ? false : this.hand == skillCastContext.hand;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "SkillCastContext(player=" + this.player + ", stack=" + this.stack + ", hand=" + this.hand + ")";
  }
}

