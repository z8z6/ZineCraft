package com.cxxcxx.zinecraft.api.world.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

/**
 * 使用原版主世界噪声路由创建自定义垂直范围的噪声设置。
 */
public final class OverworldNoiseSettingsFactory extends NoiseRouterData {
  private OverworldNoiseSettingsFactory() {
  }

  public static NoiseGeneratorSettings create(
      BootstrapContext<NoiseGeneratorSettings> context,
      int minY,
      int height,
      ResourceKey<DensityFunction> finalDensityKey
  ) {
    HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
    return new NoiseGeneratorSettings(
        NoiseSettings.create(minY, height, 1, 2),
        Blocks.STONE.defaultBlockState(),
        Blocks.WATER.defaultBlockState(),
        withFinalDensity(
            createOverworldRouter(densityFunctions, context.lookup(Registries.NOISE)),
            new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(finalDensityKey))
        ),
        SurfaceRuleData.overworld(),
        new OverworldBiomeBuilder().spawnTarget(),
        63,
        false,
        true,
        true,
        false
    );
  }

  public static NoiseRouter createOverworldRouter(
      HolderGetter<DensityFunction> densityFunctions,
      HolderGetter<NormalNoise.NoiseParameters> noises
  ) {
    return overworld(densityFunctions, noises, false, false);
  }

  private static NoiseRouter withFinalDensity(NoiseRouter router, DensityFunction finalDensity) {
    return new NoiseRouter(
        router.barrierNoise(),
        router.fluidLevelFloodednessNoise(),
        router.fluidLevelSpreadNoise(),
        router.lavaNoise(),
        router.temperature(),
        router.vegetation(),
        router.continents(),
        router.erosion(),
        router.depth(),
        router.ridges(),
        router.initialDensityWithoutJaggedness(),
        finalDensity,
        router.veinToggle(),
        router.veinRidged(),
        router.veinGap()
    );
  }
}
