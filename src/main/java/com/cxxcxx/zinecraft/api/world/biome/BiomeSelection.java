package com.cxxcxx.zinecraft.api.world.biome;

import java.util.Arrays;
import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;

/**
 * A data-pack-compatible biome selection used by NeoForge biome modifiers.
 */
public sealed interface BiomeSelection permits BiomeSelection.TagSelection, BiomeSelection.KeySelection, BiomeSelection.UnionSelection {
  static BiomeSelection overworld() {
    return new TagSelection(Tags.Biomes.IS_OVERWORLD);
  }

  @SafeVarargs
  static BiomeSelection of(ResourceKey<Biome>... keys) {
    return new KeySelection(List.copyOf(Arrays.asList(keys)));
  }

  static BiomeSelection union(BiomeSelection... selections) {
    return new UnionSelection(List.copyOf(Arrays.asList(selections)));
  }

  HolderSet<Biome> resolve(HolderGetter<Biome> biomes);

  record TagSelection(TagKey<Biome> tag) implements BiomeSelection {
    @Override
    public HolderSet<Biome> resolve(HolderGetter<Biome> biomes) {
      return biomes.getOrThrow(tag);
    }
  }

  record KeySelection(List<ResourceKey<Biome>> keys) implements BiomeSelection {
    @Override
    public HolderSet<Biome> resolve(HolderGetter<Biome> biomes) {
      return HolderSet.direct(keys.stream().map(biomes::getOrThrow).toList());
    }
  }

  record UnionSelection(List<BiomeSelection> selections) implements BiomeSelection {
    @Override
    public HolderSet<Biome> resolve(HolderGetter<Biome> biomes) {
      return HolderSet.direct(selections.stream()
          .flatMap(selection -> selection.resolve(biomes).stream())
          .distinct().toList());
    }
  }
}
