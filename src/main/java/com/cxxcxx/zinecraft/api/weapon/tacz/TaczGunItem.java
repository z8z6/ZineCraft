package com.cxxcxx.zinecraft.api.weapon.tacz;

import com.cxxcxx.zinecraft.api.weapon.item.ActionWeaponItem;
import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import kotlin.ranges.RangesKt;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public final class TaczGunItem extends ActionWeaponItem {
  public TaczGunItem(@NotNull Properties properties) {
    super(properties);
  }

  public void appendHoverText(
      @NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    ResourceLocation resourceLocation1 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    if (resourceLocation1 != null) {
      ResourceLocation resourceLocation = resourceLocation1;
      TaczGunSpec taczGunSpec1 = TaczGunPacks.INSTANCE.gun(resourceLocation);
      if (taczGunSpec1 != null) {
        TaczGunSpec taczGunSpec = taczGunSpec1;
        Object object = stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), taczGunSpec.getCapacity());
        int i = RangesKt.coerceAtMost(((Number) object).intValue(), taczGunSpec.getCapacity());
        Collection fireMode = tooltipComponents;
        Object[] objects = new Object[]{i, taczGunSpec.getCapacity()};
        MutableComponent mutableComponent = Component.translatable("item.zinecraft.firearm.ammo", objects).withStyle(ChatFormatting.YELLOW);
        fireMode.add(mutableComponent);
        fireMode = tooltipComponents;
        objects = new Object[]{taczGunSpec.getAmmoId()};
        MutableComponent mutableComponent1 = Component.translatable("item.zinecraft.tacz_gun.caliber", objects).withStyle(ChatFormatting.GRAY);
        fireMode.add(mutableComponent1);
        fireMode = tooltipComponents;
        objects = new Object[]{taczGunSpec.getDamage(), taczGunSpec.getRpm()};
        MutableComponent mutableComponent2 = Component.translatable("item.zinecraft.tacz_gun.stats", objects).withStyle(ChatFormatting.GRAY);
        fireMode.add(mutableComponent2);
        Integer integer = (Integer) stack.getOrDefault(WeaponStateComponents.INSTANCE.getFIRE_MODE(), 0);
        String string2;
        if (integer != null && integer == 0) {
          string2 = "auto";
        } else {
          byte b = 1;
          if (integer != null && integer == b) {
            string2 = "semi";
          } else {
            b = 2;
            string2 = integer != null && integer == b ? "burst" : "unknown";
          }
        }

        String string1 = string2;
        Collection collection1 = tooltipComponents;
        Object[] objects1 = new Object[]{Component.translatable("item.zinecraft.tacz_gun.fire_mode." + string1)};
        MutableComponent mutableComponent3 = Component.translatable("item.zinecraft.tacz_gun.fire_mode", objects1).withStyle(ChatFormatting.GRAY);
        collection1.add(mutableComponent3);
        string2 = taczGunSpec.getTooltipKey();
        if (string2 != null) {
          String string = string2;
          int j = 0;
          tooltipComponents.add(Component.translatable(string).withStyle(ChatFormatting.DARK_GRAY));
        }

        if (tooltipFlag.isAdvanced()) {
          tooltipComponents.add(
              Component.literal("TaCZ " + taczGunSpec.getId() + " · " + taczGunSpec.getPack().getSourceName()).withStyle(ChatFormatting.DARK_GRAY)
          );
        }
      }
    }
  }

  @NotNull
  public Component getName(@NotNull ItemStack stack) {
    ResourceLocation resourceLocation2 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
    TaczGunSpec taczGunSpec2;
    if (resourceLocation2 != null) {
      ResourceLocation resourceLocation = resourceLocation2;
      TaczGunPacks it = TaczGunPacks.INSTANCE;
      ResourceLocation resourceLocation1 = resourceLocation;
      int i = 0;
      taczGunSpec2 = it.gun(resourceLocation1);
    } else {
      taczGunSpec2 = null;
    }

    TaczGunSpec taczGunSpec = taczGunSpec2;
    if (taczGunSpec != null) {
      TaczGunSpec taczGunSpec1 = taczGunSpec;
      int j = 0;
      MutableComponent mutableComponent = Component.translatable(taczGunSpec1.getTranslationKey());
      if (mutableComponent != null) {
        return (Component) mutableComponent;
      }
    }

    Component component = super.getName(stack);
    return component;
  }
}

