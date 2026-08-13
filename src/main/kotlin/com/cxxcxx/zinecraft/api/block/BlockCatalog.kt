package com.cxxcxx.zinecraft.api.block

import com.cxxcxx.zinecraft.api.localization.TranslationCatalog
import com.cxxcxx.zinecraft.api.localization.toDisplayName
import com.cxxcxx.zinecraft.api.registry.ModRegistrar
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
    cubeModel: Boolean = true,
    registerItem: Boolean = true,
    factory: () -> T
  ): BlockEntry<T> {
    val block = registrar.block(path, factory(), registerItem)
    val entry = BlockEntry(path, block, dropSelf, cubeModel, registerItem)
    entries += entry
    translations.add(block.descriptionId, zhCn, enUs)
    return entry
  }
}

class BlockEntry<T : Block> internal constructor(
  val path: String,
  val block: T,
  internal val dropSelf: Boolean,
  internal val cubeModel: Boolean,
  internal val registerItem: Boolean
)
