package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.entity.TerraBeastEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class TerraCreatureRenderer extends MobRenderer<TerraBeastEntity, TerraCreatureModel<TerraBeastEntity>> {
  private final ResourceLocation texture;

  public TerraCreatureRenderer(
      EntityRendererProvider.Context context,
      ModelLayerLocation layer,
      ResourceLocation texture,
      float shadowRadius
  ) {
    super(context, new TerraCreatureModel<>(context.bakeLayer(layer)), shadowRadius);
    this.texture = texture;
  }

  @Override
  public ResourceLocation getTextureLocation(TerraBeastEntity entity) {
    return texture;
  }
}
