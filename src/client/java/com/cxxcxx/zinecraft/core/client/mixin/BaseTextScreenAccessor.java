package com.cxxcxx.zinecraft.core.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Accesses layout fields declared directly by L2Tabs BaseTextScreen. */
@Mixin(targets = "dev.xkmc.l2tabs.tabs.contents.BaseTextScreen")
public interface BaseTextScreenAccessor {
  @Accessor("leftPos")
  int zinecraft_getLeftPos();

  @Accessor("topPos")
  int zinecraft_getTopPos();
}
