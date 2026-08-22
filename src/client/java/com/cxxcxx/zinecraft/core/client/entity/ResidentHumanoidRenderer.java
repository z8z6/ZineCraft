package com.cxxcxx.zinecraft.core.client.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;

/**
 * Renders resident mobs with Minecraft's player model and built-in humanoid animations.
 */
public final class ResidentHumanoidRenderer<T extends PathfinderMob>
    extends HumanoidMobRenderer<T, BlockbenchResidentModel<T>> {
  private final ResourceLocation texture;

  public ResidentHumanoidRenderer(
      EntityRendererProvider.Context context,
      ModelLayerLocation layer,
      ResourceLocation texture
  ) {
    super(context, new BlockbenchResidentModel<>(context.bakeLayer(layer)), 0.5F);
    this.texture = texture;
  }

  @Override
  public void render(
      T entity,
      float entityYaw,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight
  ) {
    getModel().setAllVisible(true);
    getModel().crouching = entity.isCrouching();
    super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
  }

  @Override
  public ResourceLocation getTextureLocation(T entity) {
    return texture;
  }
}
