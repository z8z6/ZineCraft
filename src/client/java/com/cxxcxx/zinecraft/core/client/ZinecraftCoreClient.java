package com.cxxcxx.zinecraft.core.client;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.client.collection.CollectiblePresentationController;
import com.cxxcxx.zinecraft.core.client.entity.BlockbenchResidentModel;
import com.cxxcxx.zinecraft.core.client.entity.ResidentHumanoidRenderer;
import com.cxxcxx.zinecraft.core.client.entity.TerraCreatureModel;
import com.cxxcxx.zinecraft.core.client.entity.TerraCreatureRenderer;
import com.cxxcxx.zinecraft.core.client.music.TerraMusicController;
import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin;
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController;
import com.cxxcxx.zinecraft.core.registry.ModEntity;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Zinecraft.MOD_ID, value = Dist.CLIENT)
public final class ZinecraftCoreClient {
  private static final ResourceLocation SANKTA_RESIDENT_TEXTURE =
      Zinecraft.id("textures/entity/sankta_formal_resident.png");
  private static final ResourceLocation FELINE_RESIDENT_TEXTURE =
      Zinecraft.id("textures/entity/feline_victorian_resident.png");
  public static final ModelLayerLocation SANKTA_RESIDENT_LAYER = layer("sankta_formal_resident");
  public static final ModelLayerLocation FELINE_RESIDENT_LAYER = layer("feline_victorian_resident");
  public static final ModelLayerLocation SANDBEAST_LAYER = layer("sandbeast");
  public static final ModelLayerLocation RIVENBEAST_LAYER = layer("rivenbeast");
  public static final ModelLayerLocation CLAMPBEAST_LAYER = layer("clampbeast");
  public static final ModelLayerLocation PACKBEAST_LAYER = layer("packbeast");

  private ZinecraftCoreClient() {
  }

  @SubscribeEvent
  public static void setup(FMLClientSetupEvent event) {
    TerraMusicController.initialize();
    event.enqueueWork(() -> {
      EntityRenderers.register(ModEntity.SANKTA_FORMAL_RESIDENT.get(),
          context -> new ResidentHumanoidRenderer<>(context, SANKTA_RESIDENT_LAYER, SANKTA_RESIDENT_TEXTURE));
      EntityRenderers.register(ModEntity.FELINE_VICTORIAN_RESIDENT.get(),
          context -> new ResidentHumanoidRenderer<>(context, FELINE_RESIDENT_LAYER, FELINE_RESIDENT_TEXTURE));
      EntityRenderers.register(ModEntity.SANDBEAST.get(), context -> new TerraCreatureRenderer(
          context, SANDBEAST_LAYER, Zinecraft.id("textures/entity/sandbeast.png"), 0.7F));
      EntityRenderers.register(ModEntity.RIVENBEAST.get(), context -> new TerraCreatureRenderer(
          context, RIVENBEAST_LAYER, Zinecraft.id("textures/entity/rivenbeast.png"), 0.7F));
      EntityRenderers.register(ModEntity.CLAMPBEAST.get(), context -> new TerraCreatureRenderer(
          context, CLAMPBEAST_LAYER, Zinecraft.id("textures/entity/clampbeast.png"), 0.6F));
      EntityRenderers.register(ModEntity.PACKBEAST.get(), context -> new TerraCreatureRenderer(
          context, PACKBEAST_LAYER, Zinecraft.id("textures/entity/packbeast.png"), 0.8F));
      PonderIndex.addPlugin(ZinecraftPonderPlugin.INSTANCE);
      CollectiblePresentationController.initialize();
      WeaponPresentationController.initialize();
    });
  }

  @SubscribeEvent
  public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(SANKTA_RESIDENT_LAYER,
        () -> BlockbenchResidentModel.layer("sankta_formal_resident"));
    event.registerLayerDefinition(FELINE_RESIDENT_LAYER,
        () -> BlockbenchResidentModel.layer("feline_victorian_resident"));
    event.registerLayerDefinition(SANDBEAST_LAYER, TerraCreatureModel::sandbeastLayer);
    event.registerLayerDefinition(RIVENBEAST_LAYER, TerraCreatureModel::rivenbeastLayer);
    event.registerLayerDefinition(CLAMPBEAST_LAYER, TerraCreatureModel::clampbeastLayer);
    event.registerLayerDefinition(PACKBEAST_LAYER, TerraCreatureModel::packbeastLayer);
  }

  private static ModelLayerLocation layer(String path) {
    return new ModelLayerLocation(Zinecraft.id(path), "main");
  }
}
