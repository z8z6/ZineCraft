package com.cxxcxx.zinecraft.core.client;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.entity.FelineYsmModelBridge;
import com.cxxcxx.zinecraft.core.client.entity.SanktaYsmModelBridge;
import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController;
import com.cxxcxx.zinecraft.core.registry.ModEntity;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class ZinecraftCoreClient {
  private ZinecraftCoreClient() {
  }

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      EntityRenderers.register(ModEntity.SANKTA_FORMAL_RESIDENT.get(), NoopRenderer::new);
      EntityRenderers.register(ModEntity.FELINE_VICTORIAN_RESIDENT.get(), NoopRenderer::new);
      NeoForge.EVENT_BUS.addListener(SanktaYsmModelBridge::onEntityJoin);
      NeoForge.EVENT_BUS.addListener(FelineYsmModelBridge::onEntityJoin);
      PonderIndex.addPlugin(ZinecraftPonderPlugin.INSTANCE);
      WeaponPresentationController.initialize();
    });
  }
}
