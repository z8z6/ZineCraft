package com.cxxcxx.zinecraft.integration.tacz;

/**
 * Explicit bootstrap point for the TaCZ backend. Event handlers are discovered by NeoForge.
 */
public final class TaczIntegration {
  public static final TaczIntegration INSTANCE = new TaczIntegration();

  private TaczIntegration() {
    TaczWeaponBackend.INSTANCE.id();
  }
}
