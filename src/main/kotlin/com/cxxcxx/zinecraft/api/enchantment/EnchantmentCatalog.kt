package com.cxxcxx.zinecraft.api.enchantment

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.localization.toDisplayName
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment

class EnchantmentCatalog(
  private val registrar: ModRegistrar,
  private val translations: TranslationCatalog
) {
  private val entries = mutableListOf<EnchantmentEntry>()

  fun register(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    supportedItems: TagKey<Item>,
    primaryItems: TagKey<Item>? = null,
    exclusiveWith: TagKey<Enchantment>? = null,
    weight: Int = 10,
    maxLevel: Int = 1,
    minCost: Enchantment.Cost = Enchantment.constantCost(1),
    maxCost: Enchantment.Cost = Enchantment.constantCost(1),
    anvilCost: Int = 1,
    vararg slots: EquipmentSlotGroup = arrayOf(EquipmentSlotGroup.ANY),
    configure: Enchantment.Builder.() -> Unit = {}
  ): EnchantmentEntry {
    require(path.isNotBlank()) { "附魔 ID 不能为空" }
    require(weight in 1..1024) { "附魔权重必须在 1 到 1024 之间" }
    require(maxLevel in 1..255) { "附魔最高等级必须在 1 到 255 之间" }
    require(anvilCost >= 0) { "附魔铁砧成本不能为负数" }
    require(slots.isNotEmpty()) { "附魔至少需要一个装备槽" }
    val entry = EnchantmentEntry(
      registrar.key(Registries.ENCHANTMENT, path),
      supportedItems,
      primaryItems,
      exclusiveWith,
      weight,
      maxLevel,
      minCost,
      maxCost,
      anvilCost,
      slots.toList(),
      configure
    )
    entries += entry
    translations.add("enchantment.${registrar.namespace}.$path", zhCn, enUs)
    return entry
  }

  fun bootstrap(context: BootstrapContext<Enchantment>) {
    val items = context.lookup(Registries.ITEM)
    val enchantments = context.lookup(Registries.ENCHANTMENT)
    entries.forEach { entry ->
      val definition = if (entry.primaryItems == null) {
        Enchantment.definition(
          items.getOrThrow(entry.supportedItems),
          entry.weight,
          entry.maxLevel,
          entry.minCost,
          entry.maxCost,
          entry.anvilCost,
          *entry.slots.toTypedArray()
        )
      } else {
        Enchantment.definition(
          items.getOrThrow(entry.supportedItems),
          items.getOrThrow(entry.primaryItems),
          entry.weight,
          entry.maxLevel,
          entry.minCost,
          entry.maxCost,
          entry.anvilCost,
          *entry.slots.toTypedArray()
        )
      }
      val builder = Enchantment.enchantment(definition)
      entry.exclusiveWith?.let { builder.exclusiveWith(enchantments.getOrThrow(it)) }
      builder.apply(entry.configure)
      registrar.dynamic(context, entry.key, builder.build(entry.key.location()))
    }
  }
}

class EnchantmentEntry internal constructor(
  val key: ResourceKey<Enchantment>,
  internal val supportedItems: TagKey<Item>,
  internal val primaryItems: TagKey<Item>?,
  internal val exclusiveWith: TagKey<Enchantment>?,
  internal val weight: Int,
  internal val maxLevel: Int,
  internal val minCost: Enchantment.Cost,
  internal val maxCost: Enchantment.Cost,
  internal val anvilCost: Int,
  internal val slots: List<EquipmentSlotGroup>,
  internal val configure: Enchantment.Builder.() -> Unit
)
