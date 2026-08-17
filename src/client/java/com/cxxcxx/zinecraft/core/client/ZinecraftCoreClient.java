package com.cxxcxx.zinecraft.core.client;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.entity.LateranoCitizenRenderer;
import com.cxxcxx.zinecraft.core.client.entity.NationResidentRenderer;
import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController;
import com.cxxcxx.zinecraft.core.entity.ModEntity;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class ZinecraftCoreClient {
  private ZinecraftCoreClient() {
  }

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      PonderIndex.addPlugin(ZinecraftPonderPlugin.INSTANCE);
      WeaponPresentationController.initialize();
    });
  }

  @SubscribeEvent
  public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(ModEntity.LATERANO_CITIZEN.get(), LateranoCitizenRenderer::new);
    for (var resident : ModEntity.GENERIC_RESIDENTS) {
      event.registerEntityRenderer(resident.get(), NationResidentRenderer::new);
    }
  }
}
