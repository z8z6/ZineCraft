package com.cxxcxx.zinecraft.api.item

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.localization.toDisplayName
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.world.item.Item
import net.minecraft.world.level.ItemLike

class ItemCatalog(
  private val registrar: ModRegistrar,
  private val translations: TranslationCatalog
) {
  internal val entries = mutableListOf<ItemEntry<*>>()

  fun <T : Item> register(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    model: ModelTemplate = ModelTemplates.FLAT_ITEM,
    factory: () -> T
  ): ItemEntry<T> {
    val entry = ItemEntry(path, registrar.item(path, factory()), model)
    entries += entry
    translations.add(entry.item.descriptionId, zhCn, enUs)
    return entry
  }

  fun register(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    model: ModelTemplate = ModelTemplates.FLAT_ITEM,
    properties: Item.Properties = Item.Properties()
  ): ItemEntry<Item> = register(path, zhCn, enUs, model) { Item(properties) }
}

class ItemEntry<T : Item> internal constructor(
  val path: String,
  val item: T,
  internal val model: ModelTemplate
) : ItemLike {
  override fun asItem(): Item = item

  fun fuel(ticks: Int): ItemEntry<T> = apply {
    require(ticks > 0) { "燃料时间必须大于 0" }
    FuelRegistry.INSTANCE.add(item, ticks)
  }

  fun compost(chance: Float): ItemEntry<T> = apply {
    require(chance in 0f..1f) { "堆肥概率必须在 0 到 1 之间" }
    CompostingChanceRegistry.INSTANCE.add(item, chance)
  }
}
