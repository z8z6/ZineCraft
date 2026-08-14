package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.NationBiomes

/**
 * 拉特兰“律法”主机：固定生成在泰拉 `(0, 0)` 正下方的前文明地下设施。
 *
 * 结构以 PRTS 所载“大教堂地下最深处”“银色山脉”和 PCS 主机为语义依据，采用原创银白机械山体与同心同步核心造型。
 */
object LateranoHostStructure {
  val LATERANO_HOST = Zinecraft.STRUCTURES.fixedOriginUndergroundLandmark(
    path = "laterano_host",
    template = "laterano_host/core",
    biome = NationBiomes.LATERANO_HOLY_FIELDS,
    startHeight = -32,
    maxDistanceFromCenter = 48
  )
}
