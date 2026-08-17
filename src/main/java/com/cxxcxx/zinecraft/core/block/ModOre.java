package com.cxxcxx.zinecraft.core.block;

import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.world.biome.BiomeSelection;
import com.cxxcxx.zinecraft.api.world.feature.OreEntry;
import com.cxxcxx.zinecraft.core.Zinecraft;
import com.cxxcxx.zinecraft.core.biome.ModBiome;
import com.cxxcxx.zinecraft.core.item.ModItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public final class ModOre {
  public static final ModOre INSTANCE = new ModOre();

  private static final BiomeSelection MATERIAL_DIMENSIONS = BiomeSelection.union(
      BiomeSelection.overworld(),
      BiomeSelection.of(ModBiome.ALL_TERRA_BIOMES)
  );

  public static final MaterialOre ORIGINITE_ORE = ore(
      "originite_ore", "originite_ore", "源石矿", ModItem.INSTANCE.ORIGINITE,
      "originite", 3, 2, -32, 0.25F
  );
  public static final MaterialOre ORIROCK_ORE = ore(
      "orirock_ore", "orirock_ore", "源岩矿", ModItem.INSTANCE.ORIROCK,
      "orirock", 10, 12, 64, 0.0F
  );
  public static final MaterialOre ORIRON_ORE = ore(
      "oriron_ore", "oriron_ore", "异铁矿", ModItem.INSTANCE.ORIRON_SHARD,
      "oriron_shard", 7, 8, 32, 0.0F
  );
  public static final MaterialOre MANGANESE_ORE = ore(
      "manganese_ore_block", "manganese_ore", "轻锰矿脉", ModItem.INSTANCE.MANGANESE_ORE,
      "manganese_ore", 5, 6, 16, 0.1F
  );
  public static final MaterialOre GRINDSTONE_ORE = ore(
      "grindstone_ore", "grindstone_ore", "研磨石矿", ModItem.INSTANCE.GRINDSTONE,
      "grindstone", 6, 5, 0, 0.15F
  );
  public static final MaterialOre RMA70_ORE = ore(
      "rma70_ore", "rma70_ore", "RMA70 矿", ModItem.INSTANCE.RMA70_12,
      "rma70_12", 4, 3, -32, 0.25F
  );
  public static final MaterialOre CRYSTAL_ELEMENT_ORE = ore(
      "crystal_element_ore", "crystal_element_ore", "晶体元件矿", ModItem.INSTANCE.CRYSTAL_ELEMENT,
      "crystal_element", 5, 4, 16, 0.15F
  );
  public static final MaterialOre LOXIC_KOHL_ORE = ore(
      "loxic_kohl_ore", "loxic_kohl_ore", "炽合金矿", ModItem.INSTANCE.LOXIC_KOHL,
      "loxic_kohl", 4, 3, -16, 0.2F
  );

  public static final List<MaterialOre> ALL = List.of(
      ORIGINITE_ORE, ORIROCK_ORE, ORIRON_ORE, MANGANESE_ORE,
      GRINDSTONE_ORE, RMA70_ORE, CRYSTAL_ELEMENT_ORE, LOXIC_KOHL_ORE
  );

  private ModOre() {
  }

  private static MaterialOre ore(
      String blockPath,
      String featurePath,
      String zhCn,
      ItemLike drop,
      String cookingGroup,
      int veinSize,
      int veinsPerChunk,
      int maxY,
      float discardChance
  ) {
    DeferredBlock<Block> block = Zinecraft.BLOCKS.builder(blockPath, zhCn, ModOre::createOre)
        .enUs(TranslationNames.toDisplayName(blockPath))
        .drop(drop)
        .build();
    OreEntry feature = Zinecraft.WORLDGEN.getFeatures().ore(
        featurePath,
        block,
        veinSize,
        veinsPerChunk,
        maxY,
        discardChance,
        MATERIAL_DIMENSIONS
    );
    return new MaterialOre(block, feature, drop, cookingGroup);
  }

  private static Block createOre() {
    return new Block(
        Properties.ofFullCopy(Blocks.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.0F, 6.0F)
            .sound(SoundType.DEEPSLATE)
    );
  }

  public record MaterialOre(
      DeferredBlock<Block> block,
      OreEntry feature,
      ItemLike drop,
      String cookingGroup
  ) {
  }
}
