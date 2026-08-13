package com.cxxcxx.zinecraft.api.content

import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.*
import net.minecraft.world.level.block.Block

class ContentCatalog(val registrar: ModRegistrar) {
  internal val items = mutableListOf<ItemEntry<*>>()
  internal val blocks = mutableListOf<BlockEntry<*>>()
  internal val songs = mutableListOf<SongEntry>()
  internal val translations = mutableMapOf<String, LocalizedText>()
  internal val recipes = mutableListOf<(RecipeOutput) -> Unit>()

  fun <T : Item> item(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    model: ModelTemplate = ModelTemplates.FLAT_ITEM,
    factory: () -> T
  ): ItemEntry<T> {
    val entry = ItemEntry(path, registrar.item(path, factory()), zhCn, enUs, model)
    items += entry
    translate(entry.item.descriptionId, zhCn, enUs)
    return entry
  }

  fun item(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    model: ModelTemplate = ModelTemplates.FLAT_ITEM,
    properties: Item.Properties = Item.Properties()
  ): ItemEntry<Item> = item(path, zhCn, enUs, model) { Item(properties) }

  fun <T : Block> block(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    dropSelf: Boolean = true,
    cubeModel: Boolean = true,
    registerItem: Boolean = true,
    factory: () -> T
  ): BlockEntry<T> {
    val block = registrar.block(path, factory(), registerItem)
    val entry = BlockEntry(path, block, zhCn, enUs, dropSelf, cubeModel, registerItem)
    blocks += entry
    translate(block.descriptionId, zhCn, enUs)
    return entry
  }

  fun musicDisc(
    path: String,
    lengthSeconds: Float,
    description: String,
    zhCn: String = "音乐唱片",
    enUs: String = "Music Disc",
    signal: Int = 15
  ): SongEntry {
    val sound = registrar.sound(path)
    val songKey = registrar.key(Registries.JUKEBOX_SONG, path)
    val descriptionKey = "jukebox_song.${registrar.namespace}.${path.replace('.', '_')}"
    val item = item(path, zhCn, enUs, ModelTemplates.MUSIC_DISC) {
      Item(Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(songKey))
    }
    val song = SongEntry(path, sound, songKey, descriptionKey, lengthSeconds, signal, description, item)
    songs += song
    translate(descriptionKey, description, description)
    return song
  }

  fun creativeTab(
    path: String,
    zhCn: String,
    enUs: String,
    icon: () -> ItemStack,
    includeBlocks: Boolean = true
  ): CreativeTabEntry {
    val translationKey = "itemGroup.${registrar.namespace}"
    translate(translationKey, zhCn, enUs)
    val registration = registrar.creativeTab(
      path,
      FabricItemGroup.builder()
        .icon(icon)
        .title(Component.translatable(translationKey))
        .build()
    )
    ItemGroupEvents.modifyEntriesEvent(registration.first).register { entries ->
      items.forEach { entries.accept(it.item) }
      if (includeBlocks) {
        blocks.filter { it.registerItem }.forEach { entries.accept(it.block.asItem()) }
      }
    }
    return CreativeTabEntry(registration.first, registration.second)
  }

  fun translate(key: String, zhCn: String, enUs: String = zhCn) {
    translations[key] = LocalizedText(zhCn, enUs)
  }

  fun recipes(generate: (RecipeOutput) -> Unit) {
    recipes += generate
  }

  fun generateRecipes(output: RecipeOutput) {
    recipes.forEach { it(output) }
  }

  fun bootstrapSongs(context: BootstrapContext<JukeboxSong>) {
    songs.forEach { registrar.dynamic(context, it.key, it.createSong()) }
  }

  private fun String.toDisplayName(): String =
    split('_', '.').filter(String::isNotEmpty).joinToString(" ") { word ->
      word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}

data class LocalizedText(val zhCn: String, val enUs: String)

class ItemEntry<T : Item> internal constructor(
  val path: String,
  val item: T,
  val zhCn: String,
  val enUs: String,
  val model: ModelTemplate
) : net.minecraft.world.level.ItemLike {
  override fun asItem(): Item = item

  fun fuel(ticks: Int): ItemEntry<T> = apply {
    FuelRegistry.INSTANCE.add(item, ticks)
  }

  fun compost(chance: Float): ItemEntry<T> = apply {
    CompostingChanceRegistry.INSTANCE.add(item, chance)
  }
}

class BlockEntry<T : Block> internal constructor(
  val path: String,
  val block: T,
  val zhCn: String,
  val enUs: String,
  val dropSelf: Boolean,
  val cubeModel: Boolean,
  val registerItem: Boolean
)

class SongEntry internal constructor(
  val path: String,
  val sound: Holder.Reference<SoundEvent>,
  val key: ResourceKey<JukeboxSong>,
  val descriptionKey: String,
  val lengthSeconds: Float,
  val signal: Int,
  val description: String,
  val itemEntry: ItemEntry<Item>
) {
  val item: Item get() = itemEntry.item

  fun createSong(): JukeboxSong =
    JukeboxSong(sound, Component.translatable(descriptionKey), lengthSeconds, signal)
}

data class CreativeTabEntry(
  val key: ResourceKey<CreativeModeTab?>,
  val tab: CreativeModeTab
)
