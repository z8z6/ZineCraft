package com.cxxcxx.zinecraft.api.collection.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.function.Consumer;

/** NeoForge 藏品客户端表现载荷注册。 */
public final class CollectiblePayloadTypes {
  private static Consumer<CollectibleFailureRecoveryPayload> failureRecoveryHandler;

  private CollectiblePayloadTypes() {
  }

  public static void register(RegisterPayloadHandlersEvent event) {
    event.registrar("1").playToClient(
        CollectibleFailureRecoveryPayload.TYPE,
        CollectibleFailureRecoveryPayload.CODEC,
        (payload, context) -> {
          if (failureRecoveryHandler != null) failureRecoveryHandler.accept(payload);
        }
    );
  }

  public static void setFailureRecoveryHandler(Consumer<CollectibleFailureRecoveryPayload> handler) {
    failureRecoveryHandler = handler;
  }
}
