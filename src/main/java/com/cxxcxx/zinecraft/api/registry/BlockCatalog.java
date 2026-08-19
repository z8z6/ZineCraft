package com.cxxcxx.zinecraft.api.registry;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.builder.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Supplier;

/**
 * Shared block registration API. Content classes should declare entries through {@link #builder}.
 */
public final class BlockCatalog {
  public final DeferredRegister.Blocks registry;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<BlockBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<BlockBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  public BlockCatalog(String mod, ItemCatalog items, TranslationCatalog translations) {
    this.registry = DeferredRegister.createBlocks(mod);
    this.items = Objects.requireNonNull(items, "物品目录不能为空");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  private static void validate(String path, String zhCn, String enUs) {
    if (!ResourceLocation.isValidPath(path)) throw new IllegalArgumentException("方块 ID 路径无效：" + path);
    if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("方块中文名不能为空：" + path);
    if (enUs == null || enUs.isBlank()) throw new IllegalArgumentException("方块英文名不能为空：" + path);
  }

  public <T extends Block> BlockBuilder<T> builder(String path, String zhCn, Supplier<? extends T> factory) {
    return new BlockBuilder<>(this, path, zhCn, factory);
  }

  public <T extends Block> BlockBuilder<T> register(BlockBuilder<T> builder) {
    validate(builder.path, builder.zhCn, builder.enUs);
    Objects.requireNonNull(builder.factory, "方块 factory 不能为空：" + builder.path);
    Objects.requireNonNull(builder.itemProperties, "方块物品属性不能为空：" + builder.path);
    if (builder.dropSelf && builder.dropItem != null) {
      throw new IllegalArgumentException("方块不能同时掉落自身和指定物品：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("方块 ID 重复：" + builder.path);
    }

    var block = registry.register(builder.path, builder.factory);
    DeferredItem<BlockItem> blockItem = builder.registerItem
        ? items.registry.register(builder.path, () -> new BlockItem(block.get(), builder.itemProperties))
        : null;
    builder.block = block;
    builder.blockItem = Optional.ofNullable(blockItem);
    mutableEntries.add(builder);
    translations.add("block." + registry.getNamespace() + "." + builder.path, builder.zhCn, builder.enUs);
    return builder;
  }
}
