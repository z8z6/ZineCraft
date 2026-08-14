package com.cxxcxx.zinecraft.core.item

import com.cxxcxx.zinecraft.api.accessory.CollectibleEntry
import com.cxxcxx.zinecraft.api.accessory.CollectiblePower
import com.cxxcxx.zinecraft.api.accessory.CollectibleSpec
import com.cxxcxx.zinecraft.core.Zinecraft
import com.google.gson.Gson
import net.minecraft.core.Holder
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Rarity
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/** PRTS《傀影与猩红孤钻》No.001–238 与 PCS01–PCS07 全部 245 件藏品。 */
object ModCollectibles {
  private const val CATALOG_RESOURCE = "/zinecraft/collectibles/phantom_crimson_solitaire.json"
  private const val EXPECTED_COUNT = 245

  init {
    Zinecraft.TRANSLATIONS.add(
      "item.zinecraft.collectible.series",
      "集成战略「傀影与猩红孤钻」 · No.%s",
      "Integrated Strategies: Phantom & Crimson Solitaire · No.%s"
    )
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.original_effect", "原效果：%s", "Original effect: %s")
    Zinecraft.TRANSLATIONS.add("item.zinecraft.collectible.minecraft_effect", "装备效果：%s", "Equipped effect: %s")
    Zinecraft.TRANSLATIONS.add("trinkets.slot.chest.relic", "藏品", "Collectible")
  }

  /**
   * 只有能忠实落到现有服务端权威机制的藏品才设置玩法能力。其他藏品仍可装备，
   * 并完整展示原效果与描述，但不把希望、部署费用、招募券等机制伪装成无关属性。
   */
  private val powerOverrides = mapOf(
    "rogue_1_relic_a11" to attribute("盔甲值+3", "+3 Armor", Attributes.ARMOR, 3.0),
    "rogue_1_relic_a12" to attribute("盔甲值+5", "+5 Armor", Attributes.ARMOR, 5.0),
    "rogue_1_relic_a13" to attribute("盔甲值+7", "+7 Armor", Attributes.ARMOR, 7.0),
    "rogue_1_relic_a14" to attribute(
      "装备者近战攻击伤害+15%",
      "The wearer gains +15% melee attack damage",
      Attributes.ATTACK_DAMAGE,
      0.15,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_a15" to attribute(
      "装备者近战攻击伤害+25%",
      "The wearer gains +25% melee attack damage",
      Attributes.ATTACK_DAMAGE,
      0.25,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_a16" to attribute(
      "装备者近战攻击伤害+35%",
      "The wearer gains +35% melee attack damage",
      Attributes.ATTACK_DAMAGE,
      0.35,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_a20" to attribute(
      "最大生命值+20%",
      "+20% maximum health",
      Attributes.MAX_HEALTH,
      0.20,
      AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ),
    "rogue_1_relic_a21" to attribute(
      "最大生命值+35%",
      "+35% maximum health",
      Attributes.MAX_HEALTH,
      0.35,
      AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ),
    "rogue_1_relic_a22" to attribute(
      "最大生命值+50%",
      "+50% maximum health",
      Attributes.MAX_HEALTH,
      0.50,
      AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    ),
    "rogue_1_relic_a31" to PowerOverride(
      minecraftEffectZhCn = "每秒回复1%的最大生命值",
      minecraftEffectEnUs = "Recover 1% of maximum health every second",
      power = CollectiblePower.Regeneration(0.01f)
    ),
    "rogue_1_relic_p05" to attributes(
      "装备者近战攻击伤害+50%，盔甲值+10",
      "The wearer gains +50% melee attack damage and +10 Armor",
      boost(Attributes.ATTACK_DAMAGE, 0.50, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
      boost(Attributes.ARMOR, 10.0)
    ),
    "rogue_1_relic_p07" to attribute(
      "装备者近战攻击伤害+25%",
      "The wearer gains +25% melee attack damage",
      Attributes.ATTACK_DAMAGE,
      0.25,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_p10" to attributes(
      "装备者盔甲值-8，近战攻击伤害+40%，攻击速度+30%",
      "The wearer loses 8 Armor but gains +40% melee attack damage and +30% attack speed",
      boost(Attributes.ARMOR, -8.0),
      boost(Attributes.ATTACK_DAMAGE, 0.40, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
      boost(Attributes.ATTACK_SPEED, 0.30, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    ),
    "rogue_1_relic_p12" to attribute(
      "装备者近战攻击伤害+60%",
      "The wearer gains +60% melee attack damage",
      Attributes.ATTACK_DAMAGE,
      0.60,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_p13" to attributes(
      "装备者盔甲值+5，最大生命值+50%",
      "The wearer gains +5 Armor and +50% maximum health",
      boost(Attributes.ARMOR, 5.0),
      boost(Attributes.MAX_HEALTH, 0.50, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
    ),
    "rogue_1_relic_p20" to attribute(
      "装备者攻击速度+70%",
      "The wearer gains +70% attack speed",
      Attributes.ATTACK_SPEED,
      0.70,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_p23" to attribute(
      "装备者攻击速度+40%",
      "The wearer gains +40% attack speed",
      Attributes.ATTACK_SPEED,
      0.40,
      AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ),
    "rogue_1_relic_p38" to attributes(
      "装备者近战攻击伤害+40%，盔甲值+8",
      "The wearer gains +40% melee attack damage and +8 Armor",
      boost(Attributes.ATTACK_DAMAGE, 0.40, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
      boost(Attributes.ARMOR, 8.0)
    )
  )

  val ALL: List<CollectibleEntry> = loadCatalog().map { imported ->
    val override = powerOverrides[imported.sourceId] ?: PowerOverride(
      minecraftEffectZhCn = "仅保留原效果与藏品资料，暂未映射为 Minecraft 玩法",
      minecraftEffectEnUs = "Original effect and archive text preserved; no Minecraft adaptation yet",
      power = CollectiblePower.ArchiveOnly
    )
    Zinecraft.COLLECTIBLES.register(
      CollectibleSpec(
        path = imported.path,
        orderId = imported.orderId,
        zhCn = imported.zhCn,
        enUs = imported.enUs,
        originalEffectZhCn = imported.originalEffectZhCn,
        originalEffectEnUs = imported.originalEffectEnUs,
        descriptionZhCn = imported.descriptionZhCn,
        descriptionEnUs = imported.descriptionEnUs,
        minecraftEffectZhCn = override.minecraftEffectZhCn,
        minecraftEffectEnUs = override.minecraftEffectEnUs,
        power = override.power,
        rarity = parseRarity(imported)
      )
    )
  }

  private fun loadCatalog(): List<ImportedCollectible> {
    val stream = checkNotNull(ModCollectibles::class.java.getResourceAsStream(CATALOG_RESOURCE)) {
      "找不到藏品目录资源：$CATALOG_RESOURCE；请运行 script/import_prts_is2_collectibles.py"
    }
    val imported = stream.use {
      InputStreamReader(it, StandardCharsets.UTF_8).use { reader ->
        Gson().fromJson(reader, Array<ImportedCollectible>::class.java).toList()
      }
    }
    require(imported.size == EXPECTED_COUNT) {
      "《傀影与猩红孤钻》藏品目录应有 $EXPECTED_COUNT 件，实际为 ${imported.size} 件"
    }
    require(imported.map { it.path }.distinct().size == imported.size) { "藏品目录存在重复物品 ID" }
    require(imported.map { it.orderId }.distinct().size == imported.size) { "藏品目录存在重复档案编号" }
    require(imported.map { it.sourceId }.distinct().size == imported.size) { "藏品目录存在重复来源 ID" }
    require(imported.map { it.iconId }.distinct().size == imported.size) { "藏品目录存在重复图片 ID" }
    imported.forEach(::validateImported)
    val unknownOverrides = powerOverrides.keys - imported.mapTo(hashSetOf()) { it.sourceId }
    require(unknownOverrides.isEmpty()) { "玩法覆盖引用了不存在的来源 ID：${unknownOverrides.sorted()}" }
    return imported
  }

  private fun validateImported(imported: ImportedCollectible) {
    require(!imported.path.isNullOrBlank() && imported.path.matches(Regex("[a-z0-9_]+"))) {
      "藏品物品 ID 格式无效：${imported.path}"
    }
    require(!imported.orderId.isNullOrBlank() && imported.orderId.matches(Regex("(?:[0-9]{3}|PCS[0-9]{2})"))) {
      "藏品档案编号格式无效：${imported.orderId}"
    }
    require(!imported.sourceId.isNullOrBlank() && imported.sourceId.matches(Regex("rogue_1_relic_[a-z0-9_]+"))) {
      "藏品来源 ID 格式无效：${imported.sourceId}"
    }
    require(!imported.iconId.isNullOrBlank() && imported.iconId.matches(Regex("rogue_1_relic_[a-z0-9_]+"))) {
      "藏品图片 ID 格式无效：${imported.iconId}"
    }
    require(imported.sourceId == imported.iconId) { "藏品来源 ID 与图片 ID 不一致：${imported.sourceId}" }
    require(!imported.zhCn.isNullOrBlank() && !imported.enUs.isNullOrBlank()) { "藏品名称不能为空：${imported.sourceId}" }
    require(!imported.originalEffectZhCn.isNullOrBlank() && !imported.originalEffectEnUs.isNullOrBlank()) {
      "藏品原效果不能为空：${imported.sourceId}"
    }
    require(!imported.descriptionZhCn.isNullOrBlank() && !imported.descriptionEnUs.isNullOrBlank()) {
      "藏品原描述不能为空：${imported.sourceId}"
    }
    parseRarity(imported)
  }

  private fun parseRarity(imported: ImportedCollectible): Rarity = runCatching {
    Rarity.valueOf(imported.rarity)
  }.getOrElse { throw IllegalArgumentException("藏品稀有度无效：${imported.sourceId}=${imported.rarity}", it) }

  private fun boost(
    attribute: Holder<Attribute>,
    amount: Double,
    operation: AttributeModifier.Operation = AttributeModifier.Operation.ADD_VALUE
  ) = CollectiblePower.AttributeBoost(attribute, amount, operation)

  private fun attribute(
    minecraftEffectZhCn: String,
    minecraftEffectEnUs: String,
    attribute: Holder<Attribute>,
    amount: Double,
    operation: AttributeModifier.Operation = AttributeModifier.Operation.ADD_VALUE
  ) = PowerOverride(
    minecraftEffectZhCn,
    minecraftEffectEnUs,
    CollectiblePower.AttributeBoost(attribute, amount, operation)
  )

  private fun attributes(
    minecraftEffectZhCn: String,
    minecraftEffectEnUs: String,
    vararg boosts: CollectiblePower.AttributeBoost
  ) = PowerOverride(
    minecraftEffectZhCn,
    minecraftEffectEnUs,
    CollectiblePower.AttributeSet(boosts.toList())
  )

  private data class PowerOverride(
    val minecraftEffectZhCn: String,
    val minecraftEffectEnUs: String,
    val power: CollectiblePower
  )

  private data class ImportedCollectible(
    val path: String,
    val orderId: String,
    val sourceId: String,
    val iconId: String,
    val zhCn: String,
    val enUs: String,
    val originalEffectZhCn: String,
    val originalEffectEnUs: String,
    val descriptionZhCn: String,
    val descriptionEnUs: String,
    val rarity: String
  )
}
