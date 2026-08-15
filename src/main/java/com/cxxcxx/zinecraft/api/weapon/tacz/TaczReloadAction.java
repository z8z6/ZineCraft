package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.action.*;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class TaczReloadAction implements WeaponAction {
  @NotNull
  private final ResourceLocation id;

  public TaczReloadAction(@NotNull ResourceLocation id) {
    super();
    this.id = id;
  }

  @NotNull
  @Override
  public ResourceLocation getId() {
    return this.id;
  }

  @Override
  public boolean canStart(@NotNull WeaponContext context) {
    TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
    if (taczGunSpec1 == null) {
      return false;
    }

    TaczGunSpec taczGunSpec = taczGunSpec1;
    return ((Number) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity())).intValue()
        >= taczGunSpec.getCapacity()
        ? false
        : context.getPlayer().isCreative() || this.countAmmo(context, taczGunSpec.getAmmoId()) > 0;
  }

  @NotNull
  @Override
  public WeaponActionRuntime createRuntime(@NotNull final WeaponContext context) {
    TaczGunSpec taczGunSpec1 = TaczWeaponActions.gun(context);
    if (taczGunSpec1 == null) {
      String string = "Required value was null.";
      throw new IllegalArgumentException(string.toString());
    }

    final TaczGunSpec taczGunSpec = taczGunSpec1;
    final boolean bl = java.util.Objects.equals(taczGunSpec.getFeedType(), "manual");
    Integer integer = (Integer) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity());
    int m;
    if (bl) {
      int l = taczGunSpec.getCapacity();
      m = Math.max(l - integer, 1);
    } else {
      m = 1;
    }

    int i = m;
    final int j = bl ? taczGunSpec.getReloadFeedTicks() * i : taczGunSpec.getReloadFeedTicks();
    int k = Math.max(j + (taczGunSpec.getReloadDurationTicks() - taczGunSpec.getReloadFeedTicks()), 1);
    TickRange intRange = new TickRange(taczGunSpec.getReloadFeedTicks(), j);
    return new TimedWeaponActionRuntime(intRange, k) {
      @Override
      protected void onTick(int tick) {
        if (tick >= taczGunSpec.getReloadFeedTicks() && tick <= j && tick % taczGunSpec.getReloadFeedTicks() == 0) {
          Integer integer1 = (Integer) context.getStack().getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity());
          int q = taczGunSpec.getCapacity();
          int n = q - integer1;
          if (n > 0) {
            int o = bl ? 1 : n;
            int p = context.getPlayer().isCreative() ? o : TaczReloadAction.this.consumeAmmo(context, taczGunSpec.getAmmoId(), o);
            if (p > 0) {
              context.getStack().set(WeaponStateComponents.INSTANCE.getAMMO(), integer1 + p);
            }

            if (p > 0) {
              context.getStack().set(WeaponStateComponents.INSTANCE.getNEEDS_BOLT(), false);
            }
          }
        }
      }
    };
  }

  private final int countAmmo(WeaponContext context, ResourceLocation id) {
    int i = 0;
    Inventory inventory = context.getPlayer().getInventory();
    int j = 0;

    for (int k = inventory.getContainerSize(); j < k; j++) {
      ItemStack itemStack = inventory.getItem(j);
      if (java.util.Objects.equals(itemStack.get(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID()), id)) {
        i += itemStack.getCount();
      }
    }

    return i;
  }

  private final int consumeAmmo(WeaponContext context, ResourceLocation id, int requested) {
    int i = requested;
    Inventory inventory = context.getPlayer().getInventory();
    int j = 0;

    for (int k = inventory.getContainerSize(); j < k; j++) {
      ItemStack itemStack = inventory.getItem(j);
      if (java.util.Objects.equals(itemStack.get(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID()), id)) {
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

