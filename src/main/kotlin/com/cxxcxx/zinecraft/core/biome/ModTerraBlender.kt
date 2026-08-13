package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.Zinecraft
import com.cxxcxx.zinecraft.core.biome.ModSurfaceRule.Rules
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

object ModTerraBlender : TerraBlenderApi {
  override fun onTerraBlenderInitialized() {
    // Weights are kept intentionally low as we add minimal biomes
    Regions.register(
      ExampleRegion(
        Zinecraft.REGISTRAR.id("overworld_1"), 2
      )
    )

    // Register our surface rules
    SurfaceRuleManager.addSurfaceRules(
      SurfaceRuleManager.RuleCategory.OVERWORLD, Zinecraft.MOD_ID, Rules()
    )
  }
}
