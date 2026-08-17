package com.cxxcxx.zinecraft.api.skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record SkillCastContext(@NotNull ServerPlayer player, @NotNull ItemStack stack, @NotNull InteractionHand hand) {

  @Override
  public boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else if (!(other instanceof SkillCastContext(ServerPlayer player1, ItemStack stack1, InteractionHand hand1))) {
      return false;
    } else if (!Objects.equals(this.player, player1)) {
      return false;
    } else {
      return Objects.equals(this.stack, stack1) && this.hand == hand1;
    }
  }

  @NotNull
  @Override
  public String toString() {
    return "SkillCastContext(player=" + this.player + ", stack=" + this.stack + ", hand=" + this.hand + ")";
  }
}

