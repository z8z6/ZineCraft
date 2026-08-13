package com.cxxcxx.zinecraft.api.datagen

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class CatalogLanguageProvider(
  output: FabricDataOutput,
  registries: CompletableFuture<HolderLookup.Provider?>?,
  private val translations: TranslationCatalog,
  private val locale: String
) : FabricLanguageProvider(output, locale, registries) {
  override fun generateTranslations(
    holderLookup: HolderLookup.Provider?,
    translationBuilder: TranslationBuilder?
  ) {
    translations.entries.forEach { (key, text) ->
      translationBuilder?.add(key, if (locale == "zh_cn") text.zhCn else text.enUs)
    }
  }
}
