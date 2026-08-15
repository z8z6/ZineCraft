package com.cxxcxx.zinecraft.core.client.weapon;

import com.cxxcxx.zinecraft.api.weapon.state.WeaponStateComponents;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunPacks;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczGunSpec;
import com.cxxcxx.zinecraft.api.weapon.tacz.TaczSoundAsset;
import com.cxxcxx.zinecraft.core.weapon.ModTaczWeapons;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class VanillaWeaponSoundService implements WeaponSoundService {
  @NotNull
  public static final VanillaWeaponSoundService INSTANCE = new VanillaWeaponSoundService();
  private static final ResourceLocation swordId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_sword_swing");
  private static final ResourceLocation rifleId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_rifle_fire");
  private static final ResourceLocation reloadId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_rifle_reload");
  private static final ResourceLocation staffId = ResourceLocation.fromNamespaceAndPath("zinecraft", "sound/test_staff_cast");

  private VanillaWeaponSoundService() {
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ResourceLocation sound) {
    ResourceLocation var10000;
    if (java.util.Objects.equals(sound, ModTaczWeapons.INSTANCE.getRELOAD_SOUND_CUE_ID())) {
      ItemStack stack = entity.getMainHandItem();
      var10000 = (ResourceLocation) stack.get(WeaponStateComponents.INSTANCE.getTACZ_GUN_ID());
      if (var10000 == null) {
        return;
      }

      ResourceLocation var7 = var10000;
      TaczGunPacks var8 = TaczGunPacks.INSTANCE;
      ResourceLocation p0 = var7;
      int var10 = 0;
      TaczGunSpec var14 = var8.gun(p0);
      if (var14 == null) {
        return;
      }

      TaczGunSpec gun = var14;
      Integer var15 = (Integer) stack.getOrDefault(WeaponStateComponents.INSTANCE.getAMMO(), gun.getCapacity());
      String cue = var15 != null && var15 == 0 ? "reload_empty" : "reload_tactical";
      TaczSoundAsset var17 = (TaczSoundAsset) gun.getAssets().getSounds().get(cue);
      if (var17 == null) {
        var17 = (TaczSoundAsset) gun.getAssets().getSounds().get("reload_empty");
        if (var17 == null) {
          var17 = (TaczSoundAsset) gun.getAssets().getSounds().get("reload_tactical");
        }
      }

      if (var17 == null) {
        return;
      }

      var10000 = var17.getRuntimeId();
      if (var10000 == null) {
        return;
      }
    } else {
      var10000 = sound;
    }

    ResourceLocation resolved = var10000;
    ResourceLocation var12 = resolved;
    SoundEvent var18;
    if (java.util.Objects.equals(var12, swordId)) {
      var18 = SoundEvents.PLAYER_ATTACK_SWEEP;
    } else if (java.util.Objects.equals(var12, rifleId)) {
      var18 = SoundEvents.FIREWORK_ROCKET_BLAST;
    } else if (java.util.Objects.equals(var12, reloadId)) {
      var18 = (SoundEvent) SoundEvents.ARMOR_EQUIP_IRON.value();
    } else if (java.util.Objects.equals(var12, staffId)) {
      var18 = SoundEvents.EVOKER_CAST_SPELL;
    } else {
      if (!java.util.Objects.equals(resolved.getNamespace(), "zinecraft")) {
        return;
      }

      String var19 = resolved.getPath();
      if (!var19.startsWith("tacz/")) {
        return;
      }

      var18 = SoundEvent.createVariableRangeEvent(resolved);
    }

    SoundEvent event = var18;
    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), event, SoundSource.PLAYERS, 0.8F, 1.0F, false);
  }
}
