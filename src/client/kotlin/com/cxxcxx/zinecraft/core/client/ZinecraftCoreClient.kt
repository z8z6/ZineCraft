package com.cxxcxx.zinecraft.core.client

import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin
import com.cxxcxx.zinecraft.core.client.entity.LateranoCitizenRenderer
import com.cxxcxx.zinecraft.core.client.entity.NationResidentRenderer
import com.cxxcxx.zinecraft.core.entity.ModEntities
import com.cxxcxx.zinecraft.core.client.weapon.WeaponPresentationController
import com.cxxcxx.zinecraft.core.client.weapon.WeaponClientInput
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczClientResourceBridge
import com.cxxcxx.zinecraft.core.client.weapon.tacz.TaczItemRenderer
import net.createmod.ponder.foundation.PonderIndex
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry

object ZinecraftCoreClient : ClientModInitializer {
  override fun onInitializeClient() {
    PonderIndex.addPlugin(ZinecraftPonderPlugin)
    WeaponClientInput.initialize()
    WeaponPresentationController.initialize()
    TaczItemRenderer.initialize()
    TaczClientResourceBridge.initialize()
    EntityRendererRegistry.register(ModEntities.LATERANO_CITIZEN.type, ::LateranoCitizenRenderer)
    ModEntities.GENERIC_RESIDENT_TYPES.forEach { type ->
      EntityRendererRegistry.register(type, ::NationResidentRenderer)
    }
  }
}
