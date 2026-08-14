package com.cxxcxx.zinecraft.api.accessory

import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
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
  private val translations: TranslationCatalog,
  private val namespace: String
) {
  private val entries = mutableListOf<CollectibleEntry>()
  private val seriesTranslationKey = "item.$namespace.collectible.series"
  private val originalEffectLabelTranslationKey = "item.$namespace.collectible.original_effect"
  private val minecraftEffectLabelTranslationKey = "item.$namespace.collectible.minecraft_effect"

  fun register(spec: CollectibleSpec): CollectibleEntry {
    require(entries.none { it.spec.path == spec.path }) { "藏品 ID 重复：${spec.path}" }
    val originalEffectLines = wrapLocalizedTooltip(
      spec.originalEffectZhCn,
      spec.originalEffectEnUs,
      firstLineCharacters = ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS
    )
    val descriptionLines = wrapLocalizedTooltip(spec.descriptionZhCn, spec.descriptionEnUs)
    val registeredSpec = spec.copy(
      originalEffectLineCount = originalEffectLines.size,
      descriptionLineCount = descriptionLines.size
    )

    val itemEntry = items.register(
      path = spec.path,
      zhCn = spec.zhCn,
      enUs = spec.enUs
    ) {
      CollectibleItem(
        registeredSpec,
        namespace,
        seriesTranslationKey,
        originalEffectLabelTranslationKey,
        minecraftEffectLabelTranslationKey
      )
    }
    val translationPrefix = itemEntry.item.descriptionId
    translations.add("$translationPrefix.original_effect", spec.originalEffectZhCn, spec.originalEffectEnUs)
    originalEffectLines.forEachIndexed { index, line ->
      translations.add("$translationPrefix.original_effect.$index", line.zhCn, line.enUs)
    }
    translations.add("$translationPrefix.description", spec.descriptionZhCn, spec.descriptionEnUs)
    descriptionLines.forEachIndexed { index, line ->
      translations.add("$translationPrefix.description.$index", line.zhCn, line.enUs)
    }
    translations.add("$translationPrefix.minecraft_effect", spec.minecraftEffectZhCn, spec.minecraftEffectEnUs)

    return CollectibleEntry(registeredSpec, itemEntry).also(entries::add)
  }

  private companion object {
    /** 为“原效果：”/“Original effect:”标签预留首行宽度。 */
    const val ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS = 24
  }
}

internal data class LocalizedTooltipLine(val zhCn: String, val enUs: String)

/**
 * 把两种语言分配到相同数量的 tooltip 组件。语言长度可以不同；较短文本会重新均匀分配，
 * 极端情况下使用不可见占位行，拼接可见字符后仍与原文完全一致。
 */
internal fun wrapLocalizedTooltip(
  zhCn: String,
  enUs: String,
  firstLineCharacters: Int = TOOLTIP_LINE_CHARACTERS,
  continuationCharacters: Int = TOOLTIP_LINE_CHARACTERS
): List<LocalizedTooltipLine> {
  require(zhCn.isNotEmpty() && enUs.isNotEmpty()) { "tooltip 文本不能为空" }
  require(firstLineCharacters > 0 && continuationCharacters > 0) { "tooltip 行宽必须大于 0" }
  val lineCount = maxOf(
    requiredTooltipLines(zhCn, firstLineCharacters, continuationCharacters),
    requiredTooltipLines(enUs, firstLineCharacters, continuationCharacters)
  )
  val zhLines = splitTooltipText(zhCn, lineCount, firstLineCharacters, continuationCharacters)
  val enLines = splitTooltipText(enUs, lineCount, firstLineCharacters, continuationCharacters)
  return List(lineCount) { LocalizedTooltipLine(zhLines[it], enLines[it]) }
}

private fun requiredTooltipLines(text: String, firstLineCharacters: Int, continuationCharacters: Int): Int {
  val length = text.codePointCount(0, text.length)
  if (length <= firstLineCharacters) return 1
  return 1 + (length - firstLineCharacters + continuationCharacters - 1) / continuationCharacters
}

private fun splitTooltipText(
  text: String,
  lineCount: Int,
  firstLineCharacters: Int,
  continuationCharacters: Int
): List<String> {
  val codePoints = text.codePoints().toArray()
  var offset = 0
  return List(lineCount) { index ->
    val remaining = codePoints.size - offset
    if (remaining == 0) return@List ZERO_WIDTH_SPACE
    val remainingLines = lineCount - index
    val limit = if (index == 0) firstLineCharacters else continuationCharacters
    val futureCapacity = (remainingLines - 1) * continuationCharacters
    val minimum = maxOf(1, remaining - futureCapacity)
    val balanced = (remaining + remainingLines - 1) / remainingLines
    val count = balanced.coerceIn(minimum, minOf(limit, remaining))
    String(codePoints, offset, count).also { offset += count }
  }
}

private const val TOOLTIP_LINE_CHARACTERS = 42
private const val ZERO_WIDTH_SPACE = "\u200B"

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
  val rarity: Rarity = Rarity.UNCOMMON,
  internal val originalEffectLineCount: Int = 0,
  internal val descriptionLineCount: Int = 0
) {
  init {
    require(path.matches(Regex("[a-z0-9_]+"))) { "藏品 ID 必须是 snake_case：$path" }
    require(orderId.matches(Regex("(?:[0-9]{3}|PCS[0-9]{2})"))) { "藏品编号格式无效：$orderId" }
    require(zhCn.isNotBlank() && enUs.isNotBlank()) { "藏品名称不能为空：$path" }
    require(originalEffectZhCn.isNotBlank() && originalEffectEnUs.isNotBlank()) { "藏品原效果不能为空：$path" }
    require(descriptionZhCn.isNotBlank() && descriptionEnUs.isNotBlank()) { "藏品原描述不能为空：$path" }
    require(minecraftEffectZhCn.isNotBlank() && minecraftEffectEnUs.isNotBlank()) { "藏品适配说明不能为空：$path" }
  }
}

sealed interface CollectiblePower {
  /** 只保留档案资料；尚未把原作局内机制生硬替换成无关的 Minecraft 属性。 */
  data object ArchiveOnly : CollectiblePower

  data class AttributeBoost(
    val attribute: Holder<Attribute>,
    val amount: Double,
    val operation: AttributeModifier.Operation
  ) : CollectiblePower {
    init {
      require(amount.isFinite()) { "属性修饰值必须是有限数：$amount" }
    }
  }

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
      require(maxHealthFraction.isFinite() && maxHealthFraction > 0f) { "每次回复的最大生命比例必须是有限正数" }
      require(intervalTicks > 0) { "回复间隔必须大于 0" }
    }
  }
}

class CollectibleItem internal constructor(
  val spec: CollectibleSpec,
  private val namespace: String,
  private val seriesTranslationKey: String,
  private val originalEffectLabelTranslationKey: String,
  private val minecraftEffectLabelTranslationKey: String
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
        namespace,
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
    tooltipComponents += Component.translatable(seriesTranslationKey, spec.orderId)
      .withStyle(ChatFormatting.DARK_AQUA)
    repeat(spec.originalEffectLineCount) { index ->
      val effectLine = Component.translatable("$descriptionId.original_effect.$index")
      tooltipComponents += if (index == 0) {
        Component.translatable(originalEffectLabelTranslationKey, effectLine)
          .withStyle(ChatFormatting.GOLD)
      } else {
        effectLine.withStyle(ChatFormatting.GOLD)
      }
    }
    repeat(spec.descriptionLineCount) { index ->
      tooltipComponents += Component.translatable("$descriptionId.description.$index").withStyle(ChatFormatting.GRAY)
    }
    tooltipComponents += Component.translatable(
      minecraftEffectLabelTranslationKey,
      Component.translatable("$descriptionId.minecraft_effect")
    ).withStyle(ChatFormatting.GREEN)
  }
}
