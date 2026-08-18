package com.cxxcxx.zinecraft.core.client.entity;

import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.entity.resident.NationResident;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class NationResidentRenderer extends HumanoidMobRenderer<NationResident, PlayerModel<NationResident>> {
  public NationResidentRenderer(EntityRendererProvider.Context context) {
    super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5f);
  }

  @Override
  public ResourceLocation getTextureLocation(NationResident entity) {
    return ResourceLocation.fromNamespaceAndPath(
        Zinecraft.MOD_ID,
        "textures/entity/nation_resident/" + entity.nation().getId() + ".png"
    );
  }
}
