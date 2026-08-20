package com.cxxcxx.zinecraft.core.registry;

import com.cxxcxx.zinecraft.api.registry.builder.BlockBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;

import java.util.List;

public final class ModSurfaceRule {
  private static final double ECOLOGICAL_PATCH_MIN = -0.12;
  private static final double ECOLOGICAL_PATCH_MAX = 0.12;

  private static final List<SurfaceDefinition> SURFACES = List.of(
      solid(ModBiome.AEGIR_ABYSSAL_SEA.key(), ModBlock.AEGIR_ABYSSAL_SLATE),
      ecological(ModBiome.BOLIVAR_PLAIN.key(), ModBlock.BOLIVAR_WAR_SCOURED_SOIL),
      ecological(ModBiome.HIGASHI_SHADOW_RIFT.key(), ModBlock.HIGASHI_SHADOW_LOAM),
      solid(ModBiome.DURIN_UNDERGROUND_GARDEN.key(), ModBlock.DURIN_GARDEN_MOSS),
      ecological(ModBiome.COLUMBIA_SANDSTONE_WILDS.key(), ModBlock.COLUMBIA_CANYON_SOIL),
      solid(ModBiome.KAZIMIERZ_KNIGHTLAND.key(), ModBlock.KAZIMIERZ_STEPPE_TURF),
      solid(ModBiome.KAZDEL_SCARRED_WASTES.key(), ModBlock.KAZDEL_SCARRED_ASH),
      ecological(ModBiome.LATERANO_HOLY_FIELDS.key(), ModBlock.LATERANO_ALLUVIAL_CHALK),
      ecological(ModBiome.LEITHANIEN_TWILIGHT_FOREST.key(), ModBlock.LEITHANIEN_TWILIGHT_HUMUS),
      solid(ModBiome.RIM_BILLITON_MINING_BADLANDS.key(), ModBlock.RIM_BILLITON_MINE_TAILINGS),
      ecological(ModBiome.MINOS_SUNLIT_HILLS.key(), ModBlock.MINOS_SUNBAKED_EARTH),
      solid(ModBiome.SARGON_ROCKY_DESERT.key(), ModBlock.SARGON_DESERT_CRUST),
      solid(ModBiome.SAMI_FROZEN_FOREST.key(), ModBlock.SAMI_FROST_MOSS),
      ecological(ModBiome.VICTORIA_MISTY_HIGHLANDS.key(), ModBlock.VICTORIA_MOORLAND_SOIL),
      solid(ModBiome.URSUS_FROZEN_STEPPE.key(), ModBlock.URSUS_PERMAFROST),
      solid(ModBiome.KJERAG_SNOWY_PEAKS.key(), ModBlock.KJERAG_SACRED_SNOWSTONE),
      ecological(ModBiome.SIRACUSA_RAINY_WOODLAND.key(), ModBlock.SIRACUSA_RAIN_DARKENED_SOIL),
      ecological(ModBiome.YAN_MOUNTAIN_GROVE.key(), ModBlock.YAN_MOUNTAIN_SOIL),
      solid(ModBiome.IBERIA_SALT_DELTA.key(), ModBlock.IBERIA_SALT_CRUSTED_GRAVEL),
      solid(ModBiome.TERRA_CATASTROPHE_ZONE.key(), ModBlock.KAZDEL_SCARRED_ASH)
  );

  private ModSurfaceRule() {
  }

  private static SurfaceDefinition solid(ResourceKey<Biome> biome, BlockBuilder<? extends Block> block) {
    return new SurfaceDefinition(biome, block, SurfaceType.SOLID);
  }

  private static SurfaceDefinition ecological(ResourceKey<Biome> biome, BlockBuilder<? extends Block> block) {
    return new SurfaceDefinition(biome, block, SurfaceType.ECOLOGICAL);
  }

  private static RuleSource blockStateRule(Block block) {
    return SurfaceRules.state(block.defaultBlockState());
  }

  private static RuleSource ecologicalSurface(Block primary) {
    return SurfaceRules.sequence(
        SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(Noises.SURFACE_SECONDARY, ECOLOGICAL_PATCH_MIN, ECOLOGICAL_PATCH_MAX),
            blockStateRule(Blocks.GRASS_BLOCK)
        ),
        blockStateRule(primary)
    );
  }

  private static RuleSource onFloorIn(ResourceKey<Biome> biome, RuleSource surface) {
    return SurfaceRules.ifTrue(
        SurfaceRules.isBiome(biome),
        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface)
    );
  }

  public static RuleSource rules() {
    RuleSource[] biomeRules = SURFACES.stream()
        .map(SurfaceDefinition::rule)
        .toArray(RuleSource[]::new);
    return SurfaceRules.sequence(biomeRules);
  }

  private enum SurfaceType {
    SOLID,
    ECOLOGICAL
  }

  private record SurfaceDefinition(
      ResourceKey<Biome> biome,
      BlockBuilder<? extends Block> block,
      SurfaceType type
  ) {
    private RuleSource rule() {
      Block surfaceBlock = block.get();
      RuleSource surface = type == SurfaceType.ECOLOGICAL
          ? ecologicalSurface(surfaceBlock)
          : blockStateRule(surfaceBlock);
      return onFloorIn(biome, surface);
    }
  }
}
