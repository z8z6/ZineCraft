package com.cxxcxx.zinecraft.api.block

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.localization.toDisplayName
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block

class BlockCatalog(
  private val registrar: ModRegistrar,
  private val translations: TranslationCatalog
) {
  internal val entries = mutableListOf<BlockEntry<*>>()

  fun <T : Block> register(
    path: String,
    zhCn: String,
    enUs: String = path.toDisplayName(),
    dropSelf: Boolean = true,
    dropItem: ItemLike? = null,
    cubeModel: Boolean = true,
    registerItem: Boolean = true,
    factory: () -> T
  ): BlockEntry<T> {
    require(!dropSelf || dropItem == null) { "方块不能同时掉落自身和指定物品: $path" }
    val block = registrar.block(path, factory(), registerItem)
    val entry = BlockEntry(path, block, dropSelf, dropItem, cubeModel, registerItem)
    entries += entry
    translations.add(block.descriptionId, zhCn, enUs)
    return entry
  }
}

class BlockEntry<T : Block> internal constructor(
  val path: String,
  val block: T,
  internal val dropSelf: Boolean,
  internal val dropItem: ItemLike?,
  internal val cubeModel: Boolean,
  internal val registerItem: Boolean
)
