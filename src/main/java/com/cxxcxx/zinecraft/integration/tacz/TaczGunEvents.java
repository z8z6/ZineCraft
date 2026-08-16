package com.cxxcxx.zinecraft.integration.tacz;

import com.cxxcxx.zinecraft.api.weapon.backend.WeaponShotContext;
import com.cxxcxx.zinecraft.api.weapon.event.WeaponShotEvent;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.tacz.guns.api.event.common.GunFireEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Converts each accepted server-side TaCZ discharge into a backend-neutral event.
 */
@EventBusSubscriber(modid = Zinecraft.MOD_ID)
public final class TaczGunEvents {
  private TaczGunEvents() {
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onGunFire(GunFireEvent event) {
    if (event.getLogicalSide() != LogicalSide.SERVER || event.isCanceled()) return;

    ItemStack stack = event.getGunItemStack();
    ResourceLocation identity = TaczWeaponBackend.INSTANCE.weaponIdentity(stack).orElse(null);
    if (identity == null) return;

    LivingEntity shooter = event.getShooter();
    WeaponShotContext context = new WeaponShotContext(
        shooter,
        stack,
        shooter.getEyePosition(),
        shooter.getViewVector(1.0F),
        identity,
        TaczWeaponBackend.INSTANCE.id()
    );
    NeoForge.EVENT_BUS.post(new WeaponShotEvent(context));
  }
}
