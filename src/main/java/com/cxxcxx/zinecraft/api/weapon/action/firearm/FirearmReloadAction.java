package com.cxxcxx.zinecraft.api.weapon.action.firearm;

import com.cxxcxx.zinecraft.api.weapon.action.TimedWeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponAction;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponActionRuntime;
import com.cxxcxx.zinecraft.api.weapon.action.WeaponContext;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.ranges.IntRange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public final class FirearmReloadAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;
  private final int reloadTick;
  private final int durationTicks;
  private final int capacity;
  @NotNull
  private final ItemLike ammunition;

  public FirearmReloadAction(@NotNull ResourceLocation id, int reloadTick, int durationTicks, int capacity, @NotNull ItemLike ammunition) {
    super();
    this.id = id;
    this.reloadTick = reloadTick;
    this.durationTicks = durationTicks;
    this.capacity = capacity;
    this.ammunition = ammunition;
    int i = this.durationTicks;
    int j = this.reloadTick;
    if (0 <= j ? j >= i : true) {
      j = 0;
      String string1 = "装填 tick 必须位于动作时间线内";
      throw new IllegalArgumentException(string1.toString());
    }

    if (this.capacity <= 0) {
      j = 0;
      String string = "弹容量必须大于 0";
      throw new IllegalArgumentException(string.toString());
    }
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    return ((Number) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), this.capacity)).intValue() >= this.capacity
        ? false
        : context.getPlayer().isCreative() || this.countAmmunition(context) > 0;
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    IntRange intRange = new IntRange(this.reloadTick, this.reloadTick);
    int i = this.durationTicks;
    return new TimedWeaponActionRuntime(intRange, i) {
      @Override
      protected void onTick(int tick) {
        if (tick == FirearmReloadAction.this.reloadTick) {
          Integer integer = (Integer) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), FirearmReloadAction.this.capacity);
          int l = FirearmReloadAction.this.capacity;
          int j = l - integer;
          if (j > 0) {
            int k = context.getPlayer().isCreative() ? j : FirearmReloadAction.this.consumeAmmunition(context, j);
            if (k > 0) {
              context.getStack().set(WeaponStateComponents.INSTANCE.getAMMO(), integer + k);
            }
          }
        }
      }
    };
  }

  private final int countAmmunition(WeaponContext context) {
    Inventory inventory = context.getPlayer().getInventory();
    int i = 0;
    int j = 0;

    for (int k = inventory.getContainerSize(); j < k; j++) {
      ItemStack itemStack = inventory.getItem(j);
      if (itemStack.is(this.ammunition.asItem())) {
        i += itemStack.getCount();
      }
    }

    return i;
  }

  private final int consumeAmmunition(WeaponContext context, int requested) {
    Inventory inventory = context.getPlayer().getInventory();
    int i = requested;
    int j = 0;

    for (int k = inventory.getContainerSize(); j < k; j++) {
      ItemStack itemStack = inventory.getItem(j);
      if (itemStack.is(this.ammunition.asItem())) {
        int l = Math.min(i, itemStack.getCount());
        itemStack.shrink(l);
        i -= l;
        if (i == 0) {
          break;
        }
      }
    }

    return requested - i;
  }
}
