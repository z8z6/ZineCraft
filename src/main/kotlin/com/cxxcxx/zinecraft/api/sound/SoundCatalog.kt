package com.cxxcxx.zinecraft.api.sound

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.core.Holder
import net.minecraft.sounds.SoundEvent

class SoundCatalog(private val registrar: ModRegistrar) {
  fun register(path: String): Holder.Reference<SoundEvent> {
    require(path.isNotBlank()) { "声音 ID 不能为空" }
    return registrar.sound(path)
  }
}
