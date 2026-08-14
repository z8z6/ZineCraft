package com.cxxcxx.zinecraft.core.nation

import com.cxxcxx.zinecraft.api.nation.NationRelation
import com.cxxcxx.zinecraft.api.nation.NationRelationEvidence
import com.cxxcxx.zinecraft.api.nation.NationRelationTag
import com.cxxcxx.zinecraft.api.nation.NationRelationshipNetwork
import com.cxxcxx.zinecraft.api.nation.NationState
import com.cxxcxx.zinecraft.api.nation.TerraNation

/**
 * 基于 PRTS 国家资料建立的初始关系网。
 *
 * PRTS 只用于确定战争、投资、宗教联系等事实；0—100 数值是为游戏系统提供的可调初始值。
 */
object TerraNationRelations {
  val NETWORK = NationRelationshipNetwork(states(), relations())

  private fun states() = listOf(
    state(TerraNation.AEGIR, 72, 50, 80, 10, 15),
    state(TerraNation.BOLIVAR, 35, 20, 45, 55, 55),
    state(TerraNation.HIGASHI, 62, 40, 60, 45, 40),
    state(TerraNation.DURIN, 82, 80, 35, 55, 15),
    state(TerraNation.COLUMBIA, 88, 72, 85, 90, 65),
    state(TerraNation.KAZIMIERZ, 78, 65, 72, 75, 45),
    state(TerraNation.KAZDEL, 30, 30, 88, 35, 70),
    state(TerraNation.LATERANO, 85, 88, 78, 82, 12),
    state(TerraNation.LEITHANIEN, 80, 70, 82, 65, 35),
    state(TerraNation.RIM_BILLITON, 72, 75, 45, 85, 20),
    state(TerraNation.MINOS, 60, 68, 62, 55, 30),
    state(TerraNation.SARGON, 65, 60, 70, 70, 40),
    state(TerraNation.SAMI, 38, 70, 75, 25, 15),
    state(TerraNation.VICTORIA, 76, 40, 90, 72, 55),
    state(TerraNation.URSUS, 55, 38, 92, 30, 85),
    state(TerraNation.KJERAG, 58, 72, 48, 52, 20),
    state(TerraNation.SIRACUSA, 70, 55, 65, 72, 45),
    state(TerraNation.YAN, 88, 88, 90, 60, 20),
    state(TerraNation.IBERIA, 32, 52, 65, 15, 25)
  )

  private fun relations(): List<NationRelation> = buildList {
    val bolivar = evidence(
      "https://prts.wiki/w/泰拉大典:地理/玻利瓦尔",
      "玻利瓦尔处于哥伦比亚与莱塔尼亚支持的政权长期割据之中。"
    )
    addAll(
      mutual(
        TerraNation.COLUMBIA, TerraNation.BOLIVAR, -55, 72, 25, 85, -65, bolivar,
        NationRelationTag.ACTIVE_CONFLICT, NationRelationTag.PROXY_CONFLICT
      )
    )
    addAll(
      mutual(
        TerraNation.LEITHANIEN, TerraNation.BOLIVAR, 25, 22, 55, 55, 15, bolivar,
        NationRelationTag.INVESTMENT, NationRelationTag.PROXY_CONFLICT
      )
    )
    addAll(
      mutual(
        TerraNation.COLUMBIA, TerraNation.LEITHANIEN, -40, 52, 35, 72, -45, bolivar,
        NationRelationTag.PROXY_CONFLICT
      )
    )

    val ursus = evidence(
      "https://prts.wiki/w/泰拉大典:地理/乌萨斯",
      "乌萨斯长期向卡西米尔和萨米扩张，并在血峰战役中进攻东国。"
    )
    addAll(
      mutual(
        TerraNation.URSUS, TerraNation.KAZIMIERZ, -65, 78, 12, 85, -72, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
      )
    )
    addAll(
      mutual(
        TerraNation.URSUS, TerraNation.SAMI, -58, 72, 8, 80, -68, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
      )
    )
    addAll(
      mutual(
        TerraNation.URSUS, TerraNation.HIGASHI, -60, 68, 12, 82, -70, ursus,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.BORDER_TENSION
      )
    )

    val londinium = evidence(
      "https://prts.wiki/w/泰拉大典:地理/维多利亚",
      "卡兹戴尔军事委员会进驻伦蒂尼姆并与维多利亚各方爆发战争；战后其残余势力撤离。"
    )
    addAll(
      mutual(
        TerraNation.VICTORIA, TerraNation.KAZDEL, -65, 48, 8, 72, -78, londinium,
        NationRelationTag.HISTORIC_WAR, NationRelationTag.OCCUPATION
      )
    )

    val iberia = evidence(
      "https://prts.wiki/w/泰拉大典:地理/伊比利亚",
      "阿戈尔技术曾推动伊比利亚繁荣；大静谧后伊比利亚严格限制并打击岛民。"
    )
    add(
      relation(
        TerraNation.IBERIA, TerraNation.AEGIR, -48, 24, 12, 68, -62, iberia,
        NationRelationTag.TECHNOLOGY_TRANSFER, NationRelationTag.DIPLOMATIC_EXCLUSION
      )
    )
    add(
      relation(
        TerraNation.AEGIR, TerraNation.IBERIA, -25, 10, 18, 52, -45, iberia,
        NationRelationTag.TECHNOLOGY_TRANSFER
      )
    )

    val laterano = evidence(
      "https://prts.wiki/w/拉特兰",
      "拉特兰长期保持中立并充当国际见证与调解方，其宗教也影响伊比利亚。"
    )
    addAll(
      mutual(
        TerraNation.LATERANO, TerraNation.IBERIA, 20, 4, 42, 22, 18, laterano,
        NationRelationTag.RELIGIOUS_TIES, NationRelationTag.MEDIATION
      )
    )
    val lateranoKazdel = evidence(
      "https://prts.wiki/w/拉特兰",
      "拉特兰现行制度禁止萨卡兹入境。"
    )
    add(
      relation(
        TerraNation.LATERANO, TerraNation.KAZDEL, -72, 18, 2, 75, -80, lateranoKazdel,
        NationRelationTag.DIPLOMATIC_EXCLUSION
      )
    )

    val kjerag = evidence(
      "https://prts.wiki/w/泰拉大典:地理/谢拉格",
      "维多利亚贵族曾支持谢拉格改革派；哥伦比亚、莱塔尼亚和雷姆必拓商人参与对谢拉格投资。"
    )
    addAll(
      mutual(
        TerraNation.KJERAG, TerraNation.VICTORIA, 38, 6, 62, 16, 32, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
      )
    )
    addAll(
      mutual(
        TerraNation.KJERAG, TerraNation.COLUMBIA, 32, 8, 72, 20, 25, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
      )
    )
    addAll(
      mutual(
        TerraNation.KJERAG, TerraNation.LEITHANIEN, 28, 6, 65, 16, 24, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
      )
    )
    addAll(
      mutual(
        TerraNation.KJERAG, TerraNation.RIM_BILLITON, 42, 4, 80, 12, 38, kjerag,
        NationRelationTag.INVESTMENT, NationRelationTag.TRADE
      )
    )

    val columbia = evidence(
      "https://prts.wiki/w/泰拉大典:地理/哥伦比亚",
      "哥伦比亚独立自维多利亚开拓区，双方在开拓区财产与军事存在问题上关系紧张。"
    )
    addAll(
      mutual(
        TerraNation.COLUMBIA, TerraNation.VICTORIA, -28, 28, 62, 58, -30, columbia,
        NationRelationTag.TRADE, NationRelationTag.BORDER_TENSION
      )
    )
  }

  private fun state(
    nation: TerraNation,
    prosperity: Int,
    stability: Int,
    military: Int,
    openness: Int,
    aggression: Int
  ) = NationState(nation, prosperity, stability, military, openness, aggression)

  private fun evidence(url: String, fact: String) = NationRelationEvidence(url, fact)

  private fun mutual(
    first: TerraNation,
    second: TerraNation,
    favor: Int,
    warDesire: Int,
    trade: Int,
    tension: Int,
    trust: Int,
    evidence: NationRelationEvidence,
    vararg tags: NationRelationTag
  ) = listOf(
    relation(first, second, favor, warDesire, trade, tension, trust, evidence, *tags),
    relation(second, first, favor, warDesire, trade, tension, trust, evidence, *tags)
  )

  private fun relation(
    from: TerraNation,
    to: TerraNation,
    favor: Int,
    warDesire: Int,
    trade: Int,
    tension: Int,
    trust: Int,
    evidence: NationRelationEvidence,
    vararg tags: NationRelationTag
  ) = NationRelation(
    from = from,
    to = to,
    favor = favor,
    warDesire = warDesire,
    tradeAffinity = trade,
    tension = tension,
    trust = trust,
    tags = tags.toSet(),
    rationale = evidence.factSummary,
    evidence = evidence
  )
}
