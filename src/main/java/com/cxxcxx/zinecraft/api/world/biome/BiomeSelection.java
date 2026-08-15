package com.cxxcxx.zinecraft.api.world.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;

import java.util.Arrays;
import java.util.List;

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

  List<HolderSet<Biome>> resolveParts(HolderGetter<Biome> biomes);

  default HolderSet<Biome> resolve(HolderGetter<Biome> biomes) {
    var parts = resolveParts(biomes);
    if (parts.size() != 1) throw new IllegalStateException("联合群系选择必须拆分为多个 biome modifier");
    return parts.getFirst();
  }

  record TagSelection(TagKey<Biome> tag) implements BiomeSelection {
    @Override
    public List<HolderSet<Biome>> resolveParts(HolderGetter<Biome> biomes) {
      return List.of(biomes.getOrThrow(tag));
    }
  }

  record KeySelection(List<ResourceKey<Biome>> keys) implements BiomeSelection {
    @Override
    public List<HolderSet<Biome>> resolveParts(HolderGetter<Biome> biomes) {
      return List.of(HolderSet.direct(keys.stream().map(biomes::getOrThrow).toList()));
    }
  }

  record UnionSelection(List<BiomeSelection> selections) implements BiomeSelection {
    @Override
    public List<HolderSet<Biome>> resolveParts(HolderGetter<Biome> biomes) {
      return selections.stream().flatMap(selection -> selection.resolveParts(biomes).stream()).toList();
    }
  }
}
