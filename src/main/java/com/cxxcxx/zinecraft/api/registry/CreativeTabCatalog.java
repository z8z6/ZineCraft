package com.cxxcxx.zinecraft.api.registry;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;


public final class CreativeTabCatalog {
  private final ModRegistrar registrar;
  private final ItemCatalog items;
  private final BlockCatalog blocks;
  private final TranslationCatalog translations;
  private final List<CreativeTabBuilder> mutableEntries = new ArrayList<>();
  public final List<CreativeTabBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public CreativeTabCatalog(ModRegistrar registrar, ItemCatalog items, BlockCatalog blocks, TranslationCatalog translations) {
    this.registrar = Objects.requireNonNull(registrar, "registrar");
    this.items = Objects.requireNonNull(items, "items");
    this.blocks = Objects.requireNonNull(blocks, "blocks");
    this.translations = Objects.requireNonNull(translations, "translations");
  }

  public CreativeTabBuilder builder(String path, String zhCn) {
    return new CreativeTabBuilder(this, path, zhCn);
  }

  private CreativeModeTab register(CreativeTabBuilder builder) {
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

    String translationKey = "itemGroup." + registrar.namespace + "." + builder.path;
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
    var registration = registrar.creativeTab(builder.path, tab);
    builder.key = registration.getFirst();
    builder.tab = registration.getSecond();
    mutableEntries.add(builder);
    return builder.tab;
  }

  public static final class CreativeTabBuilder {
    private final CreativeTabCatalog catalog;
    public final String path;
    public final String zhCn;
    public String enUs;
    public Supplier<ItemStack> icon;
    public boolean includeItems;
    public boolean includeBlocks;
    public Consumer<CreativeModeTab.Output> displayItems = output -> {
    };
    public ResourceKey<CreativeModeTab> key;
    public CreativeModeTab tab;

    private CreativeTabBuilder(CreativeTabCatalog catalog, String path, String zhCn) {
      this.catalog = catalog;
      this.path = path;
      this.zhCn = zhCn;
      this.enUs = zhCn;
    }

    public CreativeTabBuilder enUs(String enUs) {
      this.enUs = enUs;
      return this;
    }

    public CreativeTabBuilder icon(ItemLike icon) {
      this.icon = () -> new ItemStack(icon);
      return this;
    }

    public CreativeTabBuilder icon(Supplier<ItemStack> icon) {
      this.icon = icon;
      return this;
    }

    public CreativeTabBuilder includeCatalogItems() {
      includeItems = true;
      return this;
    }

    public CreativeTabBuilder includeCatalogBlocks() {
      includeBlocks = true;
      return this;
    }

    public CreativeTabBuilder displayItems(Consumer<CreativeModeTab.Output> displayItems) {
      this.displayItems = Objects.requireNonNull(displayItems, "displayItems");
      return this;
    }

    public CreativeModeTab build() {
      if (tab != null) throw new IllegalStateException("创造模式页 builder 不能重复 build：" + path);
      return catalog.register(this);
    }
  }
}
