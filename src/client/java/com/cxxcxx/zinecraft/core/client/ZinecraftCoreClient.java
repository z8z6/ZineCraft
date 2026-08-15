package com.cxxcxx.zinecraft.core.client;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.entity.LateranoCitizenRenderer;
import com.cxxcxx.zinecraft.core.client.entity.NationResidentRenderer;
import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController;
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczClientResourceBridge;
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczItemRenderer;
import com.cxxcxx.zinecraft.core.entity.ModEntities;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class ZinecraftCoreClient {
  private ZinecraftCoreClient() {
  }

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      PonderIndex.addPlugin(ZinecraftPonderPlugin.INSTANCE);
      WeaponPresentationController.initialize();
      TaczItemRenderer.INSTANCE.initialize();
      TaczClientResourceBridge.INSTANCE.initialize();
    });
  }

  @SubscribeEvent
  public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerEntityRenderer(ModEntities.INSTANCE.getLATERANO_CITIZEN().getType(), LateranoCitizenRenderer::new);
    for (var type : ModEntities.INSTANCE.getGENERIC_RESIDENT_TYPES()) {
      event.registerEntityRenderer(type, NationResidentRenderer::new);
    }
  }
}
