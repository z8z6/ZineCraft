package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class TaczAmmoItem extends Item {
  public TaczAmmoItem(@NotNull Properties properties) {
    super(properties);
  }

  public void appendHoverText(
      @NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    if (tooltipFlag.isAdvanced()) {
      ResourceLocation resourceLocation1 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID());
      if (resourceLocation1 != null) {
        ResourceLocation resourceLocation = resourceLocation1;
        int i = 0;
        tooltipComponents.add(Component.literal("TaCZ " + resourceLocation).withStyle(ChatFormatting.DARK_GRAY));
      }
    }
  }

  @NotNull
  public Component getName(@NotNull ItemStack stack) {
    ResourceLocation resourceLocation2 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_AMMO_ID());
    TaczAmmoSpec taczAmmoSpec2;
    if (resourceLocation2 != null) {
      ResourceLocation resourceLocation = resourceLocation2;
      TaczGunPacks it = TaczGunPacks.INSTANCE;
      ResourceLocation resourceLocation1 = resourceLocation;
      int i = 0;
      taczAmmoSpec2 = it.ammo(resourceLocation1);
    } else {
      taczAmmoSpec2 = null;
    }

    TaczAmmoSpec taczAmmoSpec = taczAmmoSpec2;
    if (taczAmmoSpec != null) {
      TaczAmmoSpec taczAmmoSpec1 = taczAmmoSpec;
      int j = 0;
      MutableComponent mutableComponent = Component.translatable(taczAmmoSpec1.getTranslationKey());
      if (mutableComponent != null) {
        return (Component) mutableComponent;
      }
    }

    Component component = super.getName(stack);
    return component;
  }
}

