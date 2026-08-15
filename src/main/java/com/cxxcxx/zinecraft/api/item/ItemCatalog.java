package com.cxxcxx.zinecraft.api.item;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalogKt;
import com.cxxcxx.zinecraft.api.registry.ModRegistrar;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ItemCatalog {
  @NotNull
  private final ModRegistrar registrar;
  @NotNull
  private final TranslationCatalog translations;
  @NotNull
  private final List<ItemEntry<?>> entries;

  public ItemCatalog(@NotNull ModRegistrar registrar, @NotNull TranslationCatalog translations) {
    super();
    this.registrar = registrar;
    this.translations = translations;
    this.entries = new ArrayList<>();
  }

  // $VF: synthetic method
  public static ItemEntry register$default(
      ItemCatalog var0, String var1, String var2, String var3, ModelTemplate var4, boolean var5, Function0 var6, int var7, Object var8
  ) {
    if ((var7 & 4) != 0) {
      var3 = TranslationCatalogKt.toDisplayName(var1);
    }

    if ((var7 & 8) != 0) {
      ModelTemplate modelTemplate = ModelTemplates.FLAT_ITEM;
      var4 = modelTemplate;
    }

    if ((var7 & 16) != 0) {
      var5 = true;
    }

    return var0.register(var1, var2, var3, var4, var5, var6);
  }

  // $VF: synthetic method
  public static ItemEntry register$default(
      ItemCatalog var0, String var1, String var2, String var3, ModelTemplate var4, Properties var5, boolean var6, int var7, Object var8
  ) {
    if ((var7 & 4) != 0) {
      var3 = TranslationCatalogKt.toDisplayName(var1);
    }

    if ((var7 & 8) != 0) {
      ModelTemplate modelTemplate = ModelTemplates.FLAT_ITEM;
      var4 = modelTemplate;
    }

    if ((var7 & 16) != 0) {
      var5 = new Properties();
    }

    if ((var7 & 32) != 0) {
      var6 = true;
    }

    return var0.register(var1, var2, var3, var4, var5, var6);
  }

  private static final Item register$lambda$5(Properties $properties) {
    return new Item($properties);
  }

  @NotNull
  public final List<ItemEntry<?>> getEntries$zinecraft() {
    return this.entries;
  }

  @NotNull
  public final <T extends Item> ItemEntry<T> register(
      @NotNull String path,
      @NotNull String zhCn,
      @NotNull String enUs,
      @NotNull ModelTemplate model,
      boolean includeInCreative,
      @NotNull Function0<? extends T> factory
  ) {
    if (!ResourceLocation.isValidPath(path)) {
      int m = 0;
      String string3 = "物品 ID 路径无效：" + path;
      throw new IllegalArgumentException(string3.toString());
    }

    if (StringsKt.isBlank(zhCn)) {
      int l = 0;
      String string2 = "物品中文名不能为空：" + path;
      throw new IllegalArgumentException(string2.toString());
    }

    if (StringsKt.isBlank(enUs)) {
      int k = 0;
      String string1 = "物品英文名不能为空：" + path;
      throw new IllegalArgumentException(string1.toString());
    }

    var iterable = this.entries;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      Iterator iterator = iterable.iterator();

      while (true) {
        if (!iterator.hasNext()) {
          bl = true;
          break;
        }

        Object object = iterator.next();
        ItemEntry itemEntry = (ItemEntry) object;
        int j = 0;
        if (java.util.Objects.equals(itemEntry.getPath(), path)) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string = "物品 ID 重复：" + path;
      throw new IllegalArgumentException(string.toString());
    } else {
      ItemEntry itemEntry1 = new ItemEntry<>(path, this.registrar.item(path, factory::invoke), model, includeInCreative);
      this.entries.add(itemEntry1);
      TranslationCatalog translationCatalog = this.translations;
      String string4 = "item." + this.registrar.getNamespace() + "." + path;
      translationCatalog.add(string4, zhCn, enUs);
      return itemEntry1;
    }
  }

  @NotNull
  public final ItemEntry<Item> register(
      @NotNull String path, @NotNull String zhCn, @NotNull String enUs, @NotNull ModelTemplate model, @NotNull Properties properties, boolean includeInCreative
  ) {
    return this.register(path, zhCn, enUs, model, includeInCreative, () -> new Item(properties));
  }
}

