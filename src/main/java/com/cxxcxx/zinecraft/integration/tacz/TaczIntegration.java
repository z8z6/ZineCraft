package com.cxxcxx.zinecraft.integration.tacz;

/**
 * TaCZ 后端的显式初始化入口；事件处理器仍由 NeoForge 自动发现。
 */
public final class TaczIntegration {
  private TaczIntegration() {
  }

  public static void bootstrap() {
    TaczWeaponBackend.INSTANCE.id();
  }
}
