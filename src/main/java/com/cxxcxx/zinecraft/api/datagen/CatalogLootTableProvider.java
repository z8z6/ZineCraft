package com.cxxcxx.zinecraft.api.datagen;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.entity.EntityCatalog;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Generates block and entity loot tables declared by the content catalogs.
 */
public final class CatalogLootTableProvider extends LootTableProvider {
  public CatalogLootTableProvider(
      PackOutput output,
      CompletableFuture<HolderLookup.Provider> registries,
      BlockCatalog blocks,
      EntityCatalog entities
  ) {
    super(output, Set.of(), List.of(
        new SubProviderEntry(
            lookup -> new CatalogBlockLoot(blocks, lookup), LootContextParamSets.BLOCK
        ),
        new SubProviderEntry(
            lookup -> new CatalogEntityLoot(entities, lookup), LootContextParamSets.ENTITY
        )
    ), registries);
  }

  private static final class CatalogBlockLoot extends BlockLootSubProvider {
    private final BlockCatalog blocks;

    private CatalogBlockLoot(BlockCatalog blocks, HolderLookup.Provider registries) {
      super(Set.<Item>of(), FeatureFlags.REGISTRY.allFlags(), registries);
      this.blocks = blocks;
    }

    @Override
    protected void generate() {
      for (var entry : blocks.entries) {
        if (entry.dropSelf) dropSelf(entry.block.get());
        else if (entry.dropItem != null) dropOther(entry.block.get(), entry.dropItem);
        else add(entry.block.get(), noDrop());
      }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
      return blocks.entries.stream().map(entry -> (Block) entry.block.get()).toList();
    }
  }

  private static final class CatalogEntityLoot extends EntityLootSubProvider {
    private final EntityCatalog entities;

    private CatalogEntityLoot(EntityCatalog entities, HolderLookup.Provider registries) {
      super(FeatureFlags.REGISTRY.allFlags(), registries);
      this.entities = entities;
    }

    @Override
    public void generate() {
      for (var entity : entities.mobs) {
        LootTable.Builder table = LootTable.lootTable();
        for (var drop : entity.drops) {
          table.withPool(
              LootPool.lootPool()
                  .setRolls(ConstantValue.exactly(1))
                  .add(LootItem.lootTableItem(drop.item()))
          );
        }
        add(entity.type.get(), table);
      }
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
      return entities.mobs.stream().map(entity -> (EntityType<?>) entity.type.get());
    }
  }
}
