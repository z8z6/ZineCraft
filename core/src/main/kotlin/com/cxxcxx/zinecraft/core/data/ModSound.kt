package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.JukeboxSong

// 参考原版类：SoundEvents
object ModSound {

  var PICTURES_OF_THE_PAST = register("pictures_of_the_past")
  var KEY_PICTURES_OF_THE_PAST_SONG = key("pictures_of_the_past")
  var KEY_PICTURES_OF_THE_PAST = keySound("pictures_of_the_past")

  var MUSIC_SUNDOWN = register("music_sundown")
  var KEY_MUSIC_SUNDOWN_SONG = key("music_sundown")
  var KEY_MUSIC_SUNDOWN = keySound("music_sundown")

  var RANDOM_GODS = register("random_gods")
  var KEY_RANDOM_GODS_SONG = key("random_gods")
  var KEY_RANDOM_GODS = keySound("random_gods")

  var STRANGER_THINK = register("stranger_think")
  var KEY_STRANGER_THINK_SONG = key("stranger_think")
  var KEY_STRANGER_THINK = keySound("stranger_think")


  fun configure(ctx: BootstrapContext<JukeboxSong>) {
    ctx.register(
      KEY_PICTURES_OF_THE_PAST_SONG,
      JukeboxSong(
        PICTURES_OF_THE_PAST,
        Component.translatable("music.pictures_of_the_past"),
        95.0F, // 音乐唱片的播放时间
        15     // 红石信号强度
      )
    )
    ctx.register(
      KEY_MUSIC_SUNDOWN_SONG,
      JukeboxSong(
        MUSIC_SUNDOWN,
        Component.translatable("music.music_sundown"),
        208.0F, 15
      )
    )
    ctx.register(
      KEY_RANDOM_GODS_SONG,
      JukeboxSong(
        RANDOM_GODS,
        Component.translatable("music.random_gods"),
        199.0F, 15
      )
    )
    ctx.register(
      KEY_STRANGER_THINK_SONG,
      JukeboxSong(
        STRANGER_THINK,
        Component.translatable("music.stranger_think"),
        240.0F, 15
      )
    )
  }

  // 注意，一定要使用 registerForHolder 方法注册
  private fun register(name: String): Holder.Reference<SoundEvent> {
    val r = ZinecraftCore.id(name)
    val o = SoundEvent.createVariableRangeEvent(r)
    return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, r, o)
  }

  private fun key(name: String): ResourceKey<JukeboxSong> {
    return ZinecraftCore.key(Registries.JUKEBOX_SONG, name)
  }

  private fun keySound(name: String): ResourceKey<SoundEvent> {
    return ZinecraftCore.key(Registries.SOUND_EVENT, name)
  }


  fun init() {}

}