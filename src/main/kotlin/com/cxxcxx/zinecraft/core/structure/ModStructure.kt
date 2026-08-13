package com.cxxcxx.zinecraft.core.structure

import com.cxxcxx.zinecraft.core.Zinecraft

object ModStructure {
  val PORTAL_RUINS_COMMON = Zinecraft.STRUCTURES.simpleBuilding(
    path = "portal_ruins_common",
    template = "portal_ruins/common",
    spacing = 36,
    separation = 30,
    salt = 958853901,
    removeVinesChance = 0.6f
  )

  /** start -> middle -> end 三个 Jigsaw 结构片段。 */
  val THREE_PIECE_JIGSAW = Zinecraft.STRUCTURES.jigsawBuilding(
    path = "jigsaw_example",
    spacing = 40,
    separation = 20,
    salt = 31579842,
    size = 2
  ) {
    pool("start") {
      template("jigsaw_example/start")
    }
    pool("middle") {
      template("jigsaw_example/middle")
    }
    pool("end") {
      template("jigsaw_example/end")
    }
  }
}
