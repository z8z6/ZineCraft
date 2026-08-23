package com.cxxcxx.zinecraft.api.world.city;

/** 可选的城市布局调试事件接收器。 */
@FunctionalInterface
public interface LayoutDebugCollector {
  LayoutDebugCollector NONE = event -> { };

  void accept(LayoutDebugEvent event);
}
