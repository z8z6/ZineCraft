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
    RuleSource[] ruleSources = new RuleSource[19];
    RuleSource ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getAEGIR_ABYSSAL_SLATE());
    ResourceKey[] resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getAEGIR_ABYSSAL_SEA()};
    ruleSources[0] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getBOLIVAR_WAR_SCOURED_SOIL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getBOLIVAR_PLAIN()};
    ruleSources[1] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getHIGASHI_SHADOW_LOAM());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getHIGASHI_SHADOW_RIFT()};
    ruleSources[2] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getDURIN_GARDEN_MOSS());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getDURIN_UNDERGROUND_GARDEN()};
    ruleSources[3] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getCOLUMBIA_CANYON_SOIL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getCOLUMBIA_SANDSTONE_WILDS()};
    ruleSources[4] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getKAZIMIERZ_STEPPE_TURF());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getKAZIMIERZ_KNIGHTLAND()};
    ruleSources[5] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getKAZDEL_SCARRED_ASH());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getKAZDEL_SCARRED_WASTES()};
    ruleSources[6] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getLATERANO_ALLUVIAL_CHALK());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getLATERANO_HOLY_FIELDS()};
    ruleSources[7] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getLEITHANIEN_TWILIGHT_HUMUS());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getLEITHANIEN_TWILIGHT_FOREST()};
    ruleSources[8] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getRIM_BILLITON_MINE_TAILINGS());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getRIM_BILLITON_MINING_BADLANDS()};
    ruleSources[9] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getMINOS_SUNBAKED_EARTH());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getMINOS_SUNLIT_HILLS()};
    ruleSources[10] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getSARGON_DESERT_CRUST());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getSARGON_ROCKY_DESERT()};
    ruleSources[11] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getSAMI_FROST_MOSS());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getSAMI_FROZEN_FOREST()};
    ruleSources[12] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getVICTORIA_MOORLAND_SOIL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getVICTORIA_MISTY_HIGHLANDS()};
    ruleSources[13] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getURSUS_PERMAFROST());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getURSUS_FROZEN_STEPPE()};
    ruleSources[14] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getKJERAG_SACRED_SNOWSTONE());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getKJERAG_SNOWY_PEAKS()};
    ruleSources[15] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getSIRACUSA_RAIN_DARKENED_SOIL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getSIRACUSA_RAINY_WOODLAND()};
    ruleSources[16] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.ecologicalSurface(NationBlocks.INSTANCE.getYAN_MOUNTAIN_SOIL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getYAN_MOUNTAIN_GROVE()};
    ruleSources[17] = this.onFloorIn(ruleSource1, resourceKeys);
    ruleSource1 = this.singleBlock(NationBlocks.INSTANCE.getIBERIA_SALT_CRUSTED_GRAVEL());
    resourceKeys = new ResourceKey[]{NationBiomes.INSTANCE.getIBERIA_SALT_DELTA()};
    ruleSources[18] = this.onFloorIn(ruleSource1, resourceKeys);
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
