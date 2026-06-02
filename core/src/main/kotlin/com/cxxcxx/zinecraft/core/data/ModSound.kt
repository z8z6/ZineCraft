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

  val AMBIENT_PICTURES_OF_THE_PAST = Song(
    "ambient.pictures_of_the_past", 95f,
    "James Primate - Pictures of the Past"
  )
  val AMBIENT_RANDOM_GODS = Song(
    "ambient.random_gods", 199f,
    "James Primate - Random Gods (Theme III)"
  )
  val AMBIENT_STRANGER_THINK = Song(
    "ambient.stranger_think", 240f,
    "C418 - Stranger Think"
  )

  fun configure(ctx: BootstrapContext<JukeboxSong>) {
    Song.List.forEach { song -> ctx.register(song.keySong, song.make()) }
  }

  class Song(val name: String, val length: Float, val desc: String) {
    val event: Holder.Reference<SoundEvent> = register(name)
    val keySound: ResourceKey<SoundEvent> = keySound(name)
    val keySong: ResourceKey<JukeboxSong> = keySong(name)
    val signal: Int = 15

    init {
      List.add(this)
    }

    fun make(): JukeboxSong {
      return JukeboxSong(
        event, Component.translatable(name), length, signal
      )
    }

    // 注意，一定要使用 registerForHolder 方法注册
    private fun register(name: String): Holder.Reference<SoundEvent> {
      val r = ZinecraftCore.id(name)
      val o = SoundEvent.createVariableRangeEvent(r)
      return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, r, o)
    }

    private fun keySong(name: String): ResourceKey<JukeboxSong> {
      return ZinecraftCore.key(Registries.JUKEBOX_SONG, name)
    }

    private fun keySound(name: String): ResourceKey<SoundEvent> {
      return ZinecraftCore.key(Registries.SOUND_EVENT, name)
    }

    companion object {
      var List: MutableList<Song> = mutableListOf()
    }
  }

  fun init() {}

}