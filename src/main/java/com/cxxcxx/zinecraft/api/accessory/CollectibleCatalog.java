package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.item.ItemCatalog;
import com.cxxcxx.zinecraft.api.item.ItemEntry;
import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class CollectibleCatalog {
  @Deprecated
  public static final int ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS = 24;
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

  @NotNull
  public final CollectibleEntry register(@NotNull CollectibleSpec spec) {
    if (entries.stream().anyMatch(entry -> entry.getSpec().getPath().equals(spec.getPath()))) {
      throw new IllegalArgumentException("藏品 ID 重复：" + spec.getPath());
    }

    List<LocalizedTooltipLine> originalEffectLines = CollectibleTooltips.wrapLocalizedTooltip(
        spec.getOriginalEffectZhCn(), spec.getOriginalEffectEnUs(), ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS);
    List<LocalizedTooltipLine> descriptionLines = CollectibleTooltips.wrapLocalizedTooltip(
        spec.getDescriptionZhCn(), spec.getDescriptionEnUs());
    CollectibleSpec registeredSpec = spec.withLineCounts(originalEffectLines.size(), descriptionLines.size());
    ItemEntry<CollectibleItem> itemEntry = this.items.register(
        spec.getPath(), spec.getZhCn(), spec.getEnUs(),
        net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM, false,
        () -> new CollectibleItem(registeredSpec, namespace, seriesTranslationKey,
            originalEffectLabelTranslationKey, minecraftEffectLabelTranslationKey)
    );
    String translationKey = "item." + namespace + "." + spec.getPath();
    translations.add(translationKey + ".original_effect", spec.getOriginalEffectZhCn(), spec.getOriginalEffectEnUs());
    for (int index = 0; index < originalEffectLines.size(); index++) {
      LocalizedTooltipLine line = originalEffectLines.get(index);
      translations.add(translationKey + ".original_effect." + index, line.getZhCn(), line.getEnUs());
    }

    translations.add(translationKey + ".description", spec.getDescriptionZhCn(), spec.getDescriptionEnUs());
    for (int index = 0; index < descriptionLines.size(); index++) {
      LocalizedTooltipLine line = descriptionLines.get(index);
      translations.add(translationKey + ".description." + index, line.getZhCn(), line.getEnUs());
    }

    translations.add(translationKey + ".minecraft_effect", spec.getMinecraftEffectZhCn(), spec.getMinecraftEffectEnUs());
    CollectibleEntry entry = new CollectibleEntry(registeredSpec, itemEntry);
    entries.add(entry);
    return entry;
  }
}
