package com.cxxcxx.zinecraft.api.datagen;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

/**
 * Generates block loot tables declared by {@link BlockCatalog}.
 */
public final class CatalogLootTableProvider extends LootTableProvider {
  public CatalogLootTableProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> registries,
      BlockCatalog blocks
  ) {
    super(output, Set.of(), List.of(new SubProviderEntry(
        lookup -> new CatalogBlockLoot(blocks, lookup), LootContextParamSets.BLOCK
    )), registries);
  }

  private static final class CatalogBlockLoot extends BlockLootSubProvider {
    private final BlockCatalog blocks;

    private CatalogBlockLoot(BlockCatalog blocks, HolderLookup.Provider registries) {
      super(Set.<Item>of(), FeatureFlags.REGISTRY.allFlags(), registries);
      this.blocks = blocks;
    }

    @Override
    protected void generate() {
      for (var entry : blocks.getEntries$zinecraft()) {
        if (entry.getDropSelf$zinecraft()) dropSelf(entry.getBlock());
        else if (entry.getDropItem$zinecraft() != null) dropOther(entry.getBlock(), entry.getDropItem$zinecraft());
      }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
      return blocks.getEntries$zinecraft().stream().map(entry -> (Block) entry.getBlock()).toList();
    }
  }
}
