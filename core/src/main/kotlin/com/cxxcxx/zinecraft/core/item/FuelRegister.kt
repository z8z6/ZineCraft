package com.cxxcxx.zinecraft.core.item

import net.fabricmc.fabric.api.registry.FuelRegistry

object FuelRegister {
    fun init() {
        // Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
        // Remember, Minecraft deals with logical based-time using ticks.
        // 20 ticks = 1 second.
        FuelRegistry.INSTANCE.add(ModItem.MAGIC_DUST, 30 * 20);
    }
}