package com.cxxcxx.zinecraft.api.accessory;

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog;
import com.cxxcxx.zinecraft.api.registry.ItemCatalog;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Shared collectible registration API and data-generation metadata catalog.
 */
public final class CollectibleCatalog {
  private static final int ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS = 24;

  private final ItemCatalog items;
  private final TranslationCatalog translations;
  private final String namespace;
  private final String seriesTranslationKey;
  private final String originalEffectLabelTranslationKey;
  private final String minecraftEffectLabelTranslationKey;
  private final List<CollectibleBuilder> mutableEntries = new ArrayList<>();
  public final List<CollectibleBuilder> entries = Collections.unmodifiableList(mutableEntries);

  public CollectibleCatalog(ItemCatalog items, TranslationCatalog translations, String namespace) {
    this.items = Objects.requireNonNull(items, "items");
    this.translations = Objects.requireNonNull(translations, "translations");
    this.namespace = Objects.requireNonNull(namespace, "namespace");
    this.seriesTranslationKey = "item." + namespace + ".collectible.series";
    this.originalEffectLabelTranslationKey = "item." + namespace + ".collectible.original_effect";
    this.minecraftEffectLabelTranslationKey = "item." + namespace + ".collectible.minecraft_effect";
  }

  public CollectibleBuilder builder(String path, String orderId, String zhCn) {
    return new CollectibleBuilder(this, path, orderId, zhCn);
  }

  private DeferredItem<CollectibleItem> register(CollectibleBuilder builder) {
    if (mutableEntries.stream().anyMatch(entry -> entry.path.equals(builder.path))) {
      throw new IllegalArgumentException("藏品 ID 重复：" + builder.path);
    }

    CollectibleSpec declaredSpec = new CollectibleSpec(
        builder.path, builder.orderId, builder.zhCn, builder.enUs,
        builder.originalEffectZhCn, builder.originalEffectEnUs,
        builder.descriptionZhCn, builder.descriptionEnUs,
        builder.minecraftEffectZhCn, builder.minecraftEffectEnUs,
        builder.power, builder.rarity, 0, 0
    );
    List<LocalizedTooltipLine> originalEffectLines = CollectibleTooltips.wrapLocalizedTooltip(
        declaredSpec.originalEffectZhCn(), declaredSpec.originalEffectEnUs(),
        ORIGINAL_EFFECT_FIRST_LINE_CHARACTERS
    );
    List<LocalizedTooltipLine> descriptionLines = CollectibleTooltips.wrapLocalizedTooltip(
        declaredSpec.descriptionZhCn(), declaredSpec.descriptionEnUs()
    );
    CollectibleSpec registeredSpec = declaredSpec.withLineCounts(
        originalEffectLines.size(), descriptionLines.size()
    );

    DeferredItem<CollectibleItem> item = items.builder(
            builder.path,
            builder.zhCn,
            builder.enUs,
            () -> new CollectibleItem(
                registeredSpec,
                namespace,
                seriesTranslationKey,
                originalEffectLabelTranslationKey,
                minecraftEffectLabelTranslationKey
            ),
            ModelTemplates.FLAT_ITEM,
            false
        )
        .getItem();

    registerTranslations(registeredSpec, originalEffectLines, descriptionLines);
    builder.spec = registeredSpec;
    builder.item = item;
    mutableEntries.add(builder);
    return item;
  }

  private void registerTranslations(
      CollectibleSpec spec,
      List<LocalizedTooltipLine> originalEffectLines,
      List<LocalizedTooltipLine> descriptionLines
  ) {
    String key = "item." + namespace + "." + spec.path();
    translations.add(key + ".original_effect", spec.originalEffectZhCn(), spec.originalEffectEnUs());
    for (int index = 0; index < originalEffectLines.size(); index++) {
      LocalizedTooltipLine line = originalEffectLines.get(index);
      translations.add(key + ".original_effect." + index, line.getZhCn(), line.getEnUs());
    }

    translations.add(key + ".description", spec.descriptionZhCn(), spec.descriptionEnUs());
    for (int index = 0; index < descriptionLines.size(); index++) {
      LocalizedTooltipLine line = descriptionLines.get(index);
      translations.add(key + ".description." + index, line.getZhCn(), line.getEnUs());
    }
    translations.add(key + ".minecraft_effect", spec.minecraftEffectZhCn(), spec.minecraftEffectEnUs());
  }

  /**
   * Holds a collectible declaration and its native deferred item handle.
   */
  public static final class CollectibleBuilder {
    public final String path;
    public final String orderId;
    public final String zhCn;
    private final CollectibleCatalog catalog;
    public String enUs;
    public String originalEffectZhCn;
    public String originalEffectEnUs;
    public String descriptionZhCn;
    public String descriptionEnUs;
    public String minecraftEffectZhCn;
    public String minecraftEffectEnUs;
    public CollectiblePower power;
    public Rarity rarity = Rarity.COMMON;
    public CollectibleSpec spec;
    public DeferredItem<CollectibleItem> item;

    private CollectibleBuilder(CollectibleCatalog catalog, String path, String orderId, String zhCn) {
      this.catalog = catalog;
      this.path = path;
      this.orderId = orderId;
      this.zhCn = zhCn;
    }

    public CollectibleBuilder enUs(String enUs) {
      ensureMutable();
      this.enUs = enUs;
      return this;
    }

    public CollectibleBuilder originalEffect(String zhCn, String enUs) {
      ensureMutable();
      this.originalEffectZhCn = zhCn;
      this.originalEffectEnUs = enUs;
      return this;
    }

    public CollectibleBuilder description(String zhCn, String enUs) {
      ensureMutable();
      this.descriptionZhCn = zhCn;
      this.descriptionEnUs = enUs;
      return this;
    }

    public CollectibleBuilder minecraftEffect(String zhCn, String enUs, CollectiblePower power) {
      ensureMutable();
      this.minecraftEffectZhCn = zhCn;
      this.minecraftEffectEnUs = enUs;
      this.power = power;
      return this;
    }

    public CollectibleBuilder rarity(Rarity rarity) {
      ensureMutable();
      this.rarity = Objects.requireNonNull(rarity, "rarity");
      return this;
    }

    public DeferredItem<CollectibleItem> build() {
      ensureMutable();
      return catalog.register(this);
    }

    private void ensureMutable() {
      if (item != null) throw new IllegalStateException("藏品 builder 不能在 build 后继续修改：" + path);
    }
  }
}
