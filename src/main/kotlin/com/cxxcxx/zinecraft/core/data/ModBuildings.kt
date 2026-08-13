package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore

object ModBuildings {
  val PORTAL_RUINS_COMMON = ZinecraftCore.WORLDGEN.simpleBuilding(
    path = "portal_ruins_common",
    template = "portal_ruins/common",
    spacing = 36,
    separation = 30,
    salt = 958853901,
    removeVinesChance = 0.6f
  )
}
