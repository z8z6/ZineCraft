package com.cxxcxx.zinecraft.api.item;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class CreativeTabCatalog {
  private final ModRegistrar registrar;
  private final ItemCatalog items;
  private final BlockCatalog blocks;
  private final TranslationCatalog translations;
  private final List<Consumer<CreativeModeTab.Output>> contributors = new ArrayList<>();

  public CreativeTabCatalog(ModRegistrar registrar, ItemCatalog items, BlockCatalog blocks, TranslationCatalog translations) {
    this.registrar = registrar;
    this.items = items;
    this.blocks = blocks;
    this.translations = translations;
  }

  public static CreativeTabEntry registerWithDefaults(CreativeTabCatalog self, String path, String zhCn,
                                                      String enUs, Supplier<ItemStack> icon, boolean includeBlocks, int mask, Object marker) {
    return self.register(path, zhCn, enUs, icon, (mask & 16) != 0 || includeBlocks);
  }

  public CreativeTabEntry register(String path, String zhCn, String enUs, Supplier<ItemStack> icon, boolean includeBlocks) {
    return register(path, zhCn, enUs, icon, output -> {
      for (var entry : items.getEntries()) {
        if (entry.getIncludeInCreative()) output.accept(entry.getItem());
      }
      if (includeBlocks) {
        for (var entry : blocks.getEntries()) {
          if (entry.getRegisterItem()) output.accept(entry.getBlock().asItem());
        }
      }
      contributors.forEach(contributor -> contributor.accept(output));
    });
  }

  /**
   * 注册只展示调用方指定物品的独立创造模式页。
   */
  public CreativeTabEntry register(
      String path,
      String zhCn,
      String enUs,
      Supplier<ItemStack> icon,
      Consumer<CreativeModeTab.Output> displayItems
  ) {
    if (!ResourceLocation.isValidPath(path)) throw new IllegalArgumentException("创造模式页 ID 路径无效：" + path);
    if (zhCn == null || zhCn.isBlank()) throw new IllegalArgumentException("创造模式页中文名不能为空：" + path);
    if (enUs == null || enUs.isBlank()) throw new IllegalArgumentException("创造模式页英文名不能为空：" + path);
    Objects.requireNonNull(icon, "创造模式页图标不能为空");
    Objects.requireNonNull(displayItems, "创造模式页内容不能为空");
    String translationKey = "itemGroup." + registrar.getNamespace() + "." + path;
    translations.add(translationKey, zhCn, enUs);
    var tab = CreativeModeTab.builder()
        .icon(icon)
        .title(Component.translatable(translationKey))
        .displayItems((parameters, output) -> displayItems.accept(output))
        .build();
    Pair<net.minecraft.resources.ResourceKey<CreativeModeTab>, CreativeModeTab> registration = registrar.creativeTab(path, tab);
    return new CreativeTabEntry(registration.getFirst(), registration.getSecond());
  }

  public void addContributor(Consumer<CreativeModeTab.Output> contributor) {
    contributors.add(contributor);
  }
}
