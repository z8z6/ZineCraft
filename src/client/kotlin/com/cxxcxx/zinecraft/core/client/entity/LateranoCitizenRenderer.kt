package com.cxxcxx.zinecraft.core.client.entity

import com.cxxcxx.zinecraft.core.entity.LateranoCitizen
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.HumanoidMobRenderer
import net.minecraft.resources.ResourceLocation

/** 当前复用原版宽臂玩家皮肤；正式萨科塔皮肤可在不改服务端实体的情况下替换。 */
class LateranoCitizenRenderer(context: EntityRendererProvider.Context) :
  HumanoidMobRenderer<LateranoCitizen, PlayerModel<LateranoCitizen>>(
    context,
    PlayerModel(context.bakeLayer(ModelLayers.PLAYER), false),
    0.5f
  ) {
  override fun getTextureLocation(entity: LateranoCitizen): ResourceLocation = TEXTURE

  companion object {
    private val TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/player/wide/steve.png")
  }
}
