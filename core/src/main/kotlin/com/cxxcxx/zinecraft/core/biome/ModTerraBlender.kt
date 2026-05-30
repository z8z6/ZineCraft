package com.cxxcxx.zinecraft.core.biome

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.biome.ModSurfaceRule.Rules
import net.minecraft.resources.ResourceLocation
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

object ModTerraBlender : TerraBlenderApi {
  override fun onTerraBlenderInitialized() {
    // Weights are kept intentionally low as we add minimal biomes
    Regions.register(
      ExampleRegion(
        ResourceLocation.fromNamespaceAndPath(
          ZinecraftCore.MOD_ID, "overworld_1"
        ), 2
      )
    )

    // Register our surface rules
    SurfaceRuleManager.addSurfaceRules(
      SurfaceRuleManager.RuleCategory.OVERWORLD, ZinecraftCore.MOD_ID, Rules()
    )
  }
}
