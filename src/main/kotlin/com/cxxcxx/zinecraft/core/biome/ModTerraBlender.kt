package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.ModSurfaceRule.rules
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

object ModTerraBlender : TerraBlenderApi {
  override fun onTerraBlenderInitialized() {
    // 国家群系由泰拉维度自己的群系源承载；这里不再向主世界注册 Region。
    SurfaceRuleManager.addSurfaceRules(
      SurfaceRuleManager.RuleCategory.OVERWORLD, Zinecraft.MOD_ID, rules()
    )
  }
}
