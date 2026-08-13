package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.api.content.SongEntry
import com.cxxcxx.zinecraft.core.ZinecraftCore
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.world.item.JukeboxSong

object ModSound {
  val AMBIENT_PICTURES_OF_THE_PAST = song(
    "ambient.pictures_of_the_past", 95f,
    "James Primate - Pictures of the Past"
  )
  val AMBIENT_RANDOM_GODS = song(
    "ambient.random_gods", 199f,
    "James Primate - Random Gods (Theme III)"
  )
  val AMBIENT_STRANGER_THINK = song(
    "ambient.stranger_think", 240f,
    "C418 - Stranger Think"
  )

  fun configure(context: BootstrapContext<JukeboxSong>) {
    ZinecraftCore.CONTENT.bootstrapSongs(context)
  }

  private fun song(path: String, length: Float, description: String): SongEntry =
    ZinecraftCore.CONTENT.musicDisc(path, length, description)
}
