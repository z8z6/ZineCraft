package com.cxxcxx.zinecraft.core.client;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.entity.FelineYsmModelBridge;
import com.cxxcxx.zinecraft.core.client.entity.SanktaYsmModelBridge;
import com.cxxcxx.zinecraft.core.client.entity.TerraCreatureModel;
import com.cxxcxx.zinecraft.core.client.entity.TerraCreatureRenderer;
import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController;
import com.cxxcxx.zinecraft.core.registry.ModEntity;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class ZinecraftCoreClient {
  public static final ModelLayerLocation SANDBEAST_LAYER = layer("sandbeast");
  public static final ModelLayerLocation RIVENBEAST_LAYER = layer("rivenbeast");
  public static final ModelLayerLocation CLAMPBEAST_LAYER = layer("clampbeast");
  public static final ModelLayerLocation PACKBEAST_LAYER = layer("packbeast");

  private ZinecraftCoreClient() {
  }

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    event.enqueueWork(() -> {
      EntityRenderers.register(ModEntity.SANKTA_FORMAL_RESIDENT.get(), NoopRenderer::new);
      EntityRenderers.register(ModEntity.FELINE_VICTORIAN_RESIDENT.get(), NoopRenderer::new);
      EntityRenderers.register(ModEntity.SANDBEAST.get(), context -> new TerraCreatureRenderer(
          context, SANDBEAST_LAYER, Zinecraft.id("textures/entity/sandbeast.png"), 0.7F));
      EntityRenderers.register(ModEntity.RIVENBEAST.get(), context -> new TerraCreatureRenderer(
          context, RIVENBEAST_LAYER, Zinecraft.id("textures/entity/rivenbeast.png"), 0.7F));
      EntityRenderers.register(ModEntity.CLAMPBEAST.get(), context -> new TerraCreatureRenderer(
          context, CLAMPBEAST_LAYER, Zinecraft.id("textures/entity/clampbeast.png"), 0.6F));
      EntityRenderers.register(ModEntity.PACKBEAST.get(), context -> new TerraCreatureRenderer(
          context, PACKBEAST_LAYER, Zinecraft.id("textures/entity/packbeast.png"), 0.8F));
      NeoForge.EVENT_BUS.addListener(SanktaYsmModelBridge::onEntityJoin);
      NeoForge.EVENT_BUS.addListener(FelineYsmModelBridge::onEntityJoin);
      PonderIndex.addPlugin(ZinecraftPonderPlugin.INSTANCE);
      WeaponPresentationController.initialize();
    });
  }

  @SubscribeEvent
  public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(SANDBEAST_LAYER, TerraCreatureModel::sandbeastLayer);
    event.registerLayerDefinition(RIVENBEAST_LAYER, TerraCreatureModel::rivenbeastLayer);
    event.registerLayerDefinition(CLAMPBEAST_LAYER, TerraCreatureModel::clampbeastLayer);
    event.registerLayerDefinition(PACKBEAST_LAYER, TerraCreatureModel::packbeastLayer);
  }

  private static ModelLayerLocation layer(String path) {
    return new ModelLayerLocation(Zinecraft.id(path), "main");
  }
}
