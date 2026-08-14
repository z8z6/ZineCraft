package com.cxxcxx.zinecraft.core.client.mixin;

import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczPlayerAnimationService;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class TaczHumanoidAnimationMixin<T extends LivingEntity> {
  @Inject(method = "setupAnim", at = @At("TAIL"))
  private void zinecraft$applyTaczPlayerAnimation(
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      CallbackInfo ci
  ) {
    TaczPlayerAnimationService.apply(entity, (HumanoidModel<?>) (Object) this, limbSwingAmount);
  }
}
