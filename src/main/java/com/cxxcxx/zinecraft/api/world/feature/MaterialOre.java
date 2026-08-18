package com.cxxcxx.zinecraft.api.world.feature;

import com.cxxcxx.zinecraft.api.block.BlockEntry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Objects;

/**
 * Groups the registrations and recipe metadata belonging to one material ore.
 */
public record MaterialOre(
    BlockEntry<Block> block,
    OreEntry feature,
    ItemLike drop,
    String cookingGroup
) {
  public MaterialOre {
    Objects.requireNonNull(block, "block");
    Objects.requireNonNull(feature, "feature");
    Objects.requireNonNull(drop, "drop");
    if (cookingGroup == null || cookingGroup.isBlank()) {
      throw new IllegalArgumentException("矿石烧炼分组不能为空");
    }
  }
}
