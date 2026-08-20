package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import static com.cxxcxx.zinecraft.core.registry.ModWeaponPresentation.*;

public final class VanillaWeaponSoundService implements WeaponSoundService {
  @NotNull
  public static final VanillaWeaponSoundService INSTANCE = new VanillaWeaponSoundService();
  private VanillaWeaponSoundService() {
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ResourceLocation sound) {
    SoundEvent var18;
    if (sound.equals(TEST_SWORD_SWING.getId())) {
      var18 = SoundEvents.PLAYER_ATTACK_SWEEP;
    } else if (sound.equals(RIFLE_FIRE.getId())) {
      var18 = SoundEvents.FIREWORK_ROCKET_BLAST;
    } else if (sound.equals(RIFLE_RELOAD.getId())) {
      var18 = (SoundEvent) SoundEvents.ARMOR_EQUIP_IRON.value();
    } else if (sound.equals(STAFF_CAST.getId())) {
      var18 = SoundEvents.EVOKER_CAST_SPELL;
    } else return;

    SoundEvent event = var18;
    entity.level().playLocalSound(entity.getX(), entity.getY(), entity.getZ(), event, SoundSource.PLAYERS, 0.8F, 1.0F, false);
  }
}
