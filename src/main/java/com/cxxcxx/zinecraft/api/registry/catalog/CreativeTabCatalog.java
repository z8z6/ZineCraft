package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.CreativeTabBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * 创造模式物品栏页注册目录，可汇总物品和方块目录中的内容。
 */
public final class CreativeTabCatalog {
  private final String namespace;
  private final DeferredRegister<CreativeModeTab> registry;
  private final ItemCatalog items;
  private final BlockCatalog blocks;
  private final TranslationCatalog translations;
  private final List<CreativeTabBuilder> mutableEntries = new ArrayList<>();
  public final List<CreativeTabBuilder> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * 创建创造模式页注册目录。
   *
   * @param namespace    模组命名空间
   * @param items        可供页面自动收集的物品目录
   * @param blocks       可供页面自动收集的方块目录
   * @param translations 用于登记页面标题的翻译目录
   */
  public CreativeTabCatalog(String namespace, ItemCatalog items, BlockCatalog blocks, TranslationCatalog translations) {
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.registry = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), namespace);
    this.items = Objects.requireNonNull(items, "items");
    this.blocks = Objects.requireNonNull(blocks, "blocks");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  /**
   * 校验并登记创造模式页、标题翻译和展示内容。
   *
   * @param builder 创造模式页声明
   */
  public void register(CreativeTabBuilder builder) {
    if (!ResourceLocation.isValidPath(builder.path))
      throw new IllegalArgumentException("创造模式页 ID 路径无效：" + builder.path);
    if (builder.zhCn == null || builder.zhCn.isBlank())
      throw new IllegalArgumentException("创造模式页中文名不能为空：" + builder.path);
    if (builder.enUs == null || builder.enUs.isBlank())
      throw new IllegalArgumentException("创造模式页英文名不能为空：" + builder.path);
    Objects.requireNonNull(builder.icon, "创造模式页图标不能为空：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("创造模式页 ID 重复：" + builder.path);
    }

    String translationKey = "itemGroup." + namespace + "." + builder.path;
    translations.add(translationKey, builder.zhCn, builder.enUs);
    CreativeModeTab tab = CreativeModeTab.builder()
        .icon(builder.icon)
        .title(Component.translatable(translationKey))
        .displayItems((parameters, output) -> {
          if (builder.includeItems) {
            items.entries.stream().filter(entry -> entry.inCreativeTab).forEach(entry -> output.accept(entry.getItem()));
          }
          if (builder.includeBlocks) {
            blocks.entries.forEach(entry -> entry.blockItem().ifPresent(output::accept));
          }
          builder.displayItems.accept(output);
        })
        .build();
    registry.register(builder.path, () -> tab);
    builder.key = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(),
        ResourceLocation.fromNamespaceAndPath(namespace, builder.path)
    );
    builder.tab = tab;
    mutableEntries.add(builder);
  }

  /**
   * 将创造模式页延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }

}
