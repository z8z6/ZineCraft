package com.cxxcxx.zinecraft.core.biome;

import com.cxxcxx.zinecraft.core.block.ModNationBlock;
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
    RuleSource ruleSource1 = this.singleBlock(ModNationBlock.AEGIR_ABYSSAL_SLATE.get());
    ResourceKey[] resourceKeys = new ResourceKey[]{ModBiome.AEGIR_ABYSSAL_SEA};
    ruleSources[0] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.BOLIVAR_WAR_SCOURED_SOIL.get());
    resourceKeys = new ResourceKey[]{ModBiome.BOLIVAR_PLAIN};
    ruleSources[1] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.HIGASHI_SHADOW_LOAM.get());
    resourceKeys = new ResourceKey[]{ModBiome.HIGASHI_SHADOW_RIFT};
    ruleSources[2] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.DURIN_GARDEN_MOSS.get());
    resourceKeys = new ResourceKey[]{ModBiome.DURIN_UNDERGROUND_GARDEN};
    ruleSources[3] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.COLUMBIA_CANYON_SOIL.get());
    resourceKeys = new ResourceKey[]{ModBiome.COLUMBIA_SANDSTONE_WILDS};
    ruleSources[4] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.KAZIMIERZ_STEPPE_TURF.get());
    resourceKeys = new ResourceKey[]{ModBiome.KAZIMIERZ_KNIGHTLAND};
    ruleSources[5] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.KAZDEL_SCARRED_ASH.get());
    resourceKeys = new ResourceKey[]{ModBiome.KAZDEL_SCARRED_WASTES};
    ruleSources[6] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.LATERANO_ALLUVIAL_CHALK.get());
    resourceKeys = new ResourceKey[]{ModBiome.LATERANO_HOLY_FIELDS};
    ruleSources[7] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.LEITHANIEN_TWILIGHT_HUMUS.get());
    resourceKeys = new ResourceKey[]{ModBiome.LEITHANIEN_TWILIGHT_FOREST};
    ruleSources[8] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.RIM_BILLITON_MINE_TAILINGS.get());
    resourceKeys = new ResourceKey[]{ModBiome.RIM_BILLITON_MINING_BADLANDS};
    ruleSources[9] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.MINOS_SUNBAKED_EARTH.get());
    resourceKeys = new ResourceKey[]{ModBiome.MINOS_SUNLIT_HILLS};
    ruleSources[10] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.SARGON_DESERT_CRUST.get());
    resourceKeys = new ResourceKey[]{ModBiome.SARGON_ROCKY_DESERT};
    ruleSources[11] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.SAMI_FROST_MOSS.get());
    resourceKeys = new ResourceKey[]{ModBiome.SAMI_FROZEN_FOREST};
    ruleSources[12] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.VICTORIA_MOORLAND_SOIL.get());
    resourceKeys = new ResourceKey[]{ModBiome.VICTORIA_MISTY_HIGHLANDS};
    ruleSources[13] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.URSUS_PERMAFROST.get());
    resourceKeys = new ResourceKey[]{ModBiome.URSUS_FROZEN_STEPPE};
    ruleSources[14] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.KJERAG_SACRED_SNOWSTONE.get());
    resourceKeys = new ResourceKey[]{ModBiome.KJERAG_SNOWY_PEAKS};
    ruleSources[15] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.SIRACUSA_RAIN_DARKENED_SOIL.get());
    resourceKeys = new ResourceKey[]{ModBiome.SIRACUSA_RAINY_WOODLAND};
    ruleSources[16] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(ModNationBlock.YAN_MOUNTAIN_SOIL.get());
    resourceKeys = new ResourceKey[]{ModBiome.YAN_MOUNTAIN_GROVE};
    ruleSources[17] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.IBERIA_SALT_CRUSTED_GRAVEL.get());
    resourceKeys = new ResourceKey[]{ModBiome.IBERIA_SALT_DELTA};
    ruleSources[18] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(ModNationBlock.KAZDEL_SCARRED_ASH.get());
    resourceKeys = new ResourceKey[]{ModBiome.TERRA_CATASTROPHE_ZONE};
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
