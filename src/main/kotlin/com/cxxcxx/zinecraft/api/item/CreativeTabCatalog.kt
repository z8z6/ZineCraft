package com.cxxcxx.zinecraft.api.item

import com.cxxcxx.zinecraft.api.block.BlockCatalog
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

class CreativeTabCatalog(
  private val registrar: ModRegistrar,
  private val items: ItemCatalog,
  private val blocks: BlockCatalog,
  private val translations: TranslationCatalog
) {
  fun register(
    path: String,
    zhCn: String,
    enUs: String,
    icon: () -> ItemStack,
    includeBlocks: Boolean = true
  ): CreativeTabEntry {
    val translationKey = "itemGroup.${registrar.namespace}.$path"
    translations.add(translationKey, zhCn, enUs)
    val registration = registrar.creativeTab(
      path,
      FabricItemGroup.builder().icon(icon).title(Component.translatable(translationKey)).build()
    )
    ItemGroupEvents.modifyEntriesEvent(registration.first).register { entries ->
      items.entries.forEach { entries.accept(it.item) }
      if (includeBlocks) {
        blocks.entries.filter { it.registerItem }.forEach { entries.accept(it.block.asItem()) }
      }
    }
    return CreativeTabEntry(registration.first, registration.second)
  }
}

data class CreativeTabEntry(
  val key: ResourceKey<CreativeModeTab?>,
  val tab: CreativeModeTab
)
