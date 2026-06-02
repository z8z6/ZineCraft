package com.cxxcxx.zinecraft.core.data

import com.cxxcxx.zinecraft.core.ZinecraftCore
import com.cxxcxx.zinecraft.core.block.ModBlock
import com.cxxcxx.zinecraft.core.item.ModItem
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture


class ModEnLanguageProvider(
  dataOutput: FabricDataOutput?,
  registryLookup: CompletableFuture<HolderLookup.Provider?>?
) : FabricLanguageProvider(dataOutput, "en_us", registryLookup) {

  // generated/assets/zinecraft-core/lang/en_us.json
  override fun generateTranslations(holderLookup: HolderLookup.Provider?, T: TranslationBuilder?) {
    // 创造模式物品栏标题
    T?.add("itemGroup." + ZinecraftCore.MOD_ID, "Zinecraft")
    ModItem.ItemWrap.List.forEach { T?.add(it.item, it.en_us) }
    ModSound.Song.List.forEach { T?.add(it.name, it.desc) }
    T?.add(ModBlock.EXAMPLE_ENTITY_BLOCK.asItem(), "Example Entity Block")
  }
}