package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.core.ZinecraftCore;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerraBlender implements TerraBlenderApi {

  @Override
  public void onTerraBlenderInitialized() {
    // Weights are kept intentionally low as we add minimal biomes
    Regions.register(new ExampleRegion(
        ResourceLocation.fromNamespaceAndPath(
            ZinecraftCore.MOD_ID, "overworld_1"), 2)
    );

    // Register our surface rules
    SurfaceRuleManager.addSurfaceRules(
        SurfaceRuleManager.RuleCategory.OVERWORLD, ZinecraftCore.MOD_ID,
        ExampleSurfaceRule.INSTANCE.makeRules()
    );
  }

}
