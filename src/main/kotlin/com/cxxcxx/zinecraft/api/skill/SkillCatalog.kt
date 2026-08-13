package com.cxxcxx.zinecraft.api.skill

import com.cxxcxx.zinecraft.api.item.ItemCatalog
import com.cxxcxx.zinecraft.api.item.ItemEntry
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.ItemLike

/** 将技能资料注册为物品，并自动生成名称和多行说明翻译。 */
class SkillCatalog(
  private val items: ItemCatalog,
  private val translations: TranslationCatalog
) {
  val entries = mutableListOf<SkillEntry>()

  fun register(
    path: String,
    zhCn: String,
    enUs: String,
    operatorZhCn: String,
    operatorEnUs: String,
    profession: SkillProfession,
    recoveryZhCn: String,
    recoveryEnUs: String,
    triggerZhCn: String,
    triggerEnUs: String,
    initialSp: Int,
    spCost: Int,
    durationSeconds: Int?,
    descriptionZhCn: String,
    descriptionEnUs: String,
    theme: SkillDemoTheme
  ): SkillEntry {
    require(initialSp >= 0) { "初始技力不能为负数" }
    require(spCost >= 0) { "技力消耗不能为负数" }
    require(durationSeconds == null || durationSeconds > 0) { "技能持续时间必须大于 0" }
    require(entries.none { it.definition.path == path }) { "技能 ID 重复: $path" }

    val definition = SkillDefinition(
      path, zhCn, enUs, operatorZhCn, operatorEnUs, profession,
      recoveryZhCn, recoveryEnUs, triggerZhCn, triggerEnUs,
      initialSp, spCost, durationSeconds, descriptionZhCn, descriptionEnUs, theme
    )
    val itemEntry = items.register(
      path,
      zhCn,
      enUs
    ) { SkillItem(definition) }
    registerTranslations(definition)
    return SkillEntry(definition, itemEntry).also(entries::add)
  }

  private fun registerTranslations(skill: SkillDefinition) {
    val prefix = "item.zinecraft.${skill.path}.tooltip"
    translations.add(
      "$prefix.operator",
      "干员：${skill.operatorZhCn} · ${skill.profession.zhCn}",
      "Operator: ${skill.operatorEnUs} · ${skill.profession.enUs}"
    )
    translations.add(
      "$prefix.activation",
      "${skill.recoveryZhCn} · ${skill.triggerZhCn}",
      "${skill.recoveryEnUs} · ${skill.triggerEnUs}"
    )
    val durationZh = skill.durationSeconds?.let { " · 持续 ${it}秒" }.orEmpty()
    val durationEn = skill.durationSeconds?.let { " · Duration ${it}s" }.orEmpty()
    translations.add(
      "$prefix.stats",
      "初始 ${skill.initialSp} · 消耗 ${skill.spCost}$durationZh",
      "Initial ${skill.initialSp} · Cost ${skill.spCost}$durationEn"
    )
    translations.add("$prefix.description", skill.descriptionZhCn, skill.descriptionEnUs)
    val ponderPrefix = "zinecraft.ponder.skill_demo_${skill.path}"
    translations.add(
      "$ponderPrefix.header",
      "${skill.operatorZhCn}：${skill.zhCn}",
      "${skill.operatorEnUs}: ${skill.enUs}"
    )
    translations.add(
      "$ponderPrefix.text_1",
      "${skill.operatorZhCn}的${skill.profession.zhCn}技能",
      "A ${skill.profession.enUs} skill used by ${skill.operatorEnUs}"
    )
    translations.add(
      "$ponderPrefix.text_2",
      "${skill.recoveryZhCn} · ${skill.triggerZhCn}",
      "${skill.recoveryEnUs} · ${skill.triggerEnUs}"
    )
    translations.add(
      "$ponderPrefix.text_3",
      "初始 ${skill.initialSp} · 消耗 ${skill.spCost}$durationZh",
      "Initial ${skill.initialSp} · Cost ${skill.spCost}$durationEn"
    )
    translations.add("$ponderPrefix.text_4", skill.descriptionZhCn, skill.descriptionEnUs)
    translations.add(
      "$ponderPrefix.text_5",
      "演示为 Minecraft 机制化表达，技能资料取自 PRTS。",
      "This is a Minecraft interpretation based on PRTS skill data."
    )
  }
}

class SkillItem internal constructor(
  val skill: SkillDefinition,
  properties: Properties = Properties().stacksTo(1).rarity(Rarity.RARE)
) : Item(properties) {
  override fun appendHoverText(
    stack: ItemStack,
    context: TooltipContext,
    tooltipComponents: MutableList<Component>,
    tooltipFlag: TooltipFlag
  ) {
    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
    val prefix = descriptionId + ".tooltip"
    tooltipComponents += Component.translatable("$prefix.operator").withStyle(ChatFormatting.GOLD)
    tooltipComponents += Component.translatable("$prefix.activation").withStyle(ChatFormatting.AQUA)
    tooltipComponents += Component.translatable("$prefix.stats").withStyle(ChatFormatting.YELLOW)
    tooltipComponents += Component.translatable("$prefix.description").withStyle(ChatFormatting.GRAY)
  }
}

data class SkillEntry(
  val definition: SkillDefinition,
  val itemEntry: ItemEntry<SkillItem>
) : ItemLike {
  val item: SkillItem get() = itemEntry.item
  override fun asItem(): Item = item
}

data class SkillDefinition(
  val path: String,
  val zhCn: String,
  val enUs: String,
  val operatorZhCn: String,
  val operatorEnUs: String,
  val profession: SkillProfession,
  val recoveryZhCn: String,
  val recoveryEnUs: String,
  val triggerZhCn: String,
  val triggerEnUs: String,
  val initialSp: Int,
  val spCost: Int,
  val durationSeconds: Int?,
  val descriptionZhCn: String,
  val descriptionEnUs: String,
  val theme: SkillDemoTheme
)

enum class SkillProfession(val zhCn: String, val enUs: String) {
  VANGUARD("先锋", "Vanguard"),
  GUARD("近卫", "Guard"),
  SNIPER("狙击", "Sniper"),
  CASTER("术师", "Caster"),
  DEFENDER("重装", "Defender"),
  MEDIC("医疗", "Medic"),
  SUPPORTER("辅助", "Supporter"),
  SPECIALIST("特种", "Specialist")
}

enum class SkillDemoTheme {
  COST_RECOVERY,
  AREA_SLASH,
  RAPID_FIRE,
  VOLCANIC_BURST,
  HEAL_AND_SLOW,
  SANCTUARY,
  SLOWING_FIELD,
  DEPLOYMENT_STUN
}
