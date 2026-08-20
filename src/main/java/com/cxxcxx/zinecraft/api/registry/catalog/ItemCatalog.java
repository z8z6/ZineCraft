package com.cxxcxx.zinecraft.api.registry.catalog;

import com.cxxcxx.zinecraft.api.registry.builder.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 物品注册目录，统一登记物品、翻译及数据生成元数据。
 */
public final class ItemCatalog {
  public final DeferredRegister.Items registry;
  private final TranslationCatalog translations;
  private final List<ItemBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<ItemBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  /**
   * 创建物品注册目录。
   *
   * @param mod          模组命名空间
   * @param translations 用于登记物品名称的翻译目录
   */
  public ItemCatalog(String mod, TranslationCatalog translations) {
    this.registry = DeferredRegister.createItems(mod);
    this.translations = translations;
  }

  /**
   * 校验并登记物品及名称翻译。
   *
   * @param builder 物品声明
   * @param <T> 物品类型
   * @return 已绑定延迟物品句柄的声明
   */
  public <T extends Item> ItemBuilder<T> register(ItemBuilder<T> builder) {
    if (!ResourceLocation.isValidPath(builder.path)) {
      throw new IllegalArgumentException("物品 ID 路径无效：" + builder.path);
    }
    if (builder.zhCn == null || builder.zhCn.isBlank()) {
      throw new IllegalArgumentException("物品中文名不能为空：" + builder.path);
    }
    if (builder.enUs == null || builder.enUs.isBlank()) {
      throw new IllegalArgumentException("物品英文名不能为空：" + builder.path);
    }
    Objects.requireNonNull(builder.factory, "物品 factory 不能为空：" + builder.path);
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("物品 ID 重复：" + builder.path);
    }
    builder.item = registry.register(builder.path, builder.factory);
    mutableEntries.add(builder);
    translations.add(builder.transKey(), builder.zhCn, builder.enUs);
    return builder;
  }

  /**
   * 将物品延迟注册器挂接到模组事件总线。
   *
   * @param modBus 模组事件总线
   */
  public void register(IEventBus modBus) {
    registry.register(modBus);
  }
}
