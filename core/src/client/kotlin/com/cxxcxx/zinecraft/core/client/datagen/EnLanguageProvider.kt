package com.cxxcxx.zinecraft.core.client.datagen

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture


class EnLanguageProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricLanguageProvider(dataOutput, "en_us", registryLookup) {

  // generated/assets/zinecraft-core/lang/en_us.json
  override fun generateTranslations(holderLookup: HolderLookup.Provider?, translationBuilder: TranslationBuilder?) {
    translationBuilder?.add(ModItem.CUSTOM_ITEM, "Custom Item")
    translationBuilder?.add(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), "Example Entity Block")
  }
}