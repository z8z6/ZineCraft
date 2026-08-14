package com.cxxcxx.zinecraft.core.client.entity

import com.cxxcxx.zinecraft.core.entity.NationResident
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.HumanoidMobRenderer
import net.minecraft.resources.ResourceLocation

/** 居民的国家身份由服务端实体类型与手持物区分；后续可逐国替换原创皮肤。 */
class NationResidentRenderer(context: EntityRendererProvider.Context) :
  HumanoidMobRenderer<NationResident, PlayerModel<NationResident>>(
    context,
    PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false),
    0.5f
  ) {
  override fun getTextureLocation(entity: NationResident): ResourceLocation = TEXTURE

  companion object {
    private val TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png")
  }
}
