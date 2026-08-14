package com.cxxcxx.zinecraft.core.client.mixin;

import com.cxxcxx.zinecraft.core.client.weapon.WeaponClientInput;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class WeaponAttackMixin {
  @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
  private void zinecraft$requestWeaponAction(CallbackInfoReturnable<Boolean> cir) {
    if (WeaponClientInput.requestPrimary()) {
      cir.setReturnValue(true);
    }
  }
}
