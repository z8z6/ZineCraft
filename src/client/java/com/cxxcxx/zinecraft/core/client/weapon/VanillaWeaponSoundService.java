package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
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
    ResourceLocation resolved = sound;
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
    } else return;

    SoundEvent event = var18;
    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), event, SoundSource.PLAYERS, 0.8F, 1.0F, false);
  }
}
