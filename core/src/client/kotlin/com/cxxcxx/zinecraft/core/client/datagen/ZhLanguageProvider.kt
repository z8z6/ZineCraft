package com.cxxcxx.zinecraft.core.client.datagen

import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ZhLanguageProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricLanguageProvider(dataOutput, "zh_cn", registryLookup) {

  // generated/assets/zinecraft-core/lang/zh_cn.json
  override fun generateTranslations(holderLookup: HolderLookup.Provider?, translationBuilder: TranslationBuilder?) {
    translationBuilder?.add(ModItem.EXAMPLE_ITEM, "示例物品")
    translationBuilder?.add(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), "示例实体方块")
  }
}