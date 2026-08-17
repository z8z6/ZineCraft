package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.core.block.NationBlocks;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class ModSurfaceRule {
  @NotNull
  public static final ModSurfaceRule INSTANCE = new ModSurfaceRule();

  private ModSurfaceRule() {
  }

  private final RuleSource singleBlock(Block block) {
    RuleSource ruleSource = SurfaceRules.state(block.defaultBlockState());
    return ruleSource;
  }

  @NotNull
  public final RuleSource rules() {
    RuleSource[] ruleSources = new RuleSource[20];
    RuleSource ruleSource1 = this.singleBlock(NationBlocks.AEGIR_ABYSSAL_SLATE.getBlock());
    ResourceKey[] resourceKeys = new ResourceKey[]{NationBiomes.AEGIR_ABYSSAL_SEA};
    ruleSources[0] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.BOLIVAR_WAR_SCOURED_SOIL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.BOLIVAR_PLAIN};
    ruleSources[1] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.HIGASHI_SHADOW_LOAM.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.HIGASHI_SHADOW_RIFT};
    ruleSources[2] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.DURIN_GARDEN_MOSS.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.DURIN_UNDERGROUND_GARDEN};
    ruleSources[3] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.COLUMBIA_CANYON_SOIL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.COLUMBIA_SANDSTONE_WILDS};
    ruleSources[4] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.KAZIMIERZ_STEPPE_TURF.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.KAZIMIERZ_KNIGHTLAND};
    ruleSources[5] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.KAZDEL_SCARRED_ASH.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.KAZDEL_SCARRED_WASTES};
    ruleSources[6] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.LATERANO_ALLUVIAL_CHALK.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.LATERANO_HOLY_FIELDS};
    ruleSources[7] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.LEITHANIEN_TWILIGHT_HUMUS.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.LEITHANIEN_TWILIGHT_FOREST};
    ruleSources[8] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.RIM_BILLITON_MINE_TAILINGS.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.RIM_BILLITON_MINING_BADLANDS};
    ruleSources[9] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.MINOS_SUNBAKED_EARTH.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.MINOS_SUNLIT_HILLS};
    ruleSources[10] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.SARGON_DESERT_CRUST.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.SARGON_ROCKY_DESERT};
    ruleSources[11] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.SAMI_FROST_MOSS.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.SAMI_FROZEN_FOREST};
    ruleSources[12] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.VICTORIA_MOORLAND_SOIL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.VICTORIA_MISTY_HIGHLANDS};
    ruleSources[13] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.URSUS_PERMAFROST.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.URSUS_FROZEN_STEPPE};
    ruleSources[14] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.KJERAG_SACRED_SNOWSTONE.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.KJERAG_SNOWY_PEAKS};
    ruleSources[15] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.SIRACUSA_RAIN_DARKENED_SOIL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.SIRACUSA_RAINY_WOODLAND};
    ruleSources[16] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.YAN_MOUNTAIN_SOIL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.YAN_MOUNTAIN_GROVE};
    ruleSources[17] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.IBERIA_SALT_CRUSTED_GRAVEL.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.IBERIA_SALT_DELTA};
    ruleSources[18] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.KAZDEL_SCARRED_ASH.getBlock());
    resourceKeys = new ResourceKey[]{NationBiomes.TERRA_CATASTROPHE_ZONE};
    ruleSources[19] = this.onFloorIn(ruleSource1, resourceKeys);
    RuleSource ruleSource = SurfaceRules.sequence(ruleSources);
    return ruleSource;
  }

  private final RuleSource ecologicalSurface(Block primary) {
    Block block = Blocks.GRASS_BLOCK;
    return this.mixedSurface(primary, block);
  }

  private final RuleSource mixedSurface(Block primary, Block patch) {
    RuleSource[] ruleSources = new RuleSource[]{
        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, -0.12, 0.12), this.singleBlock(patch)), this.singleBlock(primary)
    };
    RuleSource ruleSource = SurfaceRules.sequence(ruleSources);
    return ruleSource;
  }

  private final RuleSource onFloorIn(RuleSource surface, ResourceKey<Biome>... biomes) {
    RuleSource ruleSource = SurfaceRules.ifTrue(
        SurfaceRules.isBiome(Arrays.copyOf(biomes, biomes.length)), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)
    );
    return ruleSource;
  }
}
