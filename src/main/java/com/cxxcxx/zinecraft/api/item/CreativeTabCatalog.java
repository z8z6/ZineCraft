package com.cxxcxx.zinecraft.api.item;

import com.cxxcxx.zinecraft.api.block.BlockCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

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

  public static CreativeTabEntry register$default(CreativeTabCatalog self, String path, String zhCn,
                                                  String enUs, Function0<ItemStack> icon, boolean includeBlocks, int mask, Object marker) {
    return self.register(path, zhCn, enUs, icon, (mask & 16) != 0 || includeBlocks);
  }

  public CreativeTabEntry register(String path, String zhCn, String enUs, Function0<ItemStack> icon, boolean includeBlocks) {
    String translationKey = "itemGroup." + registrar.getNamespace() + "." + path;
    translations.add(translationKey, zhCn, enUs);
    var tab = CreativeModeTab.builder()
        .icon(icon::invoke)
        .title(Component.translatable(translationKey))
        .displayItems((parameters, output) -> {
          for (var entry : items.getEntries$zinecraft()) {
            if (entry.getIncludeInCreative$zinecraft()) output.accept(entry.getItem());
          }
          if (includeBlocks) {
            for (var entry : blocks.getEntries$zinecraft()) {
              if (entry.getRegisterItem$zinecraft()) output.accept(entry.getBlock().asItem());
            }
          }
          contributors.forEach(contributor -> contributor.accept(output));
        })
        .build();
    Pair<net.minecraft.resources.ResourceKey<CreativeModeTab>, CreativeModeTab> registration = registrar.creativeTab(path, tab);
    return new CreativeTabEntry(registration.getFirst(), registration.getSecond());
  }

  public void addContributor(Consumer<CreativeModeTab.Output> contributor) {
    contributors.add(contributor);
  }
}
