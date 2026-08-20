package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

/**
 * 方块注册目录，统一登记方块、可选方块物品、翻译及数据生成元数据。
 */
public final class BlockCatalog {
  public final DeferredRegister.Blocks registry;
  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final List<BlockBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<BlockBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * 创建方块注册目录。
   *
   * @param mod          模组命名空间
   * @param items        用于登记方块物品的物品目录
   * @param translations 用于登记方块名称的翻译目录
   */
  public BlockCatalog(String mod, ItemCatalog items, TranslationCatalog translations) {
    this.registry = DeferredRegister.createBlocks(mod);
    this.items = Objects.requireNonNull(items, "物品目录不能为空");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 校验方块路径和本地化名称。
   *
   * @param path 方块的命名空间内路径
   * @param zhCn 简体中文名称
   * @param enUs 英文名称
   */
  private static void validate(String path, String zhCn, String enUs) {
    if (!ResourceLocation.isValidPath(path)) throw new IllegalArgumentException("方块 ID 路径无效：" + path);
    if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("方块中文名不能为空：" + path);
    if (enUs == null || enUs.isBlank()) throw new IllegalArgumentException("方块英文名不能为空：" + path);
  }

  /**
   * 登记方块、可选方块物品和名称翻译。
   *
   * @param builder 方块声明
   * @param <T> 方块类型
   * @return 已绑定登记句柄的方块声明
   */
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

  /**
   * 将方块延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }
}
