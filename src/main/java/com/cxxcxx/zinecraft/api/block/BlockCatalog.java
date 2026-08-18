package com.cxxcxx.zinecraft.api.block;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationNames;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Shared block registration API. Content classes should declare entries through {@link #builder}.
 */
public final class BlockCatalog {
  private final ModRegistrar registrar;
  private final TranslationCatalog translations;
  private final List<BlockBuilder<?>> mutableEntries = new ArrayList<>();
  public final List<BlockBuilder<?>> entries = Collections.unmodifiableList(mutableEntries);

  public BlockCatalog(ModRegistrar registrar, TranslationCatalog translations) {
    this.registrar = Objects.requireNonNull(registrar, "registrar");
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

  private <T extends Block> BlockEntry<T> register(BlockBuilder<T> builder) {
    validate(builder.path, builder.zhCn, builder.enUs);
    Objects.requireNonNull(builder.factory, "方块 factory 不能为空：" + builder.path);
    Objects.requireNonNull(builder.itemProperties, "方块物品属性不能为空：" + builder.path);
    if (builder.dropSelf && builder.dropItem != null) {
      throw new IllegalArgumentException("方块不能同时掉落自身和指定物品：" + builder.path);
    }
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("方块 ID 重复：" + builder.path);
    }

    var registration = registrar.<T>block(
        builder.path, builder.factory, builder.registerItem, builder.itemProperties
    );
    BlockEntry<T> entry = new BlockEntry<>(
        registration.block(), registration.blockItem(), builder.dropSelf, builder.dropItem
    );
    builder.entry = entry;
    mutableEntries.add(builder);
    translations.add("block." + registrar.namespace + "." + builder.path, builder.zhCn, builder.enUs);
    return entry;
  }

  /**
   * Holds both the declaration options and the lazy NeoForge block handle.
   */
  public static final class BlockBuilder<T extends Block> {
    private final BlockCatalog catalog;
    private final Supplier<? extends T> factory;
    public final String path;
    public final String zhCn;
    public String enUs;
    public boolean dropSelf = true;
    public ItemLike dropItem;
    public boolean cubeModel = true;
    public boolean registerItem = true;
    public Item.Properties itemProperties = new Item.Properties();
    public BlockEntry<T> entry;

    private BlockBuilder(BlockCatalog catalog, String path, String zhCn, Supplier<? extends T> factory) {
      this.catalog = catalog;
      this.path = path;
      this.zhCn = zhCn;
      this.enUs = TranslationNames.toDisplayName(path);
      this.factory = Objects.requireNonNull(factory, "方块 factory 不能为空：" + path);
    }

    public BlockBuilder<T> enUs(String enUs) {
      this.enUs = enUs;
      return this;
    }

    public BlockBuilder<T> noLoot() {
      dropSelf = false;
      dropItem = null;
      return this;
    }

    public BlockBuilder<T> drop(ItemLike item) {
      dropSelf = false;
      dropItem = Objects.requireNonNull(item, "drop item");
      return this;
    }

    public BlockBuilder<T> noCubeModel() {
      cubeModel = false;
      return this;
    }

    public BlockBuilder<T> noBlockItem() {
      registerItem = false;
      return this;
    }

    public BlockBuilder<T> itemProperties(Item.Properties itemProperties) {
      this.itemProperties = itemProperties;
      return this;
    }

    public BlockEntry<T> build() {
      if (entry != null) throw new IllegalStateException("方块 builder 不能重复 build：" + path);
      return catalog.register(this);
    }
  }
}
