package com.cxxcxx.zinecraft.api.weapon.network;

import com.cxxcxx.zinecraft.api.weapon.WeaponServerController;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.function.Consumer;

/**
 * NeoForge payload registration for the weapon protocol.
 */
public final class WeaponPayloadTypes {
  private static Consumer<WeaponActionStartedPayload> startedHandler;
  private static Consumer<WeaponActionCancelledPayload> cancelledHandler;

  private WeaponPayloadTypes() {
  }

  public static void register(RegisterPayloadHandlersEvent event) {
    var registrar = event.registrar("1");
    registrar.playToServer(
        WeaponActionRequestPayload.ACCESS.getTYPE(),
        WeaponActionRequestPayload.ACCESS.getCODEC(),
        WeaponServerController.INSTANCE::handleRequest
    );
    registrar.playToClient(
        WeaponActionStartedPayload.ACCESS.getTYPE(),
        WeaponActionStartedPayload.ACCESS.getCODEC(),
        (payload, context) -> {
          if (startedHandler != null) startedHandler.accept(payload);
        }
    );
    registrar.playToClient(
        WeaponActionCancelledPayload.ACCESS.getTYPE(),
        WeaponActionCancelledPayload.ACCESS.getCODEC(),
        (payload, context) -> {
          if (cancelledHandler != null) cancelledHandler.accept(payload);
        }
    );
  }

  public static void setClientHandlers(
      Consumer<WeaponActionStartedPayload> started,
      Consumer<WeaponActionCancelledPayload> cancelled
  ) {
    startedHandler = started;
    cancelledHandler = cancelled;
  }
}
