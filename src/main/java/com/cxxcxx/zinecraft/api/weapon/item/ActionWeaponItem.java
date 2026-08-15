package com.cxxcxx.zinecraft.api.weapon.item;

import com.cxxcxx.zinecraft.api.weapon.WeaponInput;
import com.cxxcxx.zinecraft.api.weapon.WeaponServerController;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ActionWeaponItem extends Item {
  public ActionWeaponItem(@NotNull Properties properties) {
    super(properties);
  }

  @NotNull
  public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
    ItemStack itemStack = player.getItemInHand(hand);
    if (!level.isClientSide && player instanceof ServerPlayer) {
      WeaponServerController.INSTANCE.request((ServerPlayer) player, WeaponInput.SECONDARY, hand);
    }

    InteractionResultHolder interactionResultHolder = InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    return interactionResultHolder;
  }
}

