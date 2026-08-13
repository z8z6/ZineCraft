package com.cxxcxx.zinecraft.api.content

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.recipes.RecipeOutput
import java.util.concurrent.CompletableFuture

class ContentLanguageProvider(
  output: FabricDataOutput,
  registries: CompletableFuture<HolderLookup.Provider?>?,
  private val catalog: ContentCatalog,
  private val locale: String
) : FabricLanguageProvider(output, locale, registries) {
  override fun generateTranslations(
    holderLookup: HolderLookup.Provider?,
    translationBuilder: TranslationBuilder?
  ) {
    catalog.translations.forEach { (key, text) ->
      translationBuilder?.add(key, if (locale == "zh_cn") text.zhCn else text.enUs)
    }
  }
}

open class ContentModelProvider(
  output: FabricDataOutput,
  private val catalog: ContentCatalog
) : FabricModelProvider(output) {
  override fun generateBlockStateModels(generator: BlockModelGenerators?) {
    catalog.blocks.filter { it.cubeModel }.forEach { generator?.createTrivialCube(it.block) }
  }

  override fun generateItemModels(generator: ItemModelGenerators?) {
    catalog.items.forEach { generator?.generateFlatItem(it.item, it.model) }
  }
}

class ContentLootTableProvider(
  output: FabricDataOutput,
  registries: CompletableFuture<HolderLookup.Provider?>?,
  private val catalog: ContentCatalog
) : FabricBlockLootTableProvider(output, registries) {
  override fun generate() {
    catalog.blocks.filter { it.dropSelf }.forEach { dropSelf(it.block) }
  }
}

class ContentRecipeProvider(
  output: FabricDataOutput,
  registries: CompletableFuture<HolderLookup.Provider?>?,
  private val catalog: ContentCatalog
) : FabricRecipeProvider(output, registries) {
  override fun buildRecipes(exporter: RecipeOutput) {
    catalog.recipes.forEach { it(exporter) }
  }
}
