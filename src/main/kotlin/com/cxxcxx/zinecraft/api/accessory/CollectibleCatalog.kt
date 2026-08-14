package com.cxxcxx.zinecraft.api.accessory

import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.core.Zinecraft
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketItem
import net.minecraft.ChatFormatting
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag

/**
 * 声明可装备的集成战略藏品，并统一保留原效果、原描述和 Minecraft 适配说明。
 * 藏品只允许进入专用的 `chest/relic` 槽，避免误占用戒指、项链等通用饰品槽。
 */
class CollectibleCatalog(
  private val items: ItemCatalog,
  private val translations: TranslationCatalog
) {
  private val entries = mutableListOf<CollectibleEntry>()

  fun register(spec: CollectibleSpec): CollectibleEntry {
    require(spec.path.matches(Regex("[a-z0-9_]+"))) { "藏品 ID 必须是 snake_case：${spec.path}" }
    require(spec.orderId.matches(Regex("(?:[0-9]{3}|PCS[0-9]{2})"))) {
      "藏品编号必须是三位数字或 PCS 编号：${spec.orderId}"
    }
    require(spec.zhCn.isNotBlank()) { "藏品中文名不能为空" }
    require(spec.originalEffectZhCn.isNotBlank()) { "藏品原效果不能为空" }
    require(spec.descriptionZhCn.isNotBlank()) { "藏品原描述不能为空" }
    require(entries.none { it.spec.path == spec.path }) { "藏品 ID 重复：${spec.path}" }

    val itemEntry = items.register(
      path = spec.path,
      zhCn = spec.zhCn,
      enUs = spec.enUs
    ) {
      CollectibleItem(spec)
    }
    val translationPrefix = itemEntry.item.descriptionId
    translations.add("$translationPrefix.original_effect", spec.originalEffectZhCn, spec.originalEffectEnUs)
    translations.add("$translationPrefix.description", spec.descriptionZhCn, spec.descriptionEnUs)
    translations.add("$translationPrefix.minecraft_effect", spec.minecraftEffectZhCn, spec.minecraftEffectEnUs)

    return CollectibleEntry(spec, itemEntry).also(entries::add)
  }

  internal fun registerSharedTranslations() {
    translations.add(
      "item.zinecraft.collectible.series",
      "集成战略「傀影与猩红孤钻」 · No.%s",
      "Integrated Strategies: Phantom & Crimson Solitaire · No.%s"
    )
    translations.add("item.zinecraft.collectible.original_effect", "原效果：%s", "Original effect: %s")
    translations.add("item.zinecraft.collectible.minecraft_effect", "装备效果：%s", "Equipped effect: %s")
    translations.add("trinkets.slot.chest.relic", "藏品", "Collectible")
  }
}

data class CollectibleEntry(
  val spec: CollectibleSpec,
  val itemEntry: ItemEntry<CollectibleItem>
) {
  val item: CollectibleItem
    get() = itemEntry.item
}

data class CollectibleSpec(
  val path: String,
  val orderId: String,
  val zhCn: String,
  val enUs: String,
  val originalEffectZhCn: String,
  val originalEffectEnUs: String,
  val descriptionZhCn: String,
  val descriptionEnUs: String,
  val minecraftEffectZhCn: String,
  val minecraftEffectEnUs: String,
  val power: CollectiblePower,
  val rarity: Rarity = Rarity.UNCOMMON
)

sealed interface CollectiblePower {
  /** 只保留档案资料；尚未把原作局内机制生硬替换成无关的 Minecraft 属性。 */
  data object ArchiveOnly : CollectiblePower

  data class AttributeBoost(
    val attribute: Holder<Attribute>,
    val amount: Double,
    val operation: AttributeModifier.Operation
  ) : CollectiblePower

  data class AttributeSet(
    val boosts: List<AttributeBoost>
  ) : CollectiblePower {
    init {
      require(boosts.isNotEmpty()) { "复合藏品至少需要一个属性修饰" }
      require(boosts.map { it.attribute }.distinct().size == boosts.size) { "复合藏品不能重复修饰同一属性" }
    }
  }

  data class Regeneration(
    val maxHealthFraction: Float,
    val intervalTicks: Int = 20
  ) : CollectiblePower {
    init {
      require(maxHealthFraction > 0f) { "每次回复的最大生命比例必须大于 0" }
      require(intervalTicks > 0) { "回复间隔必须大于 0" }
    }
  }
}

class CollectibleItem internal constructor(
  val spec: CollectibleSpec
) : TrinketItem(Item.Properties().stacksTo(1).rarity(spec.rarity)) {
  override fun canEquip(stack: ItemStack, slot: SlotReference, entity: LivingEntity): Boolean {
    val slotType = slot.inventory().slotType
    return slotType.group == "chest" && slotType.name == "relic"
  }

  override fun canEquipFromUse(stack: ItemStack, entity: LivingEntity): Boolean = true

  override fun getModifiers(
    stack: ItemStack,
    slot: SlotReference,
    entity: LivingEntity,
    slotIdentifier: ResourceLocation
  ): Multimap<Holder<Attribute>, AttributeModifier> {
    val modifiers = ArrayListMultimap.create<Holder<Attribute>, AttributeModifier>()
    val boosts = when (val power = spec.power) {
      CollectiblePower.ArchiveOnly -> emptyList()
      is CollectiblePower.AttributeBoost -> listOf(power)
      is CollectiblePower.AttributeSet -> power.boosts
      is CollectiblePower.Regeneration -> emptyList()
    }
    boosts.forEachIndexed { index, boost ->
      val modifierId = ResourceLocation.fromNamespaceAndPath(
        Zinecraft.MOD_ID,
        "collectible/${spec.path}/$index"
      )
      modifiers.put(boost.attribute, AttributeModifier(modifierId, boost.amount, boost.operation))
    }
    return modifiers
  }

  override fun tick(stack: ItemStack, slot: SlotReference, entity: LivingEntity) {
    val power = spec.power
    if (power !is CollectiblePower.Regeneration || entity.level().isClientSide) return
    if (entity.tickCount % power.intervalTicks != 0 || entity.health >= entity.maxHealth) return
    entity.heal(entity.maxHealth * power.maxHealthFraction)
  }

  override fun appendHoverText(
    stack: ItemStack,
    context: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
    tooltipComponents += Component.translatable("item.zinecraft.collectible.series", spec.orderId)
      .withStyle(ChatFormatting.DARK_AQUA)
    tooltipComponents += Component.translatable(
      "item.zinecraft.collectible.original_effect",
      Component.translatable("$descriptionId.original_effect")
    ).withStyle(ChatFormatting.GOLD)
    tooltipComponents += Component.translatable("$descriptionId.description").withStyle(ChatFormatting.GRAY)
    tooltipComponents += Component.translatable(
      "item.zinecraft.collectible.minecraft_effect",
      Component.translatable("$descriptionId.minecraft_effect")
    ).withStyle(ChatFormatting.GREEN)
  }
}
