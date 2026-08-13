package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.ModSurfaceRule.rules
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

object ModTerraBlender : TerraBlenderApi {
  override fun onTerraBlenderInitialized() {
    // Weights are kept intentionally low as we add minimal biomes
    Regions.register(
      TerraNationRegion(
        Zinecraft.REGISTRAR.id("terra_nations"), 4
      )
    )

    // Register our surface rules
    SurfaceRuleManager.addSurfaceRules(
      SurfaceRuleManager.RuleCategory.OVERWORLD, Zinecraft.MOD_ID, rules()
    )
  }
}
