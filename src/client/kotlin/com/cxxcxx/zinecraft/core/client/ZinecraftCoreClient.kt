package com.cxxcxx.zinecraft.core.client

import com.cxxcxx.zinecraft.core.client.ponder.ZinecraftPonderPlugin
import net.createmod.ponder.foundation.PonderIndex
import net.fabricmc.api.ClientModInitializer

object ZinecraftCoreClient : ClientModInitializer {
  override fun onInitializeClient() {
    PonderIndex.addPlugin(ZinecraftPonderPlugin)
  }
}
