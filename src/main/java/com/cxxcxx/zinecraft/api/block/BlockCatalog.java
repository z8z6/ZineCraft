package com.cxxcxx.zinecraft.api.block;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalogKt;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import kotlin.jvm.functions.Function0;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class BlockCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final TranslationCatalog translations;
  @NotNull
  private final List<BlockEntry<?>> entries;

  public BlockCatalog(@NotNull ModRegistrar registrar, @NotNull TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  // $VF: synthetic method
  public static BlockEntry register$default(
      BlockCatalog var0, String var1, String var2, String var3, boolean var4, ItemLike var5, boolean var6, boolean var7, Function0 var8, int var9, Object var10
  ) {
    if ((var9 & 4) != 0) {
      var3 = TranslationCatalogKt.toDisplayName(var1);
    }

    if ((var9 & 8) != 0) {
      var4 = true;
    }

    if ((var9 & 16) != 0) {
      var5 = null;
    }

    if ((var9 & 32) != 0) {
      var6 = true;
    }

    if ((var9 & 64) != 0) {
      var7 = true;
    }

    return var0.register(var1, var2, var3, var4, var5, var6, var7, var8);
  }

  @NotNull
  public final List<BlockEntry<?>> getEntries$zinecraft() {
    return this.entries;
  }

  @NotNull
  public final <T extends Block> BlockEntry<T> register(
      @NotNull String path,
      @NotNull String zhCn,
      @NotNull String enUs,
      boolean dropSelf,
      @Nullable ItemLike dropItem,
      boolean cubeModel,
      boolean registerItem,
      @NotNull Function0<? extends T> factory
  ) {
    if (dropSelf && dropItem != null) {
      int i = 0;
      String string = "方块不能同时掉落自身和指定物品: " + path;
      throw new IllegalArgumentException(string.toString());
    } else {
      var block = ModRegistrar.block$default(this.registrar, path, factory::invoke, registerItem, null, 8, null);
      BlockEntry blockEntry = new BlockEntry<>(path, block, dropSelf, dropItem, cubeModel, registerItem);
      this.entries.add(blockEntry);
      TranslationCatalog translationCatalog = this.translations;
      String string1 = "block." + this.registrar.getNamespace() + "." + path;
      translationCatalog.add(string1, zhCn, enUs);
      return blockEntry;
    }
  }
}
