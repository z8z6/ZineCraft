package com.cxxcxx.zinecraft.core.client.collection;

import com.cxxcxx.zinecraft.api.collection.network.CollectiblePayloadTypes;
import net.minecraft.client.Minecraft;

/** 客户端藏品触发表现。 */
public final class CollectiblePresentationController {
  private CollectiblePresentationController() {
  }

  public static void initialize() {
    CollectiblePayloadTypes.setFailureRecoveryHandler(payload ->
        Minecraft.getInstance().gameRenderer.displayItemActivation(payload.stack())
    );
  }
}
