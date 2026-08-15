package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class CollectibleCatalog {
  @Deprecated
  public static final int ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS = 24;
  @NotNull
  private static final CollectibleCatalog.Companion Companion = new CollectibleCatalog.Companion(null);
  @NotNull
  private final ItemCatalog items;
  @NotNull
  private final TranslationCatalog translations;
  @NotNull
  private final String namespace;
  @NotNull
  private final List<CollectibleEntry> entries;
  @NotNull
  private final String seriesTranslationKey;
  @NotNull
  private final String originalEffectLabelTranslationKey;
  @NotNull
  private final String minecraftEffectLabelTranslationKey;

  public CollectibleCatalog(@NotNull ItemCatalog items, @NotNull TranslationCatalog translations, @NotNull String namespace) {
    super();
    this.items = items;
    this.translations = translations;
    this.namespace = namespace;
    this.entries = new ArrayList<>();
    this.seriesTranslationKey = "item." + this.namespace + ".collectible.series";
    this.originalEffectLabelTranslationKey = "item." + this.namespace + ".collectible.original_effect";
    this.minecraftEffectLabelTranslationKey = "item." + this.namespace + ".collectible.minecraft_effect";
  }

  private static final CollectibleItem register$lambda$2(CollectibleSpec $registeredSpec, CollectibleCatalog this$0) {
    return new CollectibleItem(
        $registeredSpec, this$0.namespace, this$0.seriesTranslationKey, this$0.originalEffectLabelTranslationKey, this$0.minecraftEffectLabelTranslationKey
    );
  }

  @NotNull
  public final CollectibleEntry register(@NotNull CollectibleSpec spec) {
    var iterable = this.entries;
    int i = 0;
    boolean bl;
    if (iterable instanceof Collection && ((Collection) iterable).isEmpty()) {
      bl = true;
    } else {
      Iterator registeredSpec = iterable.iterator();

      while (true) {
        if (!registeredSpec.hasNext()) {
          bl = true;
          break;
        }

        Object object = registeredSpec.next();
        CollectibleEntry collectibleEntry = (CollectibleEntry) object;
        int j = 0;
        if (java.util.Objects.equals(collectibleEntry.getSpec().getPath(), spec.getPath())) {
          bl = false;
          break;
        }
      }
    }

    if (!bl) {
      i = 0;
      String string = "藏品 ID 重复：" + spec.getPath();
      throw new IllegalArgumentException(string.toString());
    }

    iterable = CollectibleCatalogKt.wrapLocalizedTooltip$default(spec.getOriginalEffectZhCn(), spec.getOriginalEffectEnUs(), 24, 0, 8, null);
    List list = CollectibleCatalogKt.wrapLocalizedTooltip$default(spec.getDescriptionZhCn(), spec.getDescriptionEnUs(), 0, 0, 12, null);
    CollectibleSpec collectibleSpec = CollectibleSpec.copy$default(
        spec, null, null, null, null, null, null, null, null, null, null, null, null, iterable.size(), list.size(), 4095, null
    );
    ItemEntry itemEntry = this.items.register(
        spec.getPath(), spec.getZhCn(), spec.getEnUs(),
        net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM, false,
        () -> register$lambda$2(collectibleSpec, this)
    );
    String string1 = "item." + this.namespace + "." + spec.getPath();
    this.translations.add(string1 + ".original_effect", spec.getOriginalEffectZhCn(), spec.getOriginalEffectEnUs());
    Iterable iterable1 = iterable;
    int k = 0;
    int l = 0;

    for (Object object1 : iterable1) {
      int m = l++;
      if (m < 0) {
        CollectionsKt.throwIndexOverflow();
      }

      LocalizedTooltipLine line = (LocalizedTooltipLine) object1;
      int n = m;
      int o = 0;
      this.translations.add(string1 + ".original_effect." + n, line.getZhCn(), line.getEnUs());
    }

    this.translations.add(string1 + ".description", spec.getDescriptionZhCn(), spec.getDescriptionEnUs());
    iterable1 = list;
    k = 0;
    int p = 0;

    for (Object object2 : iterable1) {
      int r = p++;
      if (r < 0) {
        CollectionsKt.throwIndexOverflow();
      }

      LocalizedTooltipLine localizedTooltipLine1 = (LocalizedTooltipLine) object2;
      int s = r;
      int t = 0;
      this.translations.add(string1 + ".description." + s, localizedTooltipLine1.getZhCn(), localizedTooltipLine1.getEnUs());
    }

    this.translations.add(string1 + ".minecraft_effect", spec.getMinecraftEffectZhCn(), spec.getMinecraftEffectEnUs());
    CollectibleEntry collectibleEntry1 = new CollectibleEntry(collectibleSpec, itemEntry);
    List list1 = this.entries;
    CollectibleEntry collectibleEntry2 = collectibleEntry1;
    int q = 0;
    list1.add(collectibleEntry2);
    return collectibleEntry1;
  }

  private static final class Companion {
    private Companion() {
    }

    // $VF: synthetic method
    public Companion(DefaultConstructorMarker $constructor_marker) {
      this();
    }
  }
}

