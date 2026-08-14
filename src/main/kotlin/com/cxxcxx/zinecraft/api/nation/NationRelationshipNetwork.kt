package com.cxxcxx.zinecraft.api.nation

/** 国家自身状态。所有指标均为 0—100 的玩法标尺，不代表官方统计数据。 */
data class NationState(
  val nation: TerraNation,
  val prosperity: Int,
  val stability: Int,
  val militaryStrength: Int,
  val openness: Int,
  val aggression: Int
) {
  init {
    listOf(prosperity, stability, militaryStrength, openness, aggression).forEach {
      require(it in 0..100) { "国家状态指标必须在 0—100：${nation.id}" }
    }
  }
}

enum class NationRelationTag {
  TRADE,
  INVESTMENT,
  RELIGIOUS_TIES,
  MEDIATION,
  HISTORIC_WAR,
  ACTIVE_CONFLICT,
  PROXY_CONFLICT,
  OCCUPATION,
  BORDER_TENSION,
  DIPLOMATIC_EXCLUSION,
  TECHNOLOGY_TRANSFER
}

/** 支撑关系方向的资料事实；具体数值仍是项目为了玩法所作的推断。 */
data class NationRelationEvidence(
  val sourceUrl: String,
  val factSummary: String
) {
  init {
    require(sourceUrl.startsWith("https://")) { "关系资料必须使用 HTTPS 来源" }
    require(factSummary.isNotBlank()) { "关系资料摘要不能为空" }
  }
}

/** 从 [from] 看向 [to] 的有向关系。 */
data class NationRelation(
  val from: TerraNation,
  val to: TerraNation,
  val favor: Int,
  val warDesire: Int,
  val tradeAffinity: Int,
  val tension: Int,
  val trust: Int,
  val tags: Set<NationRelationTag> = emptySet(),
  val rationale: String = "中性基线",
  val evidence: NationRelationEvidence? = null
) {
  init {
    require(from != to) { "国家不能与自身建立双边关系" }
    require(favor in -100..100) { "好感度必须在 -100—100" }
    require(trust in -100..100) { "信任度必须在 -100—100" }
    require(warDesire in 0..100 && tradeAffinity in 0..100 && tension in 0..100) {
      "战争欲望、贸易倾向和紧张度必须在 0—100"
    }
  }
}

data class NationRelationKey(val from: TerraNation, val to: TerraNation)

/**
 * 完整的十九国关系网。
 *
 * 未被资料特别描述的国家对也会获得中性边，调用方无需处理缺失关系；显式边允许双向数值不对称。
 */
class NationRelationshipNetwork(
  states: Collection<NationState>,
  explicitRelations: Collection<NationRelation>
) {
  private val statesByNation = states.associateBy(NationState::nation)
  private val relationsByKey = buildMap {
    TerraNation.entries.forEach { from ->
      TerraNation.entries.filterNot { it == from }.forEach { to ->
        put(NationRelationKey(from, to), defaultRelation(from, to))
      }
    }
    explicitRelations.forEach { relation -> put(NationRelationKey(relation.from, relation.to), relation) }
  }

  init {
    require(statesByNation.keys == TerraNation.entries.toSet()) { "必须为全部泰拉国家提供状态" }
    require(relationsByKey.size == TerraNation.entries.size * (TerraNation.entries.size - 1)) {
      "国家关系网必须覆盖每一条非自环有向边"
    }
    require(explicitRelations.map { NationRelationKey(it.from, it.to) }.distinct().size == explicitRelations.size) {
      "存在重复的有向国家关系"
    }
  }

  fun state(nation: TerraNation): NationState = statesByNation.getValue(nation)

  fun relation(from: TerraNation, to: TerraNation): NationRelation {
    require(from != to) { "不能查询国家与自身的关系" }
    return relationsByKey.getValue(NationRelationKey(from, to))
  }

  fun relationsFrom(nation: TerraNation): List<NationRelation> =
    relationsByKey.values.filter { it.from == nation }.sortedBy { it.to.id }

  fun allRelations(): Collection<NationRelation> = relationsByKey.values

  private fun defaultRelation(from: TerraNation, to: TerraNation): NationRelation {
    val fromState = statesByNation.getValue(from)
    val toState = statesByNation.getValue(to)
    return NationRelation(
      from = from,
      to = to,
      favor = 0,
      warDesire = fromState.aggression / 5,
      tradeAffinity = (fromState.openness + toState.openness) / 10,
      tension = 10,
      trust = 0
    )
  }
}
