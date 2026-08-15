package com.cxxcxx.zinecraft.api.weapon.item;

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public final class FirearmItem extends ActionWeaponItem {
  private final int capacity;

  public FirearmItem(int capacity, @NotNull Properties properties) {
    super(properties);
    this.capacity = capacity;
    if (this.capacity <= 0) {
      int i = 0;
      String string = "枪械弹容量必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }
  }

  public final int getCapacity() {
    return this.capacity;
  }

  public void appendHoverText(
      @NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    Integer integer = (Integer) stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), this.capacity);
    Collection aimingKey = tooltipComponents;
    Object[] objects = new Object[]{integer, this.capacity};
    MutableComponent mutableComponent = Component.translatable("item.zinecraft.firearm.ammo", objects).withStyle(ChatFormatting.YELLOW);
    aimingKey.add(mutableComponent);
    String string = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAIMING(), false)
        ? "item.zinecraft.firearm.aiming"
        : "item.zinecraft.firearm.hip_fire";
    tooltipComponents.add(Component.translatable(string).withStyle(ChatFormatting.GRAY));
  }
}

