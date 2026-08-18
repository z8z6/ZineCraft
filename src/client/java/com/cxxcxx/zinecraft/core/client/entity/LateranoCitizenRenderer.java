package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.resident.LateranoCitizen;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class LateranoCitizenRenderer extends HumanoidMobRenderer<LateranoCitizen, PlayerModel<LateranoCitizen>> {
  private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
      Zinecraft.MOD_ID,
      "textures/entity/nation_resident/laterano.png"
  );

  public LateranoCitizenRenderer(EntityRendererProvider.Context context) {
    super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
  }

  @Override
  public ResourceLocation getTextureLocation(LateranoCitizen entity) {
    return TEXTURE;
  }
}
