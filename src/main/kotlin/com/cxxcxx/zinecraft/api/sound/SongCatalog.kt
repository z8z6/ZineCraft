package com.cxxcxx.zinecraft.api.sound

import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.Item
import net.minecraft.world.item.JukeboxSong
import net.minecraft.world.item.Rarity

class SongCatalog(
  private val registrar: ModRegistrar,
  private val sounds: SoundCatalog,
  private val items: ItemCatalog,
  private val translations: TranslationCatalog
) {
  internal val entries = mutableListOf<SongEntry>()

  fun register(
    path: String,
    lengthSeconds: Float,
    description: String,
    zhCn: String = "音乐唱片",
    enUs: String = "Music Disc",
    signal: Int = 15
  ): SongEntry {
    require(lengthSeconds > 0f) { "唱片时长必须大于 0" }
    require(signal in 0..15) { "唱片红石信号必须在 0 到 15 之间" }
    val sound = sounds.register(path)
    val key = registrar.key(Registries.JUKEBOX_SONG, path)
    val descriptionKey = "jukebox_song.${registrar.namespace}.${path.replace('.', '_')}"
    val item = items.register(path, zhCn, enUs, ModelTemplates.MUSIC_DISC) {
      Item(Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(key))
    }
    return SongEntry(path, sound, key, descriptionKey, lengthSeconds, signal, item).also {
      entries += it
      translations.add(descriptionKey, description, description)
    }
  }

  fun bootstrap(context: BootstrapContext<JukeboxSong>) {
    entries.forEach { registrar.dynamic(context, it.key, it.create()) }
  }
}

class SongEntry internal constructor(
  val path: String,
  val sound: Holder.Reference<SoundEvent>,
  val key: ResourceKey<JukeboxSong>,
  val descriptionKey: String,
  val lengthSeconds: Float,
  val signal: Int,
  val itemEntry: ItemEntry<Item>
) {
  val item: Item get() = itemEntry.item

  internal fun create(): JukeboxSong =
    JukeboxSong(sound, Component.translatable(descriptionKey), lengthSeconds, signal)
}
