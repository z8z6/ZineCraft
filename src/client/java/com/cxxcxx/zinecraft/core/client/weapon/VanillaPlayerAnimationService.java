package com.cxxcxx.zinecraft.core.client.weapon;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public final class VanillaPlayerAnimationService implements PlayerAnimationService {
  @NotNull
  public static final VanillaPlayerAnimationService INSTANCE = new VanillaPlayerAnimationService();

  private VanillaPlayerAnimationService() {
  }

  @Override
  public void play(@NotNull LivingEntity entity, @NotNull ResourceLocation animation) {
    entity.swing(InteractionHand.MAIN_HAND);
  }

  @Override
  public void stop(@NotNull LivingEntity entity, @NotNull ResourceLocation animation) {
  }
}
