package com.cxxcxx.zinecraft.api.datagen;

import com.cxxcxx.zinecraft.api.registry.catalog.TranslationCatalog;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Emits one locale from the shared translation catalog.
 */
public final class CatalogLanguageProvider extends LanguageProvider {
  private final TranslationCatalog translations;
  private final String locale;

  public CatalogLanguageProvider(PackOutput output, TranslationCatalog translations, String locale) {
    super(output, "zinecraft", locale);
    this.translations = translations;
    this.locale = locale;
  }

  @Override
  protected void addTranslations() {
    translations.getEntries().forEach((key, text) ->
        add(key, locale.equals("zh_cn") ? text.getZhCn() : text.getEnUs())
    );
  }
}
