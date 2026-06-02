package com.cxxcxx.zinecraft.core.item

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry

object CompostableItemRegistry {
  init {
    // Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
    CompostingChanceRegistry.INSTANCE.add(ModItem.MAGIC_DUST.item, 0.3f)
  }
}